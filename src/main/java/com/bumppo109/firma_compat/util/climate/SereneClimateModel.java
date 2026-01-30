package com.bumppo109.firma_compat.util.climate;

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

            // Hardcoded from your actual config file (exact values)
            float adjustment = switch (sub) {
                // Winter (all sub-seasons = -0.8)
                case EARLY_WINTER, MID_WINTER, LATE_WINTER -> -0.8f;

                // Spring
                case EARLY_SPRING -> -0.25f;
                case MID_SPRING, LATE_SPRING -> 0.0f;

                // Summer (all = 0.0)
                case EARLY_SUMMER, MID_SUMMER, LATE_SUMMER -> 0.0f;

                // Autumn
                case EARLY_AUTUMN, MID_AUTUMN -> 0.0f;
                case LATE_AUTUMN -> -0.25f;

                default -> 0.0f;
            };
            vanillaBase = vanillaBase + adjustment;
        }

        return Climate.fromVanilla(vanillaBase);
    }

    @Override
    public float getAverageRainfall(LevelReader level, BlockPos pos) {
        return level.getBiome(pos).value().getPrecipitationAt(pos) != Biome.Precipitation.NONE ? 300.0F : 0.0F;
    }
}
