package com.bumppo109.firma_compat.entity;

import com.bumppo109.firma_compat.FirmaCompatHelpers;
import com.bumppo109.firma_compat.worldgen.placement.CompatClimatePlacement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.data.DataManager;
import net.dries007.tfc.world.chunkdata.ForestType;
import net.dries007.tfc.world.placement.ClimatePlacement;

public record CompatFauna(int chance, int distanceBelowSeaLevel, CompatClimatePlacement climate, boolean solidGround, int maxBrightness, List<Month> months) {
    public static final Codec<CompatFauna> CODEC = RecordCodecBuilder.create((i) -> i.group(Codec.INT.optionalFieldOf("chance", 1).forGetter((c) -> c.chance), Codec.INT.optionalFieldOf("distance_below_sea_level", -1).forGetter((c) -> c.distanceBelowSeaLevel), CompatClimatePlacement.CODEC.forGetter((c) -> c.climate), Codec.BOOL.optionalFieldOf("solid_ground", false).forGetter((c) -> c.solidGround), Codec.INT.optionalFieldOf("max_brightness", -1).forGetter((c) -> c.maxBrightness), Month.CODEC.listOf().optionalFieldOf("months", Collections.emptyList()).forGetter((c) -> c.months)).apply(i, CompatFauna::new));
    public static final DataManager<CompatFauna> MANAGER;

    static {
        MANAGER = new DataManager(FirmaCompatHelpers.modIdentifier("compat_fauna"), CODEC);
    }

    public static final class Builder {
        float minTemperature = Float.NEGATIVE_INFINITY;
        float maxTemperature = Float.POSITIVE_INFINITY;
        float minRainfall = Float.NEGATIVE_INFINITY;
        float maxRainfall = Float.POSITIVE_INFINITY;
        float minRainVariance = -1.0F;
        float maxRainVariance = 1.0F;
        boolean rainVarianceAbsolute = false;
        int minForest = 0;
        int maxForest = 4;
        List<ForestType> forests = new ArrayList();
        int minElevation = -64;
        int maxElevation = 320;
        boolean fuzzy = false;
        boolean ignoreRivers = false;
        int chance = 1;
        int distanceBelowSeaLevel = -1;
        boolean solidGround = false;
        int maxBrightness = -1;
        List<Month> months = new ArrayList();

        public CompatFauna.Builder minTemperature(float min) {
            return this.temperature(min, Float.POSITIVE_INFINITY);
        }

        public CompatFauna.Builder maxTemperature(float max) {
            return this.temperature(Float.NEGATIVE_INFINITY, max);
        }

        public CompatFauna.Builder temperature(float min, float max) {
            this.minTemperature = min;
            this.maxTemperature = max;
            return this;
        }

        public CompatFauna.Builder minGroundwater(float min) {
            return this.groundwater(min, Float.POSITIVE_INFINITY);
        }

        public CompatFauna.Builder maxGroundwater(float max) {
            return this.groundwater(Float.NEGATIVE_INFINITY, max);
        }

        public CompatFauna.Builder groundwater(float min, float max) {
            this.minRainfall = min;
            this.maxRainfall = max;
            return this;
        }

        public CompatFauna.Builder minRainVariance(float min) {
            return this.rainVariance(min, 1.0F, false);
        }

        public CompatFauna.Builder maxRainVariance(float max) {
            return this.rainVariance(-1.0F, max, false);
        }

        public CompatFauna.Builder rainVariance(float min, float max, boolean isAbsolute) {
            this.minRainfall = min;
            this.maxRainfall = max;
            this.rainVarianceAbsolute = isAbsolute;
            return this;
        }

        public CompatFauna.Builder minForest(int min) {
            return this.forest(min, 4);
        }

        public CompatFauna.Builder maxForest(int max) {
            return this.forest(0, max);
        }

        public CompatFauna.Builder forest(int min, int max) {
            this.minForest = min;
            this.maxForest = max;
            return this;
        }

        public CompatFauna.Builder forestType(ForestType... types) {
            this.forests.addAll(List.of(types));
            return this;
        }

        public CompatFauna.Builder minElevation(int min) {
            return this.elevation(min, 320);
        }

        public CompatFauna.Builder maxElevation(int max) {
            return this.elevation(-64, max);
        }

        public CompatFauna.Builder elevation(int min, int max) {
            this.minElevation = min;
            this.maxElevation = max;
            return this;
        }

        public CompatFauna.Builder months(List<Month> months) {
            this.months.addAll(months);
            return this;
        }

        public CompatFauna.Builder chance(int value) {
            this.chance = value;
            return this;
        }

        public CompatFauna.Builder distanceBelowSeaLevel(int value) {
            this.distanceBelowSeaLevel = value;
            return this;
        }

        public CompatFauna.Builder maxBrightness(int value) {
            this.maxBrightness = value;
            return this;
        }

        public CompatFauna.Builder fuzzy() {
            this.fuzzy = true;
            return this;
        }

        public CompatFauna.Builder solid() {
            this.solidGround = true;
            return this;
        }

        public CompatFauna.Builder ignoreRivers() {
            this.ignoreRivers = true;
            return this;
        }

        public CompatFauna build() {
            return new CompatFauna(this.chance, this.distanceBelowSeaLevel, new CompatClimatePlacement(this.minTemperature, this.maxTemperature, this.minRainfall, this.maxRainfall, this.minElevation, this.maxElevation), this.solidGround, this.maxBrightness, this.months);
        }
    }
}

