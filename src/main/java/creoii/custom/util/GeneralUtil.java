package creoii.custom.util;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import org.apache.commons.lang3.StringUtils;

public class GeneralUtil {
    /**
     * Convert {@param vec3i} into a Vec3d
     */
    public static Vec3d asVec3d(Vec3i vec3i) {
        return new Vec3d(vec3i.getX(), vec3i.getY(), vec3i.getZ());
    }

    /**
     * Capitalize every letter after any character matching {@param after}
     */
    public static String capitalizeAfterAll(String str, char after) {
        str = StringUtils.capitalize(str);
        StringBuilder builder = new StringBuilder();
        boolean capitalize = false;
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) == after) {
                builder.append(str.charAt(i));
                capitalize = true;
            } else if (capitalize) {
                builder.append(Character.toUpperCase(str.charAt(i)));
                capitalize = false;
            } else {
                builder.append(str.charAt(i));
            }
        }
        return builder.toString();
    }
}
