package creoii.custom.util.math;

public abstract class Function {
    private final String name;
    public Function input;
    public NumberProvider provider;

    public Function(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Function setInput(Function function) {
        this.input = function;
        return this;
    }

    public Function setInput(NumberProvider provider) {
        this.provider = provider;
        return this;
    }

    public static Function getByType(String name) {
        return switch (name) {
            case "add" -> Functions.ADD;
            case "subtract" -> Functions.SUBTRACT;
            case "multiply" -> Functions.MULTIPLY;
            case "divide" -> Functions.DIVIDE;
            default -> Functions.NONE;
        };
    }
}
