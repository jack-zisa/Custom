package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;

public class BooleanParameter implements EventParameter {
    private String name;
    private boolean param;

    public boolean getBoolean() {
        return param;
    }

    public BooleanParameter withValue(boolean param) {
        this.param = param;
        return this;
    }

    public BooleanParameter name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public EventParameter getType() {
        return EventParameters.BOOLEAN;
    }

    @Override
    public EventParameter getFromJson(JsonObject object, String name) {
        param = object.get(name).getAsBoolean();
        return this;
    }

    @Override
    public String getName() {
        return name;
    }
}
