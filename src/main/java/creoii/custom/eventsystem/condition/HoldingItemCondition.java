package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.EntityParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

import java.util.List;

public class HoldingItemCondition extends Condition {
    private Item item;
    private Hand hand;

    @Override
    public HoldingItemCondition getFromJson(JsonObject object) {
        HoldingItemCondition condition = new HoldingItemCondition();
        condition.item = Registries.ITEM.get(Identifier.tryParse(object.get("item").getAsString()));
        condition.hand = Hand.valueOf(object.get("hand").getAsString().toUpperCase());
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
                return livingEntity.getStackInHand(hand).isOf(item);
            }
        }
        return false;
    }
}
