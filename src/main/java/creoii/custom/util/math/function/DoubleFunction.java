package creoii.custom.util.math.function;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import creoii.custom.util.math.DoubleValueHolder;
import creoii.custom.util.math.ValueHolder;
import creoii.custom.util.math.number.NumberProvider;
import net.minecraft.util.JsonHelper;

public abstract class DoubleFunction extends Function implements DoubleValueHolder {
    private DoubleValueHolder value1;
    private DoubleValueHolder value2;

    public DoubleFunction(String name) {
        super(name);
        Function.getDoubleFunctions().add(name);
    }

    public static DoubleFunction getFromJson(JsonObject object) {
        if (object.has("input1") && object.has("input2")) {
            DoubleFunction function = DoubleFunction.getByType(JsonHelper.getString(object, "type"));

            DoubleValueHolder first = null;
            JsonObject object1 = JsonHelper.getObject(object, "input1");
            if (Function.isSingleFunction(object1) || Function.isDoubleFunction(object1)) {
                first = Function.getByType(object1);
            } else if (NumberProvider.isNumberProvider(object1)) {
                first = NumberProvider.getByType(object1);
            }

            DoubleValueHolder second = null;
            JsonObject object2 = JsonHelper.getObject(object, "input2");
            if (Function.isSingleFunction(object2) || Function.isDoubleFunction(object2)) {
                second = Function.getByType(object2);
            } else if (NumberProvider.isNumberProvider(object2)) {
                second = NumberProvider.getByType(object2);
            }

            if (first != null && second != null) {
                return function.withValues(first, second);
            }
        }
        throw new JsonSyntaxException("Function is missing \"input1\" and/or \"input2\"");
    }

    public DoubleFunction withValues(DoubleValueHolder value1, DoubleValueHolder value2) {
        this.value1 = value1;
        this.value2 = value2;
        return this;
    }

    public static DoubleFunction getByType(String name) {
        return switch (name) {
            case "add" -> Functions.ADD;
            case "subtract" -> Functions.SUBTRACT;
            case "multiply" -> Functions.MULTIPLY;
            case "divide" -> Functions.DIVIDE;
            case "pow" -> Functions.POW;
            default -> Functions.NONE_DOUBLE;
        };
    }

    public abstract double compute(DoubleValueHolder value1, DoubleValueHolder value2);

    @Override
    public double getValue() {
        return compute(value1, value2);
    }
}
