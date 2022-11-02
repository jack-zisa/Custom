package creoii.custom.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import creoii.custom.objects.CustomPainting;

import java.io.Reader;

public class PaintingsLoader extends AbstractDataLoader<CustomPainting> {
    public PaintingsLoader() {
        super("paintings", new GsonBuilder().setPrettyPrinting().registerTypeAdapter(CustomPainting.class, new CustomPainting.Serializer()).create());
    }

    @Override
    CustomPainting createCustomObject(Reader reader, Gson gson) {
        return gson.fromJson(reader, CustomPainting.class);
    }
}
