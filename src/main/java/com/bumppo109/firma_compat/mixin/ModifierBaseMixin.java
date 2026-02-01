package com.bumppo109.firma_compat.mixin;

import net.dries007.tfc.util.climate.Climate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import sfiomn.legendarysurvivaloverhaul.api.temperature.ModifierBase;

/**
 * Replaces LSO's base biome temperature source with TFC's instant climate temperature.
 * This happens very early in the temperature pipeline, before most modifiers are applied.
 */
@Mixin(ModifierBase.class)
public abstract class ModifierBaseMixin {

    /**
     * Redirects the biome base temperature read inside getNormalizedTempForBiome.
     * We replace it with a call to TFC Climate.getInstantTemperature using a representative position.
     */
    @Redirect(
            method = "getNormalizedTempForBiome",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/biome/Biome;getBaseTemperature()F"
            )
    )
    private float useTfcInstantTemperature(Biome biome, Level level, Biome ignoredBiome) {
        // We don't have an exact BlockPos here (method is biome-only).
        // Use a representative Y=64 position in the world — good enough for base biome value.
        // Many mods do this when they need a "typical" temp for the biome.
        BlockPos representativePos = new BlockPos(0, 64, 0); // origin or any fixed spot works for normalization

        // Let TFC compute the instant temperature (includes your custom model + seasons)
        float tfcTemp = Climate.getInstantTemperature(level, representativePos);

        // Optional: you could log this during testing to see the values
        // FirmaCompat.LOGGER.debug("TFC instant temp used for biome base: {}", tfcTemp);

        return tfcTemp;
    }
}