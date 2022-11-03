package creoii.custom.mixin.entity;

import creoii.custom.util.tags.EntityTypeTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = { MobEntity.class, AnimalEntity.class})
public abstract class MilkableEntityMixin extends Entity {
    public MilkableEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "interactMob(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;", at = @At("HEAD"), cancellable = true)
    private void custom_milkableMobs(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (this.getType().isIn(EntityTypeTags.MILKABLES)) {
            ItemStack itemStack = player.getStackInHand(hand);
            if (itemStack.isOf(Items.BUCKET)) {
                player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
                ItemStack itemStack2 = ItemUsage.exchangeStack(itemStack, player, Items.MILK_BUCKET.getDefaultStack());
                player.setStackInHand(hand, itemStack2);
                cir.setReturnValue(ActionResult.success(this.world.isClient));
            }
        }
    }
}