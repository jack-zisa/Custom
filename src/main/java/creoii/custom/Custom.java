package creoii.custom;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import creoii.custom.custom.CustomTrade;
import creoii.custom.custom.block.CustomBlock;
import creoii.custom.data.*;
import creoii.custom.util.reflection.JsonReflection;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.AbstractBlock;
import net.minecraft.village.TradeOffers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class Custom implements ModInitializer, ClientModInitializer {
    public static final String MOD_ID = "custom";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final BlocksManager BLOCKS_MANAGER = new BlocksManager();
    public static final ItemsManager ITEMS_MANAGER = new ItemsManager();
    public static final ItemGroupsManager ITEM_GROUPS_MANAGER = new ItemGroupsManager();
    public static final EnchantmentsManager ENCHANTMENTS_MANAGER = new EnchantmentsManager();
    public static final StatusEffectManager STATUS_EFFECT_MANAGER = new StatusEffectManager();
    public static final PaintingsManager PAINTINGS_MANAGER = new PaintingsManager();
    public static final VillagerTradesManager VILLAGER_TRADES_MANAGER = new VillagerTradesManager();
    public static final VillagerProfessionManager VILLAGER_PROFESSION_MANAGER = new VillagerProfessionManager();
    public static final VillagerTypeManager VILLAGER_TYPE_MANAGER = new VillagerTypeManager();
    public static final GlobalEventManager GLOBAL_EVENT_MANAGER = new GlobalEventManager();

    @Override
    public void onInitialize() {
        if (VILLAGER_TRADES_MANAGER.values != null) {
            TradeOffers.Factory[] trades;
            for (CustomTrade trade : VILLAGER_TRADES_MANAGER.values.values()) {
                if (trade.isTraderTrade()) {
                    TradeOffers.Factory[] temp1 = TradeOffers.WANDERING_TRADER_TRADES.remove(trade.getTradeLevel()).clone();
                    trades = Arrays.copyOf(temp1, temp1.length + 1);
                    trades[temp1.length] = trade.get();
                    TradeOffers.WANDERING_TRADER_TRADES.put(trade.getTradeLevel(), trades);
                } else {
                    Int2ObjectMap<TradeOffers.Factory[]> temp1 = VillagerTradesManager.professionToTradeMap(trade.getProfession().toString());
                    TradeOffers.Factory[] temp2 = temp1.remove(trade.getTradeLevel()).clone();
                    trades = Arrays.copyOf(temp2, temp2.length + 1);
                    trades[temp2.length] = trade.get();
                    temp1.put(trade.getTradeLevel(), trades);
                    TradeOffers.PROFESSION_TO_LEVELED_TRADE.put(
                            trade.getProfession(), temp1
                    );
                }
            }
        }

        JsonElement element = JsonReflection.serializeClass(AbstractBlock.class);
        System.out.println(element);
        System.out.println(JsonReflection.deserializeClass(element, true));

        LOGGER.info("Custom has been successfully initialized");
    }

    @Override
    public void onInitializeClient() {
        for (CustomBlock block : BLOCKS_MANAGER.values.values()) {
            BlockRenderLayerMap.INSTANCE.putBlock(block, block.getRenderLayer());
        }
    }
}
