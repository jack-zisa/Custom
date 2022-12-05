package creoii.custom.util.provider;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import creoii.custom.Custom;
import net.minecraft.util.Identifier;

public class BooleanProvider extends ValueProvider<Boolean> {
    private Boolean value;

    public BooleanProvider withValue(boolean value) {
        this.value = value;
        return this;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    public static ValueProvider<Boolean> getFromJson(JsonObject object) {
        if (object.has("value")) {
            ValueProvider<?> provider = Custom.VALUE_PROVIDER.get(Identifier.tryParse(object.get("type").getAsString()));
            if (provider instanceof BooleanProvider booleanValueProvider) {
                JsonElement element = object.get("value");
                if (element.isJsonPrimitive()) {
                    return booleanValueProvider.withValue(element.getAsBoolean());
                } else if (element.isJsonObject()) {
                    if (object.get("type").getAsString().equals("random")) {
                        return booleanValueProvider.withValue(Custom.RANDOM.nextBoolean());
                    }
                }
            }
        }
        throw new JsonSyntaxException("Provider is missing \"value\"");
    }
}
