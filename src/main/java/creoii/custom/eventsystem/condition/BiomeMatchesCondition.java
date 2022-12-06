package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.BlockPosParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.WorldParameter;
import creoii.custom.util.json.CustomJsonHelper;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.List;
import java.util.Optional;

public class BiomeMatchesCondition extends Condition {
    private Identifier biomeId;

    @Override
    public BiomeMatchesCondition getFromJson(JsonObject object) {
        BiomeMatchesCondition condition = new BiomeMatchesCondition();
        condition.biomeId = Identifier.tryParse(CustomJsonHelper.getString(object, new String[]{"biome", "biome_id"}));
        return condition;
    }

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of(EventParameters.WORLD, EventParameters.BLOCK_POS);
    }

    @Override
    public boolean test(List<EventParameter> parameters) {
        WorldParameter worldParameter = (WorldParameter) EventParameter.find(parameters, EventParameters.WORLD);
        if (worldParameter != null) {
            BlockPosParameter blockPosParameter = (BlockPosParameter) EventParameter.find(parameters, EventParameters.BLOCK_POS);
            if (blockPosParameter != null) {
                RegistryEntry<Biome> biome = worldParameter.getWorld().getBiome(blockPosParameter.getPos());
                if (biome.hasKeyAndValue()) {
                    Optional<RegistryKey<Biome>> key = biome.getKey();
                    if (key.isPresent()) {
                        return key.get().getValue().equals(biomeId);
                    }
                }
            }
        }
        return false;
    }
}
