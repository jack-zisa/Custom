package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class StatusEffectParameter implements EventParameter {
    private StatusEffect statusEffect;

    public StatusEffect getStatusEffect() {
        return statusEffect;
    }

    @Override
    public EventParameter getType() {
        return EventParameters.STATUS_EFFECT;
    }

    @Override
    public StatusEffectParameter getFromJson(JsonObject object, String name) {
        statusEffect = Registry.STATUS_EFFECT.get(Identifier.tryParse(object.get(name).getAsString()));
        return this;
    }
}
