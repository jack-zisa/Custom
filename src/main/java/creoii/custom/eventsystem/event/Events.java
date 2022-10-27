package creoii.custom.eventsystem.event;

import creoii.custom.Custom;
import net.minecraft.util.Identifier;

public class Events {
    public static Event EMPTY;
    public static Event RIGHT_CLICK;
    public static Event LEFT_CLICK;
    public static Event STEPPED_ON;
    public static Event PROJECTILE_HIT;
    public static Event PLACE_BLOCK;
    public static Event BREAK_BLOCK;
    public static Event TARGET_DAMAGED;
    public static Event USER_DAMAGED;
    public static Event ENTITY_LANDS;
    public static Event ENTITY_COLLISION;
    public static Event NEIGHBOR_UPDATE;
    public static Event STOPPED_USING;
    public static Event CRAFTED;
    public static Event ITEM_DESPAWN;
    public static Event RANDOM_TICK;
    public static Event STATUS_EFFECT_UPDATE;
    public static Event STATUS_EFFECT_APPLY;
    public static Event STATUS_EFFECT_REMOVE;

    public static void register() {
        EMPTY = Event.register(new Identifier(Custom.NAMESPACE, "empty"), new EmptyEvent());
        RIGHT_CLICK = Event.register(new Identifier(Custom.NAMESPACE, "right_click"), new RightClickEvent());
        LEFT_CLICK = Event.register(new Identifier(Custom.NAMESPACE, "left_click"), new LeftClickEvent());
        STEPPED_ON = Event.register(new Identifier(Custom.NAMESPACE, "stepped_on"), new SteppedOnEvent());
        PROJECTILE_HIT = Event.register(new Identifier(Custom.NAMESPACE, "projectile_hit"), new ProjectileHitEvent());
        PLACE_BLOCK = Event.register(new Identifier(Custom.NAMESPACE, "place_block"), new PlaceBlockEvent());
        BREAK_BLOCK = Event.register(new Identifier(Custom.NAMESPACE, "break_block"), new BreakBlockEvent());
        TARGET_DAMAGED = Event.register(new Identifier(Custom.NAMESPACE, "target_damaged"), new TargetDamagedEvent());
        USER_DAMAGED = Event.register(new Identifier(Custom.NAMESPACE, "user_damaged"), new UserDamagedEvent());
        ENTITY_LANDS = Event.register(new Identifier(Custom.NAMESPACE, "entity_lands"), new EntityLandsEvent());
        ENTITY_COLLISION = Event.register(new Identifier(Custom.NAMESPACE, "entity_collision"), new EntityCollisionEvent());
        NEIGHBOR_UPDATE = Event.register(new Identifier(Custom.NAMESPACE, "neighbor_update"), new NeighborUpdateEvent());
        STOPPED_USING = Event.register(new Identifier(Custom.NAMESPACE, "stopped_using"), new StoppedUsingEvent());
        CRAFTED = Event.register(new Identifier(Custom.NAMESPACE, "crafted"), new CraftedEvent());
        ITEM_DESPAWN = Event.register(new Identifier(Custom.NAMESPACE, "item_despawn"), new ItemDespawnEvent());
        RANDOM_TICK = Event.register(new Identifier(Custom.NAMESPACE, "random_tick"), new RandomTickEvent());
        STATUS_EFFECT_UPDATE = Event.register(new Identifier(Custom.NAMESPACE, "status_effect_update"), new StatusEffectUpdateEvent());
        STATUS_EFFECT_APPLY = Event.register(new Identifier(Custom.NAMESPACE, "status_effect_apply"), new StatusEffectApplyEvent());
        STATUS_EFFECT_REMOVE = Event.register(new Identifier(Custom.NAMESPACE, "status_effect_remove"), new StatusEffectRemoveEvent());
    }
}
