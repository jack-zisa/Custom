package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.EntityParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.StatusEffectParameter;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HasStatusEffectCondition extends Condition {
    private final StatusEffect statusEffect = StatusEffects.ABSORPTION;

    @Override
    public Condition getFromJson(JsonObject object) {
        return null;
    }

    @Override
    public EventParameter[] getParameters() {
        return new EventParameter[]{EventParameters.ENTITY, EventParameters.STATUS_EFFECT};
    }

    @Override
    public boolean test(EventParameter[] parameters) {
        if (validate(parameters)) {
            EntityParameter entityParameter = (EntityParameter) parameters[0];
            if (entityParameter.getEntity() instanceof LivingEntity livingEntity) {
                StatusEffectParameter statusEffectParameter = (StatusEffectParameter) parameters[1];
                return livingEntity.hasStatusEffect(statusEffectParameter.getStatusEffect());
            }
        }
        return false;
    }

    private boolean test(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            return livingEntity.hasStatusEffect(statusEffect);
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
        return test(user);
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
