package creoii.custom.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.tag.Tag;

public class EnchantmentUtil {
    public static boolean isIn(Enchantment enchantment, Tag<Enchantment> tag) {
        return tag.contains(enchantment);
    }
}
