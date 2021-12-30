package creoii.custom.mixin.entity;

import creoii.custom.util.tags.EntityTypeTags;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.passive.GoatBrain;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GoatBrain.class)
public class GoatBrainMixin {
    @Mutable
    @Shadow @Final private static final TargetPredicate RAM_TARGET_PREDICATE;

    static {
        RAM_TARGET_PREDICATE = TargetPredicate.createAttackable().setPredicate(entity -> !entity.getType().isIn(EntityTypeTags.GOAT_UNRAMMABLE) && entity.world.getWorldBorder().contains(entity.getBoundingBox()));
    }
}
