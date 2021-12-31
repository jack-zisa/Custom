package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.util.StringToObject;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HeldItemCondition extends Condition {
    private final Item item;
    private final Hand hand;

    public HeldItemCondition(Item item, Hand hand) {
        super("held_item");
        this.item = item;
        this.hand = hand;
    }

    public static Condition getFromJson(JsonObject object) {
        Item item = JsonHelper.getItem(object, "item", Items.AIR);
        Hand hand = StringToObject.hand(JsonHelper.getString(object, "hand", "mainhand"));
        return new HeldItemCondition(item, hand);
    }

    @Override
    public boolean testWorld(World world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean testBlock(World world, BlockState state, BlockPos pos, PlayerEntity player, Hand hand) {
        return player.getStackInHand(this.hand).isOf(item);
    }

    @Override
    public boolean testItem(World world, Item item, BlockPos pos, PlayerEntity player, Hand hand) {
        return player.getStackInHand(this.hand).isOf(item);
    }

    @Override
    public boolean testEntity(Entity entity, PlayerEntity player, Hand hand) {
        return player.getStackInHand(this.hand).isOf(item);
    }
}
