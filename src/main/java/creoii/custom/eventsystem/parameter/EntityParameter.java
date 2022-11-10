package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityParameter implements EventParameter {
    private Entity entity;

    public Entity getEntity() {
        return entity;
    }

    public EntityParameter withValue(Entity entity) {
        this.entity = entity;
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
}
