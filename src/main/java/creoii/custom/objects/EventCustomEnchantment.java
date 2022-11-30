package creoii.custom.objects;

import creoii.custom.eventsystem.event.AbstractEvent;
import creoii.custom.eventsystem.event.BlockEvents;
import creoii.custom.eventsystem.parameter.*;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import java.util.List;

public class EventCustomEnchantment extends CustomEnchantment {
    private final List<AbstractEvent> events;

    public EventCustomEnchantment(
            Identifier identifier,
            Rarity rarity, EnchantmentTarget type, EquipmentSlot[] slotTypes,
            boolean offeredByLibrarians, boolean randomlySelectable,
            int minPlayerLevel, int maxPlayerLevel, int maxLevel, int minLevel,
            Identifier[] blacklist, List<AbstractEvent> events
    ) {
        super(identifier, rarity, type, slotTypes,
                offeredByLibrarians, randomlySelectable,
                minPlayerLevel, maxPlayerLevel, maxLevel, minLevel,
                blacklist);
        this.events = events;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        List<AbstractEvent> events1 = AbstractEvent.findAll(events, BlockEvents.ENTITY_COLLISION);
        if (events1 != null) {
            for (AbstractEvent event : events1) {
                event.apply(List.of(
                        new WorldParameter().withValue(user.getWorld()),
                        new BlockPosParameter().withValue(user.getBlockPos()).name("user_pos"),
                        new EntityParameter().withValue(user).name("user"),
                        new EntityTypeParameter().withValue(user.getType()).name("user_type"),
                        new BlockPosParameter().withValue(target.getBlockPos()).name("target_pos"),
                        new EntityParameter().withValue(target).name("target"),
                        new EntityTypeParameter().withValue(target.getType()).name("target_type"),
                        new IntegerParameter().withValue(level).name("level")
                ));
            }
        }
        super.onTargetDamaged(user, target, level);
    }

    @Override
    public void onUserDamaged(LivingEntity user, Entity attacker, int level) {
        List<AbstractEvent> events1 = AbstractEvent.findAll(events, BlockEvents.ENTITY_COLLISION);
        if (events1 != null) {
            for (AbstractEvent event : events1) {
                event.apply(List.of(
                        new WorldParameter().withValue(user.getWorld()),
                        new BlockPosParameter().withValue(user.getBlockPos()).name("user_pos"),
                        new EntityParameter().withValue(user).name("user"),
                        new EntityTypeParameter().withValue(user.getType()).name("user_type"),
                        new BlockPosParameter().withValue(attacker.getBlockPos()).name("attacker_pos"),
                        new EntityParameter().withValue(attacker).name("attacker"),
                        new EntityTypeParameter().withValue(attacker.getType()).name("attacker_type"),
                        new IntegerParameter().withValue(level).name("level")
                ));
            }
        }
        super.onUserDamaged(user, attacker, level);
    }
}
