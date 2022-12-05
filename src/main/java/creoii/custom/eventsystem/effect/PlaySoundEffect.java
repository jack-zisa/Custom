package creoii.custom.eventsystem.effect;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import creoii.custom.Custom;
import creoii.custom.eventsystem.parameter.BlockPosParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.WorldParameter;
import creoii.custom.util.json.CustomJsonHelper;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

public class PlaySoundEffect extends AbstractEffect {
    private SoundEntry[] entries;
    private SoundEvent soundEvent;
    private SoundCategory soundCategory;
    private FloatProvider volume;
    private FloatProvider pitch;

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of(EventParameters.WORLD, EventParameters.BLOCK_POS);
    }

    public PlaySoundEffect getFromJson(JsonObject object) {
        PlaySoundEffect effect = new PlaySoundEffect();
        if (object.has("entries")) {
            JsonArray array = object.get("entries").getAsJsonArray();
            SoundEntry[] entries = new SoundEntry[array.size()];
            for (int i = 0; i < array.size(); ++i) {
                JsonElement element = array.get(i);
                if (element.isJsonObject()) {
                    JsonObject entryObj = element.getAsJsonObject();
                    entries[i] = new SoundEntry(
                            Registry.SOUND_EVENT.get(Identifier.tryParse(CustomJsonHelper.getString(object, new String[]{"sound", "sound_event"}))),
                            SoundCategory.valueOf(JsonHelper.getString(object, "category")),
                            CustomJsonHelper.getFloatProvider(entryObj, "volume", 0f),
                            CustomJsonHelper.getFloatProvider(entryObj, "pitch", 0f)
                    );
                }
            }
            effect.entries = entries;
        } else {
            effect.soundEvent = Registry.SOUND_EVENT.get(Identifier.tryParse(CustomJsonHelper.getString(object, new String[]{"sound", "sound_event"})));
            effect.soundCategory = SoundCategory.valueOf(JsonHelper.getString(object, "category"));
            effect.volume = CustomJsonHelper.getFloatProvider(object, "volume", 0f);
            effect.pitch = CustomJsonHelper.getFloatProvider(object, "pitch", 0f);
        }
        return effect;
    }

    public void run(List<EventParameter> parameters) {
        WorldParameter worldParameter = (WorldParameter) EventParameter.find(parameters, getModifications(), EventParameters.WORLD);
        if (worldParameter != null) {
            BlockPosParameter blockPosParameter = (BlockPosParameter) EventParameter.find(parameters, getModifications(), EventParameters.BLOCK_POS);
            if (blockPosParameter != null) {
                World world = worldParameter.getWorld();
                BlockPos pos = blockPosParameter.getPos();
                if (entries != null) {
                    for (SoundEntry entry : entries) {
                        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), entry.soundEvent, entry.category, entry.volume.get(world.getRandom()), entry.pitch.get(world.getRandom()));
                    }
                } else {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), soundEvent, soundCategory, volume.get(world.getRandom()), pitch.get(world.getRandom()));
                }
            }
        }
    }

    private record SoundEntry(SoundEvent soundEvent, SoundCategory category, FloatProvider pitch, FloatProvider volume) {}
}
