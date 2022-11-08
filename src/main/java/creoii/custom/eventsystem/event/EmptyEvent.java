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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EmptyEvent extends AbstractEvent {
    @Override
    public AbstractEvent withValues(Condition[] conditions, Effect[] effects) {
        return null;
    }

    public AbstractEvent getFromJson(JsonObject object) {
        conditions = new Condition[0];
        effects = new Effect[0];
        return new EmptyEvent();
    }

    @Override
    public boolean applyBlockEvent(World world, BlockState state, BlockPos pos, @Nullable LivingEntity living, @Nullable Hand hand) {
        return false;
    }

    @Override
    public boolean applyItemEvent(World world, ItemStack stack, BlockPos pos, PlayerEntity player, @Nullable Hand hand) {
        return false;
    }

    @Override
    public boolean applyEntityEvent(Entity entity, PlayerEntity player, Hand hand) {
        return false;
    }

    @Override
    public void applyEnchantmentEvent(Enchantment enchantment, Entity user, Entity target, int level) {

    }

    @Override
    public void applyStatusEffectEvent(StatusEffect statusEffect, LivingEntity entity, int amplifier) {

    }
}
