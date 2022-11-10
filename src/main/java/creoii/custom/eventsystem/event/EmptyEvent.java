package creoii.custom.eventsystem.event;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.effect.Effect;
import creoii.custom.eventsystem.parameter.EventParameter;

import java.util.List;

public class EmptyEvent extends AbstractEvent {
    @Override
    public AbstractEvent getType() {
        return Events.EMPTY;
    }

    public AbstractEvent getFromJson(JsonObject object) {
        conditions = new Condition[0];
        effects = new Effect[0];
        return new EmptyEvent();
    }

    @Override
    public boolean apply(List<EventParameter> parameters) {
        return true;
    }
}
