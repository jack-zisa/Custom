package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.util.math.DoubleValueHolder;
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
    private DoubleValueHolder amount;
    private boolean affectTarget;

    public HealEffect withValues(DoubleValueHolder amount, boolean affectTarget) {
        this.amount = amount;
        this.affectTarget = affectTarget;
        return this;
    }

    public HealEffect getFromJson(JsonObject object) {
        DoubleValueHolder amount = DoubleValueHolder.getFromJson(object, "amount");
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target", false);
        return withValues(amount, affectTarget);
    }

    @Override
    public void runBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        living.heal((float) amount.getValue());
    }

    @Override
    public void runItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        player.heal((float) amount.getValue());
    }

    @Override
    public void runEntity(Entity entity, PlayerEntity player, Hand hand) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).heal((float) amount.getValue());
        }
    }

    @Override
    public void runEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        Entity entity = affectTarget ? target : user;
        if (entity instanceof LivingEntity living) {
            living.heal((float) amount.getValue());
        }
    }

    @Override
    public void runStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        entity.heal((float) amount.getValue());
    }

    @Override
    public void runWorld(World world) { }
}
