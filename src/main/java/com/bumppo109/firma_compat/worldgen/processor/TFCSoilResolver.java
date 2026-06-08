package com.bumppo109.firma_compat.worldgen.processor;

import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.common.blocks.soil.SoilBlockType.Variant;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.noise.Noise2D;
import net.dries007.tfc.world.noise.OpenSimplex2D;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;
import java.util.function.Supplier;

public final class TFCSoilResolver
{
    public static final Noise2D PATCH_NOISE = (new OpenSimplex2D(18273952837592L)).octaves(2).spread((double)0.04F);
    /*
    private static final OpenSimplex2D PATCH_NOISE =
            new OpenSimplex2D(18273952837592L);

     */

    private TFCSoilResolver()
    {
    }

    public static BlockState getNaturalDirt(LevelReader level, BlockPos pos)
    {
        return getNaturalSoil(level, pos, SoilBlockType.DIRT);
    }

    public static BlockState getNaturalGrass(LevelReader level, BlockPos pos)
    {
        return getNaturalSoil(level, pos, SoilBlockType.GRASS);
    }

    public static BlockState getNaturalMud(LevelReader level, BlockPos pos)
    {
        return getNaturalSoil(level, pos, SoilBlockType.MUD);
    }

    public static BlockState getNaturalSoil(
            LevelReader level,
            BlockPos pos,
            SoilBlockType soilType
    )
    {
        ChunkData data = ChunkData.get(level, pos);

        float avgTemp =
                Helpers.adjustAverageTemperatureByElevation(
                        pos.getY(),
                        data.getAverageSeaLevelTemp(pos),
                        63f
                );

        float groundwater =
                data.getBaseGroundwater(pos)
                        + data.getAverageRainfall(pos);

        float rainVariance =
                data.getRainVariance(pos);

        Variant variant = determineVariant(
                pos,
                avgTemp,
                groundwater,
                rainVariance
        );

        Supplier<Block> block =
                (Supplier<Block>) ((Map<?, ?>) TFCBlocks.SOIL.get(soilType))
                        .get(variant);

        return block.get().defaultBlockState();
    }

    private static Variant determineVariant(
            BlockPos pos,
            float temperature,
            float groundwater,
            float rainVariance
    )
    {
        /*
         * Direct copy of SoilSurfaceState.transitioningSoil()
         */

        if (groundwater > 25f && Math.abs(rainVariance) > 0.5f)
        {
            return Variant.FLUVISOL;
        }

        if (temperature < 16f)
        {
            return Variant.ENTISOL;
        }

        if (temperature > 16.7f)
        {
            return Variant.OXISOL;
        }

        double noise = PATCH_NOISE.noise(
                pos.getX(),
                pos.getZ()
        );

        return noise > 0
                ? Variant.OXISOL
                : Variant.ENTISOL;
    }
}