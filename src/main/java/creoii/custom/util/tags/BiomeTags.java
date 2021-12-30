package creoii.custom.util.tags;

import net.minecraft.tag.RequiredTagList;
import net.minecraft.tag.RequiredTagListRegistry;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagGroup;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public final class BiomeTags {
    public static RequiredTagList<Biome> REQUIRED_TAGS;
    public static Tag.Identified<Biome> ANIMAL;
    public static Tag.Identified<Biome> BADLANDS;
    public static Tag.Identified<Biome> BEACH;
    public static Tag.Identified<Biome> BIRCH;
    public static Tag.Identified<Biome> COLD;
    public static Tag.Identified<Biome> DARK_OAK;
    public static Tag.Identified<Biome> DEEP_OCEAN;
    public static Tag.Identified<Biome> EDGE;
    public static Tag.Identified<Biome> FOREST;
    public static Tag.Identified<Biome> FLOWER_FOREST;
    public static Tag.Identified<Biome> FROZEN;
    public static Tag.Identified<Biome> HILLS;
    public static Tag.Identified<Biome> ICE;
    public static Tag.Identified<Biome> ICE_PLAINS;
    public static Tag.Identified<Biome> INFESTED;
    public static Tag.Identified<Biome> JUNGLE;
    public static Tag.Identified<Biome> LAKES;
    public static Tag.Identified<Biome> LUKEWARM;
    public static Tag.Identified<Biome> MEGA;
    public static Tag.Identified<Biome> MONSTER;
    public static Tag.Identified<Biome> MOOSHROOM;
    public static Tag.Identified<Biome> MODIFIED;
    public static Tag.Identified<Biome> MOUNTAIN;
    public static Tag.Identified<Biome> NETHER;
    public static Tag.Identified<Biome> OCEAN;
    public static Tag.Identified<Biome> PLAINS;
    public static Tag.Identified<Biome> PLATEAU;
    public static Tag.Identified<Biome> RIVER;
    public static Tag.Identified<Biome> ROOFED;
    public static Tag.Identified<Biome> SAVANNA;
    public static Tag.Identified<Biome> SHALLOW_OCEAN;
    public static Tag.Identified<Biome> SHORE;
    public static Tag.Identified<Biome> STONE;
    public static Tag.Identified<Biome> SWAMP;
    public static Tag.Identified<Biome> TAIGA;
    public static Tag.Identified<Biome> THE_END;
    public static Tag.Identified<Biome> UNDERGROUND;
    public static Tag.Identified<Biome> WARM;

    public static Tag.Identified<Biome> biome(String name) {
        return REQUIRED_TAGS.add(name);
    }

    public static TagGroup<Biome> getTagGroup() {
        return REQUIRED_TAGS.getGroup();
    }

    //change to 'static' when reimplementing
    public static void register() {
        //REQUIRED_TAGS = RequiredTagListRegistry.register(Registry.BIOME_KEY, "tags/biomes");
        ANIMAL = biome("animal");
        BADLANDS = biome("badlands");
        BEACH = biome("beach");
        BIRCH = biome("birch");
        COLD = biome("cold");
        DARK_OAK = biome("dark_oak");
        DEEP_OCEAN = biome("deep_ocean");
        FOREST = biome("forest");
        FLOWER_FOREST = biome("flower_forest");
        FROZEN = biome("frozen");
        ICE = biome("ice");
        ICE_PLAINS = biome("ice_plains");
        INFESTED = biome("infested");
        JUNGLE = biome("jungle");
        LAKES = biome("lakes");
        LUKEWARM = biome("lukewarm");
        MEGA = biome("mega");
        MONSTER = biome("monster");
        MOOSHROOM = biome("mooshroom");
        MODIFIED = biome("modified");
        MOUNTAIN = biome("mountain");
        NETHER = biome("nether");
        OCEAN = biome("ocean");
        PLAINS = biome("plains");
        PLATEAU = biome("plateau");
        RIVER = biome("river");
        ROOFED = biome("roofed");
        SAVANNA = biome("savanna");
        SHALLOW_OCEAN = biome("shallow_ocean");
        SHORE = biome("shore");
        STONE = biome("stone");
        SWAMP = biome("swamp");
        TAIGA = biome("taiga");
        THE_END = biome("the_end");
        UNDERGROUND = biome("underground");
        WARM = biome("warm");
    }
}