package creoii.custom.util.math.number;

import com.google.gson.JsonObject;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

public class WorldNumberProvider extends NumberProvider {
    private World world;
    private double value;

    public WorldNumberProvider() {
        super("world");
    }

    public WorldNumberProvider setWorld(World world) {
        this.world = world;
        return this;
    }

    public WorldNumberProvider withValue(String type) {
        this.value = getValue(type);
        return this;
    }

    public double getValue(String type) {
        return switch (type) {
            case "time" -> world.getTime();
            case "sea_level" -> world.getSeaLevel();
            case "height" -> world.getHeight();
            case "bottom_y" -> world.getBottomY();
            case "top_y" -> world.getTopY();
            case "ambient_darkness" -> world.getAmbientDarkness();
            case "time_of_day" -> world.getTimeOfDay();
            case "lunar_time" -> world.getLunarTime();
            case "max_light_level" -> world.getMaxLightLevel();
            case "moon_phase" -> world.getMoonPhase();
            case "moon_size" -> world.getMoonSize();
            default -> 0d;
        };
    }

    @Override
    public double getValue() {
        return getValue("");
    }

    public WorldNumberProvider getFromJson(JsonObject object) {
        return this.withValue(JsonHelper.getString(object, "value", ""));
    }
}
