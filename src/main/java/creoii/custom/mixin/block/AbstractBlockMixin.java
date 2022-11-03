package creoii.custom.mixin.block;

import creoii.custom.util.tags.BlockTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.tick.OrderedTick;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.class)
public abstract class AbstractBlockMixin {
    @Shadow protected abstract Block asBlock();

    @Inject(method = "scheduledTick", at = @At("HEAD"))
    private void custom_affectedByGravityTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (state.isIn(BlockTags.AFFECTED_BY_GRAVITY) && !(state.getBlock() instanceof FallingBlock)) {
            if (FallingBlock.canFallThrough(world.getBlockState(pos.down())) && pos.getY() >= world.getBottomY()) {
                FallingBlockEntity fallingBlockEntity = new FallingBlockEntity(world, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, world.getBlockState(pos));
                world.spawnEntity(fallingBlockEntity);
            }
        }
    }

    @Inject(method = "getStateForNeighborUpdate", at = @At("HEAD"), cancellable = true)
    private void custom_affectedByGravityNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> cir) {
        if (state.isIn(BlockTags.AFFECTED_BY_GRAVITY) && !(state.getBlock() instanceof FallingBlock)) {
            world.getBlockTickScheduler().scheduleTick(OrderedTick.create(this.asBlock(), pos));
            cir.setReturnValue(state.getStateForNeighborUpdate(direction, neighborState, world, pos, neighborPos));
        }
    }

    @Inject(method = "onBlockAdded", at = @At("HEAD"))
    private void custom_affectedByGravityAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
        if (state.isIn(BlockTags.AFFECTED_BY_GRAVITY) && !(state.getBlock() instanceof FallingBlock)) {
            world.getBlockTickScheduler().scheduleTick(OrderedTick.create(this.asBlock(), pos));
        }
    }
}