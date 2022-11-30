package creoii.custom.eventsystem.parameter;

import java.util.HashMap;
import java.util.Map;

public class ParameterModifications {
    private Map<EventParameter, String> modifications;

    public Map<EventParameter, String> getModifications() {
        return modifications;
    }

    public boolean modifies(EventParameter type) {
        return modifications.containsKey(type);
    }

    public static class Builder {
        private final ParameterModifications modifications;

        public Builder() {
            modifications = new ParameterModifications();
            modifications.modifications = new HashMap<>();
        }

        public ParameterModifications add(EventParameter parameter, String name) {
            modifications.getModifications().put(parameter, name);
            return modifications;
        }

        public ParameterModifications build() {
            return modifications;
        }
    }
}
