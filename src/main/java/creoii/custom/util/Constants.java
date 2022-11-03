package creoii.custom.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    /* Controls eating speeds for custom and vanilla foods */
    public static final Map<Identifier, Integer> FOOD_EATING_SPEEDS = new HashMap<>();

    /* Formatted in Gold */
    public static Rarity LEGENDARY;
    /* Formatted in Black */
    public static Rarity MYTHICAL;
    /* Obfuscated */
    public static Rarity UNKNOWN;

    /* Has a weight of 20 */
    public static Enchantment.Rarity VERY_COMMON;
}
