package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;
import creoii.custom.Custom;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

import java.util.List;

public interface EventParameter {
    static EventParameter register(Identifier identifier, EventParameter parameter) {
        return Registry.register(Custom.EVENT_PARAMETER, identifier, parameter);
    }

    static EventParameter getEventParameter(JsonObject object) {
        return Custom.EVENT_PARAMETER.get(Identifier.tryParse(JsonHelper.getString(object, "type")));
    }

    static EventParameter find(List<EventParameter> parameters, EventParameter find) {
        for (EventParameter param : parameters) {
            if (param.getType() == find.getType()) return param;
        }
        return null;
    }

    default Identifier getIdentifier() {
        return Custom.EVENT_PARAMETER.getId(this);
    }

    EventParameter getType();

    EventParameter getFromJson(JsonObject object, String name);
}
