package creoii.custom.eventsystem.effect;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NoEffect extends Effect {
    public NoEffect() {
        super("none");
    }

    @Override
    public void runWorld(World world, BlockPos pos) {
    }

    @Override
    public void runBlock(World world, BlockState state, BlockPos pos, PlayerEntity player, Hand hand) {
    }

    @Override
    public void runItem(World world, Item item, BlockPos pos, PlayerEntity player, Hand hand) {
    }

    @Override
    public void runEntity(Entity entity, PlayerEntity player, Hand hand) {
    }
}
