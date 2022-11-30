package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.EventParameter;

import java.util.List;

public class EmptyEffect extends AbstractEffect {
    public EmptyEffect getFromJson(JsonObject object) {
        return new EmptyEffect();
    }

    @Override
    public List<EventParameter> getParameters() {
        return List.of();
    }

    @Override
    public void run(List<EventParameter> parameters) { }
}
