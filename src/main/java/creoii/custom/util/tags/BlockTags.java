package creoii.custom.util.tags;

import creoii.custom.Custom;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class BlockTags {
    public static final Tag<Block> AFFECTED_BY_GRAVITY = TagFactory.BLOCK.create(new Identifier(Custom.MOD_ID, "affected_by_gravity"));
    public static final Tag<Block> BLOCKS_VEX = TagFactory.BLOCK.create(new Identifier(Custom.MOD_ID, "blocks_vex"));
    public static final Tag<Block> PLAYER_IMMUNE = TagFactory.BLOCK.create(new Identifier(Custom.MOD_ID, "player_immune"));
    public static final Tag<Block> RAVAGER_BREAKABLE = TagFactory.BLOCK.create(new Identifier(Custom.MOD_ID, "ravager_breakable"));
    public static final Tag<Block> BOOSTS_ENCHANTMENTS = TagFactory.BLOCK.create(new Identifier(Custom.MOD_ID, "boosts_enchantments"));
    public static final Tag<Block> END_CRYSTAL_BASE_BLOCKS = TagFactory.BLOCK.create(new Identifier(Custom.MOD_ID, "end_crystal_base_blocks"));
    public static final Tag<Block> NETHER_PORTAL_FRAMES = TagFactory.BLOCK.create(new Identifier(Custom.MOD_ID, "nether_portal_frames"));
    public static final Tag<Block> CONDUIT_FRAMES = TagFactory.BLOCK.create(new Identifier(Custom.MOD_ID, "conduit_frames"));
    public static final Tag<Block> BEACON_BEAM_IGNORED = TagFactory.BLOCK.create(new Identifier(Custom.MOD_ID, "beacon_beam_ignored"));
    public static final Tag<Block> NO_DROPS_ON_EXPLOSION = TagFactory.BLOCK.create(new Identifier(Custom.MOD_ID, "no_drops_on_explosion"));
}