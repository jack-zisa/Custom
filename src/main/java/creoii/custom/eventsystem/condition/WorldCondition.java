package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.WorldParameter;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class WorldCondition extends Condition {
    private final Predicate<World> predicate;

    public WorldCondition(Predicate<World> predicate) {
        this.predicate = predicate;
    }

    @Override
    public Condition getFromJson(JsonObject object) {
        return this;
    }

    @Override
    public List<EventParameter> getParameters() {
        return List.of(EventParameters.WORLD);
    }

    @Override
    public boolean test(List<EventParameter> parameters) {
        if (validate(parameters)) {
            WorldParameter worldParameter = (WorldParameter) parameters.get(0);
            return predicate.test(worldParameter.getWorld());
        }
        return false;
    }
}
