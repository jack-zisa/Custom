package creoii.custom.util.provider.function;

import creoii.custom.Custom;
import creoii.custom.util.provider.ValueProvider;
import net.minecraft.util.Identifier;

public final class DoubleFunctions {
    public static final DoubleFunction<Double> ADD = new DoubleFunction<>() {
        @Override
        public Double compute(ValueProvider<Double> value1, ValueProvider<Double> value2) {
            return value1.getValue() + value2.getValue();
        }
    };

    public static final DoubleFunction<Double> SUBTRACT = new DoubleFunction<>() {
        @Override
        public Double compute(ValueProvider<Double> value1, ValueProvider<Double> value2) {
            return value1.getValue() - value2.getValue();
        }
    };

    public static final DoubleFunction<Double> MULTIPLY = new DoubleFunction<>() {
        @Override
        public Double compute(ValueProvider<Double> value1, ValueProvider<Double> value2) {
            return value1.getValue() * value2.getValue();
        }
    };

    public static final DoubleFunction<Double> DIVIDE = new DoubleFunction<>() {
        @Override
        public Double compute(ValueProvider<Double> value1, ValueProvider<Double> value2) {
            return value1.getValue() / value2.getValue();
        }
    };

    public static final DoubleFunction<Double> POW = new DoubleFunction<>() {
        @Override
        public Double compute(ValueProvider<Double> value1, ValueProvider<Double> value2) {
            return Math.pow(value1.getValue(), value2.getValue());
        }
    };

    public static final DoubleFunction<Double> MIN = new DoubleFunction<>() {
        @Override
        public Double compute(ValueProvider<Double> value1, ValueProvider<Double> value2) {
            return Math.min(value1.getValue(), value2.getValue());
        }
    };

    public static final DoubleFunction<Double> MAX = new DoubleFunction<>() {
        @Override
        public Double compute(ValueProvider<Double> value1, ValueProvider<Double> value2) {
            return Math.max(value1.getValue(), value2.getValue());
        }
    };

    public static final DoubleFunction<Double> MOD = new DoubleFunction<>() {
        @Override
        public Double compute(ValueProvider<Double> value1, ValueProvider<Double> value2) {
            return value1.getValue() % value2.getValue();
        }
    };

    public static final DoubleFunction<Double> HYPOT = new DoubleFunction<>() {
        @Override
        public Double compute(ValueProvider<Double> value1, ValueProvider<Double> value2) {
            return Math.hypot(value1.getValue(), value2.getValue());
        }
    };

    public static void register() {
        ValueProvider.register(new Identifier(Custom.NAMESPACE, "add"), ADD);
        ValueProvider.register(new Identifier(Custom.NAMESPACE, "subtract"), SUBTRACT);
        ValueProvider.register(new Identifier(Custom.NAMESPACE, "multiple"), MULTIPLY);
        ValueProvider.register(new Identifier(Custom.NAMESPACE, "divide"), DIVIDE);
        ValueProvider.register(new Identifier(Custom.NAMESPACE, "pow"), POW);
        ValueProvider.register(new Identifier(Custom.NAMESPACE, "min"), MIN);
        ValueProvider.register(new Identifier(Custom.NAMESPACE, "max"), MAX);
        ValueProvider.register(new Identifier(Custom.NAMESPACE, "mod"), MOD);
        ValueProvider.register(new Identifier(Custom.NAMESPACE, "hypot"), HYPOT);
    }
}
