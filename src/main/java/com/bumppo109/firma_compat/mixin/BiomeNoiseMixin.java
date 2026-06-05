package com.bumppo109.firma_compat.mixin;

import com.bumppo109.firma_compat.FirmaCompatConfig;
import net.dries007.tfc.world.noise.Noise2D;
import net.dries007.tfc.world.biome.BiomeNoise;
import net.dries007.tfc.world.noise.OpenSimplex2D;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BiomeNoise.class, remap = false)
public class BiomeNoiseMixin {

    private static final double SEA_LEVEL = 63.0;

    @Inject(
            method = "ocean(JII)Lnet/dries007/tfc/world/noise/Noise2D;",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void tfc$oceanDepthScale(
            long seed,
            int depthMin,
            int depthMax,
            CallbackInfoReturnable<Noise2D> cir) {

        final double scale = FirmaCompatConfig.COMMON.oceanDepthScale.get();

        OpenSimplex2D warp = new OpenSimplex2D(seed)
                .octaves(2)
                .spread(0.015)
                .scaled(-30.0, 30.0);

        Noise2D base = new OpenSimplex2D(seed + 1)
                .octaves(4)
                .spread(0.11)
                .scaled(63 + depthMin, 63 + depthMax)
                .warped(warp);

        cir.setReturnValue((x, z) -> {
            double h = base.noise(x, z);
            return SEA_LEVEL + (h - SEA_LEVEL) * scale;
        });
    }

    @Inject(
            method = "oceanRidge(JII)Lnet/dries007/tfc/world/noise/Noise2D;",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void tfc$oceanRidgeDepthScale(
            long seed,
            int depthMin,
            int depthMax,
            CallbackInfoReturnable<Noise2D> cir) {

        final double scale = FirmaCompatConfig.COMMON.oceanDepthScale.get();

        OpenSimplex2D warp = new OpenSimplex2D(seed)
                .octaves(2)
                .spread(0.015)
                .scaled(-30.0, 30.0);

        Noise2D ridgeNoise = new OpenSimplex2D(seed + 1)
                .octaves(4)
                .spread(0.015)
                .ridged()
                .map(x -> {
                    if (x > -0.3) {
                        x = (x + 0.3) / 1.3;
                        x = x * x * x;
                        return -16.0 * x;
                    }
                    return 0.0;
                });

        Noise2D base = new OpenSimplex2D(seed + 2)
                .octaves(4)
                .spread(0.11)
                .scaled(63 + depthMin, 63 + depthMax)
                .add(ridgeNoise)
                .warped(warp);

        cir.setReturnValue((x, z) -> {
            double h = base.noise(x, z);
            return SEA_LEVEL + (h - SEA_LEVEL) * scale;
        });
    }
}