package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.util.json.CustomJsonObjects;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SendMessageEffect extends Effect {
    private Text text;
    private CustomJsonObjects.TextFormatting formatting;
    private boolean actionBar;

    public SendMessageEffect withValues(Text text, CustomJsonObjects.TextFormatting formatting, boolean actionBar) {
        this.text = text;
        this.formatting = formatting;
        this.actionBar = actionBar;
        return this;
    }

    public SendMessageEffect getFromJson(JsonObject object) {
        CustomJsonObjects.TextFormatting formatting = CustomJsonObjects.TextFormatting.get(object);
        MutableText text = MutableText.of(new LiteralTextContent(JsonHelper.getString(object, "text", ""))).formatted(formatting.formatting());
        for (Formatting formatting1 : formatting.formatting()) {
            text.formatted(formatting1);
        }
        boolean actionBar = JsonHelper.getBoolean(object, "action_bar", false);
        return withValues(text, formatting, actionBar);
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
    public void runEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
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
                System.out.println("sent message");
                player1.sendMessage(text, actionBar);
            });
        }
    }

    @Override
    public void runWorld(World world) {
        if (!world.isClient) {
            ((ServerWorld) world).getPlayers().forEach(player1 -> {
                player1.sendMessage(text, actionBar);
            });
        }
    }
}
