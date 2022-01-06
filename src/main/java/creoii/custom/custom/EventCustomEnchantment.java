package creoii.custom.custom;

import com.google.gson.*;
import creoii.custom.data.CustomObject;
import creoii.custom.eventsystem.event.Event;
import creoii.custom.util.StringToObject;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

import java.lang.reflect.Type;

public class EventCustomEnchantment extends CustomEnchantment {
    private final Event[] events;

    public EventCustomEnchantment(
            Identifier identifier,
            Rarity rarity, EnchantmentTarget type, EquipmentSlot[] slotTypes,
            boolean offeredByLibrarians, boolean randomlySelectable,
            int minPlayerLevel, int maxPlayerLevel, int maxLevel, int minLevel,
            Identifier[] blacklist, Event[] events
    ) {
        super(identifier, rarity, type, slotTypes,
                offeredByLibrarians, randomlySelectable,
                minPlayerLevel, maxPlayerLevel, maxLevel, minLevel,
                blacklist);
        this.events = events;

        Registry.register(Registry.ENCHANTMENT, identifier, this);
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        super.onTargetDamaged(user, target, level);
        Event event = Event.findEvent(events, Event.TARGET_DAMAGED);
        if (event != null) {
            event.applyEnchantmentEvent(user, target, level);
        }
    }

    @Override
    public void onUserDamaged(LivingEntity user, Entity attacker, int level) {
        super.onUserDamaged(user, attacker, level);
        Event event = Event.findEvent(events, Event.USER_DAMAGED);
        if (event != null) {
            event.applyEnchantmentEvent(user, attacker, level);
        }
    }
}
