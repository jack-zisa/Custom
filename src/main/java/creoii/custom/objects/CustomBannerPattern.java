package creoii.custom.objects;

import com.google.gson.*;
import creoii.custom.data.Identifiable;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

import java.lang.reflect.Type;

public class CustomBannerPattern extends BannerPattern implements Identifiable {
    private final Identifier identifier;

    public CustomBannerPattern(Identifier identifier) {
        super(identifier.getPath());
        this.identifier = identifier;
        Registry.register(Registry.BANNER_PATTERN, this.getIdentifier(), this);
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    public static class Serializer implements JsonDeserializer<CustomBannerPattern>, JsonSerializer<CustomBannerPattern> {
        @Override
        public CustomBannerPattern deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = JsonHelper.asObject(json, "banner pattern");
            Identifier identifier = Identifier.tryParse(JsonHelper.getString(object, "identifier"));
            if (identifier != null)
                return new CustomBannerPattern(identifier);
            else
                throw new JsonSyntaxException("Could not parse banner pattern.");
        }

        @Override
        public JsonElement serialize(CustomBannerPattern src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.addProperty("identifier", src.getIdentifier().toString());
            return object;
        }
    }
}
