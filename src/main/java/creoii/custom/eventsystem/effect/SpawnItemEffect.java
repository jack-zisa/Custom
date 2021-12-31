package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpawnItemEffect extends Effect {
    private final Item item;
    private final int amount;

    private ItemEntity itemEntity;

    public SpawnItemEffect(Item item, int amount) {
        super("spawn_item");
        this.item = item;
        this.amount = amount;
    }

    public static Effect getFromJson(JsonObject object) {
        Item item = JsonHelper.getItem(object, "item", Items.AIR);
        int amount = JsonHelper.getInt(object, "amount", 1);
        return new SpawnItemEffect(item, amount);
    }

    @Override
    public void runWorld(World world, BlockPos pos) {
        itemEntity = new ItemEntity(world, pos.getX() + .5f, pos.getY() + .5f, pos.getZ() + .5f, new ItemStack(item, amount));
        itemEntity.setToDefaultPickupDelay();
        world.spawnEntity(itemEntity);
    }

    @Override
    public void runBlock(World world, BlockState state, BlockPos pos, PlayerEntity player, Hand hand) {
        itemEntity = new ItemEntity(world, pos.getX() + .5f, pos.getY() + .5f, pos.getZ() + .5f, new ItemStack(item, amount));
        itemEntity.setToDefaultPickupDelay();
        world.spawnEntity(itemEntity);
    }

    @Override
    public void runItem(World world, Item item, BlockPos pos, PlayerEntity player, Hand hand) {
        itemEntity = new ItemEntity(world, pos.getX() + .5f, pos.getY() + .5f, pos.getZ() + .5f, new ItemStack(item, amount));
        itemEntity.setToDefaultPickupDelay();
        world.spawnEntity(itemEntity);
    }

    @Override
    public void runEntity(Entity entity, PlayerEntity player, Hand hand) {
        itemEntity = new ItemEntity(entity.world, entity.getX() + .5f, entity.getY() + .5f, entity.getZ() + .5f, new ItemStack(item, amount));
        itemEntity.setToDefaultPickupDelay();
        entity.world.spawnEntity(itemEntity);
    }
}
