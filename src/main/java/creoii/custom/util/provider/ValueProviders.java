package creoii.custom.util.provider;

import creoii.custom.Custom;
import creoii.custom.util.provider.function.*;
import net.minecraft.util.Identifier;

public class ValueProviders {
    public static final SingleFunction<Double> EMPTY = new SingleFunction<>() {
        @Override
        public Double compute(ValueProvider<Double> value) {
            return 0d;
        }
    };
    public static ValueProvider<?> BOOLEAN;
    public static ValueProvider<?> LIVING_ENTITY;
    public static ValueProvider<?> DIRECTION;

    public static void register() {
        ValueProvider.register(new Identifier(Custom.NAMESPACE, "empty"), EMPTY);
        BOOLEAN = ValueProvider.register(new Identifier(Custom.NAMESPACE, "boolean"), new BooleanProvider());
        LIVING_ENTITY = ValueProvider.register(new Identifier(Custom.NAMESPACE, "living_entity"), new LivingEntityValueProvider());
        DIRECTION = ValueProvider.register(new Identifier(Custom.NAMESPACE, "direction"), new DirectionValueProvider());
        SingleFunctions.register();
        DoubleFunctions.register();
        StringFunctions.register();
        BooleanFunctions.register();
    }
}
