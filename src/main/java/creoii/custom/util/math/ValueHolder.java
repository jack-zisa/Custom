package creoii.custom.util.math;

public interface ValueHolder {
    Type getType();

    enum Type {
        FUNCTION,
        PROVIDER
    }
}
