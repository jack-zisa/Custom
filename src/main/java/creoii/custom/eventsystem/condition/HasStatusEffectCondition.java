package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
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

public class HasStatusEffectCondition extends Condition {
    private final StatusEffect statusEffect;
    private final boolean useTargetPosition;

    public HasStatusEffectCondition(StatusEffect statusEffect, boolean useTargetPosition) {
        super(Condition.HAS_STATUS_EFFECT);
        this.statusEffect = statusEffect;
        this.useTargetPosition = useTargetPosition;
    }

    public static Condition getFromJson(JsonObject object) {
        StatusEffect statusEffect = Registry.STATUS_EFFECT.get(Identifier.tryParse(JsonHelper.getString(object , "status_effect")));
        boolean useTargetPosition = JsonHelper.getBoolean(object, "use_target_position", false);
        return new HasStatusEffectCondition(statusEffect, useTargetPosition);
    }

    @Override
    public boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        return living.hasStatusEffect(statusEffect);
    }

    @Override
    public boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        return player.hasStatusEffect(statusEffect);
    }

    @Override
    public boolean testEntity(Entity entity, PlayerEntity player, Hand hand) {
        return entity.isLiving() && ((LivingEntity) entity).hasStatusEffect(statusEffect);
    }

    @Override
    public boolean testEnchantment(Entity user, Entity target, int level) {
        if (useTargetPosition) {
            return target.isLiving() && ((LivingEntity) target).hasStatusEffect(statusEffect);
        } else {
            return user.isLiving() && ((LivingEntity) user).hasStatusEffect(statusEffect);
        }
    }

    @Override
    public boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        return entity.hasStatusEffect(statusEffect);
    }
}
