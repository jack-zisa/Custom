package creoii.custom.custom;

import com.google.gson.JsonSerializer;
import com.google.gson.*;
import creoii.custom.data.CustomObject;
import creoii.custom.eventsystem.event.Event;
import creoii.custom.eventsystem.event.RightClickEvent;
import creoii.custom.util.CustomJsonHelper;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.List;

public class CustomItem extends Item implements CustomObject {
    private final Identifier identifier;
    private final FoodComponent food;
    private final Text tooltipText;
    private final Event[] events;

    public CustomItem(Identifier identifier, Settings settings, FoodComponent food, Text tooltipText, Event[] events) {
        super(settings);
        this.identifier = identifier;
        this.food = food;
        this.tooltipText = tooltipText;
        this.events = events;

        Registry.register(Registry.ITEM, identifier, this);
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    public FoodComponent getFood() {
        return food;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(tooltipText);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        Event event = Event.findEvent(events, Event.RIGHT_CLICK);
        if (event != null) {
            if (event.applyItemEvent(context.getWorld(), context.getStack(), context.getBlockPos(), context.getPlayer(), context.getHand())) {
                return ((RightClickEvent) event).getActionResult();
            }
        }
        return super.useOnBlock(context);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        Event event = Event.findEvent(events, Event.RIGHT_CLICK);
        if (event != null) {
            if (event.applyItemEvent(user.world, stack, entity.getBlockPos(), user, hand)) {
                return ((RightClickEvent) event).getActionResult();
            }
        }
        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        Event event = Event.findEvent(events, Event.STOPPED_USING);
        if (event != null && user instanceof PlayerEntity player) {
            event.applyItemEvent(user.world, stack, player.getBlockPos(), player, player.getActiveHand());
        }
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        Event event = Event.findEvent(events, Event.CRAFTED);
        if (event != null) {
            event.applyItemEvent(world, stack, player.getBlockPos(), player, player.getActiveHand());
        }
        super.onCraft(stack, world, player);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        Event event = Event.findEvent(events, Event.RIGHT_CLICK);
        if (event != null) {
            event.applyItemEvent(user.world, user.getStackInHand(hand), user.getBlockPos(), user, hand);
        }
        return super.use(world, user, hand);
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        Event event = Event.findEvent(events, Event.LEFT_CLICK);
        if (event != null) {
            event.applyItemEvent(player.world, stack, player.getBlockPos(), player, player.getActiveHand());
        }
        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }

    public static class Serializer implements JsonDeserializer<CustomItem>, JsonSerializer<CustomItem> {
        @Override
        public CustomItem deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = JsonHelper.asObject(json, "item");
            Identifier identifier = Identifier.tryParse(JsonHelper.getString(object, "identifier"));
            Item.Settings settings = CustomJsonHelper.getItemSettings(object, "item_settings");
            FoodComponent food;
            if (JsonHelper.hasJsonObject(object, "food")) food = CustomJsonHelper.getFood(object, "food");
            else food = null;
            Text tooltipText = Text.of(JsonHelper.getString(object, "tooltip", ""));
            Event[] events;
            if (JsonHelper.hasArray(object, "events")) {
                JsonArray array = JsonHelper.getArray(object, "events");
                events = new Event[array.size()];
                if (events.length > 0) {
                    for (int i = 0; i < events.length; ++i) {
                        if (array.get(i).isJsonObject()) {
                            JsonObject eventObj = array.get(i).getAsJsonObject();
                            events[i] = Event.getEvent(eventObj, eventObj.get("type").getAsString());
                        }
                    }
                }
            } else events = new Event[]{};
            return new CustomItem(identifier, settings, food, tooltipText, events);
        }

        @Override
        public JsonElement serialize(CustomItem src, Type typeOfSrc, JsonSerializationContext context) {
            return null;
        }
    }
}
