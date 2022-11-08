package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockParameter implements EventParameter {
    private Block block;

    public Block getBlock() {
        return block;
    }

    public BlockParameter withValue(Block block) {
        this.block = block;
        return this;
    }

    @Override
    public EventParameter getFromJson(JsonObject object) {
        block = Registry.BLOCK.get(Identifier.tryParse(object.get("block").getAsString()));
        return this;
    }
}
