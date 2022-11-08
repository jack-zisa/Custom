package creoii.custom.eventsystem.event;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.effect.Effect;
import creoii.custom.eventsystem.parameter.EventParameter;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BasicEvent extends AbstractEvent {
    public BasicEvent withValues(Condition<?>[] conditions, Effect[] effects) {
        this.conditions = conditions;
        this.effects = effects;
        return this;
    }

    public BasicEvent getFromJson(JsonObject object) {
        Condition<?>[] conditions = BasicEvent.getConditions(object);
        Effect[] effects = BasicEvent.getEffects(object);
        return withValues(conditions, effects);
    }

    @Override
    public EventParameter[] getParameters() {
        return new EventParameter[0];
    }

    public boolean apply() {
        boolean applied = true;
        for (Condition<?> condition : conditions) {
            if (!condition.validate(getParameters())) {
                if (!condition.test(getParameters())) applied = false;
            }
        }

        for (Effect effect : effects) {
            effect.run(getParameters());
        }
        return applied;
    }

    public boolean applyBlockEvent(World world, BlockState state, BlockPos pos, @Nullable LivingEntity living, @Nullable Hand hand) {
        for (Condition<?> condition : conditions) {
            if (!condition.testBlock(world, state, pos, living, hand)) return false;
        }

        for (Effect effect : effects) {
            effect.runBlock(world, state, pos, living, hand);
        }
        return true;
    }

    public boolean applyItemEvent(World world, ItemStack stack, BlockPos pos, PlayerEntity player, @Nullable Hand hand) {
        for (Condition<?> condition : conditions) {
            if (!condition.testItem(world, stack, pos, player, hand)) return false;
        }

        for (Effect effect : effects) {
            effect.runItem(world, stack, pos, player, hand);
        }
        return true;
    }

    public boolean applyEntityEvent(Entity entity, PlayerEntity player, Hand hand) {
        for (Condition<?> condition : conditions) {
            if (!condition.testEntity(entity, player, hand)) return false;
        }

        for (Effect effect : effects) {
            effect.runEntity(entity, player, hand);
        }
        return true;
    }

    public void applyEnchantmentEvent(Enchantment enchantment, Entity user, Entity target, int level) {
        for (Condition<?> condition : conditions) {
            if (!condition.testEnchantment(enchantment, user, target, level)) return;
        }

        for (Effect effect : effects) {
            effect.runEnchantment(enchantment, user, target, level);
        }
    }

    public void applyStatusEffectEvent(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        for (Condition<?> condition : conditions) {
            if (!condition.testStatusEffect(statusEffect, entity, amplifier)) return;
        }

        for (Effect effect : effects) {
            effect.runStatusEffect(statusEffect, entity, amplifier);
        }
    }
}
