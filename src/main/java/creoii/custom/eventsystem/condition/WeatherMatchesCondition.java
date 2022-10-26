package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class WeatherMatchesCondition extends Condition {
    private final String weather;
    private final boolean affectTarget;

    public WeatherMatchesCondition(String weather, boolean affectTarget) {
        super(Condition.WEATHER_MATCHES);
        this.weather = weather;
        this.affectTarget = affectTarget;
    }

    public static Condition getFromJson(JsonObject object) {
        String weather = JsonHelper.getString(object, "weather", "none");
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target", false);
        return new WeatherMatchesCondition(weather, affectTarget);
    }

    private boolean test(World world, BlockPos pos) {
        boolean hasRain = world.hasRain(pos);
        RegistryEntry<Biome> biome = world.getBiome(pos);
        if (biome.hasKeyAndValue()) {
            if (weather.equals("rain")) return hasRain && biome.value().getPrecipitation() == Biome.Precipitation.RAIN;
            else if (weather.equals("snow")) return hasRain && biome.value().getPrecipitation() == Biome.Precipitation.SNOW;
            else return !hasRain;
        } else return false;
    }

    @Override
    public boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        return test(world, pos);
    }

    @Override
    public boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        return test(world, pos);
    }

    @Override
    public boolean testEntity(Entity entity, PlayerEntity player, Hand hand) {
        return test(entity.getWorld(), entity.getBlockPos());
    }

    @Override
    public boolean testEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        return test(user.getWorld(), affectTarget ? target.getBlockPos() : user.getBlockPos());
    }

    @Override
    public boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        return test(entity.getWorld(), entity.getBlockPos());
    }

    @Override
    public boolean testWorld(World world) {
        return false;
    }
}
