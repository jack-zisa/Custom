package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.BlockPosParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.WorldParameter;
import creoii.custom.util.json.CustomJsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.List;

public class WeatherMatchesCondition extends Condition {
    private WeatherType weatherType;

    @Override
    public WeatherMatchesCondition getFromJson(JsonObject object) {
        WeatherMatchesCondition condition = new WeatherMatchesCondition();
        condition.weatherType = WeatherType.valueOf(CustomJsonHelper.getString(object, new String[]{"weather", "weather_type"}).toUpperCase());
        return condition;
    }

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of(EventParameters.WORLD, EventParameters.BLOCK_POS);
    }

    @Override
    public boolean test(List<EventParameter> parameters) {
        WorldParameter worldParameter = (WorldParameter) EventParameter.find(parameters, EventParameters.WORLD);
        if (worldParameter != null) {
            BlockPosParameter blockPosParameter = (BlockPosParameter) EventParameter.find(parameters, EventParameters.BLOCK_POS);
            if (blockPosParameter != null) {
                World world = worldParameter.getWorld();
                BlockPos pos = blockPosParameter.getPos();
                boolean hasRain = world.hasRain(pos);
                if (weatherType == WeatherType.RAIN) {
                    return hasRain;
                } else if (weatherType == WeatherType.THUNDER) {
                    return hasRain && world.isThundering();
                } else if (weatherType == WeatherType.SNOW) {
                    return world.isRaining() && world.getBiome(pos).value().getPrecipitation() == Biome.Precipitation.SNOW;
                }
            }
        }
        return false;
    }

    private enum WeatherType {
        RAIN,
        THUNDER,
        SNOW
    }
}
