package creoii.custom.objects;

import com.google.gson.*;
import creoii.custom.loaders.Identifiable;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.lang.reflect.Type;

public class CustomDamageSource extends DamageSource implements Identifiable {
    private final Identifier identifier;

    public CustomDamageSource(Identifier identifier) {
        super(identifier.getPath());
        this.identifier = identifier;
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    public static class Serializer implements JsonDeserializer<CustomDamageSource>, JsonSerializer<CustomDamageSource> {
        @Override
        public CustomDamageSource deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = JsonHelper.asObject(json, "damage source");
            Identifier identifier = Identifier.tryParse(JsonHelper.getString(object, "identifier"));
            if (identifier != null) {
                CustomDamageSource damageSource = new CustomDamageSource(identifier);
                if (object.has("scales_with_difficulty") && object.get("scales_with_difficulty").getAsBoolean()) damageSource.setScaledWithDifficulty();
                if (object.has("bypasses_armor") && object.get("bypasses_armor").getAsBoolean()) damageSource.setBypassesArmor();
                if (object.has("bypasses_protection") && object.get("bypasses_protection").getAsBoolean()) damageSource.setBypassesProtection();
                if (object.has("is_projectile") && object.get("is_projectile").getAsBoolean()) damageSource.setProjectile();
                if (object.has("is_explosive") && object.get("is_explosive").getAsBoolean()) damageSource.setExplosive();
                if (object.has("is_falling_block") && object.get("is_falling_block").getAsBoolean()) damageSource.setFallingBlock();
                if (object.has("is_out_of_world") && object.get("is_out_of_world").getAsBoolean()) damageSource.setOutOfWorld();
                if (object.has("is_unblockable") && object.get("is_unblockable").getAsBoolean()) damageSource.setUnblockable();
                if (object.has("is_fire") && object.get("is_fire").getAsBoolean()) damageSource.setFire();
                if (object.has("is_neutral") && object.get("is_neutral").getAsBoolean()) damageSource.setNeutral();
                if (object.has("is_magic") && object.get("is_magic").getAsBoolean()) damageSource.setUsesMagic();
                if (object.has("is_from_falling") && object.get("is_from_falling").getAsBoolean()) damageSource.setFromFalling();
                return damageSource;
            }
            else throw new JsonSyntaxException("Could not parse banner pattern.");
        }

        @Override
        public JsonElement serialize(CustomDamageSource src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.addProperty("identifier", src.getIdentifier().toString());
            object.addProperty("scales_with_difficulty", src.isScaledWithDifficulty());
            object.addProperty("bypasses_armor", src.bypassesArmor());
            object.addProperty("bypasses_protection", src.bypassesProtection());
            object.addProperty("is_projectile", src.isProjectile());
            object.addProperty("is_explosive", src.isExplosive());
            object.addProperty("is_falling_block", src.isFallingBlock());
            object.addProperty("is_out_of_world", src.isOutOfWorld());
            object.addProperty("is_unblockable", src.isUnblockable());
            object.addProperty("is_fire", src.isFire());
            object.addProperty("is_neutral", src.isNeutral());
            object.addProperty("is_magic", src.isMagic());
            object.addProperty("is_from_falling", src.isFromFalling());
            return object;
        }
    }
}
