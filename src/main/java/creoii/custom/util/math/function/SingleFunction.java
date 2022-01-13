package creoii.custom.util.math.function;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import creoii.custom.util.math.ValueHolder;
import creoii.custom.util.math.number.NumberProvider;
import net.minecraft.util.JsonHelper;

public abstract class SingleFunction extends Function {
    private ValueHolder value;

    public SingleFunction(String name) {
        super(name);
        Function.getSingleFunctions().add(name);
    }
    public static SingleFunction getFromJson(JsonObject object) {
        if (object.has("value")) {
            SingleFunction function = SingleFunction.getByType(JsonHelper.getString(object, "type"));
            ValueHolder value = null;
            JsonObject object1 = JsonHelper.getObject(object, "input");
            if (Function.isSingleFunction(object1) || Function.isDoubleFunction(object1)) {
                value = Function.getByType(object1);
            } else if (NumberProvider.isNumberProvider(object1)) {
                value = NumberProvider.getByType(object1);
            }
            if (value != null) return function.withValue(value);
        }
        throw new JsonSyntaxException("Function is missing \"input\"");
    }

    public SingleFunction withValue(ValueHolder value) {
        this.value = value;
        return this;
    }

    public static SingleFunction getByType(String name) {
        return switch (name) {
            case "abt" -> Functions.ABS;
            case "sqrt" -> Functions.SQRT;
            default -> Functions.NONE_SINGLE;
        };
    }

    public abstract double compute(ValueHolder value);

    @Override
    public double getValue() {
        return compute(value);
    }
}
