package creoii.custom.eventsystem.event;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.effect.Effect;

public class StatusEffectUpdateEvent extends Event {
    public StatusEffectUpdateEvent(Condition[] conditions, Effect[] effects) {
        super(Event.STATUS_EFFECT_UPDATE, conditions, effects);
    }

    public static Event getFromJson(JsonObject object) {
        Condition[] conditions = Event.getConditions(object);
        Effect[] effects = Event.getEffects(object);
        return new StatusEffectUpdateEvent(conditions, effects);
    }
}
