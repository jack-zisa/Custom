package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;
import net.minecraft.util.math.BlockPos;

public class BlockPosParameter implements EventParameter {
    private String name = "block_pos";
    private BlockPos pos;
    private int x;
    private int y;
    private int z;

    public BlockPos getPos() {
        return pos;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public BlockPosParameter withValue(BlockPos pos) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.pos = pos;
        return this;
    }

    public BlockPosParameter name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public EventParameter getType() {
        return EventParameters.BLOCK_POS;
    }

    @Override
    public EventParameter getFromJson(JsonObject object, String name) {
        x = object.get("x").getAsInt();
        y = object.get("y").getAsInt();
        z = object.get("z").getAsInt();
        pos = new BlockPos(x, y, z);
        return this;
    }

    @Override
    public String getName() {
        return name;
    }
}
