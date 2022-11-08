package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityTypeParameter implements EventParameter {
    private EntityType<?> entityType;

    public EntityType<?> getEntityType() {
        return entityType;
    }

    @Override
    public EventParameter getFromJson(JsonObject object) {
        entityType = Registry.ENTITY_TYPE.get(Identifier.tryParse(object.get("entity_type").getAsString()));
        return this;
    }
}
