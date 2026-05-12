package com.bumppo109.firma_compat.integration.sereneseasons;

import sereneseasons.api.season.Season;

public class SereneSeasonsHelper {

    public static float getSereneSeasonalAdjustment(Season.SubSeason subSeason) {

        return switch (subSeason) {

            case EARLY_WINTER,
                 MID_WINTER,
                 LATE_WINTER -> -0.8F;

            case EARLY_SPRING,
                 LATE_AUTUMN -> -0.25F;

            default -> 0.0F;
        };
    }
}
