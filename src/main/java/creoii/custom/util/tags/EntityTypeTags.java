package creoii.custom.util.tags;

import creoii.custom.Custom;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class EntityTypeTags {
    public static final TagKey<EntityType<?>> ZOMBIES = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(Custom.NAMESPACE, "zombies"));
    public static final TagKey<EntityType<?>> SPIDERS = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(Custom.NAMESPACE, "spiders"));
    public static final TagKey<EntityType<?>> MILKABLES = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(Custom.NAMESPACE, "milkables"));
    public static final TagKey<EntityType<?>> VEHICLES = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(Custom.NAMESPACE, "vehicles"));
    public static final TagKey<EntityType<?>> PROJECTILES_PASS_THROUGH = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(Custom.NAMESPACE, "projectiles_pass_through"));
    public static final TagKey<EntityType<?>> IMMOVABLE_BY_FLUIDS = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(Custom.NAMESPACE, "immovable_by_fluids"));
    public static final TagKey<EntityType<?>> FIERY = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(Custom.NAMESPACE, "fiery"));
    public static final TagKey<EntityType<?>> CACTUS_IMMUNE = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(Custom.NAMESPACE, "cactus_immune"));
    public static final TagKey<EntityType<?>> BERRY_BUSH_IMMUNE = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(Custom.NAMESPACE, "berry_bush_immune"));
    public static final TagKey<EntityType<?>> WALKS_ON_FLUIDS = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(Custom.NAMESPACE, "berry_bush_immune"));
    public static final TagKey<EntityType<?>> GOAT_UNRAMMABLE = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(Custom.NAMESPACE, "goat_unrammable"));
    public static final TagKey<EntityType<?>> DRIPSTONE_IMMUNE = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(Custom.NAMESPACE, "dripstone_immune"));
    public static final TagKey<EntityType<?>> WOLF_PREY = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(Custom.NAMESPACE, "wolf_prey"));
}