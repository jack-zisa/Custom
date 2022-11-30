package creoii.custom.eventsystem.effect;

import creoii.custom.Custom;
import net.minecraft.util.Identifier;

public class Effects {
    public static AbstractEffect EMPTY;
    public static AbstractEffect SEND_MESSAGE;
    public static AbstractEffect EMIT_GAME_EVENT;
    public static AbstractEffect GENERATE_FEATURE;
    public static AbstractEffect GENERATE_STRUCTURE;
    public static AbstractEffect APPLY_STATUS_EFFECT;
    public static AbstractEffect COMPLETE_ADVANCEMENT;
    public static AbstractEffect PLAY_SOUND;
    public static AbstractEffect RUN_FUNCTION;
    public static AbstractEffect SPAWN_ENTITY;
    public static AbstractEffect SPAWN_PARTICLE;
    public static AbstractEffect WEIGHTED_LIST;
    public static AbstractEffect COMPOSITE;

    public static void register() {
        EMPTY = AbstractEffect.register(new Identifier(Custom.NAMESPACE, "empty"), new EmptyEffect());
        SEND_MESSAGE = AbstractEffect.register(new Identifier(Custom.NAMESPACE, "send_message"), new SendMessageEffect());
        EMIT_GAME_EVENT = AbstractEffect.register(new Identifier(Custom.NAMESPACE, "emit_game_event"), new EmitGameEventEffect());
        GENERATE_FEATURE = AbstractEffect.register(new Identifier(Custom.NAMESPACE, "generate_feature"), new GenerateFeatureEffect());
        GENERATE_STRUCTURE = AbstractEffect.register(new Identifier(Custom.NAMESPACE, "generate_structure"), new GenerateStructureEffect());
        APPLY_STATUS_EFFECT = AbstractEffect.register(new Identifier(Custom.NAMESPACE, "apply_status_effect"), new ApplyStatusEffectEffect());
        COMPLETE_ADVANCEMENT = AbstractEffect.register(new Identifier(Custom.NAMESPACE, "complete_advancement"), new CompleteAdvancementEffect());
        PLAY_SOUND = AbstractEffect.register(new Identifier(Custom.NAMESPACE, "play_sound"), new PlaySoundEffect());
        RUN_FUNCTION = AbstractEffect.register(new Identifier(Custom.NAMESPACE, "run_function"), new RunFunctionEffect());
        SPAWN_ENTITY = AbstractEffect.register(new Identifier(Custom.NAMESPACE, "spawn_entity"), new SpawnEntityEffect());
        SPAWN_PARTICLE = AbstractEffect.register(new Identifier(Custom.NAMESPACE, "spawn_particle"), new SpawnParticleEffect());
        WEIGHTED_LIST = AbstractEffect.register(new Identifier(Custom.NAMESPACE, "weighted_list"), new WeightedListEffect());
        COMPOSITE = AbstractEffect.register(new Identifier(Custom.NAMESPACE, "composite"), new CompositeEffect());
    }
}
