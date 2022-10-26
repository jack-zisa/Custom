package creoii.custom.util.tags;

import creoii.custom.Custom;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemTags {
    public static final TagKey<Item> HOPPER_IMMUNE = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.MOD_ID, "hopper_immune"));
    public static final TagKey<Item> EXPLOSION_IMMUNE = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.MOD_ID, "explosion_immune"));
    public static final TagKey<Item> CACTUS_IMMUNE = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.MOD_ID, "cactus_immune"));
    public static final TagKey<Item> FIREPROOF = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.MOD_ID, "fireproof"));
    public static final TagKey<Item> UNFRAMEABLE = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.MOD_ID, "unframeable"));
    public static final TagKey<Item> SHEARS_SHEEP = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.MOD_ID, "shears_sheep"));
    public static final TagKey<Item> GLINTED = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.MOD_ID, "glinted"));
    //public static final Tag<Item> HEAD_WEARABLES = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "head_wearables"));
    //public static final Tag<Item> CHEST_WEARABLES = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "chest_wearables"));
    //public static final Tag<Item> LEGS_WEARABLES = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "legs_wearables"));
    //public static final Tag<Item> FEET_WEARABLES = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "feet_wearables"));
    public static final TagKey<Item> FOX_UNHOLDABLE = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.MOD_ID, "fox_unholdable"));
    public static final TagKey<Item> COW_FOOD = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.MOD_ID, "cow_food"));
    public static final TagKey<Item> SHEEP_FOOD = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.MOD_ID, "sheep_food"));
    public static final TagKey<Item> CHICKEN_FOOD = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.MOD_ID, "chicken_food"));
    public static final TagKey<Item> HORSE_FOOD = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.MOD_ID, "horse_food"));
    public static final TagKey<Item> PIG_FOOD = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.MOD_ID, "pig_food"));
    public static final TagKey<Item> TURTLE_FOOD = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.MOD_ID, "turtle_food"));
    public static final TagKey<Item> STRIDER_FOOD = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.MOD_ID, "strider_food"));
    public static final TagKey<Item> BEE_FOOD = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.MOD_ID, "bee_food"));
    public static final TagKey<Item> LLAMA_FOOD = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.MOD_ID, "llama_food"));
    public static final TagKey<Item> PANDA_FOOD = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.MOD_ID, "panda_food"));
    public static final TagKey<Item> SHIELD_DISABLERS = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.MOD_ID, "shield_disablers"));
    public static final TagKey<Item> LIGHTS_CREEPER_FUSE = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.MOD_ID, "lights_creeper_fuse"));
}