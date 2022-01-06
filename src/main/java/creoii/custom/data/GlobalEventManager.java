package creoii.custom.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import creoii.custom.eventsystem.global.GlobalEvent;

import java.io.Reader;

public class GlobalEventManager extends AbstractDataManager<GlobalEvent> {
    public GlobalEventManager() {
        super("global_events", new GsonBuilder().setPrettyPrinting().registerTypeAdapter(GlobalEvent.class, new GlobalEvent.Serializer()).create());
    }

    @Override
    GlobalEvent createCustomObject(Reader reader, Gson gson) {
        return gson.fromJson(reader, GlobalEvent.class);
    }
}
