package creoii.custom.util;

import com.google.gson.JsonObject;
import net.minecraft.util.JsonHelper;

public class JsonUtil {
    public static <T extends Boolean> boolean getOrDefault(JsonObject object, String name, T def) {
        return object.has(name) ? JsonHelper.getBoolean(object, name) : def;
    }

    public static <T extends Float> float getOrDefault(JsonObject object, String name, T def) {
        return object.has(name) ? JsonHelper.getFloat(object, name) : def;
    }

    public static <T extends Integer> int getOrDefault(JsonObject object, String name, T def) {
        return object.has(name) ? JsonHelper.getInt(object, name) : def;
    }

    public static <T extends String> String getOrDefault(JsonObject object, String name, T def) {
        return object.has(name) ? JsonHelper.getString(object, name) : def;
    }
}
