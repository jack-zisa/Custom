package creoii.custom.util.math.bool;

import com.google.gson.JsonObject;
import net.minecraft.entity.Entity;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

public class EntityBooleanProvider extends BooleanProvider {
    private Entity entity;
    private Boolean value;

    public EntityBooleanProvider() {
        super("entity");
    }

    public EntityBooleanProvider setEntity(Entity entity) {
        this.entity = entity;
        return this;
    }

    public EntityBooleanProvider withValue(String type) {
        this.value = getValue(type);
        return this;
    }

    public Boolean getValue(String type) {
        return switch (type) {
            case "swimming" -> entity.isSwimming();
            case "sprinting" -> entity.isSprinting();
            case "sneaking" -> entity.isSneaking();
            case "on_fire" -> entity.isOnFire();
            case "alive" -> entity.isAlive();
            case "attackable" -> entity.isAttackable();
            case "collidable" -> entity.isCollidable();
            case "crawling" -> entity.isCrawling();
            case "fire_immune" -> entity.isFireImmune();
            case "frozen" -> entity.isFrozen();
            case "glowing" -> entity.isGlowing();
            case "on_ground" -> entity.isOnGround();
            case "explosion_immune" -> entity.isImmuneToExplosion();
            case "in_lava" -> entity.isInLava();
            case "custom_name_visible" -> entity.isCustomNameVisible();
            case "invisible" -> entity.isInvisible();
            case "invulnerable" -> entity.isInvulnerable();
            case "pushable" -> entity.isPushable();
            case "pushed_by_fluids" -> entity.isPushedByFluids();
            case "silent" -> entity.isSilent();
            case "spectator" -> entity.isSpectator();
            case "wet" -> entity.isWet();
            case "in_powder_snow" -> entity.inPowderSnow;
            case "has_vehicle" -> entity.hasVehicle();
            case "has_passengers" -> entity.hasPassengers();
            case "can_freeze" -> entity.canFreeze();
            case "can_use_portals" -> entity.canUsePortals();
            case "no_gravity" -> entity.hasNoGravity();
            case "has_player_rider" -> entity.hasPlayerRider();
            case "touching_water" -> entity.isTouchingWater();
            case "touching_water_or_rain" -> entity.isTouchingWaterOrRain();
            case "player" -> entity.isPlayer();
            default -> true;
        };
    }

    @Override
    public boolean getValue() {
        return value;
    }

    public EntityBooleanProvider getFromJson(JsonObject object) {
        return this.withValue(JsonHelper.getString(object, "value", ""));
    }
}
