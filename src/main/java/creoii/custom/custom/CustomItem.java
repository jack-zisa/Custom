package creoii.custom.custom;

import com.google.gson.*;
import creoii.custom.data.Identifiable;
import creoii.custom.eventsystem.event.Event;
import creoii.custom.util.json.CustomJsonHelper;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.List;

public class CustomItem extends Item implements Identifiable {
    private final Identifier identifier;
    private final FoodComponent food;
    private final Text tooltipText;

    public CustomItem(Identifier identifier, Settings settings, FoodComponent food, Text tooltipText) {
        super(settings);
        this.identifier = identifier;
        this.food = food;
        this.tooltipText = tooltipText;

        Registry.register(Registry.ITEM, identifier, this);
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    public FoodComponent getFood() {
        return food;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(tooltipText);
    }

    public static class Serializer implements JsonDeserializer<CustomItem>, JsonSerializer<CustomItem> {
        @Override
        public CustomItem deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = JsonHelper.asObject(json, "item");
            Identifier identifier = Identifier.tryParse(JsonHelper.getString(object, "identifier"));
            Item.Settings settings = CustomJsonHelper.getItemSettings(object, "item_settings");
            FoodComponent food;
            if (JsonHelper.hasJsonObject(object, "food")) food = CustomJsonHelper.getFood(object, "food");
            else food = null;
            Text tooltipText = Text.of(JsonHelper.getString(object, "tooltip", ""));
            if (JsonHelper.hasArray(object, "events")) {
                JsonArray array = JsonHelper.getArray(object, "events");
                Event[] events = new Event[array.size()];
                if (events.length > 0) {
                    for (int i = 0; i < events.length; ++i) {
                        if (array.get(i).isJsonObject()) {
                            JsonObject eventObj = array.get(i).getAsJsonObject();
                            events[i] = Event.getEvent(eventObj, Identifier.tryParse(eventObj.get("name").getAsString()));
                        }
                    }
                }
                return new EventCustomItem(identifier, settings, food, tooltipText, events);
            } else {
                return new CustomItem(identifier, settings, food, tooltipText);
            }
        }

        @Override
        public JsonElement serialize(CustomItem src, Type typeOfSrc, JsonSerializationContext context) {
            return null;
        }
    }
}
