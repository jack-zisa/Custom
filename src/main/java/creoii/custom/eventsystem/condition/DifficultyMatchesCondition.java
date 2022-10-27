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
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class DifficultyMatchesCondition extends Condition {
    private Difficulty difficulty;

    public DifficultyMatchesCondition withValues(Difficulty difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    public DifficultyMatchesCondition getFromJson(JsonObject object) {
        Difficulty difficulty = Difficulty.byName(JsonHelper.getString(object, "difficulty", "peaceful"));
        return withValues(difficulty);
    }

    private boolean test(World world) {
        return world.getDifficulty() == difficulty;
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
