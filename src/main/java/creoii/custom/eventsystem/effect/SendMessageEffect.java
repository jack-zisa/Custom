package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.*;
import creoii.custom.util.json.CustomJsonObjects;
import creoii.custom.util.json.JsonHelper2;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.JsonHelper;

import java.util.List;

public class SendMessageEffect extends Effect {
    public MutableText text;
    private boolean actionBar;

    public SendMessageEffect getFromJson(JsonObject object) {
        CustomJsonObjects.TextFormatting formatting = CustomJsonObjects.TextFormatting.get(object, "formatting");
        text = MutableText.of(new LiteralTextContent(JsonHelper.getString(object, "text", ""))).formatted(formatting.formatting());
        for (Formatting formatting1 : formatting.formatting()) {
            text.formatted(formatting1);
        }
        actionBar = JsonHelper.getBoolean(object, "action_bar", false);
        return this;
    }

    @Override
    public List<EventParameter> getParameters() {
        return List.of(EventParameters.WORLD);
    }

    @Override
    public void run(List<EventParameter> parameters) {
        WorldParameter worldParameter = (WorldParameter) EventParameter.find(parameters, EventParameters.WORLD);
        if (worldParameter != null) {
            if (!worldParameter.getWorld().isClient) {
                ((ServerWorld) worldParameter.getWorld()).getPlayers().forEach(player -> {
                    player.sendMessage(text, actionBar);
                });
            }
        }
    }
}
