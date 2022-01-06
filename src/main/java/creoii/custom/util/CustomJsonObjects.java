package creoii.custom.util;

import com.google.gson.JsonObject;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

public class CustomJsonObjects {
    public record AttributeModifier(EntityAttribute attribute, EntityAttributeModifier modifier, double amount) {
        public static AttributeModifier get(JsonObject object) {
            String name = JsonHelper.getString(object, "name", "");
            double amount = JsonHelper.getDouble(object, "amount", 0d);
            EntityAttributeModifier.Operation operation = StringToObject.attributeModifierOperation(JsonHelper.getString(object, "operation", "addition"));
            EntityAttributeModifier modifier = new EntityAttributeModifier(name, amount, operation);
            EntityAttribute attribute = Registry.ATTRIBUTE.get(Identifier.tryParse(JsonHelper.getString(object, "attribute")));
            return new AttributeModifier(attribute, modifier, amount);
        }
    }
}
