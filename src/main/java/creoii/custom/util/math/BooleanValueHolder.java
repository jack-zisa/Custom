package creoii.custom.util.math;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import creoii.custom.util.math.bool.BooleanProvider;
import net.minecraft.util.JsonHelper;

public interface BooleanValueHolder extends ValueHolder {
    boolean getValue();

    static BooleanValueHolder getFromJson(JsonObject object, String name) {
        JsonObject object1 = JsonHelper.getObject(object, name);
        if (BooleanProvider.isBooleanProvider(object1)) {
            return BooleanProvider.getByType(object1);
        }
        throw new JsonSyntaxException("Json object is not a function or a boolean provider");
    }
}
