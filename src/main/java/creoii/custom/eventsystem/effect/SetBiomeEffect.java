package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.BlockPosParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.WorldParameter;
import creoii.custom.util.json.CustomJsonHelper;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

import java.util.List;

public class SetBiomeEffect extends AbstractEffect {
    private Identifier biomeId;

    @Override
    public AbstractEffect getFromJson(JsonObject object) {
        SetBiomeEffect effect = new SetBiomeEffect();
        effect.biomeId = Identifier.tryParse(CustomJsonHelper.getString(object, "biome", "biome_id"));
        return effect;
    }

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of(EventParameters.WORLD, EventParameters.BLOCK_POS);
    }

    @Override
    public void run(List<EventParameter> parameters) {
        WorldParameter worldParameter = (WorldParameter) EventParameter.find(parameters, EventParameters.WORLD);
        if (worldParameter != null) {
            BlockPosParameter blockPosParameter = (BlockPosParameter) EventParameter.find(parameters, EventParameters.BLOCK_POS);
            if (blockPosParameter != null) {
                World world = worldParameter.getWorld();
                if (!world.isClient) {
                    ServerWorld serverWorld = (ServerWorld) world;
                    Biome biome = serverWorld.getRegistryManager().get(RegistryKeys.BIOME).get(biomeId);
                    if (biome != null) {
                        Chunk chunk = world.getChunk(blockPosParameter.getPos());
                        chunk.populateBiomes((x, y, z, noise) -> RegistryEntry.of(biome), serverWorld.getChunkManager().getNoiseConfig().getMultiNoiseSampler());
                        chunk.setNeedsSaving(true);
                        serverWorld.getChunkManager().threadedAnvilChunkStorage.sendChunkPacketToWatchingPlayers(chunk);
                    }
                }
            }
        }
    }
}
