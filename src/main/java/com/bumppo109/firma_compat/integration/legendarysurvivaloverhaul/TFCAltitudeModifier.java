package com.bumppo109.firma_compat.integration.legendarysurvivaloverhaul;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import sfiomn.legendarysurvivaloverhaul.api.data.json.JsonTemperatureDimension;
import sfiomn.legendarysurvivaloverhaul.api.data.manager.TemperatureDataManager;
import sfiomn.legendarysurvivaloverhaul.api.temperature.ModifierBase;
import sfiomn.legendarysurvivaloverhaul.api.temperature.TemperatureImmunityEnum;
import sfiomn.legendarysurvivaloverhaul.api.temperature.TemperatureUtil;

public class TFCAltitudeModifier extends ModifierBase {
    public float getWorldInfluence(@Nullable Player player, Level level, BlockPos pos) {
        JsonTemperatureDimension dimensionTemperature = TemperatureDataManager.getDimension(level.dimension().location());
        if (dimensionTemperature != null && dimensionTemperature.hasAltitude) {
            float elevationTemperature = Mth.clamp(((float)pos.getY() - dimensionTemperature.seaLevelHeight) * 0.16225F, 0.0F, 17.822F);
            //float altitudeDiff = (float)(pos.getY() - dimensionTemperature.seaLevelHeight) / 64.0F;
            return player != null && elevationTemperature > 0.0F && TemperatureUtil.hasImmunity(player, TemperatureImmunityEnum.HIGH_ALTITUDE) ? 0.0F : -elevationTemperature;
        } else {
            return 0.0F;
        }
    }
}
