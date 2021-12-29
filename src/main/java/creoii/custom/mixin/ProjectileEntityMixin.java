package creoii.custom.mixin;

import creoii.custom.util.tags.EntityTypeTags;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ProjectileEntity.class)
public class ProjectileEntityMixin {
    @Inject(method = "onCollision", at = @At(value = "HEAD"), cancellable = true)
    private void custom$negateProjectileCollision(HitResult hitResult, CallbackInfo ci) {
        if (hitResult.getType() == HitResult.Type.ENTITY) {
            if (((EntityHitResult) hitResult).getEntity().getType().isIn(EntityTypeTags.PROJECTILES_PASS_THROUGH)) ci.cancel();
        }
    }
}