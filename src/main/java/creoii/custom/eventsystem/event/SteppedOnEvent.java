package creoii.custom.eventsystem.event;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.effect.Effect;

public class SteppedOnEvent extends Event {
    public SteppedOnEvent(Condition[] conditions, Effect[] effects) {
        super(Event.STEPPED_ON, conditions, effects);
    }

    public static Event getFromJson(JsonObject object) {
        Condition[] conditions = Event.getConditions(object);
        Effect[] effects = Event.getEffects(object);
        return new SteppedOnEvent(conditions, effects);
    }
}
