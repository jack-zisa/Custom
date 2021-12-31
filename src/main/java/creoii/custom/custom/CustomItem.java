package creoii.custom.custom;

import com.google.gson.*;
import creoii.custom.data.CustomObject;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.lang.reflect.Type;

public class CustomItem extends Item implements CustomObject {
    private final Identifier identifier;

    public CustomItem(Identifier identifier, Settings settings) {
        super(settings);
        this.identifier = identifier;
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    public static class Serializer implements JsonDeserializer<CustomItem>, JsonSerializer<CustomItem> {
        @Override
        public CustomItem deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return null;
        }

        @Override
        public JsonElement serialize(CustomItem src, Type typeOfSrc, JsonSerializationContext context) {
            return null;
        }
    }
}
