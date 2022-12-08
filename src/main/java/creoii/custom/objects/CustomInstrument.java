package creoii.custom.objects;

import com.google.gson.*;
import creoii.custom.loaders.Identifiable;
import net.minecraft.item.Instrument;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.registry.Registry;

import java.lang.reflect.Type;

public class CustomInstrument implements Identifiable {
    private final Identifier identifier;
    private final SoundEvent soundEvent;
    private final int useDuration;
    private final float range;

    private final Instrument instrument;

    public CustomInstrument(Identifier identifier, SoundEvent soundEvent, int useDuration, float range) {
        this.identifier = identifier;
        this.soundEvent = soundEvent;
        this.useDuration = useDuration;
        this.range = range;
        instrument = new Instrument(RegistryEntry.of(soundEvent), useDuration, range);

        Registry.register(Registries.INSTRUMENT, this.getIdentifier(), asInstrument());
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    public Instrument asInstrument() {
        return instrument;
    }

    public static class Serializer implements JsonDeserializer<CustomInstrument>, JsonSerializer<CustomInstrument> {
        @Override
        public CustomInstrument deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = JsonHelper.asObject(json, "painting");
            SoundEvent sound = Registries.SOUND_EVENT.get(Identifier.tryParse(JsonHelper.getString(object, "sound")));
            int useDuration = JsonHelper.getInt(object, "use_duration", 0);
            float range = JsonHelper.getFloat(object, "range", 0f);
            return new CustomInstrument(Identifier.tryParse(JsonHelper.getString(object, "identifier")), sound, useDuration, range);
        }

        @Override
        public JsonElement serialize(CustomInstrument src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.addProperty("identifier", src.getIdentifier().toString());
            object.addProperty("sound", src.soundEvent.getId().toString());
            object.addProperty("use_duration", src.useDuration);
            object.addProperty("range", src.range);
            return object;
        }
    }
}
