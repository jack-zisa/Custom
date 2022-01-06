package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
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
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class BiomeMatchesCondition extends Condition {
    private final Biome biome;
    private final Biome.Category category;
    private final Biome.Precipitation precipitation;
    private final boolean useTargetPostition;

    public BiomeMatchesCondition(Biome biome, Biome.Category category, Biome.Precipitation precipitation, boolean useTargetPostition) {
        super(Condition.BIOME_MATCHES);
        this.biome = biome;
        this.category = category;
        this.precipitation = precipitation;
        this.useTargetPostition = useTargetPostition;
    }

    public static Condition getFromJson(JsonObject object) {
        Biome biome = BuiltinRegistries.BIOME.get(Identifier.tryParse(JsonHelper.getString(object, "biome")));
        Biome.Category category = null;
        if (JsonHelper.hasString(object, "category")) category = Biome.Category.byName(JsonHelper.getString(object, "category", "none"));
        Biome.Precipitation precipitation = null;
        if (JsonHelper.hasString(object, "precipitation")) precipitation = Biome.Precipitation.byName(JsonHelper.getString(object, "precipitation", "rain"));
        boolean useTargetPosition = JsonHelper.getBoolean(object, "use_target_position", false);
        return new BiomeMatchesCondition(biome, category, precipitation, useTargetPosition);
    }

    @Override
    public boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        Biome biome = world.getBiome(pos);
        if (biome == this.biome) {
            boolean flag = true;
            if (category != null) {
                flag = category == biome.getCategory();
            }
            if (precipitation != null) {
                flag = precipitation == biome.getPrecipitation();
            }
            return flag;
        }
        return false;
    }

    @Override
    public boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        Biome biome = world.getBiome(pos);
        if (biome == this.biome) {
            boolean flag = true;
            if (category != null) {
                flag = category == biome.getCategory();
            }
            if (precipitation != null) {
                flag = precipitation == biome.getPrecipitation();
            }
            return flag;
        }
        return false;
    }

    @Override
    public boolean testEntity(Entity entity, PlayerEntity player, Hand hand) {
        Biome biome = entity.getWorld().getBiome(entity.getBlockPos());
        if (biome == this.biome) {
            boolean flag = true;
            if (category != null) {
                flag = category == biome.getCategory();
            }
            if (precipitation != null) {
                flag = precipitation == biome.getPrecipitation();
            }
            return flag;
        }
        return false;
    }

    @Override
    public boolean testEnchantment(Entity user, Entity target, int level) {
        Biome biome = useTargetPostition ? target.world.getBiome(target.getBlockPos()) : user.world.getBiome(user.getBlockPos());
        if (biome == this.biome) {
            boolean flag = true;
            if (category != null) {
                flag = category == biome.getCategory();
            }
            if (precipitation != null) {
                flag = precipitation == biome.getPrecipitation();
            }
            return flag;
        }
        return false;
    }

    @Override
    public boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        Biome biome = entity.getWorld().getBiome(entity.getBlockPos());
        if (biome == this.biome) {
            boolean flag = true;
            if (category != null) {
                flag = category == biome.getCategory();
            }
            if (precipitation != null) {
                flag = precipitation == biome.getPrecipitation();
            }
            return flag;
        }
        return false;
    }
}
