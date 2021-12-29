package creoii.custom.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import creoii.custom.custom.CustomBlock;
import creoii.custom.custom.CustomMaterial;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.JsonHelper;

import static creoii.custom.util.StringToObject.*;
import static creoii.custom.util.JsonUtil.*;

public class CustomJsonHelper {
    public static AbstractBlock.Settings getBlockSettings(JsonElement element, String name) {
        if (element.isJsonObject()) {
            JsonObject object = JsonHelper.asObject(element, "block settings");
            if (JsonHelper.getString(object, "type").equals("copy")) {
                return FabricBlockSettings.copy(block(JsonHelper.getString(object, "block")));
            }
            else {
                Material material;
                if (object.get("material").isJsonObject()) {
                    material = getMaterial(object, "material");
                } else if (object.get("material").isJsonPrimitive()) {
                    material = material(getOrDefault(object, "material", "stone"));
                } else material = Material.STONE;

                MapColor mapColor = mapColor(getOrDefault(object, "map_color", "clear"));
                AbstractBlock.Settings settings = FabricBlockSettings.of(material, mapColor)
                        .sounds(blockSoundGroup(getOrDefault(object, "sound_group", "stone")))
                        .collidable(getOrDefault(object, "collidable", true))
                        .luminance((state) -> getOrDefault(object, "luminance", 0))
                        .strength(
                                getOrDefault(object, "hardness", 0f),
                                getOrDefault(object, "blast_resistance", 0f)
                        )
                        .emissiveLighting((state, world, pos) -> getOrDefault(object, "emissive", false))
                        .postProcess((state, world, pos) -> getOrDefault(object, "post_process", false))
                        .slipperiness(getOrDefault(object, "slipperiness", .6f))
                        .velocityMultiplier(getOrDefault(object, "velocity_multiplier", 1f))
                        .jumpVelocityMultiplier(getOrDefault(object, "jump_velocity_multiplier", 1f))
                        .suffocates((state, world, pos) -> getOrDefault(object, "suffocates", true))
                        .breakByHand(getOrDefault(object, "break_by_hand", false))
                        .blockVision((state, world, pos) -> getOrDefault(object, "blocks_vision", true))
                        .solidBlock((state, world, pos) -> getOrDefault(object, "solid", true))
                        .dropsLike(block(getOrDefault(object, "block", "minecraft:air")));
                if (getOrDefault(object, "requires_tool", false)) settings.requiresTool();
                if (!getOrDefault(object, "opaque", true)) settings.nonOpaque();
                if (getOrDefault(object, "dynamic_bounds", false)) settings.dynamicBounds();
                if (getOrDefault(object, "ticks_randomly", false)) settings.ticksRandomly();
                if (getOrDefault(object, "drops_nothing", true)) settings.dropsNothing();
                if (getOrDefault(object, "is_air", false)) settings.air();
                if (getOrDefault(object, "breaks_instantly", false)) settings.breakInstantly();
                return settings;
            }
        }
        throw new JsonSyntaxException("Expected " + name + " to be block settings, was " + JsonHelper.getType(element));
    }

    public static Item.Settings getItemSettings(JsonElement element, String name) {
        if (element.isJsonObject()) {
            JsonObject object = JsonHelper.asObject(element, "item settings");
            Item.Settings settings = new FabricItemSettings();
            settings.maxCount(getOrDefault(object, "max_count", 64));
            settings.group(StringToObject.itemGroup(getOrDefault(object, "item_group", "search")));
            settings.rarity(StringToObject.rarity(getOrDefault(object, "rarity", "common")));
            if (getOrDefault(object, "fireproof", false)) settings.fireproof();
            if (object.has("food")) {
                settings.food(getFood(object.get("food"), "food"));
            }
        }
        throw new JsonSyntaxException("Expected " + name + " to be item settings, was " + JsonHelper.getType(element));
    }

    public static FoodComponent getFood(JsonElement element, String name) {
        if (element.isJsonObject()) {
            JsonObject object = JsonHelper.asObject(element, "item settings");
            FoodComponent.Builder food = new FoodComponent.Builder();
            return food.build();
        }
        throw new JsonSyntaxException("Expected " + name + " to be food, was " + JsonHelper.getType(element));
    }

    public static Material getMaterial(JsonElement element, String name) {
        if (element.isJsonObject()) {
            JsonObject object = JsonHelper.asObject(element, "block material");
            return new Material(
                    mapColor(getOrDefault(object, "map_color", "clear")),
                    getOrDefault(object, "liquid", false),
                    getOrDefault(object, "solid", true),
                    getOrDefault(object, "blocks_movement", true),
                    getOrDefault(object, "blocks_light", true),
                    getOrDefault(object, "burnable", false),
                    getOrDefault(object, "replaceable", false),
                    pistonBehavior(getOrDefault(object, "piston_behavior", "normal"))
            );
        }
        throw new JsonSyntaxException("Expected " + name + " to be block material, was " + JsonHelper.getType(element));
    }

    public static CustomBlock.AdvancedProperties getAdvancedProperties(JsonElement element, String name) {
        if (element.isJsonObject()) {
            JsonObject object = JsonHelper.asObject(element, "block advanced properties");
            return new CustomBlock.AdvancedProperties(
                    getOrDefault(object, "bounce_velocity_multiplier", 0f),
                    getOrDefault(object, "slide_velocity_multiplier", 0f),
                    getFallingBlockData(object, "gravity"),
                    new CustomBlock.AdvancedProperties.Shape(1f, 1f, 1f, 15f, 15f, 15f)
            );
        }
        throw new JsonSyntaxException("Expected " + name + " to be block advanced properties, was " + JsonHelper.getType(element));
    }

    public static CustomBlock.AdvancedProperties.FallingBlockData getFallingBlockData(JsonElement element, String name) {
        if (element.isJsonObject()) {
            JsonObject object = JsonHelper.asObject(element, "block falling block data");
            return new CustomBlock.AdvancedProperties.FallingBlockData(
                    getOrDefault(object, "affected_by_gravity", false),
                    getOrDefault(object, "delay", 2),
                    getOrDefault(object, "dust_color", 0)
            );
        }
        throw new JsonSyntaxException("Expected " + name + " to be block falling block data, was " + JsonHelper.getType(element));
    }

    public static CustomMaterial.CustomArmorMaterial getCustomArmorMaterial(JsonElement element, String name) {
        if (element.isJsonObject()) {
            JsonObject object = JsonHelper.asObject(element, "armor material");
            String id = JsonHelper.getString(object, "name");
            int durability = JsonHelper.getInt(object, "durability", 1);
            int protection = JsonHelper.getInt(object, "protection", 0);
            int enchantability = JsonHelper.getInt(object, "enchantability", 0);
            SoundEvent equipSound = StringToObject.equipSoundEvent(JsonHelper.getString(object, "equip_sound"));
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
}
