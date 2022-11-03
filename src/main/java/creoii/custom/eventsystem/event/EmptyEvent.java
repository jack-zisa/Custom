package creoii.custom.eventsystem.event;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.effect.Effect;

public class EmptyEvent extends AbstractEvent {
    public AbstractEvent getFromJson(JsonObject object) {
        conditions = new Condition[0];
        effects = new Effect[0];
        return new EmptyEvent();
    }
}
