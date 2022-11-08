package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;

public class StringParameter implements EventParameter {
    private String str;

    public String getString() {
        return str;
    }

    @Override
    public EventParameter getFromJson(JsonObject object) {
        str = object.get("string").getAsString();
        return this;
    }
}
