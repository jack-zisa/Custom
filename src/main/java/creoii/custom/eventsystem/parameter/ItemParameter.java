package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemParameter implements EventParameter {
    private String name;
    private Item item;

    public Item getItem() {
        return item;
    }

    public ItemParameter withValue(Item item) {
        this.item = item;
        return this;
    }

    public ItemParameter name(String name) {
        this.name = name;
        return this;
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

    @Override
    public String getName() {
        return name;
    }
}
