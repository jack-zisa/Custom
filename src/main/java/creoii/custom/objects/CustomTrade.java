package creoii.custom.objects;

import com.google.gson.*;
import creoii.custom.data.Identifiable;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;

import static creoii.custom.util.StringToObject.villagerProfession;

public class CustomTrade implements Identifiable, TradeOffers.Factory {
    private final Identifier identifier;
    private final VillagerProfession profession;
    private boolean isTraderTrade;
    private final int tradeLevel;
    private final ItemStack sellItem;
    private final int sellCount;
    private final ItemStack firstBuyItem;
    private final ItemStack secondBuyItem;
    private final int firstPrice;
    private final int secondPrice;
    private final int maxUses;
    private final boolean rewardExp;
    private final int merchantExp;
    private final float priceMultiplier;
    private final int demandBonus;

    public CustomTrade(Identifier identifier, boolean isTraderTrade, @Nullable VillagerProfession profession, int tradeLevel,
                       ItemStack sellItem, int sellCount,
                       ItemStack firstBuyItem, ItemStack secondBuyItem, int firstPrice, int secondPrice,
                       int maxUses, boolean rewardExp, int merchantExp, float priceMultiplier, int demandBonus
    ) {
        this.identifier = identifier;
        this.isTraderTrade = isTraderTrade;
        this.profession = profession;
        this.tradeLevel = tradeLevel;
        this.sellItem = sellItem;
        this.sellCount = sellCount;
        this.firstBuyItem = firstBuyItem;
        this.secondBuyItem = secondBuyItem;
        this.firstPrice = firstPrice;
        this.secondPrice = secondPrice;
        this.maxUses = maxUses;
        this.rewardExp = rewardExp;
        this.merchantExp = merchantExp;
        this.priceMultiplier = priceMultiplier;
        this.demandBonus = demandBonus;
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    public CustomTrade get() {
        return new CustomTrade(
                identifier, isTraderTrade, profession, tradeLevel,
                sellItem, sellCount,
                firstBuyItem, secondBuyItem, firstPrice, secondPrice,
                maxUses, rewardExp, merchantExp, priceMultiplier, demandBonus
        );
    }

    public boolean isTraderTrade() {
        return isTraderTrade;
    }

    public VillagerProfession getProfession() {
        return profession;
    }

    public int getTradeLevel() {
        return tradeLevel;
    }

    @Nullable
    @Override
    public TradeOffer create(Entity entity, Random random) {
        return new TradeOffer(
                new ItemStack(firstBuyItem.getItem(), firstPrice), new ItemStack(secondBuyItem.getItem(), secondPrice), new ItemStack(sellItem.getItem(), sellCount),
                0, maxUses,
                merchantExp, priceMultiplier, demandBonus
        ) {
            @Override
            public boolean shouldRewardPlayerExperience() {
                return rewardExp;
            }
        };
    }

    public static class Serializer implements JsonDeserializer<CustomTrade>, JsonSerializer<CustomTrade> {
        @Override
        public CustomTrade deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = JsonHelper.asObject(json, "trade");
            boolean isTraderTrade = false;
            String professionStr = JsonHelper.getString(object, "profession", "none");
            if (professionStr.equals("wandering_trader")) isTraderTrade = true;
            VillagerProfession profession = null;
            if (!isTraderTrade) profession = villagerProfession(professionStr);
            int tradeLevel = JsonHelper.getInt(object, "trade_level", 1);
            int sellCount = JsonHelper.getInt(object, "sell_count", 1);
            ItemStack sellItem = new ItemStack(JsonHelper.getItem(object, "sell_item", Items.AIR), sellCount);
            int firstPrice = JsonHelper.getInt(object, "first_price", 1);
            int secondPrice = JsonHelper.getInt(object, "second_price", 1);
            ItemStack firstBuyItem = new ItemStack(JsonHelper.getItem(object, "first_buy_item", Items.AIR), firstPrice);
            ItemStack secondBuyItem = new ItemStack(JsonHelper.getItem(object, "second_buy_item", Items.AIR), secondPrice);
            int maxUses = JsonHelper.getInt(object, "max_uses", 4);
            boolean rewardExp = JsonHelper.getBoolean(object, "reward_exp", true);
            int merchantExp = JsonHelper.getInt(object, "merchant_exp", 1);
            float priceMultiplier = JsonHelper.getFloat(object, "price_multiplier", 1f);
            int demandBonus = JsonHelper.getInt(object, "demand_bonus", 0);
            return new CustomTrade(
                    Identifier.tryParse(JsonHelper.getString(object, "identifier")), isTraderTrade, profession, tradeLevel,
                    sellItem, sellCount,
                    firstBuyItem, secondBuyItem, firstPrice, secondPrice,
                    maxUses, rewardExp, merchantExp, priceMultiplier, demandBonus
            );
        }

        @Override
        public JsonElement serialize(CustomTrade src, Type typeOfSrc, JsonSerializationContext context) {
            return null;
        }
    }
}
