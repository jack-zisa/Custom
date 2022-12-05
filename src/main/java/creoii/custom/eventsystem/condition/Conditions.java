package creoii.custom.eventsystem.condition;

import creoii.custom.Custom;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class Conditions {
    public static Condition EMPTY;
    public static Condition COMPOSITE;
    public static Condition NEGATE;
    public static Condition WEIGHTED_LIST;
    public static Condition WORLD_IS_DAY;
    public static Condition WORLD_IS_NIGHT;
    public static Condition ENTITY_SNEAKING;
    public static Condition ENTITY_SPRINTING;
    public static Condition ENTITY_SWIMMING;
    public static Condition ENTITY_CRAWLING;
    public static Condition ENTITY_JUMPING;

    public static void register() {
        EMPTY = Condition.register(new Identifier(Custom.NAMESPACE, "empty"), new EmptyCondition());
        COMPOSITE = Condition.register(new Identifier(Custom.NAMESPACE, "composite"), new CompositeCondition());
        NEGATE = Condition.register(new Identifier(Custom.NAMESPACE, "negate"), new NegateCondition());
        WEIGHTED_LIST = Condition.register(new Identifier(Custom.NAMESPACE, "weighted_list"), new WeightedListCondition());
        WORLD_IS_DAY = Condition.register(new Identifier(Custom.NAMESPACE, "world_is_day"), new WorldCondition(World::isDay));
        WORLD_IS_NIGHT = Condition.register(new Identifier(Custom.NAMESPACE, "world_is_night"), new WorldCondition(World::isNight));
        ENTITY_SNEAKING = Condition.register(new Identifier(Custom.NAMESPACE, "entity_sneaking"), new EntityCondition(Entity::isSneaking));
        ENTITY_SPRINTING = Condition.register(new Identifier(Custom.NAMESPACE, "entity_sprinting"), new EntityCondition(Entity::isSprinting));
        ENTITY_SWIMMING = Condition.register(new Identifier(Custom.NAMESPACE, "entity_swimming"), new EntityCondition(Entity::isSwimming));
        ENTITY_CRAWLING = Condition.register(new Identifier(Custom.NAMESPACE, "entity_crawling"), new EntityCondition(Entity::isCrawling));
        ENTITY_JUMPING = Condition.register(new Identifier(Custom.NAMESPACE, "entity_jumping"), new LivingEntityCondition(livingEntity -> livingEntity.jumping));
    }
}
