package creoii.custom.util;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.VillagerProfession;

public class StringToObject {
    public static Block block(String str) {
        return Registry.BLOCK.get(Identifier.tryParse(str));
    }

    public static Material material(String str) {
        return switch (str) {
            case "air" -> Material.AIR;
            case "structure_void" -> Material.STRUCTURE_VOID;
            case "portal" -> Material.PORTAL;
            case "carpet" -> Material.CARPET;
            case "plant" -> Material.PLANT;
            case "underwater_plant" -> Material.UNDERWATER_PLANT;
            case "replaceable_plant" -> Material.REPLACEABLE_PLANT;
            case "nether_shoots" -> Material.NETHER_SHOOTS;
            case "replaceable_underwater_plant" -> Material.REPLACEABLE_UNDERWATER_PLANT;
            case "water" -> Material.WATER;
            case "bubble_column" -> Material.BUBBLE_COLUMN;
            case "lava" -> Material.LAVA;
            case "snow_layer" -> Material.SNOW_LAYER;
            case "fire" -> Material.FIRE;
            case "decoration" -> Material.DECORATION;
            case "cobweb" -> Material.COBWEB;
            case "sculk" -> Material.SCULK;
            case "redstone_lamp" -> Material.REDSTONE_LAMP;
            case "organic_product" -> Material.ORGANIC_PRODUCT;
            case "soil" -> Material.SOIL;
            case "solid_organic" -> Material.SOLID_ORGANIC;
            case "dense_ice" -> Material.DENSE_ICE;
            case "aggregate" -> Material.AGGREGATE;
            case "sponge" -> Material.SPONGE;
            case "shulker_box" -> Material.SHULKER_BOX;
            case "wood" -> Material.WOOD;
            case "nether_wood" -> Material.NETHER_WOOD;
            case "bamboo_sapling" -> Material.BAMBOO_SAPLING;
            case "bamboo" -> Material.BAMBOO;
            case "wool" -> Material.WOOL;
            case "tnt" -> Material.TNT;
            case "leaves" -> Material.LEAVES;
            case "glass" -> Material.GLASS;
            case "ice" -> Material.ICE;
            case "cactus" -> Material.CACTUS;
            case "metal" -> Material.METAL;
            case "snow_block" -> Material.SNOW_BLOCK;
            case "repair_station" -> Material.REPAIR_STATION;
            case "barrier" -> Material.BARRIER;
            case "piston" -> Material.PISTON;
            case "moss_block" -> Material.MOSS_BLOCK;
            case "gourd" -> Material.GOURD;
            case "egg" -> Material.EGG;
            case "cake" -> Material.CAKE;
            case "amethyst" -> Material.AMETHYST;
            case "powder_snow" -> Material.POWDER_SNOW;
            default -> Material.STONE;
        };
    }

