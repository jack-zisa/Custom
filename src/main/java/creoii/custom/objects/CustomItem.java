package creoii.custom.objects;

import com.google.gson.*;
import creoii.custom.eventsystem.event.AbstractEvent;
import creoii.custom.loaders.Identifiable;
import creoii.custom.util.Constants;
import creoii.custom.util.json.CustomJsonHelper;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CustomItem extends Item implements Identifiable {
    private final Identifier identifier;
    private FoodComponent food;
    private final Text tooltipText;

    public CustomItem(Identifier identifier, Settings settings, Text tooltipText) {
        super(settings);
        this.identifier = identifier;
        this.tooltipText = tooltipText;

        Registry.register(Registries.ITEM, identifier, this);
    }

    public CustomItem setFood(FoodComponent food) {
        this.food = food;
        return this;
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (tooltipText != null) tooltip.add(tooltipText);
    }

    public static class Serializer implements JsonDeserializer<CustomItem>, JsonSerializer<CustomItem> {
        @Override
        public CustomItem deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = JsonHelper.asObject(json, "item");
            Identifier identifier = Identifier.tryParse(JsonHelper.getString(object, "identifier"));
            Item.Settings settings = CustomJsonHelper.getItemSettings(object, "item_settings");
            Text tooltipText = Text.of(JsonHelper.getString(object, "tooltip", null));

            FoodComponent food;
            if (JsonHelper.hasJsonObject(object, "food")) food = CustomJsonHelper.getFood(object, "food");
            else food = null;

            if (JsonHelper.hasArray(object, "events")) {
                JsonArray array = JsonHelper.getArray(object, "events");
                List<AbstractEvent> events = new ArrayList<>();
                if (array.size() > 0) {
                    for (int i = 0; i < array.size(); ++i) {
                        if (array.get(i).isJsonObject()) {
                            JsonObject eventObj = array.get(i).getAsJsonObject();
                            events.add(AbstractEvent.getEvent(Identifier.tryParse(eventObj.get("name").getAsString())));
                        }
                    }
                }
                EventCustomItem eventCustomItem = new EventCustomItem(identifier, settings, tooltipText, events);
                if (food != null) {
                    JsonObject foodObj = object.getAsJsonObject("food");
                    if (foodObj.has("eat_speed")) {
                        Constants.FOOD_EATING_SPEEDS.put(identifier, JsonHelper.getInt(foodObj, "eat_speed", 32));
                    }
                    return eventCustomItem.setFood(food);
                } else return eventCustomItem;
            } else {
                CustomItem customItem = new CustomItem(identifier, settings, tooltipText);
                if (food != null) {
                    JsonObject foodObj = object.getAsJsonObject("food");
                    if (foodObj.has("eat_speed")) {
                        Constants.FOOD_EATING_SPEEDS.put(identifier, JsonHelper.getInt(foodObj, "eat_speed", 32));
                    }
                    return customItem.setFood(food);
                } else return customItem;
            }
        }

        @Override
        public JsonElement serialize(CustomItem src, Type typeOfSrc, JsonSerializationContext context) {
            return null;
        }
    }
}
