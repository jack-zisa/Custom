package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.EventParameter;
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

import java.util.function.BiFunction;

public class MatchingAtCondition<V, P, R> extends Condition<MatchingAtCondition.MatchingParameter<R>> {
    private final BiFunction<V, P, R> value;

    public MatchingAtCondition(BiFunction<V, P, R> value) {
        this.value = value;
    }

    public BiFunction<V, P, R> getValue() {
        return value;
    }

    @Override
    public MatchingAtCondition<?, ?, ?> getFromJson(JsonObject object) {
        return null;
    }

    @Override
    public boolean test(MatchingParameter<R> parameters) {
        return value == parameters.value();
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
