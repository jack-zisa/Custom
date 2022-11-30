package creoii.custom.eventsystem.parameter;

import com.google.gson.JsonObject;
import creoii.custom.Custom;
import creoii.custom.eventsystem.condition.Condition;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

public interface EventParameter {
    static EventParameter register(Identifier identifier, EventParameter parameter) {
        return Registry.register(Custom.EVENT_PARAMETER, identifier, parameter);
    }

    static EventParameter getEventParameter(JsonObject object) {
        return Custom.EVENT_PARAMETER.get(Identifier.tryParse(JsonHelper.getString(object, "type")));
    }

    /**
     * Returns a parameter of matching type
     */
    static EventParameter find(List<EventParameter> parameters, EventParameter type) {
        for (EventParameter param : parameters) {
            if (param.getType() == type.getType()) return param;
        }
        return null;
    }

    /**
     * Returns a parameter if modified, otherwise if matching type
     */
    //static EventParameter find(List<EventParameter> parameters, ParameterModification modification, EventParameter type) {
    //    for (EventParameter param : parameters) {
    //        if (modification != null && modification.modifies(param.getType())) {
    //            if (modification.getModifications().get(param.getType()).equals(param.getName())) return param;
    //        } else {
    //            if (param.getType() == type.getType()) return param;
    //        }
    //    }
    //    return null;
    //}

    // checks if we have the correct parameters to perform whatever method
    static boolean invalidate(List<EventParameter> toValidate, List<EventParameter> validater) {
        for (EventParameter parameter : toValidate) {
            if (find(validater, parameter.getType()) == null) return true;
        }
        return false;
    }

    static EventParameter getType(Object o) {
        if (o instanceof Block) return EventParameters.BLOCK;
        else if (o instanceof BlockPos) return EventParameters.BLOCK_POS;
        else if (o instanceof BlockState) return EventParameters.BLOCKSTATE;
        else if (o instanceof Boolean) return EventParameters.BOOLEAN;
        else if (o instanceof Condition) return EventParameters.CONDITION;
        else if (o instanceof Double) return EventParameters.DOUBLE;
        else if (o instanceof Enchantment) return EventParameters.ENCHANTMENT;
        else if (o instanceof Entity) return EventParameters.ENTITY;
        else if (o instanceof EntityType<?>) return EventParameters.ENTITY_TYPE;
        else if (o instanceof Integer) return EventParameters.INTEGER;
        else if (o instanceof Item) return EventParameters.ITEM;
        else if (o instanceof ItemStack) return EventParameters.ITEMSTACK;
        else if (o instanceof StatusEffect) return EventParameters.STATUS_EFFECT;
        else if (o instanceof String) return EventParameters.STRING;
        else if (o instanceof World) return EventParameters.WORLD;
        else return EventParameters.EMPTY;
    }

    static Object getValue(EventParameter parameter) {
        if (parameter.getType() == EventParameters.BLOCK) return ((BlockParameter)parameter).getBlock();
        else if (parameter.getType() == EventParameters.BLOCK_POS) return ((BlockPosParameter)parameter).getPos();
        else return null;
    }

    default Identifier getIdentifier() {
        return Custom.EVENT_PARAMETER.getId(this);
    }

    EventParameter getType();

    EventParameter getFromJson(JsonObject object, String name);

    String getName();
}
