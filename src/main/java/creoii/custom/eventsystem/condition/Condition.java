package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.Custom;
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

public abstract class Condition {
    @Nullable
    public static Condition getCondition(JsonObject object, Identifier id) {
        return Custom.CONDITION.get(id).getFromJson(object);
    }

    public static Condition register(Identifier id, Condition condition) {
        return Registry.register(Custom.CONDITION, id, condition);
    }

    public Identifier getId() {
        return Custom.CONDITION.getId(this);
    }

    public abstract Condition getFromJson(JsonObject object);

    public abstract boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand);
    public abstract boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand);
    public abstract boolean testEntity(Entity entity, PlayerEntity player, Hand hand);
    public abstract boolean testEnchantment(Enchantment enchantment, Entity user, Entity target, int level);
    public abstract boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier);
    public abstract boolean testWorld(World world);
}
