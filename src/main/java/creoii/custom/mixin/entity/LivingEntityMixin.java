package creoii.custom.mixin.entity;

import creoii.custom.util.tags.EntityTypeTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
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
    private void custom$injectFluidWalkers(FluidState state, CallbackInfoReturnable<Boolean> cir) {
        cir.cancel();
        cir.setReturnValue(this.getType().isIn(EntityTypeTags.WALKS_ON_FLUIDS));
    }
}