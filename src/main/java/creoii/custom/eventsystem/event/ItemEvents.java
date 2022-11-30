package creoii.custom.eventsystem.event;

import creoii.custom.Custom;
import net.minecraft.util.Identifier;

public class ItemEvents {
    public static AbstractEvent USE;
    public static AbstractEvent USE_ON_BLOCK;
    public static AbstractEvent USE_ON_ENTITY;
    public static AbstractEvent CRAFTED;
    public static AbstractEvent ITEM_DESPAWN;
    public static AbstractEvent STOPPED_USING;
    public static AbstractEvent CLICKED;
    public static AbstractEvent INVENTORY_TICK;

    public static void register() {
        USE = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "use"), new BasicEvent());
        USE_ON_BLOCK = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "use_on_block"), new BasicEvent());
        USE_ON_ENTITY = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "use_on_entity"), new BasicEvent());
        CRAFTED = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "crafted"), new BasicEvent());
        ITEM_DESPAWN = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "item_despawn"), new BasicEvent());
        STOPPED_USING = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "stopped_using"), new BasicEvent());
        CLICKED = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "clicked"), new BasicEvent());
        INVENTORY_TICK = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "inventory_tick"), new BasicEvent());
    }
}
