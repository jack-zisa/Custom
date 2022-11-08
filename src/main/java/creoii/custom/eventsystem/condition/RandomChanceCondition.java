package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.Custom;
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
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class RandomChanceCondition extends Condition<Condition.OneParameter<Double>> {
    private ValueProvider<Double> chance;

    public RandomChanceCondition withValues(ValueProvider<Double> chance) {
        this.chance = chance;
        return this;
    }

    @SuppressWarnings("unchecked")
    public RandomChanceCondition getFromJson(JsonObject object) {
        ValueProvider<Double> chance = (ValueProvider<Double>) ValueProvider.getFromJson(object, "chance");
        return withValues(chance);
    }

    @Override
    public boolean test(OneParameter<Double> parameters) {
        return Custom.RANDOM.nextDouble() < parameters.value();
    }

    private boolean test(Random random) {
        return random.nextFloat() < chance.getValue();
    }

    @Override
    public boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        return test(world.getRandom());
    }

    @Override
    public boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        return test(world.getRandom());
    }

    @Override
    public boolean testEntity(Entity entity, PlayerEntity player, Hand hand) {
        return test(entity.getWorld().getRandom());
    }

    @Override
    public boolean testEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        return test(user.getWorld().getRandom());
    }

    @Override
    public boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        return test(entity.getWorld().getRandom());
    }

    @Override
    public boolean testWorld(World world) {
        return test(world.getRandom());
    }
}
