package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.Custom;
import creoii.custom.data.Identifiable;
import creoii.custom.eventsystem.parameter.EventParameter;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class Condition implements Identifiable {
    @Nullable
    public static Condition getCondition(JsonObject object, Identifier id) {
        return Custom.CONDITION.get(id).getFromJson(object);
    }

    public static Condition register(Identifier id, Condition condition) {
        return Registry.register(Custom.CONDITION, id, condition);
    }

    @Override
    public Identifier getIdentifier() {
        return Custom.CONDITION.getId(this);
    }

    /**
     * Ensure that we are provided the correct parameters to check the condition
     */
    public boolean validate(EventParameter[] parameters) {
        List<EventParameter> validatee = new ArrayList<>(List.of(getParameters()));
        for (int i = 0; i < validatee.size(); ++i) {
            if (validatee.get(i) == parameters[i]) {
                validatee.remove(validatee.get(i));
                break;
            }
        }
        return validatee.size() == 0;
    }

    /**
     * Array of the parameters needed to check the condition
     */
    public abstract EventParameter[] getParameters();

    public abstract Condition getFromJson(JsonObject object);

    public abstract boolean test(EventParameter[] parameters);

    @Deprecated
    public abstract boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand);
    @Deprecated
    public abstract boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand);
    @Deprecated
    public abstract boolean testEntity(Entity entity, PlayerEntity player, Hand hand);
    @Deprecated
    public abstract boolean testEnchantment(Enchantment enchantment, Entity user, Entity target, int level);
    @Deprecated
    public abstract boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier);
    @Deprecated
    public abstract boolean testWorld(World world);
}
