package com.bumppo109.firma_compat.datagen.helpers;

import net.cinchtail.cinchsmissingblocks.block.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

/**
 * Helper class containing publicly accessible sets of brick block variants from cinch's missing blocks.
 */
public final class CinchMissingBlocks {

    /**
     * Record representing one "brick set": regular bricks + cracked + chiseled variant.
     */
    public record BrickSet(
            Block bricks,
            Block cracked,
            Block chiseled
    ) {
        // Optional: helper method if you often need to iterate all three
        public List<Block> asList() {
            return List.of(bricks, cracked, chiseled);
        }
    }

    public record BrickVariants(
            Block bricks,
            Block stair,
            Block slab
    ) {
        // Optional: helper method if you often need to iterate all three
        public List<Block> asList() {
            return List.of(bricks, stair, slab);
        }
    }
    public record RawVariants(
            Block raw,
            Block rawStair,
            Block rawSlab
    ) {
        // Optional: helper method if you often need to iterate all three
        public List<Block> asList() {
            return List.of(raw, rawStair, rawSlab);
        }
    }


    /**
     * Public list of all brick sets (bricks → cracked → chiseled).
     * Add new entries here when cinch adds more stone types.
     */
    public static final List<BrickSet> BRICK_SETS = List.of(
            new BrickSet(
                    ModBlocks.ANDESITE_BRICKS.get(),
                    ModBlocks.CRACKED_ANDESITE_BRICKS.get(),
                    ModBlocks.CHISELED_ANDESITE_BRICKS.get()
            ),
            new BrickSet(
                    ModBlocks.DIORITE_BRICKS.get(),
                    ModBlocks.CRACKED_DIORITE_BRICKS.get(),
                    ModBlocks.CHISELED_DIORITE_BRICKS.get()
            ),
            new BrickSet(
                    ModBlocks.GRANITE_BRICKS.get(),
                    ModBlocks.CRACKED_GRANITE_BRICKS.get(),
                    ModBlocks.CHISELED_GRANITE_BRICKS.get()
            ),
            new BrickSet(
                    ModBlocks.CALCITE_BRICKS.get(),
                    ModBlocks.CRACKED_CALCITE_BRICKS.get(),
                    ModBlocks.CHISELED_CALCITE_BRICKS.get()
            ),
            new BrickSet(
                    ModBlocks.DRIPSTONE_BRICKS.get(),
                    ModBlocks.CRACKED_DRIPSTONE_BRICKS.get(),
                    ModBlocks.CHISELED_DRIPSTONE_BRICKS.get()
            ),
            new BrickSet(
                    Blocks.TUFF_BRICKS,
                    ModBlocks.CRACKED_TUFF_BRICKS.get(),
                    Blocks.CHISELED_TUFF_BRICKS
            ),
            new BrickSet(
                    Blocks.QUARTZ_BRICKS,
                    ModBlocks.CRACKED_QUARTZ_BRICKS.get(),
                    Blocks.CHISELED_QUARTZ_BLOCK
            ),
            new BrickSet(
                    Blocks.END_STONE_BRICKS,
                    ModBlocks.CRACKED_END_STONE_BRICKS.get(),
                    ModBlocks.CHISELED_END_STONE_BRICKS.get()
            ),
            new BrickSet(
                    Blocks.BRICKS,
                    ModBlocks.CRACKED_BRICKS.get(),
                    ModBlocks.CHISELED_BRICKS.get()
            ),
            new BrickSet(
                    Blocks.MUD_BRICKS,
                    ModBlocks.CRACKED_MUD_BRICKS.get(),
                    ModBlocks.CHISELED_MUD_BRICKS.get()
            ),
            new BrickSet(
                    Blocks.RED_NETHER_BRICKS,
                    ModBlocks.CRACKED_RED_NETHER_BRICKS.get(),
                    ModBlocks.CHISELED_RED_NETHER_BRICKS.get()
            )
    );

