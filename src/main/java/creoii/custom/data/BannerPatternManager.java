package creoii.custom.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import creoii.custom.custom.CustomBannerPattern;
import creoii.custom.custom.block.CustomBlock;

import java.io.Reader;

public class BannerPatternManager extends AbstractDataManager<CustomBannerPattern> {
    public BannerPatternManager() {
        super("banner_patterns", new GsonBuilder().setPrettyPrinting().registerTypeAdapter(CustomBannerPattern.class, new CustomBannerPattern.Serializer()).create());
    }

    @Override
    CustomBannerPattern createCustomObject(Reader reader, Gson gson) {
        return gson.fromJson(reader, CustomBannerPattern.class);
    }
}
