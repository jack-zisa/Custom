package creoii.custom.eventsystem.event;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.effect.Effect;
import creoii.custom.util.StringToObject;
import net.minecraft.util.ActionResult;
import net.minecraft.util.JsonHelper;

public class RightClickEvent extends BasicEvent {
    private ActionResult actionResult;

    public RightClickEvent withValues(ActionResult actionResult, Condition[] conditions, Effect[] effects) {
        this.actionResult = actionResult;
        this.conditions = conditions;
        this.effects = effects;
        return this;
    }

    public RightClickEvent getFromJson(JsonObject object) {
        Condition[] conditions = AbstractEvent.getConditions(object);
        Effect[] effects = AbstractEvent.getEffects(object);
        ActionResult actionResult = StringToObject.actionResult(JsonHelper.getString(object, "action_result", "pass"), JsonHelper.getBoolean(object, "swing_hand", true));
        return withValues(actionResult, conditions, effects);
    }

    public ActionResult getActionResult() {
        return actionResult;
    }
}
