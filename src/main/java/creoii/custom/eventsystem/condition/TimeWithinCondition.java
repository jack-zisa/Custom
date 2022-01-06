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

    @Override
    public boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        return world.getTime() > minTime && world.getTime() < maxTime;
    }

    @Override
    public boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        return world.getTime() > minTime && world.getTime() < maxTime;
    }

    @Override
    public boolean testEntity(Entity entity, PlayerEntity player, Hand hand) {
        return entity.getWorld().getTime() > minTime && entity.getWorld().getTime() < maxTime;
    }

    @Override
    public boolean testEnchantment(Entity user, Entity target, int level) {
        return user.getWorld().getTime() > minTime && user.getWorld().getTime() < maxTime;
    }

    @Override
    public boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        return entity.getWorld().getTime() > minTime && entity.getWorld().getTime() < maxTime;
    }

    @Override
    public boolean testWorld(World world) {
        return world.getTime() > minTime && world.getTime() < maxTime;
    }
}
