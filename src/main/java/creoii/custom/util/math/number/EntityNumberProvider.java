package creoii.custom.util.math.number;

import com.google.gson.JsonObject;
import net.minecraft.entity.Entity;
import net.minecraft.util.JsonHelper;

public class EntityNumberProvider extends NumberProvider {
    private Entity entity;
    private double value;

    public EntityNumberProvider() {
        super("entity");
    }

    public EntityNumberProvider setEntity(Entity entity) {
        this.entity = entity;
        return this;
    }

    public EntityNumberProvider withValue(String type) {
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
            default -> 0d;
        };
    }

    @Override
    public double getValue() {
        return value;
    }

    public EntityNumberProvider getFromJson(JsonObject object) {
        return this.withValue(JsonHelper.getString(object, "value", ""));
    }
}
