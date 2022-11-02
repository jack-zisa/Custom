package creoii.custom.util.provider.function;

import creoii.custom.Custom;
import creoii.custom.util.provider.ValueProvider;
import net.minecraft.util.Identifier;

public final class BooleanFunctions {
    public static final SingleFunction<Boolean> INVERSE = new SingleFunction<>() {
        @Override
        public Boolean compute(ValueProvider<Boolean> value) {
            return !value.getValue();
        }
    };

    public static final DoubleFunction<Boolean> AND = new DoubleFunction<>() {
        @Override
        public Boolean compute(ValueProvider<Boolean> value1, ValueProvider<Boolean> value2) {
            return value1.getValue() && value2.getValue();
        }
    };

    public static final DoubleFunction<Boolean> OR = new DoubleFunction<>() {
        @Override
        public Boolean compute(ValueProvider<Boolean> value1, ValueProvider<Boolean> value2) {
            return value1.getValue() || value2.getValue();
        }
    };

    public static final DoubleFunction<Boolean> EQUALS = new DoubleFunction<>() {
        @Override
        public Boolean compute(ValueProvider<Boolean> value1, ValueProvider<Boolean> value2) {
            return value1.getValue() == value2.getValue();
        }
    };

    public static void register() {
        ValueProvider.register(new Identifier(Custom.NAMESPACE, "inverse"), INVERSE);
        ValueProvider.register(new Identifier(Custom.NAMESPACE, "and"), AND);
        ValueProvider.register(new Identifier(Custom.NAMESPACE, "or"), OR);
        ValueProvider.register(new Identifier(Custom.NAMESPACE, "equals"), EQUALS);
    }
}
