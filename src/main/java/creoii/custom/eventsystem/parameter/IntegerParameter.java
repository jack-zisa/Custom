package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;

public class IntegerParameter implements EventParameter {
    private int param;

    public int getInt() {
        return param;
    }

    public IntegerParameter withValue(int param) {
        this.param = param;
        return this;
    }

    @Override
    public EventParameter getFromJson(JsonObject object) {
        param = object.get("param").getAsInt();
        return this;
    }
}
