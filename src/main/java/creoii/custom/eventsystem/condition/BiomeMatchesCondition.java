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
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class BiomeMatchesCondition extends Condition {
    private final Biome biome;
    private final Biome.Precipitation precipitation;
    private final boolean affectTarget;

    public BiomeMatchesCondition(Biome biome, Biome.Precipitation precipitation, boolean affectTarget) {
        super(Condition.BIOME_MATCHES);
        this.biome = biome;
        this.precipitation = precipitation;
        this.affectTarget = affectTarget;
    }

    public static Condition getFromJson(JsonObject object) {
        Biome biome = BuiltinRegistries.BIOME.get(Identifier.tryParse(JsonHelper.getString(object, "biome")));
        Biome.Precipitation precipitation = null;
        if (JsonHelper.hasString(object, "precipitation")) precipitation = Biome.Precipitation.valueOf(JsonHelper.getString(object, "precipitation", "rain"));
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target", false);
        return new BiomeMatchesCondition(biome, precipitation, affectTarget);
    }

    private boolean test(World world, BlockPos pos) {
        RegistryEntry<Biome> biome = world.getBiome(pos);
        if (biome.hasKeyAndValue()) {
            if (biome.value() == this.biome) {
                boolean flag = true;
                if (precipitation != null) {
                    flag = precipitation == biome.value().getPrecipitation();
                }
                return flag;
            }
        }
        return false;
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
