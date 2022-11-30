package creoii.custom.eventsystem.event;

import creoii.custom.Custom;
import net.minecraft.util.Identifier;

public class Events {
    public static AbstractEvent EMPTY;
    public static AbstractEvent STATUS_EFFECT_APPLY;
    public static AbstractEvent STATUS_EFFECT_REMOVE;
    public static AbstractEvent STATUS_EFFECT_UPDATE;
    public static AbstractEvent ENCHANTMENT_TARGET_DAMAGED;
    public static AbstractEvent ENCHANTMENT_USER_DAMAGED;

    public static void register() {
        EMPTY = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "empty"), new EmptyEvent());
        STATUS_EFFECT_APPLY = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "status_effect_apply"), new BasicEvent());
        STATUS_EFFECT_REMOVE = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "status_effect_remove"), new BasicEvent());
        STATUS_EFFECT_UPDATE = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "status_effect_update"), new BasicEvent());
        ENCHANTMENT_TARGET_DAMAGED = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "enchantment_target_damaged"), new BasicEvent());
        ENCHANTMENT_USER_DAMAGED = AbstractEvent.register(new Identifier(Custom.NAMESPACE, "enchantment_user_damaged"), new BasicEvent());

        BlockEvents.register();
        ItemEvents.register();
    }
}
