package creoii.custom.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import creoii.custom.custom.CustomVillagerProfession;

import java.io.Reader;

public class VillagerProfessionManager extends AbstractDataManager<CustomVillagerProfession> {
    public VillagerProfessionManager() {
        super("villager/professions", new GsonBuilder().setPrettyPrinting().registerTypeAdapter(CustomVillagerProfession.class, new CustomVillagerProfession.Serializer()).create());
    }

    @Override
    CustomVillagerProfession createCustomObject(Reader reader, Gson gson) {
        return gson.fromJson(reader, CustomVillagerProfession.class);
    }
}
