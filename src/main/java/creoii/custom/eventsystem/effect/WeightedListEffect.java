package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.Custom;
import creoii.custom.eventsystem.parameter.EventParameter;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.random.Random;

import java.util.List;

public class WeightedListEffect extends AbstractEffect {
    private DataPool<AbstractEffect> effects;

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of();
    }

    public WeightedListEffect getFromJson(JsonObject object) {
        WeightedListEffect effect = new WeightedListEffect();
        DataPool.Builder<AbstractEffect> list = DataPool.builder();
        JsonHelper.getArray(object, "effects").forEach(element -> {
            JsonObject object1 = element.getAsJsonObject();
            list.add(AbstractEffect.getEffect(object1, Identifier.tryParse(object1.get("type").getAsString())), object1.get("weight").getAsInt());
        });
        effect.effects = list.build();
        return effect;
    }

    public AbstractEffect getRandom(Random random) {
        return effects.getDataOrEmpty(random).orElseThrow(IllegalStateException::new);
    }

    @Override
    public void run(List<EventParameter> parameters) {
        getRandom(Custom.RANDOM).run(parameters);
    }
}
