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
import net.minecraft.world.World;

public class GiveExperienceEffect extends Effect {
    private ValueProvider<Double> amount;
    private Type type;
    private boolean affectTarget;

    public GiveExperienceEffect withValues(ValueProvider<Double> amount, Type type, boolean affectTarget) {
        this.amount = amount;
        this.type = type;
        this.affectTarget = affectTarget;
        return this;
    }

    @SuppressWarnings("unchecked")
    public GiveExperienceEffect getFromJson(JsonObject object) {
        ValueProvider<Double> amount = (ValueProvider<Double>) ValueProvider.getFromJson(object, "amount");
        Type type = Type.valueOf(JsonHelper.getString(object, "type", "experience").toUpperCase());
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target", false);
        return withValues(amount, type, affectTarget);
    }

    private void run(Entity entity) {
        if (entity instanceof PlayerEntity playerEntity) {
            if (type == Type.EXPERIENCE) playerEntity.addExperience(amount.getValue().intValue());
            else playerEntity.addExperienceLevels(amount.getValue().intValue());
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
        run(player);
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

    enum Type {
        EXPERIENCE,
        LEVELS;
    }
}
