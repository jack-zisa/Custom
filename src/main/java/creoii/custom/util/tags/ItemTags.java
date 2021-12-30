package creoii.custom.util.tags;

import creoii.custom.Custom;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class ItemTags {
    public static final Tag<Item> HOPPER_IMMUNE = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "hopper_immune"));
    public static final Tag<Item> EXPLOSION_IMMUNE = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "explosion_immune"));
    public static final Tag<Item> CACTUS_IMMUNE = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "cactus_immune"));
    public static final Tag<Item> FIREPROOF = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "fireproof"));
    public static final Tag<Item> UNFRAMEABLE = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "unframeable"));
    public static final Tag<Item> SHEARS_SHEEP = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "shears_sheep"));
    public static final Tag<Item> GLINTED = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "glinted"));
    public static final Tag<Item> HEAD_WEARABLES = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "head_wearables"));
    public static final Tag<Item> CHEST_WEARABLES = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "chest_wearables"));
    public static final Tag<Item> LEGS_WEARABLES = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "legs_wearables"));
    public static final Tag<Item> FEET_WEARABLES = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "feet_wearables"));
    public static final Tag<Item> FOX_HOLDABLE = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "fox_holdable"));
    public static final Tag<Item> COW_FOOD = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "cow_food"));
    public static final Tag<Item> SHEEP_FOOD = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "sheep_food"));
    public static final Tag<Item> CHICKEN_FOOD = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "chicken_food"));
    public static final Tag<Item> HORSE_FOOD = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "horse_food"));
    public static final Tag<Item> PIG_FOOD = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "pig_food"));
    public static final Tag<Item> TURTLE_FOOD = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "turtle_food"));
    public static final Tag<Item> STRIDER_FOOD = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "strider_food"));
    public static final Tag<Item> BEE_FOOD = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "bee_food"));
    public static final Tag<Item> LLAMA_FOOD = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "llama_food"));
    public static final Tag<Item> PANDA_FOOD = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "panda_food"));
    public static final Tag<Item> SHIELD_DISABLERS = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "shield_disablers"));
}