package creoii.custom.util.provider;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import creoii.custom.Custom;

public class BooleanProvider extends Provider<Boolean> {
    @Override
    public Provider<Boolean> getFromJson(JsonObject object) {
        if (object.has("value")) {
            JsonElement element = object.get("value");
            if (element.isJsonPrimitive()) {
                return setValue(element.getAsBoolean());
            } else if (element.isJsonObject()) {
                if (object.get("type").getAsString().equals("random")) {
                    return setValue(Custom.RANDOM.nextBoolean());
                }
            }
        }
        return this;
    }
}
