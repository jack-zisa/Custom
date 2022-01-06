package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.util.StringToObject;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HoldingItemCondition extends Condition {
    private final Item item;
    private final Hand hand;

    public HoldingItemCondition(Item item, Hand hand) {
        super(Condition.HOLDING_ITEM);
        this.item = item;
        this.hand = hand;
    }

    public static Condition getFromJson(JsonObject object) {
        Item item = JsonHelper.getItem(object, "item", Items.AIR);
        Hand hand = StringToObject.hand(JsonHelper.getString(object, "hand", "mainhand"));
        return new HoldingItemCondition(item, hand);
    }

    @Override
    public boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        return living.getStackInHand(this.hand).isOf(item);
    }

    @Override
    public boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        return player.getStackInHand(this.hand).getItem() == stack.getItem();
    }

    @Override
    public boolean testEntity(Entity entity, PlayerEntity player, Hand hand) {
        return player.getStackInHand(this.hand).isOf(item);
    }

    @Override
    public boolean testEnchantment(Entity user, Entity target, int level) {
        if (user instanceof LivingEntity) {
            return ((LivingEntity) user).getStackInHand(this.hand).isOf(item);
        } else return false;
    }

    @Override
    public boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        return entity.getStackInHand(this.hand).isOf(item);
    }
}
