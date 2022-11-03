package creoii.custom.mixin.misc;

import creoii.custom.util.tags.EnchantmentTags;
import creoii.custom.util.tags.TagUtil;
import net.minecraft.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin {
    @Inject(method = "isTreasure", at = @At("HEAD"), cancellable = true)
    private void custom_applyTreasures(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(TagUtil.isEnchantmentIn((Enchantment) (Object) this, EnchantmentTags.TREASURE.id()));
    }

    @Inject(method = "isCursed", at = @At("HEAD"), cancellable = true)
    private void custom_applyCurses(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(TagUtil.isEnchantmentIn((Enchantment) (Object) this, EnchantmentTags.CURSED.id()));
    }
}