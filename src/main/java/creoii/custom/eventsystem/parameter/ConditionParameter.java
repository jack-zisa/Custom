package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.condition.Condition;
import net.minecraft.util.Identifier;

public class ConditionParameter implements EventParameter {
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    @Override
    public EventParameter getType() {
        return EventParameters.CONDITION;
    }

    @Override
    public EventParameter getFromJson(JsonObject object, String name) {
        condition = Condition.getCondition(object, Identifier.tryParse(object.get(name).getAsString()));
        return this;
    }
}
