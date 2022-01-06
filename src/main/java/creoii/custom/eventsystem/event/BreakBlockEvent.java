package creoii.custom.eventsystem.event;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.effect.Effect;

public class BreakBlockEvent extends Event {
    public BreakBlockEvent(Condition[] conditions, Effect[] effects) {
        super(Event.BREAK_BLOCK, conditions, effects);
    }

    public static Event getFromJson(JsonObject object) {
        Condition[] conditions = Event.getConditions(object);
        Effect[] effects = Event.getEffects(object);
        return new BreakBlockEvent(conditions, effects);
    }
}
