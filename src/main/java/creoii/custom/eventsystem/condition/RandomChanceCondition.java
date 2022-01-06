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

public class RandomChanceCondition extends Condition {
    private final float chance;

    public RandomChanceCondition(float chance) {
        super(Condition.RANDOM_CHANCE);
        this.chance = chance;
    }

    public static Condition getFromJson(JsonObject object) {
        float chance = JsonHelper.getFloat(object, "chance", 0f);
        return new RandomChanceCondition(chance);
    }

    @Override
    public boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        return world.getRandom().nextFloat() < chance;
    }

    @Override
    public boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        return world.getRandom().nextFloat() < chance;
    }

    @Override
    public boolean testEntity(Entity entity, PlayerEntity player, Hand hand) {
        return entity.getWorld().getRandom().nextFloat() < chance;
    }

    @Override
    public boolean testEnchantment(Entity user, Entity target, int level) {
        return user.getWorld().getRandom().nextFloat() < chance;
    }

    @Override
    public boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        return entity.getWorld().getRandom().nextFloat() < chance;
    }

    @Override
    public boolean testWorld(World world) {
        return world.getRandom().nextFloat() < chance;
    }
}
