package creoii.custom.util.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import creoii.custom.Custom;
import creoii.custom.objects.CustomMaterial;
import creoii.custom.util.provider.ConstantDoubleProvider;
import creoii.custom.util.provider.DoubleProvider;
import creoii.custom.util.provider.UniformDoubleProvider;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Rarity;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.floatprovider.ConstantFloatProvider;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.util.math.floatprovider.UniformFloatProvider;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.intprovider.WeightedListIntProvider;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Optional;

import static creoii.custom.util.StringToObject.*;

public class CustomJsonHelper {
    /**
     * Gets a string from a JsonObject, allowing multiple element names
     */
    public static String getString(JsonObject object, String[] elements, @Nullable String defaultStr) {
        for (String element : elements) {
            if (object.has(element)) {
                return object.get(element).getAsString();
            }
        }

        if (defaultStr != null) {
            return defaultStr;
        }
        throw new JsonSyntaxException("Missing " + Arrays.toString(elements) + ", expected to find a string");
    }

    public static String getString(JsonObject object, String... elements) {
        for (String element : elements) {
            if (object.has(element)) {
                return object.get(element).getAsString();
            }
        }
        throw new JsonSyntaxException("Missing " + Arrays.toString(elements) + ", expected to find a string");
    }

    public static boolean getBoolean(JsonObject object, String[] elements, boolean defaultBool) {
        for (String element : elements) {
            if (object.has(element)) {
                return object.get(element).getAsBoolean();
            }
        }
        return defaultBool;
    }

    public static boolean getBoolean(JsonObject object, String... elements) {
        for (String element : elements) {
            if (object.has(element)) {
                return object.get(element).getAsBoolean();
            }
        }
        throw new JsonSyntaxException("Missing " + Arrays.toString(elements) + ", expected to find a boolean");
    }

    public static int getInt(JsonObject object, String[] elements, int defaultInt) {
        for (String element : elements) {
            if (object.has(element)) {
                return object.get(element).getAsInt();
            }
        }
        return defaultInt;
    }

    public static int getInt(JsonObject object, String... elements) {
        for (String element : elements) {
            if (object.has(element)) {
                return object.get(element).getAsInt();
            }
        }
        throw new JsonSyntaxException("Missing " + Arrays.toString(elements) + ", expected to find an integer");
    }

    public static float getFloat(JsonObject object, String[] elements, float defaultFloat) {
        for (String element : elements) {
            if (object.has(element)) {
                return object.get(element).getAsFloat();
            }
        }
        return defaultFloat;
    }

    public static float getFloat(JsonObject object, String... elements) {
        for (String element : elements) {
            if (object.has(element)) {
                return object.get(element).getAsFloat();
            }
        }
        throw new JsonSyntaxException("Missing " + Arrays.toString(elements) + ", expected to find a float");
    }

    public static DoubleProvider getDoubleProvider(JsonObject object, String name, double defaultDouble) {
        if (object.has(name)) {
            return getDoubleProvider(object.get(name));
        } return ConstantDoubleProvider.create(defaultDouble);
    }

    public static DoubleProvider getDoubleProvider(JsonObject object, String[] names, double defaultDouble) {
        for (String name : names) {
            if (object.has(name)) {
                return getDoubleProvider(object.get(name));
            }
        }
        return ConstantDoubleProvider.create(defaultDouble);
    }

    public static DoubleProvider getDoubleProvider(JsonElement element) {
        if (element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();
            if (object.has("type")) {
                return switch (object.get("type").getAsString()) {
                    case "constant", "minecraft:constant" -> ConstantDoubleProvider.create(object.get("value").getAsDouble());
                    case "uniform", "minecraft:uniform" -> UniformDoubleProvider.create(object.get("min_inclusive").getAsDouble(), object.get("max_exclusive").getAsDouble());
                    default -> ConstantDoubleProvider.ZERO;
                };
            }
        } else if (element.isJsonPrimitive()) {
            return ConstantDoubleProvider.create(element.getAsDouble());
        }
        return null;
    }

    public static FloatProvider getFloatProvider(JsonObject object, String name, float defaultFloat) {
        if (object.has(name)) {
            return getFloatProvider(object.get(name));
        } return ConstantFloatProvider.create(defaultFloat);
    }

    public static FloatProvider getFloatProvider(JsonObject object, String[] names, float defaultFloat) {
        for (String name : names) {
            if (object.has(name)) {
                return getFloatProvider(object.get(name));
            }
        }
        return ConstantFloatProvider.create(defaultFloat);
    }