    public static MapColor mapColor(String str) {
        return switch (str) {
            case "black" -> MapColor.BLACK;
            case "pale_green" -> MapColor.PALE_GREEN;
            case "pale_yellow" -> MapColor.PALE_YELLOW;
            case "white_gray" -> MapColor.WHITE_GRAY;
            case "bright_red" -> MapColor.BRIGHT_RED;
            case "pale_purple" -> MapColor.PALE_PURPLE;
            case "iron_gray" -> MapColor.IRON_GRAY;
            case "dark_green" -> MapColor.DARK_GREEN;
            case "white" -> MapColor.WHITE;
            case "light_blue_gray" -> MapColor.LIGHT_BLUE_GRAY;
            case "dirt_brown" -> MapColor.DIRT_BROWN;
            case "stone_gray" -> MapColor.STONE_GRAY;
            case "water_blue" -> MapColor.WATER_BLUE;
            case "oak_tan" -> MapColor.OAK_TAN;
            case "off_white" -> MapColor.OFF_WHITE;
            case "orange" -> MapColor.ORANGE;
            case "magenta" -> MapColor.MAGENTA;
            case "light_blue" -> MapColor.LIGHT_BLUE;
            case "yellow" -> MapColor.YELLOW;
            case "lime" -> MapColor.LIME;
            case "pink" -> MapColor.PINK;
            case "gray" -> MapColor.GRAY;
            case "light_gray" -> MapColor.LIGHT_GRAY;
            case "cyan" -> MapColor.CYAN;
            case "purple" -> MapColor.PURPLE;
            case "blue" -> MapColor.BLUE;
            case "brown" -> MapColor.BROWN;
            case "green" -> MapColor.GREEN;
            case "red" -> MapColor.RED;
            case "gold" -> MapColor.GOLD;
            case "diamond_blue" -> MapColor.DIAMOND_BLUE;
            case "lapis_blue" -> MapColor.LAPIS_BLUE;
            case "emerald_green" -> MapColor.EMERALD_GREEN;
            case "spruce_brown" -> MapColor.SPRUCE_BROWN;
            case "dark_red" -> MapColor.DARK_RED;
            case "terracotta_white" -> MapColor.TERRACOTTA_WHITE;
            case "terracotta_orange" -> MapColor.TERRACOTTA_ORANGE;
            case "terracotta_magenta" -> MapColor.TERRACOTTA_MAGENTA;
            case "terracotta_light_blue" -> MapColor.TERRACOTTA_LIGHT_BLUE;
            case "terracotta_yellow" -> MapColor.TERRACOTTA_YELLOW;
            case "terracotta_lime" -> MapColor.TERRACOTTA_LIME;
            case "terracotta_pink" -> MapColor.TERRACOTTA_PINK;
            case "terracotta_gray" -> MapColor.TERRACOTTA_GRAY;
            case "terracotta_light_gray" -> MapColor.TERRACOTTA_LIGHT_GRAY;
            case "terracotta_cyan" -> MapColor.TERRACOTTA_CYAN;
            case "terracotta_purple" -> MapColor.TERRACOTTA_PURPLE;
            case "terracotta_blue" -> MapColor.TERRACOTTA_BLUE;
            case "terracotta_brown" -> MapColor.TERRACOTTA_BROWN;
            case "terracotta_green" -> MapColor.TERRACOTTA_GREEN;
            case "terracotta_red" -> MapColor.TERRACOTTA_RED;
            case "terracotta_black" -> MapColor.TERRACOTTA_BLACK;
            case "dull_red" -> MapColor.DULL_RED;
            case "dull_pink" -> MapColor.DULL_PINK;
            case "dark_crimson" -> MapColor.DARK_CRIMSON;
            case "teal" -> MapColor.TEAL;
            case "dark_aqua" -> MapColor.DARK_AQUA;
            case "dark_dull_pink" -> MapColor.DARK_DULL_PINK;
            case "bright_teal" -> MapColor.BRIGHT_TEAL;
            case "deepslate_gray" -> MapColor.DEEPSLATE_GRAY;
            case "raw_iron_pink" -> MapColor.RAW_IRON_PINK;
            case "lichen_green" -> MapColor.LICHEN_GREEN;
            default -> MapColor.CLEAR;
        };
    }

    public static PistonBehavior pistonBehavior(String str) {
        return switch (str) {
            case "destroy" -> PistonBehavior.DESTROY;
            case "block" -> PistonBehavior.BLOCK;
            case "ignore" -> PistonBehavior.IGNORE;
            case "push_only" -> PistonBehavior.PUSH_ONLY;
            default -> PistonBehavior.NORMAL;
        };
    }

