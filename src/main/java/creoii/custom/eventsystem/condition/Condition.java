package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.Custom;
import creoii.custom.data.Identifiable;
import creoii.custom.eventsystem.parameter.EventParameter;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class Condition implements Identifiable {
    @Nullable
    public static Condition getCondition(JsonObject object, Identifier id) {
        return Custom.CONDITION.get(id).getFromJson(object);
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
