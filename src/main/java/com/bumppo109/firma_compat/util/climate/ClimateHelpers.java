package com.bumppo109.firma_compat.util.climate;

import sereneseasons.api.season.Season;

public class ClimateHelpers {
    public static float normalizeTFCTemperature(float vanillaBiomeTemp) {
        float scaled = vanillaBiomeTemp / 2.5f;

        // Apply tanh to squash outliers while preserving mid-range variability
        float tanhValue = (float) Math.tanh(scaled);

        float normalized = -0.7f + (tanhValue + 1.0f) * 1.0f;

        return normalized;
    }

    public static float getSereneSeasonalAdjustment(Season.SubSeason sub){
        return switch (sub) {
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
    }
}
