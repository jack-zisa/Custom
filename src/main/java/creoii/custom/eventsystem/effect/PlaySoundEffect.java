package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.BlockPosParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.WorldParameter;
import creoii.custom.util.StringToObject;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class PlaySoundEffect extends AbstractEffect {
    private SoundEvent soundEvent;
    private SoundCategory soundCategory;
    private float volume;
    private float pitch;

    @Override
    public List<EventParameter> getParameters() {
        return List.of(EventParameters.WORLD, EventParameters.BLOCK_POS);
    }

    public PlaySoundEffect getFromJson(JsonObject object) {
        PlaySoundEffect effect = new PlaySoundEffect();
        effect.soundEvent = Registry.SOUND_EVENT.get(Identifier.tryParse(object.get("sound").getAsString()));
        effect.soundCategory = SoundCategory.valueOf(JsonHelper.getString(object, "category"));
        effect.volume = JsonHelper.getFloat(object, "volume", 0f);
        effect.pitch = JsonHelper.getFloat(object, "pitch", 0f);
        return effect;
    }

    public void run(List<EventParameter> parameters) {
        WorldParameter worldParameter = (WorldParameter) EventParameter.find(parameters, getModifications(), EventParameters.WORLD);
        if (worldParameter != null) {
            BlockPosParameter blockPosParameter = (BlockPosParameter) EventParameter.find(parameters, getModifications(), EventParameters.BLOCK_POS);
            if (blockPosParameter != null) {
                worldParameter.getWorld().playSound(null, blockPosParameter.getPos().getX(), blockPosParameter.getPos().getY(), blockPosParameter.getPos().getZ(), soundEvent, soundCategory, volume, pitch);
            }
        }
    }
}
