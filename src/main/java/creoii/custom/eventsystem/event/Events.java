package creoii.custom.eventsystem.event;

import creoii.custom.Custom;
import net.minecraft.util.Identifier;

public class Events {
    public static AbstractEvent EMPTY;
    public static AbstractEvent RIGHT_CLICK;
    public static AbstractEvent LEFT_CLICK;
    public static AbstractEvent STEPPED_ON;
    public static AbstractEvent PROJECTILE_HIT;
    public static AbstractEvent PLACE_BLOCK;
    public static AbstractEvent BREAK_BLOCK;
    public static AbstractEvent TARGET_DAMAGED;
    public static AbstractEvent USER_DAMAGED;
    public static AbstractEvent ENTITY_LANDS;
    public static AbstractEvent ENTITY_COLLISION;
    public static AbstractEvent NEIGHBOR_UPDATE;
    public static AbstractEvent STOPPED_USING;
    public static AbstractEvent CRAFTED;
    public static AbstractEvent ITEM_DESPAWN;
    public static AbstractEvent RANDOM_TICK;
    public static AbstractEvent INVENTORY_TICK;
    public static AbstractEvent STATUS_EFFECT_UPDATE;
    public static AbstractEvent STATUS_EFFECT_APPLY;
    public static AbstractEvent STATUS_EFFECT_REMOVE;

    public static void register() {
        EMPTY = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "empty"), new EmptyEvent());
        RIGHT_CLICK = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "right_click"), new RightClickEvent());
        LEFT_CLICK = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "left_click"), new BasicEvent());
        STEPPED_ON = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "stepped_on"), new BasicEvent());
        PROJECTILE_HIT = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "projectile_hit"), new BasicEvent());
        PLACE_BLOCK = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "place_block"), new BasicEvent());
        BREAK_BLOCK = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "break_block"), new BasicEvent());
        TARGET_DAMAGED = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "target_damaged"), new BasicEvent());
        USER_DAMAGED = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "user_damaged"), new BasicEvent());
        ENTITY_LANDS = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "entity_lands"), new EntityLandsEvent());
        ENTITY_COLLISION = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "entity_collision"), new EntityCollisionEvent());
        NEIGHBOR_UPDATE = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "neighbor_update"), new NeighborUpdateEvent());
        STOPPED_USING = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "stopped_using"), new BasicEvent());
        CRAFTED = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "crafted"), new BasicEvent());
        ITEM_DESPAWN = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "item_despawn"), new BasicEvent());
        RANDOM_TICK = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "random_tick"), new ChanceEvent());
        INVENTORY_TICK = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "inventory_tick"), new ChanceEvent());
        STATUS_EFFECT_UPDATE = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "status_effect_update"), new BasicEvent());
        STATUS_EFFECT_APPLY = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "status_effect_apply"), new BasicEvent());
        STATUS_EFFECT_REMOVE = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "status_effect_remove"), new BasicEvent());
    }
}
