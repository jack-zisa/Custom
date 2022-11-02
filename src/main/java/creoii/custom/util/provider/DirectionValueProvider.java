package creoii.custom.util.provider;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import creoii.custom.Custom;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

public class DirectionValueProvider extends ValueProvider<Direction> {
    private Direction value;

    public DirectionValueProvider withValue(Direction value) {
        this.value = value;
        return this;
    }

    @Override
    public Direction getValue() {
        return value;
    }

    public static ValueProvider<Direction> getFromJson(JsonObject object) {
        if (object.has("value")) {
            ValueProvider<?> provider = Custom.VALUE_PROVIDER.get(Identifier.tryParse(object.get("type").getAsString()));
            if (provider instanceof DirectionValueProvider doubleValueProvider) {
                JsonElement element = object.get("value");
                if (element.isJsonPrimitive()) {
                    return doubleValueProvider.withValue(Direction.valueOf(element.getAsString()));
                } else if (element.isJsonObject()) {
                    String type = object.get("type").getAsString();
                    return switch (type) {
                        case "random" -> doubleValueProvider.withValue(Direction.random(Custom.RANDOM));
                        case "horizontal_random" -> doubleValueProvider.withValue(Direction.Type.HORIZONTAL.random(Custom.RANDOM));
                        case "vertical_random" -> doubleValueProvider.withValue(Direction.Type.VERTICAL.random(Custom.RANDOM));
                        default -> doubleValueProvider.withValue(Direction.UP);
                    };
                }
            }
        }
        throw new JsonSyntaxException("Provider is missing \"value\"");
    }
}
