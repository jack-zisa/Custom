package creoii.custom.eventsystem.event;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.effect.Effect;

public class StatusEffectApplyEvent extends Event {
    public StatusEffectApplyEvent(Condition[] conditions, Effect[] effects) {
        super(Event.STATUS_EFFECT_APPLY, conditions, effects);
    }

    public static Event getFromJson(JsonObject object) {
        Condition[] conditions = Event.getConditions(object);
        Effect[] effects = Event.getEffects(object);
        return new StatusEffectApplyEvent(conditions, effects);
    }
}
