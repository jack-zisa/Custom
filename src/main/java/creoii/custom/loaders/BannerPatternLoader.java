package creoii.custom.loaders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import creoii.custom.objects.CustomBannerPattern;

import java.io.Reader;

public class BannerPatternLoader extends AbstractDataLoader<CustomBannerPattern> {
    public BannerPatternLoader() {
        super("banner_patterns", new GsonBuilder().setPrettyPrinting().registerTypeAdapter(CustomBannerPattern.class, new CustomBannerPattern.Serializer()).create());
    }

    @Override
    CustomBannerPattern createCustomObject(Reader reader, Gson gson) {
        return gson.fromJson(reader, CustomBannerPattern.class);
    }
}
