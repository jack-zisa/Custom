package creoii.custom.util.math.bool;

import com.google.gson.JsonObject;
import creoii.custom.util.math.number.NumberProvider;
import net.minecraft.util.JsonHelper;

import java.util.Random;

import static creoii.custom.Custom.RANDOM;

public class RandomBooleanProvider extends BooleanProvider {
    public RandomBooleanProvider() {
        super("random");
    }

    public boolean getValue() {
        return RANDOM.nextBoolean();
    }

    public RandomBooleanProvider getFromJson(JsonObject object) {
        return new RandomBooleanProvider();
    }
}
