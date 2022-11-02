package creoii.custom.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import creoii.custom.objects.CustomBannerPattern;
import creoii.custom.objects.CustomDamageSource;

import java.io.Reader;

public class DamageSourcesLoader extends AbstractDataLoader<CustomDamageSource> {
    public DamageSourcesLoader() {
        super("damage_sources", new GsonBuilder().setPrettyPrinting().registerTypeAdapter(CustomDamageSource.class, new CustomBannerPattern.Serializer()).create());
    }

    @Override
    CustomDamageSource createCustomObject(Reader reader, Gson gson) {
        return gson.fromJson(reader, CustomDamageSource.class);
    }
}
