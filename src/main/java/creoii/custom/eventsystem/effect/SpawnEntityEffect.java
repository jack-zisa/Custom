package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.BlockPosParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.WorldParameter;
import creoii.custom.util.json.CustomJsonHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

public class SpawnEntityEffect extends AbstractEffect {
    private EntityType<?> entityType;
    private BlockPos offset;

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of(EventParameters.WORLD, EventParameters.BLOCK_POS);
    }

    public SpawnEntityEffect getFromJson(JsonObject object) {
        SpawnEntityEffect effect = new SpawnEntityEffect();
        effect.entityType = Registry.ENTITY_TYPE.get(Identifier.tryParse(CustomJsonHelper.getString(object, new String[]{"entity", "entity_type"})));
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
                    world.spawnEntity(entityType.create((ServerWorld) world, null, null, null, blockPosParameter.getPos().add(offset), SpawnReason.NATURAL, false, false));
                }
            }
        }
    }
}
