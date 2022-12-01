package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.EntityParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.WorldParameter;
import net.minecraft.advancement.Advancement;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;

public class CompleteAdvancementEffect extends AbstractEffect {
    private Identifier advancementId;

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of(EventParameters.WORLD, EventParameters.ENTITY);
    }

    public AbstractEffect getFromJson(JsonObject object) {
        CompleteAdvancementEffect effect = new CompleteAdvancementEffect();
        effect.advancementId = Identifier.tryParse(object.get("advancement").getAsString());
        return effect;
    }

    public void run(List<EventParameter> parameters) {
        WorldParameter worldParameter = (WorldParameter) EventParameter.find(parameters, getModifications(), EventParameters.WORLD);
        if (worldParameter != null) {
            World world = worldParameter.getWorld();
            if (!world.isClient) {
                EntityParameter entityParameter = (EntityParameter) EventParameter.find(parameters, getModifications(), EventParameters.ENTITY);
                if (entityParameter != null) {
                    if (entityParameter.getEntity() instanceof ServerPlayerEntity serverPlayerEntity) {
                        Advancement advancement = ((ServerWorld) world).getServer().getAdvancementLoader().get(advancementId);
                        for (String string : serverPlayerEntity.getAdvancementTracker().getProgress(advancement).getUnobtainedCriteria()) {
                            serverPlayerEntity.getAdvancementTracker().grantCriterion(advancement, string);
                        }
                    }
                }
            }
        }
    }
}
