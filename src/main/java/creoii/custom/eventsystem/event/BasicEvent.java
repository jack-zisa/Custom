package creoii.custom.eventsystem.event;

import creoii.custom.Custom;
import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.effect.Effect;
import creoii.custom.eventsystem.parameter.EventParameter;

import java.util.List;

public class BasicEvent extends AbstractEvent {
    @Override
    public AbstractEvent getType() {
        return Custom.EVENT.get(getIdentifier());
    }

    public boolean apply(List<EventParameter> parameters) {
        boolean applied = true;
        for (Condition condition : conditions) {
            if (!condition.test(parameters)) applied = false;
        }

        if (applied) {
            for (Effect effect : effects) {
                effect.run(parameters);
            }
        }
        return applied;
    }
}
