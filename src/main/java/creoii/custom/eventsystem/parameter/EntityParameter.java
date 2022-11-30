package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;
import net.minecraft.entity.Entity;

public class EntityParameter implements EventParameter {
    private String name;
    private Entity entity;

    public Entity getEntity() {
        return entity;
    }

    public EntityParameter withValue(Entity entity) {
        this.entity = entity;
        return this;
    }

    public EntityParameter name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public EventParameter getType() {
        return EventParameters.ENTITY;
    }

    @Override
    public EventParameter getFromJson(JsonObject object, String name) {
        return this;
    }

    @Override
    public String getName() {
        return name;
    }
}
