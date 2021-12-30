package creoii.custom.mixin.block;

import creoii.custom.util.tags.ItemTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.Hopper;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 * DOES NOT WORK
 */
@Mixin(HopperBlockEntity.class)
public class HopperBlockEntityMixin {
    @Inject(method = "getInputItemEntities", at = @At("RETURN"))
    private static void custom$hopperImmunes(World world, Hopper hopper, CallbackInfoReturnable<List<ItemEntity>> cir) {
        cir.getReturnValue().stream().filter((entity) -> entity.getStack().isIn(ItemTags.HOPPER_IMMUNE)).toList();
    }

    @Inject(method = "onEntityCollided", at = @At("HEAD"), cancellable = true)
    private static void custom$hopperImmunes(World world, BlockPos pos, BlockState state, Entity entity, HopperBlockEntity blockEntity, CallbackInfo ci) {
        if (entity instanceof ItemEntity) {
            if (((ItemEntity) entity).getStack().isIn(ItemTags.HOPPER_IMMUNE)) ci.cancel();
        }
    }
}