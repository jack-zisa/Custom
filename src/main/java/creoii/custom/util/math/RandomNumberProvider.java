package creoii.custom.util.math;

import java.util.Random;

public class RandomNumberProvider extends NumberProvider {
    private static final Random RANDOM = new Random();
    private double min;
    private double max;

    public RandomNumberProvider() {
        super();
    }

    public void setValues(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public double getValue() {
        return RANDOM.nextDouble(max) + min;
    }
}
