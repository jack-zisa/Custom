package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.function.CommandFunctionManager;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RunFunctionEffect extends Effect {
    private Identifier functionId;
    private boolean affectTarget;

    public RunFunctionEffect withValues(Identifier functionId, boolean affectTarget) {
        this.functionId = functionId;
        this.affectTarget = affectTarget;
        return this;
    }

    public RunFunctionEffect getFromJson(JsonObject object) {
        Identifier id = Identifier.tryParse(object.get("function").getAsString());
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target");
        return withValues(id, affectTarget);
    }

    private void run(World world, Entity entity) {
        if (!world.isClient && entity instanceof PlayerEntity playerEntity) {
            ServerCommandSource source = playerEntity.getCommandSource();
            CommandFunctionManager manager = source.getServer().getCommandFunctionManager();
            manager.getFunction(functionId).ifPresent(commandFunction -> manager.execute(commandFunction, source));
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
