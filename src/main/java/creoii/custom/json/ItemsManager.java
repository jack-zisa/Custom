package creoii.custom.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import creoii.custom.custom.CustomItem;
import creoii.custom.custom.CustomPainting;

import java.io.Reader;

public class ItemsManager extends AbstractDataManager<CustomItem> {
    public ItemsManager() {
        super("items", new GsonBuilder().setPrettyPrinting().registerTypeAdapter(CustomItem.class, new CustomItem.Serializer()).create());
    }

    @Override
    CustomItem createCustomObject(Reader reader, Gson gson) {
        return gson.fromJson(reader, CustomItem.class);
    }
}
