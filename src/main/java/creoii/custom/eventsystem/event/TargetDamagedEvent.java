package creoii.custom.eventsystem.event;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.effect.Effect;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.concurrent.atomic.AtomicBoolean;

public class TargetDamagedEvent extends Event {
    public TargetDamagedEvent(Condition[] conditions, Effect[] effects) {
        super(Event.TARGET_DAMAGED, conditions, effects);
    }

    public static Event getFromJson(JsonObject object) {
        Condition[] conditions = Event.getConditions(object);
        Effect[] effects = Event.getEffects(object);
        return new TargetDamagedEvent(conditions, effects);
    }

    @Override
    public boolean applyWorldEvent(World world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean applyBlockEvent(World world, BlockState state, BlockPos pos, PlayerEntity player, Hand hand) {
        AtomicBoolean pass = new AtomicBoolean(true);
        forEachCondition(condition -> {
            if (!condition.testBlock(world, state, pos, player, hand)) pass.set(false);
        });

        if (pass.get()) {
            forEachEffect(effect -> effect.runBlock(world, state, pos, player, hand));
        }

        return pass.get();
    }

    @Override
    public boolean applyItemEvent(World world, Item item, BlockPos pos, PlayerEntity player, Hand hand) {
        return false;
    }

    @Override
    public boolean applyEntityEvent(Entity entity, PlayerEntity player, Hand hand) {
        return false;
    }

    @Override
    public boolean applyEnchantmentEvent(Entity user, Entity target, int level) {
        return false;
    }
}
