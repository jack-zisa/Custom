package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class InTagCondition extends Condition {
    private final Tag<?> tag;
    private final Type type;
    private Block block;
    private Item item;
    private EntityType<?> entityType;

    public InTagCondition(Tag<?> tag, Type type, Block block) {
        super(Condition.IN_TAG);
        this.tag = tag;
        this.type = type;
        this.block = block;
    }

    public InTagCondition(Tag<?> tag, Type type, Item item) {
        super(Condition.IN_TAG);
        this.tag = tag;
        this.type = type;
        this.item = item;
    }

    public InTagCondition(Tag<?> tag, Type type, EntityType<?> entityType) {
        super(Condition.IN_TAG);
        this.tag = tag;
        this.type = type;
        this.entityType = entityType;
    }

    public static Condition getFromJson(JsonObject object) {
        Tag<?> tag = BlockTags.REQUIRED_TAGS.getGroup().getTag(Identifier.tryParse(JsonHelper.getString(object, "tag")));
        Type type = Type.get(JsonHelper.getString(object, "type", "block"));
        InTagCondition condition;
        if (type == Type.BLOCK) {
            condition = new InTagCondition(tag, type, Registry.BLOCK.get(Identifier.tryParse(JsonHelper.getString(object, "block"))));
        } else if (type == Type.ITEM) {
            condition = new InTagCondition(tag, type, JsonHelper.getItem(object, "item"));
        } else if (type == Type.ENTITY_TYPE) {
            condition = new InTagCondition(tag, type, Registry.ENTITY_TYPE.get(Identifier.tryParse(JsonHelper.getString(object, "entity_type"))));
        } else condition = null;
        return condition;
    }

    @Override
    public boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        if (type == Type.BLOCK && block != null) {
            return state.isIn((Tag<Block>) tag);
        } else return false;
    }

    @Override
    public boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        if (type == Type.ITEM && this.item != null) {
            return stack.isIn((Tag<Item>) tag);
        } else return false;
    }

    @Override
    public boolean testEntity(Entity entity, PlayerEntity player, Hand hand) {
        if (type == Type.ENTITY_TYPE && entityType != null) {
            return entity.getType().isIn((Tag<EntityType<?>>) tag);
        } else return false;
    }

    @Override
    public boolean testEnchantment(Entity user, Entity target, int level) {
        return false;
    }

    @Override
    public boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        return false;
    }

    public enum Type {
        BLOCK("block"),
        ITEM("item"),
        ENTITY_TYPE("entity_type");

        private final String id;

        Type(String id) {
            this.id = id;
        }

        public static Type get(String str) {
            return str.equals("block") ? BLOCK : str.equals("item") ? ITEM : ENTITY_TYPE;
        }
    }
}
