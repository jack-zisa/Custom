package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;
import creoii.custom.Custom;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

public interface EventParameter {
    static EventParameter register(Identifier identifier, EventParameter parameter) {
        return Registry.register(Custom.EVENT_PARAMETER, identifier, parameter);
    }

    static EventParameter parse(JsonObject object) {
        EventParameter parameter = Custom.EVENT_PARAMETER.get(Identifier.tryParse(JsonHelper.getString(object, "type")));
        if (parameter != null)
            return parameter.getFromJson(object);
        return null;
    }

    EventParameter getFromJson(JsonObject object);
}
