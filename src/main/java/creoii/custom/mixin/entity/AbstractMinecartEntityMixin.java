package creoii.custom.mixin.entity;

import creoii.custom.util.Constants;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractMinecartEntity.class)
public class AbstractMinecartEntityMixin extends Entity {
    public AbstractMinecartEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "getMaxSpeed", at = @At("RETURN"), cancellable = true)
    private void custom_customMinecartSpeed(CallbackInfoReturnable<Double> cir) {
        cir.setReturnValue((isTouchingWater() ? (Constants.MINECART_BASE_SPEED / 2d) : Constants.MINECART_BASE_SPEED) / 20d);
    }

    @Override
    protected void initDataTracker() { }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) { }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) { }

    @Override
    public Packet<?> createSpawnPacket() { return null; }
}
