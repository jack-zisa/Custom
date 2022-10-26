package creoii.custom.mixin.block;

import creoii.custom.util.tags.BlockTags;
import net.minecraft.block.entity.ConduitBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(ConduitBlockEntity.class)
public class ConduitBlockEntityMixin {
    //@Inject(method = "updateActivatingBlocks", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void custom_conduitFrames(World world, BlockPos pos, List<BlockPos> activatingBlocks, CallbackInfoReturnable<Boolean> cir, int i, int j, int k, int l, int m, int n, BlockPos blockPos2) {
        if (world.getBlockState(blockPos2).isIn(BlockTags.CONDUIT_FRAMES)) activatingBlocks.add(blockPos2);
    }
}
