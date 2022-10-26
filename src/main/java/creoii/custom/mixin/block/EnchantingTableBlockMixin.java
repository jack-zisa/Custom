package creoii.custom.mixin.block;

import creoii.custom.util.tags.BlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.EnchantingTableBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantingTableBlock.class)
public class EnchantingTableBlockMixin {
    @Redirect(method = "canAccessBookshelf", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
    private static boolean custom$applyEnchantBoostParticles(BlockState instance, Block block) {
        return instance.isIn(BlockTags.BOOSTS_ENCHANTMENTS);
    }
}