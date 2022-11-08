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
    public ItemParameter getFromJson(JsonObject object) {
        item = Registry.ITEM.get(Identifier.tryParse(object.get("item").getAsString()));
        return this;
    }
}
