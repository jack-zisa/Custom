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

public class PlayerLevelWithinCondition extends Condition {
    private final int minLevel;
    private final int maxLevel;
    private final boolean useTargetPostition;

    public PlayerLevelWithinCondition(int minLevel, int maxLevel, boolean useTargetPostition) {
        super(Condition.PLAYER_LEVEL_WITHIN);
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.useTargetPostition = useTargetPostition;
    }

    public static Condition getFromJson(JsonObject object) {
        int minLevel = JsonHelper.getInt(object, "min_level", 0);
        int maxLevel = JsonHelper.getInt(object, "max_level", 1);
        boolean useTargetPosition = JsonHelper.getBoolean(object, "use_target_position", false);
        return new PlayerLevelWithinCondition(minLevel, maxLevel, useTargetPosition);
    }

    @Override
    public boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        if (living instanceof PlayerEntity player) {
            return player.experienceLevel > minLevel && player.experienceLevel < maxLevel;
        }
        return false;
    }

    @Override
    public boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        return player.experienceLevel > minLevel && player.experienceLevel < maxLevel;
    }

    @Override
    public boolean testEntity(Entity entity, PlayerEntity player, Hand hand) {
        if (entity instanceof PlayerEntity player1) {
            return player1.experienceLevel > minLevel && player1.experienceLevel < maxLevel;
        }
        return false;
    }

    @Override
    public boolean testEnchantment(Entity user, Entity target, int level) {
        Entity entity = useTargetPostition ? target : user;
        if (entity instanceof PlayerEntity player) {
            return player.experienceLevel > minLevel && player.experienceLevel < maxLevel;
        }
        return false;
    }

    @Override
    public boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity player) {
            return player.experienceLevel > minLevel && player.experienceLevel < maxLevel;
        }
        return false;
    }
}
