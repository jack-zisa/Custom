package creoii.custom.registry;

import creoii.custom.Custom;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class AttributeRegistry {
    public static final EntityAttribute GENERIC_GRAVITY = new ClampedEntityAttribute("attribute.name.generic.gravity", .08d, -8d, 8d).setTracked(true);
    public static final EntityAttribute GENERIC_SWIM_SPEED = new ClampedEntityAttribute("attribute.name.generic.swim_speed", 1d, 0d, 1024d).setTracked(true);
    public static final EntityAttribute GENERIC_JUMP_HEIGHT = new ClampedEntityAttribute("attribute.name.generic.jump_height", 1d, 0d, 1024d).setTracked(true);

    public static void register() {
        Registry.register(Registry.ATTRIBUTE, new Identifier(Custom.NAMESPACE, "generic.gravity"), GENERIC_GRAVITY);
        Registry.register(Registry.ATTRIBUTE, new Identifier(Custom.NAMESPACE, "generic.swim_speed"), GENERIC_SWIM_SPEED);
        Registry.register(Registry.ATTRIBUTE, new Identifier(Custom.NAMESPACE, "generic.jump_height"), GENERIC_JUMP_HEIGHT);
    }
}
