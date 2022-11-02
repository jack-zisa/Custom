package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
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

public class DestroyEffect extends Effect {
    private boolean blocksDropItem;

    public DestroyEffect withValues(boolean blocksDropItem) {
        this.blocksDropItem = blocksDropItem;
        return this;
    }

    public Effect getFromJson(JsonObject object) {
        boolean blocksDropItem = JsonHelper.getBoolean(object, "blocks_drop_item", true);
        return withValues(blocksDropItem);
    }

    @Override
    public void runBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        world.breakBlock(pos, blocksDropItem, living);
    }

    @Override
    public void runItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        stack.setCount(0);
    }

    @Override
    public void runEntity(Entity entity, PlayerEntity player, Hand hand) {
        entity.kill();
    }

    @Override
    public void runEnchantment(Enchantment enchantment, Entity user, Entity target, int level) { }

    @Override
    public void runStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        entity.removeStatusEffect(statusEffect);
    }

    @Override
    public void runWorld(World world) { }
}
