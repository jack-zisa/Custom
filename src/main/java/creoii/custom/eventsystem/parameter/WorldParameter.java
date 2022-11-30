package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;
import net.minecraft.world.World;

public class WorldParameter implements EventParameter {
    private String name = "world";
    private World world;

    public World getWorld() {
        return world;
    }

    public WorldParameter withValue(World world) {
        this.world = world;
        return this;
    }

    public WorldParameter name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public EventParameter getType() {
        return EventParameters.WORLD;
    }

    @Override
    public EventParameter getFromJson(JsonObject object, String name) {
        return this;
    }

    @Override
    public String getName() {
        return name;
    }
}
