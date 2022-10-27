package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.util.json.CustomJsonHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class BlockMatchesCondition extends Condition {
    private Block block;
    private BlockPos offset;
    private boolean affectTarget;

    public BlockMatchesCondition withValues(Block block, BlockPos offset, boolean affectTarget) {
        this.block = block;
        this.offset = offset;
        this.affectTarget = affectTarget;
        return this;
    }

    public BlockMatchesCondition getFromJson(JsonObject object) {
        Block block = Registry.BLOCK.get(Identifier.tryParse(JsonHelper.getString(object, "block")));
        BlockPos offset = CustomJsonHelper.getBlockPos(object, "offset");
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target", false);
        return withValues(block, offset, affectTarget);
    }

    private boolean test(World world, BlockPos pos) {
        return world.getBlockState(pos.add(offset)).isOf(block);
    }

    @Override
    public boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        return test(world, pos);
    }

    @Override
    public boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        return test(world, pos);
    }

    @Override
    public boolean testEntity(Entity entity, PlayerEntity player, Hand hand) {
        return test(entity.getWorld(), entity.getBlockPos());
    }

    @Override
    public boolean testEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        return test(user.getWorld(), affectTarget ? target.getBlockPos() : user.getBlockPos());
    }

    @Override
    public boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        return test(entity.getWorld(), entity.getBlockPos());
    }

    @Override
    public boolean testWorld(World world) {
        return false;
    }
}
