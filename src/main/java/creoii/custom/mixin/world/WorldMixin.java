package creoii.custom.mixin.world;

import creoii.custom.util.tags.BlockTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public class WorldMixin {
    @Inject(method = "breakBlock", at = @At(value = "HEAD"), cancellable = true)
    public void creo$ravagerBreakables(BlockPos pos, boolean drop, Entity breakingEntity, int maxUpdateDepth, CallbackInfoReturnable<Boolean> cir) {
        if (breakingEntity instanceof RavagerEntity && !breakingEntity.getEntityWorld().getBlockState(pos).isIn(BlockTags.RAVAGER_BREAKABLE)) {
            cir.setReturnValue(false);
        }
    }
}