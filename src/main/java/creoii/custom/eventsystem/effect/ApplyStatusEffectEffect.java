package creoii.custom.eventsystem.effect;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stat.Stat;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ApplyStatusEffectEffect extends Effect {
    private final StatusEffect statusEffect;
    private final StatusEffect hiddenEffect;
    private final int amplifier;
    private final int duration;
    private final boolean ambient;
    private final boolean showParticles;
    private final boolean showIcon;
    private final EnchantmentOptions enchantmentOptions;

    public ApplyStatusEffectEffect(StatusEffect statusEffect, StatusEffect hiddenEffect,
                                   int amplifier, int duration,
                                   boolean ambient, boolean showParticles, boolean showIcon, EnchantmentOptions enchantmentOptions
    ) {
        super(Effect.DROP_ITEM);
        this.statusEffect = statusEffect;
        this.hiddenEffect = hiddenEffect;
        this.amplifier = amplifier;
        this.duration = duration;
        this.ambient = ambient;
        this.showParticles = showParticles;
        this.showIcon = showIcon;
        this.enchantmentOptions = enchantmentOptions;
    }

    public static Effect getFromJson(JsonObject object) {
        StatusEffect statusEffect = Registry.STATUS_EFFECT.get(Identifier.tryParse(JsonHelper.getString(object, "status_effect")));
        StatusEffect hiddenEffect = Registry.STATUS_EFFECT.get(Identifier.tryParse(JsonHelper.getString(object, "hidden_effect")));
        int amplifier = JsonHelper.getInt(object, "amplifier", 0);
        int duration = JsonHelper.getInt(object, "duration", 1);
        boolean ambient = JsonHelper.getBoolean(object, "ambient", false);
        boolean showParticles = JsonHelper.getBoolean(object, "show_particles", true);
        boolean showIcon = JsonHelper.getBoolean(object, "show_icon", true);
        EnchantmentOptions enchantmentOptions = EnchantmentOptions.get(object, "enchantment_options");
        return new ApplyStatusEffectEffect(statusEffect, hiddenEffect, amplifier, duration, ambient, showParticles, showIcon, enchantmentOptions);
    }

    @Override
    public void runBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        living.addStatusEffect(new StatusEffectInstance(statusEffect, amplifier, duration, ambient, showParticles, showIcon));
    }

    @Override
    public void runItem(World world, Item item, BlockPos pos, PlayerEntity player, Hand hand) {
        player.addStatusEffect(new StatusEffectInstance(statusEffect, amplifier, duration, ambient, showParticles, showIcon));
    }

    @Override
    public void runEntity(Entity entity, PlayerEntity player, Hand hand) {
        player.addStatusEffect(new StatusEffectInstance(statusEffect, amplifier, duration, ambient, showParticles, showIcon));
    }

    @Override
    public void runEnchantment(Entity user, Entity target, int level) {
        if (enchantmentOptions.useTargetPosition() && target.isLiving()) {
            ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(statusEffect, amplifier, duration, ambient, showParticles, showIcon));
        } else if (user.isLiving()) ((LivingEntity) user).addStatusEffect(new StatusEffectInstance(statusEffect, amplifier, duration, ambient, showParticles, showIcon));
    }

    private record EnchantmentOptions(boolean useTargetPosition) {
        public static EnchantmentOptions get(JsonElement element, String name) {
            if (element.isJsonObject()) {
                JsonObject object = element.getAsJsonObject();
                boolean useTargetPosition = JsonHelper.getBoolean(object, "use_target_position", false);
                return new EnchantmentOptions(useTargetPosition);
            }
            throw new JsonSyntaxException(name);
        }
    }
}
