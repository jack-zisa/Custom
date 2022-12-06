package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.WorldParameter;
import net.minecraft.world.Difficulty;

import java.util.List;

public class DifficultyMatchesCondition extends Condition {
    private Difficulty difficulty;

    @Override
    public DifficultyMatchesCondition getFromJson(JsonObject object) {
        DifficultyMatchesCondition condition = new DifficultyMatchesCondition();
        condition.difficulty = Difficulty.byName(object.get("difficulty").getAsString());
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
            return worldParameter.getWorld().getDifficulty() == difficulty;
        }
        return false;
    }
}
