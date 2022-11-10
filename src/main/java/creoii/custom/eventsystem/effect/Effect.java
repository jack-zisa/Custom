package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.Custom;
import creoii.custom.data.Identifiable;
import creoii.custom.eventsystem.parameter.EventParameter;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class Effect implements Identifiable {
    @Nullable
    public static Effect getEffect(JsonObject object, Identifier id) {
        Effect effect = Custom.EFFECT.get(id);
        if (effect != null) {
            return effect.getFromJson(object);
        }
        return null;
    }

    public static Effect register(Identifier id, Effect effect) {
        return Registry.register(Custom.EFFECT, id, effect);
    }

    @Override
    public Identifier getIdentifier() {
        return Custom.EFFECT.getId(this);
    }

    public abstract List<EventParameter> getParameters();

    public abstract Effect getFromJson(JsonObject object);

    public abstract void run(List<EventParameter> parameters);
}
