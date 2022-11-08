package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.ConditionParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.ParameterParameter;
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

public class InvertCondition extends Condition {
    private final Condition condition = Conditions.EMPTY;

    public InvertCondition getFromJson(JsonObject object) {
        return null;
    }

    // CUSTOM OVERRIDE VALIDATION METHOD

    @Override
    public boolean test(EventParameter[] parameters) {
        if (validate(parameters)) {
            ConditionParameter conditionParameter = (ConditionParameter) parameters[0];
            return !conditionParameter.getCondition().test(conditionParameter.getCondition().getParameters());
        }
        return false;
    }

    @Override
    public EventParameter[] getParameters() {
        return new EventParameter[]{EventParameters.CONDITION};
    }

    @Override
    public boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        return !condition.testBlock(world, state, pos, living, hand);
    }

    @Override
    public boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        return !condition.testItem(world, stack, pos, player, hand);
    }

    @Override
    public boolean testEntity(Entity entity, PlayerEntity player, Hand hand) {
        return !condition.testEntity(entity, player, hand);
    }

    @Override
    public boolean testEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        return condition.testEnchantment(enchantment, user, target, level);
    }

    @Override
    public boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        return condition.testStatusEffect(statusEffect, entity, amplifier);
    }

    @Override
    public boolean testWorld(World world) {
        return condition.testWorld(world);
    }
}
