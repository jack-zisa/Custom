package creoii.custom.eventsystem.condition;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.EntityParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class HasEnchantmentCondition extends Condition {
    private Enchantment enchantment;
    private EquipmentSlot[] slots;

    @Override
    public HasEnchantmentCondition getFromJson(JsonObject object) {
        HasEnchantmentCondition condition = new HasEnchantmentCondition();
        condition.enchantment = Registry.ENCHANTMENT.get(Identifier.tryParse(object.get("enchantment").getAsString()));
        if (object.has("slots")) {
            JsonArray array = object.get("slots").getAsJsonArray();
            EquipmentSlot[] slots = new EquipmentSlot[array.size()];
            for (int i = 0; i < array.size(); ++i) {
                JsonElement element = array.get(i);
                if (element.isJsonPrimitive()) {
                    slots[i] = EquipmentSlot.valueOf(element.getAsString().toUpperCase());
                }
            }
            condition.slots = slots;
        }
        return condition;
    }

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of(EventParameters.ENTITY);
    }

    @Override
    public boolean test(List<EventParameter> parameters) {
        EntityParameter entityParameter = (EntityParameter) EventParameter.find(parameters, EventParameters.ENTITY);
        if (entityParameter != null) {
            if (entityParameter.getEntity() instanceof LivingEntity livingEntity) {
                for (EquipmentSlot slot : slots) {
                    return EnchantmentHelper.getLevel(enchantment, livingEntity.getEquippedStack(slot)) > 0;
                }
            }
        }
        return false;
    }
}
