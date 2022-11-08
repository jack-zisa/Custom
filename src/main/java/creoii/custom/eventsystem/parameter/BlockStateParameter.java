package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockStateParameter implements EventParameter {
    private BlockState state;

    public BlockState getBlockState() {
        return state;
    }

    @Override
    public EventParameter getFromJson(JsonObject object) {
        state = Registry.BLOCK.get(Identifier.tryParse(object.get("block").getAsString())).getDefaultState();
        return this;
    }
}
