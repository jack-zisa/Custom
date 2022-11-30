package creoii.custom.mixin.entity;

import creoii.custom.util.Constants;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractMinecartEntity.class)
public class AbstractMinecartEntityMixin {
    @Inject(method = "getMaxSpeed", at = @At("RETURN"), cancellable = true)
    private void custom_customMinecartSpeed(CallbackInfoReturnable<Double> cir) {
        cir.setReturnValue(((((Entity)(Object)this)).isTouchingWater() ? (Constants.MINECART_BASE_SPEED / 2d) : Constants.MINECART_BASE_SPEED) / 20d);
    }
}
