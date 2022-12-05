package creoii.custom.eventsystem.effect;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.BlockPosParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.WorldParameter;
import creoii.custom.util.json.CustomJsonHelper;
import creoii.custom.util.provider.DoubleProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

/**
 * Allow for a list of ItemEntries, which each contain an item & count
 * Drop all ItemEntries
 */
public class DropItemEffect extends AbstractEffect {
    private DropEntry[] entries;
    private Item item;
    private IntProvider count;
    private DoubleProvider posOffset;
    private DoubleProvider scatterAmount;

    public DropItemEffect getFromJson(JsonObject object) {
        DropItemEffect effect = new DropItemEffect();
        if (object.has("entries")) {
            JsonArray array = object.get("entries").getAsJsonArray();
            DropEntry[] entries = new DropEntry[array.size()];
            for (int i = 0; i < array.size(); ++i) {
                JsonElement element = array.get(i);
                if (element.isJsonObject()) {
                    JsonObject entryObj = element.getAsJsonObject();
                    entries[i] = new DropEntry(
                            Registry.ITEM.get(Identifier.tryParse(JsonHelper.getString(entryObj, "item"))),
                            CustomJsonHelper.getIntProvider(entryObj, new String[]{"count", "amount"}, 1),
                            CustomJsonHelper.getDoubleProvider(entryObj, "pos_offset", .25d),
                            CustomJsonHelper.getDoubleProvider(entryObj, "scatter_amount", .1d)
                    );
                }
            }
            effect.entries = entries;
        } else {
            effect.item = Registry.ITEM.get(Identifier.tryParse(JsonHelper.getString(object, "item")));
            effect.count = CustomJsonHelper.getIntProvider(object, new String[]{"count", "amount"}, 1);
            effect.posOffset = CustomJsonHelper.getDoubleProvider(object, "pos_offset", .25d);
            effect.scatterAmount = CustomJsonHelper.getDoubleProvider(object, "scatter_amount", .1d);
        }
        return effect;
    }

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of(EventParameters.WORLD, EventParameters.BLOCK_POS);
    }

    @Override
    public void run(List<EventParameter> parameters) {
        WorldParameter worldParameter = (WorldParameter) EventParameter.find(parameters, getModifications(), EventParameters.WORLD);
        if (worldParameter != null) {
            BlockPosParameter blockPosParameter = (BlockPosParameter) EventParameter.find(parameters, getModifications(), EventParameters.BLOCK_POS);
            if (blockPosParameter != null) {
                World world = worldParameter.getWorld();
                int count;
                if (entries != null) {
                    for (DropEntry entry : entries) {
                        count = entry.count.get(world.getRandom());
                        if (count > 0) {
                            dropItem(world, blockPosParameter.getPos(), new ItemStack(
                                            entry.item,
                                            count),
                                    entry.posOffset.get(world.getRandom()),
                                    entry.scatterAmount.get(world.getRandom())
                            );
                        }
                    }
                } else {
                    count = this.count.get(world.getRandom());
                    if (count > 0) {
                        dropItem(world, blockPosParameter.getPos(), new ItemStack(
                                        item,
                                        count),
                                posOffset.get(world.getRandom()),
                                scatterAmount.get(world.getRandom())
                        );
                    }
                }
            }
        }
    }

    private static void dropItem(World world, BlockPos pos, ItemStack stack, double posOffset, double scatterAmount) {
        if (!world.isClient && !stack.isEmpty()) {
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

    public static record DropEntry(Item item, IntProvider count, DoubleProvider posOffset, DoubleProvider scatterAmount) {}
}
