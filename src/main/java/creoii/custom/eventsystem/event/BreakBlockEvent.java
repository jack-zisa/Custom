package creoii.custom.eventsystem.event;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.effect.Effect;

public class BreakBlockEvent extends Event {
    public BreakBlockEvent withValues(Condition[] conditions, Effect[] effects) {
        this.conditions = conditions;
        this.effects = effects;
        return this;
    }

    public BreakBlockEvent getFromJson(JsonObject object) {
        Condition[] conditions = Event.getConditions(object);
        Effect[] effects = Event.getEffects(object);
        return withValues(conditions, effects);
    }
}
