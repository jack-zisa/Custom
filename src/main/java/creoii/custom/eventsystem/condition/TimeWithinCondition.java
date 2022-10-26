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
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TimeWithinCondition extends Condition {
    private final int minTime;
    private final int maxTime;

    public TimeWithinCondition(int minTime, int maxTime) {
        super(Condition.TIME_WITHIN);
        this.minTime = minTime;
        this.maxTime = maxTime;
    }

    public static Condition getFromJson(JsonObject object) {
        int minTime = JsonHelper.getInt(object, "min_time", 0);
        int maxTime = JsonHelper.getInt(object, "max_time", 24000);
        return new TimeWithinCondition(minTime, maxTime);
    }

    private boolean test(World world) {
        return world.getTime() > minTime && world.getTime() < maxTime;
    }

    @Override
    public boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        return test(world);
    }

    @Override
    public boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        return test(world);
    }

    @Override
    public boolean testEntity(Entity entity, PlayerEntity player, Hand hand) {
        return test(entity.getWorld());
    }

    @Override
    public boolean testEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        return test(user.getWorld());
    }

    @Override
    public boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        return test(entity.getWorld());
    }

    @Override
    public boolean testWorld(World world) {
        return test(world);
    }
}
