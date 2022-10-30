package creoii.custom.util.provider;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import java.util.Map;

public class WorldProvider {
    private static final Map<String, Provider.Primitive> VALUES = new ImmutableMap.Builder<String, Provider.Primitive>()
            .put("time", Provider.Primitive.DOUBLE)
            .put("sea_level", Provider.Primitive.DOUBLE)
            .put("height", Provider.Primitive.DOUBLE)
            .put("bottom_y", Provider.Primitive.DOUBLE)
            .put("top_y", Provider.Primitive.DOUBLE)
            .put("ambient_darkness", Provider.Primitive.DOUBLE)
            .put("time_of_day", Provider.Primitive.DOUBLE)
            .put("lunar_time", Provider.Primitive.DOUBLE)
            .put("max_light_level", Provider.Primitive.DOUBLE)
            .put("moon_phase", Provider.Primitive.DOUBLE)
            .put("moon_size", Provider.Primitive.DOUBLE)
            .put("is_raining", Provider.Primitive.BOOLEAN)
            .put("is_day", Provider.Primitive.BOOLEAN)
            .put("is_night", Provider.Primitive.BOOLEAN)
            .put("is_thundering", Provider.Primitive.BOOLEAN)
            .put("is_debug", Provider.Primitive.BOOLEAN)
            .put("difficulty", Provider.Primitive.STRING)
            .put("dimension", Provider.Primitive.STRING)
            .build();

    private World world;
    private double doubleVal;
    private boolean booleanVal;
    private String stringVal;

    public WorldProvider setWorld(World world) {
        this.world = world;
        return this;
    }

    public WorldProvider setDoubleVal(double doubleVal) {
        this.doubleVal = doubleVal;
        return this;
    }

    public WorldProvider setBooleanVal(boolean booleanVal) {
        this.booleanVal = booleanVal;
        return this;
    }

    public WorldProvider setStringVal(String stringVal) {
        this.stringVal = stringVal;
        return this;
    }

    public double getDouble() {
        return doubleVal;
    }

    public boolean getBoolean() {
        return booleanVal;
    }

    public String getString() {
        return stringVal;
    }

    public double getDoubleValue(String type) {
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

    public Boolean getBooleanValue(String type) {
        return switch (type) {
            case "is_raining" -> world.isRaining();
            case "is_day" -> world.isDay();
            case "is_night" -> world.isNight();
            case "is_thundering" -> world.isThundering();
            case "is_debug" -> world.isDebugWorld();
            default -> true;
        };
    }

    public String getStringVal(String type) {
        switch (type) {
            case "difficulty" -> {
                return world.getDifficulty().getName();
            }
            case "dimension" -> {
                DimensionType dimensionType = BuiltinRegistries.DIMENSION_TYPE.get(world.getDimensionKey());
                if (dimensionType != null) {
                    return dimensionType.toString();
                }
            }
            default -> {
                return "";
            }
        }
        return "";
    }

    public WorldProvider getFromJson(JsonObject object) {
        if (object.has("value")) {
            String value = object.get("value").getAsString();
            switch (VALUES.get(value)) {
                case DOUBLE -> {
                    return setDoubleVal(getDoubleValue(value));
                }
                case STRING -> {
                    return setStringVal(getStringVal(value));
                }
                case BOOLEAN -> {
                    return setBooleanVal(getBooleanValue(value));
                }
            }
        }
        return this;
    }
}
