package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.util.json.CustomJsonHelper;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class SpawnParticleEffect extends Effect {
    private ParticleEffect particle;
    private int amount;
    private double xVelocity;
    private double yVelocity;
    private double zVelocity;
    private BlockPos offset;
    private boolean important;
    private boolean affectTarget;

    public SpawnParticleEffect withValues(ParticleEffect particleType, int amount, double xVelocity, double yVelocity, double zVelocity, BlockPos offset, boolean important, boolean affectTarget) {
        this.particle = particleType;
        this.amount = amount;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.zVelocity = zVelocity;
        this.offset = offset;
        this.important = important;
        this.affectTarget = affectTarget;
        return this;
    }

    public Effect getFromJson(JsonObject object) {
        DefaultParticleType particleType = (DefaultParticleType) Registry.PARTICLE_TYPE.get(Identifier.tryParse(JsonHelper.getString(object, "particle")));
        int amount = JsonHelper.getInt(object, "amount", 1);
        double xVelocity = JsonHelper.getInt(object, "x_velocity", 1);
        double yVelocity = JsonHelper.getInt(object, "y_velocity", 1);
        double zVelocity = JsonHelper.getInt(object, "z_velocity", 1);
        BlockPos offset = CustomJsonHelper.getBlockPos(object, "offset");
        boolean important = JsonHelper.getBoolean(object, "important", false);
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target", false);
        return withValues(particleType, amount, xVelocity, yVelocity, zVelocity, offset, important, affectTarget);
    }

    private void run(World world, BlockPos pos) {
        if (important) {
            for (int i = 0; i < amount; ++i) {
                world.addImportantParticle(particle, pos.getX() + offset.getX(), pos.getY() + offset.getY(), pos.getZ() + offset.getZ(), xVelocity, yVelocity, zVelocity);
            }
        } else {
            for (int i = 0; i < amount; ++i) {
                world.addParticle(particle, pos.getX() + offset.getX(), pos.getY() + offset.getY(), pos.getZ() + offset.getZ(), xVelocity, yVelocity, zVelocity);
            }
        }
    }

    @Override
    public void runBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        run(world, pos);
    }

    @Override
    public void runItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        run(world, pos);
    }

    @Override
    public void runEntity(Entity entity, PlayerEntity player, Hand hand) {
        run(entity.getWorld(), entity.getBlockPos());
    }

    @Override
    public void runEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        run(user.getWorld(), affectTarget ? target.getBlockPos() : user.getBlockPos());
    }

    @Override
    public void runStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        run(entity.getWorld(), entity.getBlockPos());
    }

    @Override
    public void runWorld(World world) { }
}
