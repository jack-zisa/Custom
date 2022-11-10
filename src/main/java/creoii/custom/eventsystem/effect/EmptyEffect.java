package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
import creoii.custom.eventsystem.parameter.EventParameter;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class EmptyEffect extends Effect {
    public EmptyEffect getFromJson(JsonObject object) {
        return new EmptyEffect();
    }

    @Override
    public List<EventParameter> getParameters() {
        return List.of();
    }

    @Override
    public void run(List<EventParameter> parameters) { }
}
