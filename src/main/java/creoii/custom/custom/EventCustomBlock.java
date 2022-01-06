package creoii.custom.custom;

import creoii.custom.data.CustomObject;
import creoii.custom.eventsystem.event.EntityLandsEvent;
import creoii.custom.eventsystem.event.Event;
import creoii.custom.eventsystem.event.NeighborUpdateEvent;
import creoii.custom.eventsystem.event.RightClickEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EventCustomBlock extends CustomBlock implements CustomObject {
    private final Event[] events;

    public EventCustomBlock(
            Identifier identifier, boolean hasItem, Settings blockSettings, Item.Settings itemSettings,
            boolean placeableOnLiquid,
            int redstonePower, int droppedXp, int fuelPower,
            float fallDamageMultiplier, float bounceVelocity, float slideVelocity,
            RenderLayer renderLayer, PathNodeType pathNodeType, OffsetType offsetType, Shape shape,
            int flammability, int fireSpread, float compostChance,
            Event[] events
    ) {
        super(identifier, hasItem,
                blockSettings, itemSettings,
                placeableOnLiquid,
                redstonePower, droppedXp, fuelPower,
                fallDamageMultiplier, bounceVelocity, slideVelocity,
                renderLayer, pathNodeType, offsetType,
                shape, flammability, fireSpread, compostChance
        );
        this.events = events;
    }

    public Event[] getEvents() {
        return events;
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        Event event = Event.findEvent(events, Event.ENTITY_LANDS);
        if (event != null) {
            ((EntityLandsEvent) event).setEntity(entity);
            event.applyBlockEvent(world, state, pos, null, null);
        }
        super.onLandedUpon(world, state, pos, entity, fallDistance);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        Event event = Event.findEvent(events, Event.RIGHT_CLICK);
        if (event != null) {
            if (event.applyBlockEvent(world, state, pos, player, hand)) {
                return ((RightClickEvent) event).getActionResult();
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        Event event = Event.findEvent(events, Event.STEPPED_ON);
        if (event != null && entity instanceof LivingEntity living) {
            event.applyBlockEvent(world, state, pos, living, living.getActiveHand());
        }
        super.onSteppedOn(world, pos, state, entity);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        Event event = Event.findEvent(events, Event.PROJECTILE_HIT);
        if (event != null && projectile.getOwner() instanceof LivingEntity living) {
            event.applyBlockEvent(world, state, hit.getBlockPos(), living, living.getActiveHand());
        }
        super.onProjectileHit(world, state, hit, projectile);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        Event event = Event.findEvent(events, Event.LEFT_CLICK);
        if (event != null) {
            event.applyBlockEvent(world, state, pos, player, player.getActiveHand());
        }
        super.onBlockBreakStart(state, world, pos, player);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        Event event = Event.findEvent(events, Event.NEIGHBOR_UPDATE);
        if (event != null) {
            NeighborUpdateEvent neighborUpdateEvent = (NeighborUpdateEvent) event;
            neighborUpdateEvent.setNeighborState(block.getDefaultState());
            neighborUpdateEvent.setNeighborPos(fromPos);
            neighborUpdateEvent.applyBlockEvent(world, state, pos, null, null);
        }
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        Event event = Event.findEvent(events, Event.PLACE_BLOCK);
        if (event != null) {
            event.applyBlockEvent(world, state, pos, placer, placer.getActiveHand());
        }
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        Event event = Event.findEvent(events, Event.BREAK_BLOCK);
        if (event != null) {
            event.applyBlockEvent(world, state, pos, player, player.getActiveHand());
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        Event event = Event.findEvent(events, Event.ENTITY_COLLISION);
        if (event != null && entity instanceof LivingEntity living) {
            event.applyBlockEvent(world, state, pos, living, living.getActiveHand());
        }
        super.onEntityCollision(state, world, pos, entity);
    }
}
