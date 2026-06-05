package com.bumppo109.firma_compat.integration.legendarysurvivaloverhaul;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sfiomn.legendarysurvivaloverhaul.registry.TemperatureModifierRegistry;

public class LSOHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LSOHandler.class);

    public static void registerModifiers() {
        try {
            // Register your custom TFC climate model modifier
            TemperatureModifierRegistry.MODIFIERS.register("tfc_climate_modifier", TFCClimateModifier::new);
            TemperatureModifierRegistry.MODIFIERS.register("tfc_time_modifier", TFCTimeModifier::new);
            TemperatureModifierRegistry.MODIFIERS.register("tfc_altitude_modifier", TFCAltitudeModifier::new);

            LOGGER.info("Registered custom TFCClimateModelModifier with LSO");
        } catch (Exception e) {
            LOGGER.error("Failed to register custom LSO modifier", e);
        }
    }
}