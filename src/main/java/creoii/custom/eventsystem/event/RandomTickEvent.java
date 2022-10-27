package creoii.custom.eventsystem.event;

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

public class RandomTickEvent extends Event {
    private float chance;

    public RandomTickEvent withValues(Condition[] conditions, Effect[] effects, float chance) {
        this.conditions = conditions;
        this.effects = effects;
        this.chance = chance;
        return this;
    }

    public RandomTickEvent getFromJson(JsonObject object) {
        Condition[] conditions = Event.getConditions(object);
        Effect[] effects = Event.getEffects(object);
        float chance = JsonHelper.getFloat(object, "chance", 0f);
        return withValues(conditions, effects, chance);
    }

    public boolean applyBlockEvent(World world, BlockState state, BlockPos pos, @Nullable LivingEntity living, @Nullable Hand hand) {
        if (living != null) {
            if (living.getWorld().getRandom().nextFloat() > chance) {
                super.applyBlockEvent(world, state, pos, living, hand);
            }
        }
        return false;
    }

    public boolean applyItemEvent(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        if (player.getWorld().getRandom().nextFloat() > chance) {
            super.applyItemEvent(world, stack, pos, player, hand);
        }
        return false;
    }

    public boolean applyEntityEvent(Entity entity, PlayerEntity player, Hand hand) {
        if (entity.getWorld().getRandom().nextFloat() > chance) {
            return super.applyEntityEvent(entity, player, hand);
        }
        return false;
    }

    public void applyEnchantmentEvent(Enchantment enchantment, Entity user, Entity target, int level) {
        if (user.getWorld().getRandom().nextFloat() > chance) {
            super.applyEnchantmentEvent(enchantment, user, target, level);
        }
    }

    @Override
    public void applyStatusEffectEvent(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        if (entity.getWorld().getRandom().nextFloat() > chance) {
            super.applyStatusEffectEvent(statusEffect, entity, amplifier);
        }
    }
}
