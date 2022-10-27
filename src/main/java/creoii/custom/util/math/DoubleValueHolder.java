package creoii.custom.util.math;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import creoii.custom.util.math.function.Function;
import creoii.custom.util.math.number.NumberProvider;
import net.minecraft.util.JsonHelper;

public interface DoubleValueHolder extends ValueHolder {
    double getValue();

    static DoubleValueHolder getFromJson(JsonObject object, String name) {
        JsonObject object1 = JsonHelper.getObject(object, name);
        if (Function.isSingleFunction(object1) || Function.isDoubleFunction(object1)) {
            return Function.getByType(object1);
        } else if (NumberProvider.isNumberProvider(object1)) {
            return NumberProvider.getByType(object1);
        }
        throw new JsonSyntaxException("Json object is not a function or a number provider");
    }
}
