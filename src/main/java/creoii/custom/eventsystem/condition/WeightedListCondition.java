package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.Custom;
import creoii.custom.eventsystem.parameter.EventParameter;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.random.Random;

import java.util.List;

public class WeightedListCondition extends Condition {
    private DataPool<Condition> conditions;

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of();
    }

    public WeightedListCondition getFromJson(JsonObject object) {
        WeightedListCondition condition = new WeightedListCondition();
        DataPool.Builder<Condition> list = DataPool.builder();
        JsonHelper.getArray(object, "conditions").forEach(element -> {
            JsonObject object1 = element.getAsJsonObject();
            list.add(Condition.getCondition(object1, object1.get("type").getAsString()), object1.get("weight").getAsInt());
        });
        condition.conditions = list.build();
        return condition;
    }

    public Condition getRandom(Random random) {
        return conditions.getDataOrEmpty(random).orElseThrow(IllegalStateException::new);
    }

    @Override
    public boolean test(List<EventParameter> parameters) {
        return getRandom(Custom.RANDOM).test(parameters);
    }
}
