package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.BlockPosParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.WorldParameter;
import creoii.custom.util.json.CustomJsonHelper;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.LightType;

import java.util.List;

public class LightLevelWithinCondition extends Condition {
    private IntProvider range;
    private LightType lightType;

    @Override
    public LightLevelWithinCondition getFromJson(JsonObject object) {
        LightLevelWithinCondition condition = new LightLevelWithinCondition();
        condition.range = CustomJsonHelper.getIntProvider(object.get("range"));
        condition.lightType = object.has("light_type") ? LightType.valueOf(object.get("light_type").getAsString().toUpperCase()) : LightType.BLOCK;
        return condition;
    }

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of(EventParameters.WORLD, EventParameters.BLOCK_POS);
    }

    @Override
    public boolean test(List<EventParameter> parameters) {
        WorldParameter worldParameter = (WorldParameter) EventParameter.find(parameters, EventParameters.WORLD);
        if (worldParameter != null) {
            BlockPosParameter blockPosParameter = (BlockPosParameter) EventParameter.find(parameters, EventParameters.BLOCK_POS);
            if (blockPosParameter != null) {
                int level = worldParameter.getWorld().getLightLevel(lightType, blockPosParameter.getPos());
                return level >= range.getMin() && level < range.getMax();
            }
        }
        return false;
    }
}
