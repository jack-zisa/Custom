package creoii.custom.util.provider;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import creoii.custom.Custom;

import java.util.function.Function;

public abstract class DoubleProvider implements DoubleSupplier {
    private static final Codec<Either<Double, DoubleProvider>> DOUBLE_CODEC;
    public static final Codec<DoubleProvider> VALUE_CODEC;

    public static Codec<DoubleProvider> createValidatingCodec(double min, double max) {
        Function<DoubleProvider, DataResult<DoubleProvider>> function = (provider) -> {
            if (provider.getMin() < min) {
                return DataResult.error("Value provider too low: " + min + " [" + provider.getMin() + "-" + provider.getMax() + "]");
            } else {
                return provider.getMax() > max ? DataResult.error("Value provider too high: " + max + " [" + provider.getMin() + "-" + provider.getMax() + "]") : DataResult.success(provider);
            }
        };
        return VALUE_CODEC.flatXmap(function, function);
    }

    public abstract double getMin();

    public abstract double getMax();

    public abstract DoubleProviderType<?> getType();

    static {
        DOUBLE_CODEC = Codec.either(Codec.DOUBLE, Custom.DOUBLE_PROVIDER_TYPE.getCodec().dispatch(DoubleProvider::getType, DoubleProviderType::codec));
        VALUE_CODEC = DOUBLE_CODEC.xmap(either -> {
            return either.map(ConstantDoubleProvider::create, provider -> provider);
        }, provider -> {
            return provider.getType() == DoubleProviderType.CONSTANT ? Either.left(((ConstantDoubleProvider)provider).getValue()) : Either.right(provider);
        });
    }
}
