package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class EnchantmentParameter implements EventParameter {
    private String name;
    private Enchantment enchantment;

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public EnchantmentParameter withValue(Enchantment enchantment) {
        this.enchantment = enchantment;
        return this;
    }

    public EnchantmentParameter name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public EventParameter getType() {
        return EventParameters.ENCHANTMENT;
    }

    @Override
    public EventParameter getFromJson(JsonObject object, String name) {
        this.enchantment = Registries.ENCHANTMENT.get(Identifier.tryParse(object.get(name).getAsString()));
        return this;
    }

    @Override
    public String getName() {
        return name;
    }
}
