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
    public EventParameter getFromJson(JsonObject object) {
        this.enchantment = Registry.ENCHANTMENT.get(Identifier.tryParse(object.get("enchantment").getAsString()));
        return this;
    }
}
