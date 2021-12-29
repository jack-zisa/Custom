package creoii.custom.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import creoii.custom.custom.CustomItemGroup;

import java.io.Reader;

public class ItemGroupsManager extends AbstractDataManager<CustomItemGroup> {
    public ItemGroupsManager() {
        super("item_groups", new GsonBuilder().setPrettyPrinting().registerTypeAdapter(CustomItemGroup.class, new CustomItemGroup.Serializer()).create());
    }

    @Override
    CustomItemGroup createCustomObject(Reader reader, Gson gson) {
        return gson.fromJson(reader, CustomItemGroup.class);
    }
}
