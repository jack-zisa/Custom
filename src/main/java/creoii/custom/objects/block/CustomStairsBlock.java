package creoii.custom.custom.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.StairShape;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

import java.util.stream.IntStream;

public class CustomStairsBlock extends CustomBlock implements Waterloggable {
    public static final DirectionProperty FACING;
    public static final EnumProperty<BlockHalf> HALF;
    public static final EnumProperty<StairShape> SHAPE;
    public static final BooleanProperty WATERLOGGED;
    protected static final VoxelShape TOP_SHAPE;
    protected static final VoxelShape BOTTOM_SHAPE;
    protected static final VoxelShape BOTTOM_NORTH_WEST_CORNER_SHAPE;
    protected static final VoxelShape BOTTOM_SOUTH_WEST_CORNER_SHAPE;
    protected static final VoxelShape TOP_NORTH_WEST_CORNER_SHAPE;
    protected static final VoxelShape TOP_SOUTH_WEST_CORNER_SHAPE;
    protected static final VoxelShape BOTTOM_NORTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape BOTTOM_SOUTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape TOP_NORTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape TOP_SOUTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape[] TOP_SHAPES;
    protected static final VoxelShape[] BOTTOM_SHAPES;
    private static final int[] SHAPE_INDICES;

    public CustomStairsBlock(
            Identifier identifier, boolean hasItem, Settings blockSettings, Item.Settings itemSettings,
            boolean placeableOnLiquid,
            int redstonePower, int droppedXp, int fuelPower,
            float fallDamageMultiplier, float bounceVelocity, float slideVelocity,
            RenderLayer renderLayer, PathNodeType pathNodeType,
            int flammability, int fireSpread, float compostChance
    ) {
        super(identifier, hasItem, blockSettings, itemSettings,
                placeableOnLiquid, redstonePower, droppedXp, fuelPower,
                fallDamageMultiplier, bounceVelocity, slideVelocity,
                renderLayer, pathNodeType, OffsetType.NONE, null,
                flammability, fireSpread, compostChance);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(HALF, BlockHalf.BOTTOM).with(SHAPE, StairShape.STRAIGHT).with(WATERLOGGED, false));
    }


    private static VoxelShape[] composeShapes(VoxelShape base, VoxelShape northWest, VoxelShape northEast, VoxelShape southWest, VoxelShape southEast) {
        return IntStream.range(0, 16).mapToObj((i) -> {
            return composeShape(i, base, northWest, northEast, southWest, southEast);
        }).toArray(VoxelShape[]::new);
    }

    private static VoxelShape composeShape(int i, VoxelShape base, VoxelShape northWest, VoxelShape northEast, VoxelShape southWest, VoxelShape southEast) {
        VoxelShape voxelShape = base;
        if ((i & 1) != 0) {
            voxelShape = VoxelShapes.union(base, northWest);
        }

        if ((i & 2) != 0) {
            voxelShape = VoxelShapes.union(voxelShape, northEast);
        }

        if ((i & 4) != 0) {
            voxelShape = VoxelShapes.union(voxelShape, southWest);
        }

        if ((i & 8) != 0) {
            voxelShape = VoxelShapes.union(voxelShape, southEast);
        }

        return voxelShape;
    }


    @SuppressWarnings("deprecation")
    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return (state.get(HALF) == BlockHalf.TOP ? TOP_SHAPES : BOTTOM_SHAPES)[SHAPE_INDICES[this.getShapeIndexIndex(state)]];
    }

    private int getShapeIndexIndex(BlockState state) {
        return state.get(SHAPE).ordinal() * 4 + state.get(FACING).getHorizontal();
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getSide();
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
        BlockState blockState = ((getDefaultState().with(FACING, ctx.getPlayerFacing())).with(HALF, direction != Direction.DOWN && (direction == Direction.UP || !(ctx.getHitPos().y - (double)blockPos.getY() > 0.5D)) ? BlockHalf.BOTTOM : BlockHalf.TOP)).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
        return blockState.with(SHAPE, getStairShape(blockState, ctx.getWorld(), blockPos));
    }

    @SuppressWarnings("deprecation")
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return direction.getAxis().isHorizontal() ? state.with(SHAPE, getStairShape(state, world, pos)) : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    private static StairShape getStairShape(BlockState state, BlockView world, BlockPos pos) {
        Direction direction = state.get(FACING);
        BlockState blockState = world.getBlockState(pos.offset(direction));
        if (isStairs(blockState) && state.get(HALF) == blockState.get(HALF)) {
            Direction direction2 = blockState.get(FACING);
            if (direction2.getAxis() != (state.get(FACING)).getAxis() && isDifferentOrientation(state, world, pos, direction2.getOpposite())) {
                if (direction2 == direction.rotateYCounterclockwise()) {
                    return StairShape.OUTER_LEFT;
                }

                return StairShape.OUTER_RIGHT;
            }
        }

        BlockState direction2 = world.getBlockState(pos.offset(direction.getOpposite()));
        if (isStairs(direction2) && state.get(HALF) == direction2.get(HALF)) {
            Direction direction3 = direction2.get(FACING);
            if (direction3.getAxis() != (state.get(FACING)).getAxis() && isDifferentOrientation(state, world, pos, direction3)) {
                if (direction3 == direction.rotateYCounterclockwise()) {
                    return StairShape.INNER_LEFT;
                }

                return StairShape.INNER_RIGHT;
            }
        }

        return StairShape.STRAIGHT;
    }

    private static boolean isDifferentOrientation(BlockState state, BlockView world, BlockPos pos, Direction dir) {
        BlockState blockState = world.getBlockState(pos.offset(dir));
        return !isStairs(blockState) || blockState.get(FACING) != state.get(FACING) || blockState.get(HALF) != state.get(HALF);
    }

    public static boolean isStairs(BlockState state) {
        return state.getBlock() instanceof StairsBlock;
    }

    @SuppressWarnings("deprecation")
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        Direction direction = state.get(FACING);
        StairShape stairShape = state.get(SHAPE);
        switch(mirror) {
            case LEFT_RIGHT:
                if (direction.getAxis() == Direction.Axis.Z) {
                    return switch (stairShape) {
                        case INNER_LEFT -> state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.INNER_RIGHT);
                        case INNER_RIGHT -> state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.INNER_LEFT);
                        case OUTER_LEFT -> state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.OUTER_RIGHT);
                        case OUTER_RIGHT -> state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.OUTER_LEFT);
                        default -> state.rotate(BlockRotation.CLOCKWISE_180);
                    };
                }
                break;
            case FRONT_BACK:
                if (direction.getAxis() == Direction.Axis.X) {
                    return switch (stairShape) {
                        case INNER_LEFT -> state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.INNER_LEFT);
                        case INNER_RIGHT -> state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.INNER_RIGHT);
                        case OUTER_LEFT -> state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.OUTER_RIGHT);
                        case OUTER_RIGHT -> state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.OUTER_LEFT);
                        case STRAIGHT -> state.rotate(BlockRotation.CLOCKWISE_180);
                    };
                }
        }

        return super.mirror(state, mirror);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, HALF, SHAPE, WATERLOGGED);
    }

    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @SuppressWarnings("deprecation")
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    static {
        FACING = HorizontalFacingBlock.FACING;
        HALF = Properties.BLOCK_HALF;
        SHAPE = Properties.STAIR_SHAPE;
        WATERLOGGED = Properties.WATERLOGGED;
        TOP_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        BOTTOM_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
        BOTTOM_NORTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D);
        BOTTOM_SOUTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D);
        TOP_NORTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 8.0D);
        TOP_SOUTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 8.0D, 8.0D, 16.0D, 16.0D);
        BOTTOM_NORTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
        BOTTOM_SOUTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
        TOP_NORTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D);
        TOP_SOUTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D);
        TOP_SHAPES = composeShapes(TOP_SHAPE, BOTTOM_NORTH_WEST_CORNER_SHAPE, BOTTOM_NORTH_EAST_CORNER_SHAPE, BOTTOM_SOUTH_WEST_CORNER_SHAPE, BOTTOM_SOUTH_EAST_CORNER_SHAPE);
        BOTTOM_SHAPES = composeShapes(BOTTOM_SHAPE, TOP_NORTH_WEST_CORNER_SHAPE, TOP_NORTH_EAST_CORNER_SHAPE, TOP_SOUTH_WEST_CORNER_SHAPE, TOP_SOUTH_EAST_CORNER_SHAPE);
        SHAPE_INDICES = new int[]{12, 5, 3, 10, 14, 13, 7, 11, 13, 7, 11, 14, 8, 4, 1, 2, 4, 1, 2, 8};
    }
}
