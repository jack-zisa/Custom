package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.ObjectParameter;
import creoii.custom.eventsystem.parameter.StringParameter;
import creoii.custom.util.tags.TagUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class InTagCondition extends Condition {
    private String tag;

    public InTagCondition withValues(String tag) {
        this.tag = tag;
        return this;
    }

    public InTagCondition getFromJson(JsonObject object) {
        return null;
    }

    @Override
    public EventParameter[] getParameters() {
        return new EventParameter[]{EventParameters.OBJECT, EventParameters.STRING};
    }

    @Override
    public boolean test(EventParameter[] parameters) {
        if (validate(parameters)) {
            ObjectParameter objectParameter = (ObjectParameter) parameters[0];
            StringParameter stringParameter = (StringParameter) parameters[1];
            Identifier identifier = Identifier.tryParse(stringParameter.getString());

            Object obj = objectParameter.getObject();
            if (obj instanceof Block block) {
                return block.getDefaultState().isIn(TagKey.of(Registry.BLOCK_KEY, identifier));
            } else if (obj instanceof Item item) {
                return item.getDefaultStack().isIn(TagKey.of(Registry.ITEM_KEY, identifier));
            } else if (obj instanceof EntityType<?> entityType) {
                return entityType.isIn(TagKey.of(Registry.ENTITY_TYPE_KEY, identifier));
            } else if (obj instanceof Biome biome) {
                return TagUtil.isBiomeIn(biome, identifier);
            } else if (obj instanceof Enchantment enchantment) {
                return TagUtil.isEnchantmentIn(enchantment, identifier);
            } else if (obj instanceof StatusEffect statusEffect) {
                return TagUtil.isStatusEffectIn(statusEffect, identifier);
            }
        }
        return false;
    }

    @Override
    public boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        return state.isIn(TagKey.of(Registry.BLOCK_KEY, Identifier.tryParse(tag)));
    }

    @Override
    public boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        return player.getStackInHand(hand).isIn(TagKey.of(Registry.ITEM_KEY, Identifier.tryParse(tag)));
    }

    @Override
    public boolean testEntity(Entity entity, PlayerEntity player, Hand hand) {
        return entity.getType().isIn(TagKey.of(Registry.ENTITY_TYPE_KEY, Identifier.tryParse(tag)));
    }

    @Override
    public boolean testEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        return TagUtil.isEnchantmentIn(enchantment, Identifier.tryParse(tag));
    }

    @Override
    public boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        return TagUtil.isStatusEffectIn(statusEffect, Identifier.tryParse(tag));
    }

    @Override
    public boolean testWorld(World world) {
        return false;
    }
}
