package creoii.custom.util.provider;

import net.minecraft.util.math.random.Random;

public class ConstantDoubleProvider implements DoubleProvider {
    public static final ConstantDoubleProvider ZERO = new ConstantDoubleProvider(0f);
    private final double value;

    public static ConstantDoubleProvider create(double value) {
        return value == 0d ? ZERO : new ConstantDoubleProvider(value);
    }

    private ConstantDoubleProvider(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public double get(Random random) {
        return value;
    }

    public double getMin() {
        return value;
    }

    public double getMax() {
        return value + 1d;
    }

    public String toString() {
        return Double.toString(value);
    }
}