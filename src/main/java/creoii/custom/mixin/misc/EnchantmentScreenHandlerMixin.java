package creoii.custom.mixin.misc;

import creoii.custom.util.tags.BlockTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.screen.EnchantmentScreenHandler;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EnchantmentScreenHandler.class)
public class EnchantmentScreenHandlerMixin {
    //@Inject(method = "onContentChanged", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
    private boolean creo$applyEnchantBoosters(AbstractBlock.AbstractBlockState state, Block block) {
        return state.isIn(BlockTags.BOOSTS_ENCHANTMENTS);
    }
}