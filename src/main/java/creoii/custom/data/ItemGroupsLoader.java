package creoii.custom.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import creoii.custom.objects.CustomItemGroup;

import java.io.Reader;

public class ItemGroupsLoader extends AbstractDataLoader<CustomItemGroup> {
    public ItemGroupsLoader() {
        super("item_groups", new GsonBuilder().setPrettyPrinting().registerTypeAdapter(CustomItemGroup.class, new CustomItemGroup.Serializer()).create());
    }

    @Override
    CustomItemGroup createCustomObject(Reader reader, Gson gson) {
        return gson.fromJson(reader, CustomItemGroup.class);
    }
}
