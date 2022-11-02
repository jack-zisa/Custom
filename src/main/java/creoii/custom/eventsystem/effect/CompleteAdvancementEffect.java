package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Iterator;

public class CompleteAdvancementEffect extends Effect {
    private Identifier advancementId;
    private boolean affectTarget;

    public CompleteAdvancementEffect withValues(Identifier advancementId, boolean affectTarget) {
        this.advancementId = advancementId;
        this.affectTarget = affectTarget;
        return this;
    }

    public Effect getFromJson(JsonObject object) {
        Identifier identifier = Identifier.tryParse(object.get("advancement_id").getAsString());
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target", false);
        return withValues(identifier, affectTarget);
    }

    private void run(World world, Entity entity) {
        if (!world.isClient) {
            if (entity instanceof ServerPlayerEntity serverPlayerEntity) {
                Advancement advancement = ((ServerWorld) world).getServer().getAdvancementLoader().get(advancementId);
                for (String string : serverPlayerEntity.getAdvancementTracker().getProgress(advancement).getUnobtainedCriteria()) {
                    serverPlayerEntity.getAdvancementTracker().grantCriterion(advancement, string);
                }
            }
        }
    }

    @Override
    public void runBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        run(world, living);
    }

    @Override
    public void runItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        run(world, player);
    }

    @Override
    public void runEntity(Entity entity, PlayerEntity player, Hand hand) {
        run(entity.getWorld(), entity);
    }

    @Override
    public void runEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        run(user.getWorld(), affectTarget ? target : user);
    }

    @Override
    public void runStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        run(entity.getWorld(), entity);
    }

    @Override
    public void runWorld(World world) { }
}
