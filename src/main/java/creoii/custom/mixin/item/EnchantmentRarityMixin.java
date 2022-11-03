package creoii.custom.mixin.item;

import creoii.custom.util.Constants;
import net.minecraft.enchantment.Enchantment;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(Enchantment.Rarity.class)
public class EnchantmentRarityMixin {
    @SuppressWarnings("InvokerTarget")
    @Invoker("<init>")
    private static Enchantment.Rarity create(String internalName, int internalId, int weight) {
        throw new AssertionError();
    }

    @Shadow @Final @Mutable
    private static Enchantment.Rarity[] field_9092;

    @Inject(method = "<clinit>", at = @At(value = "FIELD", opcode = Opcodes.PUTSTATIC, target = "Lnet/minecraft/enchantment/Enchantment$Rarity;field_9092:[Lnet/minecraft/enchantment/Enchantment$Rarity;", shift = At.Shift.AFTER))
    private static void addItemRarity(CallbackInfo ci) {
        ArrayList<Enchantment.Rarity> types = new ArrayList<>(Arrays.asList(field_9092));
        Enchantment.Rarity last = types.get(types.size() - 1);

        Enchantment.Rarity veryCommon = create("VERY_COMMON", last.ordinal() + 1, 20);
        Constants.VERY_COMMON = veryCommon;
        types.add(veryCommon);

        field_9092 = types.toArray(new Enchantment.Rarity[0]);
    }
}