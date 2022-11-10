package creoii.custom.util.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.util.JsonHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class JsonHelper2 {
    // doesnt work for some reason
    public static String getString(JsonObject object, String[] elements, @Nullable String defaultStr) {
        for (String element : elements) {
            String str = null;
            if (object.has(element)) {
                str = object.get(element).getAsString();
            }

            if (str != null) {
                return str;
            }
        }

        if (defaultStr != null) {
            return defaultStr;
        }
        throw new JsonSyntaxException("Missing " + Arrays.toString(elements) + ", expected to find a string");
    }
}