    public static final List<BrickVariants> BRICK_VARIANTS = List.of(
            new BrickVariants(
                    ModBlocks.ANDESITE_BRICKS.get(),
                    ModBlocks.ANDESITE_BRICK_STAIRS.get(),
                    ModBlocks.ANDESITE_BRICK_SLAB.get()
            ),
            new BrickVariants(
                    ModBlocks.CRACKED_ANDESITE_BRICKS.get(),
                    ModBlocks.CRACKED_ANDESITE_BRICK_STAIRS.get(),
                    ModBlocks.CRACKED_ANDESITE_BRICK_SLAB.get()
            ),
            new BrickVariants(
                    ModBlocks.MOSSY_ANDESITE_BRICKS.get(),
                    ModBlocks.MOSSY_ANDESITE_BRICK_STAIRS.get(),
                    ModBlocks.MOSSY_ANDESITE_BRICK_SLAB.get()
            ),
            new BrickVariants(
                    ModBlocks.DIORITE_BRICKS.get(),
                    ModBlocks.DIORITE_BRICK_STAIRS.get(),
                    ModBlocks.DIORITE_BRICK_SLAB.get()
            ),
            new BrickVariants(
                    ModBlocks.CRACKED_DIORITE_BRICKS.get(),
                    ModBlocks.CRACKED_DIORITE_BRICK_STAIRS.get(),
                    ModBlocks.CRACKED_DIORITE_BRICK_SLAB.get()
            ),
            new BrickVariants(
                    ModBlocks.MOSSY_DIORITE_BRICKS.get(),
                    ModBlocks.MOSSY_DIORITE_BRICK_STAIRS.get(),
                    ModBlocks.MOSSY_DIORITE_BRICK_SLAB.get()
            ),
            new BrickVariants(
                    ModBlocks.GRANITE_BRICKS.get(),
                    ModBlocks.GRANITE_BRICK_STAIRS.get(),
                    ModBlocks.GRANITE_BRICK_SLAB.get()
            ),
            new BrickVariants(
                    ModBlocks.CRACKED_GRANITE_BRICKS.get(),
                    ModBlocks.CRACKED_GRANITE_BRICK_STAIRS.get(),
                    ModBlocks.CRACKED_GRANITE_BRICK_SLAB.get()
            ),
            new BrickVariants(
                    ModBlocks.MOSSY_GRANITE_BRICKS.get(),
                    ModBlocks.MOSSY_GRANITE_BRICK_STAIRS.get(),
                    ModBlocks.MOSSY_GRANITE_BRICK_SLAB.get()
            ),
            new BrickVariants(
                    ModBlocks.POLISHED_CALCITE.get(),
                    ModBlocks.POLISHED_CALCITE_STAIRS.get(),
                    ModBlocks.POLISHED_CALCITE_SLAB.get()
            ),
            new BrickVariants(
                    ModBlocks.CALCITE_BRICKS.get(),
                    ModBlocks.CALCITE_BRICK_STAIRS.get(),
                    ModBlocks.CALCITE_BRICK_SLAB.get()
            ),
            new BrickVariants(
                    ModBlocks.MOSSY_CALCITE_BRICKS.get(),
                    ModBlocks.MOSSY_CALCITE_BRICK_STAIRS.get(),
                    ModBlocks.MOSSY_CALCITE_BRICK_SLAB.get()
            ),
            new BrickVariants(
                    ModBlocks.CRACKED_CALCITE_BRICKS.get(),
                    ModBlocks.CRACKED_CALCITE_BRICK_STAIRS.get(),
                    ModBlocks.CRACKED_CALCITE_BRICK_SLAB.get()
            ),
            new BrickVariants(
                    ModBlocks.POLISHED_DRIPSTONE.get(),
                    ModBlocks.POLISHED_DRIPSTONE_STAIRS.get(),
                    ModBlocks.POLISHED_DRIPSTONE_SLAB.get()
            ),
            new BrickVariants(
                    ModBlocks.DRIPSTONE_BRICKS.get(),
                    ModBlocks.DRIPSTONE_BRICK_STAIRS.get(),
                    ModBlocks.DRIPSTONE_BRICK_SLAB.get()
            ),
            new BrickVariants(
                    ModBlocks.MOSSY_DRIPSTONE_BRICKS.get(),
                    ModBlocks.MOSSY_DRIPSTONE_BRICK_STAIRS.get(),
                    ModBlocks.MOSSY_DRIPSTONE_BRICK_SLAB.get()
            ),
            new BrickVariants(
                    ModBlocks.CRACKED_DRIPSTONE_BRICKS.get(),
                    ModBlocks.CRACKED_DRIPSTONE_BRICK_STAIRS.get(),
                    ModBlocks.CRACKED_DRIPSTONE_BRICK_SLAB.get()
            ),
            new BrickVariants(
                    ModBlocks.CRACKED_RED_NETHER_BRICKS.get(),
                    ModBlocks.CRACKED_RED_NETHER_BRICK_STAIRS.get(),
                    ModBlocks.CRACKED_RED_NETHER_BRICK_SLAB.get()
            ),
            new BrickVariants(
                    ModBlocks.CRACKED_MUD_BRICKS.get(),
                    ModBlocks.CRACKED_MUD_BRICK_STAIRS.get(),
                    ModBlocks.CRACKED_MUD_BRICK_SLAB.get()
            ),
            new BrickVariants(
                    ModBlocks.MOSSY_MUD_BRICKS.get(),
                    ModBlocks.MOSSY_MUD_BRICK_STAIRS.get(),
                    ModBlocks.MOSSY_MUD_BRICK_SLAB.get()
            ),
            new BrickVariants(
                    ModBlocks.CRACKED_BRICKS.get(),
                    ModBlocks.CRACKED_BRICK_STAIRS.get(),
                    ModBlocks.CRACKED_BRICK_SLAB.get()
            ),
            new BrickVariants(
                    Blocks.SMOOTH_BASALT,
                    ModBlocks.SMOOTH_BASALT_STAIRS.get(),
                    ModBlocks.SMOOTH_BASALT_SLAB.get()
            ),
            new BrickVariants(
                    ModBlocks.CRACKED_END_STONE_BRICKS.get(),
                    ModBlocks.CRACKED_END_STONE_BRICK_STAIRS.get(),
                    ModBlocks.CRACKED_END_STONE_BRICK_SLAB.get()
            ),
            new BrickVariants(
                    ModBlocks.CRACKED_PRISMARINE_BRICKS.get(),
                    ModBlocks.CRACKED_PRISMARINE_BRICK_STAIRS.get(),
                    ModBlocks.CRACKED_PRISMARINE_BRICK_SLAB.get()
            ),
            new BrickVariants(
                    ModBlocks.MOSSY_PRISMARINE_BRICKS.get(),
                    ModBlocks.MOSSY_PRISMARINE_BRICK_STAIRS.get(),
                    ModBlocks.MOSSY_PRISMARINE_BRICK_SLAB.get()
            ),
            new BrickVariants(
                    ModBlocks.CRACKED_QUARTZ_BRICKS.get(),
                    ModBlocks.CRACKED_QUARTZ_BRICK_STAIRS.get(),
                    ModBlocks.CRACKED_QUARTZ_BRICK_SLAB.get()
            ),
            new BrickVariants(
                    ModBlocks.MOSSY_QUARTZ_BRICKS.get(),
                    ModBlocks.MOSSY_QUARTZ_BRICK_STAIRS.get(),
                    ModBlocks.MOSSY_QUARTZ_BRICK_SLAB.get()
            ),
            new BrickVariants(
                    Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS,
                    ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_STAIRS.get(),
                    ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB.get()
            ),
            new BrickVariants(
                    Blocks.CRACKED_DEEPSLATE_BRICKS,
                    ModBlocks.CRACKED_DEEPSLATE_BRICK_STAIRS.get(),
                    ModBlocks.CRACKED_DEEPSLATE_BRICK_SLAB.get()
            ),
            new BrickVariants(
                    Blocks.CRACKED_DEEPSLATE_TILES,
                    ModBlocks.CRACKED_DEEPSLATE_TILE_STAIRS.get(),
                    ModBlocks.CRACKED_DEEPSLATE_TILE_SLAB.get()
            ),
            new BrickVariants(
                    Blocks.CRACKED_STONE_BRICKS,
                    ModBlocks.CRACKED_STONE_BRICK_STAIRS.get(),
                    ModBlocks.CRACKED_STONE_BRICK_SLAB.get()
            ),
            new BrickVariants(
                    Blocks.DRIPSTONE_BLOCK,
                    ModBlocks.DRIPSTONE_STAIRS.get(),
                    ModBlocks.DRIPSTONE_SLAB.get()
            ),
            new BrickVariants(
                    Blocks.CALCITE,
                    ModBlocks.CALCITE_STAIRS.get(),
                    ModBlocks.CALCITE_SLAB.get()
            ),
            new BrickVariants(
                    Blocks.PACKED_MUD,
                    ModBlocks.PACKED_MUD_STAIRS.get(),
                    ModBlocks.PACKED_MUD_SLAB.get()
            ),
            new BrickVariants(
                    Blocks.NETHERRACK,
                    ModBlocks.NETHERRACK_STAIRS.get(),
                    ModBlocks.NETHERRACK_SLAB.get()
            ),
            new BrickVariants(
                    Blocks.END_STONE,
                    ModBlocks.END_STONE_STAIRS.get(),
                    ModBlocks.END_STONE_SLAB.get()
            )
    );

    public static final List<RawVariants> RAW_SET = List.of(
            new RawVariants(
                    Blocks.DRIPSTONE_BLOCK,
                    ModBlocks.DRIPSTONE_STAIRS.get(),
                    ModBlocks.DRIPSTONE_SLAB.get()
            ),
            new RawVariants(
                    Blocks.CALCITE,
                    ModBlocks.CALCITE_STAIRS.get(),
                    ModBlocks.CALCITE_SLAB.get()
            ),
            new RawVariants(
                    Blocks.PACKED_MUD,
                    ModBlocks.PACKED_MUD_STAIRS.get(),
                    ModBlocks.PACKED_MUD_SLAB.get()
            ),
            new RawVariants(
                    Blocks.NETHERRACK,
                    ModBlocks.NETHERRACK_STAIRS.get(),
                    ModBlocks.NETHERRACK_SLAB.get()
            ),
            new RawVariants(
                    Blocks.END_STONE,
                    ModBlocks.END_STONE_STAIRS.get(),
                    ModBlocks.END_STONE_SLAB.get()
            )
    );

    // Private constructor – prevents instantiation
    private CinchMissingBlocks() {
        throw new UnsupportedOperationException("Utility class – do not instantiate");
    }
}