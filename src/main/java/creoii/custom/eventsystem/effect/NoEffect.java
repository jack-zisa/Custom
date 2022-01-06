package creoii.custom.eventsystem.effect;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NoEffect extends Effect {
    public NoEffect() {
        super("none");
    }

    @Override
    public void runBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
    }

    @Override
    public void runItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
    }

    @Override
    public void runEntity(Entity entity, PlayerEntity player, Hand hand) {
    }

    @Override
    public void runEnchantment(Entity user, Entity target, int level) {
    }

    @Override
    public void runStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
    }

    @Override
    public void runWorld(World world) { }
}
