package com.bumppo109.firma_compat.integration.ecliptic;

import com.bumppo109.firma_compat.util.climate.ModClimateModels;
import com.bumppo109.firma_compat.util.climate.VanillaClimateHelper;
import io.netty.buffer.ByteBuf;
import net.dries007.tfc.util.climate.ClimateModel;
import net.dries007.tfc.util.climate.ClimateModelType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import sfiomn.legendarysurvivaloverhaul.common.integration.eclipticseasons.EclipticSeasonsModifier;

public class EclipticSeasonsLSOClimateModel implements ClimateModel {

    public static final EclipticSeasonsLSOClimateModel INSTANCE = new EclipticSeasonsLSOClimateModel();
    public static final StreamCodec<ByteBuf, EclipticSeasonsLSOClimateModel> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    @Override
    public ClimateModelType type() {
        return ModClimateModels.ECLIPTIC_LSO_MODEL.get();
    }

    @Override
    public float getAverageTemperature(LevelReader levelReader, BlockPos pos) {
        return VanillaClimateHelper.getTemperature(levelReader, pos) + getLSOSeasonInfluence((Level) levelReader, pos);
    }

    @Override
    public float getAverageRainfall(LevelReader levelReader, BlockPos pos) {
        return EclipticClimateHelper.getRainfall(levelReader, pos);
    }

    public static float getLSOSeasonInfluence(Level level, BlockPos pos) {
        return new EclipticSeasonsModifier().getUncaughtWorldInfluence(level, pos);
    }
}