package creoii.custom.util.provider;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

public record UniformDoubleProvider(double min, double max) implements DoubleProvider {
    public static UniformDoubleProvider create(double min, double max) {
        if (max <= min) {
            throw new IllegalArgumentException("Max must exceed min");
        } else return new UniformDoubleProvider(min, max);
    }

    public double get(Random random) {
        return MathHelper.nextBetween(random, (float) min, (float) max);
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public String toString() {
        return "[" + min + "-" + max + "]";
    }
}
