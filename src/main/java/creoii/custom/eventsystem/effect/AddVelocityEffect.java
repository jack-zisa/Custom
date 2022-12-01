package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.EntityParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.WorldParameter;
import creoii.custom.util.json.CustomJsonHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class AddVelocityEffect extends AbstractEffect {
    private double power;
    private float pitch;
    private float yaw;
    private boolean negate;
    private Type type;

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of(EventParameters.WORLD, EventParameters.ENTITY);
    }

    @Override
    public AbstractEffect getFromJson(JsonObject object) {
        AddVelocityEffect effect = new AddVelocityEffect();
        effect.power = JsonHelper.getDouble(object, "power", 0d);
        effect.pitch = JsonHelper.getFloat(object, "pitch", 0f);
        effect.yaw = JsonHelper.getFloat(object, "yaw", 0f);
        effect.negate = JsonHelper.getBoolean(object, "negate", false);
        effect.type = Type.valueOf(JsonHelper.getString(object, "velocity_type"));
        return effect;
    }

    @Override
    public void run(List<EventParameter> parameters) {
        WorldParameter worldParameter = (WorldParameter) EventParameter.find(parameters, getModifications(), EventParameters.WORLD);
        if (worldParameter != null) {
            EntityParameter entityParameter = (EntityParameter) EventParameter.find(parameters, getModifications(), EventParameters.ENTITY);
            if (entityParameter != null) {
                World world = worldParameter.getWorld();
                if (!world.isClient) {
                    Entity entity = entityParameter.getEntity();
                    Vec3d vec3d = entity.getRotationVector(pitch, yaw);
                    if (negate) vec3d = vec3d.negate();
                    switch (type) {
                        case SET -> entity.setVelocity(vec3d);
                        case ADD -> entity.setVelocity(entity.getVelocity().add(vec3d));
                        case MULTIPLY -> entity.setVelocity(entity.getVelocity().multiply(vec3d));
                    }
                }
            }
        }
    }

    public enum Type {
        ADD,
        MULTIPLY,
        SET
    }
}
