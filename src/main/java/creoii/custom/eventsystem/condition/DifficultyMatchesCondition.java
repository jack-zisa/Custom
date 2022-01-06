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
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class DifficultyMatchesCondition extends Condition {
    private final Difficulty difficulty;

    public DifficultyMatchesCondition(Difficulty difficulty) {
        super(Condition.DIFFICULTY_MATCHES);
        this.difficulty = difficulty;
    }

    public static Condition getFromJson(JsonObject object) {
        Difficulty difficulty = Difficulty.byName(JsonHelper.getString(object, "difficulty", "peaceful"));
        return new DifficultyMatchesCondition(difficulty);
    }

    @Override
    public boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        return world.getDifficulty() == difficulty;
    }

    @Override
    public boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        return world.getDifficulty() == difficulty;
    }

    @Override
    public boolean testEntity(Entity entity, PlayerEntity player, Hand hand) {
        return entity.getWorld().getDifficulty() == difficulty;
    }

    @Override
    public boolean testEnchantment(Entity user, Entity target, int level) {
        return user.getWorld().getDifficulty() == difficulty;
    }

    @Override
    public boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        return entity.getWorld().getDifficulty() == difficulty;
    }
}
