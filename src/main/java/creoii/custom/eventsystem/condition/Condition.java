package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import creoii.custom.Custom;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.loaders.Identifiable;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class Condition implements Identifiable {
    @Nullable
    public static Condition getCondition(JsonObject object, String id) {
        Condition condition;
        if (id.startsWith("!")) {
            condition = Custom.CONDITION.get(Identifier.tryParse(id.substring(1)));
            if (condition != null) {
                NegateCondition negateCondition = new NegateCondition();
                negateCondition.setCondition(condition.getFromJson(object));
                return negateCondition;
            }
        } else {
            condition = Custom.CONDITION.get(Identifier.tryParse(id));
            if (condition != null) {
                return condition.getFromJson(object);
            }
        }
        throw new JsonParseException("Unable to parse condition " + id);
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
        List<EventParameter> validatee = getRequiredParameters();
        for (int i = 0; i < validatee.size(); ++i) {
            if (validatee.get(i) == parameters.get(i)) {
                validatee.remove(validatee.get(i));
                break;
            }
        }
        return validatee.size() == 0;
    }

    /**
     * List of the parameters required to check the condition
     */
    public abstract List<EventParameter> getRequiredParameters();

    public abstract Condition getFromJson(JsonObject object);

    public abstract boolean test(List<EventParameter> parameters);
}
