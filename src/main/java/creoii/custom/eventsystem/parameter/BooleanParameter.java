package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BooleanParameter implements EventParameter {
    private boolean param;

    public boolean getBoolean() {
        return param;
    }

    public BooleanParameter withValue(boolean param) {
        this.param = param;
        return this;
    }

    @Override
    public EventParameter getType() {
        return EventParameters.BOOLEAN;
    }

    @Override
    public EventParameter getFromJson(JsonObject object, String name) {
        param = object.get(name).getAsBoolean();
        return this;
    }
}
