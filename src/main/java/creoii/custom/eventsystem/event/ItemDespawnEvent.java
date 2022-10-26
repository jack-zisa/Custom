package creoii.custom.eventsystem.event;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.effect.Effect;

public class ItemDespawnEvent extends Event {
    public ItemDespawnEvent(Condition[] conditions, Effect[] effects) {
        super(Event.ITEM_DESPAWN, conditions, effects);
    }

    public static Event getFromJson(JsonObject object) {
        Condition[] conditions = Event.getConditions(object);
        Effect[] effects = Event.getEffects(object);
        return new ItemDespawnEvent(conditions, effects);
    }
}
