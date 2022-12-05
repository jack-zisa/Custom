package creoii.custom.eventsystem.effect;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;

public class EmitGameEventEffect extends AbstractEffect {
    private GameEventEntry[] entries;
    private GameEvent gameEvent;
    private BlockPos offset;

    public EmitGameEventEffect getFromJson(JsonObject object) {
        EmitGameEventEffect effect = new EmitGameEventEffect();
        if (object.has("entries")) {
            JsonArray array = object.get("entries").getAsJsonArray();
            GameEventEntry[] entries = new GameEventEntry[array.size()];
            for (int i = 0; i < array.size(); ++i) {
                JsonElement element = array.get(i);
                if (element.isJsonObject()) {
                    JsonObject elementObj = element.getAsJsonObject();
                    entries[i] = new GameEventEntry(
                            Registry.GAME_EVENT.get(Identifier.tryParse(JsonHelper.getString(elementObj, "game_event"))),
                            CustomJsonHelper.getBlockPos(element, "offset")
                    );
                }
            }
            effect.entries = entries;
        } else {
            effect.gameEvent = Registry.GAME_EVENT.get(Identifier.tryParse(JsonHelper.getString(object, "game_event")));
            effect.offset = CustomJsonHelper.getBlockPos(object, "offset");
        }
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
                World world = worldParameter.getWorld();
                BlockPos pos = blockPosParameter.getPos();
                if (entries != null) {
                    for (GameEventEntry entry : entries) {
                        world.emitGameEvent(null, entry.event, pos.add(entry.offset));
                    }
                }
                else world.emitGameEvent(null, gameEvent, pos.add(offset));
            }
        }
    }

    public record GameEventEntry(GameEvent event, BlockPos offset) {}
}
