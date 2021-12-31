package creoii.custom.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import creoii.custom.custom.CustomBlock;

import java.io.Reader;

public class BlocksManager extends AbstractDataManager<CustomBlock> {
    public BlocksManager() {
        super("blocks", new GsonBuilder().setPrettyPrinting().registerTypeAdapter(CustomBlock.class, new CustomBlock.Serializer()).create());
    }

    @Override
    CustomBlock createCustomObject(Reader reader, Gson gson) {
        return gson.fromJson(reader, CustomBlock.class);
    }
}
