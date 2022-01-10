package creoii.custom.util.math;

import com.google.gson.JsonObject;
import net.minecraft.util.JsonHelper;

public class SingleFunction extends Function {
    public SingleFunction(String name) {
        super(name);
    }

    public SingleFunction getFromJson(JsonObject object) {
        Function function = Function.getByType(JsonHelper.getString(object, "type"));
        function.setInput(Function.getByType(JsonHelper.getString(object, "first")));
        if (function instanceof SingleFunction) {
            return (SingleFunction) function;
        }
        return null;
    }

    public NumberProvider compute(NumberProvider first) {
        return new NumberProvider();
    }
}
