package com.bumppo109.firma_compat.integration.rnr;

import com.bumppo109.firma_compat.block.CompatRock;
import com.bumppo109.firma_compat.block.ModRegistryRock;
import com.therighthon.rnr.common.block.PathHeightBlock;
import com.therighthon.rnr.common.block.PathSlabBlock;
import com.therighthon.rnr.common.block.PathStairBlock;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public enum CompatRNR implements StringRepresentable {
    FLAGSTONE((rock, self) -> new PathHeightBlock(properties(rock).strength(rock.category().hardness(3.0F), 8.0F)), true),
    SETT_ROAD((rock, self) -> new PathHeightBlock(properties(rock).strength(rock.category().hardness(3.0F), 8.0F)), true),
    COBBLED_ROAD((rock, self) -> new PathHeightBlock(properties(rock).strength(rock.category().hardness(3.0F), 8.0F)), true);

    public static final CompatRNR[] VALUES = values();
    private final boolean variants;
    private final BiFunction<ModRegistryRock, CompatRNR, Block> blockFactory;
    private final String serializedName;

    /*
    public static CompatRNR valueOf(int i) {
        return i >= 0 && i < VALUES.length ? VALUES[i] : GRAVEL_ROAD;
    }
    
     */

    private static Properties properties(ModRegistryRock rock) {
        return Properties.of().mapColor(rock.color()).sound(SoundType.STONE).instrument(NoteBlockInstrument.BASEDRUM);
    }

    private CompatRNR(BiFunction<ModRegistryRock, CompatRNR, Block> blockFactory, boolean variants) {
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

    public Block createRockSlab(ModRegistryRock rock, CompatRNR type) {
            Properties properties = Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(rock.category().hardness(3.0F), 8.0F).requiresCorrectToolForDrops();
            return new PathSlabBlock(properties);
    }

    public PathStairBlock createPathStairs(CompatRock rock, CompatRNR type) {
        Supplier<BlockState> state = () -> ((Block)rock.rawBlock()).defaultBlockState();
            Properties properties = Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(rock.category().hardness(3.0F), 8.0F).requiresCorrectToolForDrops();
            return new PathStairBlock(state, properties);
    }

    public String getSerializedName() {
        return this.serializedName;
    }
}
