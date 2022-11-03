package creoii.custom.util.tags;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;

import java.util.Optional;

public class TagUtil {
    public static boolean isEnchantmentIn(Enchantment enchantment, Identifier tag) {
        RegistryKey<Enchantment> key = RegistryKey.of(Registry.ENCHANTMENT.getKey(), Registry.ENCHANTMENT.getId(enchantment));
        Optional<RegistryEntry<Enchantment>> entry = Registry.ENCHANTMENT.getEntry(key);
        return entry.isPresent() && entry.get().isIn(TagKey.of(Registry.ENCHANTMENT.getKey(), tag));
    }

    public static boolean isStatusEffectIn(StatusEffect statusEffect, Identifier tag) {
        RegistryKey<StatusEffect> key = RegistryKey.of(Registry.STATUS_EFFECT.getKey(), Registry.STATUS_EFFECT.getId(statusEffect));
        Optional<RegistryEntry<StatusEffect>> entry = Registry.STATUS_EFFECT.getEntry(key);
        return entry.isPresent() && entry.get().isIn(TagKey.of(Registry.STATUS_EFFECT.getKey(), tag));
    }
}
