package creoii.custom.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import creoii.custom.custom.CustomTrade;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;

import java.io.Reader;

public class TradesManager extends AbstractDataManager<CustomTrade> {
    private static Int2ObjectMap<TradeOffers.Factory[]> FARMER;
    private static Int2ObjectMap<TradeOffers.Factory[]> FISHERMAN;
    private static Int2ObjectMap<TradeOffers.Factory[]> SHEPHERD;
    private static Int2ObjectMap<TradeOffers.Factory[]> FLETCHER;
    private static Int2ObjectMap<TradeOffers.Factory[]> LIBRARIAN;
    private static Int2ObjectMap<TradeOffers.Factory[]> CARTOGRAPHER;
    private static Int2ObjectMap<TradeOffers.Factory[]> CLERIC;
    private static Int2ObjectMap<TradeOffers.Factory[]> ARMORER;
    private static Int2ObjectMap<TradeOffers.Factory[]> WEAPONSMITH;
    private static Int2ObjectMap<TradeOffers.Factory[]> TOOLSMITH;
    private static Int2ObjectMap<TradeOffers.Factory[]> BUTCHER;
    private static Int2ObjectMap<TradeOffers.Factory[]> LEATHERWORKER;
    private static Int2ObjectMap<TradeOffers.Factory[]> MASON;

    public TradesManager() {
        super("trades", new GsonBuilder().setPrettyPrinting().registerTypeAdapter(CustomTrade.class, new CustomTrade.Serializer()).create());
        FARMER = TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.FARMER);
        FISHERMAN = TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.FISHERMAN);
        SHEPHERD = TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.SHEPHERD);
        FLETCHER = TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.FLETCHER);
        LIBRARIAN = TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.LIBRARIAN);
        CARTOGRAPHER = TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.CARTOGRAPHER);
        CLERIC = TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.CLERIC);
        ARMORER = TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.ARMORER);
        WEAPONSMITH = TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.WEAPONSMITH);
        TOOLSMITH = TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.TOOLSMITH);
        BUTCHER = TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.BUTCHER);
        LEATHERWORKER = TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.LEATHERWORKER);
        MASON = TradeOffers.PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.MASON);
    }

    @Override
    CustomTrade createCustomObject(Reader reader, Gson gson) {
        return gson.fromJson(reader, CustomTrade.class);
    }

    public static Int2ObjectMap<TradeOffers.Factory[]> professionToTradeMap(String profession) {
        return switch (profession) {
            case "armorer" -> ARMORER;
            case "butcher" -> BUTCHER;
            case "cartographer" -> CARTOGRAPHER;
            case "cleric" -> CLERIC;
            case "farmer" -> FARMER;
            case "fisherman" -> FISHERMAN;
            case "fletcher" -> FLETCHER;
            case "leatherworker" -> LEATHERWORKER;
            case "librarian" -> LIBRARIAN;
            case "mason" -> MASON;
            case "shepherd" -> SHEPHERD;
            case "toolsmith" -> TOOLSMITH;
            case "weaponsmith" -> WEAPONSMITH;
            case "wandering_trader" -> TradeOffers.WANDERING_TRADER_TRADES;
            default -> FARMER;
        };
    }
}
