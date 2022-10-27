package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.util.json.CustomJsonHelper;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class EmitGameEventEffect extends Effect {
    private GameEvent gameEvent;
    private BlockPos offset;
    private boolean affectTarget;

    public EmitGameEventEffect withValues(GameEvent gameEvent, BlockPos offset, boolean affectTarget) {
        this.gameEvent = gameEvent;
        this.offset = offset;
        this.affectTarget = affectTarget;
        return this;
    }

    public EmitGameEventEffect getFromJson(JsonObject object) {
        GameEvent event = Registry.GAME_EVENT.get(Identifier.tryParse(JsonHelper.getString(object, "event")));
        BlockPos offset = CustomJsonHelper.getBlockPos(object, "offset");
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target", false);
        return withValues(event, offset, affectTarget);
    }

    @Override
    public void runBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        world.emitGameEvent(null, gameEvent, pos.add(offset));
    }

    @Override
    public void runItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        world.emitGameEvent(null, gameEvent, pos.add(offset));
    }

    @Override
    public void runEntity(Entity entity, PlayerEntity player, Hand hand) {
        entity.getWorld().emitGameEvent(null, gameEvent, entity.getBlockPos().add(offset));
    }

    @Override
    public void runEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        BlockPos pos = affectTarget ? target.getBlockPos() : user.getBlockPos();
        user.getWorld().emitGameEvent(null, gameEvent, pos.add(offset));
    }

    @Override
    public void runStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        entity.getWorld().emitGameEvent(null, gameEvent, entity.getBlockPos().add(offset));
    }

    @Override
    public void runWorld(World world) { }
}
