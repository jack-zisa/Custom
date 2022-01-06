package creoii.custom.eventsystem.condition;

import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntitySwimmingCondition extends Condition {
    private final boolean useTargetPosition;

    public EntitySwimmingCondition(boolean useTargetPosition) {
        super(Condition.ENTITY_SWIMMING);
        this.useTargetPosition = useTargetPosition;
    }

    public static Condition getFromJson(JsonObject object) {
        boolean useTargetPosition = JsonHelper.getBoolean(object, "use_target_position", false);
        return new EntitySwimmingCondition(useTargetPosition);
    }

    @Override
    public boolean testBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand) {
        return living.isSwimming();
    }

    @Override
    public boolean testItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand) {
        return player.isSwimming();
    }

    @Override
    public boolean testEntity(Entity entity, PlayerEntity player, Hand hand) {
        return entity.isSwimming();
    }

    @Override
    public boolean testEnchantment(Entity user, Entity target, int level) {
        return useTargetPosition ? target.isSwimming() : user.isSwimming();
    }

    @Override
    public boolean testStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier) {
        return entity.isSwimming();
    }

    @Override
    public boolean testWorld(World world) {
        return false;
    }
}
