package creoii.custom.util.tags;

import creoii.custom.Custom;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemTags {
    public static final TagKey<Item> HOPPER_IMMUNE = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.NAMESPACE, "hopper_immune"));
    public static final TagKey<Item> EXPLOSION_IMMUNE = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.NAMESPACE, "explosion_immune"));
    public static final TagKey<Item> CACTUS_IMMUNE = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.NAMESPACE, "cactus_immune"));
    public static final TagKey<Item> FIREPROOF = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.NAMESPACE, "fireproof"));
    public static final TagKey<Item> UNFRAMEABLE = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.NAMESPACE, "unframeable"));
    public static final TagKey<Item> SHEARS_SHEEP = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.NAMESPACE, "shears_sheep"));
    public static final TagKey<Item> GLINTED = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.NAMESPACE, "glinted"));
    //public static final Tag<Item> HEAD_WEARABLES = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "head_wearables"));
    //public static final Tag<Item> CHEST_WEARABLES = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "chest_wearables"));
    //public static final Tag<Item> LEGS_WEARABLES = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "legs_wearables"));
    //public static final Tag<Item> FEET_WEARABLES = TagFactory.ITEM.create(new Identifier(Custom.MOD_ID, "feet_wearables"));
    public static final TagKey<Item> FOX_UNHOLDABLE = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.NAMESPACE, "fox_unholdable"));
    public static final TagKey<Item> COW_FOOD = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.NAMESPACE, "cow_food"));
    public static final TagKey<Item> SHEEP_FOOD = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.NAMESPACE, "sheep_food"));
    public static final TagKey<Item> CHICKEN_FOOD = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.NAMESPACE, "chicken_food"));
    public static final TagKey<Item> HORSE_FOOD = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.NAMESPACE, "horse_food"));
    public static final TagKey<Item> PIG_FOOD = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.NAMESPACE, "pig_food"));
    public static final TagKey<Item> TURTLE_FOOD = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.NAMESPACE, "turtle_food"));
    public static final TagKey<Item> STRIDER_FOOD = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.NAMESPACE, "strider_food"));
    public static final TagKey<Item> BEE_FOOD = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.NAMESPACE, "bee_food"));
    public static final TagKey<Item> LLAMA_FOOD = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.NAMESPACE, "llama_food"));
    public static final TagKey<Item> PANDA_FOOD = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.NAMESPACE, "panda_food"));
    public static final TagKey<Item> SHIELD_DISABLERS = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.NAMESPACE, "shield_disablers"));
    public static final TagKey<Item> LIGHTS_CREEPER_FUSE = TagKey.of(Registry.ITEM_KEY, new Identifier(Custom.NAMESPACE, "lights_creeper_fuse"));
}