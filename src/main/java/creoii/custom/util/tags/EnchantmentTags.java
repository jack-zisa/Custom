package creoii.custom.util.tags;

import creoii.custom.Custom;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public final class EnchantmentTags {
    public static TagKey<Enchantment> CURSED = TagKey.of(Registry.ENCHANTMENT_KEY, new Identifier(Custom.NAMESPACE, "cursed"));
    public static TagKey<Enchantment> TREASURE = TagKey.of(Registry.ENCHANTMENT_KEY, new Identifier(Custom.NAMESPACE, "treasure"));
}