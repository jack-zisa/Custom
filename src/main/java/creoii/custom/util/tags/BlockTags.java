package creoii.custom.util.tags;

import creoii.custom.Custom;
import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockTags {
    public static final TagKey<Block> AFFECTED_BY_GRAVITY = TagKey.of(Registry.BLOCK_KEY, new Identifier(Custom.MOD_ID, "affected_by_gravity"));
    public static final TagKey<Block> BLOCKS_VEX = TagKey.of(Registry.BLOCK_KEY, new Identifier(Custom.MOD_ID, "blocks_vex"));
    public static final TagKey<Block> PLAYER_IMMUNE = TagKey.of(Registry.BLOCK_KEY, new Identifier(Custom.MOD_ID, "player_immune"));
    public static final TagKey<Block> RAVAGER_BREAKABLE = TagKey.of(Registry.BLOCK_KEY, new Identifier(Custom.MOD_ID, "ravager_breakable"));
    public static final TagKey<Block> BOOSTS_ENCHANTMENTS = TagKey.of(Registry.BLOCK_KEY, new Identifier(Custom.MOD_ID, "boosts_enchantments"));
    public static final TagKey<Block> END_CRYSTAL_BASE_BLOCKS = TagKey.of(Registry.BLOCK_KEY, new Identifier(Custom.MOD_ID, "end_crystal_base_blocks"));
    public static final TagKey<Block> NETHER_PORTAL_FRAMES = TagKey.of(Registry.BLOCK_KEY, new Identifier(Custom.MOD_ID, "nether_portal_frames"));
    public static final TagKey<Block> CONDUIT_FRAMES = TagKey.of(Registry.BLOCK_KEY, new Identifier(Custom.MOD_ID, "conduit_frames"));
    public static final TagKey<Block> BEACON_BEAM_IGNORED = TagKey.of(Registry.BLOCK_KEY, new Identifier(Custom.MOD_ID, "beacon_beam_ignored"));
    public static final TagKey<Block> NO_DROPS_ON_EXPLOSION = TagKey.of(Registry.BLOCK_KEY, new Identifier(Custom.MOD_ID, "no_drops_on_explosion"));
}