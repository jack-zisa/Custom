package creoii.custom.util;

import net.fabricmc.fabric.impl.item.group.ItemGroupExtensions;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class ItemGroupUtil {
    public static ItemGroup build(Identifier identifier, Supplier<ItemStack> stackSupplier, boolean hasScrollBar, boolean renderName) {
        ((ItemGroupExtensions) ItemGroup.BUILDING_BLOCKS).fabric_expandArray();
        return new ItemGroup(ItemGroup.GROUPS.length - 1, String.format("%s.%s", identifier.getNamespace(), identifier.getPath())) {
            @Override
            public ItemStack createIcon() {
                return stackSupplier.get();
            }

            @Override
            public boolean shouldRenderName() {
                return renderName;
            }

            @Override
            public boolean hasScrollbar() {
                return hasScrollBar;
            }
        };
    }
}
