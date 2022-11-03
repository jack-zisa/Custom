package creoii.custom.mixin.misc;

import creoii.custom.util.Constants;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
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

@Mixin(Rarity.class)
public class ItemRarityMixin {
    @SuppressWarnings("InvokerTarget")
    @Invoker("<init>")
    private static Rarity create(String internalName, int internalId, Formatting formatting) {
        throw new AssertionError();
    }

    @Shadow @Final @Mutable
    private static Rarity[] field_8905;

    @Inject(method = "<clinit>", at = @At(value = "FIELD", opcode = Opcodes.PUTSTATIC, target = "Lnet/minecraft/util/Rarity;field_8905:[Lnet/minecraft/util/Rarity;", shift = At.Shift.AFTER))
    private static void addItemRarity(CallbackInfo ci) {
        ArrayList<Rarity> types = new ArrayList<>(Arrays.asList(field_8905));
        Rarity last = types.get(types.size() - 1);

        Rarity legendary = create("LEGENDARY", last.ordinal() + 1, Formatting.GOLD);
        Rarity mythical = create("MYTHICAL", last.ordinal() + 1, Formatting.BLACK);
        Rarity unknown = create("UNKNOWN", last.ordinal() + 1, Formatting.OBFUSCATED);
        Constants.LEGENDARY = legendary;
        Constants.MYTHICAL = mythical;
        Constants.UNKNOWN = unknown;
        types.add(legendary);
        types.add(mythical);
        types.add(unknown);

        field_8905 = types.toArray(new Rarity[0]);
    }
}