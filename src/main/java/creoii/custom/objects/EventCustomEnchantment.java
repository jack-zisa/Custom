package creoii.custom.objects;

import creoii.custom.eventsystem.event.AbstractEvent;
import creoii.custom.eventsystem.event.Events;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class EventCustomEnchantment extends CustomEnchantment {
    private final AbstractEvent[] events;

    public EventCustomEnchantment(
            Identifier identifier,
            Rarity rarity, EnchantmentTarget type, EquipmentSlot[] slotTypes,
            boolean offeredByLibrarians, boolean randomlySelectable,
            int minPlayerLevel, int maxPlayerLevel, int maxLevel, int minLevel,
            Identifier[] blacklist, AbstractEvent[] events
    ) {
        super(identifier, rarity, type, slotTypes,
                offeredByLibrarians, randomlySelectable,
                minPlayerLevel, maxPlayerLevel, maxLevel, minLevel,
                blacklist);
        this.events = events;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        super.onTargetDamaged(user, target, level);
    }

    @Override
    public void onUserDamaged(LivingEntity user, Entity attacker, int level) {
        super.onUserDamaged(user, attacker, level);
    }
}
