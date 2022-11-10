package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.EventParameter;

import java.util.List;

public class EmptyCondition extends Condition {
    @Override
    public EmptyCondition getFromJson(JsonObject object) {
        return this;
    }

    @Override
    public boolean test(List<EventParameter> parameters) {
        return false;
    }

    @Override
    public List<EventParameter> getParameters() {
        return List.of();
    }
}
