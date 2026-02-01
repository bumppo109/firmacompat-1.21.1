package com.bumppo109.firma_compat.item;

import com.bumppo109.firma_compat.FirmaCompat;
import com.bumppo109.firma_compat.block.*;
import com.bumppo109.firma_compat.integration.firmalife.CompatFLBlocks;
import com.bumppo109.firma_compat.integration.rnr.CompatRNR;
import com.bumppo109.firma_compat.integration.rnr.RNRCompatBlocks;
import com.bumppo109.firma_compat.integration.rnr.RNRCompatItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Map;
import java.util.function.Supplier;

public class ModCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FirmaCompat.MODID);

    public static final Id FIRMA_COMPAT_TAB = register("firma_compat",
            () -> new ItemStack(Items.DIAMOND), ModCreativeModeTab::fillTab);

    private static void fillTab(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out)
    {
        //Food
        ModItems.SWEET_BERRIES_JAR.get();
        ModItems.SWEET_BERRIES_JAR_UNSEALED.get();
        ModItems.SWEET_BERRIES_JAM.get();
        ModItems.GLOW_BERRIES_JAR.get();
        ModItems.GLOW_BERRIES_JAR_UNSEALED.get();
        ModItems.GLOW_BERRIES_JAM.get();
        //Wood
        for (CompatWood wood : CompatWood.VALUES)
        {
            ModBlocks.WOODS.get(wood).forEach((type, reg) -> {
                if (type.needsItem())
                {
                    accept(out, reg);
                }
            });
            accept(out, ModItems.LUMBER, wood);
            accept(out, ModItems.SUPPORTS, wood);
        }

        //TODO - Rock
        for (CompatRock rock : CompatRock.VALUES){
            ModBlocks.ROCK_BLOCKS.get(rock).forEach((blockType, blockId) -> {
                if(blockId != ModBlocks.ROCK_BLOCKS.get(CompatRock.STONE).get(CompatRock.BlockType.HARDENED_COBBLE) && blockId != ModBlocks.ROCK_BLOCKS.get(CompatRock.DEEPSLATE).get(CompatRock.BlockType.HARDENED_COBBLE)){
                    accept(out, blockId);
                }

            });
        }
        for (CompatBricks brick : CompatBricks.VALUES){
            accept(out, ModBlocks.AQUEDUCTS.get(brick));
        }
        accept(out, ModItems.STONE_BRICK);
        accept(out, ModItems.DEEPSLATE_TILE);
        accept(out, ModItems.DEEPSLATE_BRICK);
        accept(out, ModItems.POLISHED_BLACKSTONE_BRICK);
        accept(out, ModItems.END_STONE_BRICK);
        accept(out, ModItems.TUFF_BRICK);
        accept(out, ModItems.QUARTZ_BRICK);
        accept(out, ModItems.PRISMARINE_BRICK);
        accept(out, ModItems.UNFIRED_POT);
        accept(out, ModItems.MUD_BRICK);

        accept(out, ModItems.ANDESITE_BRICK);
        accept(out, ModItems.DIORITE_BRICK);
        accept(out, ModItems.GRANITE_BRICK);
        accept(out, ModItems.CALCITE_BRICK);
        accept(out, ModItems.DRIPSTONE_BRICK);
        accept(out, ModItems.BASALT_BRICK);

        accept(out, ModBlocks.CASSITERITE_GRAVEL_DEPOSIT);
        accept(out, ModBlocks.NATIVE_COPPER_GRAVEL_DEPOSIT);
        accept(out, ModBlocks.NATIVE_GOLD_GRAVEL_DEPOSIT);
        accept(out, ModBlocks.NATIVE_SILVER_GRAVEL_DEPOSIT);

        //TODO - Natural
        accept(out, ModBlocks.CLAY_DIRT);
        accept(out, ModBlocks.CLAY_PODZOL);
        accept(out, ModBlocks.CLAY_GRASS_BLOCK);
        accept(out, ModBlocks.KAOLIN_CLAY_DIRT);
        accept(out, ModBlocks.KAOLIN_CLAY_PODZOL);
        accept(out, ModBlocks.KAOLIN_CLAY_GRASS_BLOCK);
        accept(out, ModBlocks.DRYING_MUD_BRICK);
        accept(out, ModBlocks.PRIMITIVE_ANVIL);
        accept(out, ModBlocks.COMPAT_FARMLAND);
        //TODO - Metal
        for (CompatMetal metal : CompatMetal.values()) {
            // Metal items
            var metalItemMap = ModItems.METAL_ITEMS.get(metal);
            if (metalItemMap != null) {
                metalItemMap.forEach((type, reg) -> accept(out, reg));
            } else {
                FirmaCompat.LOGGER.warn("No metal items registered for {}", metal);
            }

            // Fluid buckets
            var bucket = ModItems.METAL_FLUID_BUCKETS.get(metal);
            if (bucket != null) {
                accept(out, bucket);
            }
        }

        if(ModList.get().isLoaded("firmalife")){
            for(CompatWood wood : CompatWood.VALUES){
                accept(out, CompatFLBlocks.FOOD_SHELVES, wood);
                accept(out, CompatFLBlocks.HANGERS, wood);
                accept(out, CompatFLBlocks.JARBNETS, wood);
                accept(out, CompatFLBlocks.WINE_SHELVES, wood);
                accept(out, CompatFLBlocks.KEGS, wood);
                accept(out, CompatFLBlocks.STOMPING_BARRELS, wood);
                accept(out, CompatFLBlocks.BARREL_PRESSES, wood);
            }
        }

        if(ModList.get().isLoaded("rnr")){
            accept(out, RNRCompatBlocks.TAMPED_DIRT);
            accept(out, RNRCompatBlocks.TAMPED_MUD);
            accept(out, RNRCompatBlocks.OVER_HEIGHT_GRAVEL);
            accept(out, RNRCompatItems.GRAVEL_FILL);
            accept(out, RNRCompatBlocks.GRAVEL_ROAD);
            accept(out, RNRCompatBlocks.GRAVEL_ROAD_STAIRS);
            accept(out, RNRCompatBlocks.GRAVEL_ROAD_SLAB);
            accept(out, RNRCompatBlocks.MACADAM_ROAD);
            accept(out, RNRCompatBlocks.MACADAM_ROAD_STAIRS);
            accept(out, RNRCompatBlocks.MACADAM_ROAD_SLAB);

            for(CompatRock rock : CompatRock.VALUES){
                accept(out, RNRCompatItems.FLAGSTONE.get(rock));
                accept(out, RNRCompatBlocks.ROCK_BLOCKS.get(rock).get(CompatRNR.FLAGSTONE));
                accept(out, RNRCompatBlocks.ROCK_STAIRS.get(rock).get(CompatRNR.FLAGSTONE));
                accept(out, RNRCompatBlocks.ROCK_SLABS.get(rock).get(CompatRNR.FLAGSTONE));
                accept(out, RNRCompatBlocks.ROCK_BLOCKS.get(rock).get(CompatRNR.COBBLED_ROAD));
                accept(out, RNRCompatBlocks.ROCK_STAIRS.get(rock).get(CompatRNR.COBBLED_ROAD));
                accept(out, RNRCompatBlocks.ROCK_SLABS.get(rock).get(CompatRNR.COBBLED_ROAD));

                if(rock.equals(CompatRock.NETHERRACK)) continue;
                accept(out, RNRCompatBlocks.ROCK_BLOCKS.get(rock).get(CompatRNR.SETT_ROAD));
                accept(out, RNRCompatBlocks.ROCK_STAIRS.get(rock).get(CompatRNR.SETT_ROAD));
                accept(out, RNRCompatBlocks.ROCK_SLABS.get(rock).get(CompatRNR.SETT_ROAD));
            }
            for(CompatWood wood : CompatWood.VALUES){
                accept(out, RNRCompatItems.SHINGLE.get(wood));
                accept(out, RNRCompatBlocks.WOOD_SHINGLE_ROOFS.get(wood));
                accept(out, RNRCompatBlocks.WOOD_SHINGLE_ROOF_STAIRS.get(wood));
                accept(out, RNRCompatBlocks.WOOD_SHINGLE_ROOF_SLABS.get(wood));
            }
        }
    }

    //Helpers from TFC
    private static Id register(String name, Supplier<ItemStack> icon, CreativeModeTab.DisplayItemsGenerator displayItems)
    {
        final var holder = CREATIVE_TABS.register(name, () -> CreativeModeTab.builder()
                .icon(icon)
                .title(Component.translatable("firma_compat.creative_tab." + name))
                .displayItems(displayItems)
                .build());
        return new Id(holder, displayItems);
    }

    private static <T extends ItemLike, R extends Supplier<T>, K1, K2> void accept(CreativeModeTab.Output out, Map<K1, Map<K2, R>> map, K1 key1, K2 key2)
    {
        if (map.containsKey(key1) && map.get(key1).containsKey(key2))
        {
            out.accept(map.get(key1).get(key2).get());
        }
    }

    private static <T extends ItemLike, R extends Supplier<T>, K> void accept(CreativeModeTab.Output out, Map<K, R> map, K key)
    {
        if (map.containsKey(key))
        {
            out.accept(map.get(key).get());
        }
    }

    private static <T extends ItemLike, R extends Supplier<T>> void accept(CreativeModeTab.Output out, R reg)
    {
        if (reg.get().asItem() == Items.AIR)
        {
            FirmaCompat.LOGGER.error("BlockItem with no Item added to creative tab: " + reg);
            return;
        }
        out.accept(reg.get());
    }

    public static record Id(DeferredHolder<CreativeModeTab, CreativeModeTab> tab, CreativeModeTab.DisplayItemsGenerator generator) {
        public Id(DeferredHolder<CreativeModeTab, CreativeModeTab> tab, CreativeModeTab.DisplayItemsGenerator generator) {
            this.tab = tab;
            this.generator = generator;
        }

        public DeferredHolder<CreativeModeTab, CreativeModeTab> tab() {
            return this.tab;
        }

        public CreativeModeTab.DisplayItemsGenerator generator() {
            return this.generator;
        }
    }
}
