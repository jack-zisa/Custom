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
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PlayerLevelWithinCondition extends Condition {
    private ValueProvider<Double> minLevel;
    private ValueProvider<Double> maxLevel;
    private boolean affectTarget;

    public PlayerLevelWithinCondition withValues(ValueProvider<Double> minLevel, ValueProvider<Double> maxLevel, boolean affectTarget) {
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.affectTarget = affectTarget;
        return this;
    }

    public PlayerLevelWithinCondition getFromJson(JsonObject object) {
        ValueProvider<Double> minLevel = (ValueProvider<Double>) ValueProvider.getFromJson(object, "min_level");
        ValueProvider<Double> maxLevel = (ValueProvider<Double>) ValueProvider.getFromJson(object, "max_level");
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target", false);
        return withValues(minLevel, maxLevel, affectTarget);
    }

    private boolean test(Entity entity) {
        if (entity instanceof PlayerEntity player) {
            return player.experienceLevel > minLevel.getValue() && player.experienceLevel < maxLevel.getValue();
        }
        return false;
    }

    @Override
    public boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        return test(living);
    }

    @Override
    public boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        return test(player);
    }

    @Override
    public boolean testEntity(Entity entity, PlayerEntity player, Hand hand) {
        return test(entity);
    }

    @Override
    public boolean testEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        return test(affectTarget ? target : user);
    }

    @Override
    public boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        return test(entity);
    }

    @Override
    public boolean testWorld(World world) {
        return false;
    }
}
