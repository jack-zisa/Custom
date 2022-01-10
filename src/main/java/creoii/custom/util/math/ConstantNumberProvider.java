package creoii.custom.util.math;

public class ConstantNumberProvider extends NumberProvider {
    private double value;

    public ConstantNumberProvider() {
        super();
    }

    public ConstantNumberProvider setValueAndReturn(double value) {
        this.value = value;
        return this;
    }
    public double getValue() {
        return value;
    }
}
