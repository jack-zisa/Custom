package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

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
        item = Registries.ITEM.get(Identifier.tryParse(object.get(name).getAsString()));
        return this;
    }

    @Override
    public String getName() {
        return name;
    }
}
