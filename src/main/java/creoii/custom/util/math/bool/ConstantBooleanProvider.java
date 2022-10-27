package creoii.custom.util.math.bool;

import com.google.gson.JsonObject;
import creoii.custom.util.math.number.NumberProvider;
import net.minecraft.util.JsonHelper;

public class ConstantBooleanProvider extends BooleanProvider {
    private boolean value;

    public ConstantBooleanProvider() {
        super("constant");
    }

    public ConstantBooleanProvider withValue(boolean value) {
        this.value = value;
        return this;
    }

    public boolean getValue() {
        return value;
    }

    public ConstantBooleanProvider getFromJson(JsonObject object) {
        return this.withValue(JsonHelper.getBoolean(object, "value", true));
    }
}
