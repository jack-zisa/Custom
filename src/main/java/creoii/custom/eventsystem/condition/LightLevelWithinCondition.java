package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.util.math.DoubleValueHolder;
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
import net.minecraft.world.Difficulty;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class LightLevelWithinCondition extends Condition {
    private DoubleValueHolder minLight;
    private DoubleValueHolder maxLight;
    private LightType lightType;
    private boolean affectTarget;

    public LightLevelWithinCondition withValues(DoubleValueHolder minLight, DoubleValueHolder maxLight, LightType lightType, boolean affectTarget) {
        this.minLight = minLight;
        this.maxLight = maxLight;
        this.lightType = lightType;
        this.affectTarget = affectTarget;
        return this;
    }

    public LightLevelWithinCondition getFromJson(JsonObject object) {
        DoubleValueHolder minLight = DoubleValueHolder.getFromJson(object, "min_light");
        DoubleValueHolder maxLight = DoubleValueHolder.getFromJson(object, "max_light");
        LightType lightType = LightType.valueOf(JsonHelper.getString(object, "light_type").toUpperCase());
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target", false);
        return withValues(minLight, maxLight, lightType, affectTarget);
    }

    private boolean test(World world, BlockPos pos) {
        int light = world.getLightLevel(lightType, pos);
        return light < maxLight.getValue() && light > minLight.getValue();
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
