package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class BlockParameter implements EventParameter {
    private String name = "block";
    private Block block;

    public Block getBlock() {
        return block;
    }

    public BlockParameter withValue(Block block) {
        this.block = block;
        return this;
    }

    public BlockParameter name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public EventParameter getType() {
        return EventParameters.BLOCK;
    }

    @Override
    public EventParameter getFromJson(JsonObject object, String name) {
        block = Registries.BLOCK.get(Identifier.tryParse(object.get(name).getAsString()));
        return this;
    }

    @Override
    public String getName() {
        return name;
    }
}
