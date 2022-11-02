package creoii.custom.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import creoii.custom.custom.CustomBannerPattern;
import creoii.custom.custom.CustomDamageSource;

import java.io.Reader;

public class DamageSourcesManager extends AbstractDataManager<CustomDamageSource> {
    public DamageSourcesManager() {
        super("damage_sources", new GsonBuilder().setPrettyPrinting().registerTypeAdapter(CustomDamageSource.class, new CustomBannerPattern.Serializer()).create());
    }

    @Override
    CustomDamageSource createCustomObject(Reader reader, Gson gson) {
        return gson.fromJson(reader, CustomDamageSource.class);
    }
}
