package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.WorldParameter;
import creoii.custom.util.json.CustomJsonHelper;
import net.minecraft.util.Identifier;

import java.util.List;

public class DimensionMatchesCondition extends Condition {
    private Identifier dimensionId;

    @Override
    public DimensionMatchesCondition getFromJson(JsonObject object) {
        DimensionMatchesCondition condition = new DimensionMatchesCondition();
        condition.dimensionId = Identifier.tryParse(CustomJsonHelper.getString(object, new String[]{"dimension", "dimension_id"}));
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
            return worldParameter.getWorld().getDimensionKey().getValue().equals(dimensionId);
        }
        return false;
    }
}
