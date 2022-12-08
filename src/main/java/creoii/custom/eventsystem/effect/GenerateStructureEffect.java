package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.BlockPosParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.WorldParameter;
import creoii.custom.util.json.CustomJsonHelper;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.structure.Structure;

import java.util.List;

public class GenerateStructureEffect extends AbstractEffect {
    private Identifier structureId;
    private BlockPos offset;

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of(EventParameters.WORLD, EventParameters.BLOCK_POS);
    }

    public AbstractEffect getFromJson(JsonObject object) {
        GenerateStructureEffect effect = new GenerateStructureEffect();
        effect.structureId = Identifier.tryParse(object.get("structure").getAsString());
        effect.offset = CustomJsonHelper.getBlockPos(object, "offset");
        return effect;
    }

    @Override
    public void run(List<EventParameter> parameters) {
        WorldParameter worldParameter = (WorldParameter) EventParameter.find(parameters, getModifications(), EventParameters.WORLD);
        if (worldParameter != null) {
            World world = worldParameter.getWorld();
            if (!world.isClient) {
                BlockPosParameter blockPosParameter = (BlockPosParameter) EventParameter.find(parameters, getModifications(), EventParameters.BLOCK_POS);
                if (blockPosParameter != null) {
                    BlockPos pos = blockPosParameter.getPos();
                    ServerWorld serverWorld = (ServerWorld) world;
                    Structure structure = serverWorld.getRegistryManager().get(RegistryKeys.STRUCTURE).get(structureId);
                    if (structure != null) {
                        ChunkGenerator chunkGenerator = serverWorld.getChunkManager().getChunkGenerator();
                        StructureStart structureStart = structure.createStructureStart(world.getRegistryManager(), chunkGenerator, chunkGenerator.getBiomeSource(), serverWorld.getChunkManager().getNoiseConfig(), serverWorld.getStructureTemplateManager(), serverWorld.getSeed(), new ChunkPos(pos.add(offset)), 0, serverWorld, (registryEntry) -> true);
                        if (structureStart.hasChildren()) {
                            BlockBox blockBox = structureStart.getBoundingBox();
                            ChunkPos chunkPos = new ChunkPos(ChunkSectionPos.getSectionCoord(blockBox.getMinX()), ChunkSectionPos.getSectionCoord(blockBox.getMinZ()));
                            ChunkPos chunkPos2 = new ChunkPos(ChunkSectionPos.getSectionCoord(blockBox.getMaxX()), ChunkSectionPos.getSectionCoord(blockBox.getMaxZ()));
                            if (ChunkPos.stream(chunkPos, chunkPos2).anyMatch(chunkPos3 -> serverWorld.canSetBlock(pos))) {
                                ChunkPos.stream(chunkPos, chunkPos2).forEach(chunkPosx -> structureStart.place(serverWorld, serverWorld.getStructureAccessor(), chunkGenerator, serverWorld.getRandom(), new BlockBox(chunkPosx.getStartX(), serverWorld.getBottomY(), chunkPosx.getStartZ(), chunkPosx.getEndX(), serverWorld.getTopY(), chunkPosx.getEndZ()), chunkPosx));
                            }
                        }
                    }
                }
            }
        }
    }
}
