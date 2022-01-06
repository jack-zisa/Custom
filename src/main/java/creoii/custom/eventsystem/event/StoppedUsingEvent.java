package creoii.custom.eventsystem.event;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.effect.Effect;

public class StoppedUsingEvent extends Event {
    public StoppedUsingEvent(Condition[] conditions, Effect[] effects) {
        super(Event.STOPPED_USING, conditions, effects);
    }

    public static Event getFromJson(JsonObject object) {
        Condition[] conditions = Event.getConditions(object);
        Effect[] effects = Event.getEffects(object);
        return new StoppedUsingEvent(conditions, effects);
    }
}
