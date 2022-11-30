package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.Custom;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.loaders.Identifiable;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractEffect implements Identifiable {
    @Nullable
    public static AbstractEffect getEffect(JsonObject object, Identifier id) {
        AbstractEffect effect = Custom.EFFECT.get(id);
        if (effect != null) {
            return effect.getFromJson(object);
        }
        return null;
    }

    public static AbstractEffect register(Identifier id, AbstractEffect effect) {
        return Registry.register(Custom.EFFECT, id, effect);
    }

    @Override
    public Identifier getIdentifier() {
        return Custom.EFFECT.getId(this);
    }

    public abstract List<EventParameter> getParameters();

    public abstract AbstractEffect getFromJson(JsonObject object);

    public abstract void run(List<EventParameter> parameters);
}
