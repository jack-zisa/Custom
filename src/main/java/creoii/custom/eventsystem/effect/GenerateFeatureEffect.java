package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.util.json.CustomJsonHelper;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.function.Supplier;

public class GenerateFeatureEffect extends Effect {
    private Supplier<RegistryEntry<ConfiguredFeature<?, ?>>> configuredFeature;
    private BlockPos offset;
    private boolean removeBlock;
    private boolean affectTarget;

    public GenerateFeatureEffect withValues(Supplier<RegistryEntry<ConfiguredFeature<?, ?>>> configuredFeature, BlockPos offset, boolean removeBlock, boolean affectTarget) {
        this.configuredFeature = configuredFeature;
        this.offset = offset;
        this.removeBlock = removeBlock;
        this.affectTarget = affectTarget;
        return this;
    }

    public Effect getFromJson(JsonObject object) {
        ConfiguredFeature<?, ?> configuredFeature = BuiltinRegistries.CONFIGURED_FEATURE.get(Identifier.tryParse(object.get("feature").getAsString()));
        BlockPos offset = CustomJsonHelper.getBlockPos(object, "offset");
        boolean removeBlock = JsonHelper.getBoolean(object, "removeBlock", true);
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target", false);
        return withValues(() -> RegistryEntry.of(configuredFeature), offset, removeBlock, affectTarget);
    }

    private void run(World world, BlockPos pos) {
        if (!world.isClient && configuredFeature.get().hasKeyAndValue()) {
            if (removeBlock) world.removeBlock(pos, false);
            configuredFeature.get().value().generate((ServerWorld)world, ((ServerWorld)world).getChunkManager().getChunkGenerator(), world.getRandom(), pos.add(offset));
        }
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
}
