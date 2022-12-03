package creoii.custom.util.provider;

import net.minecraft.util.math.random.Random;

public interface DoubleSupplier {
    double get(Random random);
}