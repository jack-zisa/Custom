package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;

public class StringParameter implements EventParameter {
    private String name;
    private String string;

    public String getString() {
        return string;
    }

    public StringParameter withValue(String string) {
        this.string = string;
        return this;
    }

    public StringParameter name(String name) {
        this.name = name;
        return this;
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

    @Override
    public String getName() {
        return name;
    }
}
