package creoii.custom.util.tags;

import creoii.custom.Custom;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.entity.EntityType;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class EntityTypeTags {
    public static final Tag<EntityType<?>> ZOMBIES = TagFactory.ENTITY_TYPE.create(new Identifier(Custom.MOD_ID, "zombies"));
    public static final Tag<EntityType<?>> SPIDERS = TagFactory.ENTITY_TYPE.create(new Identifier(Custom.MOD_ID, "spiders"));
    public static final Tag<EntityType<?>> MILKABLES = TagFactory.ENTITY_TYPE.create(new Identifier(Custom.MOD_ID, "milkables"));
    public static final Tag<EntityType<?>> VEHICLES = TagFactory.ENTITY_TYPE.create(new Identifier(Custom.MOD_ID, "vehicles"));
    public static final Tag<EntityType<?>> PROJECTILES_PASS_THROUGH = TagFactory.ENTITY_TYPE.create(new Identifier(Custom.MOD_ID, "projectiles_pass_through"));
    public static final Tag<EntityType<?>> IMMOVABLE_BY_FLUIDS = TagFactory.ENTITY_TYPE.create(new Identifier(Custom.MOD_ID, "immovable_by_fluids"));
    public static final Tag<EntityType<?>> FIERY = TagFactory.ENTITY_TYPE.create(new Identifier(Custom.MOD_ID, "fiery"));
    public static final Tag<EntityType<?>> CACTUS_IMMUNE = TagFactory.ENTITY_TYPE.create(new Identifier(Custom.MOD_ID, "cactus_immune"));
    public static final Tag<EntityType<?>> BERRY_BUSH_IMMUNE = TagFactory.ENTITY_TYPE.create(new Identifier(Custom.MOD_ID, "berry_bush_immune"));
    public static final Tag<EntityType<?>> WALKS_ON_FLUIDS = TagFactory.ENTITY_TYPE.create(new Identifier(Custom.MOD_ID, "berry_bush_immune"));
    public static final Tag<EntityType<?>> GOAT_UNRAMMABLE = TagFactory.ENTITY_TYPE.create(new Identifier(Custom.MOD_ID, "goat_unrammable"));
    public static final Tag<EntityType<?>> DRIPSTONE_IMMUNE = TagFactory.ENTITY_TYPE.create(new Identifier(Custom.MOD_ID, "dripstone_immune"));
}