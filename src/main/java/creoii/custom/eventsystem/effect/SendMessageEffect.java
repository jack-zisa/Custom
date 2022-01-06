package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SendMessageEffect extends Effect {
    private final Text text;
    private final boolean actionBar;

    public SendMessageEffect(Text text, boolean actionBar) {
        super(Effect.SEND_MESSAGE);
        this.text = text;
        this.actionBar = actionBar;
    }

    public static Effect getFromJson(JsonObject object) {
        Text text = new LiteralText(JsonHelper.getString(object, "text", ""));
        boolean actionBar = JsonHelper.getBoolean(object, "action_bar", false);
        return new SendMessageEffect(text, actionBar);
    }

    @Override
    public void runBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        if (!world.isClient) {
            ((ServerWorld) world).getPlayers().forEach(player -> {
                player.sendMessage(text, actionBar);
            });
        }
    }

    @Override
    public void runItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            ((ServerWorld) world).getPlayers().forEach(player1 -> {
                player1.sendMessage(text, actionBar);
            });
        }
    }

    @Override
    public void runEntity(Entity entity, PlayerEntity player, Hand hand) {
        if (!entity.getWorld().isClient) {
            ((ServerWorld) entity.getWorld()).getPlayers().forEach(player1 -> {
                player1.sendMessage(text, actionBar);
            });
        }
    }

    @Override
    public void runEnchantment(Entity user, Entity target, int level) {
        if (!user.getWorld().isClient) {
            ((ServerWorld) user.getWorld()).getPlayers().forEach(player1 -> {
                player1.sendMessage(text, actionBar);
            });
        }
    }

    @Override
    public void runStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        if (!entity.getWorld().isClient) {
            ((ServerWorld) entity.getWorld()).getPlayers().forEach(player1 -> {
                player1.sendMessage(text, actionBar);
            });
        }
    }

    @Override
    public void runWorld(World world) {
        if (!world.isClient) {
            System.out.println("run");
            ((ServerWorld) world).getPlayers().forEach(player1 -> {
                player1.sendMessage(text, actionBar);
            });
        }
    }
}
