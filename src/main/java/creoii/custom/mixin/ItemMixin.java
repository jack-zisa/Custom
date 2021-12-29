package creoii.custom.mixin;

import creoii.custom.util.tags.ItemTags;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {
    @Shadow public abstract Item asItem();

    @Inject(method = "hasGlint", at = @At("HEAD"), cancellable = true)
    private void creo$applyGlints(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.isIn(ItemTags.GLINTED)) cir.setReturnValue(true);
    }

    @Inject(method = "isFireproof", at = @At("RETURN"), cancellable = true)
    private void creo$applyFireproofs(CallbackInfoReturnable<Boolean> cir) {
        if (new ItemStack(this.asItem()).isIn(ItemTags.FIREPROOF)) cir.setReturnValue(true);
    }
}