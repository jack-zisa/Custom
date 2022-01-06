package creoii.custom.eventsystem.condition;

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
import net.minecraft.world.World;

public class CompositeCondition extends Condition {
    private final Condition first;
    private final Condition second;
    private final Type type;

    public CompositeCondition(Condition first, Condition second, Type type) {
        super(Condition.COMPOSITE);
        this.first = first;
        this.second = second;
        this.type = type;
    }

    public static Condition getFromJson(JsonObject object) {
        Condition first = getCondition(object, "first");
        Condition second = getCondition(object, "second");
        Type type = Type.byName(JsonHelper.getString(object, "type", "and"));
        return new CompositeCondition(first, second, type);
    }

    @Override
    public boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        if (type == Type.AND) {
            return first.testBlock(world, state, pos, living, hand) && second.testBlock(world, state, pos, living, hand);
        } else {
            return first.testBlock(world, state, pos, living, hand) || second.testBlock(world, state, pos, living, hand);
        }
    }

    @Override
    public boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        if (type == Type.AND) {
            return first.testItem(world, stack, pos, player, hand) && second.testItem(world, stack, pos, player, hand);
        } else {
            return first.testItem(world, stack, pos, player, hand) || second.testItem(world, stack, pos, player, hand);
        }
    }

    @Override
    public boolean testEntity(Entity entity, PlayerEntity player, Hand hand) {
        if (type == Type.AND) {
            return first.testEntity(entity, player, hand) && second.testEntity(entity, player, hand);
        } else {
            return first.testEntity(entity, player, hand) || second.testEntity(entity, player, hand);
        }
    }

    @Override
    public boolean testEnchantment(Entity user, Entity target, int level) {
        if (type == Type.AND) {
            return first.testEnchantment(user, target, level) && second.testEnchantment(user, target, level);
        } else {
            return first.testEnchantment(user, target, level) || second.testEnchantment(user, target, level);
        }
    }

    @Override
    public boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        if (type == Type.AND) {
            return first.testStatusEffect(statusEffect, entity, amplifier) && second.testStatusEffect(statusEffect, entity, amplifier);
        } else {
            return first.testStatusEffect(statusEffect, entity, amplifier) || second.testStatusEffect(statusEffect, entity, amplifier);
        }
    }

    public enum Type {
        AND,
        OR;

        public static Type byName(String str) {
            return str.equals("or") ? OR : AND;
        }
    }
}
