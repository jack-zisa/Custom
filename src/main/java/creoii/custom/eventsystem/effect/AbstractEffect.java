package creoii.custom.eventsystem.effect;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import creoii.custom.Custom;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.ParameterModifications;
import creoii.custom.loaders.Identifiable;
import creoii.custom.util.StringToObject;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractEffect implements Identifiable {
    private ParameterModifications modifications;

    @Nullable
    public static AbstractEffect getEffect(JsonObject object, Identifier id) {
        AbstractEffect effect = Custom.EFFECT.get(id);
        if (effect != null) {
            if (object.has("parameters")) {
                return effect.getFromJson(object).getWithModifications(object.getAsJsonArray("parameters"));
            } else return effect.getFromJson(object);
        }
        return null;
    }

    public void createModifications() {
        modifications = new ParameterModifications();
    }

    public static AbstractEffect register(Identifier id, AbstractEffect effect) {
        return Registry.register(Custom.EFFECT, id, effect);
    }

    @Override
    public Identifier getIdentifier() {
        return Custom.EFFECT.getId(this);
    }

    public AbstractEffect getWithModifications(JsonArray array) {
        ParameterModifications modifications = new ParameterModifications();
        for (int i = 0; i < array.size(); ++i) {
            JsonElement element = array.get(i);
            if (element.isJsonObject()) {
                JsonObject object = element.getAsJsonObject();
                String type = JsonHelper.getString(object, "type");
                String name = JsonHelper.getString(object, "name");
                modifications.add(StringToObject.eventParameter(type), name);
            }
        }
        this.modifications = modifications;
        return this;
    }

    public ParameterModifications getModifications() {
        return modifications;
    }

    public abstract List<EventParameter> getRequiredParameters();

    public abstract AbstractEffect getFromJson(JsonObject object);

    public abstract void run(List<EventParameter> parameters);
}
