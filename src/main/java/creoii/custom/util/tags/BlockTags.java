package creoii.custom.util.tags;

import creoii.custom.Custom;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class BlockTags {
    public static final TagKey<Block> AFFECTED_BY_GRAVITY = TagKey.of(RegistryKeys.BLOCK, new Identifier(Custom.NAMESPACE, "affected_by_gravity"));
    public static final TagKey<Block> BLOCKS_VEX = TagKey.of(RegistryKeys.BLOCK, new Identifier(Custom.NAMESPACE, "blocks_vex"));
    public static final TagKey<Block> PLAYER_IMMUNE = TagKey.of(RegistryKeys.BLOCK, new Identifier(Custom.NAMESPACE, "player_immune"));
    public static final TagKey<Block> RAVAGER_BREAKABLE = TagKey.of(RegistryKeys.BLOCK, new Identifier(Custom.NAMESPACE, "ravager_breakable"));
    public static final TagKey<Block> BOOSTS_ENCHANTMENTS = TagKey.of(RegistryKeys.BLOCK, new Identifier(Custom.NAMESPACE, "boosts_enchantments"));
    public static final TagKey<Block> END_CRYSTAL_BASE_BLOCKS = TagKey.of(RegistryKeys.BLOCK, new Identifier(Custom.NAMESPACE, "end_crystal_base_blocks"));
    public static final TagKey<Block> NETHER_PORTAL_FRAMES = TagKey.of(RegistryKeys.BLOCK, new Identifier(Custom.NAMESPACE, "nether_portal_frames"));
    public static final TagKey<Block> CONDUIT_FRAMES = TagKey.of(RegistryKeys.BLOCK, new Identifier(Custom.NAMESPACE, "conduit_frames"));
    public static final TagKey<Block> BEACON_BEAM_IGNORED = TagKey.of(RegistryKeys.BLOCK, new Identifier(Custom.NAMESPACE, "beacon_beam_ignored"));
    public static final TagKey<Block> NO_DROPS_ON_EXPLOSION = TagKey.of(RegistryKeys.BLOCK, new Identifier(Custom.NAMESPACE, "no_drops_on_explosion"));
}