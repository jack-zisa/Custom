package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.util.math.DoubleValueHolder;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ApplyStatusEffectEffect extends Effect {
    private StatusEffect statusEffect;
    private DoubleValueHolder amplifier;
    private DoubleValueHolder duration;
    private boolean ambient;
    private boolean showParticles;
    private boolean showIcon;
    private boolean affectTarget;

    public ApplyStatusEffectEffect withValues(StatusEffect statusEffect, DoubleValueHolder amplifier, DoubleValueHolder duration, boolean ambient, boolean showParticles, boolean showIcon, boolean affectTarget) {
        this.statusEffect = statusEffect;
        this.amplifier = amplifier;
        this.duration = duration;
        this.ambient = ambient;
        this.showParticles = showParticles;
        this.showIcon = showIcon;
        this.affectTarget = affectTarget;
        return this;
    }

    public Effect getFromJson(JsonObject object) {
        StatusEffect statusEffect = Registry.STATUS_EFFECT.get(Identifier.tryParse(JsonHelper.getString(object, "status_effect")));
        DoubleValueHolder amplifier = DoubleValueHolder.getFromJson(object, "amplifier");
        DoubleValueHolder duration = DoubleValueHolder.getFromJson(object, "duration");
        boolean ambient = JsonHelper.getBoolean(object, "ambient", false);
        boolean showParticles = JsonHelper.getBoolean(object, "show_particles", true);
        boolean showIcon = JsonHelper.getBoolean(object, "show_icon", true);
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target", false);
        return withValues(statusEffect, amplifier, duration, ambient, showParticles, showIcon, affectTarget);
    }

    private void run(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.addStatusEffect(new StatusEffectInstance(statusEffect, (int) amplifier.getValue(), (int) duration.getValue(), ambient, showParticles, showIcon));
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
}
