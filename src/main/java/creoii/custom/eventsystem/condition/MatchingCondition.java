package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Function;

public class MatchingCondition<V, R> extends Condition {
    private final Function<V, R> value;

    public MatchingCondition(Function<V, R> value) {
        this.value = value;
    }

    @Override
    public MatchingCondition<?, ?> getFromJson(JsonObject object) {
        return null;
    }

    @Override
    public EventParameter[] getParameters() {
        return new EventParameter[]{EventParameters.OBJECT, EventParameters.OBJECT};
    }

    @Override
    public boolean test(EventParameter[] parameters) {
        return value.apply(parameters.value) == parameters.ret;
    }

    @Override
    public boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        return false;
    }

    @Override
    public boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        return false;
    }

    @Override
    public boolean testEntity(Entity entity, PlayerEntity player, Hand hand) {
        return false;
    }

    @Override
    public boolean testEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        return false;
    }

    @Override
    public boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        return false;
    }

    @Override
    public boolean testWorld(World world) {
        return false;
    }
}
