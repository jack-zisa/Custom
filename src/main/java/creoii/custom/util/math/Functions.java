package creoii.custom.util.math;

public class Functions {
    public static final SingleFunction NONE = new SingleFunction("none") {
        @Override
        public NumberProvider compute(NumberProvider first) {
            return first;
        }
    };

    public static final DoubleFunction ADD = new DoubleFunction("add") {
        @Override
        public NumberProvider compute(NumberProvider first, NumberProvider second) {
            return new NumberProvider(first.getValue() + second.getValue());
        }
    };

    public static final DoubleFunction SUBTRACT = new DoubleFunction("subtract") {
        @Override
        public NumberProvider compute(NumberProvider first, NumberProvider second) {
            return new NumberProvider(first.getValue() - second.getValue());
        }
    };

    public static final DoubleFunction MULTIPLY = new DoubleFunction("multiply") {
        @Override
        public NumberProvider compute(NumberProvider first, NumberProvider second) {
            return new NumberProvider(first.getValue() * second.getValue());
        }
    };

    public static final DoubleFunction DIVIDE = new DoubleFunction("divide") {
        @Override
        public NumberProvider compute(NumberProvider first, NumberProvider second) {
            return new NumberProvider(first.getValue() / second.getValue());
        }
    };
}
