package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.BlockPosParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.WorldParameter;
import creoii.custom.util.json.CustomJsonHelper;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.event.GameEvent;

import java.util.List;

public class EmitGameEventEffect extends AbstractEffect {
    private GameEvent gameEvent;
    private BlockPos offset;

    public EmitGameEventEffect getFromJson(JsonObject object) {
        EmitGameEventEffect effect = new EmitGameEventEffect();
        effect.gameEvent = Registry.GAME_EVENT.get(Identifier.tryParse(JsonHelper.getString(object, "game_event")));
        effect.offset = CustomJsonHelper.getBlockPos(object, "offset");
        return effect;
    }

    @Override
    public List<EventParameter> getParameters() {
        return List.of(EventParameters.WORLD);
    }

    @Override
    public void run(List<EventParameter> parameters) {
        WorldParameter worldParameter = (WorldParameter) EventParameter.find(parameters, EventParameters.WORLD);
        if (worldParameter != null) {
            BlockPosParameter blockPosParameter = (BlockPosParameter) EventParameter.find(parameters, EventParameters.BLOCK_POS);
            if (blockPosParameter != null) {
                worldParameter.getWorld().emitGameEvent(null, gameEvent, blockPosParameter.getPos().add(offset));
            }
        }
    }
}
