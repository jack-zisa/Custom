package creoii.custom.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import creoii.custom.custom.CustomPainting;

import java.io.Reader;

public class PaintingsManager extends AbstractDataManager<CustomPainting> {
    public PaintingsManager() {
        super("paintings", new GsonBuilder().setPrettyPrinting().registerTypeAdapter(CustomPainting.class, new CustomPainting.Serializer()).create());
    }

    @Override
    CustomPainting createCustomObject(Reader reader, Gson gson) {
        return gson.fromJson(reader, CustomPainting.class);
    }
}
