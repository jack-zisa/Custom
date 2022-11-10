package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;
import net.minecraft.block.Block;

public class ObjectParameter implements EventParameter {
    private Object object;

    public Object getObject() {
        return object;
    }

    public ObjectParameter withValue(Object object) {
        this.object = object;
        return this;
    }

    @Override
    public EventParameter getType() {
        return EventParameters.OBJECT;
    }

    @Override
    public EventParameter getFromJson(JsonObject object, String name) {
        return this;
    }
}
