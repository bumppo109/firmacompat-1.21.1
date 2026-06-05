package com.bumppo109.firma_compat.integration.sereneseasons;

import com.bumppo109.firma_compat.util.climate.ModClimateModels;
import com.bumppo109.firma_compat.util.climate.VanillaClimateHelper;
import io.netty.buffer.ByteBuf;
import net.dries007.tfc.util.climate.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
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

        float temp = VanillaClimateHelper.getTemperature(reader, pos);

        if (reader instanceof Level level) {

            Season.SubSeason subSeason =
                    SeasonHelper.getSeasonState(level).getSubSeason();

            float seasonalAdjustment = SereneSeasonsHelper.getSereneSeasonalAdjustment(subSeason);

            /*
             * Serene Seasons adjustment is in vanilla biome-temp units.
             *
             * Apply BEFORE TFC conversion.
             */
            temp += seasonalAdjustment;
        }

        return temp;
    }

    @Override
    public float getAverageRainfall(LevelReader level, BlockPos pos) {
        return VanillaClimateHelper.getRainfall(level, pos);
    }
}
