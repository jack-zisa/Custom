package creoii.custom.mixin.entity;

import creoii.custom.registry.AttributeRegistry;
import creoii.custom.util.tags.EntityTypeTags;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.TagKey;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow public abstract double getAttributeValue(EntityAttribute attribute);
    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);
    @Shadow @Nullable public abstract EntityAttributeInstance getAttributeInstance(EntityAttribute attribute);
    @Shadow protected abstract int computeFallDamage(float fallDistance, float damageMultiplier);
    @Shadow public abstract Vec3d applyFluidMovingSpeed(double gravity, boolean falling, Vec3d motion);
    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    private static final UUID SLOW_FALLING_ID = UUID.fromString("A5B6CF2A-2F7C-31EF-9022-7C3E7D5E6ABA");
    private static final EntityAttributeModifier SLOW_FALLING = new EntityAttributeModifier(SLOW_FALLING_ID, "Slow falling acceleration reduction", -0.07, EntityAttributeModifier.Operation.ADDITION);
    private static EntityAttributeInstance gravity;

    private LivingEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean onKilledOther(ServerWorld world, LivingEntity other) {
        boolean bl = super.onKilledOther(world, other);
        if ((world.getDifficulty() == Difficulty.NORMAL || world.getDifficulty() == Difficulty.HARD) && other instanceof VillagerEntity villagerEntity) {
            if (world.getDifficulty() != Difficulty.HARD && this.random.nextBoolean()) {
                return bl;
            }

            ZombieVillagerEntity zombieVillagerEntity = villagerEntity.convertTo(EntityType.ZOMBIE_VILLAGER, false);
            zombieVillagerEntity.initialize(world, world.getLocalDifficulty(zombieVillagerEntity.getBlockPos()), SpawnReason.CONVERSION, new ZombieEntity.ZombieData(false, true), (NbtCompound)null);
            zombieVillagerEntity.setVillagerData(villagerEntity.getVillagerData());
            zombieVillagerEntity.setGossipData(villagerEntity.getGossip().serialize(NbtOps.INSTANCE).getValue());
            zombieVillagerEntity.setOfferData(villagerEntity.getOffers().toNbt());
            zombieVillagerEntity.setXp(villagerEntity.getExperience());
            if (!this.isSilent()) {
                world.syncWorldEvent(null, 1026, this.getBlockPos(), 0);
            }

            bl = false;
        }

        return bl;
    }
    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (this.getType().isIn(EntityTypeTags.VEHICLES)) {
            if (player.shouldCancelInteraction()) return ActionResult.PASS;
            return this.world.isClient ? ActionResult.PASS : player.startRiding(this) ? ActionResult.CONSUME : ActionResult.PASS;
        } return ActionResult.PASS;
    }

    @Override
    public boolean isPushedByFluids() {
        return !this.getType().isIn(EntityTypeTags.IMMOVABLE_BY_FLUIDS);
    }

    @Inject(method = "canWalkOnFluid", at = @At("RETURN"), cancellable = true)
    private void custom_injectFluidWalkers(FluidState state, CallbackInfoReturnable<Boolean> cir) {
        cir.cancel();
        cir.setReturnValue(this.getType().isIn(EntityTypeTags.WALKS_ON_FLUIDS));
    }

    @Inject(method = "createLivingAttributes", at = @At("RETURN"))
    private static void custom_createNewAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        cir.getReturnValue().add(AttributeRegistry.GENERIC_GRAVITY).add(AttributeRegistry.GENERIC_SWIM_SPEED);
    }

    @Inject(method = "knockDownwards", at = @At("HEAD"), cancellable = true)
    private void custom_applyKnockbackSwimSpeed(CallbackInfo ci) {
        setVelocity(getVelocity().add(0d, -.03999999910593033d * getAttributeValue(AttributeRegistry.GENERIC_GRAVITY), 0d));
        ci.cancel();
    }

    @Inject(method = "swimUpward", at = @At("HEAD"), cancellable = true)
    private void custom_applyUpwardSwimSpeed(TagKey<Fluid> fluid, CallbackInfo ci) {
        setVelocity(getVelocity().add(0d, .03999999910593033d * getAttributeValue(AttributeRegistry.GENERIC_SWIM_SPEED), 0d));
        ci.cancel();
    }

    @Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;updateVelocity(FLnet/minecraft/util/math/Vec3d;)V"))
    private void custom_applySwimSpeed(LivingEntity entity, float speed, Vec3d movementInput) {
        speed *= getAttributeValue(AttributeRegistry.GENERIC_SWIM_SPEED);
        updateVelocity(speed, movementInput);
    }

    @Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;applyFluidMovingSpeed(DZLnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;"))
    private Vec3d custom_applyGravity(LivingEntity entity, double d, boolean bl, Vec3d vec3d) {
        return applyFluidMovingSpeed(entity.getAttributeValue(AttributeRegistry.GENERIC_GRAVITY), bl, vec3d);
    }

    @ModifyVariable(method = "travel", at = @At("STORE"), ordinal = 0)
    private double custom_modifyGravityVariable(double d) {
        gravity = getAttributeInstance(AttributeRegistry.GENERIC_GRAVITY);
        if (hasStatusEffect(StatusEffects.SLOW_FALLING) && !gravity.hasModifier(SLOW_FALLING)) gravity.addTemporaryModifier(SLOW_FALLING);
        return gravity.getValue();
    }

    @Inject(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getFluidState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/fluid/FluidState;", shift = At.Shift.BEFORE))
    private void custom_removeSlowFallingModifier(Vec3d movementInput, CallbackInfo ci) {
        if (gravity.hasModifier(SLOW_FALLING)) gravity.removeModifier(SLOW_FALLING);
    }

    @Redirect(method = "handleFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;computeFallDamage(FF)I"))
    private int custom_handleFallDamageGravity(LivingEntity entity, float fallDistance, float damageMultiplier) {
        return computeFallDamage(fallDistance * (float) (entity.getAttributeValue(AttributeRegistry.GENERIC_GRAVITY) * 12.5f), damageMultiplier);
    }
}