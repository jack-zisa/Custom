package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.effect.Effect;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class Condition {
    public static final String HOLDING_ITEM = "holding_item";
    public static final String IN_TAG = "in_tag";

    private final String type;

    public Condition(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static Condition getCondition(JsonObject object, String str) {
        return switch (str) {
            case HOLDING_ITEM -> HoldingItemCondition.getFromJson(object);
            case IN_TAG -> InTagCondition.getFromJson(object);
            default -> new NoCondition();
        };
    }

    public static Condition findEffect(Condition[] conditions, String name) {
        for (Condition condition : conditions) {
            if (condition.getType().equals(name)) return condition;
        } return null;
    }

    public abstract boolean testWorld(World world, BlockPos pos);
    public abstract boolean testBlock(World world, BlockState state, BlockPos pos, PlayerEntity player, Hand hand);
    public abstract boolean testItem(World world, Item item, BlockPos pos, PlayerEntity player, Hand hand);
    public abstract boolean testEntity(Entity entity, PlayerEntity player, Hand hand);
    public abstract boolean testEnchantment(Entity user, Entity target, int level);
}
