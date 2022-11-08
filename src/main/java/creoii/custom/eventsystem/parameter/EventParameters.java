package creoii.custom.eventsystem.parameter;

import creoii.custom.Custom;
import net.minecraft.util.Identifier;

public class EventParameters {
    public static EmptyParameter EMPTY;
    public static EmptyParameter INTEGER;
    public static EmptyParameter BOOLEAN;
    public static EmptyParameter STRING;
    public static ObjectParameter OBJECT;
    public static BlockParameter BLOCK;
    public static BlockStateParameter BLOCKSTATE;
    public static ItemParameter ITEM;
    public static ItemStackParameter ITEMSTACK;
    public static EntityParameter ENTITY;
    public static EntityTypeParameter ENTITY_TYPE;
    public static EnchantmentParameter ENCHANTMENT;
    public static StatusEffectParameter STATUS_EFFECT;
    public static WorldParameter WORLD;
    public static BlockPosParameter BLOCK_POS;
    public static ConditionParameter CONDITION;

    public static void register() {
        EMPTY = (EmptyParameter) EventParameter.register(new Identifier(Custom.NAMESPACE, "empty"), new EmptyParameter());
        INTEGER = (EmptyParameter) EventParameter.register(new Identifier(Custom.NAMESPACE, "integer"), new IntegerParameter());
        BOOLEAN = (EmptyParameter) EventParameter.register(new Identifier(Custom.NAMESPACE, "boolean"), new BooleanParameter());
        STRING = (EmptyParameter) EventParameter.register(new Identifier(Custom.NAMESPACE, "string"), new StringParameter());
        OBJECT = (ObjectParameter) EventParameter.register(new Identifier(Custom.NAMESPACE, "object"), new ObjectParameter());
        BLOCK = (BlockParameter) EventParameter.register(new Identifier(Custom.NAMESPACE, "block"), new BlockParameter());
        BLOCKSTATE = (BlockStateParameter) EventParameter.register(new Identifier(Custom.NAMESPACE, "blockstate"), new BlockStateParameter());
        ITEM = (ItemParameter) EventParameter.register(new Identifier(Custom.NAMESPACE, "item"), new ItemParameter());
        ITEMSTACK = (ItemStackParameter) EventParameter.register(new Identifier(Custom.NAMESPACE, "itemstack"), new ItemStackParameter());
        ENTITY = (EntityParameter) EventParameter.register(new Identifier(Custom.NAMESPACE, "entity"), new EntityParameter());
        ENTITY_TYPE = (EntityTypeParameter) EventParameter.register(new Identifier(Custom.NAMESPACE, "entity_type"), new EntityTypeParameter());
        ENCHANTMENT = (EnchantmentParameter) EventParameter.register(new Identifier(Custom.NAMESPACE, "enchantment"), new EnchantmentParameter());
        STATUS_EFFECT = (StatusEffectParameter) EventParameter.register(new Identifier(Custom.NAMESPACE, "status_effect"), new StatusEffectParameter());
        WORLD = (WorldParameter) EventParameter.register(new Identifier(Custom.NAMESPACE, "world"), new WorldParameter());
        BLOCK_POS = (BlockPosParameter) EventParameter.register(new Identifier(Custom.NAMESPACE, "block_pos"), new BlockPosParameter());
        CONDITION = (ConditionParameter) EventParameter.register(new Identifier(Custom.NAMESPACE, "condition"), new ConditionParameter());
    }
}
