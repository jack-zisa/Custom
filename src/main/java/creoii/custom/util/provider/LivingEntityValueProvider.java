package creoii.custom.util.provider;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import creoii.custom.Custom;
import creoii.custom.util.GeneralUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.Map;

public class LivingEntityValueProvider extends ValueProvider<Object> {
    private static final Map<String, Primitive> VALUES = new ImmutableMap.Builder<String, Primitive>()

            .build();

    private LivingEntity livingEntity;
    private Object value;

    public LivingEntityValueProvider setLivingEntity(LivingEntity livingEntity) {
        this.livingEntity = livingEntity;
        return this;
    }

    public LivingEntityValueProvider withValue(Object value) {
        this.value = value;
        return this;
    }

    public double getDoubleValue(String type) {
        return switch (type) {
            case "speed" -> livingEntity.speed;
            case "fall_distance" -> livingEntity.fallDistance;
            case "age" -> livingEntity.age;
            case "step_height" -> livingEntity.stepHeight;
            case "air" -> livingEntity.getAir();
            case "max_air" -> livingEntity.getMaxAir();
            case "fire_ticks" -> livingEntity.getFireTicks();
            case "frozen_ticks" -> livingEntity.getFrozenTicks();
            case "height" -> livingEntity.getHeight();
            case "width" -> livingEntity.getWidth();
            case "x" -> livingEntity.getX();
            case "y" -> livingEntity.getY();
            case "z" -> livingEntity.getZ();
            case "block_x" -> livingEntity.getBlockX();
            case "block_y" -> livingEntity.getBlockY();
            case "block_z" -> livingEntity.getBlockZ();
            case "pitch" -> livingEntity.getPitch();
            case "yaw" -> livingEntity.getYaw();
            case "armor" -> livingEntity.getArmor();
            case "health" -> livingEntity.getHealth();
            case "max_health" -> livingEntity.getMaxHealth();
            case "absorption" -> livingEntity.getAbsorptionAmount();
            case "armor_visibility" -> livingEntity.getArmorVisibility();
            case "roll" -> livingEntity.getRoll();
            case "item_use_time" -> livingEntity.getItemUseTime();
            case "scale" -> livingEntity.getScaleFactor();
            case "stinger_count" -> livingEntity.getStingerCount();
            case "stuck_arrow_count" -> livingEntity.getStuckArrowCount();
            case "attack_damage" -> livingEntity.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
            case "attack_knockback" -> livingEntity.getAttributeValue(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
            case "knockback_resistance" -> livingEntity.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
            case "flying_speed" -> livingEntity.getAttributeValue(EntityAttributes.GENERIC_FLYING_SPEED);
            case "movement_speed" -> livingEntity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            case "follow_range" -> livingEntity.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
            case "armor_toughness" -> livingEntity.getAttributeValue(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
            case "attack_speed" -> livingEntity.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED);
            case "luck" -> livingEntity.getAttributeValue(EntityAttributes.GENERIC_LUCK);
            case "sound_pitch" -> livingEntity.getSoundPitch();
            default -> 0d;
        };
    }

    public boolean getBooleanValue(String type) {
        return switch (type) {
            case "swimming" -> livingEntity.isSwimming();
            case "sprinting" -> livingEntity.isSprinting();
            case "sneaking" -> livingEntity.isSneaking();
            case "on_fire" -> livingEntity.isOnFire();
            case "alive" -> livingEntity.isAlive();
            case "attackable" -> livingEntity.isAttackable();
            case "collidable" -> livingEntity.isCollidable();
            case "crawling" -> livingEntity.isCrawling();
            case "fire_immUne" -> livingEntity.isFireImmune();
            case "frozen" -> livingEntity.isFrozen();
            case "glowing" -> livingEntity.isGlowing();
            case "on_ground" -> livingEntity.isOnGround();
            case "explosion_immune" -> livingEntity.isImmuneToExplosion();
            case "in_lava" -> livingEntity.isInLava();
            case "custom_name_visible" -> livingEntity.isCustomNameVisible();
            case "invisible" -> livingEntity.isInvisible();
            case "invulnerable" -> livingEntity.isInvulnerable();
            case "pushable" -> livingEntity.isPushable();
            case "pushed_by_fluids" -> livingEntity.isPushedByFluids();
            case "silent" -> livingEntity.isSilent();
            case "spectator" -> livingEntity.isSpectator();
            case "wet" -> livingEntity.isWet();
            case "in_powder_snow" -> livingEntity.inPowderSnow;
            case "has_vehicle" -> livingEntity.hasVehicle();
            case "has_passengers" -> livingEntity.hasPassengers();
            case "can_freeze" -> livingEntity.canFreeze();
            case "can_use_portals" -> livingEntity.canUsePortals();
            case "no_gravity" -> livingEntity.hasNoGravity();
            case "has_player_rider" -> livingEntity.hasPlayerRider();
            case "touching_water" -> livingEntity.isTouchingWater();
            case "touching_water_or_rain" -> livingEntity.isTouchingWaterOrRain();
            case "player" -> livingEntity.isPlayer();
            case "affected_by_splash_potions" -> livingEntity.isAffectedBySplashPotions();
            case "baby" -> livingEntity.isBaby();
            case "blocking" -> livingEntity.isBlocking();
            case "climbing" -> livingEntity.isClimbing();
            case "dead" -> livingEntity.isDead();
            case "holding_onto_ladder" -> livingEntity.isHoldingOntoLadder();
            case "sleeping" -> livingEntity.isSleeping();
            case "undead" -> livingEntity.isUndead();
            case "using_item" -> livingEntity.isUsingItem();
            case "using_riptide" -> livingEntity.isUsingRiptide();
            case "breathe_in_water" -> livingEntity.canBreatheInWater();
            case "can_take_damage" -> livingEntity.canTakeDamage();
            default -> true;
        };
    }

    public String getStringVal(String type) {
        switch (type) {
            case "held_item_name" -> {
                return livingEntity.getStackInHand(livingEntity.getActiveHand()).getName().getString();
            }
            case "recent_damage_source" -> {
                if (livingEntity.getDamageTracker().getMostRecentDamage() != null)
                    return livingEntity.getDamageTracker().getMostRecentDamage().getDamageSource().getName();
            }
            case "custom_name" -> {
                if (livingEntity.hasCustomName())
                    return livingEntity.getCustomName().getString();
            }
            case "display_name" -> {
                return livingEntity.getDisplayName().getString();
            }
            case "uuid" -> {
                return livingEntity.getUuid().toString();
            }
            case "entity_group" -> {
                return livingEntity.getGroup().toString();
            }
            case "loot_table" -> {
                return livingEntity.getLootTable().toString();
            }
            case "piston_behavior" -> {
                return livingEntity.getPistonBehavior().toString();
            }
        }
        return "";
    }

    @SuppressWarnings("deprecation")
    public Vec3d getBlockPos(String type) {
        switch (type) {
            case "block_pos" -> {
                return GeneralUtil.asVec3d(livingEntity.getBlockPos());
            }
            case "climbing_pos" -> {
                if (livingEntity.getClimbingPos().isPresent())
                    return GeneralUtil.asVec3d(livingEntity.getClimbingPos().get());
            }
            case "stepping_pos" -> {
                return GeneralUtil.asVec3d(livingEntity.getSteppingPos());
            }
            case "sleeping_pos" -> {
                if (livingEntity.getSleepingPosition().isPresent())
                    return GeneralUtil.asVec3d(livingEntity.getSleepingPosition().get());
            }
            case "landing_pos" -> {
                return GeneralUtil.asVec3d(livingEntity.getLandingPos());
            }
            case "pos" -> {
                return livingEntity.getPos();
            }
            case "eye_pos" -> {
                return livingEntity.getEyePos();
            }
            case "synced_pos" -> {
                return livingEntity.getSyncedPos();
            }
        }
        return Vec3d.ZERO;
    }

    public Direction getDirection(String type) {
        return switch (type) {
            case "horizontal_facing" -> livingEntity.getHorizontalFacing();
            case "sleeping_direction" -> livingEntity.getSleepingDirection();
            case "movement_direction" -> livingEntity.getMovementDirection();
            default -> Direction.UP;
        };
    }

    @Override
    public Object getValue() {
        return value;
    }

    public static LivingEntityValueProvider getFromJson(JsonObject object) {
        if (object.has("value")) {
            ValueProvider<?> provider = Custom.VALUE_PROVIDER.get(Identifier.tryParse(object.get("type").getAsString()));
            if (provider instanceof LivingEntityValueProvider entityValueProvider) {
                String value = object.get("value").getAsString();
                switch (VALUES.get(value)) {
                    case DOUBLE -> {
                        return entityValueProvider.withValue(entityValueProvider.getDoubleValue(value));
                    }
                    case STRING -> {
                        return entityValueProvider.withValue(entityValueProvider.getStringVal(value));
                    }
                    case BOOLEAN -> {
                        return entityValueProvider.withValue(entityValueProvider.getBooleanValue(value));
                    }
                    case BLOCK_POS -> {
                        return entityValueProvider.withValue(entityValueProvider.getBlockPos(value));
                    }
                    case DIRECTION -> {
                        return entityValueProvider.withValue(entityValueProvider.getDirection(value));
                    }
                }
            }
        }
        throw new JsonSyntaxException("Provider is missing \"value\"");
    }
}
