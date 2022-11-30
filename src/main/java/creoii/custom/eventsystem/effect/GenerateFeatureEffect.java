package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.BlockPosParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.WorldParameter;
import creoii.custom.util.json.CustomJsonHelper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.List;
import java.util.function.Supplier;

public class GenerateFeatureEffect extends AbstractEffect {
    private Supplier<RegistryEntry<ConfiguredFeature<?, ?>>> configuredFeature;
    private BlockPos offset;
    private boolean removeBlock;

    @Override
    public List<EventParameter> getParameters() {
        return List.of(EventParameters.WORLD, EventParameters.BLOCK_POS);
    }

    public AbstractEffect getFromJson(JsonObject object) {
        GenerateFeatureEffect effect = new GenerateFeatureEffect();
        effect.configuredFeature = () -> RegistryEntry.of(BuiltinRegistries.CONFIGURED_FEATURE.get(Identifier.tryParse(object.get("feature").getAsString())));
        effect.offset = CustomJsonHelper.getBlockPos(object, "offset");
        effect.removeBlock = JsonHelper.getBoolean(object, "remove_block", true);
        return effect;
    }

    public void run(List<EventParameter> parameters) {
        WorldParameter worldParameter = (WorldParameter) EventParameter.find(parameters, getModifications(), EventParameters.WORLD);
        if (worldParameter != null) {
            World world = worldParameter.getWorld();
            BlockPosParameter blockPosParameter = (BlockPosParameter) EventParameter.find(parameters, getModifications(), EventParameters.BLOCK_POS);
            if (blockPosParameter != null) {
                if (!world.isClient && configuredFeature.get().hasKeyAndValue()) {
                    if (removeBlock) world.removeBlock(blockPosParameter.getPos(), false);
                    configuredFeature.get().value().generate((ServerWorld) world, ((ServerWorld) world).getChunkManager().getChunkGenerator(), world.getRandom(), blockPosParameter.getPos().add(offset));
                }
            }
        }
    }
}
