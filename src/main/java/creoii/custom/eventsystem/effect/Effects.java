package creoii.custom.eventsystem.effect;

import creoii.custom.Custom;
import net.minecraft.util.Identifier;

public class Effects {
    public static Effect EMPTY;
    public static Effect DROP_ITEM;
    public static Effect SPAWN_ENTITY;
    public static Effect HEAL;
    public static Effect APPLY_STATUS_EFFECT;
    public static Effect DESTROY;
    public static Effect ADD_VELOCITY;
    public static Effect SEND_MESSAGE;
    public static Effect GENERATE_FEATURE;
    public static Effect GENERATE_STRUCTURE;
    public static Effect WEIGHTED_LIST;
    public static Effect EMIT_GAME_EVENT;
    public static Effect GIVE_EXPERIENCE;
    public static Effect SET_SPAWN;
    public static Effect SET_BLOCK;
    public static Effect SET_ON_FIRE;

    public static void register() {
        EMPTY = Effect.register(new Identifier(Custom.NAMESPACE, "empty"), new EmptyEffect());
        DROP_ITEM = Effect.register(new Identifier(Custom.NAMESPACE, "drop_item"), new DropItemEffect());
        SPAWN_ENTITY = Effect.register(new Identifier(Custom.NAMESPACE, "spawn_entity"), new SpawnEntityEffect());
        HEAL = Effect.register(new Identifier(Custom.NAMESPACE, "heal"), new HealEffect());
        APPLY_STATUS_EFFECT = Effect.register(new Identifier(Custom.NAMESPACE, "apply_status_effect"), new ApplyStatusEffectEffect());
        DESTROY = Effect.register(new Identifier(Custom.NAMESPACE, "destroy"), new DestroyEffect());
        ADD_VELOCITY = Effect.register(new Identifier(Custom.NAMESPACE, "add_velocity"), new AddVelocityEffect());
        SEND_MESSAGE = Effect.register(new Identifier(Custom.NAMESPACE, "send_message"), new SendMessageEffect());
        GENERATE_FEATURE = Effect.register(new Identifier(Custom.NAMESPACE, "generate_feature"), new GenerateFeatureEffect());
        GENERATE_STRUCTURE = Effect.register(new Identifier(Custom.NAMESPACE, "generate_structure"), new GenerateStructureEffect());
        WEIGHTED_LIST = Effect.register(new Identifier(Custom.NAMESPACE, "weighted_list"), new WeightedListEffect());
        EMIT_GAME_EVENT = Effect.register(new Identifier(Custom.NAMESPACE, "emit_game_event"), new EmitGameEventEffect());
        GIVE_EXPERIENCE = Effect.register(new Identifier(Custom.NAMESPACE, "give_experience"), new GiveExperienceEffect());
        SET_SPAWN = Effect.register(new Identifier(Custom.NAMESPACE, "set_spawnpoint"), new SetSpawnPointEffect());
        SET_BLOCK = Effect.register(new Identifier(Custom.NAMESPACE, "set_block"), new SetBlockEffect());
        SET_ON_FIRE = Effect.register(new Identifier(Custom.NAMESPACE, "set_on_fire"), new SetOnFireEffect());
    }
}