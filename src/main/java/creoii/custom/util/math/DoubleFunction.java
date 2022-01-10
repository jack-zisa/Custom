package creoii.custom.util.math;

import com.google.gson.JsonObject;
import net.minecraft.util.JsonHelper;

public class DoubleFunction extends Function implements ValueHolder {
    private Function input2;
    private NumberProvider provider2;

    public DoubleFunction(String name) {
        super(name);
    }

    public DoubleFunction setInput(Function input1, Function input2) {
        setInput(input1);
        this.input2 = input2;
        return this;
    }

    public DoubleFunction setInput(NumberProvider provider1, NumberProvider provider2) {
        setInput(provider1);
        this.provider2 = provider2;
        return this;
    }

    public DoubleFunction getFromJson(JsonObject object) {
        Function function = Function.getByType(JsonHelper.getString(object, "type"));
        NumberProvider provider1 = null;
        NumberProvider provider2 = null;
        if (JsonHelper.hasJsonObject(object, "function1")) {
            JsonObject obj = JsonHelper.getObject(object, "function1");
            provider1 = new ConstantNumberProvider().setValueAndReturn(Function.getByType(JsonHelper.getString(obj, "type")).provider.getValue());
        } else if (JsonHelper.hasJsonObject(object, "provider1")) {
            JsonObject obj = JsonHelper.getObject(object, "provider1");
            provider1 = NumberProvider.get(obj);
        }
        if (JsonHelper.hasJsonObject(object, "function2")) {
            JsonObject obj = JsonHelper.getObject(object, "function2");
            provider2 = new ConstantNumberProvider().setValueAndReturn(Function.getByType(JsonHelper.getString(obj, "type")).provider.getValue());
        } else if (JsonHelper.hasJsonObject(object, "provider2")) {
            JsonObject obj = JsonHelper.getObject(object, "provider2");
            provider2 = NumberProvider.get(obj);
        }
        if (function instanceof DoubleFunction) {
            return ((DoubleFunction) function).setInput(provider1, provider2);
        }
        return null;
    }

    public NumberProvider compute(NumberProvider first, NumberProvider second) {
        return new NumberProvider();
    }

    @Override
    public double getValue() {
        return compute(provider, provider2).getValue();
    }
}
