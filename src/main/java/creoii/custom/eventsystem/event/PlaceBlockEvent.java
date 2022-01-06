package creoii.custom.eventsystem.event;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.effect.Effect;

public class PlaceBlockEvent extends Event {
    public PlaceBlockEvent(Condition[] conditions, Effect[] effects) {
        super(Event.PLACE_BLOCK, conditions, effects);
    }

    public static Event getFromJson(JsonObject object) {
        Condition[] conditions = Event.getConditions(object);
        Effect[] effects = Event.getEffects(object);
        return new PlaceBlockEvent(conditions, effects);
    }
}
