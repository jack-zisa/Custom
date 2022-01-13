package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.util.math.ValueHolder;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AddVelocityEffect extends Effect {
    private final ValueHolder xVelocity;
    private final ValueHolder yVelocity;
    private final ValueHolder zVelocity;
    private final boolean useLookVec;
    private final boolean useTargetPosition;

    public AddVelocityEffect(ValueHolder xVelocity, ValueHolder yVelocity, ValueHolder zVelocity, boolean useLookVec, boolean useTargetPosition) {
        super(Effect.ADD_VELOCITY);
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.zVelocity = zVelocity;
        this.useLookVec = useLookVec;
        this.useTargetPosition = useTargetPosition;
    }

    public static Effect getFromJson(JsonObject object) {
        ValueHolder xVelocity = ValueHolder.getFromJson(object, "x_velocity");
        ValueHolder yVelocity = ValueHolder.getFromJson(object, "y_velocity");
        ValueHolder zVelocity = ValueHolder.getFromJson(object, "z_velocity");
        boolean useLookVec = JsonHelper.getBoolean(object, "use_look_vec", true);
        boolean useTargetPosition = JsonHelper.getBoolean(object, "use_target_position", false);
        return new AddVelocityEffect(xVelocity, yVelocity, zVelocity, useLookVec, useTargetPosition);
    }

    @Override
    public void runBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        Vec3d look = living.getRotationVector();
        living.addVelocity(look.x * xVelocity.getValue(), look.y * yVelocity.getValue(), look.z * zVelocity.getValue());
    }

    @Override
    public void runItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        Vec3d look = player.getRotationVector();
        player.addVelocity(look.x * xVelocity.getValue(), look.y * yVelocity.getValue(), look.z * zVelocity.getValue());
    }

    @Override
    public void runEntity(Entity entity, PlayerEntity player, Hand hand) {
        Vec3d look = entity.getRotationVector();
        entity.addVelocity(look.x * xVelocity.getValue(), look.y * yVelocity.getValue(), look.z * zVelocity.getValue());
    }

    @Override
    public void runEnchantment(Entity user, Entity target, int level) {
        Vec3d look;
        if (useTargetPosition) {
            look = target.getRotationVector();
            target.addVelocity(look.x * xVelocity.getValue(), look.y * yVelocity.getValue(), look.z * zVelocity.getValue());
        } else {
            look = user.getRotationVector();
            user.addVelocity(look.x * xVelocity.getValue(), look.y * yVelocity.getValue(), look.z * zVelocity.getValue());
        }
    }

    @Override
    public void runStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        Vec3d look = entity.getRotationVector();
        entity.addVelocity(look.x * xVelocity.getValue(), look.y * yVelocity.getValue(), look.z * zVelocity.getValue());
    }

    @Override
    public void runWorld(World world) { }
}
