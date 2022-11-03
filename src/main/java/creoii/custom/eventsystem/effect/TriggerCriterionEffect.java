package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.BrewingStandScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class TriggerCriterionEffect extends Effect {
    private AbstractCriterion<?> criterion;

    public TriggerCriterionEffect withValues(AbstractCriterion<?> criterion) {
        this.criterion = criterion;
        return this;
    }

    public Effect getFromJson(JsonObject object) {
        AbstractCriterion<?> criterion = (AbstractCriterion<?>) Criteria.getById(Identifier.tryParse(JsonHelper.getString(object, "criterion")));
        return withValues(criterion);
    }

    private void run(Entity entity) {
        if (entity instanceof ServerPlayerEntity serverPlayerEntity) {
            criterion.trigger(serverPlayerEntity, conditions -> true);
        }
    }

    @Override
    public void runBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        run(living);
    }

    @Override
    public void runItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        run(player);
    }

    @Override
    public void runEntity(Entity entity, PlayerEntity player, Hand hand) {
        run(entity);
    }

    @Override
    public void runEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        run(user);
    }

    @Override
    public void runStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        run(entity);
    }

    @Override
    public void runWorld(World world) { }
}
