package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.WorldParameter;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class WorldCondition extends Condition {
    private final Predicate<World> predicate;

    public WorldCondition(Predicate<World> predicate) {
        this.predicate = predicate;
    }

    @Override
    public Condition getFromJson(JsonObject object) {
        return null;
    }

    @Override
    public EventParameter[] getParameters() {
        return new EventParameter[]{EventParameters.WORLD};
    }

    @Override
    public boolean test(EventParameter[] parameters) {
        if (validate(parameters)) {
            WorldParameter worldParameter = (WorldParameter) parameters[0];
            return predicate.test(worldParameter.getWorld());
        }
        return false;
    }

    @Override
    public boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        return false;
    }

    @Override
    public boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        return false;
    }

    @Override
    public boolean testEntity(Entity entity, PlayerEntity player, Hand hand) {
        return false;
    }

    @Override
    public boolean testEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        return false;
    }

    @Override
    public boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        return false;
    }

    @Override
    public boolean testWorld(World world) {
        return false;
    }
}
