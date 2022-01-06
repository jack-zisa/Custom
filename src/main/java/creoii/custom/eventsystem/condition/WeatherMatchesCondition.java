package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class WeatherMatchesCondition extends Condition {
    private final String weather;
    private final boolean useTargetPosition;

    public WeatherMatchesCondition(String weather, boolean useTargetPosition) {
        super(Condition.WEATHER_MATCHES);
        this.weather = weather;
        this.useTargetPosition = useTargetPosition;
    }

    public static Condition getFromJson(JsonObject object) {
        String weather = JsonHelper.getString(object, "weather", "none");
        if (!weather.equals("rain") || !weather.equals("snow")) weather = "none";
        boolean useTargetPosition = JsonHelper.getBoolean(object, "use_target_position", false);
        return new WeatherMatchesCondition(weather, useTargetPosition);
    }

    @Override
    public boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        boolean hasRain = world.hasRain(pos);
        Biome biome = world.getBiome(pos);
        if (weather.equals("rain")) return hasRain && biome.getPrecipitation() == Biome.Precipitation.RAIN;
        else if (weather.equals("snow")) return hasRain && biome.getPrecipitation() == Biome.Precipitation.SNOW;
        else return !hasRain;
    }

    @Override
    public boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        boolean hasRain = world.hasRain(pos);
        Biome biome = world.getBiome(pos);
        if (weather.equals("rain")) return hasRain && biome.getPrecipitation() == Biome.Precipitation.RAIN;
        else if (weather.equals("snow")) return hasRain && biome.getPrecipitation() == Biome.Precipitation.SNOW;
        else return !hasRain;
    }

    @Override
    public boolean testEntity(Entity entity, PlayerEntity player, Hand hand) {
        boolean hasRain = entity.getWorld().hasRain(entity.getBlockPos());
        Biome biome = entity.getWorld().getBiome(entity.getBlockPos());
        if (weather.equals("rain")) return hasRain && biome.getPrecipitation() == Biome.Precipitation.RAIN;
        else if (weather.equals("snow")) return hasRain && biome.getPrecipitation() == Biome.Precipitation.SNOW;
        else return !hasRain;
    }

    @Override
    public boolean testEnchantment(Entity user, Entity target, int level) {
        boolean hasRain = useTargetPosition ? target.getWorld().hasRain(target.getBlockPos()) : user.getWorld().hasRain(user.getBlockPos());
        Biome biome = useTargetPosition ? target.getWorld().getBiome(target.getBlockPos()) : user.getWorld().getBiome(user.getBlockPos());
        if (weather.equals("rain")) return hasRain && biome.getPrecipitation() == Biome.Precipitation.RAIN;
        else if (weather.equals("snow")) return hasRain && biome.getPrecipitation() == Biome.Precipitation.SNOW;
        else return !hasRain;
    }

    @Override
    public boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        boolean hasRain = entity.getWorld().hasRain(entity.getBlockPos());
        Biome biome = entity.getWorld().getBiome(entity.getBlockPos());
        if (weather.equals("rain")) return hasRain && biome.getPrecipitation() == Biome.Precipitation.RAIN;
        else if (weather.equals("snow")) return hasRain && biome.getPrecipitation() == Biome.Precipitation.SNOW;
        else return !hasRain;
    }
}
