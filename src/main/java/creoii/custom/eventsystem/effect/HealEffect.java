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
    private final boolean useTargetPosition;

    public HealEffect(float amount, boolean useTargetPosition) {
        super(Effect.HEAL);
        this.amount = amount;
        this.useTargetPosition = useTargetPosition;
    }

    public static Effect getFromJson(JsonObject object) {
        float amount = JsonHelper.getFloat(object, "amount", 0f);
        boolean useTargetPosition = JsonHelper.getBoolean(object, "use_target_position", false);
        return new HealEffect(amount, useTargetPosition);
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
        Entity entity = useTargetPosition ? target : user;
        if (entity instanceof LivingEntity living) {
            living.heal(this.amount);
        }
    }

    @Override
    public void runStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        entity.heal(this.amount);
    }

    @Override
    public void runWorld(World world) { }
}
