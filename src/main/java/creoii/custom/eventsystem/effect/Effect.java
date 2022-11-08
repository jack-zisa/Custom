package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.Custom;
import creoii.custom.data.Identifiable;
import creoii.custom.eventsystem.parameter.EventParameter;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class Effect implements Identifiable {
    @Nullable
    public static Effect getEffect(JsonObject object, Identifier id) {
        return Custom.EFFECT.get(id).getFromJson(object);
    }

    public static Effect register(Identifier id, Effect effect) {
        return Registry.register(Custom.EFFECT, id, effect);
    }

    @Override
    public Identifier getIdentifier() {
        return Custom.EFFECT.getId(this);
    }

    public abstract EventParameter[] getEventParameters();

    public abstract Effect getFromJson(JsonObject object);

    public abstract void run(EventParameter[] parameters);

    public abstract void runBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand);
    public abstract void runItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand);
    public abstract void runEntity(Entity entity, PlayerEntity player, Hand hand);
    public abstract void runEnchantment(Enchantment enchantment, Entity user, Entity target, int level);
    public abstract void runStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier);
    public abstract void runWorld(World world);
}
