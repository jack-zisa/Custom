package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class Effect {
    public static final String SPAWN_ITEM = "spawn_item";
    public static final String SPAWN_ENTITY = "spawn_entity";

    private final String name;

    public Effect(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Effect getEffect(JsonObject object, String str) {
        return switch (str) {
            case SPAWN_ITEM -> SpawnItemEffect.getFromJson(object);
            case SPAWN_ENTITY -> SpawnEntityEffect.getFromJson(object);
            default -> new NoEffect();
        };
    }

    public abstract void runWorld(World world, BlockPos pos);
    public abstract void runBlock(World world, BlockState state, BlockPos pos, PlayerEntity player, Hand hand);
    public abstract void runItem(World world, Item item, BlockPos pos, PlayerEntity player, Hand hand);
    public abstract void runEntity(Entity entity, PlayerEntity player, Hand hand);
}
