package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Property;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class SetBlockEffect extends Effect {
    private Block block;
    private boolean affectTarget;

    public SetBlockEffect withValues(Block block, boolean affectTarget) {
        this.block = block;
        this.affectTarget = affectTarget;
        return this;
    }

    public SetBlockEffect getFromJson(JsonObject object) {
        Block block = Registry.BLOCK.get(Identifier.tryParse(JsonHelper.getString(object, "block")));
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target");
        return withValues(block, affectTarget);
    }

    private void run(World world, BlockPos pos) {
        world.setBlockState(pos, block.getDefaultState(), 3);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void runBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        run(world, pos);
    }

    @Override
    public void runItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        run(world, pos);
    }

    @Override
    public void runEntity(Entity entity, PlayerEntity player, Hand hand) {
        run(entity.getWorld(), entity.getBlockPos());
    }

    @Override
    public void runEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        run(user.getWorld(), affectTarget ? target.getBlockPos() : user.getBlockPos());
    }

    @Override
    public void runStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        run(entity.getWorld(), entity.getBlockPos());
    }

    @Override
    public void runWorld(World world) { }
}
