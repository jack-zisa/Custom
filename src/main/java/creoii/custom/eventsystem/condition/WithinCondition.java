package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
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

public class WithinCondition<T, V extends Number> extends Condition<WithinParameter<T, V>> {
    private final Function<T, V> value;

    public WithinCondition(Function<T, V> value) {
        this.value = value;
    }

    @Override
    public Condition<?> getFromJson(JsonObject object) {
        return null;
    }

    @Override
    public WithinParameter<?, ?> getParameters() {
        return EventParameters.WITHIN;
    }

    @Override
    public boolean test(WithinParameter<T, V> parameters) {
        V val = value.apply(parameters.getValue());
        if (parameters.isInclusive()) {
            if (val instanceof Double d) {
                return d <= (double)parameters.getMax() && d >= (double)parameters.getMin();
            } else if (val instanceof Float f) {
                return f <= (float)parameters.getMax() && f >= (float)parameters.getMin();
            } else if (val instanceof Long l) {
                return l <= (long)parameters.getMax() && l >= (long)parameters.getMin();
            } else if (val instanceof Integer i) {
                return i <= (int)parameters.getMax() && i >= (int)parameters.getMin();
            }
        } else {
            if (val instanceof Double d) {
                return d < (double)parameters.getMax() && d > (double)parameters.getMin();
            } else if (val instanceof Float f) {
                return f < (float)parameters.getMax() && f > (float)parameters.getMin();
            } else if (val instanceof Long l) {
                return l < (long)parameters.getMax() && l > (long)parameters.getMin();
            } else if (val instanceof Integer i) {
                return i < (int)parameters.getMax() && i >= (int)parameters.getMin();
            }
        }
        return false;
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
