package creoii.custom.util.provider.function;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import creoii.custom.Custom;
import creoii.custom.util.provider.ValueProvider;
import net.minecraft.util.Identifier;

public abstract class SingleFunction<T> extends ValueProvider<T> {
    private ValueProvider<T> value;

    public abstract T compute(ValueProvider<T> input);

    @SuppressWarnings("unchecked")
    public SingleFunction<T> withValue(ValueProvider<?> value) {
        this.value = (ValueProvider<T>) value;
        return this;
    }

    public static SingleFunction<?> getSingleFunction(JsonObject object) {
        if (object.has("input")) {
            ValueProvider<?> provider = Custom.VALUE_PROVIDER.get(Identifier.tryParse(object.get("type").getAsString()));
            if (provider instanceof SingleFunction<?> singleFunction) {
                ValueProvider<?> input = ValueProvider.getFromJson(object.getAsJsonObject("input"));
                return singleFunction.withValue(input);
            }
        }
        throw new JsonSyntaxException("Function is missing \"input\"");
    }

    @Override
    public T getValue() {
        return compute(value);
    }
}
