package creoii.custom.eventsystem.effect;

import creoii.custom.Custom;
import net.minecraft.util.Identifier;

public class Effects {
    public static Effect EMPTY;
    public static Effect SEND_MESSAGE;

    public static void register() {
        EMPTY = Effect.register(new Identifier(Custom.NAMESPACE, "empty"), new EmptyEffect());
        SEND_MESSAGE = Effect.register(new Identifier(Custom.NAMESPACE, "send_message"), new SendMessageEffect());
    }
}
