package creoii.custom.mixin;

import creoii.custom.util.tags.BlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.entity.ConduitBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ConduitBlockEntity.class)
public class ConduitBlockEntityMixin {
    @Mutable
    @Shadow @Final private static final Block[] ACTIVATING_BLOCKS;

    static {
        ACTIVATING_BLOCKS = BlockTags.CONDUIT_FRAMES.values().toArray(new Block[0]);
    }
}
