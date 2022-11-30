package creoii.custom.loaders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import creoii.custom.objects.CustomVillagerType;

import java.io.Reader;

public class VillagerTypesLoader extends AbstractDataLoader<CustomVillagerType> {
    public VillagerTypesLoader() {
        super("villager/types", new GsonBuilder().setPrettyPrinting().registerTypeAdapter(CustomVillagerType.class, new CustomVillagerType.Serializer()).create());
    }

    @Override
    CustomVillagerType createCustomObject(Reader reader, Gson gson) {
        return gson.fromJson(reader, CustomVillagerType.class);
    }
}
