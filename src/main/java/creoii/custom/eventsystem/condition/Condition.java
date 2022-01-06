package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class Condition {
    public static final String HOLDING_ITEM = "holding_item";
    public static final String IN_TAG = "in_tag";
    public static final String RANDOM_CHANCE = "random_chance";
    public static final String ENTITY_SNEAKING = "entity_sneaking";
    public static final String ENTITY_SPRINTING = "entity_sprinting";
    public static final String ENTITY_SWIMMING = "entity_swimming";
    public static final String BIOME_MATCHES = "biome_matches";
    public static final String DIFFICULTY_MATCHES = "difficulty_matches";
    public static final String HAS_ENCHANTMENT = "has_enchantment";
    public static final String HAS_STATUS_EFFECT = "has_status_effect";
    public static final String WEATHER_MATCHES = "weather_matches";
    public static final String GAMEMODE_MATCHES = "gamemode_matches";
    public static final String WITHIN_Y = "within_y";
    public static final String TIME_WITHIN = "time_within";
    public static final String PLAYER_LEVEL_WITHIN = "player_level_within";

    private final String type;

    public Condition(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static Condition getCondition(JsonObject object, String str) {
        return switch (str) {
            case HOLDING_ITEM -> HoldingItemCondition.getFromJson(object);
            case IN_TAG -> InTagCondition.getFromJson(object);
            case RANDOM_CHANCE -> RandomChanceCondition.getFromJson(object);
            case ENTITY_SNEAKING -> EntitySneakingCondition.getFromJson(object);
            case ENTITY_SPRINTING -> EntitySprintingCondition.getFromJson(object);
            case ENTITY_SWIMMING -> EntitySwimmingCondition.getFromJson(object);
            case BIOME_MATCHES -> BiomeMatchesCondition.getFromJson(object);
            case DIFFICULTY_MATCHES -> DifficultyMatchesCondition.getFromJson(object);
            case HAS_ENCHANTMENT -> HasEnchantmentCondition.getFromJson(object);
            case HAS_STATUS_EFFECT -> HasStatusEffectCondition.getFromJson(object);
            case WEATHER_MATCHES -> WeatherMatchesCondition.getFromJson(object);
            case WITHIN_Y -> WithinYCondition.getFromJson(object);
            case TIME_WITHIN -> TimeWithinCondition.getFromJson(object);
            case PLAYER_LEVEL_WITHIN -> PlayerLevelWithinCondition.getFromJson(object);
            default -> new NoCondition();
        };
    }

    public abstract boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand);
    public abstract boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand);
    public abstract boolean testEntity(Entity entity, PlayerEntity player, Hand hand);
    public abstract boolean testEnchantment(Entity user, Entity target, int level);
    public abstract boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier);
}
