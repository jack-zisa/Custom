package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.util.CustomJsonHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
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
        entity.getWorld().spawnEntity(this.entityType.create(entity.getWorld()));
    }
}
