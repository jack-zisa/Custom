package creoii.custom.eventsystem.event;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.effect.Effect;

public class TargetDamagedEvent extends Event {
    public TargetDamagedEvent(Condition[] conditions, Effect[] effects) {
        super(Event.TARGET_DAMAGED, conditions, effects);
    }

    public static Event getFromJson(JsonObject object) {
        Condition[] conditions = Event.getConditions(object);
        Effect[] effects = Event.getEffects(object);
        return new TargetDamagedEvent(conditions, effects);
    }
}
