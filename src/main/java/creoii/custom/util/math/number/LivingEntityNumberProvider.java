package creoii.custom.util.math.number;

import com.google.gson.JsonObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.JsonHelper;

public class LivingEntityNumberProvider extends NumberProvider {
    private LivingEntity entity;
    private double value;

    public LivingEntityNumberProvider() {
        super("living_entity");
    }

    public LivingEntityNumberProvider setEntity(LivingEntity entity) {
        this.entity = entity;
        return this;
    }

    public LivingEntityNumberProvider withValue(String type) {
        this.value = getValue(type);
        return this;
    }

    public double getValue(String type) {
        return switch (type) {
            case "speed" -> entity.speed;
            case "fall_distance" -> entity.fallDistance;
            case "age" -> entity.age;
            case "step_height" -> entity.stepHeight;
            case "air" -> entity.getAir();
            case "max_air" -> entity.getMaxAir();
            case "fire_ticks" -> entity.getFireTicks();
            case "frozen_ticks" -> entity.getFrozenTicks();
            case "height" -> entity.getHeight();
            case "width" -> entity.getWidth();
            case "x" -> entity.getX();
            case "y" -> entity.getY();
            case "z" -> entity.getZ();
            case "block_x" -> entity.getBlockX();
            case "block_y" -> entity.getBlockY();
            case "block_z" -> entity.getBlockZ();
            case "pitch" -> entity.getPitch();
            case "yaw" -> entity.getYaw();
            case "armor" -> entity.getArmor();
            case "health" -> entity.getHealth();
            case "max_health" -> entity.getMaxHealth();
            case "absorption" -> entity.getAbsorptionAmount();
            case "armor_visibility" -> entity.getArmorVisibility();
            case "roll" -> entity.getRoll();
            case "item_use_time" -> entity.getItemUseTime();
            case "scale" -> entity.getScaleFactor();
            case "stinger_count" -> entity.getStingerCount();
            case "stuck_arrow_count" -> entity.getStuckArrowCount();
            case "attack_damage" -> entity.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
            case "attack_knockback" -> entity.getAttributeValue(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
            case "knockback_resistance" -> entity.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
            case "flying_speed" -> entity.getAttributeValue(EntityAttributes.GENERIC_FLYING_SPEED);
            case "movement_speed" -> entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            case "follow_range" -> entity.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
            case "armor_toughness" -> entity.getAttributeValue(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
            case "attack_speed" -> entity.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED);
            case "luck" -> entity.getAttributeValue(EntityAttributes.GENERIC_LUCK);
            default -> 0d;
        };
    }

    @Override
    public double getValue() {
        return value;
    }

    public LivingEntityNumberProvider getFromJson(JsonObject object) {
        return this.withValue(JsonHelper.getString(object, "value", ""));
    }
}
