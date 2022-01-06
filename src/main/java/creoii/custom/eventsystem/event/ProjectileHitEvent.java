package creoii.custom.eventsystem.event;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.effect.Effect;

public class ProjectileHitEvent extends Event {
    public ProjectileHitEvent(Condition[] conditions, Effect[] effects) {
        super(Event.PROJECTILE_HIT, conditions, effects);
    }

    public static Event getFromJson(JsonObject object) {
        Condition[] conditions = Event.getConditions(object);
        Effect[] effects = Event.getEffects(object);
        return new ProjectileHitEvent(conditions, effects);
    }
}
