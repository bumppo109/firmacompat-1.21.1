package com.bumppo109.firma_compat;

import com.bumppo109.firma_compat.block.ModBlocks;
import com.bumppo109.firma_compat.data.ModDataMaps;
import com.bumppo109.firma_compat.dynamic.everycompat.*;
import com.bumppo109.firma_compat.entity.CompatFaunas;
import com.bumppo109.firma_compat.entity.CompatTFCEntities;
import com.bumppo109.firma_compat.event.ModEvents;
import com.bumppo109.firma_compat.fluid.ModFluids;
import com.bumppo109.firma_compat.item.ModCreativeModeTab;
import com.bumppo109.firma_compat.item.ModItemCapabilities;
import com.bumppo109.firma_compat.item.ModItems;
import com.bumppo109.firma_compat.loot_modifiers.ModLootModifiers;
import com.bumppo109.firma_compat.tfcaddon.firmalife.CompatFLBlocks;
import com.bumppo109.firma_compat.tfcaddon.firmalife.CompatFLItems;
import com.bumppo109.firma_compat.tfcaddon.rnr.RNRCompatBlocks;
import com.bumppo109.firma_compat.tfcaddon.rnr.RNRCompatItems;
import com.bumppo109.firma_compat.util.RecipeRemover;
import com.bumppo109.firma_compat.util.climate.ModClimateModels;
import com.bumppo109.firma_compat.util.climate.SereneClimateModel;
import com.bumppo109.firma_compat.worldgen.ModFeatures;
import com.bumppo109.firma_compat.worldgen.placement.ModPlacement;
import net.mehvahdjukaar.every_compat.api.EveryCompatAPI;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(FirmaCompat.MODID)
public class FirmaCompat {
    public static final String MODID = "firma_compat";
    public static final Logger LOGGER = LogUtils.getLogger();

    public FirmaCompat(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        modEventBus.addListener(ModEvents::addToBlockEntities);

        modEventBus.addListener(CompatTFCEntities::onEntityAttributeCreation);
        modEventBus.addListener(CompatFaunas::registerSpawnPlacements);

        ModFluids.FLUID_TYPES.register(modEventBus);
        ModFluids.FLUID.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModCreativeModeTab.CREATIVE_TABS.register(modEventBus);
        ModLootModifiers.register(modEventBus);
        ModFeatures.FEATURES.register(modEventBus);
        ModPlacement.PLACEMENT_MODIFIERS.register(modEventBus);
        ModClimateModels.TYPES.register(modEventBus);

        CompatTFCEntities.ENTITIES.register(modEventBus);
        modEventBus.addListener(FirmaCompatClient::registerEntityRenderers);

        modEventBus.addListener(ModDataMaps::register);
        if (ModList.get().isLoaded("everycomp") || ModList.get().isLoaded("stonezone")) {
            try {
                Class.forName("com.bumppo109.firma_compat.dynamic.EveryCompatHandler")
                        .getMethod("registerModules")
                        .invoke(null);
            } catch (Exception e) {
                LOGGER.error("Failed to invoke EveryCompat integration", e);
            }
        }
        if(ModList.get().isLoaded("firmalife")){
            CompatFLBlocks.BLOCKS.register(modEventBus);
            CompatFLItems.ITEMS.register(modEventBus);
        }
        if(ModList.get().isLoaded("rnr")){
            RNRCompatBlocks.BLOCKS.register(modEventBus);
            RNRCompatItems.ITEMS.register(modEventBus);
        }

        RecipeRemover.init();

        //FirmaCompatDynamicPack.init();

        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, FirmaCompatConfig.SPEC);

        CompatFaunas.init();

        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            modEventBus.addListener(ModItemCapabilities::register);
            modEventBus.addListener(FirmaCompatClient::registerExtensions);
        }
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

}
