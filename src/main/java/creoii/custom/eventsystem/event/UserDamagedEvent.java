package creoii.custom.eventsystem.event;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.effect.Effect;

public class UserDamagedEvent extends Event {
    public UserDamagedEvent(Condition[] conditions, Effect[] effects) {
        super(Event.USER_DAMAGED, conditions, effects);
    }

    public static Event getFromJson(JsonObject object) {
        Condition[] conditions = Event.getConditions(object);
        Effect[] effects = Event.getEffects(object);
        return new UserDamagedEvent(conditions, effects);
    }
}
