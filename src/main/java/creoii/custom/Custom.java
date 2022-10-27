package creoii.custom;

import creoii.custom.custom.block.CustomBlock;
import creoii.custom.data.*;
import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.condition.Conditions;
import creoii.custom.eventsystem.effect.Effect;
import creoii.custom.eventsystem.effect.Effects;
import creoii.custom.eventsystem.event.Event;
import creoii.custom.eventsystem.event.Events;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.SimpleRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class Custom implements ModInitializer, ClientModInitializer {
    public static final String NAMESPACE = "custom";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final Random RANDOM = new Random();

    public static final DefaultedRegistry<Event> EVENT = FabricRegistryBuilder.createDefaulted(Event.class, new Identifier(NAMESPACE, "event"), new Identifier(NAMESPACE, "empty")).buildAndRegister();
    public static final DefaultedRegistry<Condition> CONDITION = FabricRegistryBuilder.createDefaulted(Condition.class, new Identifier(NAMESPACE, "condition"), new Identifier(NAMESPACE, "empty")).buildAndRegister();
    public static final DefaultedRegistry<Effect> EFFECT = FabricRegistryBuilder.createDefaulted(Effect.class, new Identifier(NAMESPACE, "effect"), new Identifier(NAMESPACE, "empty")).buildAndRegister();

    public static final BlocksManager BLOCKS_MANAGER = new BlocksManager();
    public static final ItemsManager ITEMS_MANAGER = new ItemsManager();
    public static final ItemGroupsManager ITEM_GROUPS_MANAGER = new ItemGroupsManager();
    public static final EnchantmentsManager ENCHANTMENTS_MANAGER = new EnchantmentsManager();
    public static final StatusEffectManager STATUS_EFFECT_MANAGER = new StatusEffectManager();
    public static final PaintingsManager PAINTINGS_MANAGER = new PaintingsManager();
    public static final VillagerTradesManager VILLAGER_TRADES_MANAGER = new VillagerTradesManager();
    public static final VillagerTypeManager VILLAGER_TYPE_MANAGER = new VillagerTypeManager();
    public static final GlobalEventManager GLOBAL_EVENT_MANAGER = new GlobalEventManager();

    @Override
    public void onInitialize() {
        Events.register();
        Conditions.register();
        Effects.register();

        System.out.println(EVENT.getDefaultId());
        System.out.println(CONDITION.getDefaultId());
        System.out.println(EFFECT.getDefaultId());

        System.out.println("===== EVENTS =====");
        EVENT.forEach(event -> System.out.println(event.getId()));
        System.out.println("===== CONDITIONS =====");
        CONDITION.forEach(condition -> System.out.println(condition.getId()));
        System.out.println("===== EFFECTS =====");
        EFFECT.forEach(effect -> System.out.println(effect.getId()));

        LOGGER.info("Custom has been successfully initialized");
    }

    @Override
    public void onInitializeClient() {
        for (CustomBlock block : BLOCKS_MANAGER.values.values()) {
            BlockRenderLayerMap.INSTANCE.putBlock(block, block.getRenderLayer());
        }
    }
}
