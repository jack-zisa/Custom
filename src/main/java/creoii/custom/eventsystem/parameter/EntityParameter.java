package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityParameter implements EventParameter {
    private Entity entity;

    public Entity getEntity() {
        return entity;
    }

    @Override
    public EventParameter getFromJson(JsonObject object) {
        return this;
    }
}
