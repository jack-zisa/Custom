package creoii.custom.mixin.entity;

import creoii.custom.util.tags.ItemTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Shadow public abstract void disableShield(boolean sprinting);

    @Inject(method = "takeShieldHit", at = @At("TAIL"))
    private void custom$applyShieldDisablers(LivingEntity attacker, CallbackInfo ci) {
        if (attacker.getMainHandStack().isIn(ItemTags.SHIELD_DISABLERS)) {
            this.disableShield(true);
        }
    }
}
