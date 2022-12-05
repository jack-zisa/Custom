package creoii.custom.eventsystem.effect;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.EntityParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.function.CommandFunctionManager;
import net.minecraft.util.Identifier;

import java.util.List;

public class RunFunctionEffect extends AbstractEffect {
    private Identifier[] entries;
    private Identifier functionId;

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of(EventParameters.ENTITY);
    }

    public RunFunctionEffect getFromJson(JsonObject object) {
        RunFunctionEffect effect = new RunFunctionEffect();
        if (object.has("entries")) {
            JsonArray array = object.get("entries").getAsJsonArray();
            Identifier[] entries = new Identifier[array.size()];
            for (int i = 0; i < array.size(); ++i) {
                JsonElement element = array.get(i);
                if (element.isJsonPrimitive()) {
                    entries[i] = Identifier.tryParse(element.getAsString());
                }
            }
            effect.entries = entries;
        } else effect.functionId = Identifier.tryParse(object.get("function").getAsString());
        return effect;
    }

    public void run(List<EventParameter> parameters) {
        EntityParameter entityParameter = (EntityParameter) EventParameter.find(parameters, getModifications(), EventParameters.ENTITY);
        if (entityParameter != null) {
            if (entityParameter.getEntity() instanceof PlayerEntity playerEntity) {
                ServerCommandSource source = playerEntity.getCommandSource();
                CommandFunctionManager manager = source.getServer().getCommandFunctionManager();
                if (entries != null) {
                    for (Identifier entry : entries) {
                        manager.getFunction(entry).ifPresent(commandFunction -> manager.execute(commandFunction, source));
                    }
                } else manager.getFunction(functionId).ifPresent(commandFunction -> manager.execute(commandFunction, source));
            }
        }
    }
}
