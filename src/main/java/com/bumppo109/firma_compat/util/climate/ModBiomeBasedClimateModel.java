package com.bumppo109.firma_compat.util.climate;

import io.netty.buffer.ByteBuf;
import net.dries007.tfc.util.climate.Climate;
import net.dries007.tfc.util.climate.ClimateModel;
import net.dries007.tfc.util.climate.ClimateModelType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class ModBiomeBasedClimateModel implements ClimateModel {
    public static final ModBiomeBasedClimateModel INSTANCE = new ModBiomeBasedClimateModel();
    private static Pair<BlockPos, Float> undergroundEffect = new ImmutablePair(new BlockPos(0, 0, 0), 0.0F);

    public static final StreamCodec<ByteBuf, ModBiomeBasedClimateModel> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    public ClimateModelType<?> type() {
        return (ClimateModelType) ModClimateModels.LSO_MODEL.get();
    }

    @Override
    public float getAverageTemperature(LevelReader reader, BlockPos pos) {
        Biome biome = reader.getBiome(pos).value();

        return Climate.fromVanilla(ClimateHelpers.normalizeTFCTemperature(biome.getBaseTemperature()));
    }

    @Override
    public float getAverageRainfall(LevelReader level, BlockPos pos) {
        return level.getBiome(pos).value().getPrecipitationAt(pos) != Biome.Precipitation.NONE ? 300.0F : 0.0F;
    }
}