    public static BlockSoundGroup blockSoundGroup(String str) {
        return switch (str) {
            case "wood" -> BlockSoundGroup.WOOD;
            case "gravel" -> BlockSoundGroup.GRAVEL;
            case "grass" -> BlockSoundGroup.GRASS;
            case "lily_pad" -> BlockSoundGroup.LILY_PAD;
            case "metal" -> BlockSoundGroup.METAL;
            case "glass" -> BlockSoundGroup.GLASS;
            case "wool" -> BlockSoundGroup.WOOL;
            case "sand" -> BlockSoundGroup.SAND;
            case "snow" -> BlockSoundGroup.SNOW;
            case "powder_snow" -> BlockSoundGroup.POWDER_SNOW;
            case "ladder" -> BlockSoundGroup.LADDER;
            case "anvil" -> BlockSoundGroup.ANVIL;
            case "slime" -> BlockSoundGroup.SLIME;
            case "honey" -> BlockSoundGroup.HONEY;
            case "wet_grass" -> BlockSoundGroup.WET_GRASS;
            case "coral" -> BlockSoundGroup.CORAL;
            case "bamboo" -> BlockSoundGroup.BAMBOO;
            case "bamboo_sapling" -> BlockSoundGroup.BAMBOO_SAPLING;
            case "scaffolding" -> BlockSoundGroup.SCAFFOLDING;
            case "sweet_berry_bush" -> BlockSoundGroup.SWEET_BERRY_BUSH;
            case "crop" -> BlockSoundGroup.CROP;
            case "stem" -> BlockSoundGroup.STEM;
            case "vine" -> BlockSoundGroup.VINE;
            case "nether_wart" -> BlockSoundGroup.NETHER_WART;
            case "lantern" -> BlockSoundGroup.LANTERN;
            case "nether_stem" -> BlockSoundGroup.NETHER_STEM;
            case "nylium" -> BlockSoundGroup.NYLIUM;
            case "fungus" -> BlockSoundGroup.FUNGUS;
            case "roots" -> BlockSoundGroup.ROOTS;
            case "shroomlight" -> BlockSoundGroup.SHROOMLIGHT;
            case "weeping_vines" -> BlockSoundGroup.WEEPING_VINES;
            case "weeping_vines_low_pitch" -> BlockSoundGroup.WEEPING_VINES_LOW_PITCH;
            case "soul_sand" -> BlockSoundGroup.SOUL_SAND;
            case "soul_soil" -> BlockSoundGroup.SOUL_SOIL;
            case "basalt" -> BlockSoundGroup.BASALT;
            case "wart_block" -> BlockSoundGroup.WART_BLOCK;
            case "netherrack" -> BlockSoundGroup.NETHERRACK;
            case "nether_bricks" -> BlockSoundGroup.NETHER_BRICKS;
            case "nether_sprouts" -> BlockSoundGroup.NETHER_SPROUTS;
            case "nether_ore" -> BlockSoundGroup.NETHER_ORE;
            case "bone" -> BlockSoundGroup.BONE;
            case "netherite" -> BlockSoundGroup.NETHERITE;
            case "ancient_debris" -> BlockSoundGroup.ANCIENT_DEBRIS;
            case "lodestone" -> BlockSoundGroup.LODESTONE;
            case "chain" -> BlockSoundGroup.CHAIN;
            case "nether_gold_ore" -> BlockSoundGroup.NETHER_GOLD_ORE;
            case "gilded_blackstone" -> BlockSoundGroup.GILDED_BLACKSTONE;
            case "candle" -> BlockSoundGroup.CANDLE;
            case "amethyst_block" -> BlockSoundGroup.AMETHYST_BLOCK;
            case "amethyst_cluster" -> BlockSoundGroup.AMETHYST_CLUSTER;
            case "small_amethyst_bud" -> BlockSoundGroup.SMALL_AMETHYST_BUD;
            case "medium_amethyst_bud" -> BlockSoundGroup.MEDIUM_AMETHYST_BUD;
            case "large_amethyst_bud" -> BlockSoundGroup.LARGE_AMETHYST_BUD;
            case "tuff" -> BlockSoundGroup.TUFF;
            case "calcite" -> BlockSoundGroup.CALCITE;
            case "dripstone_block" -> BlockSoundGroup.DRIPSTONE_BLOCK;
            case "pointed_dripstone" -> BlockSoundGroup.POINTED_DRIPSTONE;
            case "copper" -> BlockSoundGroup.COPPER;
            case "cave_vines" -> BlockSoundGroup.CAVE_VINES;
            case "spore_blossom" -> BlockSoundGroup.SPORE_BLOSSOM;
            case "azalea" -> BlockSoundGroup.AZALEA;
            case "flowering_azalea" -> BlockSoundGroup.FLOWERING_AZALEA;
            case "moss_carpet" -> BlockSoundGroup.MOSS_CARPET;
            case "moss_block" -> BlockSoundGroup.MOSS_BLOCK;
            case "big_dripleaf" -> BlockSoundGroup.BIG_DRIPLEAF;
            case "small_dripleaf" -> BlockSoundGroup.SMALL_DRIPLEAF;
            case "rooted_dirt" -> BlockSoundGroup.ROOTED_DIRT;
            case "hanging_roots" -> BlockSoundGroup.HANGING_ROOTS;
            case "azalea_leaves" -> BlockSoundGroup.AZALEA_LEAVES;
            case "skulk_sensor" -> BlockSoundGroup.SCULK_SENSOR;
            case "glow_lichen" -> BlockSoundGroup.GLOW_LICHEN;
            case "deepslate" -> BlockSoundGroup.DEEPSLATE;
            case "deepslate_bricks" -> BlockSoundGroup.DEEPSLATE_BRICKS;
            case "deepslate_tiles" -> BlockSoundGroup.DEEPSLATE_TILES;
            case "polished_deepslate" -> BlockSoundGroup.POLISHED_DEEPSLATE;
            default -> BlockSoundGroup.STONE;
        };
    }

