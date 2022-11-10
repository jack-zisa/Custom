package creoii.custom.objects.block;

import creoii.custom.data.Identifiable;
import creoii.custom.eventsystem.effect.Effect;
import creoii.custom.eventsystem.effect.SendMessageEffect;
import creoii.custom.eventsystem.event.*;
import creoii.custom.eventsystem.parameter.*;
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

import java.util.List;

public class EventCustomBlock extends CustomBlock implements Identifiable {
    private final List<AbstractEvent> events;
    private List<AbstractEvent> cachedEvents = null;
    private AbstractEvent cachedEvent = null;

    public EventCustomBlock(
            Identifier identifier, boolean hasItem, Settings blockSettings, Item.Settings itemSettings,
            boolean placeableOnLiquid,
            int redstonePower, int droppedXp, int fuelPower,
            float fallDamageMultiplier, float bounceVelocity, float slideVelocity,
            RenderLayer renderLayer, PathNodeType pathNodeType, OffsetType offsetType, Shape shape,
            int flammability, int fireSpread, float compostChance,
            List<AbstractEvent> events
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

    public List<AbstractEvent> getEvents() {
        return events;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        super.onLandedUpon(world, state, pos, entity, fallDistance);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        List<AbstractEvent> events1 = AbstractEvent.findAll(events, Events.RIGHT_CLICK);
        if (events1 != null) {
            for (AbstractEvent event : events1) {
                event.apply(List.of(
                        new WorldParameter().withValue(world),
                        new BlockPosParameter().withValue(pos),
                        new BlockStateParameter().withValue(state),
                        new BlockParameter().withValue(state.getBlock()),
                        new EntityParameter().withValue(player),
                        new EntityTypeParameter().withValue(player.getType())
                ));
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        List<AbstractEvent> events1 = AbstractEvent.findAll(events, Events.STEPPED_ON);
        if (events1 != null) {
            for (AbstractEvent event : events1) {
                event.apply(List.of(
                        new WorldParameter().withValue(world),
                        new BlockPosParameter().withValue(pos),
                        new BlockStateParameter().withValue(state),
                        new BlockParameter().withValue(state.getBlock()),
                        new EntityParameter().withValue(entity),
                        new EntityTypeParameter().withValue(entity.getType())
                ));
            }
        }
        super.onSteppedOn(world, pos, state, entity);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        super.onProjectileHit(world, state, hit, projectile);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        List<AbstractEvent> events1 = AbstractEvent.findAll(events, Events.LEFT_CLICK);
        if (events1 != null) {
            for (AbstractEvent event : events1) {
                event.apply(List.of(
                        new WorldParameter().withValue(world),
                        new BlockPosParameter().withValue(pos),
                        new BlockStateParameter().withValue(state),
                        new BlockParameter().withValue(state.getBlock()),
                        new EntityParameter().withValue(player),
                        new EntityTypeParameter().withValue(player.getType())
                ));
            }
        }
        super.onBlockBreakStart(state, world, pos, player);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
    }
}
