package creoii.custom.custom;

import com.google.gson.*;
import creoii.custom.data.CustomObject;
import net.fabricmc.fabric.impl.item.group.ItemGroupExtensions;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.lang.reflect.Type;

public class CustomItemGroup implements CustomObject {
    private final Identifier identifier;
    private final ItemStack icon;
    //private final Item[] defaultItems;
    private final boolean hasScrollbar;
    private final boolean renderName;

    public CustomItemGroup(Identifier identifier, ItemStack icon, boolean hasScrollbar, boolean renderName) {
        this.identifier = identifier;
        this.icon = icon;
        this.hasScrollbar = hasScrollbar;
        this.renderName = renderName;
        ((ItemGroupExtensions) ItemGroup.BUILDING_BLOCKS).fabric_expandArray();
        new ItemGroup(ItemGroup.GROUPS.length - 1, String.format("%s.%s", identifier.getNamespace(), identifier.getPath())) {
            @Override
            public ItemStack createIcon() {
                return icon;
            }

            @Override
            public boolean shouldRenderName() {
                return renderName;
            }

            @Override
            public boolean hasScrollbar() {
                return hasScrollbar;
            }
        };
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    public boolean hasScrollbar() {
        return hasScrollbar;
    }

    public boolean shouldRenderName() {
        return renderName;
    }

    public static class Serializer implements JsonDeserializer<CustomItemGroup>, JsonSerializer<CustomItemGroup> {
        @Override
        public CustomItemGroup deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = JsonHelper.asObject(json, "item group");
            ItemStack icon = new ItemStack(JsonHelper.getItem(object, "icon", Items.AIR));
            boolean hasScrollbar = JsonHelper.getBoolean(object, "has_scrollbar", false);
            boolean renderName = JsonHelper.getBoolean(object, "render_name", true);
            return new CustomItemGroup(
                    Identifier.tryParse(JsonHelper.getString(object, "identifier")), icon,
                    hasScrollbar, renderName
            );
        }

        @Override
        public JsonElement serialize(CustomItemGroup src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.addProperty("identifier", src.getIdentifier().toString());
            object.add("icon", JsonHelper.deserialize("icon"));
            object.addProperty("has_scrollbar", src.hasScrollbar());
            object.addProperty("render_name", src.shouldRenderName());
            return object;
        }
    }
}
