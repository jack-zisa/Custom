package creoii.custom.util.math.bool;

import com.google.gson.JsonObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.JsonHelper;

public class LivingEntityBooleanProvider extends BooleanProvider {
    private LivingEntity entity;
    private Boolean value;

    public LivingEntityBooleanProvider() {
        super("living_entity");
    }

    public LivingEntityBooleanProvider setEntity(LivingEntity entity) {
        this.entity = entity;
        return this;
    }

    public LivingEntityBooleanProvider withValue(String type) {
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
            case "fire_immUne" -> entity.isFireImmune();
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
            case "affected_by_splash_potions" -> entity.isAffectedBySplashPotions();
            case "baby" -> entity.isBaby();
            case "blocking" -> entity.isBlocking();
            case "climbing" -> entity.isClimbing();
            case "dead" -> entity.isDead();
            case "holding_onto_ladder" -> entity.isHoldingOntoLadder();
            case "sleeping" -> entity.isSleeping();
            case "undead" -> entity.isUndead();
            case "using_item" -> entity.isUsingItem();
            case "using_riptide" -> entity.isUsingRiptide();
            case "breathe_in_water" -> entity.canBreatheInWater();
            case "can_take_damage" -> entity.canTakeDamage();
            default -> true;
        };
    }

    @Override
    public boolean getValue() {
        return value;
    }

    public LivingEntityBooleanProvider getFromJson(JsonObject object) {
        return this.withValue(JsonHelper.getString(object, "value", ""));
    }
}
