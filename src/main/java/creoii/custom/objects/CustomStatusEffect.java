package creoii.custom.objects;

import com.google.gson.*;
import creoii.custom.eventsystem.event.AbstractEvent;
import creoii.custom.eventsystem.event.Events;
import creoii.custom.eventsystem.parameter.*;
import creoii.custom.loaders.Identifiable;
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
import java.util.ArrayList;
import java.util.List;

public class CustomStatusEffect extends StatusEffect implements Identifiable {
    private final Identifier identifier;
    private final boolean instant;
    private final List<AbstractEvent> events;

    public CustomStatusEffect(Identifier identifier, boolean instant, StatusEffectCategory category, int color,
                              CustomJsonObjects.AttributeModifier[] attributeModifiers, List<AbstractEvent> events) {
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
        List<AbstractEvent> events1 = AbstractEvent.findAll(events, Events.STATUS_EFFECT_UPDATE);
        if (events1 != null) {
            for (AbstractEvent event : events1) {
                event.apply(List.of(
                        new WorldParameter().withValue(entity.getWorld()),
                        new BlockPosParameter().withValue(entity.getBlockPos()),
                        new EntityParameter().withValue(entity),
                        new EntityTypeParameter().withValue(entity.getType()),
                        new IntegerParameter().withValue(amplifier).name("amplifier")
                ));
            }
        }
        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        List<AbstractEvent> events1 = AbstractEvent.findAll(events, Events.STATUS_EFFECT_APPLY);
        if (events1 != null) {
            for (AbstractEvent event : events1) {
                event.apply(List.of(
                        new WorldParameter().withValue(target.getWorld()),
                        new BlockPosParameter().withValue(target.getBlockPos()),
                        new EntityParameter().withValue(target),
                        new EntityTypeParameter().withValue(target.getType()),
                        new IntegerParameter().withValue(amplifier).name("amplifier"),
                        new DoubleParameter().withValue(proximity).name("proximity")
                ));
            }
        }
        super.applyInstantEffect(source, attacker, target, amplifier, proximity);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        List<AbstractEvent> events1 = AbstractEvent.findAll(events, Events.STATUS_EFFECT_APPLY);
        if (events1 != null) {
            for (AbstractEvent event : events1) {
                event.apply(List.of(
                        new WorldParameter().withValue(entity.getWorld()),
                        new BlockPosParameter().withValue(entity.getBlockPos()),
                        new EntityParameter().withValue(entity),
                        new EntityTypeParameter().withValue(entity.getType()),
                        new IntegerParameter().withValue(amplifier).name("amplifier")
                ));
            }
        }
        super.onApplied(entity, attributes, amplifier);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        List<AbstractEvent> events1 = AbstractEvent.findAll(events, Events.STATUS_EFFECT_REMOVE);
        if (events1 != null) {
            for (AbstractEvent event : events1) {
                event.apply(List.of(
                        new WorldParameter().withValue(entity.getWorld()),
                        new BlockPosParameter().withValue(entity.getBlockPos()),
                        new EntityParameter().withValue(entity),
                        new EntityTypeParameter().withValue(entity.getType()),
                        new IntegerParameter().withValue(amplifier).name("amplifier")
                ));
            }
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
            List<AbstractEvent> events = new ArrayList<>();
            if (JsonHelper.hasArray(object, "events")) {
                JsonArray array = JsonHelper.getArray(object, "events");
                if (array.size() > 0) {
                    for (int i = 0; i < array.size(); ++i) {
                        if (array.get(i).isJsonObject()) {
                            JsonObject eventObj = array.get(i).getAsJsonObject();
                            events.add(AbstractEvent.getEvent(Identifier.tryParse(eventObj.get("name").getAsString())));
                        }
                    }
                }
            }
            return new CustomStatusEffect(identifier, instant, category, color, attributeModifiers, events);
        }

        @Override
        public JsonElement serialize(CustomStatusEffect src, Type typeOfSrc, JsonSerializationContext context) {
            return null;
        }
    }
}
