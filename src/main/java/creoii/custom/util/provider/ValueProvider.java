package creoii.custom.util.provider;

import com.google.gson.JsonObject;
import creoii.custom.Custom;
import creoii.custom.util.provider.function.DoubleFunction;
import creoii.custom.util.provider.function.SingleFunction;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

public abstract class ValueProvider<T> {
    public static ValueProvider<?> getFromJson(JsonObject object, String name) {
        return ValueProvider.getFromJson(JsonHelper.getObject(object, name));
    }

    public static ValueProvider<?> getFromJson(JsonObject object) {
        if (object.has("type")) {
            return Custom.VALUE_PROVIDER.get(Identifier.tryParse(object.get("type").getAsString()));
        }
        return null;
    }

    public ValueProvider<?> getFunction(JsonObject object) {
        if (object.has("input")) {
            return SingleFunction.getSingleFunction(object);
        } else {
            ValueProvider<?> value1 = getFromJson(object.getAsJsonObject("input1"));
            ValueProvider<?> value2 = getFromJson(object.getAsJsonObject("input2"));
            return DoubleFunction.getDoubleFunction(object);
        }
    }

    public static ValueProvider<?> register(Identifier id, ValueProvider<?> condition) {
        return Registry.register(Custom.VALUE_PROVIDER, id, condition);
    }

    public Identifier getId() {
        return Custom.VALUE_PROVIDER.getId(this);
    }

    public abstract T getValue();

    public enum Primitive {
        BLOCK_POS,
        DIRECTION,
        BOOLEAN,
        DOUBLE,
        STRING
    }
}
