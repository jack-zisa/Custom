package creoii.custom.util.math.number;

import com.google.gson.JsonObject;
import creoii.custom.util.math.ValueHolder;
import net.minecraft.util.JsonHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class NumberProvider implements ValueHolder {
    private static final List<String> NUMBER_PROVIDERS = new ArrayList<>();
    private final String name;

    public NumberProvider(String name) {
        this.name = name;
        NUMBER_PROVIDERS.add(name);
    }

    public String getName() {
        return name;
    }

    public static boolean isNumberProvider(JsonObject object) {
        String type = JsonHelper.getString(object, "type");
        return NUMBER_PROVIDERS.contains(type);
    }

    public static NumberProvider getByType(JsonObject object) {
        String type = JsonHelper.getString(object, "type");
        return switch (type) {
            case "random" -> NumberProviders.RANDOM.getFromJson(object);
            case "constant" -> NumberProviders.CONSTANT.getFromJson(object);
            case "world" -> NumberProviders.WORLD.getFromJson(object);
            default ->  NumberProviders.NONE;
        };
    }

    @Override
    public Type getType() {
        return Type.PROVIDER;
    }
}
