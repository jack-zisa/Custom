package creoii.custom.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import creoii.custom.custom.CustomPotion;
import creoii.custom.custom.block.CustomBlock;

import java.io.Reader;

public class PotionsManager extends AbstractDataManager<CustomPotion> {
    public PotionsManager() {
        super("potions", new GsonBuilder().setPrettyPrinting().registerTypeAdapter(CustomPotion.class, new CustomPotion.Serializer()).create());
    }

    @Override
    CustomPotion createCustomObject(Reader reader, Gson gson) {
        return gson.fromJson(reader, CustomPotion.class);
    }
}
