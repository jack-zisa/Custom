package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;

public class DoubleParameter implements EventParameter {
    private double param;

    public double getDouble() {
        return param;
    }

    public DoubleParameter withValue(double param) {
        this.param = param;
        return this;
    }

    @Override
    public EventParameter getType() {
        return EventParameters.DOUBLE;
    }

    @Override
    public EventParameter getFromJson(JsonObject object, String name) {
        param = object.get(name).getAsDouble();
        return this;
    }
}
