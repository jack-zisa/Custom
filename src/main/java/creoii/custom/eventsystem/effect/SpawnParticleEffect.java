package creoii.custom.eventsystem.effect;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.BlockPosParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.WorldParameter;
import creoii.custom.util.json.CustomJsonHelper;
import creoii.custom.util.provider.DoubleProvider;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

public class SpawnParticleEffect extends AbstractEffect {
    private ParticleEntry[] entries;
    private ParticleEffect particle;
    private IntProvider amount;
    private DoubleProvider xVelocity;
    private DoubleProvider yVelocity;
    private DoubleProvider zVelocity;
    private BlockPos offset;
    private boolean important;

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of(EventParameters.WORLD, EventParameters.BLOCK_POS);
    }

    public AbstractEffect getFromJson(JsonObject object) {
        SpawnParticleEffect effect = new SpawnParticleEffect();
        if (object.has("entries")) {
            JsonArray array = object.get("entries").getAsJsonArray();
            ParticleEntry[] entries = new ParticleEntry[array.size()];
            for (int i = 0; i < array.size(); ++i) {
                JsonElement element = array.get(i);
                if (element.isJsonObject()) {
                    JsonObject elementObj = element.getAsJsonObject();
                    entries[i] = new ParticleEntry(
                            (DefaultParticleType) Registry.PARTICLE_TYPE.get(Identifier.tryParse(JsonHelper.getString(elementObj, "particle"))),
                            CustomJsonHelper.getIntProvider(object, "amount", 1),
                            CustomJsonHelper.getDoubleProvider(object, "x_velocity", 0d),
                            CustomJsonHelper.getDoubleProvider(object, "y_velocity", 0d),
                            CustomJsonHelper.getDoubleProvider(object, "z_velocity", 0d),
                            CustomJsonHelper.getBlockPos(object, "offset"),
                            JsonHelper.getBoolean(object, "important", false)
                    );
                }
            }
            effect.entries = entries;
        } else {
            effect.particle = (DefaultParticleType) Registry.PARTICLE_TYPE.get(Identifier.tryParse(JsonHelper.getString(object, "particle")));
            effect.amount = CustomJsonHelper.getIntProvider(object, "amount", 1);
            effect.xVelocity = CustomJsonHelper.getDoubleProvider(object, "x_velocity", 0d);
            effect.yVelocity = CustomJsonHelper.getDoubleProvider(object, "y_velocity", 0d);
            effect.zVelocity = CustomJsonHelper.getDoubleProvider(object, "z_velocity", 0d);
            effect.offset = CustomJsonHelper.getBlockPos(object, "offset");
            effect.important = JsonHelper.getBoolean(object, "important", false);
        }
        return effect;
    }

    @Override
    public void run(List<EventParameter> parameters) {
        WorldParameter worldParameter = (WorldParameter) EventParameter.find(parameters, getModifications(), EventParameters.WORLD);
        if (worldParameter != null) {
            BlockPosParameter blockPosParameter = (BlockPosParameter) EventParameter.find(parameters, getModifications(), EventParameters.BLOCK_POS);
            if (blockPosParameter != null) {
                World world = worldParameter.getWorld();
                BlockPos pos = blockPosParameter.getPos();
                if (entries != null) {
                    for (ParticleEntry entry : entries) {
                        int amount = entry.amount.get(world.getRandom());
                        double x = entry.xVelocity.get(world.getRandom());
                        double y = entry.yVelocity.get(world.getRandom());
                        double z = entry.zVelocity.get(world.getRandom());
                        addParticle(world, pos.add(entry.offset), entry.important, amount, x, y, z);
                    }
                } else {
                    int amount = this.amount.get(world.getRandom());
                    double x = xVelocity.get(world.getRandom());
                    double y = yVelocity.get(world.getRandom());
                    double z = zVelocity.get(world.getRandom());
                    addParticle(world, pos.add(offset), important, amount, x, y, z);
                }
            }
        }
    }

    private void addParticle(World world, BlockPos pos, boolean important, int amount, double x, double y, double z) {
        if (important) {
            for (int i = 0; i < amount; ++i) {
                world.addImportantParticle(particle, .5d + pos.getX(), .5d + pos.getY(), .5d + pos.getZ(), x, y, z);
            }
        } else {
            for (int i = 0; i < amount; ++i) {
                world.addParticle(particle, .5d + pos.getX(), .5d + pos.getY(), .5d + pos.getZ(), x, y, z);
            }
        }
    }

    public record ParticleEntry(ParticleEffect particle, IntProvider amount, DoubleProvider xVelocity, DoubleProvider yVelocity, DoubleProvider zVelocity, BlockPos offset, boolean important) {}
}
