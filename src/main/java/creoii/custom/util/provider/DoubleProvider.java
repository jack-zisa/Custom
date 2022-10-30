package creoii.custom.util.provider;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import creoii.custom.Custom;

public class DoubleProvider extends Provider<Double> {
    @Override
    public Provider<Double> getFromJson(JsonObject object) {
        if (object.has("value")) {
            JsonElement element = object.get("value");
            if (element.isJsonPrimitive()) {
                return setValue(element.getAsDouble());
            } else if (element.isJsonObject()) {
                if (object.get("type").getAsString().equals("random")) {
                    int min = object.get("min").getAsInt();
                    int max = object.get("max").getAsInt();
                    return setValue((double) Custom.RANDOM.nextBetween(min, max));
                }
            }
        }
        return this;
    }
}
