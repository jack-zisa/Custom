package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.util.provider.ValueProvider;
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

public class TimeWithinCondition extends Condition {
    private ValueProvider<Double> minTime;
    private ValueProvider<Double> maxTime;

    public TimeWithinCondition withValues(ValueProvider<Double> minTime, ValueProvider<Double> maxTime) {
        this.minTime = minTime;
        this.maxTime = maxTime;
        return this;
    }

    public TimeWithinCondition getFromJson(JsonObject object) {
        ValueProvider<Double> minTime = (ValueProvider<Double>) ValueProvider.getFromJson(object, "min_time");
        ValueProvider<Double> maxTime = (ValueProvider<Double>) ValueProvider.getFromJson(object, "max_time");
        return withValues(minTime, maxTime);
    }

    private boolean test(World world) {
        return world.getTime() > minTime.getValue() && world.getTime() < maxTime.getValue();
    }

    @Override
    public boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        return test(world);
    }

    @Override
    public boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        return test(world);
    }

    @Override
    public boolean testEntity(Entity entity, PlayerEntity player, Hand hand) {
        return test(entity.getWorld());
    }

    @Override
    public boolean testEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        return test(user.getWorld());
    }

    @Override
    public boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        return test(entity.getWorld());
    }

    @Override
    public boolean testWorld(World world) {
        return test(world);
    }
}