    public static AbstractBlock.OffsetType offsetType(String str) {
        return switch (str) {
            case "xz" -> AbstractBlock.OffsetType.XZ;
            case "xyz" -> AbstractBlock.OffsetType.XYZ;
            default -> AbstractBlock.OffsetType.NONE;
        };
    }

    public static PathNodeType pathNodeType(String str) {
        return switch (str) {
            case "blocked" -> PathNodeType.BLOCKED;
            case "open" -> PathNodeType.OPEN;
            case "walkable_door" -> PathNodeType.WALKABLE_DOOR;
            case "trapdoor" -> PathNodeType.TRAPDOOR;
            case "powder_snow" -> PathNodeType.POWDER_SNOW;
            case "fence" -> PathNodeType.FENCE;
            case "lava" -> PathNodeType.LAVA;
            case "water" -> PathNodeType.WATER;
            case "water_border" -> PathNodeType.WATER_BORDER;
            case "rail" -> PathNodeType.RAIL;
            case "unpassable_rail" -> PathNodeType.UNPASSABLE_RAIL;
            case "danger_fire" -> PathNodeType.DANGER_FIRE;
            case "damage_fire" -> PathNodeType.DAMAGE_FIRE;
            case "danger_cactus" -> PathNodeType.DANGER_CACTUS;
            case "damage_cactus" -> PathNodeType.DAMAGE_CACTUS;
            case "danger_other" -> PathNodeType.DANGER_OTHER;
            case "damage_other" -> PathNodeType.DAMAGE_OTHER;
            case "door_open" -> PathNodeType.DOOR_OPEN;
            case "door_wood_closed" -> PathNodeType.DOOR_WOOD_CLOSED;
            case "door_iron_closed" -> PathNodeType.DOOR_IRON_CLOSED;
            case "breach" -> PathNodeType.BREACH;
            case "leaves" -> PathNodeType.LEAVES;
            case "sticky_honey" -> PathNodeType.STICKY_HONEY;
            case "cocoa" -> PathNodeType.COCOA;
            default -> PathNodeType.WALKABLE;
        };
    }

    public static RenderLayer renderLayer(String str) {
        return switch (str) {
            case "cutout" -> RenderLayer.getCutout();
            case "cutout_mipped" -> RenderLayer.getCutoutMipped();
            case "translucent" -> RenderLayer.getTranslucent();
            default -> RenderLayer.getSolid();
        };
    }

