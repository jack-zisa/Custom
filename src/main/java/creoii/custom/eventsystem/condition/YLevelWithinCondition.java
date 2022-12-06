package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.BlockPosParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.util.json.CustomJsonHelper;
import net.minecraft.util.math.intprovider.IntProvider;

import java.util.List;

public class YLevelWithinCondition extends Condition {
    private IntProvider range;

    @Override
    public YLevelWithinCondition getFromJson(JsonObject object) {
        YLevelWithinCondition condition = new YLevelWithinCondition();
        condition.range = CustomJsonHelper.getIntProvider(object.get("range"));
        return condition;
    }

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of(EventParameters.BLOCK_POS);
    }

    @Override
    public boolean test(List<EventParameter> parameters) {
        BlockPosParameter blockPosParameter = (BlockPosParameter) EventParameter.find(parameters, EventParameters.BLOCK_POS);
        if (blockPosParameter != null) {
            int y = blockPosParameter.getY();
            return y >= range.getMin() && y < range.getMax();
        }
        return false;
    }
}
