package creoii.custom.objects;

import creoii.custom.eventsystem.event.AbstractEvent;
import creoii.custom.eventsystem.event.Events;
import creoii.custom.eventsystem.event.RightClickEvent;
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

public class EventCustomItem extends CustomItem {
    private final AbstractEvent[] events;

    public EventCustomItem(Identifier identifier, Settings settings, Text tooltipText, AbstractEvent[] events) {
        super(identifier, settings, tooltipText);
        this.events = events;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        AbstractEvent event = AbstractEvent.findEvent(events, Events.RIGHT_CLICK);
        if (event != null) {
            if (event.applyItemEvent(context.getWorld(), context.getStack(), context.getBlockPos(), context.getPlayer(), context.getHand())) {
                return ((RightClickEvent) event).getActionResult();
            }
        }
        return super.useOnBlock(context);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        AbstractEvent event = AbstractEvent.findEvent(events, Events.RIGHT_CLICK);
        if (event != null) {
            if (event.applyItemEvent(user.world, stack, entity.getBlockPos(), user, hand)) {
                return ((RightClickEvent) event).getActionResult();
            }
        }
        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        AbstractEvent event = AbstractEvent.findEvent(events, Events.STOPPED_USING);
        if (event != null && user instanceof PlayerEntity player) {
            event.applyItemEvent(user.world, stack, player.getBlockPos(), player, player.getActiveHand());
        }
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        AbstractEvent event = AbstractEvent.findEvent(events, Events.CRAFTED);
        if (event != null) {
            event.applyItemEvent(world, stack, player.getBlockPos(), player, player.getActiveHand());
        }
        super.onCraft(stack, world, player);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        AbstractEvent event = AbstractEvent.findEvent(events, Events.RIGHT_CLICK);
        if (event != null) {
            event.applyItemEvent(user.world, user.getStackInHand(hand), user.getBlockPos(), user, hand);
        }
        return super.use(world, user, hand);
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        AbstractEvent event = AbstractEvent.findEvent(events, Events.LEFT_CLICK);
        if (event != null) {
            event.applyItemEvent(player.world, stack, player.getBlockPos(), player, player.getActiveHand());
        }
        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }

    @Override
    public void onItemEntityDestroyed(ItemEntity entity) {
        AbstractEvent event = AbstractEvent.findEvent(events, Events.ITEM_DESPAWN);
        if (event != null) {
            event.applyItemEvent(entity.getWorld(), entity.getStack(), entity.getBlockPos(), null, null);
        }
        super.onItemEntityDestroyed(entity);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        AbstractEvent event = AbstractEvent.findEvent(events, Events.INVENTORY_TICK);
        if (event != null) {
            event.applyItemEvent(world, stack, entity.getBlockPos(), null, null);
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
