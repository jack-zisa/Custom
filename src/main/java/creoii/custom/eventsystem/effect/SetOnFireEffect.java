package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.util.provider.ValueProvider;
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
    private ValueProvider<Double> duration;
    private ValueProvider<Double> damage;
    private boolean affectTarget;

    public SetOnFireEffect withValues(ValueProvider<Double> duration, ValueProvider<Double> damage, boolean affectTarget) {
        this.duration = duration;
        this.damage = damage;
        this.affectTarget = affectTarget;
        return this;
    }

    @SuppressWarnings("unchecked")
    public SetOnFireEffect getFromJson(JsonObject object) {
        ValueProvider<Double> duration = (ValueProvider<Double>) ValueProvider.getFromJson(object, "duration");
        ValueProvider<Double> damage = (ValueProvider<Double>) ValueProvider.getFromJson(object, "damage");
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target", false);
        return withValues(duration, damage, affectTarget);
    }

    public void run(Entity entity) {
        if (!entity.isFireImmune()) {
            entity.setOnFireFor(duration.getValue().intValue());
            entity.damage(DamageSource.ON_FIRE, damage.getValue().floatValue());
        }
    }

    @Override
    public void runBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        run(living);
    }

    @Override
    public void runItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        run(player);
    }

    @Override
    public void runEntity(Entity entity, PlayerEntity player, Hand hand) {
        run(entity);
    }

    @Override
    public void runEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        run(affectTarget ? target : user);
    }

    @Override
    public void runStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        run(entity);
    }

    @Override
    public void runWorld(World world) { }
}
