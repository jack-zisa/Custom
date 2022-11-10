package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;

public class StringParameter implements EventParameter {
    private String string;

    public String getString() {
        return string;
    }

    @Override
    public EventParameter getType() {
        return EventParameters.STRING;
    }

    @Override
    public EventParameter getFromJson(JsonObject object, String name) {
        string = object.get(name).getAsString();
        return this;
    }
}
