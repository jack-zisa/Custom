package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;
import net.minecraft.block.Block;

public class EmptyParameter implements EventParameter {
    @Override
    public EventParameter getType() {
        return EventParameters.EMPTY;
    }

    @Override
    public EventParameter getFromJson(JsonObject object, String name) {
        return EventParameters.EMPTY;
    }
}
