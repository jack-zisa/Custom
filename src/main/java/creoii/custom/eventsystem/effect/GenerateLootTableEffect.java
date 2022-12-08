package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.BlockPosParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.WorldParameter;
import creoii.custom.util.json.CustomJsonHelper;
import creoii.custom.util.provider.DoubleProvider;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class GenerateLootTableEffect extends AbstractEffect {
    private Type generateType;
    private Identifier lootTableId;
    private DoubleProvider posOffset;
    private DoubleProvider scatterAmount;

    @Override
    public AbstractEffect getFromJson(JsonObject object) {
        GenerateLootTableEffect effect = new GenerateLootTableEffect();
        effect.generateType = Type.valueOf(object.get("generate_type").getAsString().toUpperCase());
        effect.lootTableId = Identifier.tryParse(CustomJsonHelper.getString(object, new String[]{"loot_table", "table"}));
        effect.posOffset = CustomJsonHelper.getDoubleProvider(object, "pos_offset", .25d);
        effect.scatterAmount = CustomJsonHelper.getDoubleProvider(object, "scatter_amount", .1d);
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
                BlockPos pos = blockPosParameter.getPos();
                if (lootTableId != null && !world.isClient) {
                    LootTable table = ((ServerWorld)world).getServer().getLootManager().getTable(lootTableId);
                    LootContext context = new LootContext.Builder((ServerWorld) world).build(LootContextTypes.EMPTY);
                    if (generateType == Type.DROP) {
                        table.generateLoot(context).forEach(stack -> DropItemEffect.dropItem(world, pos, stack,
                                posOffset.get(world.getRandom()),
                                scatterAmount.get(world.getRandom())
                        ));
                    } else if (generateType == Type.CHEST) {
                        world.setBlockState(pos, Blocks.CHEST.getDefaultState(), 3);
                        table.supplyInventory(((ChestBlockEntity)world.getBlockEntity(pos)), context);
                    }
                }
            }
        }
    }

    private enum Type {
        DROP,
        CHEST
    }
}
