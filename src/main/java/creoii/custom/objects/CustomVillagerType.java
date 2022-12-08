package creoii.custom.objects;

import com.google.gson.*;
import creoii.custom.loaders.Identifiable;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.Registry;
import net.minecraft.village.VillagerType;
import net.minecraft.world.biome.Biome;

import java.lang.reflect.Type;

public class CustomVillagerType extends VillagerType implements Identifiable {
    private final Identifier identifier;
    private final Biome[] biomes;

    public CustomVillagerType(Identifier identifier, Biome[] biomes) {
        super(identifier.getPath());
        this.identifier = identifier;
        this.biomes = biomes;

        Registry.register(Registries.VILLAGER_TYPE, identifier, this);
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    public Biome[] getBiomes() {
        return biomes;
    }

    public static class Serializer implements JsonDeserializer<CustomVillagerType>, JsonSerializer<CustomVillagerType> {
        @Override
        public CustomVillagerType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = JsonHelper.asObject(json, "villager type");
            Identifier identifier = Identifier.tryParse(JsonHelper.getString(object, "identifier"));
            Biome[] biomes;
            if (JsonHelper.hasArray(object, "biomes")) {
                JsonArray array = JsonHelper.getArray(object, "biomes");
                biomes = new Biome[array.size()];
                for (int i = 0; i < array.size(); ++i) {
                    if (array.get(i).isJsonPrimitive()) {
                        //biomes[i] = BuiltinRegistries.BIOME.get(Identifier.tryParse(array.get(i).getAsString()));
                    }
                }
            } else biomes = new Biome[0];
            return new CustomVillagerType(identifier, biomes);
        }

        @Override
        public JsonElement serialize(CustomVillagerType src, Type typeOfSrc, JsonSerializationContext context) {
            return null;
        }
    }
}
