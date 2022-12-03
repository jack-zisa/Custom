package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.BlockPosParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.WorldParameter;
import creoii.custom.util.json.CustomJsonHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

/**
 * Allow for a list of ItemEntries, which each contain an item & count
 * Drop all ItemEntries
 */
public class DropItemEffect extends AbstractEffect {
    private Item item;
    private int count;
    private double posOffset;
    private double scatterAmount;

    public DropItemEffect getFromJson(JsonObject object) {
        DropItemEffect effect = new DropItemEffect();
        effect.item = Registry.ITEM.get(Identifier.tryParse(JsonHelper.getString(object, "item")));
        effect.count = CustomJsonHelper.getInt(object, new String[]{"count", "amount"}, 1);
        effect.posOffset = JsonHelper.getDouble(object, "pos_offset", .25d);
        effect.scatterAmount = JsonHelper.getDouble(object, "scatter_amount", .1d);
        return effect;
    }

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of(EventParameters.WORLD, EventParameters.BLOCK_POS);
    }

    @Override
    public void run(List<EventParameter> parameters) {
        if (count > 0 && item != null) {
            WorldParameter worldParameter = (WorldParameter) EventParameter.find(parameters, getModifications(), EventParameters.WORLD);
            if (worldParameter != null) {
                BlockPosParameter blockPosParameter = (BlockPosParameter) EventParameter.find(parameters, getModifications(), EventParameters.BLOCK_POS);
                if (blockPosParameter != null) {
                    World world = worldParameter.getWorld();
                    ItemStack stack = new ItemStack(item, count);
                    if (!world.isClient && !stack.isEmpty()) {
                        BlockPos pos = blockPosParameter.getPos();
                        float h = EntityType.ITEM.getHeight() / 2f;
                        float w = EntityType.ITEM.getWidth() / 2f;
                        double d = (double) ((float) pos.getX() + .5f) + MathHelper.nextDouble(world.random, -posOffset, posOffset) - (double) w;
                        double e = (double) ((float) pos.getY() + .5f) + MathHelper.nextDouble(world.random, -posOffset, posOffset) - (double) h;
                        double g = (double) ((float) pos.getZ() + .5f) + MathHelper.nextDouble(world.random, -posOffset, posOffset) - (double) w;
                        double l = MathHelper.nextDouble(world.random, -scatterAmount, scatterAmount);
                        double m = MathHelper.nextDouble(world.random, scatterAmount, scatterAmount);
                        double n = MathHelper.nextDouble(world.random, -scatterAmount, scatterAmount);
                        ItemEntity itemEntity = new ItemEntity(world, d, e, g, stack, l, m, n);
                        itemEntity.setToDefaultPickupDelay();
                        world.spawnEntity(itemEntity);
                    }
                }
            }
        }
    }
}
