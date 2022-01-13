package creoii.custom.util.math.number;

import com.google.gson.JsonObject;
import net.minecraft.util.JsonHelper;

import java.util.Random;

public class RandomNumberProvider extends NumberProvider {
    private static final Random RANDOM = new Random();
    private double min;
    private double max;

    public RandomNumberProvider() {
        super("random");
    }

    public RandomNumberProvider withValues(double min, double max) {
        this.min = min;
        this.max = max;
        return this;
    }

    public double getValue() {
        return RANDOM.nextDouble(max) + min;
    }

    public RandomNumberProvider getFromJson(JsonObject object) {
        double min = JsonHelper.getDouble(object, "min_value", 0d);
        double max = JsonHelper.getDouble(object, "max_value", 0d);
        return this.withValues(min, max);
    }
}
