package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.BooleanParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.IntegerParameter;
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

public class BiWithinCondition<T1, T2, V extends Number> extends Condition {
    private final BiFunction<T1, T2, V> value;

    public BiWithinCondition(BiFunction<T1, T2, V> value) {
        this.value = value;
    }

    @Override
    public Condition getFromJson(JsonObject object) {
        return null;
    }

    @Override
    public EventParameter[] getParameters() {
        return new EventParameter[]{EventParameters.INTEGER, EventParameters.INTEGER, EventParameters.BOOLEAN};
    }

    @Override
    public boolean test(EventParameter[] parameters) {
        if (validate(parameters)) {
            BooleanParameter booleanParameter = (BooleanParameter) parameters[2];
            if (booleanParameter.getBoolean()) {
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
