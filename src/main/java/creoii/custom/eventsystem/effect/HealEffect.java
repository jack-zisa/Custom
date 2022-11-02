package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.util.provider.ValueProvider;
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

public class HealEffect extends Effect {
    private ValueProvider<Double> amount;
    private boolean affectTarget;

    public HealEffect withValues(ValueProvider<Double> amount, boolean affectTarget) {
        this.amount = amount;
        this.affectTarget = affectTarget;
        return this;
    }

    @SuppressWarnings("unchecked")
    public HealEffect getFromJson(JsonObject object) {
        ValueProvider<Double> amount = (ValueProvider<Double>) ValueProvider.getFromJson(object, "amount");
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target", false);
        return withValues(amount, affectTarget);
    }

    @Override
    public void runBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        living.heal(amount.getValue().floatValue());
    }

    @Override
    public void runItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        player.heal(amount.getValue().floatValue());
    }

    @Override
    public void runEntity(Entity entity, PlayerEntity player, Hand hand) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).heal(amount.getValue().floatValue());
        }
    }

    @Override
    public void runEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        Entity entity = affectTarget ? target : user;
        if (entity instanceof LivingEntity living) {
            living.heal(amount.getValue().floatValue());
        }
    }

    @Override
    public void runStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        entity.heal(amount.getValue().floatValue());
    }

    @Override
    public void runWorld(World world) { }
}
