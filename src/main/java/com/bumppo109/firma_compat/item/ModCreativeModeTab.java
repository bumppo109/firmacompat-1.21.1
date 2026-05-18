package com.bumppo109.firma_compat.item;

import com.bumppo109.firma_compat.FirmaCompat;
import com.bumppo109.firma_compat.block.*;
import com.bumppo109.firma_compat.integration.firmalife.CompatFLBlocks;
import com.bumppo109.firma_compat.integration.rnr.CompatRNR;
import com.bumppo109.firma_compat.integration.rnr.RNRCompatBlocks;
import com.bumppo109.firma_compat.integration.rnr.RNRCompatItems;
import net.dries007.tfc.util.Metal;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Map;
import java.util.function.Supplier;

@EventBusSubscriber (modid = "firma_compat") // Replace with your mod ID
public class ModCreativeModeTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FirmaCompat.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> FIRMA_COMPAT_TAB =
            CREATIVE_TABS.register("firma_compat",
                    () -> CreativeModeTab.builder()
                            .icon(() -> new ItemStack(Items.DIAMOND))
                            .title(Component.translatable("firma_compat.creative_tab.firma_compat"))
                            .build());

    @SubscribeEvent
    public static void onBuildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() != FIRMA_COMPAT_TAB.get()) {
            return;
        }

        FirmaCompat.LOGGER.debug("Populating FirmaCompat creative tab");

        // ────────────────────────────────
        //          Food
        // ────────────────────────────────
        accept(event, ModItems.SWEET_BERRIES_JAR);
        accept(event, ModItems.SWEET_BERRIES_JAR_UNSEALED);
        accept(event, ModItems.SWEET_BERRIES_JAM);
        accept(event, ModItems.GLOW_BERRIES_JAR);
        accept(event, ModItems.GLOW_BERRIES_JAR_UNSEALED);
        accept(event, ModItems.GLOW_BERRIES_JAM);

        // ────────────────────────────────
        //          Wood
        // ────────────────────────────────
        for (CompatWood wood : CompatWood.VALUES) {
            var woodBlocks = ModBlocks.WOODS.get(wood);
            if (woodBlocks != null) {
                woodBlocks.forEach((type, reg) -> {
                    if (type.needsItem()) {
                        accept(event, reg);
                    }
                });
            }

            accept(event, ModItems.LUMBER, wood);
            accept(event, ModItems.SUPPORTS, wood);
        }

        // ────────────────────────────────
        //          Rock / Bricks / Deposits
        // ────────────────────────────────
        for (CompatRock rock : CompatRock.VALUES) {
            var rockBlocks = ModBlocks.ROCK_BLOCKS.get(rock);
            if (rockBlocks != null) {
                rockBlocks.forEach((blockType, blockId) -> {
                    // Your original exclusion logic
                    if (blockId != ModBlocks.ROCK_BLOCKS.get(CompatRock.STONE).get(CompatRock.BlockType.HARDENED_COBBLE) &&
                            blockId != ModBlocks.ROCK_BLOCKS.get(CompatRock.DEEPSLATE).get(CompatRock.BlockType.HARDENED_COBBLE)) {
                        accept(event, blockId);
                    }
                });
            }
        }

        for (CompatBricks brick : CompatBricks.VALUES) {
            accept(event, ModBlocks.AQUEDUCTS.get(brick));
        }

        accept(event, ModItems.STONE_BRICK);
        accept(event, ModItems.DEEPSLATE_TILE);
        accept(event, ModItems.DEEPSLATE_BRICK);
        accept(event, ModItems.POLISHED_BLACKSTONE_BRICK);
        accept(event, ModItems.END_STONE_BRICK);
        accept(event, ModItems.TUFF_BRICK);
        accept(event, ModItems.QUARTZ_BRICK);
        accept(event, ModItems.PRISMARINE_BRICK);
        accept(event, ModItems.UNFIRED_POT);
        accept(event, ModItems.MUD_BRICK);

        accept(event, ModItems.ANDESITE_BRICK);
        accept(event, ModItems.DIORITE_BRICK);
        accept(event, ModItems.GRANITE_BRICK);
        accept(event, ModItems.CALCITE_BRICK);
        accept(event, ModItems.DRIPSTONE_BRICK);
        accept(event, ModItems.BASALT_BRICK);

        accept(event, ModBlocks.CASSITERITE_GRAVEL_DEPOSIT);
        accept(event, ModBlocks.NATIVE_COPPER_GRAVEL_DEPOSIT);
        accept(event, ModBlocks.NATIVE_GOLD_GRAVEL_DEPOSIT);
        accept(event, ModBlocks.NATIVE_SILVER_GRAVEL_DEPOSIT);

        // ────────────────────────────────
        //          Natural
        // ────────────────────────────────
        accept(event, ModBlocks.CLAY_DIRT);
        accept(event, ModBlocks.CLAY_PODZOL);
        accept(event, ModBlocks.CLAY_GRASS_BLOCK);
        accept(event, ModBlocks.KAOLIN_CLAY_DIRT);
        accept(event, ModBlocks.KAOLIN_CLAY_PODZOL);
        accept(event, ModBlocks.KAOLIN_CLAY_GRASS_BLOCK);
        accept(event, ModBlocks.DRYING_MUD_BRICK);
        accept(event, ModBlocks.PRIMITIVE_ANVIL);
        accept(event, ModBlocks.COMPAT_FARMLAND);

        // ────────────────────────────────
        //          Metal Items & Buckets
        // ────────────────────────────────
        for (CompatMetal metal : CompatMetal.values()) {
            var metalItems = ModItems.METAL_ITEMS.get(metal);
            if (metalItems != null) {
                metalItems.forEach((type, reg) -> accept(event, reg));
            } else {
                FirmaCompat.LOGGER.warn("No metal items registered for metal: {}", metal);
            }

            var bucket = ModItems.METAL_FLUID_BUCKETS.get(metal);
            if (bucket != null) {
                accept(event, bucket);
            }
        }
        accept(event, ModBlocks.LANTERN);
        for (Metal metal : Metal.values()) {
            if(metal.allParts()){
                accept(event, ModBlocks.COMPAT_LANTERNS.get(metal));
            }
        }

        // ────────────────────────────────
        //          Firmalife Integration
        // ────────────────────────────────
        if (ModList.get().isLoaded("firmalife")) {
            for (CompatWood wood : CompatWood.VALUES) {
                accept(event, CompatFLBlocks.FOOD_SHELVES, wood);
                accept(event, CompatFLBlocks.HANGERS, wood);
                accept(event, CompatFLBlocks.JARBNETS, wood);
                accept(event, CompatFLBlocks.WINE_SHELVES, wood);
                accept(event, CompatFLBlocks.KEGS, wood);
                accept(event, CompatFLBlocks.STOMPING_BARRELS, wood);
                accept(event, CompatFLBlocks.BARREL_PRESSES, wood);
            }
        }

        // ────────────────────────────────
        //          Roads & Roofs (rnr) Integration
        // ────────────────────────────────
        if (ModList.get().isLoaded("rnr")) {
            accept(event, RNRCompatBlocks.TAMPED_DIRT);
            accept(event, RNRCompatBlocks.TAMPED_MUD);
            accept(event, RNRCompatBlocks.OVER_HEIGHT_GRAVEL);
            accept(event, RNRCompatItems.GRAVEL_FILL);
            accept(event, RNRCompatBlocks.GRAVEL_ROAD);
            accept(event, RNRCompatBlocks.GRAVEL_ROAD_STAIRS);
            accept(event, RNRCompatBlocks.GRAVEL_ROAD_SLAB);
            accept(event, RNRCompatBlocks.MACADAM_ROAD);
            accept(event, RNRCompatBlocks.MACADAM_ROAD_STAIRS);
            accept(event, RNRCompatBlocks.MACADAM_ROAD_SLAB);

            for (CompatRock rock : CompatRock.VALUES) {
                accept(event, RNRCompatItems.FLAGSTONE.get(rock));
                accept(event, RNRCompatBlocks.ROCK_BLOCKS.get(rock).get(CompatRNR.FLAGSTONE));
                accept(event, RNRCompatBlocks.ROCK_STAIRS.get(rock).get(CompatRNR.FLAGSTONE));
                accept(event, RNRCompatBlocks.ROCK_SLABS.get(rock).get(CompatRNR.FLAGSTONE));

                accept(event, RNRCompatBlocks.ROCK_BLOCKS.get(rock).get(CompatRNR.COBBLED_ROAD));
                accept(event, RNRCompatBlocks.ROCK_STAIRS.get(rock).get(CompatRNR.COBBLED_ROAD));
                accept(event, RNRCompatBlocks.ROCK_SLABS.get(rock).get(CompatRNR.COBBLED_ROAD));

                if (rock == CompatRock.NETHERRACK) continue;

                accept(event, RNRCompatBlocks.ROCK_BLOCKS.get(rock).get(CompatRNR.SETT_ROAD));
                accept(event, RNRCompatBlocks.ROCK_STAIRS.get(rock).get(CompatRNR.SETT_ROAD));
                accept(event, RNRCompatBlocks.ROCK_SLABS.get(rock).get(CompatRNR.SETT_ROAD));
            }

            for (CompatWood wood : CompatWood.VALUES) {
                accept(event, RNRCompatItems.SHINGLE.get(wood));
                accept(event, RNRCompatBlocks.WOOD_SHINGLE_ROOFS.get(wood));
                accept(event, RNRCompatBlocks.WOOD_SHINGLE_ROOF_STAIRS.get(wood));
                accept(event, RNRCompatBlocks.WOOD_SHINGLE_ROOF_SLABS.get(wood));
            }
        }
    }

    // ────────────────────────────────────────────────
    //               Helper Methods
    // ────────────────────────────────────────────────

    private static void accept(BuildCreativeModeTabContentsEvent output, Supplier<? extends ItemLike> supplier) {
        if (supplier == null || supplier.get() == null || supplier.get().asItem() == Items.AIR) {
            FirmaCompat.LOGGER.warn("Skipping invalid or AIR item in FirmaCompat creative tab");
            return;
        }
        output.accept(supplier.get());
    }

    private static <K> void accept(BuildCreativeModeTabContentsEvent output,
                                   Map<K, ? extends Supplier<? extends ItemLike>> map,
                                   K key) {
        var supplier = map.get(key);
        if (supplier != null) {
            accept(output, supplier);
        } else {
            FirmaCompat.LOGGER.warn("No supplier found for key: {}", key);
        }
    }

    // If you have additional nested map helpers, add similar overloads here
}