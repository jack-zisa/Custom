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
import org.apache.commons.lang3.function.TriFunction;

public class TriWithinCondition<T1, T2, T3, V extends Number> extends Condition<TriWithinParameter<T1, T2, T3, V>> {
    private final TriFunction<T1, T2, T3, V> value;

    public TriWithinCondition(TriFunction<T1, T2, T3, V> value) {
        this.value = value;
    }

    @Override
    public Condition<?> getFromJson(JsonObject object) {
        return null;
    }

    @Override
    public TriWithinParameter<?, ?, ?, ?> getParameters() {
        return EventParameters.TRI_WITHIN;
    }

    @Override
    public boolean test(TriWithinParameter<T1, T2, T3, V> parameters) {
        V val = value.apply(parameters.getValue1(), parameters.getValue2(), parameters.getValue3());
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
                return i < (int)parameters.getMax() && i > (int)parameters.getMin();
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
