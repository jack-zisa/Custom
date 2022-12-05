package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.EntityParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.WorldParameter;
import net.minecraft.entity.Entity;

import java.util.List;
import java.util.function.Predicate;

public class EntityCondition extends Condition {
    private final Predicate<Entity> predicate;

    public EntityCondition(Predicate<Entity> predicate) {
        this.predicate = predicate;
    }

    public EntityCondition getFromJson(JsonObject object) {
        return this;
    }

    @Override
    public List<EventParameter> getParameters() {
        return List.of(EventParameters.ENTITY);
    }

    @Override
    public boolean test(List<EventParameter> parameters) {
        EntityParameter entityParameter = (EntityParameter) EventParameter.find(parameters, EventParameters.ENTITY);
        if (entityParameter != null) {
            return predicate.test(entityParameter.getEntity());
        }
        return false;
    }
}
