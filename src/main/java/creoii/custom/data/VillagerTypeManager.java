package creoii.custom.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import creoii.custom.custom.CustomVillagerType;

import java.io.Reader;

public class VillagerTypeManager extends AbstractDataManager<CustomVillagerType> {
    public VillagerTypeManager() {
        super("villager/types", new GsonBuilder().setPrettyPrinting().registerTypeAdapter(CustomVillagerType.class, new CustomVillagerType.Serializer()).create());
    }

    @Override
    CustomVillagerType createCustomObject(Reader reader, Gson gson) {
        return gson.fromJson(reader, CustomVillagerType.class);
    }
}
