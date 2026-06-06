package com.bumppo109.firma_compat.mixin.legendarysurvivaloverhaul;

import com.bumppo109.firma_compat.FirmaCompat;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sfiomn.legendarysurvivaloverhaul.LegendarySurvivalOverhaul;

@Mixin(LegendarySurvivalOverhaul.class)
public abstract class LegendarySurvivalOverhaulMixin {

    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    private void disableLSOIntegrations(IEventBus modBus, ModContainer modContainer, CallbackInfo ci) {
        // 1. Disable LSO's built-in TFC integration (already working for you)
        //LegendarySurvivalOverhaul.terraFirmaCraftLoaded = false;

        // 2. Disable LSO's Serene Seasons integration so it doesn't fight with TFC's custom climate model
        LegendarySurvivalOverhaul.sereneSeasonsLoaded = false;
        // Optional: also disable Ecliptic Seasons if you don't want any season interference
        // LegendarySurvivalOverhaul.eclipticSeasonsLoaded = false;

        FirmaCompat.LOGGER.info("Disabled LSO TFC and Serene Seasons integrations - using custom TFC climate model");
    }
}