    public static VillagerProfession villagerProfession(String str) {
        return switch (str) {
            case "armorer" -> VillagerProfession.ARMORER;
            case "butcher" -> VillagerProfession.BUTCHER;
            case "cartographer" -> VillagerProfession.CARTOGRAPHER;
            case "cleric" -> VillagerProfession.CLERIC;
            case "farmer" -> VillagerProfession.FARMER;
            case "fisherman" -> VillagerProfession.FISHERMAN;
            case "fletcher" -> VillagerProfession.FLETCHER;
            case "leatherworker" -> VillagerProfession.LEATHERWORKER;
            case "librarian" -> VillagerProfession.LIBRARIAN;
            case "mason" -> VillagerProfession.MASON;
            case "shepherd" -> VillagerProfession.SHEPHERD;
            case "toolsmith" -> VillagerProfession.TOOLSMITH;
            case "weaponsmith" -> VillagerProfession.WEAPONSMITH;
            default -> VillagerProfession.NONE;
        };
    }

    public static ItemGroup itemGroup(String str) {
        for (ItemGroup group : ItemGroup.GROUPS) {
            System.out.println(group.getName());
            if (str.equals(group.getName())) return group;
        }
        return ItemGroup.SEARCH;
    }

    public static Rarity itemRarity(String str) {
        return switch (str) {
            case "uncommon" -> Rarity.UNCOMMON;
            case "rare" -> Rarity.RARE;
            case "epic" -> Rarity.EPIC;
            default -> Rarity.COMMON;
        };
    }

    public static Enchantment.Rarity enchantmentRarity(String str) {
        return switch (str) {
            case "uncommon" -> Enchantment.Rarity.UNCOMMON;
            case "rare" -> Enchantment.Rarity.RARE;
            case "very_rare" -> Enchantment.Rarity.VERY_RARE;
            default -> Enchantment.Rarity.COMMON;
        };
    }

    public static EnchantmentTarget enchantmentTarget(String str) {
        return switch (str) {
            case "armor" -> EnchantmentTarget.ARMOR;
            case "armor_feet" -> EnchantmentTarget.ARMOR_FEET;
            case "armor_legs" -> EnchantmentTarget.ARMOR_LEGS;
            case "armor_chest" -> EnchantmentTarget.ARMOR_CHEST;
            case "armor_head" -> EnchantmentTarget.ARMOR_HEAD;
            case "weapon" -> EnchantmentTarget.WEAPON;
            case "digger" -> EnchantmentTarget.DIGGER;
            case "fishing_rod" -> EnchantmentTarget.FISHING_ROD;
            case "trident" -> EnchantmentTarget.TRIDENT;
            case "bow" -> EnchantmentTarget.BOW;
            case "wearable" -> EnchantmentTarget.WEARABLE;
            case "crossbow" -> EnchantmentTarget.CROSSBOW;
            case "vanishable" -> EnchantmentTarget.VANISHABLE;
            default -> EnchantmentTarget.BREAKABLE;
        };
    }

    public static EquipmentSlot equipmentSlot(String str) {
        return switch (str) {
            case "offhand" -> EquipmentSlot.OFFHAND;
            case "head" -> EquipmentSlot.HEAD;
            case "chest" -> EquipmentSlot.CHEST;
            case "legs" -> EquipmentSlot.LEGS;
            case "feet" -> EquipmentSlot.FEET;
            default -> EquipmentSlot.MAINHAND;
        };
    }

    public static SoundEvent equipSoundEvent(String str) {
        return switch (str) {
            case "iron" -> SoundEvents.ITEM_ARMOR_EQUIP_IRON;
            case "chain" -> SoundEvents.ITEM_ARMOR_EQUIP_CHAIN;
            case "gold" -> SoundEvents.ITEM_ARMOR_EQUIP_GOLD;
            case "diamond" -> SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
            case "netherite" -> SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE;
            case "elytra" -> SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA;
            case "turtle" -> SoundEvents.ITEM_ARMOR_EQUIP_TURTLE;
            case "leather" -> SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
            default -> SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
        };
    }

    public static Hand hand(String str) {
        return str.equals("offhand") ? Hand.OFF_HAND : Hand.MAIN_HAND;
    }

    public static ActionResult actionResult(String str, boolean swingHand) {
        return switch (str) {
            case "consume_partial" -> ActionResult.CONSUME_PARTIAL;
            case "success" -> ActionResult.success(swingHand);
            case "fail" -> ActionResult.FAIL;
            case "consume" -> ActionResult.CONSUME;
            default -> ActionResult.PASS;
        };
    }
}
