package com.bumppo109.firma_compat;

import com.bumppo109.firma_compat.block.ModBlocks;
import com.bumppo109.firma_compat.data.ModDataMaps;
import com.bumppo109.firma_compat.dynamic.EveryCompatHandler;
import com.bumppo109.firma_compat.entity.CompatFaunas;
import com.bumppo109.firma_compat.entity.CompatTFCEntities;
import com.bumppo109.firma_compat.event.ModEvents;
import com.bumppo109.firma_compat.fluid.ModFluids;
import com.bumppo109.firma_compat.integration.legendarysurvivaloverhaul.LSOHandler;
import com.bumppo109.firma_compat.item.ModCreativeModeTab;
import com.bumppo109.firma_compat.item.ModItemCapabilities;
import com.bumppo109.firma_compat.item.ModItems;
import com.bumppo109.firma_compat.loot.ModLootFunctions;
import com.bumppo109.firma_compat.loot_modifiers.ModLootModifiers;
import com.bumppo109.firma_compat.integration.firmalife.CompatFLBlocks;
import com.bumppo109.firma_compat.integration.firmalife.CompatFLItems;
import com.bumppo109.firma_compat.integration.rnr.RNRCompatBlocks;
import com.bumppo109.firma_compat.integration.rnr.RNRCompatItems;
import com.bumppo109.firma_compat.data.ModDataComponents;
import com.bumppo109.firma_compat.util.climate.ModClimateModels;
import com.bumppo109.firma_compat.worldgen.ModFeatures;
import com.bumppo109.firma_compat.worldgen.placement.ModPlacement;
import com.bumppo109.firma_compat.worldgen.processor.ModStructureProcessors;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(FirmaCompat.MODID)
public class FirmaCompat {
    public static final String MODID = "firma_compat";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static boolean isTreePhysicsLoaded = false;
    public static boolean isEclipticLoaded = false;
    public static boolean isSereneLoaded = false;
    public static boolean isLSOLoaded = false;
    public static boolean isFirmalifeLoaded = false;
    public static boolean isRnRLoaded = false;
    public static boolean isDynamicLightLoaded = false;

    public FirmaCompat(IEventBus modEventBus, ModContainer modContainer) {

        modEventBus.addListener(ModEvents::addToBlockEntities);

        modEventBus.addListener(CompatTFCEntities::onEntityAttributeCreation);
        modEventBus.addListener(CompatFaunas::registerSpawnPlacements);

        ModDataComponents.COMPONENTS.register(modEventBus);
        ModLootFunctions.FUNCTIONS.register(modEventBus);
        ModFluids.FLUID_TYPES.register(modEventBus);
        ModFluids.FLUID.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModCreativeModeTab.CREATIVE_TABS.register(modEventBus);
        ModLootModifiers.register(modEventBus);
        ModFeatures.FEATURES.register(modEventBus);
        ModPlacement.PLACEMENT_MODIFIERS.register(modEventBus);
        ModStructureProcessors.register(modEventBus);
        ModClimateModels.TYPES.register(modEventBus);

        CompatTFCEntities.ENTITIES.register(modEventBus);
        modEventBus.addListener(FirmaCompatClient::registerEntityRenderers);

        modEventBus.addListener(ModDataMaps::register);

        modEventBus.addListener(this::commonSetup);

        if(ModList.get().isLoaded("everycomp") || ModList.get().isLoaded("stonezone")){
            EveryCompatHandler.registerModules();
        }
        if(ModList.get().isLoaded("firmalife")){
            CompatFLBlocks.BLOCKS.register(modEventBus);
            CompatFLItems.ITEMS.register(modEventBus);
        }
        if(ModList.get().isLoaded("rnr")){
            RNRCompatBlocks.BLOCKS.register(modEventBus);
            RNRCompatItems.ITEMS.register(modEventBus);
        }
        if(ModList.get().isLoaded("legendarysurvivaloverhaul")){
            LSOHandler.registerModifiers();
        }

        //FirmaCompatDynamicPack.init();

        NeoForge.EVENT_BUS.register(this);

        this.modIntegration();
        modEventBus.addListener(this::addCreative);

        FirmaCompatConfig.register(modContainer);

        CompatFaunas.init();

        if (FMLEnvironment.dist == Dist.CLIENT)
        {
            modEventBus.addListener(ModItemCapabilities::register);
            modEventBus.addListener(FirmaCompatClient::registerExtensions);
        }
    }

    private void modIntegration() {
        isTreePhysicsLoaded = ModList.get().isLoaded("treephysics");
        isEclipticLoaded = ModList.get().isLoaded("eclipticseasons");
        isSereneLoaded = ModList.get().isLoaded("sereneseasons");
        isLSOLoaded = ModList.get().isLoaded("legendarysurvivaloverhaul");
        isFirmalifeLoaded = ModList.get().isLoaded("firmalife");
        isRnRLoaded = ModList.get().isLoaded("rnr");
        isDynamicLightLoaded = ModList.get().isLoaded("sodiumdynamiclights");
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
