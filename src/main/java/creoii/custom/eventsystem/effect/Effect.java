package creoii.custom.eventsystem.effect;

import com.google.gson.JsonObject;
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

public abstract class Effect {
    public static final String DROP_ITEM = "drop_item";
    public static final String SPAWN_ENTITY = "spawn_entity";
    public static final String HEAL = "heal";
    public static final String APPLY_STATUS_EFFECT = "apply_status_effect";
    public static final String DESTROY = "destroy";
    public static final String ADD_VELOCITY = "add_velocity";
    public static final String SEND_MESSAGE = "send_message";
    public static final String GENERATE_FEATURE = "generate_feature";
    public static final String GENERATE_STRUCTURE = "generate_structure";
    public static final String WEIGHTED_LIST = "weighted_list";
    public static final String EMIT_GAME_EVENT = "emit_game_event";
    public static final String GIVE_EXPERIENCE = "give_experience";
    public static final String SET_SPAWN = "set_spawn";
    public static final String SET_BLOCK = "set_block";

    private final String name;

    public Effect(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Effect getEffect(JsonObject object, String str) {
        return switch (str) {
            case DROP_ITEM -> DropItemEffect.getFromJson(object);
            case SPAWN_ENTITY -> SpawnEntityEffect.getFromJson(object);
            case HEAL -> HealEffect.getFromJson(object);
            case APPLY_STATUS_EFFECT -> ApplyStatusEffectEffect.getFromJson(object);
            case DESTROY -> DestroyEffect.getFromJson(object);
            case ADD_VELOCITY -> AddVelocityEffect.getFromJson(object);
            case SEND_MESSAGE -> SendMessageEffect.getFromJson(object);
            case GENERATE_FEATURE -> GenerateFeatureEffect.getFromJson(object);
            case GENERATE_STRUCTURE -> GenerateStructureEffect.getFromJson(object);
            case WEIGHTED_LIST -> WeightedListEffect.getFromJson(object);
            case EMIT_GAME_EVENT -> EmitGameEventEffect.getFromJson(object);
            case GIVE_EXPERIENCE -> GiveExperienceEffect.getFromJson(object);
            case SET_SPAWN -> SetSpawnEffect.getFromJson(object);
            case SET_BLOCK -> SetBlockEffect.getFromJson(object);
            default -> new NoEffect();
        };
    }

    public static Effect findEffect(Effect[] effects, String name) {
        for (Effect effect : effects) {
            if (effect.getName().equals(name)) return effect;
        } return null;
    }

    public abstract void runBlock(World world, BlockState state, BlockPos pos, LivingEntity living, Hand hand);
    public abstract void runItem(World world, ItemStack stack, BlockPos pos, PlayerEntity player, Hand hand);
    public abstract void runEntity(Entity entity, PlayerEntity player, Hand hand);
    public abstract void runEnchantment(Enchantment enchantment, Entity user, Entity target, int level);
    public abstract void runStatusEffect(StatusEffect statusEffect, LivingEntity entity, int amplifier);
    public abstract void runWorld(World world);
}
