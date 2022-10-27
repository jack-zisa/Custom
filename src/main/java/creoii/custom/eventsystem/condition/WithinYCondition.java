package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.util.math.DoubleValueHolder;
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

public class WithinYCondition extends Condition {
    private DoubleValueHolder minY;
    private DoubleValueHolder maxY;
    private boolean affectTarget;

    public WithinYCondition withValues(DoubleValueHolder minY, DoubleValueHolder maxY, boolean affectTarget) {
        this.minY = minY;
        this.maxY = maxY;
        this.affectTarget = affectTarget;
        return this;
    }

    public WithinYCondition getFromJson(JsonObject object) {
        DoubleValueHolder minY = DoubleValueHolder.getFromJson(object, "min_y");
        DoubleValueHolder maxY = DoubleValueHolder.getFromJson(object, "max_y");
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target", false);
        return withValues(minY, maxY, affectTarget);
    }

    private boolean test(BlockPos pos) {
        return pos.getY() > minY.getValue() && pos.getY() < maxY.getValue();
    }

    @Override
    public boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        return test(pos);
    }

    @Override
    public boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        return test(pos);
    }

    @Override
    public boolean testEntity(Entity entity, PlayerEntity player, Hand hand) {
        return test(entity.getBlockPos());
    }

    @Override
    public boolean testEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        return test(affectTarget ? target.getBlockPos() : user.getBlockPos());
    }

    @Override
    public boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        return test(entity.getBlockPos());
    }

    @Override
    public boolean testWorld(World world) {
        return false;
    }
}
