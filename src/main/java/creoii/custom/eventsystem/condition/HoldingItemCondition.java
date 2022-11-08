package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.*;
import creoii.custom.util.StringToObject;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HoldingItemCondition extends Condition {
    private final Item item = Items.PAPER;
    private final Hand hand = Hand.MAIN_HAND;

    public HoldingItemCondition getFromJson(JsonObject object) {
        return null;
    }

    @Override
    public EventParameter[] getParameters() {
        return new EventParameter[]{EventParameters.ENTITY, EventParameters.STRING, EventParameters.ITEMSTACK};
    }

    @Override
    public boolean test(EventParameter[] parameters) {
        if (validate(parameters)) {
            EntityParameter entityParameter = (EntityParameter) parameters[0];
            if (entityParameter.getEntity() instanceof PlayerEntity playerEntity) {
                StringParameter stringParameter = (StringParameter) parameters[1];
                ItemStackParameter itemStackParameter = (ItemStackParameter) parameters[2];
                return playerEntity.getStackInHand(StringToObject.hand(stringParameter.getString())) == itemStackParameter.getItemStack();
            }
        }
        return false;
    }

    private boolean test(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            return livingEntity.getStackInHand(this.hand).isOf(item);
        }
        return false;
    }

    @Override
    public boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        return test(living);
    }

    @Override
    public boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        return test(player);
    }

    @Override
    public boolean testEntity(Entity entity, PlayerEntity player, Hand hand) {
        return test(entity);
    }

    @Override
    public boolean testEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        return test(user);
    }

    @Override
    public boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        return test(entity);
    }

    @Override
    public boolean testWorld(World world) {
        return false;
    }
}
