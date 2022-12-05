package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import creoii.custom.Custom;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.loaders.Identifiable;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class Condition implements Identifiable {
    @Nullable
    public static Condition getCondition(JsonObject object, Identifier id) {
        Condition condition = Custom.CONDITION.get(id);
        if (condition != null) {
            return condition.getFromJson(object);
        }
        throw new JsonParseException("Unable to parse condition " + id.toString());
    }

    public static Condition register(Identifier id, Condition condition) {
        return Registry.register(Custom.CONDITION, id, condition);
    }

    @Override
    public Identifier getIdentifier() {
        return Custom.CONDITION.getId(this);
    }

    /**
     * Ensure that we are provided the correct parameters to check the condition
     */
    public boolean validate(List<EventParameter> parameters) {
        List<EventParameter> validatee = getParameters();
        for (int i = 0; i < validatee.size(); ++i) {
            if (validatee.get(i) == parameters.get(i)) {
                validatee.remove(validatee.get(i));
                break;
            }
        }
        return validatee.size() == 0;
    }

    /**
     * List of the parameters needed to check the condition
     */
    public abstract List<EventParameter> getParameters();

    public abstract Condition getFromJson(JsonObject object);

    public abstract boolean test(List<EventParameter> parameters);
}
