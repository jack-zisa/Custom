package creoii.custom.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import creoii.custom.custom.SimpleCustomBlock;

import java.io.Reader;

public class BlocksManager extends AbstractDataManager<SimpleCustomBlock> {
    public BlocksManager() {
        super("blocks", new GsonBuilder().setPrettyPrinting().registerTypeAdapter(SimpleCustomBlock.class, new SimpleCustomBlock.Serializer()).create());
    }

    @Override
    SimpleCustomBlock createCustomObject(Reader reader, Gson gson) {
        return gson.fromJson(reader, SimpleCustomBlock.class);
    }
}
