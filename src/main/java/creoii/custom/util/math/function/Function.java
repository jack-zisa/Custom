package creoii.custom.util.math.function;

import com.google.gson.JsonObject;
import creoii.custom.util.math.DoubleValueHolder;
import net.minecraft.util.JsonHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class Function implements DoubleValueHolder {
    private static final List<String> SINGLE_FUNCTIONS = new ArrayList<>();
    private static final List<String> DOUBLE_FUNCTIONS = new ArrayList<>();
    private final String name;

    public Function(String name) {
        this.name = name;
    }

    public static List<String> getSingleFunctions() {
        return SINGLE_FUNCTIONS;
    }

    public static List<String> getDoubleFunctions() {
        return DOUBLE_FUNCTIONS;
    }

    public String getName() {
        return name;
    }

    public static boolean isSingleFunction(JsonObject object) {
        String type = JsonHelper.getString(object, "type");
        return SINGLE_FUNCTIONS.contains(type);
    }

    public static boolean isDoubleFunction(JsonObject object) {
        String type = JsonHelper.getString(object, "type");
        return DOUBLE_FUNCTIONS.contains(type);
    }

    public static Function getByType(JsonObject object) {
        if (isSingleFunction(object)) return SingleFunction.getByType(object);
        else if (isDoubleFunction(object)) return DoubleFunction.getByType(object);
        else return null;
    }

    @Override
    public Type getType() {
        return Type.FUNCTION;
    }
}
