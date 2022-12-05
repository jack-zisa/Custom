package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import creoii.custom.Custom;
import creoii.custom.eventsystem.parameter.EntityParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Predicate;

public class LivingEntityCondition extends Condition {
    private final Predicate<LivingEntity> predicate;

    public LivingEntityCondition(Predicate<LivingEntity> predicate) {
        this.predicate = predicate;
    }

    public LivingEntityCondition getFromJson(JsonObject object) {
        Condition condition = Custom.CONDITION.get(Identifier.tryParse(object.get("type").getAsString()));
        if (condition instanceof LivingEntityCondition livingEntityCondition) {
            return livingEntityCondition;
        }
        throw new JsonParseException("Unable to parse living entity " + condition.getIdentifier().toString());
    }

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of(EventParameters.ENTITY);
    }

    @Override
    public boolean test(List<EventParameter> parameters) {
        EntityParameter entityParameter = (EntityParameter) EventParameter.find(parameters, EventParameters.ENTITY);
        if (entityParameter != null) {
            if (entityParameter.getEntity() instanceof LivingEntity livingEntity)
            return predicate.test(livingEntity);
        }
        return false;
    }
}
