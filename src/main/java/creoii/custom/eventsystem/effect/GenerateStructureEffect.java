package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.util.json.CustomJsonHelper;
import net.minecraft.block.BlockState;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.World;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.structure.Structure;

import java.util.function.Supplier;

public class GenerateStructureEffect extends Effect {
    private final RegistryEntry<Structure> structure;
    private final BlockPos offset;
    private final boolean affectTarget;

    public GenerateStructureEffect(RegistryEntry<Structure> structure, BlockPos offset, boolean affectTarget) {
        super(Effect.GENERATE_STRUCTURE);
        this.structure = structure;
        this.offset = offset;
        this.affectTarget = affectTarget;
    }

    public static Effect getFromJson(JsonObject object) {
        Structure structure = BuiltinRegistries.STRUCTURE.get(Identifier.tryParse(object.get("structure").getAsString()));
        BlockPos offset = CustomJsonHelper.getBlockPos(object, "offset");
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target", false);
        return new GenerateStructureEffect(RegistryEntry.of(structure), offset, affectTarget);
    }

    private void run(World world, BlockPos pos) {
        if (!world.isClient && structure.hasKeyAndValue()) {
            ServerWorld serverWorld = (ServerWorld) world;
            ChunkGenerator chunkGenerator = serverWorld.getChunkManager().getChunkGenerator();
            StructureStart structureStart = structure.value().createStructureStart(world.getRegistryManager(), chunkGenerator, chunkGenerator.getBiomeSource(), serverWorld.getChunkManager().getNoiseConfig(), serverWorld.getStructureTemplateManager(), serverWorld.getSeed(), new ChunkPos(pos.add(offset)), 0, serverWorld, (registryEntry) -> true);
            if (structureStart.hasChildren()) {
                BlockBox blockBox = structureStart.getBoundingBox();
                ChunkPos chunkPos = new ChunkPos(ChunkSectionPos.getSectionCoord(blockBox.getMinX()), ChunkSectionPos.getSectionCoord(blockBox.getMinZ()));
                ChunkPos chunkPos2 = new ChunkPos(ChunkSectionPos.getSectionCoord(blockBox.getMaxX()), ChunkSectionPos.getSectionCoord(blockBox.getMaxZ()));
                if (ChunkPos.stream(chunkPos, chunkPos2).anyMatch((chunkPos3) -> !serverWorld.canSetBlock(chunkPos3.getStartPos())))
                ChunkPos.stream(chunkPos, chunkPos2).forEach((chunkPosx) -> {
                    structureStart.place(serverWorld, serverWorld.getStructureAccessor(), chunkGenerator, serverWorld.getRandom(), new BlockBox(chunkPosx.getStartX(), serverWorld.getBottomY(), chunkPosx.getStartZ(), chunkPosx.getEndX(), serverWorld.getTopY(), chunkPosx.getEndZ()), chunkPosx);
                });
            }
        }
    }

    @Override
    public void runBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        run(world, pos);
    }

    @Override
    public void runItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        run(world, pos);
    }

    @Override
    public void runEntity(Entity entity, PlayerEntity player, Hand hand) {
        run(entity.getWorld(), entity.getBlockPos());
    }

    @Override
    public void runEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        run(user.getWorld(), affectTarget ? target.getBlockPos() : user.getBlockPos());
    }

    @Override
    public void runStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        run(entity.getWorld(), entity.getBlockPos());
    }

    @Override
    public void runWorld(World world) { }
}
