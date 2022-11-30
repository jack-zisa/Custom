package creoii.custom.loaders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import creoii.custom.objects.CustomStatusEffect;

import java.io.Reader;

public class StatusEffectsLoader extends AbstractDataLoader<CustomStatusEffect> {
    public StatusEffectsLoader() {
        super("status_effects", new GsonBuilder().setPrettyPrinting().registerTypeAdapter(CustomStatusEffect.class, new CustomStatusEffect.Serializer()).create());
    }

    @Override
    CustomStatusEffect createCustomObject(Reader reader, Gson gson) {
        return gson.fromJson(reader, CustomStatusEffect.class);
    }
}
