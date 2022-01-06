package creoii.custom.eventsystem.global;

import com.google.gson.*;
import creoii.custom.data.CustomObject;
import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.effect.Effect;
import creoii.custom.eventsystem.event.Event;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

import java.lang.reflect.Type;

public record GlobalEvent(Identifier identifier, String type, Condition[] conditions, Effect[] effects) implements CustomObject {
    public static final String CLIENT_WORLD_TICK = "client_world_tick";
    public static final String SERVER_WORLD_TICK = "server_world_tick";

    public GlobalEvent(Identifier identifier, String type, Condition[] conditions, Effect[] effects) {
        this.identifier = identifier;
        this.type = type;
        this.conditions = conditions;
        this.effects = effects;

        if (type.equals(CLIENT_WORLD_TICK)) {
            ClientTickEvents.END_WORLD_TICK.register(this::applyWorldEvent);
        } else if (type.equals(SERVER_WORLD_TICK)) {
            ServerTickEvents.END_WORLD_TICK.register(this::applyWorldEvent);
        }
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    public void applyWorldEvent(World world) {
        for (Condition condition : conditions) {
            if (!condition.testWorld(world)) return;
        }

        for (Effect effect : effects) {
            effect.runWorld(world);
        }
    }

    public static class Serializer implements JsonDeserializer<GlobalEvent>, JsonSerializer<GlobalEvent> {
        @Override
        public GlobalEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = JsonHelper.asObject(json, "global event");
            Identifier identifier = Identifier.tryParse(JsonHelper.getString(object, "identifier"));
            String type = JsonHelper.getString(object, "type", "client_world_tick");
            Condition[] conditions = Event.getConditions(object);
            Effect[] effects = Event.getEffects(object);
            return new GlobalEvent(identifier, type, conditions, effects);
        }

        @Override
        public JsonElement serialize(GlobalEvent src, Type typeOfSrc, JsonSerializationContext context) {
            return null;
        }
    }
}
