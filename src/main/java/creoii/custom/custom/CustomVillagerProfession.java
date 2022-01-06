package creoii.custom.custom;

import com.google.common.collect.ImmutableSet;
import com.google.gson.*;
import creoii.custom.data.CustomObject;
import creoii.custom.util.StringToObject;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;

public class CustomVillagerProfession extends VillagerProfession implements CustomObject {
    private final Identifier identifier;

    public CustomVillagerProfession(Identifier identifier, PointOfInterestType workStation, ImmutableSet<Item> gatherableItems, ImmutableSet<Block> secondaryJobSites, @Nullable SoundEvent workSound) {
        super(identifier.getPath(), workStation, gatherableItems, secondaryJobSites, workSound);
        this.identifier = identifier;

        Registry.register(Registry.VILLAGER_PROFESSION, identifier, this);
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    public static class Serializer implements JsonDeserializer<CustomVillagerProfession>, JsonSerializer<CustomVillagerProfession> {
        @Override
        public CustomVillagerProfession deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = JsonHelper.asObject(json, "villager profession");
            Identifier identifier = Identifier.tryParse(JsonHelper.getString(object, "identifier"));
            SoundEvent workSound = StringToObject.workSoundEvent(JsonHelper.getString(object, "work_sound", "work_farmer"));
            return new CustomVillagerProfession(identifier, PointOfInterestType.ARMORER, ImmutableSet.of(), ImmutableSet.of(), workSound);
        }

        @Override
        public JsonElement serialize(CustomVillagerProfession src, Type typeOfSrc, JsonSerializationContext context) {
            return null;
        }
    }
}
