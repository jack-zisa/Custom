package creoii.custom.eventsystem.event;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import creoii.custom.Custom;
import creoii.custom.data.Identifiable;
import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.context.Context;
import creoii.custom.eventsystem.effect.Effect;
import creoii.custom.eventsystem.parameter.EventParameter;
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
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEvent implements Identifiable {
    private final List<Context> contexts;
    protected Condition<?>[] conditions;
    protected Effect[] effects;

    public AbstractEvent() {
        contexts = new ArrayList<>();
    }

    public static AbstractEvent register(Identifier id, AbstractEvent event) {
        return Registry.register(Custom.EVENT, id, event);
    }

    @Nullable
    public static AbstractEvent getEvent(JsonObject object, Identifier id) {
        return Custom.EVENT.get(id).getFromJson(object);
    }

    public Condition<?>[] getConditions() {
        return conditions;
    }

    public Effect[] getEffects() {
        return effects;
    }

    public abstract EventParameter[] getParameters();

    @Override
    public Identifier getIdentifier() {
        return Custom.EVENT.getId(this);
    }

    public void addContext(Context context) {
        contexts.add(context);
    }

    public List<Context> getContexts() {
        return contexts;
    }

    public abstract AbstractEvent withValues(Condition<?>[] conditions, Effect[] effects) ;

    public abstract AbstractEvent getFromJson(JsonObject object);

    public static Condition<?>[] getConditions(JsonObject object) {
        Condition<?>[] conditions;
        if (JsonHelper.hasArray(object, "conditions")) {
            JsonArray array = JsonHelper.getArray(object, "conditions");
            conditions = new Condition[array.size()];
            for (int i = 0; i < conditions.length; ++i) {
                if (array.get(i).isJsonObject()) {
                    JsonObject eventObj = array.get(i).getAsJsonObject();
                    conditions[i] = Condition.getCondition(eventObj, Identifier.tryParse(eventObj.get("name").getAsString()));
                }
            }
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
                    effects[i] = Effect.getEffect(eventObj, Identifier.tryParse(eventObj.get("name").getAsString()));
                }
            }
        } else effects = new Effect[0];
        return effects;
    }

    public static AbstractEvent findEvent(AbstractEvent[] events, AbstractEvent event) {
        for (AbstractEvent event1 : events) {
            if (event1.getIdentifier().equals(event.getIdentifier())) return event1;
        } return null;
    }

    public abstract boolean applyBlockEvent(World world, BlockState state, BlockPos pos, @Nullable LivingEntity living, @Nullable Hand hand);

    public abstract boolean applyItemEvent(World world, ItemStack stack, BlockPos pos, PlayerEntity player, @Nullable Hand hand);

    public abstract boolean applyEntityEvent(Entity entity, PlayerEntity player, Hand hand);

    public abstract void applyEnchantmentEvent(Enchantment enchantment, Entity user, Entity target, int level);

    public abstract void applyStatusEffectEvent(StatusEffect statusEffect, LivingEntity entity, int amplifier);
}
