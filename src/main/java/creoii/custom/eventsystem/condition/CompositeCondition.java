package creoii.custom.eventsystem.condition;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.EventParameter;

import java.util.List;

public class CompositeCondition extends Condition {
    private Condition[] conditions;

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of();
    }

    @Override
    public Condition getFromJson(JsonObject object) {
        CompositeCondition effect = new CompositeCondition();
        JsonArray array = object.getAsJsonArray("effects");
        Condition[] conditions = new Condition[array.size()];
        for (int i = 0; i < array.size(); ++i) {
            JsonObject arrayObj = array.get(i).getAsJsonObject();
            conditions[i] = Condition.getCondition(arrayObj, arrayObj.get("type").getAsString());
        }
        effect.conditions = conditions;
        return effect;
    }

    @Override
    public boolean test(List<EventParameter> parameters) {
        boolean test = false;
        for (Condition condition : conditions) {
            if (condition.test(parameters)) test = true;
        }
        return test;
    }
}
