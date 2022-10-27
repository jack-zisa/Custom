package creoii.custom.util.math.bool;

import com.google.gson.JsonObject;
import creoii.custom.util.math.BooleanValueHolder;
import creoii.custom.util.math.ValueHolder;
import creoii.custom.util.math.number.NumberProviders;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public abstract class BooleanProvider implements BooleanValueHolder {
    private static final List<String> BOOLEAN_PROVIDERS = new ArrayList<>();
    private final String name;

    public BooleanProvider(String name) {
        this.name = name;
        BOOLEAN_PROVIDERS.add(name);
    }

    public String getName() {
        return name;
    }

    public static boolean isBooleanProvider(JsonObject object) {
        String type = JsonHelper.getString(object, "type");
        return BOOLEAN_PROVIDERS.contains(type);
    }

    public static BooleanProvider getByType(JsonObject object) {
        String type = JsonHelper.getString(object, "type");
        return switch (type) {
            case "random" -> BooleanProviders.RANDOM.getFromJson(object);
            case "constant" -> BooleanProviders.CONSTANT.getFromJson(object);
            case "world" -> BooleanProviders.WORLD.getFromJson(object);
            case "entity" -> BooleanProviders.ENTITY.getFromJson(object);
            case "living_entity" -> BooleanProviders.LIVING_ENTITY.getFromJson(object);
            default ->  BooleanProviders.NONE;
        };
    }

    public static BooleanProvider getByType(JsonObject object, World world) {
        if (JsonHelper.getString(object, "type").equals("world"))
            return BooleanProviders.WORLD.getFromJson(object).setWorld(world);
        return BooleanProviders.NONE;
    }

    @Override
    public Type getType() {
        return Type.PROVIDER;
    }
}
