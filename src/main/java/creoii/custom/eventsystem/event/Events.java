package creoii.custom.eventsystem.event;

import creoii.custom.Custom;
import net.minecraft.util.Identifier;

public class Events {
    public static AbstractEvent EMPTY;
    public static AbstractEvent RIGHT_CLICK;
    public static AbstractEvent LEFT_CLICK;
    public static AbstractEvent STEPPED_ON;

    public static void register() {
        EMPTY = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "empty"), new EmptyEvent());
        RIGHT_CLICK = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "right_click"), new BasicEvent());
        LEFT_CLICK = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "left_click"), new BasicEvent());
        STEPPED_ON = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "stepped_on"), new BasicEvent());
    }
}
