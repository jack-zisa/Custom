package creoii.custom.eventsystem.effect;

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
import net.minecraft.world.World;

public class SetWeatherEffect extends Effect {
    private boolean rain;
    private boolean thunder;

    public SetWeatherEffect withValues(boolean rain, boolean thunder) {
        this.rain = rain;
        this.thunder = thunder;
        return this;
    }

    public SetWeatherEffect getFromJson(JsonObject object) {
        boolean rain = JsonHelper.getBoolean(object, "rain", false);
        boolean thunder = JsonHelper.getBoolean(object, "thunder", false);
        return withValues(rain, thunder);
    }

    public void run(World world) {
        if (thunder) world.setThunderGradient(1f);
        world.getLevelProperties().setRaining(rain);
    }

    @Override
    public void runBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        run(world);
    }

    @Override
    public void runItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        run(world);
    }

    @Override
    public void runEntity(Entity entity, PlayerEntity player, Hand hand) {
        run(entity.getWorld());
    }

    @Override
    public void runEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        run(user.getWorld());
    }

    @Override
    public void runStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        run(entity.getWorld());
    }

    @Override
    public void runWorld(World world) {
        run(world);
    }
}
