package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
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
    private final float xVelocity;
    private final float yVelocity;
    private final float zVelocity;
    private final boolean useLookVec;
    private final boolean useTargetPosition;

    public AddVelocityEffect(float xVelocity, float yVelocity, float zVelocity, boolean useLookVec, boolean useTargetPosition) {
        super(Effect.ADD_VELOCITY);
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.zVelocity = zVelocity;
        this.useLookVec = useLookVec;
        this.useTargetPosition = useTargetPosition;
    }

    public static Effect getFromJson(JsonObject object) {
        float xVelocity = JsonHelper.getFloat(object, "x_velocity", 0f);
        float yVelocity = JsonHelper.getFloat(object, "y_velocity", 0f);
        float zVelocity = JsonHelper.getFloat(object, "z_velocity", 0f);
        boolean useLookVec = JsonHelper.getBoolean(object, "use_look_vec", true);
        boolean useTargetPosition = JsonHelper.getBoolean(object, "use_target_position", true);
        return new AddVelocityEffect(xVelocity, yVelocity, zVelocity, useLookVec, useTargetPosition);
    }

    @Override
    public void runBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        Vec3d look = living.getRotationVector();
        living.addVelocity(look.x * xVelocity, look.y * yVelocity, look.z * zVelocity);
    }

    @Override
    public void runItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        Vec3d look = player.getRotationVector();
        player.addVelocity(look.x * xVelocity, look.y * yVelocity, look.z * zVelocity);
    }

    @Override
    public void runEntity(Entity entity, PlayerEntity player, Hand hand) {
        Vec3d look = entity.getRotationVector();
        entity.addVelocity(look.x * xVelocity, look.y * yVelocity, look.z * zVelocity);
    }

    @Override
    public void runEnchantment(Entity user, Entity target, int level) {
        Vec3d look;
        if (useTargetPosition) {
            look = target.getRotationVector();
            target.addVelocity(look.x * xVelocity, look.y * yVelocity, look.z * zVelocity);
        } else {
            look = user.getRotationVector();
            user.addVelocity(look.x * xVelocity, look.y * yVelocity, look.z * zVelocity);
        }
    }

    @Override
    public void runStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        Vec3d look = entity.getRotationVector();
        entity.addVelocity(look.x * xVelocity, look.y * yVelocity, look.z * zVelocity);
    }

    @Override
    public void runWorld(World world) { }
}
