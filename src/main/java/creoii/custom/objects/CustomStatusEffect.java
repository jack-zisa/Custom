package creoii.custom.custom;

import com.google.gson.*;
import creoii.custom.data.Identifiable;
import creoii.custom.eventsystem.event.Event;
import creoii.custom.eventsystem.event.Events;
import creoii.custom.util.StringToObject;
import creoii.custom.util.json.CustomJsonObjects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.Arrays;

public class CustomStatusEffect extends StatusEffect implements Identifiable {
    private final Identifier identifier;
    private final boolean instant;
    private final Event[] events;

    public CustomStatusEffect(Identifier identifier, boolean instant, StatusEffectCategory category, int color,
                              CustomJsonObjects.AttributeModifier[] attributeModifiers, Event[] events) {
        super(category, color);
        this.identifier = identifier;
        this.instant = instant;
        for (CustomJsonObjects.AttributeModifier modifier : attributeModifiers) {
            addAttributeModifier(modifier.attribute(), modifier.modifier().getId().toString(), modifier.amount(), modifier.modifier().getOperation());
        }
        this.events = events;

        Registry.register(Registry.STATUS_EFFECT, identifier, this);
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public boolean isInstant() {
        return instant;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        Event event = Event.findEvent(events, Events.STATUS_EFFECT_UPDATE);
        if (event != null) {
            event.applyStatusEffectEvent(this, entity, amplifier);
        }
        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        Event event = Event.findEvent(events, Events.STATUS_EFFECT_APPLY);
        if (event != null) {
            event.applyStatusEffectEvent(this, target, amplifier);
        }
        super.applyInstantEffect(source, attacker, target, amplifier, proximity);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        Event event = Event.findEvent(events, Events.STATUS_EFFECT_APPLY);
        if (event != null) {
            event.applyStatusEffectEvent(this, entity, amplifier);
        }
        super.onApplied(entity, attributes, amplifier);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        Event event = Event.findEvent(events, Events.STATUS_EFFECT_REMOVE);
        if (event != null) {
            event.applyStatusEffectEvent(this, entity, amplifier);
        }
        super.onRemoved(entity, attributes, amplifier);
    }

    public static class Serializer implements JsonSerializer<CustomStatusEffect>, JsonDeserializer<CustomStatusEffect> {
        @Override
        public CustomStatusEffect deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = JsonHelper.asObject(json, "status effect");
            Identifier identifier = Identifier.tryParse(JsonHelper.getString(object, "identifier"));
            boolean instant = JsonHelper.getBoolean(object, "instant", false);
            StatusEffectCategory category = StringToObject.statusEffectCategory(JsonHelper.getString(object, "category", "neutral"));
            int color = JsonHelper.getInt(object, "color", 0);
            CustomJsonObjects.AttributeModifier[] attributeModifiers;
            if (JsonHelper.hasArray(object, "attribute_modifiers")) {
                JsonArray array = JsonHelper.getArray(object, "attribute_modifiers");
                attributeModifiers = new CustomJsonObjects.AttributeModifier[array.size()];
                if (attributeModifiers.length > 0) {
                    for (int i = 0; i < attributeModifiers.length; ++i) {
                        if (array.get(i).isJsonObject()) {
                            attributeModifiers[i] = CustomJsonObjects.AttributeModifier.get(array.get(i).getAsJsonObject());
                        }
                    }
                }
            } else attributeModifiers = new CustomJsonObjects.AttributeModifier[0];
            Event[] events;
            if (JsonHelper.hasArray(object, "events")) {
                JsonArray array = JsonHelper.getArray(object, "events");
                events = new Event[array.size()];
                if (events.length > 0) {
                    for (int i = 0; i < events.length; ++i) {
                        if (array.get(i).isJsonObject()) {
                            JsonObject eventObj = array.get(i).getAsJsonObject();
                            events[i] = Event.getEvent(eventObj, Identifier.tryParse(eventObj.get("name").getAsString()));
                        }
                    }
                }
            } else events = new Event[]{};
            return new CustomStatusEffect(identifier, instant, category, color, attributeModifiers, events);
        }

        @Override
        public JsonElement serialize(CustomStatusEffect src, Type typeOfSrc, JsonSerializationContext context) {
            return null;
        }
    }
}
