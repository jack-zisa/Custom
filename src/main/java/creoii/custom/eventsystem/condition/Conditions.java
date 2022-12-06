package creoii.custom.eventsystem.condition;

import creoii.custom.Custom;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class Conditions {
    public static Condition EMPTY;
    public static Condition COMPOSITE;
    public static Condition NEGATE;
    public static Condition WEIGHTED_LIST;
    public static Condition RANDOM_CHANCE;
    public static Condition IS_DAY;
    public static Condition IS_NIGHT;
    public static Condition IS_RAINING;
    public static Condition IS_THUNDERING;
    public static Condition IS_DEBUG;
    public static Condition ENTITY_SNEAKING;
    public static Condition ENTITY_SPRINTING;
    public static Condition ENTITY_SWIMMING;
    public static Condition ENTITY_CRAWLING;
    public static Condition ENTITY_JUMPING;
    public static Condition DIMENSION_MATCHES;
    public static Condition BIOME_MATCHES;
    public static Condition GAMEMODE_MATCHES;
    public static Condition DIFFICULTY_MATCHES;
    public static Condition WEATHER_MATCHES;
    public static Condition MOON_PHASE_MATCHES;
    public static Condition LIGHT_LEVEL_WITHIN;
    public static Condition PLAYER_LEVEL_WITHIN;
    public static Condition TIME_WITHIN;
    public static Condition Y_LEVEL_WITHIN;
    public static Condition HAS_STATUS_EFFECT;
    public static Condition HAS_ENCHANTMENT;
    public static Condition IN_TAG;
    public static Condition HOLDING_ITEM;

    public static void register() {
        EMPTY = Condition.register(new Identifier(Custom.NAMESPACE, "empty"), new EmptyCondition());
        COMPOSITE = Condition.register(new Identifier(Custom.NAMESPACE, "composite"), new CompositeCondition());
        NEGATE = Condition.register(new Identifier(Custom.NAMESPACE, "negate"), new NegateCondition());
        WEIGHTED_LIST = Condition.register(new Identifier(Custom.NAMESPACE, "weighted_list"), new WeightedListCondition());
        RANDOM_CHANCE = Condition.register(new Identifier(Custom.NAMESPACE, "random_chance"), new RandomChanceCondition());
        IS_DAY = Condition.register(new Identifier(Custom.NAMESPACE, "is_day"), new WorldCondition(World::isDay));
        IS_NIGHT = Condition.register(new Identifier(Custom.NAMESPACE, "is_night"), new WorldCondition(World::isNight));
        IS_RAINING = Condition.register(new Identifier(Custom.NAMESPACE, "is_raining"), new WorldCondition(World::isRaining));
        IS_THUNDERING = Condition.register(new Identifier(Custom.NAMESPACE, "is_thundering"), new WorldCondition(World::isThundering));
        IS_DEBUG = Condition.register(new Identifier(Custom.NAMESPACE, "is_debug"), new WorldCondition(World::isDebugWorld));
        ENTITY_SNEAKING = Condition.register(new Identifier(Custom.NAMESPACE, "entity_sneaking"), new EntityCondition(Entity::isSneaking));
        ENTITY_SPRINTING = Condition.register(new Identifier(Custom.NAMESPACE, "entity_sprinting"), new EntityCondition(Entity::isSprinting));
        ENTITY_SWIMMING = Condition.register(new Identifier(Custom.NAMESPACE, "entity_swimming"), new EntityCondition(Entity::isSwimming));
        ENTITY_CRAWLING = Condition.register(new Identifier(Custom.NAMESPACE, "entity_crawling"), new EntityCondition(Entity::isCrawling));
        ENTITY_JUMPING = Condition.register(new Identifier(Custom.NAMESPACE, "entity_jumping"), new LivingEntityCondition(livingEntity -> livingEntity.jumping));
        DIMENSION_MATCHES = Condition.register(new Identifier(Custom.NAMESPACE, "dimension_matches"), new DimensionMatchesCondition());
        BIOME_MATCHES = Condition.register(new Identifier(Custom.NAMESPACE, "biome_matches"), new BiomeMatchesCondition());
        GAMEMODE_MATCHES = Condition.register(new Identifier(Custom.NAMESPACE, "gamemode_matches"), new GameModeMatchesCondition());
        DIFFICULTY_MATCHES = Condition.register(new Identifier(Custom.NAMESPACE, "difficulty_matches"), new DifficultyMatchesCondition());
        WEATHER_MATCHES = Condition.register(new Identifier(Custom.NAMESPACE, "weather_matches"), new WeatherMatchesCondition());
        MOON_PHASE_MATCHES = Condition.register(new Identifier(Custom.NAMESPACE, "moon_phase_matches"), new MoonPhaseMatches());
        LIGHT_LEVEL_WITHIN = Condition.register(new Identifier(Custom.NAMESPACE, "light_level_within"), new LightLevelWithinCondition());
        PLAYER_LEVEL_WITHIN = Condition.register(new Identifier(Custom.NAMESPACE, "player_level_within"), new PlayerLevelWithinCondition());
        TIME_WITHIN = Condition.register(new Identifier(Custom.NAMESPACE, "time_within"), new TimeWithinCondition());
        Y_LEVEL_WITHIN = Condition.register(new Identifier(Custom.NAMESPACE, "y_level_within"), new YLevelWithinCondition());
        HAS_STATUS_EFFECT = Condition.register(new Identifier(Custom.NAMESPACE, "has_status_effect"), new HasStatusEffectCondition());
        HAS_ENCHANTMENT = Condition.register(new Identifier(Custom.NAMESPACE, "has_enchantment"), new HasEnchantmentCondition());
        HOLDING_ITEM = Condition.register(new Identifier(Custom.NAMESPACE, "holding_item"), new HoldingItemCondition());
    }
}
