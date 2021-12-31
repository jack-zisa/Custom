package creoii.custom;

import creoii.custom.custom.CustomBlock;
import creoii.custom.custom.CustomTrade;
import creoii.custom.data.*;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.village.TradeOffers;

import java.util.Arrays;

/**
 * TODO:
 * Add shear and elytra enchantment targets
 * Support for:
 *  custom commands, status effects, particles, villager professions, entities, fluids
 */
public class Custom implements ModInitializer, ClientModInitializer {
    public static final String MOD_ID = "custom";
    public static final BlocksManager BLOCKS_MANAGER = new BlocksManager();
    public static final ItemsManager ITEMS_MANAGER = new ItemsManager();
    public static final ItemGroupsManager ITEM_GROUPS_MANAGER = new ItemGroupsManager();
    public static final EnchantmentsManager ENCHANTMENTS_MANAGER = new EnchantmentsManager();
    public static final PaintingsManager PAINTINGS_MANAGER = new PaintingsManager();
    public static final TradesManager TRADES_MANAGER = new TradesManager();

    @Override
    public void onInitialize() {
        TradeOffers.Factory[] trades;
        for (CustomTrade trade : TRADES_MANAGER.values.values()) {
            if (trade.isTraderTrade()) {
                TradeOffers.Factory[] temp1 = TradeOffers.WANDERING_TRADER_TRADES.remove(trade.getTradeLevel()).clone();
                trades = Arrays.copyOf(temp1, temp1.length + 1);
                trades[temp1.length] = trade.get();
                TradeOffers.WANDERING_TRADER_TRADES.put(trade.getTradeLevel(), trades);
            } else {
                Int2ObjectMap<TradeOffers.Factory[]> temp1 = TradesManager.professionToTradeMap(trade.getProfession().toString());
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

    @Override
    public void onInitializeClient() {
        for (CustomBlock block : BLOCKS_MANAGER.values.values()) {
            BlockRenderLayerMap.INSTANCE.putBlock(block, block.getRenderLayer());
        }
    }
}
