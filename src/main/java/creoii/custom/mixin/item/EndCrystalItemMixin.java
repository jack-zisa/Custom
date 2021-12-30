package creoii.custom.mixin.item;

import creoii.custom.util.tags.BlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.EndCrystalItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EndCrystalItem.class)
public class EndCrystalItemMixin {
    @Redirect(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z", ordinal = 1))
    private boolean creo$applyEndCrystalBaseBlocks(BlockState instance, Block block) {
        return instance.isIn(BlockTags.END_CRYSTAL_BASE_BLOCKS);
    }
}