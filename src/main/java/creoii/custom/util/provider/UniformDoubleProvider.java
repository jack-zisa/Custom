package creoii.custom.util.provider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

import java.util.function.Function;

public class UniformDoubleProvider extends DoubleProvider {
    public static final Codec<UniformDoubleProvider> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(Codec.DOUBLE.fieldOf("min_inclusive").forGetter(provider -> {
            return provider.min;
        }), Codec.DOUBLE.fieldOf("max_exclusive").forGetter(provider -> {
            return provider.max;
        })).apply(instance, UniformDoubleProvider::new);
    }).comapFlatMap(provider -> {
        return provider.max <= provider.min ? DataResult.error("Max must be larger than min, min_inclusive: " + provider.min + ", max_exclusive: " + provider.max) : DataResult.success(provider);
    }, Function.identity());
    private final double min;
    private final double max;

    private UniformDoubleProvider(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public static UniformDoubleProvider create(double min, double max) {
        if (max <= min) {
            throw new IllegalArgumentException("Max must exceed min");
        } else return new UniformDoubleProvider(min, max);
    }

    public double get(Random random) {
        return MathHelper.nextBetween(random, (float)min, (float)max);
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public DoubleProviderType<?> getType() {
        return DoubleProviderType.UNIFORM;
    }

    public String toString() {
        return "[" + min + "-" + max + "]";
    }
}
