package creoii.custom.util.tags;

import creoii.custom.Custom;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class EnchantmentTags {
    public static TagKey<Enchantment> CURSED = TagKey.of(RegistryKeys.ENCHANTMENT, new Identifier(Custom.NAMESPACE, "cursed"));
    public static TagKey<Enchantment> TREASURE = TagKey.of(RegistryKeys.ENCHANTMENT, new Identifier(Custom.NAMESPACE, "treasure"));
    public static TagKey<Enchantment> PROTECTION = TagKey.of(RegistryKeys.ENCHANTMENT, new Identifier(Custom.NAMESPACE, "protection"));
    public static TagKey<Enchantment> SHARPNESS = TagKey.of(RegistryKeys.ENCHANTMENT, new Identifier(Custom.NAMESPACE, "sharpness"));
}