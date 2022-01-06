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

public class DestroyEffect extends Effect {
    private final boolean dropItem;

    public DestroyEffect(boolean dropItem) {
        super(Effect.DESTROY);
        this.dropItem = dropItem;
    }

    public static Effect getFromJson(JsonObject object) {
        boolean dropItem = JsonHelper.getBoolean(object, "drop_item", true);
        return new DestroyEffect(dropItem);
    }

    @Override
    public void runBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        world.breakBlock(pos, dropItem, living);
    }

    @Override
    public void runItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        stack.setCount(stack.getCount() - 1);
    }

    @Override
    public void runEntity(Entity entity, PlayerEntity player, Hand hand) {
        entity.kill();
    }

    @Override
    public void runEnchantment(Entity user, Entity target, int level) {
    }

    @Override
    public void runStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
    }

    @Override
    public void runWorld(World world) { }
}
