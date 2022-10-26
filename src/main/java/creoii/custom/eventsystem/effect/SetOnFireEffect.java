package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SetOnFireEffect extends Effect {
    private final int duration;
    private final float damage;
    private final boolean affectTarget;

    public SetOnFireEffect(int duration, float damage, boolean affectTarget) {
        super(Effect.SPAWN_ENTITY);
        this.duration = duration;
        this.damage = damage;
        this.affectTarget = affectTarget;
    }

    public static Effect getFromJson(JsonObject object) {
        int duration = JsonHelper.getInt(object, "duration", 0);
        float damage = JsonHelper.getFloat(object, "damage", 0f);
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target", false);
        return new SetOnFireEffect(duration, damage, affectTarget);
    }

    @Override
    public void runBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        if (!living.isFireImmune()) {
            living.setOnFireFor(duration);
            living.damage(DamageSource.ON_FIRE, damage);
        }
    }

    @Override
    public void runItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        if (!player.isFireImmune()) {
            player.setOnFireFor(duration);
            player.damage(DamageSource.ON_FIRE, damage);
        }
    }

    @Override
    public void runEntity(Entity entity, PlayerEntity player, Hand hand) {
        if (!entity.isFireImmune()) {
            entity.setOnFireFor(duration);
            entity.damage(DamageSource.ON_FIRE, damage);
        }
    }

    @Override
    public void runEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        Entity entity = affectTarget ? target : user;
        if (!entity.isFireImmune()) {
            entity.setOnFireFor(duration);
            entity.damage(DamageSource.ON_FIRE, damage);
        }
    }

    @Override
    public void runStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        if (!entity.isFireImmune()) {
            entity.setOnFireFor(duration);
            entity.damage(DamageSource.ON_FIRE, damage);
        }
    }

    @Override
    public void runWorld(World world) { }
}
