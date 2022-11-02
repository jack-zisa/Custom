package creoii.custom.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import creoii.custom.custom.CustomInstrument;
import creoii.custom.custom.block.CustomBlock;

import java.io.Reader;

public class InstrumentsManager extends AbstractDataManager<CustomInstrument> {
    public InstrumentsManager() {
        super("instruments", new GsonBuilder().setPrettyPrinting().registerTypeAdapter(CustomInstrument.class, new CustomInstrument.Serializer()).create());
    }

    @Override
    CustomInstrument createCustomObject(Reader reader, Gson gson) {
        return gson.fromJson(reader, CustomInstrument.class);
    }
}
