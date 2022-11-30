package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.EventParameter;
import net.minecraft.util.Identifier;

import java.util.List;

public class CompositeEffect extends AbstractEffect {
    private AbstractEffect effect1;
    private AbstractEffect effect2;

    @Override
    public List<EventParameter> getParameters() {
        return List.of();
    }

    @Override
    public AbstractEffect getFromJson(JsonObject object) {
        CompositeEffect effect = new CompositeEffect();
        JsonObject first = object.getAsJsonObject("first");
        JsonObject second = object.getAsJsonObject("second");
        effect.effect1 = AbstractEffect.getEffect(first, Identifier.tryParse(first.get("type").getAsString()));
        effect.effect2 = AbstractEffect.getEffect(second, Identifier.tryParse(second.get("type").getAsString()));
        return effect;
    }

    @Override
    public void run(List<EventParameter> parameters) {
        effect1.run(parameters);
        effect2.run(parameters);
    }
}
