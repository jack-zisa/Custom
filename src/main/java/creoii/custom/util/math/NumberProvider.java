package creoii.custom.util.math;

import com.google.gson.JsonObject;
import net.minecraft.util.JsonHelper;

public class NumberProvider implements ValueHolder {
    private double value;

    public NumberProvider() { this.value = 0d; }
    public NumberProvider(double d) {
        this.value = d;
    }
    public NumberProvider(NumberProvider provider) {
        this.value = provider.getValue();
    }

    public double getValue() {
        return value;
    }
    public void setValue(double value) { this.value = value; }

    public static NumberProvider get(JsonObject object) {
        String type = JsonHelper.getString(object, "type");
        return switch (type) {
            case "world" -> new WorldNumberProvider();
            case "random" -> new RandomNumberProvider();
            default -> new ConstantNumberProvider();
        };
    }
}
