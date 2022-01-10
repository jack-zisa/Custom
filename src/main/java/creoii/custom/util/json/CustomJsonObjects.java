package creoii.custom.util.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import creoii.custom.util.StringToObject;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.util.Formatting;
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

    public record TextFormatting(Formatting[] formatting) {
        public static TextFormatting get(JsonObject object) {
            if (JsonHelper.hasArray(object, "formatting")) {
                JsonArray array = JsonHelper.getArray(object, "formatting");
                Formatting[] formatting = new Formatting[array.size()];
                if (formatting.length > 0) {
                    for (int i = 0; i < formatting.length; ++i) {
                        if (array.get(i).isJsonPrimitive()) {
                            formatting[i] = Formatting.byName(array.get(i).getAsString().toUpperCase());
                        }
                    }
                }
                return new TextFormatting(formatting);
            } else {
                return new TextFormatting(new Formatting[0]);
            }
        }
    }
}
