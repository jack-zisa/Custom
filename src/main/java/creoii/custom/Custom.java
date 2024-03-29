package creoii.custom;

import creoii.custom.eventsystem.condition.Condition;
import creoii.custom.eventsystem.condition.Conditions;
import creoii.custom.eventsystem.effect.AbstractEffect;
import creoii.custom.eventsystem.effect.Effects;
import creoii.custom.eventsystem.event.AbstractEvent;
import creoii.custom.eventsystem.event.Events;
import creoii.custom.eventsystem.parameter.EventParameter;
import creoii.custom.eventsystem.parameter.EventParameters;
import creoii.custom.loaders.*;
import creoii.custom.objects.block.CustomBlock;
import creoii.custom.registry.AttributeRegistry;
import creoii.custom.util.provider.ValueProvider;
import creoii.custom.util.provider.ValueProviders;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.random.Random;
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

    public static final SimpleRegistry<AbstractEvent> EVENT = FabricRegistryBuilder.createSimple(AbstractEvent.class, new Identifier(NAMESPACE, "event")).buildAndRegister();
    public static final SimpleRegistry<Condition> CONDITION = FabricRegistryBuilder.createSimple(Condition.class, new Identifier(NAMESPACE, "condition")).buildAndRegister();
    public static final SimpleRegistry<AbstractEffect> EFFECT = FabricRegistryBuilder.createSimple(AbstractEffect.class, new Identifier(NAMESPACE, "effect")).buildAndRegister();
    public static final SimpleRegistry<ValueProvider> VALUE_PROVIDER = FabricRegistryBuilder.createSimple(ValueProvider.class, new Identifier(NAMESPACE, "value_provider")).buildAndRegister();
    public static final SimpleRegistry<EventParameter> EVENT_PARAMETER = FabricRegistryBuilder.createSimple(EventParameter.class, new Identifier(NAMESPACE, "event_parameter")).buildAndRegister();

    public static BlocksLoader BLOCKS_LOADER;
    public static ItemsLoader ITEMS_LOADER;
    public static ItemGroupsLoader ITEM_GROUPS_LOADER;
    public static EnchantmentsLoader ENCHANTMENTS_LOADER;
    public static StatusEffectsLoader STATUS_EFFECTS_LOADER;
    public static PotionsLoader POTIONS_LOADER;
    public static PaintingsLoader PAINTINGS_LOADER;
    public static InstrumentsLoader INSTRUMENTS_LOADER;
    public static BannerPatternLoader BANNER_PATTERNS_LOADER;
    public static DamageSourcesLoader DAMAGE_SOURCES_LOADER;
    public static VillagerTradesLoader VILLAGER_TRADES_LOADER;
    public static VillagerTypesLoader VILLAGER_TYPES_LOADER;
    public static GlobalEventsLoader GLOBAL_EVENTS_LOADER;

    @Override
    public void onInitialize() {
        Block blueprint = Registry.register(Registries.BLOCK, new Identifier(NAMESPACE, "blueprint"), new Block(FabricBlockSettings.copy(Blocks.BARRIER)));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.OPERATOR).register(entries -> entries.add(Registry.register(Registries.ITEM, new Identifier(NAMESPACE, "blueprint"), new BlockItem(blueprint, new FabricItemSettings().rarity(Rarity.EPIC)))));
        AttributeRegistry.register();
        EventParameters.register();
        Events.register();
        Conditions.register();
        Effects.register();
        ValueProviders.register();

        BLOCKS_LOADER = new BlocksLoader();
        ITEMS_LOADER = new ItemsLoader();
        ITEM_GROUPS_LOADER = new ItemGroupsLoader();
        ENCHANTMENTS_LOADER = new EnchantmentsLoader();
        STATUS_EFFECTS_LOADER = new StatusEffectsLoader();
        POTIONS_LOADER = new PotionsLoader();
        PAINTINGS_LOADER = new PaintingsLoader();
        INSTRUMENTS_LOADER = new InstrumentsLoader();
        BANNER_PATTERNS_LOADER = new BannerPatternLoader();
        DAMAGE_SOURCES_LOADER = new DamageSourcesLoader();
        VILLAGER_TRADES_LOADER = new VillagerTradesLoader();
        VILLAGER_TYPES_LOADER = new VillagerTypesLoader();
        GLOBAL_EVENTS_LOADER = new GlobalEventsLoader();

        LOGGER.info("Custom has been successfully initialized");
        BLOCKS_LOADER.printLoads();
        ITEMS_LOADER.printLoads();
        ITEM_GROUPS_LOADER.printLoads();
        ENCHANTMENTS_LOADER.printLoads();
        STATUS_EFFECTS_LOADER.printLoads();
        INSTRUMENTS_LOADER.printLoads();
        POTIONS_LOADER.printLoads();
        PAINTINGS_LOADER.printLoads();
        BANNER_PATTERNS_LOADER.printLoads();
        DAMAGE_SOURCES_LOADER.printLoads();
        VILLAGER_TRADES_LOADER.printLoads();
        VILLAGER_TYPES_LOADER.printLoads();
        GLOBAL_EVENTS_LOADER.printLoads();
    }

    @Override
    public void onInitializeClient() {
        if (BLOCKS_LOADER.getValues() != null) {
            for (CustomBlock block : BLOCKS_LOADER.getValues().values()) {
                BlockRenderLayerMap.INSTANCE.putBlock(block, block.getRenderLayer());
            }
        }
    }
}
