package creoii.custom.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import creoii.custom.objects.CustomEnchantment;

import java.io.Reader;

public class EnchantmentsLoader extends AbstractDataLoader<CustomEnchantment> {
    public EnchantmentsLoader() {
        super("enchantments", new GsonBuilder().setPrettyPrinting().registerTypeAdapter(CustomEnchantment.class, new CustomEnchantment.Serializer()).create());
    }

    @Override
    CustomEnchantment createCustomObject(Reader reader, Gson gson) {
        return gson.fromJson(reader, CustomEnchantment.class);
    }
}
