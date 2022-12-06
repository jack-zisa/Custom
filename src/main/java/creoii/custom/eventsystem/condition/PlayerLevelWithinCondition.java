package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.EntityParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.util.json.CustomJsonHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.intprovider.IntProvider;

import java.util.List;

public class PlayerLevelWithinCondition extends Condition {
    private IntProvider range;

    @Override
    public PlayerLevelWithinCondition getFromJson(JsonObject object) {
        PlayerLevelWithinCondition condition = new PlayerLevelWithinCondition();
        condition.range = CustomJsonHelper.getIntProvider(object.get("range"));
        return condition;
    }

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of(EventParameters.ENTITY);
    }

    @Override
    public boolean test(List<EventParameter> parameters) {
        EntityParameter entityParameter = (EntityParameter) EventParameter.find(parameters, EventParameters.ENTITY);
        if (entityParameter != null) {
            if (entityParameter.getEntity() instanceof PlayerEntity playerEntity) {
                int level = playerEntity.experienceLevel;
                return level >= range.getMin() && level < range.getMax();
            }
        }
        return false;
    }
}
