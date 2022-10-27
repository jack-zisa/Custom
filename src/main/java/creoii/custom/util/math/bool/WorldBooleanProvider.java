package creoii.custom.util.math.bool;

import com.google.gson.JsonObject;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

public class WorldBooleanProvider extends BooleanProvider {
    private World world;
    private Boolean value;

    public WorldBooleanProvider() {
        super("world");
    }

    public WorldBooleanProvider setWorld(World world) {
        this.world = world;
        return this;
    }

    public WorldBooleanProvider withValue(String type) {
        this.value = getValue(type);
        return this;
    }

    public Boolean getValue(String type) {
        return switch (type) {
            case "is_raining" -> world.isRaining();
            case "is_day" -> world.isDay();
            case "is_night" -> world.isNight();
            case "is_thundering" -> world.isThundering();
            case "is_debug" -> world.isDebugWorld();
            default -> true;
        };
    }

    @Override
    public boolean getValue() {
        return value;
    }

    public WorldBooleanProvider getFromJson(JsonObject object) {
        return this.withValue(JsonHelper.getString(object, "value", ""));
    }
}
