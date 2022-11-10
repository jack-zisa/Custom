package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EnchantmentParameter implements EventParameter {
    private Enchantment enchantment;

    public Enchantment getEnchantment() {
        return enchantment;
    }

    @Override
    public EventParameter getType() {
        return EventParameters.ENCHANTMENT;
    }

    @Override
    public EventParameter getFromJson(JsonObject object, String name) {
        this.enchantment = Registry.ENCHANTMENT.get(Identifier.tryParse(object.get(name).getAsString()));
        return this;
    }
}
