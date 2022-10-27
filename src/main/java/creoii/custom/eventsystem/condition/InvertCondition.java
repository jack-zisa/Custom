package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.Optional;

public class InvertCondition extends Condition {
    private Condition condition;

    public InvertCondition withValues(Condition condition) {
        this.condition = condition;
        return this;
    }

    public InvertCondition getFromJson(JsonObject object) {
        Condition condition = Condition.getCondition(object, Identifier.tryParse(JsonHelper.getString(object, "condition")));
        return withValues(condition);
    }

    @Override
    public boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        return !condition.testBlock(world, state, pos, living, hand);
    }

    @Override
    public boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        return !condition.testItem(world, stack, pos, player, hand);
    }

    @Override
    public boolean testEntity(Entity entity, PlayerEntity player, Hand hand) {
        return !condition.testEntity(entity, player, hand);
    }

    @Override
    public boolean testEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        return condition.testEnchantment(enchantment, user, target, level);
    }

    @Override
    public boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        return condition.testStatusEffect(statusEffect, entity, amplifier);
    }

    @Override
    public boolean testWorld(World world) {
        return condition.testWorld(world);
    }
}
