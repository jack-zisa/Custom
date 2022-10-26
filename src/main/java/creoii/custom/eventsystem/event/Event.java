package creoii.custom.eventsystem.event;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.effect.Effect;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class Event {
    public static final String RIGHT_CLICK = "right_click";
    public static final String LEFT_CLICK = "left_click";
    public static final String STEPPED_ON = "stepped_on";
    public static final String PROJECTILE_HIT = "projectile_hit";
    public static final String PLACE_BLOCK = "place_block";
    public static final String BREAK_BLOCK = "break_block";
    public static final String TARGET_DAMAGED = "target_damaged";
    public static final String USER_DAMAGED = "user_damaged";
    public static final String ENTITY_LANDS = "entity_lands";
    public static final String ENTITY_COLLISION = "entity_collision";
    public static final String NEIGHBOR_UPDATE = "neighbor_update";
    public static final String STOPPED_USING = "stopped_using";
    public static final String CRAFTED = "crafted";
    public static final String ITEM_DESPAWN = "item_despawn";
    public static final String RANDOM_TICK = "random_tick";
    public static final String STATUS_EFFECT_UPDATE = "status_effect_update";
    public static final String STATUS_EFFECT_APPLY = "status_effect_apply";
    public static final String STATUS_EFFECT_REMOVE = "status_effect_remove";

    private final String name;
    public final Condition[] conditions;
    public final Effect[] effects;

    public Event(String name, Condition[] conditions, Effect[] effects) {
        this.name = name;
        this.conditions = conditions;
        this.effects = effects;
    }

    public String getName() {
        return name;
    }

    public boolean isType(String type) {
        return this.name.equals(type);
    }

    public static Event getEvent(JsonObject object, String str) {
        return switch (str) {
            case RIGHT_CLICK -> RightClickEvent.getFromJson(object);
            case LEFT_CLICK -> LeftClickEvent.getFromJson(object);
            case STEPPED_ON -> SteppedOnEvent.getFromJson(object);
            case PROJECTILE_HIT -> ProjectileHitEvent.getFromJson(object);
            case PLACE_BLOCK -> PlaceBlockEvent.getFromJson(object);
            case BREAK_BLOCK -> BreakBlockEvent.getFromJson(object);
            case TARGET_DAMAGED -> TargetDamagedEvent.getFromJson(object);
            case USER_DAMAGED -> UserDamagedEvent.getFromJson(object);
            case ENTITY_LANDS -> EntityLandsEvent.getFromJson(object);
            case ENTITY_COLLISION -> EntityCollisionEvent.getFromJson(object);
            case NEIGHBOR_UPDATE -> NeighborUpdateEvent.getFromJson(object);
            case STOPPED_USING -> StoppedUsingEvent.getFromJson(object);
            case CRAFTED -> CraftedEvent.getFromJson(object);
            case ITEM_DESPAWN -> ItemDespawnEvent.getFromJson(object);
            case RANDOM_TICK -> RandomTickEvent.getFromJson(object);
            case STATUS_EFFECT_UPDATE -> StatusEffectUpdateEvent.getFromJson(object);
            case STATUS_EFFECT_APPLY -> StatusEffectApplyEvent.getFromJson(object);
            case STATUS_EFFECT_REMOVE -> StatusEffectRemoveEvent.getFromJson(object);
            default -> new NoEvent();
        };
    }

    public static Condition[] getConditions(JsonObject object) {
        Condition[] conditions;
        if (JsonHelper.hasArray(object, "conditions")) {
            JsonArray array = JsonHelper.getArray(object, "conditions");
            conditions = new Condition[array.size()];
            for (int i = 0; i < conditions.length; ++i) {
                if (array.get(i).isJsonObject()) {
                    JsonObject eventObj = array.get(i).getAsJsonObject();
                    conditions[i] = Condition.getCondition(eventObj, eventObj.get("name").getAsString());
                }            }
        } else conditions = new Condition[0];
        return conditions;
    }

    public static Effect[] getEffects(JsonObject object) {
        Effect[] effects;
        if (JsonHelper.hasArray(object, "effects")) {
            JsonArray array = JsonHelper.getArray(object, "effects");
            effects = new Effect[array.size()];
            for (int i = 0; i < effects.length; ++i) {
                if (array.get(i).isJsonObject()) {
                    JsonObject eventObj = array.get(i).getAsJsonObject();
                    effects[i] = Effect.getEffect(eventObj, eventObj.get("name").getAsString());
                }
            }
        } else effects = new Effect[0];
        return effects;
    }

    public static Event findEvent(Event[] events, String name) {
        for (Event event : events) {
            if (event.getName().equals(name)) return event;
        } return null;
    }

    public boolean applyBlockEvent(World world, BlockState state, BlockPos pos, @Nullable LivingEntity living, @Nullable Hand hand) {
        for (Condition condition : conditions) {
            if (!condition.testBlock(world, state, pos, living, hand)) return false;
        }

        for (Effect effect : effects) {
            effect.runBlock(world, state, pos, living, hand);
        }
        return true;
    }

    public boolean applyItemEvent(World world, ItemStack stack, BlockPos pos, PlayerEntity player, @Nullable Hand hand) {
        for (Condition condition : conditions) {
            if (!condition.testItem(world, stack, pos, player, hand)) return false;
        }

        for (Effect effect : effects) {
            effect.runItem(world, stack, pos, player, hand);
        }
        return true;
    }

    public boolean applyEntityEvent(Entity entity, PlayerEntity player, Hand hand) {
        for (Condition condition : conditions) {
            if (!condition.testEntity(entity, player, hand)) return false;
        }

        for (Effect effect : effects) {
            effect.runEntity(entity, player, hand);
        }
        return true;
    }

    public void applyEnchantmentEvent(Enchantment enchantment, Entity user, Entity target, int level) {
        for (Condition condition : conditions) {
            if (!condition.testEnchantment(enchantment, user, target, level)) return;
        }

        for (Effect effect : effects) {
            effect.runEnchantment(enchantment, user, target, level);
        }
    }

    public void applyStatusEffectEvent(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        for (Condition condition : conditions) {
            if (!condition.testStatusEffect(statusEffect, entity, amplifier)) return;
        }

        for (Effect effect : effects) {
            effect.runStatusEffect(statusEffect, entity, amplifier);
        }
    }
}
