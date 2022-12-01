package creoii.custom.eventsystem.effect;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.EventParameter;
import net.minecraft.util.Identifier;

import java.util.List;

public class CompositeEffect extends AbstractEffect {
    private AbstractEffect[] effects;

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of();
    }

    @Override
    public AbstractEffect getFromJson(JsonObject object) {
        CompositeEffect effect = new CompositeEffect();
        JsonArray array = object.getAsJsonArray("effects");
        AbstractEffect[] effects = new AbstractEffect[array.size()];
        for (int i = 0; i < array.size(); ++i) {
            JsonObject arrayObj = array.get(i).getAsJsonObject();
            effects[i] = AbstractEffect.getEffect(arrayObj, Identifier.tryParse(arrayObj.get("type").getAsString()));
        }
        effect.effects = effects;
        return effect;
    }

    @Override
    public void run(List<EventParameter> parameters) {
        for (AbstractEffect effect : effects) {
            effect.run(parameters);
        }
    }
}
