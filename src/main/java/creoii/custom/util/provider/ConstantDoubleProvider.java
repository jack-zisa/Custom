package creoii.custom.util.provider;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.random.Random;

public class ConstantDoubleProvider extends DoubleProvider {
    public static final ConstantDoubleProvider ZERO = new ConstantDoubleProvider(0f);
    public static final Codec<ConstantDoubleProvider> CODEC;
    private final double value;

    public static ConstantDoubleProvider create(double value) {
        return value == 0f ? ZERO : new ConstantDoubleProvider(value);
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

    public DoubleProviderType<?> getType() {
        return DoubleProviderType.CONSTANT;
    }

    public String toString() {
        return Double.toString(value);
    }

    static {
        CODEC = Codec.either(Codec.DOUBLE, RecordCodecBuilder.create(instance -> {
            return instance.group(Codec.DOUBLE.fieldOf("value").forGetter(provider -> {
                return provider.value;
            })).apply(instance, ConstantDoubleProvider::new);
        })).xmap(either -> {
            return (ConstantDoubleProvider)either.map(ConstantDoubleProvider::create, provider -> {
                return provider;
            });
        }, provider -> {
            return Either.left(provider.value);
        });
    }
}