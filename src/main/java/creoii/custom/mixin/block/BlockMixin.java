package creoii.custom.mixin.block;

import creoii.custom.util.tags.BlockTags;
import net.minecraft.block.Block;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockMixin {
    @Shadow protected abstract Block asBlock();

    @Inject(method = "shouldDropItemsOnExplosion", at = @At("RETURN"), cancellable = true)
    private void custom$applyExplosionDroppables(Explosion explosion, CallbackInfoReturnable<Boolean> cir) {
        cir.cancel();
        cir.setReturnValue(!BlockTags.NO_DROPS_ON_EXPLOSION.contains(this.asBlock()));
    }
}
