package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.util.StringToObject;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class PlaySoundEffect extends Effect {
    private SoundEvent soundEvent;
    private SoundCategory soundCategory;
    private float volume;
    private float pitch;
    private boolean affectTarget;

    public PlaySoundEffect withValues(SoundEvent soundEvent, SoundCategory soundCategory, float volume, float pitch, boolean affectTarget) {
        this.soundEvent = soundEvent;
        this.soundCategory = soundCategory;
        this.volume = volume;
        this.pitch = pitch;
        this.affectTarget = affectTarget;
        return this;
    }

    public PlaySoundEffect getFromJson(JsonObject object) {
        SoundEvent soundEvent = Registry.SOUND_EVENT.get(Identifier.tryParse(object.get("sound_event").getAsString()));
        SoundCategory soundCategory = StringToObject.soundCategory(JsonHelper.getString(object, "category"));
        float volume = JsonHelper.getFloat(object, "volume", 0f);
        float pitch = JsonHelper.getFloat(object, "pitch", 0f);
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target");
        return withValues(soundEvent, soundCategory, volume, pitch, affectTarget);
    }

    private void run(World world, BlockPos pos) {
        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), soundEvent, soundCategory, volume, pitch);
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