    public static FloatProvider getFloatProvider(JsonElement element) {
        if (element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();
            if (object.has("type")) {
                return switch (object.get("type").getAsString()) {
                    case "constant", "minecraft:constant" -> ConstantFloatProvider.create(object.get("value").getAsFloat());
                    case "uniform", "minecraft:uniform" -> UniformFloatProvider.create(object.get("min_inclusive").getAsFloat(), object.get("max_exclusive").getAsFloat());
                    default -> ConstantFloatProvider.ZERO;
                };
            }
        } else if (element.isJsonPrimitive()) {
            return ConstantFloatProvider.create(element.getAsFloat());
        }
        return null;
    }

    public static IntProvider getIntProvider(JsonObject object, String name, int defaultInt) {
        if (object.has(name)) {
            return getIntProvider(object.get(name));
        } return ConstantIntProvider.create(defaultInt);
    }

    public static IntProvider getIntProvider(JsonObject object, String[] names, int defaultInt) {
        for (String name : names) {
            if (object.has(name)) {
                return getIntProvider(object.get(name));
            }
        }
        return ConstantIntProvider.create(defaultInt);
    }

    public static IntProvider getIntProvider(JsonElement element) {
        if (element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();
            if (object.has("type")) {
                switch (object.get("type").getAsString()) {
                    case  "constant", "minecraft:constant" -> {
                        return ConstantIntProvider.create(object.get("value").getAsInt());
                    }
                    case "uniform", "minecraft:uniform" -> {
                        return UniformIntProvider.create(CustomJsonHelper.getInt(object, new String[]{"min_inclusive", "min"}), CustomJsonHelper.getInt(object, new String[]{"max_exclusive", "max"}));
                    }
                    case  "weighted_list", "minecraft:weighted_list" -> {
                        DataPool.Builder<IntProvider> list = DataPool.builder();
                        JsonHelper.getArray(object, "values").forEach(intElement -> {
                            JsonObject object1 = intElement.getAsJsonObject();
                            list.add(getIntProvider(object1), object1.get("weight").getAsInt());
                        });
                        return new WeightedListIntProvider(list.build());
                    }
                }
            }
        } else if (element.isJsonPrimitive()) {
            return ConstantIntProvider.create(element.getAsInt());
        }
        return null;
    }

    public static AbstractBlock.Settings getBlockSettings(JsonElement element, String name) {
        if (element.isJsonObject()) {
            JsonObject object = JsonHelper.asObject(element, "block settings");
            if (object.has("type") && JsonHelper.getString(object, "type").equals("copy")) {
                return FabricBlockSettings.copy(block(JsonHelper.getString(object, "block")));
            }
            else {
                Material material;
                if (object.get("material").isJsonObject()) {
                    material = getMaterial(object, "material");
                } else if (object.get("material").isJsonPrimitive()) {
                    material = material(JsonHelper.getString(object, "material", "stone"));
                } else material = Material.STONE;

                MapColor mapColor = mapColor(JsonHelper.getString(object, "map_color", "clear"));
                AbstractBlock.Settings settings = FabricBlockSettings.of(material, mapColor)
                        .sounds(blockSoundGroup(JsonHelper.getString(object, "sound_group", "stone")))
                        .collidable(JsonHelper.getBoolean(object, "collidable", true))
                        .luminance((state) -> JsonHelper.getInt(object, "luminance", 0))
                        .strength(
                                JsonHelper.getFloat(object, "hardness", 0f),
                                CustomJsonHelper.getFloat(object, new String[]{"resistance", "blast_resistance"}, 0f)
                        )
                        .emissiveLighting((state, world, pos) -> JsonHelper.getBoolean(object, "emissive", false))
                        .postProcess((state, world, pos) -> JsonHelper.getBoolean(object, "post_process", false))
                        .slipperiness(JsonHelper.getFloat(object, "slipperiness", .6f))
                        .velocityMultiplier(JsonHelper.getFloat(object, "velocity_multiplier", 1f))
                        .jumpVelocityMultiplier(JsonHelper.getFloat(object, "jump_velocity_multiplier", 1f))
                        .suffocates((state, world, pos) -> JsonHelper.getBoolean(object, "suffocates", true))
                        .blockVision((state, world, pos) -> JsonHelper.getBoolean(object, "blocks_vision", true))
                        .solidBlock((state, world, pos) -> JsonHelper.getBoolean(object, "solid", true))
                        .dropsLike(block(JsonHelper.getString(object, "drops_like", "minecraft:air")));
                if (JsonHelper.getBoolean(object, "requires_tool", false)) settings.requiresTool();
                if (!JsonHelper.getBoolean(object, "opaque", true)) settings.nonOpaque();
                if (JsonHelper.getBoolean(object, "dynamic_bounds", false)) settings.dynamicBounds();
                if (JsonHelper.getBoolean(object, "ticks_randomly", false)) settings.ticksRandomly();
                if (JsonHelper.getBoolean(object, "drops_nothing", false)) settings.dropsNothing();
                if (JsonHelper.getBoolean(object, "is_air", false)) settings.air();
                if (JsonHelper.getBoolean(object, "breaks_instantly", false)) settings.breakInstantly();
                return settings;
            }
        }
        throw new JsonSyntaxException("Expected " + name + " to be block settings, was " + JsonHelper.getType(element));
    }

