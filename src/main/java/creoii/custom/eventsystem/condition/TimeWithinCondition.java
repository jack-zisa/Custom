package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.WorldParameter;
import creoii.custom.util.json.CustomJsonHelper;
import net.minecraft.util.math.intprovider.IntProvider;

import java.util.List;

public class TimeWithinCondition extends Condition {
    private IntProvider range;

    @Override
    public TimeWithinCondition getFromJson(JsonObject object) {
        TimeWithinCondition condition = new TimeWithinCondition();
        condition.range = CustomJsonHelper.getIntProvider(object.get("range"));
        return condition;
    }

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of(EventParameters.WORLD);
    }

    @Override
    public boolean test(List<EventParameter> parameters) {
        WorldParameter worldParameter = (WorldParameter) EventParameter.find(parameters, EventParameters.WORLD);
        if (worldParameter != null) {
            long time = worldParameter.getWorld().getTime();
            return time >= range.getMin() && time < range.getMax();
        }
        return false;
    }
}
