package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ItemStackParameter implements EventParameter {
    private String name;
    private ItemStack stack;

    public ItemStack getItemStack() {
        return stack;
    }

    public ItemStackParameter withValue(ItemStack stack) {
        this.stack = stack;
        return this;
    }

    public ItemStackParameter name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public EventParameter getType() {
        return EventParameters.ITEMSTACK;
    }

    @Override
    public ItemStackParameter getFromJson(JsonObject object, String name) {
        stack = Registries.ITEM.get(Identifier.tryParse(object.get(name).getAsString())).getDefaultStack();
        return this;
    }

    @Override
    public String getName() {
        return name;
    }
}
