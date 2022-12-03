package creoii.custom.util.provider;

import com.mojang.serialization.Codec;
import creoii.custom.Custom;
import net.minecraft.util.registry.Registry;

public interface DoubleProviderType<P extends DoubleProvider> {
    DoubleProviderType<ConstantDoubleProvider> CONSTANT = register("constant", ConstantDoubleProvider.CODEC);
    DoubleProviderType<UniformDoubleProvider> UNIFORM = register("uniform", UniformDoubleProvider.CODEC);

    Codec<P> codec();

    static <P extends DoubleProvider> DoubleProviderType<P> register(String id, Codec<P> codec) {
        return Registry.register(Custom.DOUBLE_PROVIDER_TYPE, id, () -> codec);
    }
}