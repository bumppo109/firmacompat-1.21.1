package com.bumppo109.firma_compat.integration.legendarysurvivaloverhaul;

import com.bumppo109.firma_compat.FirmaCompat;
import net.dries007.tfc.util.climate.Climate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;
import sfiomn.legendarysurvivaloverhaul.common.temperature.BiomeModifier;
import sfiomn.legendarysurvivaloverhaul.config.Config;

public class TFCClimateModifier extends BiomeModifier {

    public float getWorldInfluence(Player player, Level level, BlockPos pos) {
        try {
            return this.getUncaughtWorldInfluence(level, pos);
        } catch (Exception e) {
            FirmaCompat.LOGGER.error("An error has occurred with assigning LSO climate model to player temperature, disabling integration", e);
            LegendarySurvivalOverhaul.terraFirmaCraftLoaded = false;
            return 0.0F;
        }
    }

    public float getUncaughtWorldInfluence(Level level, BlockPos pos) {
        Vec3i[] posOffsets = new Vec3i[]{new Vec3i(0, 0, 0), new Vec3i(10, 0, 0), new Vec3i(-10, 0, 0), new Vec3i(0, 0, 10), new Vec3i(0, 0, -10)};
        float value = 0.0F;

        for(Vec3i offset : posOffsets) {
            value += (float)((double)Climate.getInstantTemperature(level, pos.offset(offset))* Config.Baked.tfcTemperatureMultiplier);
        }

        value /= (float)posOffsets.length;
        return value;
    }
}
