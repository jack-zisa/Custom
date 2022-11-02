package creoii.custom.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import creoii.custom.custom.CustomItem;

import java.io.Reader;

public class ItemsLoader extends AbstractDataLoader<CustomItem> {
    public ItemsLoader() {
        super("items", new GsonBuilder().setPrettyPrinting().registerTypeAdapter(CustomItem.class, new CustomItem.Serializer()).create());
    }

    @Override
    CustomItem createCustomObject(Reader reader, Gson gson) {
        return gson.fromJson(reader, CustomItem.class);
    }
}
