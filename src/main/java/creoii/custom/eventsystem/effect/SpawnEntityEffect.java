package creoii.custom.eventsystem.effect;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import creoii.custom.util.CustomJsonHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class SpawnEntityEffect extends Effect {
    private final EntityType<?> entityType;
    private final BlockPos offset;
    private final EnchantmentOptions enchantmentOptions;

    public SpawnEntityEffect(EntityType<?> entityType, BlockPos offset, EnchantmentOptions enchantmentOptions) {
        super(Effect.SPAWN_ENTITY);
        this.entityType = entityType;
        this.offset = offset;
        this.enchantmentOptions = enchantmentOptions;
    }

    public static Effect getFromJson(JsonObject object) {
        EntityType<?> entityType = Registry.ENTITY_TYPE.get(Identifier.tryParse(object.get("entity_type").getAsString()));
        BlockPos offset = CustomJsonHelper.getBlockPos(object, "offset");
        EnchantmentOptions enchantmentOptions = EnchantmentOptions.get(object, "enchantment_options");
        return new SpawnEntityEffect(entityType, offset, enchantmentOptions);
    }

    @Override
    public void runBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        if (!world.isClient) {
            world.spawnEntity(this.entityType.create((ServerWorld) world, null, null, living instanceof PlayerEntity ? (PlayerEntity) living : null, pos.add(offset), SpawnReason.NATURAL, false, false));
        }
    }

    @Override
    public void runItem(World world, Item item, BlockPos pos, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            world.spawnEntity(this.entityType.create((ServerWorld) world, null, null, player, pos.add(offset), SpawnReason.NATURAL, false, false));
        }
    }

    @Override
    public void runEntity(Entity entity, PlayerEntity player, Hand hand) {
        World world = entity.getWorld();
        if (!world.isClient) {
            world.spawnEntity(this.entityType.create((ServerWorld) world, null, null, player, entity.getBlockPos().add(offset), SpawnReason.NATURAL, false, false));
        }
    }

    @Override
    public void runEnchantment(Entity user, Entity target, int level) {
        World world = target.getWorld();
        if (!world.isClient) {
            BlockPos pos = enchantmentOptions.useTargetPosition() ? target.getBlockPos() : user.getBlockPos();
            world.spawnEntity(this.entityType.create((ServerWorld) world, null, null, null, pos.add(offset), SpawnReason.NATURAL, false, false));
        }
    }

    private record EnchantmentOptions(boolean useTargetPosition) {
        public static EnchantmentOptions get(JsonElement element, String name) {
            if (element.isJsonObject()) {
                JsonObject object = element.getAsJsonObject();
                boolean useTargetPosition = JsonHelper.getBoolean(object, "use_target_position", false);
                return new EnchantmentOptions(useTargetPosition);
            }
            throw new JsonSyntaxException(name);
        }
    }
}
