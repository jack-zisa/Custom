package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityTypeParameter implements EventParameter {
    private String name = "entity_type";
    private EntityType<?> entityType;

    public EntityType<?> getEntityType() {
        return entityType;
    }

    public EntityTypeParameter withValue(EntityType<?> entityType) {
        this.entityType = entityType;
        return this;
    }

    public EntityTypeParameter name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public EventParameter getType() {
        return EventParameters.ENTITY_TYPE;
    }

    @Override
    public EventParameter getFromJson(JsonObject object, String name) {
        entityType = Registry.ENTITY_TYPE.get(Identifier.tryParse(object.get(name).getAsString()));
        return this;
    }

    @Override
    public String getName() {
        return name;
    }
}
