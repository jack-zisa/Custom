package creoii.custom.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import creoii.custom.custom.CustomEnchantment;
import creoii.custom.custom.CustomItem;

import java.io.Reader;

public class EnchantmentsManager extends AbstractDataManager<CustomEnchantment> {
    public EnchantmentsManager() {
        super("enchantments", new GsonBuilder().setPrettyPrinting().registerTypeAdapter(CustomEnchantment.class, new CustomEnchantment.Serializer()).create());
    }

    @Override
    CustomEnchantment createCustomObject(Reader reader, Gson gson) {
        return gson.fromJson(reader, CustomEnchantment.class);
    }
}
