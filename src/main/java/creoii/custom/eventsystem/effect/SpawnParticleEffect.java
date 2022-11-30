package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.BlockPosParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.WorldParameter;
import creoii.custom.util.json.CustomJsonHelper;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

public class SpawnParticleEffect extends AbstractEffect {
    private ParticleEffect particle;
    private int amount;
    private double xVelocity;
    private double yVelocity;
    private double zVelocity;
    private BlockPos offset;
    private boolean important;

    @Override
    public List<EventParameter> getParameters() {
        return List.of(EventParameters.WORLD, EventParameters.BLOCK_POS);
    }

    public AbstractEffect getFromJson(JsonObject object) {
        SpawnParticleEffect effect = new SpawnParticleEffect();
        effect.particle = (DefaultParticleType) Registry.PARTICLE_TYPE.get(Identifier.tryParse(JsonHelper.getString(object, "particle")));
        effect.amount = JsonHelper.getInt(object, "amount", 1);
        effect.xVelocity = JsonHelper.getDouble(object, "x_velocity", 0d);
        effect.yVelocity = JsonHelper.getDouble(object, "y_velocity", 0d);
        effect.zVelocity = JsonHelper.getDouble(object, "z_velocity", 0d);
        effect.offset = CustomJsonHelper.getBlockPos(object, "offset");
        effect.important = JsonHelper.getBoolean(object, "important", false);
        return effect;
    }

    @Override
    public void run(List<EventParameter> parameters) {
        WorldParameter worldParameter = (WorldParameter) EventParameter.find(parameters, EventParameters.WORLD);
        if (worldParameter != null) {
            BlockPosParameter blockPosParameter = (BlockPosParameter) EventParameter.find(parameters, EventParameters.BLOCK_POS);
            if (blockPosParameter != null) {
                World world = worldParameter.getWorld();
                BlockPos pos = blockPosParameter.getPos();
                if (important) {
                    for (int i = 0; i < amount; ++i) {
                        world.addImportantParticle(particle, .5d + pos.getX() + offset.getX(), .5d + pos.getY() + offset.getY(), .5d + pos.getZ() + offset.getZ(), xVelocity, yVelocity, zVelocity);
                    }
                } else {
                    for (int i = 0; i < amount; ++i) {
                        world.addParticle(particle, .5d + pos.getX() + offset.getX(), .5d + pos.getY() + offset.getY(), .5d + pos.getZ() + offset.getZ(), xVelocity, yVelocity, zVelocity);
                    }
                }
            }
        }
    }
}
