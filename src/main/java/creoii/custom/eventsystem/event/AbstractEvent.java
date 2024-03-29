package creoii.custom.eventsystem.event;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import creoii.custom.Custom;
import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.effect.AbstractEffect;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.loaders.Identifiable;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEvent implements Identifiable {
    protected Condition[] conditions;
    protected AbstractEffect[] effects;

    public static AbstractEvent register(Identifier id, AbstractEvent event) {
        return Registry.register(Custom.EVENT, id, event);
    }

    public static AbstractEvent getEvent(Identifier id) {
        return Custom.EVENT.get(id);
    }

    @Override
    public Identifier getIdentifier() {
        return Custom.EVENT.getId(this);
    }

    public abstract boolean apply(List<EventParameter> parameters);

    public AbstractEvent getFromJson(JsonObject object) {
        conditions = BasicEvent.getConditions(object);
        effects = BasicEvent.getEffects(object);
        return this;
    }

    public abstract AbstractEvent getType();

    public static Condition[] getConditions(JsonObject object) {
        Condition[] conditions;
        if (JsonHelper.hasArray(object, "conditions")) {
            JsonArray array = JsonHelper.getArray(object, "conditions");
            conditions = new Condition[array.size()];
            for (int i = 0; i < conditions.length; ++i) {
                if (array.get(i).isJsonObject()) {
                    JsonObject eventObj = array.get(i).getAsJsonObject();
                    conditions[i] = Condition.getCondition(eventObj, eventObj.get("type").getAsString());
                }
            }
        } else conditions = new Condition[0];
        return conditions;
    }

    public static AbstractEffect[] getEffects(JsonObject object) {
        AbstractEffect[] effects;
        if (JsonHelper.hasArray(object, "effects")) {
            JsonArray array = JsonHelper.getArray(object, "effects");
            effects = new AbstractEffect[array.size()];
            for (int i = 0; i < effects.length; ++i) {
                if (array.get(i).isJsonObject()) {
                    JsonObject eventObj = array.get(i).getAsJsonObject();
                    effects[i] = AbstractEffect.getEffect(eventObj, Identifier.tryParse(eventObj.get("type").getAsString()));
                }
            }
        } else effects = new AbstractEffect[0];
        return effects;
    }

    public static AbstractEvent find(AbstractEvent[] events, AbstractEvent type) {
        for (AbstractEvent event : events) {
            if (event.getType() == type.getType()) return event;
        } return null;
    }

    public static List<AbstractEvent> findAll(List<AbstractEvent> events, AbstractEvent type) {
        List<AbstractEvent> ret = new ArrayList<>();
        for (AbstractEvent event : events) {
            if (event.getType() == type.getType()) {
                ret.add(event);
            }
        }
        return ret.size() > 0 ? ret : null;
    }

    public static boolean hasEvent(List<AbstractEvent> events, AbstractEvent type) {
        for (AbstractEvent event : events) {
            if (event.getType() == type.getType()) {
                return true;
            }
        }
        return false;
    }
}
