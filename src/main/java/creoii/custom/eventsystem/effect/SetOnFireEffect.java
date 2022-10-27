package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.util.math.DoubleValueHolder;
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
    private DoubleValueHolder duration;
    private DoubleValueHolder damage;
    private boolean affectTarget;

    public SetOnFireEffect withValues(DoubleValueHolder duration, DoubleValueHolder damage, boolean affectTarget) {
        this.duration = duration;
        this.damage = damage;
        this.affectTarget = affectTarget;
        return this;
    }

    public SetOnFireEffect getFromJson(JsonObject object) {
        DoubleValueHolder duration = DoubleValueHolder.getFromJson(object, "duration");
        DoubleValueHolder damage = DoubleValueHolder.getFromJson(object, "damage");
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target", false);
        return withValues(duration, damage, affectTarget);
    }

    @Override
    public void runBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        if (!living.isFireImmune()) {
            living.setOnFireFor((int) duration.getValue());
            living.damage(DamageSource.ON_FIRE, (float) damage.getValue());
        }
    }

    @Override
    public void runItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        if (!player.isFireImmune()) {
            player.setOnFireFor((int) duration.getValue());
            player.damage(DamageSource.ON_FIRE, (float) damage.getValue());
        }
    }

    @Override
    public void runEntity(Entity entity, PlayerEntity player, Hand hand) {
        if (!entity.isFireImmune()) {
            entity.setOnFireFor((int) duration.getValue());
            entity.damage(DamageSource.ON_FIRE, (float) damage.getValue());
        }
    }

    @Override
    public void runEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        Entity entity = affectTarget ? target : user;
        if (!entity.isFireImmune()) {
            entity.setOnFireFor((int) duration.getValue());
            entity.damage(DamageSource.ON_FIRE, (float) damage.getValue());
        }
    }

    @Override
    public void runStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        if (!entity.isFireImmune()) {
            entity.setOnFireFor((int) duration.getValue());
            entity.damage(DamageSource.ON_FIRE, (float) damage.getValue());
        }
    }

    @Override
    public void runWorld(World world) { }
}
