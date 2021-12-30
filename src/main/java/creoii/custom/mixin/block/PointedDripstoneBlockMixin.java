package creoii.custom.mixin.block;

import creoii.custom.util.tags.EntityTypeTags;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PointedDripstoneBlock.class)
public class PointedDripstoneBlockMixin {
    @Redirect(method = "onLandedUpon", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;handleFallDamage(FFLnet/minecraft/entity/damage/DamageSource;)Z"))
    private boolean custom$addDripstoneImmunes(Entity instance, float fallDistance, float damageMultiplier, DamageSource damageSource) {
        if (instance.getType().isIn(EntityTypeTags.DRIPSTONE_IMMUNE)) return instance.handleFallDamage(fallDistance, 1.0f, DamageSource.FALL);
        return instance.handleFallDamage(fallDistance + 2.0f, 2.0f, DamageSource.STALAGMITE);
    }
}
