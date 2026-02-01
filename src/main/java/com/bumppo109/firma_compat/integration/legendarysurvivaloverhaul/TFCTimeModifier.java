package com.bumppo109.firma_compat.integration.legendarysurvivaloverhaul;

import com.bumppo109.firma_compat.FirmaCompat;
import net.dries007.tfc.util.climate.Climate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;
import sfiomn.legendarysurvivaloverhaul.common.integration.eclipticseasons.EclipticSeasonsUtil;
import sfiomn.legendarysurvivaloverhaul.common.temperature.BiomeModifier;
import sfiomn.legendarysurvivaloverhaul.config.Config;

public class TFCTimeModifier extends BiomeModifier {

    public float getWorldInfluence(Player player, Level level, BlockPos pos) {
        long time = level.getLevelData().getDayTime();
        double timeAngle = (double)time * 2.6179938E-4;
        if (EclipticSeasonsUtil.hasDimensionSeason(level)) {
            long sunRiseTime = EclipticSeasonsUtil.getSunRiseTime(level);
            int dayDuration = EclipticSeasonsUtil.getDayDuration(level);
            long sunSetTime = sunRiseTime + (long)dayDuration;
            if (time > sunRiseTime && time < sunSetTime % 24000L) {
                timeAngle = Math.PI / (double)dayDuration * (double)((time - sunRiseTime) % 24000L);
            } else {
                timeAngle = -(Math.PI / (double)(24000 - dayDuration)) * (double)((time - sunSetTime) % 24000L);
            }
        }

        float timeTemperature = (float)Math.sin(timeAngle) * (float) Config.Baked.timeModifier;
        float biomeMultiplier = 1.0F + Math.abs(this.normalizeToPositiveNegative(this.getNormalizedTempForBiome(level, (Biome)level.getBiome(pos).value()))) * ((float) Config.Baked.biomeTimeMultiplier - 1.0F);
        timeTemperature *= biomeMultiplier;
        return this.applyUndergroundEffect(timeTemperature, level, pos, 0.0F);
    }
}
