package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.WorldParameter;
import creoii.custom.util.json.CustomJsonHelper;
import net.minecraft.world.World;

import java.util.List;

public class MoonPhaseMatches extends Condition {
    private MoonPhase moonPhase;

    @Override
    public MoonPhaseMatches getFromJson(JsonObject object) {
        MoonPhaseMatches condition = new MoonPhaseMatches();
        condition.moonPhase = MoonPhase.valueOf(CustomJsonHelper.getString(object, new String[]{"phase", "moon_phase"}).toUpperCase());
        return condition;
    }

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of(EventParameters.WORLD);
    }

    @Override
    public boolean test(List<EventParameter> parameters) {
        WorldParameter worldParameter = (WorldParameter) EventParameter.find(parameters, EventParameters.WORLD);
        if (worldParameter != null) {
            World world = worldParameter.getWorld();
            return world.getMoonPhase() == moonPhase.getPhaseIndex();
        }
        return false;
    }

    private enum MoonPhase {
        NEW(0),
        WANING_CRESCENT(1),
        THIRD_QUARTER(2),
        WANING_GIBBOUS(3),
        FULL(4),
        WAXING_GIBBOUS(5),
        FIRST_QUARTER(6),
        WAXING_CRESCENT(7);

        private final int i;

        MoonPhase(int i) {
            this.i = i;
        }

        public int getPhaseIndex() {
            return i;
        }
    }
}
