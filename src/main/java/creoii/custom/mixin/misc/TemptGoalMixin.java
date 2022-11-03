package creoii.custom.mixin.misc;

import creoii.custom.util.tags.ItemTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TemptGoal.class)
public class TemptGoalMixin {
    @Shadow @Final protected PathAwareEntity mob;

    @Inject(method = "isTemptedBy(Lnet/minecraft/entity/LivingEntity;)Z", at = @At(value = "HEAD"), cancellable = true)
    private void custom_applyAnimalFoodTags(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        if (this.mob.getType() == EntityType.COW) {
            cir.setReturnValue(entity.getMainHandStack().isIn(ItemTags.COW_FOOD) || entity.getOffHandStack().isIn(ItemTags.COW_FOOD));
        } else if (this.mob.getType() == EntityType.SHEEP) {
            cir.setReturnValue(entity.getMainHandStack().isIn(ItemTags.SHEEP_FOOD) || entity.getOffHandStack().isIn(ItemTags.SHEEP_FOOD));
        } else if (this.mob.getType() == EntityType.PIG) {
            cir.setReturnValue(entity.getMainHandStack().isIn(ItemTags.PIG_FOOD) || entity.getOffHandStack().isIn(ItemTags.PIG_FOOD));
        } else if (this.mob.getType() == EntityType.CHICKEN) {
            cir.setReturnValue(entity.getMainHandStack().isIn(ItemTags.CHICKEN_FOOD) || entity.getOffHandStack().isIn(ItemTags.CHICKEN_FOOD));
        } else if (this.mob.getType() == EntityType.BEE) {
            cir.setReturnValue(entity.getMainHandStack().isIn(ItemTags.BEE_FOOD) || entity.getOffHandStack().isIn(ItemTags.BEE_FOOD));
        } else if (this.mob.getType() == EntityType.TURTLE) {
            cir.setReturnValue(entity.getMainHandStack().isIn(ItemTags.TURTLE_FOOD) || entity.getOffHandStack().isIn(ItemTags.TURTLE_FOOD));
        } else if (this.mob.getType() == EntityType.STRIDER) {
            cir.setReturnValue(entity.getMainHandStack().isIn(ItemTags.STRIDER_FOOD) || entity.getOffHandStack().isIn(ItemTags.STRIDER_FOOD));
        } else if (this.mob.getType() == EntityType.HORSE) {
            cir.setReturnValue(entity.getMainHandStack().isIn(ItemTags.HORSE_FOOD) || entity.getOffHandStack().isIn(ItemTags.HORSE_FOOD));
        } else if (this.mob.getType() == EntityType.LLAMA) {
            cir.setReturnValue(entity.getMainHandStack().isIn(ItemTags.LLAMA_FOOD) || entity.getOffHandStack().isIn(ItemTags.LLAMA_FOOD));
        } else if (this.mob.getType() == EntityType.PANDA) {
            cir.setReturnValue(entity.getMainHandStack().isIn(ItemTags.PANDA_FOOD) || entity.getOffHandStack().isIn(ItemTags.PANDA_FOOD));
        }
    }
}
