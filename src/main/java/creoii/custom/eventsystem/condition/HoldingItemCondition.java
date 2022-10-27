package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
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
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HoldingItemCondition extends Condition {
    private Item item;
    private Hand hand;
    private boolean affectTarget;

    public HoldingItemCondition withValues(Item item, Hand hand, boolean affectTarget) {
        this.item = item;
        this.hand = hand;
        this.affectTarget = affectTarget;
        return this;
    }

    public HoldingItemCondition getFromJson(JsonObject object) {
        Item item = JsonHelper.getItem(object, "item", Items.AIR);
        Hand hand = StringToObject.hand(JsonHelper.getString(object, "hand", "mainhand"));
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target", false);
        return withValues(item, hand, affectTarget);
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
        return test(affectTarget ? target : user);
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
