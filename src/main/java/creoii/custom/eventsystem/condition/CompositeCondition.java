package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CompositeCondition extends Condition {
    private Condition first;
    private Condition second;
    private Comparison comparison;

    public CompositeCondition withValues(Condition first, Condition second, Comparison comparison) {
        this.first = first;
        this.second = second;
        this.comparison = comparison;
        return this;
    }

    public CompositeCondition getFromJson(JsonObject object) {
        Condition first = Condition.getCondition(object, Identifier.tryParse(JsonHelper.getString(object, "first")));
        Condition second = Condition.getCondition(object, Identifier.tryParse(JsonHelper.getString(object, "second")));
        Comparison comparison = Comparison.byName(JsonHelper.getString(object, "comparison", "and"));
        return withValues(first, second, comparison);
    }

    @Override
    public boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        if (comparison == Comparison.AND) {
            return first.testBlock(world, state, pos, living, hand) && second.testBlock(world, state, pos, living, hand);
        } else {
            return first.testBlock(world, state, pos, living, hand) || second.testBlock(world, state, pos, living, hand);
        }
    }

    @Override
    public boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        if (comparison == Comparison.AND) {
            return first.testItem(world, stack, pos, player, hand) && second.testItem(world, stack, pos, player, hand);
        } else {
            return first.testItem(world, stack, pos, player, hand) || second.testItem(world, stack, pos, player, hand);
        }
    }

    @Override
    public boolean testEntity(Entity entity, PlayerEntity player, Hand hand) {
        if (comparison == Comparison.AND) {
            return first.testEntity(entity, player, hand) && second.testEntity(entity, player, hand);
        } else {
            return first.testEntity(entity, player, hand) || second.testEntity(entity, player, hand);
        }
    }

    @Override
    public boolean testEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        if (comparison == Comparison.AND) {
            return first.testEnchantment(enchantment, user, target, level) && second.testEnchantment(enchantment, user, target, level);
        } else {
            return first.testEnchantment(enchantment, user, target, level) || second.testEnchantment(enchantment, user, target, level);
        }
    }

    @Override
    public boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        if (comparison == Comparison.AND) {
            return first.testStatusEffect(statusEffect, entity, amplifier) && second.testStatusEffect(statusEffect, entity, amplifier);
        } else {
            return first.testStatusEffect(statusEffect, entity, amplifier) || second.testStatusEffect(statusEffect, entity, amplifier);
        }
    }

    @Override
    public boolean testWorld(World world) {
        if (comparison == Comparison.AND) {
            return first.testWorld(world) && second.testWorld(world);
        } else {
            return first.testWorld(world) || second.testWorld(world);
        }
    }

    public enum Comparison {
        AND,
        OR;

        public static Comparison byName(String str) {
            return str.equals("or") ? OR : AND;
        }
    }
}
