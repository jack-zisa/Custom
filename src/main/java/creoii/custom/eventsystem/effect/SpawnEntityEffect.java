package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.util.CustomJsonHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpawnEntityEffect extends Effect {
    private final EntityType<?> entityType;

    public SpawnEntityEffect(EntityType<?> entityType) {
        super(Effect.SPAWN_ENTITY);
        this.entityType = entityType;
    }

    public static Effect getFromJson(JsonObject object) {
        EntityType<?> entityType = CustomJsonHelper.getEntityType(object, object.get("entity_type").getAsString());
        return new SpawnEntityEffect(entityType);
    }

    @Override
    public void runWorld(World world, BlockPos pos) {
        world.spawnEntity(this.entityType.create(world));
    }

    @Override
    public void runBlock(World world, BlockState state, BlockPos pos, PlayerEntity player, Hand hand) {
        world.spawnEntity(this.entityType.create(world));
    }

    @Override
    public void runItem(World world, Item item, BlockPos pos, PlayerEntity player, Hand hand) {
        world.spawnEntity(this.entityType.create(world));
    }

    @Override
    public void runEntity(Entity entity, PlayerEntity player, Hand hand) {
        entity.getWorld().spawnEntity(this.entityType.create(entity.getWorld()));
    }
}
