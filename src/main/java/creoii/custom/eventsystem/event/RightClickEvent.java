package creoii.custom.eventsystem.event;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.effect.Effect;
import creoii.custom.util.StringToObject;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.concurrent.atomic.AtomicBoolean;

public class RightClickEvent extends Event {
    private final ActionResult actionResult;

    public RightClickEvent(ActionResult actionResult, Condition[] conditions, Effect[] effects) {
        super(Event.RIGHT_CLICK, conditions, effects);
        this.actionResult = actionResult;
    }

    public static Event getFromJson(JsonObject object) {
        Condition[] conditions = Event.getConditions(object);
        Effect[] effects = Event.getEffects(object);
        ActionResult actionResult = StringToObject.actionResult(JsonHelper.getString(object, "action_result", "pass"), JsonHelper.getBoolean(object, "swing_hand", true));
        return new RightClickEvent(actionResult, conditions, effects);
    }

    public ActionResult getActionResult() {
        return actionResult;
    }
}
