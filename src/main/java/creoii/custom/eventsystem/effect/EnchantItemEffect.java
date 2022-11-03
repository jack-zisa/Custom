package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.Custom;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class EnchantItemEffect extends Effect {
    private Enchantment enchantment;
    private int level;
    private boolean randomly;
    private boolean ignoreCompatibility;
    private boolean ignoreTarget;
    private boolean ignoreSlotTypes;
    private boolean affectTarget;

    public EnchantItemEffect withValues(Enchantment enchantment, int level, boolean randomly, boolean affectTarget) {
        this.enchantment = enchantment;
        this.level = level;
        this.randomly = randomly;
        this.affectTarget = affectTarget;
        return this;
    }

    public Effect getFromJson(JsonObject object) {
        Enchantment enchantment = Registry.ENCHANTMENT.get(Identifier.tryParse(JsonHelper.getString(object, "enchantment")));
        int level = JsonHelper.getInt(object, "level", 1);
        boolean randomly = JsonHelper.getBoolean(object, "randomly", false);
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target", false);
        return withValues(enchantment, level, randomly, affectTarget);
    }

    private void run(ItemStack held) {
        if (randomly) {
            EnchantmentHelper.enchant(Custom.RANDOM, held, level, true);
        } else {
            if (EnchantmentHelper.getLevel(enchantment, held) < 1) {
                held.addEnchantment(enchantment, level);
            }
        }
    }

    @Override
    public void runBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        run(living.getStackInHand(hand));
    }

    @Override
    public void runItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        run(stack);
    }

    @Override
    public void runEntity(Entity entity, PlayerEntity player, Hand hand) {
        if (entity instanceof LivingEntity livingEntity) {
            run(livingEntity.getStackInHand(hand));
        }
    }

    @Override
    public void runEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        Entity entity = affectTarget ? target : user;
        if (entity instanceof LivingEntity livingEntity) {
            run(livingEntity.getStackInHand(livingEntity.getActiveHand()));
        }
    }

    @Override
    public void runStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        run(entity.getStackInHand(entity.getActiveHand()));
    }

    @Override
    public void runWorld(World world) { }
}
