package creoii.custom.util.math.number;

import com.google.gson.JsonObject;
import net.minecraft.util.JsonHelper;

public class ConstantNumberProvider extends NumberProvider {
    private double value;

    public ConstantNumberProvider() {
        super("constant");
    }

    public ConstantNumberProvider withValue(double value) {
        this.value = value;
        return this;
    }

    public double getValue() {
        return value;
    }

    public ConstantNumberProvider getFromJson(JsonObject object) {
        return this.withValue(JsonHelper.getDouble(object, "value", 0d));
    }
}
