package creoii.custom.eventsystem.effect;

import com.google.gson.*;
import creoii.custom.util.StringToObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Property;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SetBlockEffect extends Effect {
    private Block block;
    private List<StateContainer> properties;
    private boolean affectTarget;

    public SetBlockEffect withValues(Block block, List<StateContainer> properties, boolean affectTarget) {
        this.block = block;
        this.properties = properties;
        this.affectTarget = affectTarget;
        return this;
    }

    public SetBlockEffect getFromJson(JsonObject object) {
        Block block = Registry.BLOCK.get(Identifier.tryParse(JsonHelper.getString(object, "block")));
        List<StateContainer> properties = new ArrayList<>();
        if (object.has("properties")) {
            JsonArray propertiesArray = object.getAsJsonArray("properties");
            propertiesArray.forEach(element -> properties.add(StateContainer.getFromJson(element.getAsJsonObject())));
        }
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target");
        return withValues(block, properties, affectTarget);
    }

    private void run(World world, BlockPos pos) {
        BlockState state = block.getDefaultState();
        world.setBlockState(pos, state, 3);
    }

    @Override
    public void runBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        run(world, pos);
    }

    @Override
    public void runItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        run(world, pos);
    }

    @Override
    public void runEntity(Entity entity, PlayerEntity player, Hand hand) {
        run(entity.getWorld(), entity.getBlockPos());
    }

    @Override
    public void runEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        run(user.getWorld(), affectTarget ? target.getBlockPos() : user.getBlockPos());
    }

    @Override
    public void runStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        run(entity.getWorld(), entity.getBlockPos());
    }

    @Override
    public void runWorld(World world) { }

    public record StateContainer(Property<?> property, Comparable<?> value) {
        public static StateContainer getFromJson(JsonObject object) {
            Property<?> property = StringToObject.property(object.get("property").getAsString());
            JsonPrimitive primitive = object.get("value").getAsJsonPrimitive();
            Comparable<?> value = null;
            if (primitive.isBoolean()) {
                value = primitive.getAsBoolean();
            } else if (primitive.isString()) {
                value = primitive.getAsString();
            } else if (primitive.isNumber()) {
                value = primitive.getAsDouble();
            }
            if (value != null)
                return new StateContainer(property, value);
            else
                throw new JsonSyntaxException("Could not parse State+Value.");
        }
    }
}
