package creoii.custom.util.provider.function;

import creoii.custom.Custom;
import creoii.custom.util.provider.ValueProvider;
import net.minecraft.util.Identifier;

public final class StringFunctions {
    public static final SingleFunction<String> TO_LOWER = new SingleFunction<>() {
        @Override
        public String compute(ValueProvider<String> value) {
            return value.getValue().toLowerCase();
        }
    };

    public static final SingleFunction<String> TO_UPPER = new SingleFunction<>() {
        @Override
        public String compute(ValueProvider<String> value) {
            return value.getValue().toUpperCase();
        }
    };

    public static void register() {
        ValueProvider.register(new Identifier(Custom.NAMESPACE, "to_lower"), TO_LOWER);
        ValueProvider.register(new Identifier(Custom.NAMESPACE, "to_upper"), TO_UPPER);
    }
}
