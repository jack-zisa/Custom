package creoii.custom.eventsystem.effect;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import creoii.custom.eventsystem.parameter.BlockPosParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.WorldParameter;
import creoii.custom.util.json.CustomJsonHelper;
import net.minecraft.command.argument.NbtCompoundArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpawnEntityEffect extends AbstractEffect {
    private SpawnEntry[] entries;
    private EntityType<?> entityType;
    private SpawnReason reason;
    private boolean alignPosition;
    private BlockPos offset;
    private IntProvider count;
    private String nbt;

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of(EventParameters.WORLD, EventParameters.BLOCK_POS);
    }

    public SpawnEntityEffect getFromJson(JsonObject object) {
        SpawnEntityEffect effect = new SpawnEntityEffect();
        if (object.has("entries")) {
            JsonArray array = object.get("entries").getAsJsonArray();
            SpawnEntry[] entries = new SpawnEntry[array.size()];
            for (int i = 0; i < array.size(); ++i) {
                JsonElement element = array.get(i);
                if (element.isJsonObject()) {
                    JsonObject entryObj = element.getAsJsonObject();
                    entries[i] = new SpawnEntry(
                            Registries.ENTITY_TYPE.get(Identifier.tryParse(JsonHelper.getString(entryObj, "entity_type"))),
                            CustomJsonHelper.getBlockPos(entryObj, "offset"),
                            CustomJsonHelper.getBoolean(entryObj, new String[]{"align", "align_position"}, false),
                            SpawnReason.valueOf(CustomJsonHelper.getString(entryObj, new String[]{"reason", "spawn_reason"}, "NATURAL")),
                            CustomJsonHelper.getIntProvider(entryObj, new String[]{"count", "amount"}, 1),
                            entryObj.get("nbt").getAsString()
                    );
                }
            }
            effect.entries = entries;
        } else {
            effect.entityType = Registries.ENTITY_TYPE.get(Identifier.tryParse(CustomJsonHelper.getString(object, new String[]{"entity", "entity_type"})));
            effect.reason = SpawnReason.valueOf(CustomJsonHelper.getString(object, new String[]{"reason", "spawn_reason"}, "NATURAL"));
            effect.alignPosition = CustomJsonHelper.getBoolean(object, new String[]{"align", "align_position"}, false);
            effect.offset = CustomJsonHelper.getBlockPos(object, "offset");
            effect.count = CustomJsonHelper.getIntProvider(object, new String[]{"count", "amount"}, 1);
            effect.nbt = object.get("nbt").getAsString();
        }
        return effect;
    }

    @Override
    public void run(List<EventParameter> parameters) {
        WorldParameter worldParameter = (WorldParameter) EventParameter.find(parameters, getModifications(), EventParameters.WORLD);
        if (worldParameter != null) {
            World world = worldParameter.getWorld();
            if (!world.isClient) {
                BlockPosParameter blockPosParameter = (BlockPosParameter) EventParameter.find(parameters, getModifications(), EventParameters.BLOCK_POS);
                if (blockPosParameter != null) {
                    if (entries != null) {
                        for (SpawnEntry entry : entries) {
                            NbtCompound nbt = null;
                            if (entry.nbt != null) {
                                try {
                                    nbt = NbtCompoundArgumentType.nbtCompound().parse(new StringReader(entry.nbt));
                                } catch (CommandSyntaxException ignored) {}
                            }
                            Entity entity;
                            for (int i = 0; i < entry.count.get(world.getRandom()); ++i) {
                                entity = entry.entityType.create((ServerWorld) world, null, null, blockPosParameter.getPos().add(entry.offset), entry.reason, entry.align, false);
                                if (entity != null) {
                                    if (nbt != null) entity.writeNbt(nbt);
                                    world.spawnEntity(entity);
                                }
                            }
                        }
                    } else {
                        NbtCompound nbt = null;
                        if (this.nbt != null) {
                            try {
                                nbt = NbtCompoundArgumentType.nbtCompound().parse(new StringReader(this.nbt));
                            } catch (CommandSyntaxException ignored) {}
                        }
                        Entity entity;
                        for (int i = 0; i < count.get(world.getRandom()); ++i) {
                            entity = entityType.create((ServerWorld) world, null, null, blockPosParameter.getPos().add(offset), reason, alignPosition, false);
                            if (entity != null) {
                                if (nbt != null) {
                                    entity.writeNbt(nbt);
                                }
                                world.spawnEntity(entity);
                            }
                        }
                    }
                }
            }
        }
    }

    public static record SpawnEntry(EntityType<?> entityType, BlockPos offset, boolean align, SpawnReason reason, IntProvider count, @Nullable String nbt) {}
}
