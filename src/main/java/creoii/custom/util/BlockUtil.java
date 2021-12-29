package creoii.custom.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;

public class BlockUtil {
    public static void bounce(Entity entity, float multiplier) {
        Vec3d vec3d = entity.getVelocity();
        if (vec3d.y < 0d) {
            double d = entity instanceof LivingEntity ? 1f : .8f;
            entity.setVelocity(vec3d.x, -vec3d.y * d * multiplier, vec3d.z);
        }
    }
}
