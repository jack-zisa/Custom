package creoii.custom.eventsystem.event;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.effect.Effect;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Consumer;

public abstract class Event {
    public static final String RIGHT_CLICK = "right_click";
    public static final String PLACE_BLOCK = "place_block";
    public static final String TARGET_DAMAGED = "target_damaged";
    public static final String USER_DAMAGED = "user_damaged";
    public static final String ENTITY_LANDS = "entity_lands";

    private final String type;
    public final Condition[] conditions;
    public final Effect[] effects;

    public Event(String type, Condition[] conditions, Effect[] effects) {
        this.type = type;
        this.conditions = conditions;
        this.effects = effects;
    }

    public String getType() {
        return type;
    }

    public static Event getEvent(JsonObject object, String str) {
        return switch (str) {
            case RIGHT_CLICK -> RightClickEvent.getFromJson(object);
            case PLACE_BLOCK -> PlaceBlockEvent.getFromJson(object);
            case TARGET_DAMAGED -> TargetDamagedEvent.getFromJson(object);
            case USER_DAMAGED -> PlaceBlockEvent.getFromJson(object);
            case ENTITY_LANDS -> EntityLandsEvent.getFromJson(object);
            default -> new NoEvent();
        };
    }

    public static Condition[] getConditions(JsonObject object) {
        Condition[] conditions;
        if (JsonHelper.hasArray(object, "conditions")) {
            JsonArray array = JsonHelper.getArray(object, "conditions");
            conditions = new Condition[array.size()];
            for (int i = 0; i < conditions.length; ++i) {
                if (array.get(i).isJsonObject()) {
                    JsonObject eventObj = array.get(i).getAsJsonObject();
                    conditions[i] = Condition.getCondition(eventObj, eventObj.get("type").getAsString());
                }            }
        } else conditions = new Condition[0];
        return conditions;
    }

    public static Effect[] getEffects(JsonObject object) {
        Effect[] effects;
        if (JsonHelper.hasArray(object, "effects")) {
            JsonArray array = JsonHelper.getArray(object, "effects");
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

    public void forEachCondition(Consumer<Condition> action) {
        for (Condition condition : conditions) {
            action.accept(condition);
        }
    }

    public void forEachEffect(Consumer<Effect> action) {
        for (Effect effect : effects) {
            action.accept(effect);
        }
    }

    public static Event findEvent(Event[] events, String name) {
        for (Event event : events) {
            if (event.getType().equals(name)) return event;
        } return null;
    }

    public abstract boolean applyWorldEvent(World world, BlockPos pos);
    public abstract boolean applyBlockEvent(World world, BlockState state, BlockPos pos, PlayerEntity player, Hand hand);
    public abstract boolean applyItemEvent(World world, Item item, BlockPos pos, PlayerEntity player, Hand hand);
    public abstract boolean applyEntityEvent(Entity entity, PlayerEntity player, Hand hand);
    public abstract boolean applyEnchantmentEvent(Entity user, Entity target, int level);

    /**
     * Maybe reimplement Types and allow events to specify multiple.
     * Each type has an 'apply' method which will be called if the event is that type.
     * For example:
     *  if an event has the types BLOCK and WORLD, only applyWorldEvent and applyBlockEvent will be called.
     *  all others will be ignored.
     */
}
