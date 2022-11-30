package creoii.custom.objects.block;

import com.google.gson.*;
import creoii.custom.eventsystem.event.AbstractEvent;
import creoii.custom.loaders.Identifiable;
import creoii.custom.util.BlockUtil;
import creoii.custom.util.StringToObject;
import creoii.custom.util.json.CustomJsonHelper;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CustomBlock extends Block implements Identifiable {
    private final Identifier identifier;
    private final boolean hasItem;
    private final Settings blockSettings;
    private final Item.Settings itemSettings;
    private final boolean placeableOnLiquid;
    private final int redstonePower;
    private final int droppedXp;
    private final int fuelPower;
    private final float fallDamageMultiplier;
    private final float bounceVelocity;
    private final float slideVelocity;
    private final RenderLayer renderLayer;
    private final PathNodeType pathNodeType;
    private final OffsetType offsetType;
    private final Shape shape;
    private final int flammability;
    private final int fireSpread;
    private final float compostChance;

    public CustomBlock(
            Identifier identifier, boolean hasItem, Settings blockSettings, Item.Settings itemSettings,
            boolean placeableOnLiquid,
            int redstonePower, int droppedXp, int fuelPower,
            float fallDamageMultiplier, float bounceVelocity, float slideVelocity,
            RenderLayer renderLayer, PathNodeType pathNodeType, OffsetType offsetType, Shape shape,
            int flammability, int fireSpread, float compostChance
    ) {
        super(blockSettings);

        this.identifier = identifier;
        this.hasItem = hasItem;
        this.blockSettings = blockSettings;
        this.itemSettings = itemSettings;
        this.placeableOnLiquid = placeableOnLiquid;
        this.redstonePower = redstonePower;
        this.droppedXp = droppedXp;
        this.fuelPower = fuelPower;
        this.fallDamageMultiplier = fallDamageMultiplier;
        this.bounceVelocity = bounceVelocity;
        this.slideVelocity = slideVelocity;
        this.renderLayer = renderLayer;
        this.pathNodeType = pathNodeType;
        this.offsetType = offsetType;
        this.shape = shape;
        this.flammability = flammability;
        this.fireSpread = fireSpread;
        this.compostChance = compostChance;

        Registry.register(Registry.BLOCK, this.getIdentifier(), this);
        Registry.register(Registry.ITEM, this.getIdentifier(), new BlockItem(this, this.getItemSettings()));
        FuelRegistry.INSTANCE.add(this, this.getFuelPower());
        ((FireBlock) Blocks.FIRE).registerFlammableBlock(this, this.getFlammability(), this.getFireSpread());
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(this, this.getCompostChance());
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public boolean hasItem() {
        return hasItem;
    }

    public Settings getBlockSettings() {
        return blockSettings;
    }

    public Item.Settings getItemSettings() {
        return itemSettings;
    }

    public boolean isPlaceableOnLiquid() {
        return placeableOnLiquid;
    }

    public int getRedstonePower() {
        return redstonePower;
    }

    public int getDroppedXp() {
        return droppedXp;
    }

    public int getFuelPower() {
        return fuelPower;
    }

    public RenderLayer getRenderLayer() {
        return renderLayer;
    }

    public PathNodeType getPathNodeType() {
        return pathNodeType;
    }

    public int getFlammability() {
        return flammability;
    }

    public int getFireSpread() {
        return fireSpread;
    }

    public float getCompostChance() {
        return compostChance;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean emitsRedstonePower(BlockState state) {
        return getRedstonePower() > 0;
    }

    @Override
    @SuppressWarnings("deprecation")
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return getRedstonePower();
    }

    private VoxelShape unionAll(Shape[] shapes) {
        VoxelShape[] voxelShapes = new VoxelShape[shapes.length];
        for (int i = 0; i < shapes.length; ++i) {
            float x = shapes[i].minX;
            float y = shapes[i].minY;
            float z = shapes[i].minZ;
            float x1 = shapes[i].maxX;
            float y1 = shapes[i].maxY;
            float z1 = shapes[i].maxZ;
            voxelShapes[i] = VoxelShapes.cuboid(x, y, z, x1, y1, z1);
        }

        VoxelShape ret = null;
        int prev = 0;
        for (int i = 1; i < voxelShapes.length; ++i, ++prev) {
            ret = voxelShapes[prev];
            ret = VoxelShapes.union(ret, voxelShapes[i]);
        }
        return ret;
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.handleFallDamage(fallDistance, this.fallDamageMultiplier, DamageSource.FALL);
    }

    @Override
    public void onEntityLand(BlockView world, Entity entity) {
        if (entity.bypassesLandingEffects()) {
            super.onEntityLand(world, entity);
        } else {
            BlockUtil.bounce(entity, this.bounceVelocity);
        }
    }

    public static class Serializer implements JsonDeserializer<CustomBlock>, JsonSerializer<CustomBlock> {
        @Override
        public CustomBlock deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = JsonHelper.asObject(json, "block");
            boolean hasItem = JsonHelper.getBoolean(object, "has_item", true);
            Settings blockSettings;
            if (object.has("block_settings")) {
                blockSettings = CustomJsonHelper.getBlockSettings(JsonHelper.getObject(object, "block_settings"), "block settings");
            } else blockSettings = FabricBlockSettings.copy(Blocks.STONE);

            Item.Settings itemSettings;
            if (object.has("item_settings")) {
                itemSettings = CustomJsonHelper.getItemSettings(JsonHelper.getObject(object, "item_settings"), "item settings");
            } else itemSettings = new FabricItemSettings();

            boolean placeableOnLiquid = JsonHelper.getBoolean(object, "placeable_on_liquid", false);
            int redstonePower = JsonHelper.getInt(object, "redstone_power", 0);
            int droppedXp = JsonHelper.getInt(object, "dropped_xp", 0);
            int fuelPower = JsonHelper.getInt(object, "fuel_power", 0);
            float fallDamageMultiplier = JsonHelper.getFloat(object, "fall_damage_multiplier", 1f);
            float bounceVelocity = JsonHelper.getFloat(object, "bounce_velocity_multiplier", 0f);
            float slideVelocity = JsonHelper.getFloat(object, "slide_velocity_multiplier", 1f);
            RenderLayer renderLayer = StringToObject.renderLayer(JsonHelper.getString(object, "render_layer", "solid"));
            PathNodeType pathNodeType = PathNodeType.valueOf(JsonHelper.getString(object, "pathing_type", "walkable"));
            OffsetType offsetType = OffsetType.valueOf(JsonHelper.getString(object, "offset_type", "none"));
            Shape shape = Shape.get(object);
            int flammability = JsonHelper.getInt(object, "flammability", 0);
            int fireSpread = JsonHelper.getInt(object, "fire_spread", 0);
            float compostChance = JsonHelper.getFloat(object, "compost_chance", 0f);
            if (JsonHelper.hasArray(object, "events")) {
                JsonArray array = JsonHelper.getArray(object, "events");
                List<AbstractEvent> events = new ArrayList<>();
                for (int i = 0; i < array.size(); ++i) {
                    if (array.get(i).isJsonObject()) {
                        JsonObject eventObj = array.get(i).getAsJsonObject();
                        if (eventObj.getAsJsonArray("effects").size() > 0) {
                            AbstractEvent event = AbstractEvent.getEvent(Identifier.tryParse(eventObj.get("type").getAsString()));
                            events.add(event.getFromJson(eventObj));
                        }
                    }
                }
                return new EventCustomBlock(
                        Identifier.tryParse(JsonHelper.getString(object, "identifier")), hasItem,
                        blockSettings, itemSettings,
                        placeableOnLiquid,
                        redstonePower, droppedXp, fuelPower,
                        fallDamageMultiplier, bounceVelocity, slideVelocity,
                        renderLayer, pathNodeType, offsetType, shape,
                        flammability, fireSpread, compostChance,
                        events
                );
            } else {
                if (shape.presetShape != null) {
                    switch (shape.presetShape) {
                        case SLAB -> {
                            return new CustomSlabBlock(
                                    Identifier.tryParse(JsonHelper.getString(object, "identifier")), hasItem,
                                    blockSettings, itemSettings,
                                    placeableOnLiquid,
                                    redstonePower, droppedXp, fuelPower,
                                    fallDamageMultiplier, bounceVelocity, slideVelocity,
                                    renderLayer, pathNodeType,
                                    flammability, fireSpread, compostChance
                            );
                        }
                        case STAIRS -> {
                            return new CustomStairsBlock(
                                    Identifier.tryParse(JsonHelper.getString(object, "identifier")), hasItem,
                                    blockSettings, itemSettings,
                                    placeableOnLiquid,
                                    redstonePower, droppedXp, fuelPower,
                                    fallDamageMultiplier, bounceVelocity, slideVelocity,
                                    renderLayer, pathNodeType,
                                    flammability, fireSpread, compostChance
                            );
                        }
                        case WALL -> {
                            return new CustomWallBlock(
                                    Identifier.tryParse(JsonHelper.getString(object, "identifier")), hasItem,
                                    blockSettings, itemSettings,
                                    placeableOnLiquid,
                                    redstonePower, droppedXp, fuelPower,
                                    fallDamageMultiplier, bounceVelocity, slideVelocity,
                                    renderLayer, pathNodeType,
                                    flammability, fireSpread, compostChance
                            );
                        }
                    }
                }
                return new CustomBlock(
                        Identifier.tryParse(JsonHelper.getString(object, "identifier")), hasItem,
                        blockSettings, itemSettings,
                        placeableOnLiquid,
                        redstonePower, droppedXp, fuelPower,
                        fallDamageMultiplier, bounceVelocity, slideVelocity,
                        renderLayer, pathNodeType, offsetType, shape,
                        flammability, fireSpread, compostChance
                );
            }
        }

        @Override
        public JsonElement serialize(CustomBlock src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.addProperty("identifier", src.getIdentifier().toString());
            object.addProperty("has_item", src.hasItem());
            object.add("block_settings", context.serialize(src.getBlockSettings()));
            object.add("item_settings", context.serialize(src.getItemSettings()));
            object.addProperty("placeable_on_liquid", src.isPlaceableOnLiquid());
            object.addProperty("redstone_powder", src.getRedstonePower());
            object.addProperty("dropped_xp", src.getDroppedXp());
            object.addProperty("fuel_power", src.getFuelPower());
            object.add("render_layer", context.serialize(src.getRenderLayer()));
            object.add("pathing_type", context.serialize(src.getPathNodeType()));
            object.addProperty("flammability", src.getFlammability());
            object.addProperty("compost_chance", src.getCompostChance());
            return object;
        }
    }

    public static class Shape {
        public PresetShape presetShape;
        public float minX;
        public float minY;
        public float minZ;
        public float maxX;
        public float maxY;
        public float maxZ;

        public Shape(PresetShape presetShape) {
            this.presetShape = presetShape;
        }

        public Shape(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
            this.minX = minX;
            this.minY = minY;
            this.minZ = minZ;
            this.maxX = maxX;
            this.maxY = maxY;
            this.maxZ = maxZ;
        }

        public static Shape get(JsonObject object) {
            if (object.has("shape")) {
                JsonObject shapeObj = object.getAsJsonObject("shape");
                if (shapeObj.has("preset")) {
                    return new Shape(PresetShape.valueOf(JsonHelper.getString(shapeObj, "preset", "CUBE").toUpperCase()));
                } else {
                    float minX;
                    float minY;
                    float minZ;
                    float maxX;
                    float maxY;
                    float maxZ;
                }
            }
            return new Shape(PresetShape.CUBE);
        }

        public enum PresetShape {
            CUBE,
            STAIRS,
            SLAB,
            WALL,
            FENCE
        }
    }
}
