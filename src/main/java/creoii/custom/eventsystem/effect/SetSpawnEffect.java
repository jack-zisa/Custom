package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.util.json.CustomJsonHelper;
import creoii.custom.util.math.ValueHolder;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.SpawnPointCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SetSpawnEffect extends Effect {
    private final BlockPos offset;
    private final float angle;
    private final boolean sendMessage;
    private final boolean affectTarget;

    public SetSpawnEffect(BlockPos offset, float angle, boolean sendMessage, boolean affectTarget) {
        super(Effect.SET_SPAWN);
        this.offset = offset;
        this.angle = angle;
        this.sendMessage = sendMessage;
        this.affectTarget = affectTarget;
    }

    public static Effect getFromJson(JsonObject object) {
        BlockPos offset = CustomJsonHelper.getBlockPos(object, "offset");
        float angle = JsonHelper.getFloat(object, "angle", 0f);
        boolean sendMessage = JsonHelper.getBoolean(object, "send_message", false);
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target", false);
        return new SetSpawnEffect(offset, angle, sendMessage, affectTarget);
    }

    private void run(World world, Entity entity, BlockPos pos) {
        if (!world.isClient) {
            if (entity instanceof ServerPlayerEntity playerEntity) {
                playerEntity.setSpawnPoint(world.getRegistryKey(), pos.add(offset), angle, false, sendMessage);
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
