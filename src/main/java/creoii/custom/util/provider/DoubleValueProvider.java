package creoii.custom.util.provider;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import creoii.custom.Custom;
import net.minecraft.util.Identifier;

public class DoubleValueProvider extends ValueProvider<Double> {
    private Double value;

    public DoubleValueProvider withValue(double value) {
        this.value = value;
        return this;
    }

    @Override
    public Double getValue() {
        return value;
    }

    public static ValueProvider<Double> getFromJson(JsonObject object) {
        if (object.has("value")) {
            ValueProvider<?> provider = Custom.VALUE_PROVIDER.get(Identifier.tryParse(object.get("type").getAsString()));
            if (provider instanceof DoubleValueProvider doubleValueProvider) {
                JsonElement element = object.get("value");
                if (element.isJsonPrimitive()) {
                    return doubleValueProvider.withValue(element.getAsDouble());
                } else if (element.isJsonObject()) {
                    if (object.get("type").getAsString().equals("random")) {
                        int min = object.get("min").getAsInt();
                        int max = object.get("max").getAsInt();
                        return doubleValueProvider.withValue(Custom.RANDOM.nextBetween(min, max));
                    }
                }
            }
        }
        throw new JsonSyntaxException("Provider is missing \"value\"");
    }
}
