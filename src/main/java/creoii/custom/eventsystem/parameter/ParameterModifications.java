package creoii.custom.eventsystem.parameter;

import java.util.HashMap;
import java.util.Map;

public class ParameterModifications {
    private final Map<EventParameter, String> modifications;

    public ParameterModifications() {
        modifications = new HashMap<>();
    }

    public Map<EventParameter, String> getModifications() {
        return modifications;
    }

    public void addModification(EventParameter type, String name) {
        modifications.put(type, name);
    }

    public ParameterModifications add(EventParameter parameter, String name) {
        modifications.put(parameter, name);
        return this;
    }

    public boolean modifies(EventParameter type) {
        return modifications.containsKey(type);
    }
}
