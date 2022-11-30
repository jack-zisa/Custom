package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;

public class EmptyParameter implements EventParameter {
    @Override
    public EventParameter getType() {
        return EventParameters.EMPTY;
    }

    @Override
    public EventParameter getFromJson(JsonObject object, String name) {
        return EventParameters.EMPTY;
    }

    @Override
    public String getName() {
        return "empty";
    }
}