    public static Item.Settings getItemSettings(JsonElement element, String name) {
        if (element.isJsonObject()) {
            JsonObject object = JsonHelper.asObject(element, "item settings");
            Item.Settings settings = new FabricItemSettings();
            settings.maxCount(JsonHelper.getInt(object, "max_count", 64));
            settings.rarity(Rarity.valueOf(JsonHelper.getString(object, "rarity", Rarity.COMMON.name())));
            if (JsonHelper.getBoolean(object, "fireproof", false)) settings.fireproof();
            if (object.has("food")) {
                settings.food(getFood(object.get("food"), "food"));
            }
            return settings;
        }
        throw new JsonSyntaxException("Expected " + name + " to be item settings, was " + JsonHelper.getType(element));
    }

    public static FoodComponent getFood(JsonElement element, String name) {
        if (element.isJsonObject()) {
            JsonObject object = JsonHelper.asObject(element, "item settings");
            int hunger = JsonHelper.getInt(object, "hunger", 0);
            float saturationModifier = JsonHelper.getFloat(object, "saturation_modifier", 0f);
            boolean meat = JsonHelper.getBoolean(object, "meat", false);
            boolean alwaysEdible = JsonHelper.getBoolean(object, "always_edible", false);
            boolean snack = JsonHelper.getBoolean(object, "snack", false);
            FoodComponent.Builder food = new FoodComponent.Builder().hunger(hunger).saturationModifier(saturationModifier);
            if (JsonHelper.hasArray(object, "status_effects")) {
                JsonArray array = JsonHelper.getArray(object, "status_effects");
                for (int i = 0; i < array.size(); ++i) {
                    float chance = JsonHelper.getFloat(array.get(i).getAsJsonObject(), "chance", 1f);
                    food.statusEffect(getStatusEffectInstance(array.get(i)), chance);
                }
            }
            if (meat) food.meat();
            if (alwaysEdible) food.alwaysEdible();
            if (snack) food.snack();
            return food.build();
        }
        throw new JsonSyntaxException("Expected " + name + " to be food, was " + JsonHelper.getType(element));
    }

    public static Material getMaterial(JsonElement element, String name) {
        if (element.isJsonObject()) {
            JsonObject object = JsonHelper.asObject(element, "block material");
            return new Material(
                    mapColor(JsonHelper.getString(object, "map_color", "clear")),
                    JsonHelper.getBoolean(object, "liquid", false),
                    JsonHelper.getBoolean(object, "solid", true),
                    JsonHelper.getBoolean(object, "blocks_movement", true),
                    JsonHelper.getBoolean(object, "blocks_light", true),
                    JsonHelper.getBoolean(object, "burnable", false),
                    JsonHelper.getBoolean(object, "replaceable", false),
                    PistonBehavior.valueOf(JsonHelper.getString(object, "piston_behavior", PistonBehavior.NORMAL.name()))
            );
        }
        throw new JsonSyntaxException("Expected " + name + " to be block material, was " + JsonHelper.getType(element));
    }

    public static CustomMaterial.CustomArmorMaterial getCustomArmorMaterial(JsonElement element, String name) {
        if (element.isJsonObject()) {
            JsonObject object = JsonHelper.asObject(element, "armor material");
            String id = JsonHelper.getString(object, "name");
            int durability = JsonHelper.getInt(object, "durability", 1);
            int protection = JsonHelper.getInt(object, "protection", 0);
            int enchantability = JsonHelper.getInt(object, "enchantability", 0);
            SoundEvent equipSound = equipSoundEvent(JsonHelper.getString(object, "equip_sound"));
            ItemStack repairItem = new ItemStack(JsonHelper.getItem(object, "repair_item", Items.AIR));
            float toughness = JsonHelper.getFloat(object, "toughness", 0f);
            float knockbackResistance = JsonHelper.getFloat(object, "knockback_resistance", 0f);
            return new CustomMaterial.CustomArmorMaterial(
                    id, durability, protection, enchantability, equipSound, repairItem, toughness, knockbackResistance
            );
        }
        throw new JsonSyntaxException("Expected " + name + " to be armor material, was " + JsonHelper.getType(element));
    }

