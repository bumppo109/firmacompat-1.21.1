package com.bumppo109.firma_compat.integration.sereneseasons;

import com.bumppo109.firma_compat.util.climate.ClimateHelpers;
import com.bumppo109.firma_compat.util.climate.ModClimateModels;
import io.netty.buffer.ByteBuf;
import net.dries007.tfc.util.climate.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.fml.ModList;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;

public class SereneClimateModel implements ClimateModel {
    public static final SereneClimateModel INSTANCE = new SereneClimateModel();

    public static final StreamCodec<ByteBuf, SereneClimateModel> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    public ClimateModelType<?> type() {
        return (ClimateModelType) ModClimateModels.SERENE_MODEL.get();
    }

    @Override
    public float getAverageTemperature(LevelReader reader, BlockPos pos) {
        Biome biome = reader.getBiome(pos).value();
        float vanillaBase = biome.getBaseTemperature();

        if (ModList.get().isLoaded("sereneseasons") && reader instanceof Level level) {
            Season.SubSeason sub = SeasonHelper.getSeasonState(level).getSubSeason();
            //seasonal adjustment
            vanillaBase = vanillaBase + ClimateHelpers.getSereneSeasonalAdjustment(sub);
        }

        return Climate.fromVanilla(ClimateHelpers.normalizeTFCTemperature(vanillaBase));
    }

    @Override
    public float getAverageRainfall(LevelReader level, BlockPos pos) {
        return level.getBiome(pos).value().getPrecipitationAt(pos) != Biome.Precipitation.NONE ? 300.0F : 0.0F;
    }
}
