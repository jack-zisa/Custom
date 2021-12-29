package creoii.custom.mixin;

import creoii.custom.util.tags.EntityTypeTags;
import creoii.custom.util.tags.ItemTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.CactusBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CactusBlock.class)
public class CactusBlockMixin {
    @Inject(method = "onEntityCollision", at = @At("HEAD"), cancellable = true)
    private void custom$negateCactusDamage(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (entity instanceof ItemEntity) {
            if (((ItemEntity) entity).getStack().isIn(ItemTags.CACTUS_IMMUNE)) {
                ci.cancel();
            }
        } else if (entity.getType().isIn(EntityTypeTags.CACTUS_IMMUNE)) ci.cancel();
    }
}