package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.Custom;
import creoii.custom.eventsystem.parameter.EventParameter;

import java.util.List;

public class RandomChanceCondition extends Condition {
    private double chance;

    @Override
    public RandomChanceCondition getFromJson(JsonObject object) {
        RandomChanceCondition condition = new RandomChanceCondition();
        condition.chance = object.get("chance").getAsDouble();
        return condition;
    }

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of();
    }

    @Override
    public boolean test(List<EventParameter> parameters) {
        return Custom.RANDOM.nextDouble() <= chance;
    }
}
