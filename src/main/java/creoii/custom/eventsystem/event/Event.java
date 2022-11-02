package creoii.custom.eventsystem.event;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import creoii.custom.Custom;
import creoii.custom.data.Identifiable;
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
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class Event implements Identifiable {
    protected Condition[] conditions;
    protected Effect[] effects;

    public static Event register(Identifier id, Event event) {
        return Registry.register(Custom.EVENT, id, event);
    }

    @Nullable
    public static Event getEvent(JsonObject object, Identifier id) {
        return Custom.EVENT.get(id).getFromJson(object);
    }

    public Condition[] getConditions() {
        return conditions;
    }

    public Effect[] getEffects() {
        return effects;
    }

    @Override
    public Identifier getIdentifier() {
        return Custom.EVENT.getId(this);
    }

    public abstract Event getFromJson(JsonObject object);

    public static Condition[] getConditions(JsonObject object) {
        Condition[] conditions;
        if (JsonHelper.hasArray(object, "conditions")) {
            JsonArray array = JsonHelper.getArray(object, "conditions");
            conditions = new Condition[array.size()];
            for (int i = 0; i < conditions.length; ++i) {
                if (array.get(i).isJsonObject()) {
                    JsonObject eventObj = array.get(i).getAsJsonObject();
                    conditions[i] = Condition.getCondition(eventObj, Identifier.tryParse(eventObj.get("name").getAsString()));
                }
            }
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
                    effects[i] = Effect.getEffect(eventObj, Identifier.tryParse(eventObj.get("name").getAsString()));
                }
            }
        } else effects = new Effect[0];
        return effects;
    }

    public static Event findEvent(Event[] events, Event event) {
        for (Event event1 : events) {
            if (event1.getIdentifier().equals(event.getIdentifier())) return event1;
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
