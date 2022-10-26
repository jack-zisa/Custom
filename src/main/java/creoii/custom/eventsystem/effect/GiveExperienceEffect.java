package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GiveExperienceEffect extends Effect {
    private final int amount;
    private final Type type;
    private final boolean affectTarget;

    public GiveExperienceEffect(int amount, Type type, boolean affectTarget) {
        super(Effect.GIVE_EXPERIENCE);
        this.amount = amount;
        this.type = type;
        this.affectTarget = affectTarget;
    }

    public static Effect getFromJson(JsonObject object) {
        int amount = JsonHelper.getInt(object, "amount", 0);
        Type type = Type.valueOf(JsonHelper.getString(object, "type", "experience").toUpperCase());
        boolean affectTarget = JsonHelper.getBoolean(object, "affect_target", false);
        return new GiveExperienceEffect(amount, type, affectTarget);
    }

    private void run(Entity entity) {
        if (entity instanceof PlayerEntity playerEntity) {
            if (type == Type.EXPERIENCE) playerEntity.addExperience(amount);
            else playerEntity.addExperienceLevels(amount);
        }
    }

    @Override
    public void runBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        run(living);
    }

    @Override
    public void runItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        run(player);
    }

    @Override
    public void runEntity(Entity entity, PlayerEntity player, Hand hand) {
        run(player);
    }

    @Override
    public void runEnchantment(Enchantment enchantment, Entity user, Entity target, int level) {
        run(affectTarget ? target : user);
    }

    @Override
    public void runStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        run(entity);
    }

    @Override
    public void runWorld(World world) { }

    enum Type {
        EXPERIENCE,
        LEVELS;
    }
}
