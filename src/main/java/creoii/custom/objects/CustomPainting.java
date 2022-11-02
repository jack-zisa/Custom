package creoii.custom.objects;

import com.google.gson.*;
import creoii.custom.data.Identifiable;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

import java.lang.reflect.Type;

public class CustomPainting extends PaintingVariant implements Identifiable {
    private final Identifier identifier;

    public CustomPainting(Identifier identifier, int width, int height) {
        super(width, height);
        this.identifier = identifier;
        Registry.register(Registry.PAINTING_VARIANT, this.getIdentifier(), this);
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    public static class Serializer implements JsonDeserializer<CustomPainting>, JsonSerializer<CustomPainting> {
        @Override
        public CustomPainting deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = JsonHelper.asObject(json, "painting");
            int width = JsonHelper.getInt(object, "width", 16);
            int height = JsonHelper.getInt(object, "height", 16);
            return new CustomPainting(
                    Identifier.tryParse(JsonHelper.getString(object, "identifier")), width, height
            );
        }

        @Override
        public JsonElement serialize(CustomPainting src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.addProperty("identifier", src.getIdentifier().toString());
            object.addProperty("width", src.getWidth());
            object.addProperty("height", src.getHeight());
            return object;
        }
    }
}
