package creoii.custom.eventsystem.event;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.effect.Effect;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class NeighborUpdateEvent extends Event {
    private BlockState neighborState;
    private BlockPos neighborPos;

    private Condition[] neighborConditions;
    private Effect[] neighborEffects;

    public NeighborUpdateEvent(Condition[] conditions, Effect[] effects, Condition[] neighborConditions, Effect[] neighborEffects) {
        super(Event.NEIGHBOR_UPDATE, conditions, effects);
        this.neighborConditions = neighborConditions;
        this.neighborEffects = neighborEffects;
    }

    public void setNeighborState(BlockState neighborState) {
        this.neighborState = neighborState;
    }

    public void setNeighborPos(BlockPos neighborPos) {
        this.neighborPos = neighborPos;
    }

    public BlockState getNeighborState() {
        return neighborState;
    }

    public BlockPos getNeighborPos() {
        return neighborPos;
    }

    public static Event getFromJson(JsonObject object) {
        Condition[] conditions = Event.getConditions(object);
        Effect[] effects = Event.getEffects(object);
        Condition[] neighborConditions = getNeighborConditions(object);
        Effect[] neighborEffects = getNeighborEffects(object);
        return new NeighborUpdateEvent(conditions, effects, neighborConditions, neighborEffects);
    }

    public static Condition[] getNeighborConditions(JsonObject object) {
        Condition[] conditions;
        if (JsonHelper.hasArray(object, "neighbor_conditions")) {
            JsonArray array = JsonHelper.getArray(object, "neighbor_conditions");
            conditions = new Condition[array.size()];
            for (int i = 0; i < conditions.length; ++i) {
                if (array.get(i).isJsonObject()) {
                    JsonObject eventObj = array.get(i).getAsJsonObject();
                    conditions[i] = Condition.getCondition(eventObj, eventObj.get("type").getAsString());
                }            }
        } else conditions = new Condition[0];
        return conditions;
    }

    public static Effect[] getNeighborEffects(JsonObject object) {
        Effect[] effects;
        if (JsonHelper.hasArray(object, "neighbor_effects")) {
            JsonArray array = JsonHelper.getArray(object, "neighbor_effects");
            effects = new Effect[array.size()];
            for (int i = 0; i < effects.length; ++i) {
                if (array.get(i).isJsonObject()) {
                    JsonObject eventObj = array.get(i).getAsJsonObject();
                    effects[i] = Effect.getEffect(eventObj, eventObj.get("type").getAsString());
                }
            }
        } else effects = new Effect[0];
        return effects;
    }

    public void forEachNeighborCondition(Consumer<Condition> action) {
        for (Condition condition : neighborConditions) {
            action.accept(condition);
        }
    }

    public void forEachNeighborEffect(Consumer<Effect> action) {
        for (Effect effect : neighborEffects) {
            action.accept(effect);
        }
    }

    @Override
    public boolean applyBlockEvent(World world, BlockState state, BlockPos pos, @Nullable LivingEntity living, @Nullable Hand hand) {
        for (Condition condition : conditions) {
            if (!condition.testBlock(world, state, pos, living, hand)) return false;
        }
        for (Condition condition : neighborConditions) {
            if (!condition.testBlock(world, state, pos, living, hand)) return false;
        }

        for (Effect effect : effects) {
            effect.runBlock(world, state, pos, living, hand);
        }
        for (Effect effect : neighborEffects) {
            effect.runBlock(world, state, pos, living, hand);
        }
        return true;
    }
}
