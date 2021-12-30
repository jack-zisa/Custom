package creoii.custom.custom;

import com.google.gson.*;
import creoii.custom.json.CustomObject;
import creoii.custom.util.StringToObject;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

import java.lang.reflect.Type;

public class CustomEnchantment extends Enchantment implements CustomObject {
    private final Identifier identifier;
    private final boolean offeredByLibrarians;
    private final boolean randomlySelectable;
    private final int minPlayerLevel;
    private final int maxPlayerLevel;
    private final int maxLevel;
    private final Identifier[] blacklist;

    public CustomEnchantment(
            Identifier identifier,
            Rarity rarity, EnchantmentTarget type, EquipmentSlot[] slotTypes,
            boolean offeredByLibrarians, boolean randomlySelectable,
            int minPlayerLevel, int maxPlayerLevel, int maxLevel,
            Identifier[] blacklist
    ) {
        super(rarity, type, slotTypes);
        this.identifier = identifier;
        this.offeredByLibrarians = offeredByLibrarians;
        this.randomlySelectable = randomlySelectable;
        this.minPlayerLevel = minPlayerLevel;
        this.maxPlayerLevel = maxPlayerLevel;
        this.maxLevel = maxLevel;
        this.blacklist = blacklist;

        Registry.register(Registry.ENCHANTMENT, identifier, this);
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    public static class Serializer implements JsonDeserializer<CustomEnchantment>, JsonSerializer<CustomEnchantment> {
        @Override
        public CustomEnchantment deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = JsonHelper.asObject(json, "enchantment");
            Identifier identifier = Identifier.tryParse(JsonHelper.getString(object, "identifier"));
            Rarity rarity = StringToObject.enchantmentRarity(JsonHelper.getString(object, "rarity", "common"));
            EnchantmentTarget target = StringToObject.enchantmentTarget(JsonHelper.getString(object, "target", "breakable"));
            EquipmentSlot[] slots;
            if (JsonHelper.hasArray(object, "equipment_slots")) {
                JsonArray array = JsonHelper.getArray(object, "equipment_slots");
                slots = new EquipmentSlot[array.size()];
                for (int i = 0; i < slots.length; ++i) {
                    if (array.get(i).isJsonPrimitive()) slots[i] = StringToObject.equipmentSlot(array.get(i).getAsString());
                }
            } else slots = new EquipmentSlot[]{};
            boolean offeredByLibrarians = JsonHelper.getBoolean(object, "offered_by_librarians", true);
            boolean randomlySelectable = JsonHelper.getBoolean(object, "randomly_selectable", true);
            int minPlayerLevel = JsonHelper.getInt(object, "min_player_level", 1);
            int maxPlayerLevel = JsonHelper.getInt(object, "max_player_level", 30);
            int maxLevel = JsonHelper.getInt(object, "max_level", 1);
            Identifier[] blacklist;
            if (JsonHelper.hasArray(object, "blacklist")) {
                JsonArray array = JsonHelper.getArray(object, "blacklist");
                blacklist = new Identifier[array.size()];
                for (int i = 0; i < blacklist.length; ++i) {
                    if (array.get(i).isJsonPrimitive()) blacklist[i] = Identifier.tryParse(array.get(i).getAsString());
                }
            } else blacklist = new Identifier[]{};
            return new CustomEnchantment(identifier, rarity, target, slots,
                    offeredByLibrarians, randomlySelectable,
                    minPlayerLevel, maxPlayerLevel, maxLevel,
                    blacklist
            );
        }

        @Override
        public JsonElement serialize(CustomEnchantment src, Type typeOfSrc, JsonSerializationContext context) {
            return null;
        }
    }
}
