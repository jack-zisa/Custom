package creoii.custom.objects;

import com.google.gson.*;
import creoii.custom.loaders.Identifiable;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.lang.reflect.Type;

public class CustomFluid extends FlowableFluid implements Identifiable {
    private final Identifier identifier;
    private final Item bucketItem;
    private final int flowSpeed;
    private final int levelDecrease;
    private final int tickRate;
    private final float blastResistance;
    private final boolean infinite;

    public CustomFluid(Identifier identifier, Item bucketItem,
                       int flowSpeed, int levelDecrease, int tickRate,
                       float blastResistance, boolean infinite
    ) {
        this.identifier = identifier;
        this.bucketItem = bucketItem;
        this.flowSpeed = flowSpeed;
        this.levelDecrease = levelDecrease;
        this.tickRate = tickRate;
        this.blastResistance = blastResistance;
        this.infinite = infinite;
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public Fluid getFlowing() {
        return null;
    }

    @Override
    public Fluid getStill() {
        return null;
    }

    @Override
    protected boolean isInfinite() {
        return infinite;
    }

    @Override
    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
    }

    @Override
    protected int getFlowSpeed(WorldView world) {
        return flowSpeed;
    }

    @Override
    protected int getLevelDecreasePerBlock(WorldView world) {
        return levelDecrease;
    }

    @Override
    public Item getBucketItem() {
        return bucketItem;
    }

    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
        return false;
    }

    @Override
    public int getTickRate(WorldView world) {
        return tickRate;
    }

    @Override
    protected float getBlastResistance() {
        return blastResistance;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return null;
    }

    @Override
    public boolean isStill(FluidState state) {
        return state.isStill();
    }

    @Override
    public int getLevel(FluidState state) {
        return state.getLevel();
    }

    public static class Serializer implements JsonSerializer<CustomFluid>, JsonDeserializer<CustomFluid> {
        @Override
        public CustomFluid deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return null;
        }

        @Override
        public JsonElement serialize(CustomFluid src, Type typeOfSrc, JsonSerializationContext context) {
            return null;
        }
    }
}
