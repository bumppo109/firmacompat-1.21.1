package com.bumppo109.firma_compat.block;

import com.bumppo109.firma_compat.item.ModItems;
import com.google.common.base.Suppliers;
import net.dries007.tfc.common.Lore;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.LooseRockBlock;
import net.dries007.tfc.common.blocks.rock.RockDisplayCategory;
import net.dries007.tfc.common.blocks.rock.RockSpikeBlock;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

import java.util.Locale;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public enum CompatRock implements ModRegistryRock {
    GRANITE(RockDisplayCategory.MAFIC_IGNEOUS_INTRUSIVE, MapColor.DIRT),
    DIORITE(RockDisplayCategory.INTERMEDIATE_IGNEOUS_INTRUSIVE, MapColor.QUARTZ),
    DEEPSLATE(RockDisplayCategory.FELSIC_IGNEOUS_INTRUSIVE, MapColor.DEEPSLATE),
    ANDESITE(RockDisplayCategory.INTERMEDIATE_IGNEOUS_EXTRUSIVE, MapColor.STONE),
    BASALT(RockDisplayCategory.MAFIC_IGNEOUS_EXTRUSIVE, MapColor.COLOR_BLACK),
    CALCITE(RockDisplayCategory.SEDIMENTARY, MapColor.TERRACOTTA_WHITE),
    TUFF(RockDisplayCategory.SEDIMENTARY, MapColor.TERRACOTTA_GRAY),
    DRIPSTONE(RockDisplayCategory.SEDIMENTARY, MapColor.TERRACOTTA_BROWN),
    STONE(RockDisplayCategory.METAMORPHIC, MapColor.STONE),
    BLACKSTONE(RockDisplayCategory.METAMORPHIC, MapColor.COLOR_BLACK),
    NETHERRACK(RockDisplayCategory.METAMORPHIC, MapColor.NETHER),
    END_STONE(RockDisplayCategory.METAMORPHIC, MapColor.SAND)
    ;

    public static final CompatRock[] VALUES = values();
    private final String serializedName;
    private final RockDisplayCategory category;
    private final MapColor color;

    private CompatRock(RockDisplayCategory category, MapColor color) {
        this.serializedName = this.name().toLowerCase(Locale.ROOT);
        this.category = category;
        this.color = color;
    }

    public Item.Properties createItemProperties() {
        return (new Item.Properties()).component(Lore.TYPE, (ItemLore)Lore.ROCK_DISPLAY_CATEGORIES.get(this.category));
    }

    public RockDisplayCategory displayCategory() {
        return this.category;
    }

    public MapColor color() {
        return this.color;
    }

    public Supplier<? extends Block> getBlock(BlockType type) {
        return (Supplier)((Map)TFCBlocks.ROCK_BLOCKS.get(this)).get(type);
    }

    public Supplier<? extends SlabBlock> getSlab(BlockType type) {
        return ((CompatDecorationBlockHolder)((Map)ModBlocks.ROCK_DECORATIONS.get(this)).get(type)).slab();
    }

    public Supplier<? extends StairBlock> getStair(BlockType type) {
        return ((CompatDecorationBlockHolder)((Map)ModBlocks.ROCK_DECORATIONS.get(this)).get(type)).stair();
    }

    public Supplier<? extends WallBlock> getWall(BlockType type) {
        return ((CompatDecorationBlockHolder)((Map)ModBlocks.ROCK_DECORATIONS.get(this)).get(type)).wall();
    }

    public String getSerializedName() {
        return this.serializedName;
    }

    public static enum BlockType implements StringRepresentable {
        HARDENED((rock, self) -> new Block(properties(rock).strength(rock.category().hardness(6.5f), 10).requiresCorrectToolForDrops()), false),
        LOOSE_COBBLE((rock, self) -> new Block(properties(rock).strength(rock.category().hardness(5.5f), 10).requiresCorrectToolForDrops()), false),
        HARDENED_COBBLE((rock, self) -> new Block(properties(rock).strength(rock.category().hardness(6.5f), 10).requiresCorrectToolForDrops()), false),
        LOOSE((rock, self) -> new LooseRockBlock(properties(rock).strength(0.05f, 0.0f).noCollission()), false),
        SPIKE((rock, self) -> new RockSpikeBlock(properties(rock).strength(rock.category().hardness(4.0F), 10.0F).requiresCorrectToolForDrops().lightLevel(TFCBlocks.lavaLoggedBlockEmission())), false),
        ;

        public static final BlockType[] VALUES = values();
        private final boolean variants;
        private final BiFunction<ModRegistryRock, BlockType, Block> blockFactory;
        private final String serializedName;

        public static BlockType valueOf(int i) {
            //return i >= 0 && i < VALUES.length ? VALUES[i] : RAW;
            return null;
        }

        private static Properties properties(ModRegistryRock rock) {
            return Properties.of().mapColor(rock.color()).sound(SoundType.STONE).instrument(NoteBlockInstrument.BASEDRUM);
        }

        private BlockType(BiFunction<ModRegistryRock, BlockType, Block> blockFactory, boolean variants) {
            this.blockFactory = blockFactory;
            this.variants = variants;
            this.serializedName = this.name().toLowerCase(Locale.ROOT);
        }

        public boolean hasVariants() {
            return this.variants;
        }

        public Block create(ModRegistryRock rock) {
            return (Block)this.blockFactory.apply(rock, this);
        }

        public SlabBlock createSlab(ModRegistryRock rock) {
            BlockBehaviour.Properties properties = Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(1.5F, 10.0F).requiresCorrectToolForDrops();
                return new SlabBlock(properties);
        }

        public StairBlock createStairs(ModRegistryRock rock) {
            BlockBehaviour.Properties properties = Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(1.5F, 10.0F).requiresCorrectToolForDrops();
                return new StairBlock(rock.getStair(this).get().defaultBlockState(), properties);
        }

        public WallBlock createWall(ModRegistryRock rock) {
            BlockBehaviour.Properties properties = Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(1.5F, 10.0F).requiresCorrectToolForDrops();
                return new WallBlock(properties);
        }

        public String getSerializedName() {
            return this.serializedName;
        }
    }

    public Supplier<Block> rawBlock() {
        return switch (this) {
            case ANDESITE -> () -> Blocks.ANDESITE;
            case DEEPSLATE -> () -> Blocks.DEEPSLATE;
            case DIORITE -> () -> Blocks.DIORITE;
            case CALCITE -> () -> Blocks.CALCITE;
            case END_STONE -> () -> Blocks.END_STONE;
            case STONE -> () -> Blocks.STONE;
            case BLACKSTONE -> () -> Blocks.BLACKSTONE;
            case NETHERRACK -> () -> Blocks.NETHERRACK;
            case GRANITE -> () -> Blocks.GRANITE;
            case BASALT -> () -> Blocks.BASALT;
            case TUFF -> () -> Blocks.TUFF;
            case DRIPSTONE -> () -> Blocks.DRIPSTONE_BLOCK;
        };
    }

    public Supplier<Block> brickBlock() {
        return switch (this) {
            case STONE -> () -> Blocks.STONE_BRICKS;
            case DEEPSLATE -> () -> Blocks.DEEPSLATE_BRICKS;
            case BLACKSTONE -> () -> Blocks.POLISHED_BLACKSTONE_BRICKS;
            case END_STONE -> () -> Blocks.END_STONE_BRICKS;
            case TUFF -> () -> Blocks.TUFF_BRICKS;
            default -> () -> null;
        };
    }

    public Supplier<Item> brickItem() {
        return switch (this) {
            case STONE -> ModItems.STONE_BRICK;
            case DEEPSLATE -> ModItems.DEEPSLATE_BRICK;
            case BLACKSTONE -> ModItems.POLISHED_BLACKSTONE_BRICK;
            case END_STONE -> ModItems.END_STONE_BRICK;
            case TUFF -> ModItems.TUFF_BRICK;
            case ANDESITE -> ModItems.ANDESITE_BRICK;
            case DIORITE -> ModItems.DIORITE_BRICK;
            case GRANITE -> ModItems.GRANITE_BRICK;
            case CALCITE -> ModItems.CALCITE_BRICK;
            case DRIPSTONE -> ModItems.DRIPSTONE_BRICK;
            case BASALT -> ModItems.BASALT_BRICK;
            default -> () -> null;
        };
    }
}
