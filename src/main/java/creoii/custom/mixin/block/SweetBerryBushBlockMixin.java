package creoii.custom.mixin.block;

import creoii.custom.util.tags.EntityTypeTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SweetBerryBushBlock.class)
public class SweetBerryBushBlockMixin {
    @Inject(method = "onEntityCollision", at = @At("RETURN"), cancellable = true)
    private void custom_applyBerryBushImmunes(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (entity.getType().isIn(EntityTypeTags.BERRY_BUSH_IMMUNE)) ci.cancel();
    }
}
