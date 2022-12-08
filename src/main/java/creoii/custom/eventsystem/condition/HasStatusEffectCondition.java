package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.EntityParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.util.json.CustomJsonHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.List;

public class HasStatusEffectCondition extends Condition {
    private StatusEffect statusEffect;

    @Override
    public HasStatusEffectCondition getFromJson(JsonObject object) {
        HasStatusEffectCondition condition = new HasStatusEffectCondition();
        condition.statusEffect = Registries.STATUS_EFFECT.get(Identifier.tryParse(CustomJsonHelper.getString(object, new String[]{"effect", "status_effect"})));
        return condition;
    }

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of(EventParameters.ENTITY);
    }

    @Override
    public boolean test(List<EventParameter> parameters) {
        EntityParameter entityParameter = (EntityParameter) EventParameter.find(parameters, EventParameters.ENTITY);
        if (entityParameter != null) {
            if (entityParameter.getEntity() instanceof LivingEntity livingEntity) {
                return livingEntity.hasStatusEffect(statusEffect);
            }
        }
        return false;
    }
}
