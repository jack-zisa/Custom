package creoii.custom.objects;

import com.google.gson.*;
import creoii.custom.loaders.Identifiable;
import creoii.custom.util.json.CustomJsonHelper;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

public class CustomPotion extends Potion implements Identifiable {
    private final Identifier identifier;
    private final int color;

    public CustomPotion(Identifier id, int color, StatusEffectInstance... effects) {
        super(effects);
        identifier = id;
        this.color = color;
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    public static class Serializer implements JsonDeserializer<CustomPotion>, JsonSerializer<CustomPotion> {
        @Override
        public CustomPotion deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = JsonHelper.asObject(json, "potion");
            Identifier identifier = Identifier.tryParse(JsonHelper.getString(object, "id"));
            int color = JsonHelper.getInt(object, "color", 0);
            JsonArray array = JsonHelper.getArray(object, "effects");
            StatusEffectInstance[] effects = new StatusEffectInstance[array.size()];
            for (int i = 0; i < effects.length; ++i) {
                effects[i] = CustomJsonHelper.getStatusEffectInstance(array.get(i));
            }
            CustomPotion potion = new CustomPotion(identifier, color, effects);
            if (object.has("recipe")) {
                JsonObject recipeObj = JsonHelper.getObject(object, "recipe");
                Potion input = Registry.POTION.get(Identifier.tryParse(JsonHelper.getString(recipeObj, "input")));
                Item catalyst = Registry.ITEM.get(Identifier.tryParse(JsonHelper.getString(object, "catalyst")));
                BrewingRecipeRegistry.registerPotionRecipe(input, catalyst, potion);
            }
            return potion;
        }

        @Override
        public JsonElement serialize(CustomPotion src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.addProperty("identifier", src.getIdentifier().toString());
            object.addProperty("color", src.color);
            return object;
        }
    }
}
