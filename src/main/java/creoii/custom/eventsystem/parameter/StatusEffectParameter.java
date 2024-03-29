package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class StatusEffectParameter implements EventParameter {
    private String name;
    private StatusEffect statusEffect;

    public StatusEffect getStatusEffect() {
        return statusEffect;
    }

    public StatusEffectParameter name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public EventParameter getType() {
        return EventParameters.STATUS_EFFECT;
    }

    @Override
    public StatusEffectParameter getFromJson(JsonObject object, String name) {
        statusEffect = Registries.STATUS_EFFECT.get(Identifier.tryParse(object.get(name).getAsString()));
        return this;
    }

    @Override
    public String getName() {
        return name;
    }
}
