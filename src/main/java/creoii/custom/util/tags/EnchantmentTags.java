package creoii.custom.util.tags;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.tag.RequiredTagList;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagGroup;

public final class EnchantmentTags {
    public static RequiredTagList<Enchantment> REQUIRED_TAGS;
    //public static Tag.Identified<Enchantment> CURSED;
    public static Tag.Identified<Enchantment> TREASURE;

    public static Tag.Identified<Enchantment> enchantment(String name) {
        return REQUIRED_TAGS.add(name);
    }

    public static TagGroup<Enchantment> getTagGroup() {
        return REQUIRED_TAGS.getGroup();
    }

    static {
        //REQUIRED_TAGS = RequiredTagListRegistry.register(Registry.ENCHANTMENT_KEY, "tags/enchantments");
        //CURSED = enchantment("cursed");
        //TREASURE = enchantment("treasure");
    }
}