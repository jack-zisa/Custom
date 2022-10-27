package creoii.custom.util.math.function;

import creoii.custom.util.math.DoubleValueHolder;

public final class Functions {
    public static final SingleFunction NONE_SINGLE = new SingleFunction("none") {
        @Override
        public double compute(DoubleValueHolder value) {
            return value.getValue();
        }
    };

    public static final SingleFunction ABS = new SingleFunction("abs") {
        @Override
        public double compute(DoubleValueHolder value) {
            return Math.abs(value.getValue());
        }
    };

    public static final SingleFunction SQRT = new SingleFunction("sqrt") {
        @Override
        public double compute(DoubleValueHolder value) { return Math.sqrt(value.getValue()); }
    };

    public static final DoubleFunction NONE_DOUBLE = new DoubleFunction("none") {
        @Override
        public double compute(DoubleValueHolder value1, DoubleValueHolder value2) {
            return value1.getValue();
        }
    };

    public static final DoubleFunction ADD = new DoubleFunction("add") {
        @Override
        public double compute(DoubleValueHolder value1, DoubleValueHolder value2) {
            return value1.getValue() + value2.getValue();
        }
    };

    public static final DoubleFunction SUBTRACT = new DoubleFunction("subtract") {
        @Override
        public double compute(DoubleValueHolder value1, DoubleValueHolder value2) {
            return value1.getValue() - value2.getValue();
        }
    };

    public static final DoubleFunction MULTIPLY = new DoubleFunction("multiply") {
        @Override
        public double compute(DoubleValueHolder value1, DoubleValueHolder value2) {
            return value1.getValue() * value2.getValue();
        }
    };

    public static final DoubleFunction DIVIDE = new DoubleFunction("divide") {
        @Override
        public double compute(DoubleValueHolder value1, DoubleValueHolder value2) {
            return value1.getValue() / value2.getValue();
        }
    };

    public static final DoubleFunction POW = new DoubleFunction("pow") {
        @Override
        public double compute(DoubleValueHolder value1, DoubleValueHolder value2) {
            return Math.pow(value1.getValue(), value2.getValue());
        }
    };
}
