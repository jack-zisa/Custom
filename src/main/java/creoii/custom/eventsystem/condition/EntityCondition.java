package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.EntityParameter;
import creoii.custom.eventsystem.parameter.EntityTypeParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
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

import java.util.function.Predicate;

public class EntityCondition extends Condition {
    private final Predicate<Entity> predicate;
    private boolean affectTarget;

    public EntityCondition(Predicate<Entity> predicate) {
        this.predicate = predicate;
    }

    public EntityCondition withValues(boolean affectTarget) {
        this.affectTarget = affectTarget;
        return this;
    }

    public EntityCondition getFromJson(JsonObject object) {
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target", false);
        return withValues(affectTarget);
    }

    @Override
    public EventParameter[] getParameters() {
        return new EventParameter[]{EventParameters.ENTITY_TYPE};
    }

    @Override
    public boolean test(EventParameter[] parameters) {
        if (validate(parameters))
            return predicate.test(((EntityParameter)parameters[0]).getEntity());
        return false;
    }

    private boolean test(Entity entity) {
        return entity.isCrawling();
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
