package creoii.custom.eventsystem.event;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.effect.Effect;

public class CraftedEvent extends Event {
    public CraftedEvent(Condition[] conditions, Effect[] effects) {
        super(Event.CRAFTED, conditions, effects);
    }

    public static Event getFromJson(JsonObject object) {
        Condition[] conditions = Event.getConditions(object);
        Effect[] effects = Event.getEffects(object);
        return new CraftedEvent(conditions, effects);
    }
}
