package creoii.custom.eventsystem.effect;

import com.google.gson.JsonArray;
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
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class WeightedListEffect extends Effect {
    private final DataPool<Effect> effects;

    public WeightedListEffect(DataPool<Effect> effects) {
        super(Effect.WEIGHTED_LIST);
        this.effects = effects;
    }

    public static Effect getFromJson(JsonObject object) {
        JsonArray effects = JsonHelper.getArray(object, "effects");
        DataPool.Builder<Effect> list = DataPool.builder();
        effects.forEach(element -> {
            JsonObject object1 = element.getAsJsonObject();
            JsonObject effect = object1.getAsJsonObject("effect");
            list.add(Effect.getEffect(effect, effect.get("name").getAsString()), object1.get("weight").getAsInt());
        });
        return new WeightedListEffect(list.build());
    }

    public Effect getRandom(Random random) {
        return effects.getDataOrEmpty(random).orElseThrow(IllegalStateException::new);
    }

    @Override
    public void runBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        getRandom(world.getRandom()).runBlock(world, state, pos, living, hand);
    }

    @Override
    public void runItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        getRandom(world.getRandom()).runItem(world, stack, pos, player, hand);
    }

    @Override
    public void runEntity(Entity entity, PlayerEntity player, Hand hand) {
        getRandom(entity.getWorld().getRandom()).runEntity(entity, player, hand);
    }

    @Override
    public void runEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        getRandom(user.getWorld().getRandom()).runEnchantment(enchantment, user, target, level);
    }

    @Override
    public void runStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        getRandom(entity.getRandom()).runStatusEffect(statusEffect, entity, amplifier);
    }

    @Override
    public void runWorld(World world) {
        getRandom(world.getRandom()).runWorld(world);
    }
}
