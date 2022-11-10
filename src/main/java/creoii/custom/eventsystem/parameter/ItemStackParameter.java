package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemStackParameter implements EventParameter {
    private ItemStack stack;

    public ItemStack getItemStack() {
        return stack;
    }

    @Override
    public EventParameter getType() {
        return EventParameters.ITEMSTACK;
    }

    @Override
    public ItemStackParameter getFromJson(JsonObject object, String name) {
        stack = Registry.ITEM.get(Identifier.tryParse(object.get(name).getAsString())).getDefaultStack();
        return this;
    }
}
