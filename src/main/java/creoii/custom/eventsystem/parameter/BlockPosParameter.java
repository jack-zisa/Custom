package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;
import net.minecraft.util.math.BlockPos;

public class BlockPosParameter implements EventParameter {
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

    @Override
    public EventParameter getFromJson(JsonObject object) {
        x = object.get("x").getAsInt();
        y = object.get("y").getAsInt();
        z = object.get("z").getAsInt();
        pos = new BlockPos(x, y, z);
        return this;
    }
}
