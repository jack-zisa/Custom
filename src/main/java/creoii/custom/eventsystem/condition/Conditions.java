package creoii.custom.eventsystem.condition;

import creoii.custom.Custom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.GameMode;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class Conditions {
    public static Condition<?> EMPTY;
    public static Condition<?> HOLDING_ITEM;
    public static Condition<?> IN_TAG;
    public static Condition<?> RANDOM_CHANCE;
    public static Condition<?> ENTITY_SNEAKING;
    public static Condition<?> ENTITY_SPRINTING;
    public static Condition<?> ENTITY_SWIMMING;
    public static Condition<?> ENTITY_JUMPING;
    public static Condition<?> ENTITY_CRAWLING;
    public static Condition<?> BIOME_MATCHES;
    public static Condition<?> DIFFICULTY_MATCHES;
    public static Condition<?> BLOCK_MATCHES;
    public static Condition<?> HAS_ENCHANTMENT;
    public static Condition<?> HAS_STATUS_EFFECT;
    public static Condition<?> WEATHER_MATCHES;
    public static Condition<?> GAMEMODE_MATCHES;
    public static Condition<?> WITHIN_Y;
    public static Condition<?> TIME_WITHIN;
    public static Condition<?> PLAYER_LEVEL_WITHIN;
    public static Condition<?> LIGHT_LEVEL_WITHIN;
    public static Condition<?> COMPOSITE;
    public static Condition<?> INVERT;

    public static void register() {
        EMPTY = Condition.register(new Identifier(Custom.NAMESPACE, "empty"), new EmptyCondition());
        HOLDING_ITEM = Condition.register(new Identifier(Custom.NAMESPACE, "holding_item"), new HoldingItemCondition());
        IN_TAG = Condition.register(new Identifier(Custom.NAMESPACE, "in_tag"), new InTagCondition<>());
        RANDOM_CHANCE = Condition.register(new Identifier(Custom.NAMESPACE, "random_chance"), new RandomChanceCondition());
        ENTITY_SNEAKING = Condition.register(new Identifier(Custom.NAMESPACE, "entity_sneaking"), new EntityCondition(Entity::isSneaking));
        ENTITY_SPRINTING = Condition.register(new Identifier(Custom.NAMESPACE, "entity_sprinting"), new EntityCondition(Entity::isSprinting));
        ENTITY_SWIMMING = Condition.register(new Identifier(Custom.NAMESPACE, "entity_swimming"), new EntityCondition(Entity::isSwimming));
        ENTITY_CRAWLING = Condition.register(new Identifier(Custom.NAMESPACE, "entity_crawling"), new EntityCondition(Entity::isCrawling));
        ENTITY_JUMPING = Condition.register(new Identifier(Custom.NAMESPACE, "entity_jumping"), new EntityCondition(entity -> {
            if (entity instanceof LivingEntity livingEntity)
                return livingEntity.jumping;
            else
                return false;
        }));
        BIOME_MATCHES = Condition.register(new Identifier(Custom.NAMESPACE, "biome_matches"), new WorldMatchingAtCondition<>(World::getBiome));
        DIFFICULTY_MATCHES = Condition.register(new Identifier(Custom.NAMESPACE, "difficulty_matches"), new MatchingCondition<>(World::getDifficulty));
        BLOCK_MATCHES = Condition.register(new Identifier(Custom.NAMESPACE, "block_matches"), new WorldMatchingAtCondition<>(World::getBlockState));
        HAS_ENCHANTMENT = Condition.register(new Identifier(Custom.NAMESPACE, "has_enchantment"), new HasEnchantmentCondition());
        HAS_STATUS_EFFECT = Condition.register(new Identifier(Custom.NAMESPACE, "has_status_effect"), new HasStatusEffectCondition());
        WEATHER_MATCHES = Condition.register(new Identifier(Custom.NAMESPACE, "weather_matches"), new WeatherMatchesCondition());
        GAMEMODE_MATCHES = Condition.register(new Identifier(Custom.NAMESPACE, "gamemode_matches"), new MatchingCondition<ServerPlayerEntity, GameMode>(playerEntity -> playerEntity.interactionManager.getGameMode()));
        WITHIN_Y = Condition.register(new Identifier(Custom.NAMESPACE, "within_y"), new WithinCondition<>(BlockPos::getY));
        TIME_WITHIN = Condition.register(new Identifier(Custom.NAMESPACE, "time_within"), new WithinCondition<>(World::getTime));
        PLAYER_LEVEL_WITHIN = Condition.register(new Identifier(Custom.NAMESPACE, "player_level_within"), new WithinCondition<PlayerEntity, Integer>(playerEntity -> playerEntity.experienceLevel));
        LIGHT_LEVEL_WITHIN = Condition.register(new Identifier(Custom.NAMESPACE, "light_level_within"), new TriWithinCondition<World, LightType, BlockPos, Integer>(BlockRenderView::getLightLevel));
        COMPOSITE = Condition.register(new Identifier(Custom.NAMESPACE, "composite"), new CompositeCondition<>());
        INVERT = Condition.register(new Identifier(Custom.NAMESPACE, "invert"), new InvertCondition<>());
    }
}
