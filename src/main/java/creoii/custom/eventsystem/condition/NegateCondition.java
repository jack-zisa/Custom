package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.EventParameter;

import java.util.List;

public class NegateCondition extends Condition {
    private Condition condition;

    @Override
    public NegateCondition getFromJson(JsonObject object) {
        NegateCondition condition = new NegateCondition();
        condition.condition = Condition.getCondition(object, object.get("type").getAsString());
        return condition;
    }

    @Override
    public boolean test(List<EventParameter> parameters) {
        return !condition.test(parameters);
    }

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of();
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
}
