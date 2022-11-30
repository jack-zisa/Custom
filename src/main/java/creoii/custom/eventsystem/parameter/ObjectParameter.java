package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;

public class ObjectParameter implements EventParameter {
    private String name;
    private Object object;

    public Object getObject() {
        return object;
    }

    public ObjectParameter withValue(Object object) {
        this.object = object;
        return this;
    }

    public ObjectParameter name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public EventParameter getType() {
        return EventParameters.OBJECT;
    }

    @Override
    public EventParameter getFromJson(JsonObject object, String name) {
        return this;
    }

    @Override
    public String getName() {
        return name;
    }
}
