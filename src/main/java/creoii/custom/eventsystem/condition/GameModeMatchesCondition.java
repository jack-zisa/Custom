package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.EntityParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.util.json.CustomJsonHelper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;

import java.util.List;

public class GameModeMatchesCondition extends Condition {
    private GameMode gameMode;

    @Override
    public GameModeMatchesCondition getFromJson(JsonObject object) {
        GameModeMatchesCondition condition = new GameModeMatchesCondition();
        condition.gameMode = GameMode.byName(CustomJsonHelper.getString(object, new String[]{"game_mode", "gamemode"}));
        return condition;
    }

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of(EventParameters.ENTITY);
    }

    @Override
    public boolean test(List<EventParameter> parameters) {
        EntityParameter entityParameter = (EntityParameter) EventParameter.find(parameters, EventParameters.ENTITY);
        if (entityParameter != null) {
            if (entityParameter.getEntity() instanceof ServerPlayerEntity playerEntity) {
                return playerEntity.interactionManager.getGameMode() == gameMode;
            }
        }
        return false;
    }
}
