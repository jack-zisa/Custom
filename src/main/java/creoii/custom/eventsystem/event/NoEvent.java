package creoii.custom.eventsystem.event;

import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.effect.Effect;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NoEvent extends Event {
    public NoEvent() {
        super("none", new Condition[0], new Effect[0]);
    }

    @Override
    public boolean applyWorldEvent(World world, BlockPos pos) { return false; }

    @Override
    public boolean applyBlockEvent(World world, BlockState state, BlockPos pos, PlayerEntity player, Hand hand) {
        return false;
    }

    @Override
    public boolean applyItemEvent(World world, Item item, BlockPos pos, PlayerEntity player, Hand hand) {
        return false;
    }

    @Override
    public boolean applyEntityEvent(Entity entity, PlayerEntity player, Hand hand) {
        return false;
    }
}
