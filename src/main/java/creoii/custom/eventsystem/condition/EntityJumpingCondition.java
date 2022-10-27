package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
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

public class EntityJumpingCondition extends Condition {
    private boolean affectTarget;

    public EntityJumpingCondition withValues(boolean affectTarget) {
        this.affectTarget = affectTarget;
        return this;
    }

    public EntityJumpingCondition getFromJson(JsonObject object) {
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target", false);
        return withValues(affectTarget);
    }

    private boolean test(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            return livingEntity.jumping;
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
