package creoii.custom.eventsystem.event;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.effect.Effect;
import net.minecraft.entity.Entity;

public class EntityCollisionEvent extends BasicEvent {
    private Entity entity;

    public EntityCollisionEvent withValues(Condition[] conditions, Effect[] effects) {
        this.conditions = conditions;
        this.effects = effects;
        return this;
    }

    public EntityCollisionEvent getFromJson(JsonObject object) {
        Condition[] conditions = AbstractEvent.getConditions(object);
        Effect[] effects = AbstractEvent.getEffects(object);
        return withValues(conditions, effects);
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
