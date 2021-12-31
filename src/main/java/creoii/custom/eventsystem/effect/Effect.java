package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.event.Event;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class Effect {
    public static final String DROP_ITEM = "drop_item";
    public static final String SPAWN_ENTITY = "spawn_entity";
    public static final String HEAL = "heal";

    private final String type;

    public Effect(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static Effect getEffect(JsonObject object, String str) {
        return switch (str) {
            case DROP_ITEM -> DropItemEffect.getFromJson(object);
            case SPAWN_ENTITY -> SpawnEntityEffect.getFromJson(object);
            case HEAL -> HealEffect.getFromJson(object);
            default -> new NoEffect();
        };
    }

    public static Effect findEffect(Effect[] effects, String name) {
        for (Effect effect : effects) {
            if (effect.getType().equals(name)) return effect;
        } return null;
    }

    public abstract void runWorld(World world, BlockPos pos);
    public abstract void runBlock(World world, BlockState state, BlockPos pos, PlayerEntity player, Hand hand);
    public abstract void runItem(World world, Item item, BlockPos pos, PlayerEntity player, Hand hand);
    public abstract void runEntity(Entity entity, PlayerEntity player, Hand hand);
    public abstract void runEnchantment(Entity user, Entity target, int level);
}
