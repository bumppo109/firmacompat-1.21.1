package com.bumppo109.firma_compat.util.climate;

import com.bumppo109.firma_compat.FirmaCompat;
import com.bumppo109.firma_compat.integration.sereneseasons.SereneClimateModel;
import net.dries007.tfc.util.climate.BiomeBasedClimateModel;
import net.dries007.tfc.util.events.SelectClimateModelEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

@Mod(value = FirmaCompat.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = FirmaCompat.MODID, value = Dist.CLIENT)
public class ClimateEventHandler {

    /**
     * Overrides the default biome-based climate model with our Serene Seasons compatible one
     * only in non-TFC worlds (when the default model is still active) and only if Serene Seasons is loaded.
     */
    @SubscribeEvent
    public static void onSelectClimateModel(SelectClimateModelEvent event) {
        boolean serene = ModList.get().isLoaded("sereneseasons");

        if(event.getModel() == BiomeBasedClimateModel.INSTANCE){
            if(serene){
                event.setModel(SereneClimateModel.INSTANCE);
                FirmaCompat.LOGGER.debug("Applied SereneClimateModel for world: {}", event.level().dimension().location());
            } else {
                event.setModel(ModBiomeBasedClimateModel.INSTANCE);
                FirmaCompat.LOGGER.debug("Applied ModBiomeBasedClimateModel for world: {}", event.level().dimension().location());
            }
        }
    }
}
