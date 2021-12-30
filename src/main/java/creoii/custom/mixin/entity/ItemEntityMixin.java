package creoii.custom.mixin.entity;

import creoii.custom.util.tags.ItemTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    @Shadow public ItemStack getStack() { return null; }

    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public boolean isImmuneToExplosion() {
        return getStack().isIn(ItemTags.EXPLOSION_IMMUNE);
    }
}