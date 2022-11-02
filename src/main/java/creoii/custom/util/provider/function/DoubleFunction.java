package creoii.custom.util.provider.function;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import creoii.custom.Custom;
import creoii.custom.util.provider.ValueProvider;
import net.minecraft.util.Identifier;

public abstract class DoubleFunction<T> extends ValueProvider<T> {
    private ValueProvider<T> value1;
    private ValueProvider<T> value2;

    public abstract T compute(ValueProvider<T> input1, ValueProvider<T> input2);

    @SuppressWarnings("unchecked")
    public DoubleFunction<T> withValues(ValueProvider<?> value1, ValueProvider<?> value2) {
        this.value1 = (ValueProvider<T>) value1;
        this.value2 = (ValueProvider<T>) value2;
        return this;
    }

    public static DoubleFunction<?> getDoubleFunction(JsonObject object) {
        if (object.has("input1") && object.has("input2")) {
            ValueProvider<?> provider = Custom.VALUE_PROVIDER.get(Identifier.tryParse(object.get("type").getAsString()));
            if (provider instanceof DoubleFunction<?> doubleFunction) {
                ValueProvider<?> input1 = ValueProvider.getFromJson(object.getAsJsonObject("input1"));
                ValueProvider<?> input2 = ValueProvider.getFromJson(object.getAsJsonObject("input2"));
                return doubleFunction.withValues(input1, input2);
            }
        }
        throw new JsonSyntaxException("Function is missing \"input1\" and/or \"input2\"");
    }

    @Override
    public T getValue() {
        return compute(value1, value2);
    }
}
