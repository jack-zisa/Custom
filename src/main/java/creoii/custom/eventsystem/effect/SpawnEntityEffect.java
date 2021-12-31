package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class SpawnEntityEffect extends Effect {
    private final EntityType<?> entityType;

    public SpawnEntityEffect(EntityType<?> entityType) {
        super(Effect.SPAWN_ENTITY);
        this.entityType = entityType;
    }

    public static Effect getFromJson(JsonObject object) {
        EntityType<?> entityType = Registry.ENTITY_TYPE.get(Identifier.tryParse(object.get("entity_type").getAsString()));
        return new SpawnEntityEffect(entityType);
    }

    @Override
    public void runWorld(World world, BlockPos pos) {
        if (!world.isClient) {
            world.spawnEntity(this.entityType.create((ServerWorld) world, null, null, null, pos, SpawnReason.NATURAL, false, false));
        }
    }

    @Override
    public void runBlock(World world, BlockState state, BlockPos pos, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            world.spawnEntity(this.entityType.create((ServerWorld) world, null, null, player, pos, SpawnReason.NATURAL, false, false));
        }
    }

    @Override
    public void runItem(World world, Item item, BlockPos pos, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            world.spawnEntity(this.entityType.create((ServerWorld) world, null, null, player, pos, SpawnReason.NATURAL, false, false));
        }
    }

    @Override
    public void runEntity(Entity entity, PlayerEntity player, Hand hand) {
        World world = entity.getWorld();
        if (!world.isClient) {
            world.spawnEntity(this.entityType.create((ServerWorld) world, null, null, player, entity.getBlockPos(), SpawnReason.NATURAL, false, false));
        }
    }

    @Override
    public void runEnchantment(Entity user, Entity target, int level) {
        World world = target.getWorld();
        if (!world.isClient) {
            world.spawnEntity(this.entityType.create((ServerWorld) world, null, null, null, target.getBlockPos(), SpawnReason.NATURAL, false, false));
        }
    }
}
