package creoii.custom.util.provider;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import creoii.custom.Custom;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import java.util.Map;

public class WorldValueProvider extends ValueProvider<Object> {
    private static final Map<String, Primitive> VALUES = new ImmutableMap.Builder<String, Primitive>()
            .put("time", Primitive.DOUBLE)
            .put("sea_level", Primitive.DOUBLE)
            .put("height", Primitive.DOUBLE)
            .put("bottom_y", Primitive.DOUBLE)
            .put("top_y", Primitive.DOUBLE)
            .put("ambient_darkness", Primitive.DOUBLE)
            .put("time_of_day", Primitive.DOUBLE)
            .put("lunar_time", Primitive.DOUBLE)
            .put("max_light_level", Primitive.DOUBLE)
            .put("moon_phase", Primitive.DOUBLE)
            .put("moon_size", Primitive.DOUBLE)
            .put("is_raining", Primitive.BOOLEAN)
            .put("is_day", Primitive.BOOLEAN)
            .put("is_night", Primitive.BOOLEAN)
            .put("is_thundering", Primitive.BOOLEAN)
            .put("is_debug", Primitive.BOOLEAN)
            .put("difficulty", Primitive.STRING)
            .put("dimension", Primitive.STRING)
            .build();

    private World world;
    private Object value;

    public WorldValueProvider setWorld(World world) {
        this.world = world;
        return this;
    }

    public WorldValueProvider withValue(Object value) {
        this.value = value;
        return this;
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

    public boolean getBooleanValue(String type) {
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

    @Override
    public Object getValue() {
        return value;
    }

    public static WorldValueProvider getFromJson(JsonObject object) {
        if (object.has("value")) {
            ValueProvider<?> provider = Custom.VALUE_PROVIDER.get(Identifier.tryParse(object.get("type").getAsString()));
            if (provider instanceof WorldValueProvider worldValueProvider) {
                String value = object.get("value").getAsString();
                switch (VALUES.get(value)) {
                    case DOUBLE -> {
                        return worldValueProvider.withValue(worldValueProvider.getDoubleValue(value));
                    }
                    case STRING -> {
                        return worldValueProvider.withValue(worldValueProvider.getStringVal(value));
                    }
                    case BOOLEAN -> {
                        return worldValueProvider.withValue(worldValueProvider.getBooleanValue(value));
                    }
                }
            }
        }
        throw new JsonSyntaxException("Provider is missing \"value\"");
    }
}
