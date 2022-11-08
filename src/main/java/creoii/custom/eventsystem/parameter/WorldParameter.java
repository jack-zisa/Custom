package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;
import net.minecraft.world.World;

public class WorldParameter implements EventParameter {
    private World world;

    public World getWorld() {
        return world;
    }

    @Override
    public EventParameter getFromJson(JsonObject object) {
        return null;
    }
}
