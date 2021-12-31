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
import net.minecraft.entity.EntityType;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

import static creoii.custom.util.StringToObject.*;

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
                    material = material(JsonHelper.getString(object, "material", "stone"));
                } else material = Material.STONE;

                MapColor mapColor = mapColor(JsonHelper.getString(object, "map_color", "clear"));
                AbstractBlock.Settings settings = FabricBlockSettings.of(material, mapColor)
                        .sounds(blockSoundGroup(JsonHelper.getString(object, "sound_group", "stone")))
                        .collidable(JsonHelper.getBoolean(object, "collidable", true))
                        .luminance((state) -> JsonHelper.getInt(object, "luminance", 0))
                        .strength(
                                JsonHelper.getFloat(object, "hardness", 0f),
                                JsonHelper.getFloat(object, "blast_resistance", 0f)
                        )
                        .emissiveLighting((state, world, pos) -> JsonHelper.getBoolean(object, "emissive", false))
                        .postProcess((state, world, pos) -> JsonHelper.getBoolean(object, "post_process", false))
                        .slipperiness(JsonHelper.getFloat(object, "slipperiness", .6f))
                        .velocityMultiplier(JsonHelper.getFloat(object, "velocity_multiplier", 1f))
                        .jumpVelocityMultiplier(JsonHelper.getFloat(object, "jump_velocity_multiplier", 1f))
                        .suffocates((state, world, pos) -> JsonHelper.getBoolean(object, "suffocates", true))
                        .breakByHand(JsonHelper.getBoolean(object, "break_by_hand", false))
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
            settings.group(itemGroup(JsonHelper.getString(object, "item_group", "search")));
            settings.rarity(itemRarity(JsonHelper.getString(object, "rarity", "common")));
            if (JsonHelper.getBoolean(object, "fireproof", false)) settings.fireproof();
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
                    mapColor(JsonHelper.getString(object, "map_color", "clear")),
                    JsonHelper.getBoolean(object, "liquid", false),
                    JsonHelper.getBoolean(object, "solid", true),
                    JsonHelper.getBoolean(object, "blocks_movement", true),
                    JsonHelper.getBoolean(object, "blocks_light", true),
                    JsonHelper.getBoolean(object, "burnable", false),
                    JsonHelper.getBoolean(object, "replaceable", false),
                    pistonBehavior(JsonHelper.getString(object, "piston_behavior", "normal"))
            );
        }
        throw new JsonSyntaxException("Expected " + name + " to be block material, was " + JsonHelper.getType(element));
    }

    public static CustomBlock.FallingBlockData getFallingBlockData(JsonElement element, String name) {
        if (element.isJsonObject()) {
            JsonObject object = JsonHelper.asObject(element, "block falling block data");
            return new CustomBlock.FallingBlockData(
                    JsonHelper.getBoolean(object, "affected_by_gravity", false),
                    JsonHelper.getInt(object, "delay", 2),
                    JsonHelper.getInt(object, "dust_color", 0)
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
}
