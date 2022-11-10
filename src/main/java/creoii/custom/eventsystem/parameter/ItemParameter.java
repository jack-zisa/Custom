package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemParameter implements EventParameter {
    private Item item;

    public Item getItem() {
        return item;
    }

    @Override
    public EventParameter getType() {
        return EventParameters.ITEM;
    }

    @Override
    public ItemParameter getFromJson(JsonObject object, String name) {
        item = Registry.ITEM.get(Identifier.tryParse(object.get(name).getAsString()));
        return this;
    }
}
