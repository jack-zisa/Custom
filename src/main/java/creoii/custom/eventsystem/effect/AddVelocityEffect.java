package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.util.provider.ValueProvider;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
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
    private ValueProvider<Double> xVelocity;
    private ValueProvider<Double> yVelocity;
    private ValueProvider<Double> zVelocity;
    private boolean useLookVec;
    private boolean affectTarget;

    public AddVelocityEffect withValues(ValueProvider<Double> xVelocity, ValueProvider<Double> yVelocity, ValueProvider<Double> zVelocity, boolean useLookVec, boolean affectTarget) {
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.zVelocity = zVelocity;
        this.useLookVec = useLookVec;
        this.affectTarget = affectTarget;
        return this;
    }

    @SuppressWarnings("unchecked")
    public Effect getFromJson(JsonObject object) {
        ValueProvider<Double> xVelocity = (ValueProvider<Double>) ValueProvider.getFromJson(object, "x_velocity");
        ValueProvider<Double> yVelocity = (ValueProvider<Double>) ValueProvider.getFromJson(object, "y_velocity");
        ValueProvider<Double> zVelocity = (ValueProvider<Double>) ValueProvider.getFromJson(object, "z_velocity");
        boolean useLookVec = JsonHelper.getBoolean(object, "use_look_vec", true);
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target", false);
        return withValues(xVelocity, yVelocity, zVelocity, useLookVec, affectTarget);
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
    public void runEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        Vec3d look;
        if (affectTarget) {
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
