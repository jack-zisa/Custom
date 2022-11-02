package creoii.custom.custom;

import com.google.gson.*;
import creoii.custom.data.Identifiable;
import creoii.custom.util.json.CustomJsonHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.jetbrains.annotations.Nullable;

public class CustomMaterial implements Identifiable {
    private final Identifier identifier;
    private final Type type;
    private final CustomArmorMaterial armorMaterial;
    private final CustomToolMaterial toolMaterial;

    public CustomMaterial(Identifier identifier, Type type, @Nullable CustomArmorMaterial armorMaterial, @Nullable CustomToolMaterial toolMaterial) {
        this.identifier = identifier;
        this.type = type;
        this.armorMaterial = armorMaterial;
        this.toolMaterial = toolMaterial;
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    public static class CustomArmorMaterial implements ArmorMaterial {
        private String name;
        private int durability;
        private int protection;
        private int enchantability;
        private SoundEvent equipSound;
        private ItemStack repairItem;
        private float toughness;
        private float knockbackResistance;

        public CustomArmorMaterial(String name,
                                   int durability, int protection, int enchantability,
                                   SoundEvent equipSound, ItemStack repairItem,
                                   float toughness, float knockbackResistance
        ) {
            this.name = name;
            this.durability = durability;
            this.protection = protection;
            this.enchantability = enchantability;
            this.equipSound = equipSound;
            this.repairItem = repairItem;
            this.toughness = toughness;
            this.knockbackResistance = knockbackResistance;
        }

        @Override
        public int getDurability(EquipmentSlot slot) {
            return durability;
        }

        @Override
        public int getProtectionAmount(EquipmentSlot slot) {
            return protection;
        }

        @Override
        public int getEnchantability() {
            return enchantability;
        }

        @Override
        public SoundEvent getEquipSound() {
            return equipSound;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.ofStacks(repairItem);
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public float getToughness() {
            return toughness;
        }

        @Override
        public float getKnockbackResistance() {
            return knockbackResistance;
        }
    }

    public static class CustomToolMaterial implements ToolMaterial {
        private int durability;
        private int miningLevel;
        private int enchantability;
        private float miningSpeedMultiplier;
        private float attackDamage;
        private ItemStack repairItem;

        public CustomToolMaterial(
                int durability, int miningLevel, int enchantability,
                float miningSpeedMultiplier, float attackDamage,
                ItemStack repairItem
        ) {
            this.durability = durability;
            this.miningLevel = miningLevel;
            this.enchantability = enchantability;
            this.miningSpeedMultiplier = miningSpeedMultiplier;
            this.attackDamage = attackDamage;
            this.repairItem = repairItem;
        }

        @Override
        public int getDurability() {
            return durability;
        }

        @Override
        public float getMiningSpeedMultiplier() {
            return miningSpeedMultiplier;
        }

        @Override
        public float getAttackDamage() {
            return attackDamage;
        }

        @Override
        public int getMiningLevel() {
            return miningLevel;
        }

        @Override
        public int getEnchantability() {
            return enchantability;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.ofStacks(repairItem);
        }
    }

    public static class Serializer implements JsonDeserializer<CustomMaterial>, JsonSerializer<CustomMaterial> {
        @Override
        public CustomMaterial deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = JsonHelper.asObject(json, "material");
            Identifier identifier = Identifier.tryParse(JsonHelper.getString(object, "identifier"));
            CustomMaterial.Type matType = CustomMaterial.Type.valueOf(JsonHelper.getString(object, "type", ""));
            CustomArmorMaterial armorMaterial = CustomJsonHelper.getCustomArmorMaterial(object, "armor_material");
            CustomToolMaterial toolMaterial = CustomJsonHelper.getCustomToolMaterial(object, "tool_material");
            return new CustomMaterial(identifier, matType, armorMaterial, toolMaterial);
        }

        @Override
        public JsonElement serialize(CustomMaterial src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
            return null;
        }
    }

    private enum Type {
        ARMOR("armor"),
        TOOL("tool");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
