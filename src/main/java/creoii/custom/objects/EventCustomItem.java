package creoii.custom.objects;

import creoii.custom.eventsystem.event.AbstractEvent;
import creoii.custom.eventsystem.event.ItemEvents;
import creoii.custom.eventsystem.parameter.*;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;

public class EventCustomItem extends CustomItem {
    private final List<AbstractEvent> events;

    public EventCustomItem(Identifier identifier, Settings settings, Text tooltipText, List<AbstractEvent> events) {
        super(identifier, settings, tooltipText);
        this.events = events;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        List<AbstractEvent> events1 = AbstractEvent.findAll(events, ItemEvents.USE_ON_BLOCK);
        if (events1 != null) {
            for (AbstractEvent event : events1) {
                BlockState state = context.getWorld().getBlockState(context.getBlockPos());
                event.apply(List.of(
                        new WorldParameter().withValue(context.getWorld()),
                        new BlockPosParameter().withValue(context.getBlockPos()),
                        new BlockStateParameter().withValue(state),
                        new BlockParameter().withValue(state.getBlock()),
                        new EntityParameter().withValue(context.getPlayer()).name("player"),
                        new EntityTypeParameter().withValue(context.getPlayer().getType()),
                        new StringParameter().withValue(context.getSide().asString()).name("side"),
                        new StringParameter().withValue(context.getPlayerFacing().asString()).name("player_facing"),
                        new StringParameter().withValue(context.getHand().toString()).name("hand"),
                        new DoubleParameter().withValue(context.getPlayerYaw()).name("player_yaw"),
                        new ItemStackParameter().withValue(context.getStack()),
                        new ItemParameter().withValue(context.getStack().getItem()),
                        new BooleanParameter().withValue(context.hitsInsideBlock()).name("hits_inside_block"),
                        new BooleanParameter().withValue(context.shouldCancelInteraction()).name("should_cancel_interaction")
                ));
            }
        }
        return super.useOnBlock(context);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        List<AbstractEvent> events1 = AbstractEvent.findAll(events, ItemEvents.USE_ON_ENTITY);
        if (events1 != null) {
            for (AbstractEvent event : events1) {
                event.apply(List.of(
                        new WorldParameter().withValue(user.getWorld()),
                        new EntityParameter().withValue(user).name("player"),
                        new EntityTypeParameter().withValue(user.getType()).name("user_type"),
                        new BlockPosParameter().withValue(user.getBlockPos()).name("user_pos"),
                        new EntityParameter().withValue(user),
                        new EntityTypeParameter().withValue(user.getType()).name("target_type"),
                        new BlockPosParameter().withValue(user.getBlockPos()).name("target_pos"),
                        new StringParameter().withValue(hand.toString()).name("hand"),
                        new ItemStackParameter().withValue(stack),
                        new ItemParameter().withValue(stack.getItem())
                ));
            }
        }
        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        List<AbstractEvent> events1 = AbstractEvent.findAll(events, ItemEvents.STOPPED_USING);
        if (events1 != null) {
            for (AbstractEvent event : events1) {
                event.apply(List.of(
                        new WorldParameter().withValue(user.getWorld()),
                        new EntityParameter().withValue(user).name("player"),
                        new EntityTypeParameter().withValue(user.getType()).name("user_type"),
                        new BlockPosParameter().withValue(user.getBlockPos()).name("user_pos"),
                        new EntityParameter().withValue(user),
                        new EntityTypeParameter().withValue(user.getType()).name("target_type"),
                        new BlockPosParameter().withValue(user.getBlockPos()).name("target_pos"),
                        new ItemStackParameter().withValue(stack),
                        new ItemParameter().withValue(stack.getItem()),
                        new IntegerParameter().withValue(remainingUseTicks).name("remaining_use_ticks")
                ));
            }
        }
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        List<AbstractEvent> events1 = AbstractEvent.findAll(events, ItemEvents.CRAFTED);
        if (events1 != null) {
            for (AbstractEvent event : events1) {
                event.apply(List.of(
                        new WorldParameter().withValue(world),
                        new EntityParameter().withValue(player).name("player"),
                        new EntityTypeParameter().withValue(player.getType()),
                        new BlockPosParameter().withValue(player.getBlockPos()).name("player_pos"),
                        new ItemStackParameter().withValue(stack),
                        new ItemParameter().withValue(stack.getItem())
                ));
            }
        }
        super.onCraft(stack, world, player);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        List<AbstractEvent> events1 = AbstractEvent.findAll(events, ItemEvents.USE);
        if (events1 != null) {
            for (AbstractEvent event : events1) {
                event.apply(List.of(
                        new WorldParameter().withValue(world),
                        new EntityParameter().withValue(user).name("player"),
                        new EntityTypeParameter().withValue(user.getType()),
                        new BlockPosParameter().withValue(user.getBlockPos()).name("player_pos"),
                        new StringParameter().withValue(hand.toString()).name("hand")
                ));
            }
        }
        return super.use(world, user, hand);
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        List<AbstractEvent> events1 = AbstractEvent.findAll(events, ItemEvents.CLICKED);
        if (events1 != null) {
            for (AbstractEvent event : events1) {
                event.apply(List.of(
                        new WorldParameter().withValue(player.getWorld()),
                        new EntityParameter().withValue(player).name("player"),
                        new EntityTypeParameter().withValue(player.getType()),
                        new BlockPosParameter().withValue(player.getBlockPos()).name("player_pos"),
                        new ItemStackParameter().withValue(stack).name("held_stack"),
                        new ItemParameter().withValue(stack.getItem()).name("held_item"),
                        new ItemStackParameter().withValue(otherStack).name("other_stack"),
                        new ItemParameter().withValue(otherStack.getItem()).name("other_item"),
                        new IntegerParameter().withValue(slot.getIndex()).name("slot_index"),
                        new StringParameter().withValue(clickType.toString()).name("click_type")
                ));
            }
        }
        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }

    @Override
    public void onItemEntityDestroyed(ItemEntity entity) {
        List<AbstractEvent> events1 = AbstractEvent.findAll(events, ItemEvents.ITEM_DESPAWN);
        if (events1 != null) {
            for (AbstractEvent event : events1) {
                event.apply(List.of(
                        new WorldParameter().withValue(entity.getWorld()),
                        new EntityParameter().withValue(entity),
                        new EntityTypeParameter().withValue(entity.getType()),
                        new BlockPosParameter().withValue(entity.getBlockPos()),
                        new ItemStackParameter().withValue(entity.getStack()),
                        new ItemParameter().withValue(entity.getStack().getItem())
                ));
            }
        }
        super.onItemEntityDestroyed(entity);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        List<AbstractEvent> events1 = AbstractEvent.findAll(events, ItemEvents.INVENTORY_TICK);
        if (events1 != null) {
            for (AbstractEvent event : events1) {
                event.apply(List.of(
                        new WorldParameter().withValue(world),
                        new EntityParameter().withValue(entity),
                        new EntityTypeParameter().withValue(entity.getType()),
                        new BlockPosParameter().withValue(entity.getBlockPos()),
                        new ItemStackParameter().withValue(stack),
                        new ItemParameter().withValue(stack.getItem()),
                        new IntegerParameter().withValue(slot).name("slot_index"),
                        new BooleanParameter().withValue(selected).name("selected")
                ));
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
