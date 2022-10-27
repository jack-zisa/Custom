package creoii.custom.util.math.number;

public final class NumberProviders {
    public static final NumberProvider NONE = new NumberProvider("none") {
        @Override
        public double getValue() {
            return 0d;
        }
    };
    public static final ConstantNumberProvider CONSTANT = new ConstantNumberProvider();
    public static final RandomNumberProvider RANDOM = new RandomNumberProvider();
    public static final WorldNumberProvider WORLD = new WorldNumberProvider();
    public static final EntityNumberProvider ENTITY = new EntityNumberProvider();
    public static final LivingEntityNumberProvider LIVING_ENTITY = new LivingEntityNumberProvider();
}
