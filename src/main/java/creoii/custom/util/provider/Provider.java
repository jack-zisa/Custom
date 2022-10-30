package creoii.custom.util.provider;

import com.google.gson.JsonObject;

public abstract class Provider<T> {
    private T value;

    public abstract Provider<T> getFromJson(JsonObject object);

    public T getValue() {
        return value;
    }

    public Provider<T> setValue(T value) {
        this.value = value;
        return this;
    }

    public enum Primitive {
        BOOLEAN,
        DOUBLE,
        STRING
    }
}
