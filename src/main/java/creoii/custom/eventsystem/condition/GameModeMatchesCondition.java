package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;

public class GameModeMatchesCondition extends Condition {
    private final GameMode gameMode;
    private final boolean useTargetPosition;

    public GameModeMatchesCondition(GameMode gameMode, boolean useTargetPosition) {
        super(Condition.GAMEMODE_MATCHES);
        this.gameMode = gameMode;
        this.useTargetPosition = useTargetPosition;
    }

    public static Condition getFromJson(JsonObject object) {
        GameMode gameMode = GameMode.byName(JsonHelper.getString(object, "gamemode", "survival"));
        boolean useTargetPosition = JsonHelper.getBoolean(object, "use_target_position", false);
        return new GameModeMatchesCondition(gameMode, useTargetPosition);
    }

    @Override
    public boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        if (living instanceof ServerPlayerEntity player) {
            return player.interactionManager.getGameMode() == gameMode;
        } return false;
    }

    @Override
    public boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        if (player instanceof ServerPlayerEntity player1) {
            return player1.interactionManager.getGameMode() == gameMode;
        } return false;
    }

    @Override
    public boolean testEntity(Entity entity, PlayerEntity player, Hand hand) {
        if (player instanceof ServerPlayerEntity player1) {
            return player1.interactionManager.getGameMode() == gameMode;
        } return false;
    }

    @Override
    public boolean testEnchantment(Entity user, Entity target, int level) {
        Entity entity = useTargetPosition ? target : user;
        if (entity instanceof ServerPlayerEntity player) {
            return player.interactionManager.getGameMode() == gameMode;
        } return false;
    }

    @Override
    public boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        if (entity instanceof ServerPlayerEntity player) {
            return player.interactionManager.getGameMode() == gameMode;
        } return false;
    }
}
