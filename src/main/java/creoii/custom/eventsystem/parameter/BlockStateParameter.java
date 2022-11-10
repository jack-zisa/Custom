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

    public BlockStateParameter withValue(BlockState state) {
        this.state = state;
        return this;
    }

    @Override
    public EventParameter getType() {
        return EventParameters.BLOCKSTATE;
    }

    @Override
    public EventParameter getFromJson(JsonObject object, String name) {
        state = Registry.BLOCK.get(Identifier.tryParse(object.get(name).getAsString())).getDefaultState();
        return this;
    }
}
