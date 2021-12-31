package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class Condition {
    public static final String HELD_ITEM = "held_item";

    private final String name;

    public Condition(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Condition getCondition(JsonObject object, String str) {
        return switch (str) {
            case HELD_ITEM -> HeldItemCondition.getFromJson(object);
            default -> new NoCondition();
        };
    }

    public abstract boolean testWorld(World world, BlockPos pos);
    public abstract boolean testBlock(World world, BlockState state, BlockPos pos, PlayerEntity player, Hand hand);
    public abstract boolean testItem(World world, Item item, BlockPos pos, PlayerEntity player, Hand hand);
    public abstract boolean testEntity(Entity entity, PlayerEntity player, Hand hand);
}
