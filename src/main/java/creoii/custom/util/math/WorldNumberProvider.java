package creoii.custom.util.math;

import net.minecraft.world.World;

public class WorldNumberProvider extends NumberProvider {
    private World world;

    public WorldNumberProvider() {
        super();
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public double getValueByType(String type) {
        return switch (type) {
            case "time" -> time();
            case "sea_level" -> seaLevel();
            case "height" -> height();
            case "bottom_y" -> bottomY();
            case "top_y" -> topY();
            case "ambient_darkness" -> ambientDarkness();
            case "time_of_day" -> timeOfDay();
            case "lunar_time" -> lunarTime();
            case "max_light_level" -> maxLightLevel();
            case "moon_phase" -> moonPhase();
            case "moon_size" -> moonSize();
            default -> 0d;
        };
    }

    private double time() { return world.getTime(); }
    private double seaLevel() { return world.getSeaLevel(); }
    private double height() { return world.getHeight(); }
    private double bottomY() { return world.getBottomY(); }
    private double topY() { return world.getTopY(); }
    private double ambientDarkness() { return world.getAmbientDarkness(); }
    private double timeOfDay() { return world.getTimeOfDay(); }
    private double lunarTime() { return world.getLunarTime(); }
    private double maxLightLevel() { return world.getMaxLightLevel(); }
    private double moonPhase() { return world.getMoonPhase(); }
    private double moonSize() { return world.getMoonSize(); }
}
