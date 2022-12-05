package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.EntityParameter;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.eventsystem.parameter.WorldParameter;
import creoii.custom.util.json.CustomJsonHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

public class ApplyStatusEffectEffect extends AbstractEffect {
    private StatusEffect statusEffect;
    private IntProvider amplifier;
    private IntProvider duration;
    private boolean ambient;
    private boolean showParticles;
    private boolean showIcon;

    @Override
    public List<EventParameter> getRequiredParameters() {
        return List.of(EventParameters.WORLD, EventParameters.ENTITY);
    }

    public AbstractEffect getFromJson(JsonObject object) {
        ApplyStatusEffectEffect effect = new ApplyStatusEffectEffect();
        effect.statusEffect = Registry.STATUS_EFFECT.get(Identifier.tryParse(CustomJsonHelper.getString(object, new String[]{"status_effect", "effect"})));
        effect.amplifier = CustomJsonHelper.getIntProvider(object, "amplifier", 0);
        effect.duration = CustomJsonHelper.getIntProvider(object, "duration", 0);
        effect.ambient = JsonHelper.getBoolean(object, "ambient", false);
        effect.showParticles = JsonHelper.getBoolean(object, "show_particles", true);
        effect.showIcon = JsonHelper.getBoolean(object, "show_icon", true);
        return effect;
    }

    public void run(List<EventParameter> parameters) {
        WorldParameter worldParameter = (WorldParameter) EventParameter.find(parameters, getModifications(), EventParameters.WORLD);
        if (worldParameter != null) {
            World world = worldParameter.getWorld();
            if (!world.isClient) {
                EntityParameter entityParameter = (EntityParameter) EventParameter.find(parameters, getModifications(), EventParameters.ENTITY);
                if (entityParameter != null) {
                    if (entityParameter.getEntity() instanceof LivingEntity livingEntity) {
                        System.out.println("Apply status effect to " + livingEntity.getType().toString());
                        livingEntity.addStatusEffect(new StatusEffectInstance(statusEffect, amplifier.get(world.getRandom()), duration.get(world.getRandom()), ambient, showParticles, showIcon));
                    }
                }
            }
        }
    }
}