    public static CustomMaterial.CustomToolMaterial getCustomToolMaterial(JsonElement element, String name) {
        if (element.isJsonObject()) {
            JsonObject object = JsonHelper.asObject(element, "tool material");
            int durability = JsonHelper.getInt(object, "durability", 1);
            int miningLevel = JsonHelper.getInt(object, "mining_level", 0);
            int enchantability = JsonHelper.getInt(object, "enchantability", 0);
            float miningSpeedMultiplier = JsonHelper.getFloat(object, "mining_speed_multiplier", 1f);
            float attackDamage = JsonHelper.getFloat(object, "attack_damage", 1f);
            ItemStack repairItem = new ItemStack(JsonHelper.getItem(object, "repair_item", Items.AIR));
            return new CustomMaterial.CustomToolMaterial(
                    durability, miningLevel, enchantability, miningSpeedMultiplier, attackDamage, repairItem
            );
        }
        throw new JsonSyntaxException("Expected " + name + " to be tool material, was " + JsonHelper.getType(element));
    }

    public static BlockPos getBlockPos(JsonObject object) {
        IntProvider x = getIntProvider(object, "x", 0);
        IntProvider y = getIntProvider(object, "y", 0);
        IntProvider z = getIntProvider(object, "z", 0);
        return new BlockPos(x.get(Custom.RANDOM), y.get(Custom.RANDOM), z.get(Custom.RANDOM));
    }

    public static BlockPos getBlockPos(JsonObject object, String name) {
        if (object.has(name)) {
            return getBlockPos(object.getAsJsonObject(name));
        } else return BlockPos.ORIGIN;
    }

    public static BlockPos getBlockPos(JsonElement element, String name) {
        if (element.isJsonObject()) {
            JsonObject object = JsonHelper.asObject(element, "block pos");
            if (object.has(name)) {
                IntProvider x = getIntProvider(object, "x", 0);
                IntProvider y = getIntProvider(object, "y", 0);
                IntProvider z = getIntProvider(object, "z", 0);
                return new BlockPos(x.get(Custom.RANDOM), y.get(Custom.RANDOM), z.get(Custom.RANDOM));
            } else return BlockPos.ORIGIN;
        }
        throw new JsonSyntaxException("Expected " + name + " to be block pos, was " + JsonHelper.getType(element));
    }

    public static StatusEffectInstance getStatusEffectInstance(JsonElement element) {
        if (element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();
            StatusEffect effect = Registries.STATUS_EFFECT.get(Identifier.tryParse(object.get("effect").getAsString()));
            if (effect != null) {
                int duration = JsonHelper.getInt(object, "duration", 0);
                int amplifier = JsonHelper.getInt(object, "amplifier", 0);
                boolean ambient = JsonHelper.getBoolean(object, "ambient", false);
                boolean visible = JsonHelper.getBoolean(object, "visible", true);
                boolean showIcon = JsonHelper.getBoolean(object, "show_icon", true);
                if (object.has("hidden_effect")) {
                    StatusEffectInstance hiddenEffect = getStatusEffectInstance(JsonHelper.getObject(object, "hidden_effect"));
                    return new StatusEffectInstance(effect, duration, amplifier, ambient, visible, showIcon, hiddenEffect, Optional.empty());
                }
                return new StatusEffectInstance(effect, duration, amplifier, ambient, visible, showIcon);
            }
        }
        throw new JsonSyntaxException("Expected to find status effect instance, was " + JsonHelper.getType(element));
    }

    public static Text getText(JsonElement element) {
        if (element.isJsonPrimitive()) {
            return MutableText.of(new LiteralTextContent(element.getAsString()));
        } else if (element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();

            MutableText mutableText = MutableText.of(new LiteralTextContent(CustomJsonHelper.getString(object, new String[]{"text", "message", "content"})));
            if (object.has("formatting")) {
                for (Formatting formatting : CustomJsonObjects.TextFormatting.get(object, "formatting").formatting()) {
                    mutableText.formatted(formatting);
                }
            }
            return mutableText;
        }
        return Text.empty();
    }
}
