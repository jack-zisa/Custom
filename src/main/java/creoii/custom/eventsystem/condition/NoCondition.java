package creoii.custom.eventsystem.condition;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NoCondition extends Condition {
    public NoCondition() {
        super("none");
    }

    @Override
    public boolean testWorld(World world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean testBlock(World world, BlockState state, BlockPos pos, PlayerEntity player, Hand hand) {
        return false;
    }

    @Override
    public boolean testItem(World world, Item item, BlockPos pos, PlayerEntity player, Hand hand) {
        return false;
    }

    @Override
    public boolean testEntity(Entity entity, PlayerEntity player, Hand hand) {
        return false;
    }

    @Override
    public boolean testEnchantment(Entity user, Entity target, int level) {
        return false;
    }
}
