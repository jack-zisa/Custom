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

public class WithinYCondition extends Condition {
    private final int minY;
    private final int maxY;
    private final boolean useTargetPostition;

    public WithinYCondition(int minY, int maxY, boolean useTargetPostition) {
        super(Condition.WITHIN_Y);
        this.minY = minY;
        this.maxY = maxY;
        this.useTargetPostition = useTargetPostition;
    }

    public static Condition getFromJson(JsonObject object) {
        int minY = JsonHelper.getInt(object, "min_y", -64);
        int maxY = JsonHelper.getInt(object, "max_y", 320);
        boolean useTargetPosition = JsonHelper.getBoolean(object, "use_target_position", false);
        return new WithinYCondition(minY, maxY, useTargetPosition);
    }

    @Override
    public boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        return pos.getY() > minY && pos.getY() < maxY;
    }

    @Override
    public boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        return pos.getY() > minY && pos.getY() < maxY;
    }

    @Override
    public boolean testEntity(Entity entity, PlayerEntity player, Hand hand) {
        return entity.getY() > minY && entity.getY() < maxY;
    }

    @Override
    public boolean testEnchantment(Entity user, Entity target, int level) {
        return useTargetPostition ? target.getY() > minY && target.getY() < maxY : user.getY() > minY && user.getY() < maxY;
    }

    @Override
    public boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        return entity.getY() > minY && entity.getY() < maxY;
    }

    @Override
    public boolean testWorld(World world) {
        return false;
    }
}
