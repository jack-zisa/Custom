package creoii.custom.custom.block;

import creoii.custom.data.Identifiable;
import creoii.custom.eventsystem.event.*;
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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class EventCustomBlock extends CustomBlock implements Identifiable {
    private final Event[] events;
    private Event cachedEvent = null;

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
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (cachedEvent == null) cachedEvent = Event.findEvent(events, Events.RANDOM_TICK);
        else if (cachedEvent == Events.RANDOM_TICK) {
            cachedEvent.applyBlockEvent(world, state, pos, null, null);
        }

        super.randomTick(state, world, pos, random);
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (cachedEvent == null) cachedEvent = Event.findEvent(events, Events.ENTITY_LANDS);
        else if (cachedEvent == Events.ENTITY_LANDS) {
            ((EntityLandsEvent) cachedEvent).setEntity(entity);
            cachedEvent.applyBlockEvent(world, state, pos, null, null);
        }
        super.onLandedUpon(world, state, pos, entity, fallDistance);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (cachedEvent == null) cachedEvent = Event.findEvent(events, Events.RIGHT_CLICK);
        else if (cachedEvent == Events.RIGHT_CLICK && cachedEvent.applyBlockEvent(world, state, pos, player, hand)) {
            return ((RightClickEvent) cachedEvent).getActionResult();
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (cachedEvent == null) cachedEvent = Event.findEvent(events, Events.STEPPED_ON);
        else if (cachedEvent == Events.STEPPED_ON && entity instanceof LivingEntity living) {
            cachedEvent.applyBlockEvent(world, state, pos, living, living.getActiveHand());
        }
        super.onSteppedOn(world, pos, state, entity);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (cachedEvent == null) cachedEvent = Event.findEvent(events, Events.PROJECTILE_HIT);
        else if (cachedEvent == Events.PROJECTILE_HIT && projectile.getOwner() instanceof LivingEntity living) {
            cachedEvent.applyBlockEvent(world, state, hit.getBlockPos(), living, living.getActiveHand());
        }
        super.onProjectileHit(world, state, hit, projectile);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (cachedEvent == null) cachedEvent = Event.findEvent(events, Events.LEFT_CLICK);
        else if (cachedEvent == Events.BREAK_BLOCK) cachedEvent.applyBlockEvent(world, state, pos, player, player.getActiveHand());
        super.onBlockBreakStart(state, world, pos, player);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (cachedEvent == null) cachedEvent = Event.findEvent(events, Events.NEIGHBOR_UPDATE);
        else if (cachedEvent == Events.NEIGHBOR_UPDATE) {
            NeighborUpdateEvent neighborUpdateEvent = (NeighborUpdateEvent) cachedEvent;
            neighborUpdateEvent.setNeighborState(block.getDefaultState());
            neighborUpdateEvent.setNeighborPos(fromPos);
            neighborUpdateEvent.applyBlockEvent(world, state, pos, null, null);
        }
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (cachedEvent == null) cachedEvent = Event.findEvent(events, Events.PLACE_BLOCK);
        else if (cachedEvent == Events.PLACE_BLOCK) cachedEvent.applyBlockEvent(world, state, pos, placer, placer.getActiveHand());
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (cachedEvent == null) cachedEvent = Event.findEvent(events, Events.BREAK_BLOCK);
        else if (cachedEvent == Events.BREAK_BLOCK) cachedEvent.applyBlockEvent(world, state, pos, player, player.getActiveHand());
        super.onBreak(world, pos, state, player);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (cachedEvent == null) cachedEvent = Event.findEvent(events, Events.ENTITY_COLLISION);
        else if (cachedEvent == Events.ENTITY_COLLISION && entity instanceof LivingEntity living) {
            cachedEvent.applyBlockEvent(world, state, pos, living, living.getActiveHand());
        }
        super.onEntityCollision(state, world, pos, entity);
    }
}
