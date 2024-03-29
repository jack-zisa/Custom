package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.BlockPosParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.WorldParameter;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.util.List;

public class SetBlockEffect extends AbstractEffect {
    private Block block;
    private int flags;

    public SetBlockEffect getFromJson(JsonObject object) {
        SetBlockEffect effect = new SetBlockEffect();
        effect.block = Registries.BLOCK.get(Identifier.tryParse(JsonHelper.getString(object, "block")));
        effect.flags = JsonHelper.getInt(object, "flags", 3);
        return effect;
    }

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of(EventParameters.WORLD, EventParameters.BLOCK_POS);
    }

    @Override
    public void run(List<EventParameter> parameters) {
        WorldParameter worldParameter = (WorldParameter) EventParameter.find(parameters, getModifications(), EventParameters.WORLD);
        if (worldParameter != null) {
            BlockPosParameter blockPosParameter = (BlockPosParameter) EventParameter.find(parameters, getModifications(), EventParameters.BLOCK_POS);
            if (blockPosParameter != null) {
                worldParameter.getWorld().setBlockState(blockPosParameter.getPos(), block.getDefaultState(), flags);
            }
        }
    }
}
