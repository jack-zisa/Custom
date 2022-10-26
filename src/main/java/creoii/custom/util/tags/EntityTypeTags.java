package creoii.custom.util.tags;

import creoii.custom.Custom;
import net.minecraft.entity.EntityType;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityTypeTags {
    public static final TagKey<EntityType<?>> ZOMBIES = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(Custom.MOD_ID, "zombies"));
    public static final TagKey<EntityType<?>> SPIDERS = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(Custom.MOD_ID, "spiders"));
    public static final TagKey<EntityType<?>> MILKABLES = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(Custom.MOD_ID, "milkables"));
    public static final TagKey<EntityType<?>> VEHICLES = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(Custom.MOD_ID, "vehicles"));
    public static final TagKey<EntityType<?>> PROJECTILES_PASS_THROUGH = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(Custom.MOD_ID, "projectiles_pass_through"));
    public static final TagKey<EntityType<?>> IMMOVABLE_BY_FLUIDS = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(Custom.MOD_ID, "immovable_by_fluids"));
    public static final TagKey<EntityType<?>> FIERY = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(Custom.MOD_ID, "fiery"));
    public static final TagKey<EntityType<?>> CACTUS_IMMUNE = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(Custom.MOD_ID, "cactus_immune"));
    public static final TagKey<EntityType<?>> BERRY_BUSH_IMMUNE = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(Custom.MOD_ID, "berry_bush_immune"));
    public static final TagKey<EntityType<?>> WALKS_ON_FLUIDS = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(Custom.MOD_ID, "berry_bush_immune"));
    public static final TagKey<EntityType<?>> GOAT_UNRAMMABLE = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(Custom.MOD_ID, "goat_unrammable"));
    public static final TagKey<EntityType<?>> DRIPSTONE_IMMUNE = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(Custom.MOD_ID, "dripstone_immune"));
    public static final TagKey<EntityType<?>> WOLF_PREY = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(Custom.MOD_ID, "wolf_prey"));
}