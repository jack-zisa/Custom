package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.condition.Condition;

public class ConditionParameter implements EventParameter {
    private String name;
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    public ConditionParameter name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public EventParameter getType() {
        return EventParameters.CONDITION;
    }

    @Override
    public EventParameter getFromJson(JsonObject object, String name) {
        condition = Condition.getCondition(object, object.get("type").getAsString());
        return this;
    }

    @Override
    public String getName() {
        return name;
    }
}
