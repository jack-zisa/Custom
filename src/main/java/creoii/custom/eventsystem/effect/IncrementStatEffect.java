package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.StatType;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class IncrementStatEffect extends Effect {
    private Identifier statId;
    private boolean affectTarget;

    public IncrementStatEffect withValues(Identifier statId, boolean affectTarget) {
        this.statId = statId;
        this.affectTarget = affectTarget;
        return this;
    }

    public IncrementStatEffect getFromJson(JsonObject object) {
        Identifier statId = Identifier.tryParse(object.get("stat_type").getAsString());
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target");
        return withValues(statId, affectTarget);
    }

    private void run(Entity entity) {
        if (entity instanceof PlayerEntity playerEntity) {
            playerEntity.incrementStat(statId);
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
        run(affectTarget ? target : user);
    }

    @Override
    public void runStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        run(entity);
    }

    @Override
    public void runWorld(World world) { }
}
