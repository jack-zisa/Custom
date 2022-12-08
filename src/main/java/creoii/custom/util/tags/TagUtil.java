package creoii.custom.util.tags;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class TagUtil {
    public static boolean isBiomeIn(Biome biome, Identifier tag) {
        RegistryEntry<Biome> entry = BuiltinRegistries.BIOME.getEntry(biome);
        return entry.isIn(TagKey.of(BuiltinRegistries.BIOME.getKey(), tag));
    }

    public static boolean isEnchantmentIn(Enchantment enchantment, Identifier tag) {
        RegistryEntry<Enchantment> entry = Registries.ENCHANTMENT.getEntry(enchantment);
        return entry.isIn(TagKey.of(Registries.ENCHANTMENT.getKey(), tag));
    }

    public static boolean isStatusEffectIn(StatusEffect statusEffect, Identifier tag) {
        RegistryEntry<StatusEffect> entry = Registries.STATUS_EFFECT.getEntry(statusEffect);
        return entry.isIn(TagKey.of(Registries.STATUS_EFFECT.getKey(), tag));
    }
}
