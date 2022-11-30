package creoii.custom.eventsystem.event;

import creoii.custom.Custom;
import net.minecraft.util.Identifier;

public class BlockEvents {
    public static AbstractEvent RIGHT_CLICK;
    public static AbstractEvent LEFT_CLICK;
    public static AbstractEvent LANDED_ON;
    public static AbstractEvent STEPPED_ON;
    public static AbstractEvent PROJECTILE_HIT;
    public static AbstractEvent PLACE_BLOCK;
    public static AbstractEvent BREAK_BLOCK;
    public static AbstractEvent ENTITY_COLLISION;
    public static AbstractEvent NEIGHBOR_UPDATE;
    public static AbstractEvent RANDOM_TICK;

    public static void register() {
        RIGHT_CLICK = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "right_click"), new BasicEvent());
        LEFT_CLICK = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "left_click"), new BasicEvent());
        LANDED_ON = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "landed_on"), new BasicEvent());
        STEPPED_ON = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "stepped_on"), new BasicEvent());
        PROJECTILE_HIT = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "projectile_hit"), new BasicEvent());
        PLACE_BLOCK = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "place_block"), new BasicEvent());
        BREAK_BLOCK = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "break_block"), new BasicEvent());
        ENTITY_COLLISION = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "entity_collision"), new BasicEvent());
        NEIGHBOR_UPDATE = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "neighbor_update"), new BasicEvent());
        RANDOM_TICK = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "random_tick"), new BasicEvent());
    }
}
