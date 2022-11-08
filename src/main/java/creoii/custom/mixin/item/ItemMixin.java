package creoii.custom.mixin.item;

import creoii.custom.util.Constants;
import creoii.custom.util.tags.ItemTags;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {
    @Shadow public abstract Item asItem();

    @Inject(method = "hasGlint", at = @At("HEAD"), cancellable = true)
    private void custom_applyGlints(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.isIn(ItemTags.GLINTED))
            cir.setReturnValue(true);
    }

    @Inject(method = "isFireproof", at = @At("RETURN"), cancellable = true)
    private void custom_applyFireproofs(CallbackInfoReturnable<Boolean> cir) {
        if (new ItemStack(asItem()).isIn(ItemTags.FIREPROOF))
            cir.setReturnValue(true);
    }

    @Inject(method = "getMaxUseTime", at = @At(value = "RETURN", ordinal = 0), cancellable = true)
    private void custom_applyFoodEatingSpeeds(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (stack.getItem().isFood()) {
            Identifier identifier = Registry.ITEM.getId(stack.getItem());
            if (Constants.FOOD_EATING_SPEEDS.containsKey(identifier)) {
                cir.setReturnValue(Constants.FOOD_EATING_SPEEDS.get(identifier));
            }
        }
    }
}