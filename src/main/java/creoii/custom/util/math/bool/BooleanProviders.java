package creoii.custom.util.math.bool;

public final class BooleanProviders {
    public static final BooleanProvider NONE = new BooleanProvider("none") {
        @Override
        public boolean getValue() {
            return true;
        }
    };
    public static final ConstantBooleanProvider CONSTANT = new ConstantBooleanProvider();
    public static final RandomBooleanProvider RANDOM = new RandomBooleanProvider();
    public static final WorldBooleanProvider WORLD = new WorldBooleanProvider();
    public static final EntityBooleanProvider ENTITY = new EntityBooleanProvider();
    public static final LivingEntityBooleanProvider LIVING_ENTITY = new LivingEntityBooleanProvider();
}
