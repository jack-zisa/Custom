package creoii.custom;

import creoii.custom.custom.block.CustomBlock;
import creoii.custom.data.*;
import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.condition.Conditions;
import creoii.custom.eventsystem.effect.Effect;
import creoii.custom.eventsystem.effect.Effects;
import creoii.custom.eventsystem.event.Event;
import creoii.custom.eventsystem.event.Events;
import creoii.custom.registry.AttributeRegistry;
import creoii.custom.util.provider.ValueProvider;
import creoii.custom.util.provider.ValueProviders;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.SimpleRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * CUSTOM OBJECTS TODO:
 *  - {@link net.minecraft.fluid.Fluid} Fluid
 *  - {@link net.minecraft.stat.StatType} Stat
 *  - {@link net.minecraft.sound.SoundEvent} SoundEvent
 *  - {@link net.minecraft.world.event.GameEvent} GameEvent
 *  - {@link net.minecraft.particle.ParticleType} ParticleType
 *  - {@link net.minecraft.block.Material} Material
 */
public class Custom implements ModInitializer, ClientModInitializer {
    public static final String NAMESPACE = "custom";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final Random RANDOM = Random.create();

    public static final SimpleRegistry<Event> EVENT = FabricRegistryBuilder.createSimple(Event.class, new Identifier(NAMESPACE, "event")).buildAndRegister();
    public static final SimpleRegistry<Condition> CONDITION = FabricRegistryBuilder.createSimple(Condition.class, new Identifier(NAMESPACE, "condition")).buildAndRegister();
    public static final SimpleRegistry<Effect> EFFECT = FabricRegistryBuilder.createSimple(Effect.class, new Identifier(NAMESPACE, "effect")).buildAndRegister();
    public static final SimpleRegistry<ValueProvider> VALUE_PROVIDER = FabricRegistryBuilder.createSimple(ValueProvider.class, new Identifier(NAMESPACE, "value_provider")).buildAndRegister();

    public static BlocksManager BLOCKS_MANAGER;
    public static ItemsManager ITEMS_MANAGER;
    public static ItemGroupsManager ITEM_GROUPS_MANAGER;
    public static EnchantmentsManager ENCHANTMENTS_MANAGER;
    public static StatusEffectsManager STATUS_EFFECTS_MANAGER;
    public static PotionsManager POTIONS_MANAGER;
    public static PaintingsManager PAINTINGS_MANAGER;
    public static InstrumentsManager INSTRUMENTS_MANAGER;
    public static BannerPatternManager BANNER_PATTERNS_MANAGER;
    public static DamageSourcesManager DAMAGE_SOURCES_MANAGER;
    public static VillagerTradesManager VILLAGER_TRADES_MANAGER;
    public static VillagerTypesManager VILLAGER_TYPES_MANAGER;
    public static GlobalEventsManager GLOBAL_EVENTS_MANAGER;

    @Override
    public void onInitialize() {
        AttributeRegistry.register();
        Events.register();
        Conditions.register();
        Effects.register();
        ValueProviders.register();

        BLOCKS_MANAGER = new BlocksManager();
        ITEMS_MANAGER = new ItemsManager();
        ITEM_GROUPS_MANAGER = new ItemGroupsManager();
        ENCHANTMENTS_MANAGER = new EnchantmentsManager();
        STATUS_EFFECTS_MANAGER = new StatusEffectsManager();
        POTIONS_MANAGER = new PotionsManager();
        PAINTINGS_MANAGER = new PaintingsManager();
        INSTRUMENTS_MANAGER = new InstrumentsManager();
        BANNER_PATTERNS_MANAGER = new BannerPatternManager();
        DAMAGE_SOURCES_MANAGER = new DamageSourcesManager();
        VILLAGER_TRADES_MANAGER = new VillagerTradesManager();
        VILLAGER_TYPES_MANAGER = new VillagerTypesManager();
        GLOBAL_EVENTS_MANAGER = new GlobalEventsManager();

        LOGGER.info("Custom has been successfully initialized");
        BLOCKS_MANAGER.printFailedLoads();
        ITEMS_MANAGER.printFailedLoads();
        ITEM_GROUPS_MANAGER.printFailedLoads();
        ENCHANTMENTS_MANAGER.printFailedLoads();
        STATUS_EFFECTS_MANAGER.printFailedLoads();
        INSTRUMENTS_MANAGER.printFailedLoads();
        POTIONS_MANAGER.printFailedLoads();
        PAINTINGS_MANAGER.printFailedLoads();
        BANNER_PATTERNS_MANAGER.printFailedLoads();
        DAMAGE_SOURCES_MANAGER.printFailedLoads();
        VILLAGER_TRADES_MANAGER.printFailedLoads();
        VILLAGER_TYPES_MANAGER.printFailedLoads();
        GLOBAL_EVENTS_MANAGER.printFailedLoads();
    }

    @Override
    public void onInitializeClient() {
        for (CustomBlock block : BLOCKS_MANAGER.getValues().values()) {
            BlockRenderLayerMap.INSTANCE.putBlock(block, block.getRenderLayer());
        }
    }
}
