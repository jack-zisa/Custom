package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
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
    private final float amount;

    public HealEffect(float amount) {
        super(Effect.HEAL);
        this.amount = amount;
    }

    public static Effect getFromJson(JsonObject object) {
        float amount = JsonHelper.getFloat(object, "amount", 0f);
        return new HealEffect(amount);
    }

    @Override
    public void runBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        living.heal(this.amount);
    }

    @Override
    public void runItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        player.heal(this.amount);
    }

    @Override
    public void runEntity(Entity entity, PlayerEntity player, Hand hand) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).heal(this.amount);
        }
    }

    @Override
    public void runEnchantment(Entity user, Entity target, int level) {
        if (user instanceof LivingEntity) {
            ((LivingEntity) user).heal(this.amount);
        }
    }

    @Override
    public void runStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        entity.heal(this.amount);
    }
}
