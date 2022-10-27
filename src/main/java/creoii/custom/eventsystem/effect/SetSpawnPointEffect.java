package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.util.json.CustomJsonHelper;
import creoii.custom.util.math.DoubleValueHolder;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SetSpawnPointEffect extends Effect {
    private BlockPos offset;
    private DoubleValueHolder angle;
    private boolean sendMessage;
    private boolean affectTarget;

    public SetSpawnPointEffect withValues(BlockPos offset, DoubleValueHolder angle, boolean sendMessage, boolean affectTarget) {
        this.offset = offset;
        this.angle = angle;
        this.sendMessage = sendMessage;
        this.affectTarget = affectTarget;
        return this;
    }

    public SetSpawnPointEffect getFromJson(JsonObject object) {
        BlockPos offset = CustomJsonHelper.getBlockPos(object, "offset");
        DoubleValueHolder angle = DoubleValueHolder.getFromJson(object, "angle");
        boolean sendMessage = JsonHelper.getBoolean(object, "send_message", false);
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target", false);
        return withValues(offset, angle, sendMessage, affectTarget);
    }

    private void run(World world, Entity entity, BlockPos pos) {
        if (!world.isClient) {
            if (entity instanceof ServerPlayerEntity playerEntity) {
                playerEntity.setSpawnPoint(world.getRegistryKey(), pos.add(offset), (float) angle.getValue(), false, sendMessage);
            }
        }
    }

    @Override
    public void runBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        run(world, living, pos);
    }

    @Override
    public void runItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        run(world, player, pos);
    }

    @Override
    public void runEntity(Entity entity, PlayerEntity player, Hand hand) {
        run(entity.getWorld(), entity, entity.getBlockPos());
    }

    @Override
    public void runEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        run(user.getWorld(), affectTarget ? target : user, affectTarget ? target.getBlockPos() : user.getBlockPos());
    }

    @Override
    public void runStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        run(entity.getWorld(), entity, entity.getBlockPos());
    }

    @Override
    public void runWorld(World world) { }
}
