package creoii.custom.eventsystem.condition;

import creoii.custom.Custom;
import net.minecraft.util.Identifier;

public class Conditions {
    public static Condition EMPTY;
    public static Condition HOLDING_ITEM;
    public static Condition IN_TAG;
    public static Condition RANDOM_CHANCE;
    public static Condition ENTITY_SNEAKING;
    public static Condition ENTITY_SPRINTING;
    public static Condition ENTITY_SWIMMING;
    public static Condition ENTITY_JUMPING;
    public static Condition ENTITY_CRAWLING;
    public static Condition BIOME_MATCHES;
    public static Condition DIFFICULTY_MATCHES;
    public static Condition BLOCK_MATCHES;
    public static Condition HAS_ENCHANTMENT;
    public static Condition HAS_STATUS_EFFECT;
    public static Condition WEATHER_MATCHES;
    public static Condition GAMEMODE_MATCHES;
    public static Condition WITHIN_Y;
    public static Condition TIME_WITHIN;
    public static Condition PLAYER_LEVEL_WITHIN;
    public static Condition LIGHT_LEVEL_WITHIN;
    public static Condition COMPOSITE;
    public static Condition INVERT;

    public static void register() {
        EMPTY = Condition.register(new Identifier(Custom.NAMESPACE, "empty"), new EmptyCondition());
        HOLDING_ITEM = Condition.register(new Identifier(Custom.NAMESPACE, "holding_item"), new HoldingItemCondition());
        IN_TAG = Condition.register(new Identifier(Custom.NAMESPACE, "in_tag"), new InTagCondition());
        RANDOM_CHANCE = Condition.register(new Identifier(Custom.NAMESPACE, "random_chance"), new RandomChanceCondition());
        ENTITY_SNEAKING = Condition.register(new Identifier(Custom.NAMESPACE, "entity_sneaking"), new EntitySneakingCondition());
        ENTITY_SPRINTING = Condition.register(new Identifier(Custom.NAMESPACE, "entity_sprinting"), new EntitySprintingCondition());
        ENTITY_SWIMMING = Condition.register(new Identifier(Custom.NAMESPACE, "entity_swimming"), new EntitySwimmingCondition());
        ENTITY_JUMPING = Condition.register(new Identifier(Custom.NAMESPACE, "entity_jumping"), new EntityJumpingCondition());
        ENTITY_CRAWLING = Condition.register(new Identifier(Custom.NAMESPACE, "entity_crawling"), new EntityCrawlingCondition());
        BIOME_MATCHES = Condition.register(new Identifier(Custom.NAMESPACE, "biome_matches"), new BiomeMatchesCondition());
        DIFFICULTY_MATCHES = Condition.register(new Identifier(Custom.NAMESPACE, "difficulty_matches"), new DifficultyMatchesCondition());
        BLOCK_MATCHES = Condition.register(new Identifier(Custom.NAMESPACE, "block_matches"), new BlockMatchesCondition());
        HAS_ENCHANTMENT = Condition.register(new Identifier(Custom.NAMESPACE, "has_enchantment"), new HasEnchantmentCondition());
        HAS_STATUS_EFFECT = Condition.register(new Identifier(Custom.NAMESPACE, "has_status_effect"), new HasStatusEffectCondition());
        WEATHER_MATCHES = Condition.register(new Identifier(Custom.NAMESPACE, "weather_matches"), new WeatherMatchesCondition());
        GAMEMODE_MATCHES = Condition.register(new Identifier(Custom.NAMESPACE, "gamemode_matches"), new GameModeMatchesCondition());
        WITHIN_Y = Condition.register(new Identifier(Custom.NAMESPACE, "within_y"), new WithinYCondition());
        TIME_WITHIN = Condition.register(new Identifier(Custom.NAMESPACE, "time_within"), new TimeWithinCondition());
        PLAYER_LEVEL_WITHIN = Condition.register(new Identifier(Custom.NAMESPACE, "player_level_within"), new PlayerLevelWithinCondition());
        LIGHT_LEVEL_WITHIN = Condition.register(new Identifier(Custom.NAMESPACE, "light_level_within"), new LightLevelWithinCondition());
        COMPOSITE = Condition.register(new Identifier(Custom.NAMESPACE, "composite"), new CompositeCondition());
        INVERT = Condition.register(new Identifier(Custom.NAMESPACE, "invert"), new InvertCondition());
    }
}
