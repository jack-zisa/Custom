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
    public StatusEffectParameter getFromJson(JsonObject object) {
        statusEffect = Registry.STATUS_EFFECT.get(Identifier.tryParse(object.get("status_effect").getAsString()));
        return this;
    }
}
