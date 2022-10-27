package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.Optional;

public class InTagCondition extends Condition {
    private String tag;

    public InTagCondition withValues(String tag) {
        this.tag = tag;
        return this;
    }

    public InTagCondition getFromJson(JsonObject object) {
        String tag = object.get("tag").getAsString();
        return withValues(tag);
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
        RegistryKey<Enchantment> key = RegistryKey.of(Registry.ENCHANTMENT.getKey(), Registry.ENCHANTMENT.getId(enchantment));
        Optional<RegistryEntry<Enchantment>> entry = Registry.ENCHANTMENT.getEntry(key);
        return entry.isPresent() && entry.get().isIn(TagKey.of(Registry.ENCHANTMENT.getKey(), Identifier.tryParse(tag)));
    }

    @Override
    public boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        RegistryKey<StatusEffect> key = RegistryKey.of(Registry.STATUS_EFFECT.getKey(), Registry.STATUS_EFFECT.getId(statusEffect));
        Optional<RegistryEntry<StatusEffect>> entry = Registry.STATUS_EFFECT.getEntry(key);
        return entry.isPresent() && entry.get().isIn(TagKey.of(Registry.STATUS_EFFECT.getKey(), Identifier.tryParse(tag)));
    }

    @Override
    public boolean testWorld(World world) {
        return false;
    }
}
