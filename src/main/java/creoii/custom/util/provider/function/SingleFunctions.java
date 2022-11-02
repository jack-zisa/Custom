package creoii.custom.util.provider.function;

import creoii.custom.Custom;
import creoii.custom.util.provider.ValueProvider;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public final class SingleFunctions {
    public static final SingleFunction<Double> ABS = new SingleFunction<>() {
        @Override
        public Double compute(ValueProvider<Double> value) {
            return Math.abs(value.getValue());
        }
    };

    public static final SingleFunction<Double> SQRT = new SingleFunction<>() {
        @Override
        public Double compute(ValueProvider<Double> value) { return Math.sqrt(value.getValue()); }
    };

    public static final SingleFunction<Double> LOG = new SingleFunction<>() {
        @Override
        public Double compute(ValueProvider<Double> value) { return Math.log(value.getValue()); }
    };

    public static final SingleFunction<Double> LOG_10 = new SingleFunction<>() {
        @Override
        public Double compute(ValueProvider<Double> value) { return Math.log10(value.getValue()); }
    };

    public static final SingleFunction<Double> ROUND = new SingleFunction<>() {
        @Override
        public Double compute(ValueProvider<Double> value) { return (double) Math.round(value.getValue()); }
    };

    public static final SingleFunction<Double> CEIL = new SingleFunction<>() {
        @Override
        public Double compute(ValueProvider<Double> value) { return Math.ceil(value.getValue()); }
    };

    public static final SingleFunction<Double> FLOOR = new SingleFunction<>() {
        @Override
        public Double compute(ValueProvider<Double> value) { return Math.floor(value.getValue()); }
    };

    public static final SingleFunction<Double> SIN = new SingleFunction<>() {
        @Override
        public Double compute(ValueProvider<Double> value) { return Math.sin(value.getValue()); }
    };

    public static final SingleFunction<Double> COS = new SingleFunction<>() {
        @Override
        public Double compute(ValueProvider<Double> value) { return Math.cos(value.getValue()); }
    };

    public static final SingleFunction<Double> TAN = new SingleFunction<>() {
        @Override
        public Double compute(ValueProvider<Double> value) { return Math.tan(value.getValue()); }
    };

    public static final SingleFunction<Double> NEGATE = new SingleFunction<>() {
        @Override
        public Double compute(ValueProvider<Double> value) { return -value.getValue(); }
    };

    public static final SingleFunction<Double> SQUARE = new SingleFunction<>() {
        @Override
        public Double compute(ValueProvider<Double> value) { return value.getValue() * value.getValue(); }
    };

    public static void register() {
        ValueProvider.register(new Identifier(Custom.NAMESPACE, "abs"), ABS);
        ValueProvider.register(new Identifier(Custom.NAMESPACE, "sqrt"), SQRT);
        ValueProvider.register(new Identifier(Custom.NAMESPACE, "log"), LOG);
        ValueProvider.register(new Identifier(Custom.NAMESPACE, "log_10"), LOG_10);
        ValueProvider.register(new Identifier(Custom.NAMESPACE, "round"), ROUND);
        ValueProvider.register(new Identifier(Custom.NAMESPACE, "ceil"), CEIL);
        ValueProvider.register(new Identifier(Custom.NAMESPACE, "floor"), FLOOR);
        ValueProvider.register(new Identifier(Custom.NAMESPACE, "sin"), SIN);
        ValueProvider.register(new Identifier(Custom.NAMESPACE, "cos"), COS);
        ValueProvider.register(new Identifier(Custom.NAMESPACE, "tan"), TAN);
        ValueProvider.register(new Identifier(Custom.NAMESPACE, "negate"), NEGATE);
        ValueProvider.register(new Identifier(Custom.NAMESPACE, "square"), SQUARE);
    }
}
