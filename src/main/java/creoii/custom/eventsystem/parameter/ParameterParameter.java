package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;

public class ParameterParameter implements EventParameter {
    private EventParameter parameter;

    public EventParameter getParameter() {
        return parameter;
    }

    @Override
    public EventParameter getFromJson(JsonObject object) {
        return null;
    }
}
