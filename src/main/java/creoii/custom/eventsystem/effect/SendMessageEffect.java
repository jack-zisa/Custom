package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.WorldParameter;
import creoii.custom.util.json.CustomJsonObjects;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.JsonHelper;

import java.util.List;

public class SendMessageEffect extends AbstractEffect {
    public MutableText text;
    private boolean actionBar;

    public SendMessageEffect getFromJson(JsonObject object) {
        SendMessageEffect effect = new SendMessageEffect();
        CustomJsonObjects.TextFormatting formatting = CustomJsonObjects.TextFormatting.get(object, "formatting");
        effect.text = MutableText.of(new LiteralTextContent(JsonHelper.getString(object, "text", ""))).formatted(formatting.formatting());
        for (Formatting formatting1 : formatting.formatting()) {
            effect.text.formatted(formatting1);
        }
        effect.actionBar = JsonHelper.getBoolean(object, "action_bar", false);
        return effect;
    }

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of(EventParameters.WORLD);
    }

    @Override
    public void run(List<EventParameter> parameters) {
        WorldParameter worldParameter = (WorldParameter) EventParameter.find(parameters, getModifications(), EventParameters.WORLD);
        if (worldParameter != null) {
            if (!worldParameter.getWorld().isClient) {
                ((ServerWorld) worldParameter.getWorld()).getPlayers().forEach(player -> player.sendMessage(text, actionBar));
            }
        }
    }
}
