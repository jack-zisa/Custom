package creoii.custom.util.provider;

import net.minecraft.util.math.random.Random;

public interface DoubleProvider {
    double get(Random random);

    double getMin();

    double getMax();
}
