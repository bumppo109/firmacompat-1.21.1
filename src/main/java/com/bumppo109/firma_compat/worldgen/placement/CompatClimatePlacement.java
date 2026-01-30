package com.bumppo109.firma_compat.worldgen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dries007.tfc.client.overworld.SolarCalculator;
import net.dries007.tfc.util.EnvironmentHelpers;
import net.dries007.tfc.util.climate.BiomeBasedClimateModel;
import net.dries007.tfc.util.climate.Climate;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.chunkdata.ForestType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.stream.Stream;

import static vazkii.patchouli.api.PatchouliAPI.LOGGER;

public class CompatClimatePlacement extends PlacementModifier {

    // Codec matching relevant fields from TFC ClimatePlacement, with defaults to make them optional
    public static final MapCodec<CompatClimatePlacement> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.FLOAT.optionalFieldOf("min_temperature", Float.NEGATIVE_INFINITY).forGetter(c -> c.minTemp),
                    Codec.FLOAT.optionalFieldOf("max_temperature", Float.POSITIVE_INFINITY).forGetter(c -> c.maxTemp),
                    Codec.FLOAT.optionalFieldOf("min_groundwater", Float.NEGATIVE_INFINITY).forGetter(c -> c.minGroundwater),
                    Codec.FLOAT.optionalFieldOf("max_groundwater", Float.POSITIVE_INFINITY).forGetter(c -> c.maxGroundwater),
                    Codec.INT.optionalFieldOf("min_elevation", Integer.MIN_VALUE).forGetter(c -> c.minElevation),
                    Codec.INT.optionalFieldOf("max_elevation", Integer.MAX_VALUE).forGetter(c -> c.maxElevation)
            ).apply(instance, CompatClimatePlacement::new)
    );

    private final float minTemp;
    private final float maxTemp;
    private final float minGroundwater;
    private final float maxGroundwater;
    private final int minElevation;
    private final int maxElevation;

    public CompatClimatePlacement(
            float minTemp, float maxTemp,
            float minGroundwater, float maxGroundwater,
            int minElevation, int maxElevation
    ) {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.minGroundwater = minGroundwater;
        this.maxGroundwater = maxGroundwater;
        this.minElevation = minElevation;
        this.maxElevation = maxElevation;

    }

    @Override
    public PlacementModifierType<?> type() {
        return ModPlacement.COMPAT_CLIMATE_PLACEMENT.get();
    }

    public boolean isValid(WorldGenLevel level, BlockPos pos, RandomSource random) {
        Holder<Biome> biomeHolder = level.getBiome(pos);
        Biome biome = biomeHolder.value();

        // 1. Use the exact same conversion TFC uses
        float vanillaTemp = Climate.fromVanilla(biome.getBaseTemperature());

        // 2. Apply the exact same elevation adjustment TFC uses everywhere
        float adjustedTemp = EnvironmentHelpers.adjustAvgTempForElev(pos.getY(), vanillaTemp);

        // 3. Rainfall proxy — this is the best available without chunk data
        //    (TFC falls back to average rainfall in non-TFC contexts too)
        //TODO - not sure this matters if the model is either SereneClimateModel or BiomeBasedClimateModel
        float rainfall = BiomeBasedClimateModel.INSTANCE.getAverageRainfall(level, pos);

        int elevation = pos.getY();

        // Core validity checks — same as TFC's main conditions
        boolean elevationOk = elevation >= minElevation && elevation <= maxElevation;
        boolean tempOk     = adjustedTemp >= minTemp && adjustedTemp <= maxTemp;
        boolean rainOk     = rainfall >= minGroundwater && rainfall <= maxGroundwater;

        boolean valid = elevationOk && tempOk && rainOk;

//        LOGGER.debug("Climate check at {} | Biome: {} | Temp: {} (ok: {}) | Rainfall: {} (ok: {}) | Elev: {} (ok: {}) | Overall Valid: {}",
//                pos, biomeHolder.getKey().location(), adjustedTemp, tempOk, rainfall, rainOk, elevation, elevationOk, valid);


        return valid;
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos pos) {
        WorldGenLevel level = context.getLevel();
        if (isValid(level, pos, random)) {
            return Stream.of(pos);
        }
        return Stream.empty();
    }
}