package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.util.StringToObject;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class HasEnchantmentCondition extends Condition {
    private final Enchantment enchantment;
    private final int level;
    private final EquipmentSlot equipmentSlot;
    private final boolean affectTarget;

    public HasEnchantmentCondition(Enchantment enchantment, int level, EquipmentSlot equipmentSlot, boolean affectTarget) {
        super(Condition.HAS_ENCHANTMENT);
        this.enchantment = enchantment;
        this.level = level;
        this.equipmentSlot = equipmentSlot;
        this.affectTarget = affectTarget;
    }

    public static Condition getFromJson(JsonObject object) {
        Enchantment enchantment = Registry.ENCHANTMENT.get(Identifier.tryParse(JsonHelper.getString(object , "enchantment")));
        int level = JsonHelper.getInt(object, "level", 1);
        EquipmentSlot equipmentSlot = StringToObject.equipmentSlot(JsonHelper.getString(object, "equipment_slot"));
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target", false);
        return new HasEnchantmentCondition(enchantment, level, equipmentSlot, affectTarget);
    }

    private boolean test(ItemStack stack) {
        int level1 = EnchantmentHelper.getLevel(enchantment, stack);
        return level1 >= level;
    }

    @Override
    public boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        return false;
    }

    @Override
    public boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        return test(stack);
    }

    @Override
    public boolean testEntity(Entity entity, PlayerEntity player, Hand hand) {
        if (equipmentSlot != null) {
            return test(player.getEquippedStack(equipmentSlot));
        } else return false;
    }

    @Override
    public boolean testEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        if (equipmentSlot != null) {
            Entity entity = affectTarget ? user : target;
            if (entity instanceof LivingEntity livingEntity)
                return test(livingEntity.getEquippedStack(equipmentSlot));
        }
        return false;
    }

    @Override
    public boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        if (equipmentSlot != null) {
            return test(entity.getEquippedStack(equipmentSlot));
        } else return false;
    }

    @Override
    public boolean testWorld(World world) {
        return false;
    }
}
