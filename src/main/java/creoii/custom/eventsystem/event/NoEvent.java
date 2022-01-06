package creoii.custom.eventsystem.event;

import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.effect.Effect;

public class NoEvent extends Event {
    public NoEvent() {
        super("none", new Condition[0], new Effect[0]);
    }
}
