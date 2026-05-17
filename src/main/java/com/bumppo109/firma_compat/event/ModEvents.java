package com.bumppo109.firma_compat.event;

import com.bumppo109.firma_compat.FirmaCompat;
import com.bumppo109.firma_compat.block.CompatWood;
import com.bumppo109.firma_compat.block.ModBlocks;
import com.eerussianguy.firmalife.common.blockentities.FLBlockEntities;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.util.Metal;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;

import java.util.stream.Stream;

public class ModEvents {
    // This is where we add new AFC wood blocks to existing TFC block entity types
    @SubscribeEvent
    public static void addToBlockEntities(BlockEntityTypeAddBlocksEvent event)
    {
        //Wood Good Module
        modifyBlockEntityType(
                TFCBlockEntities.TOOL_RACK.get(),
                BuiltInRegistries.BLOCK.stream()
                        .filter(block -> {
                            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
                            return id.getNamespace().equals(FirmaCompat.MODID)
                                    && id.getPath().endsWith("_tool_rack");
                        }),
                event
        );
        modifyBlockEntityType(
                TFCBlockEntities.LOOM.get(),
                BuiltInRegistries.BLOCK.stream()
                        .filter(block -> {
                            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
                            return id.getNamespace().equals(FirmaCompat.MODID)
                                    && id.getPath().endsWith("_loom");
                        }),
                event
        );
        modifyBlockEntityType(
                TFCBlockEntities.SLUICE.get(),
                BuiltInRegistries.BLOCK.stream()
                        .filter(block -> {
                            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
                            return id.getNamespace().equals(FirmaCompat.MODID)
                                    && id.getPath().endsWith("_sluice");
                        }),
                event
        );
        modifyBlockEntityType(
                TFCBlockEntities.BARREL.get(),
                BuiltInRegistries.BLOCK.stream()
                        .filter(block -> {
                            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
                                if (id == null) return false;
                                String path = id.getPath();
                                return id.getNamespace().equals(FirmaCompat.MODID)
                                        && path.endsWith("_barrel")
                                        && !path.endsWith("_stomping_barrel")
                                        && !path.equals("compat_barrel");
                        }),
                event
        );

        modifyBlockEntityType(
                TFCBlockEntities.AXLE.get(),
                BuiltInRegistries.BLOCK.stream()
                        .filter(block -> {
                            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
                            if (id == null) return false;
                            String path = id.getPath();
                            return id.getNamespace().equals(FirmaCompat.MODID)
                                    && path.endsWith("_axle")
                                    && !path.endsWith("_bladed_axle")      // exclude bladed
                                    && !path.endsWith("_encased_axle");    // exclude encased
                        }),
                event
        );

        modifyBlockEntityType(
                TFCBlockEntities.BLADED_AXLE.get(),
                BuiltInRegistries.BLOCK.stream()
                        .filter(block -> {
                            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
                            if (id == null) return false;
                            return id.getNamespace().equals(FirmaCompat.MODID)
                                    && id.getPath().endsWith("_bladed_axle");
                        }),
                event
        );

        modifyBlockEntityType(
                TFCBlockEntities.WATER_WHEEL.get(),
                BuiltInRegistries.BLOCK.stream()
                        .filter(block -> {
                            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
                            if (id == null) return false;
                            String path = id.getPath();
                            return id.getNamespace().equals(FirmaCompat.MODID)
                                    && path.endsWith("_water_wheel");
                        }),
                event
        );

        modifyBlockEntityType(
                TFCBlockEntities.WINDMILL.get(),
                BuiltInRegistries.BLOCK.stream()
                        .filter(block -> {
                            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
                            return id.getNamespace().equals(FirmaCompat.MODID)
                                    && id.getPath().endsWith("_windmill");
                        }),
                event
        );
        modifyBlockEntityType(
                TFCBlockEntities.CLUTCH.get(),
                BuiltInRegistries.BLOCK.stream()
                        .filter(block -> {
                            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
                            return id.getNamespace().equals(FirmaCompat.MODID)
                                    && id.getPath().endsWith("_clutch");
                        }),
                event
        );
        modifyBlockEntityType(
                TFCBlockEntities.GEAR_BOX.get(),
                BuiltInRegistries.BLOCK.stream()
                        .filter(block -> {
                            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
                            return id.getNamespace().equals(FirmaCompat.MODID)
                                    && id.getPath().endsWith("_gear_box");
                        }),
                event
        );
        modifyBlockEntityType(
                TFCBlockEntities.SHELF.get(),
                BuiltInRegistries.BLOCK.stream()
                        .filter(block -> {
                            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
                                if (id == null) return false;
                                String path = id.getPath();
                                return id.getNamespace().equals(FirmaCompat.MODID)
                                        && path.endsWith("_shelf")
                                        && !path.endsWith("_wine_shelf")
                                        && !path.endsWith("_food_shelf");
                        }),
                event
        );
        modifyBlockEntityType(
                TFCBlockEntities.ENCASED_AXLE.get(),
                BuiltInRegistries.BLOCK.stream()
                        .filter(block -> {
                            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
                            return id.getNamespace().equals(FirmaCompat.MODID)
                                    && id.getPath().endsWith("_encased_axle");
                        }),
                event
        );

        modifyWood(TFCBlockEntities.LOOM.get(), CompatWood.BlockType.LOOM, event);
        modifyWood(TFCBlockEntities.BARREL.get(), CompatWood.BlockType.BARREL, event);
        modifyWood(TFCBlockEntities.TOOL_RACK.get(), CompatWood.BlockType.TOOL_RACK, event);
        modifyWood(TFCBlockEntities.SLUICE.get(), CompatWood.BlockType.SLUICE, event);
        modifyWood(TFCBlockEntities.BLADED_AXLE.get(), CompatWood.BlockType.BLADED_AXLE, event);
        modifyWood(TFCBlockEntities.AXLE.get(), CompatWood.BlockType.AXLE, event);
        modifyWood(TFCBlockEntities.WATER_WHEEL.get(), CompatWood.BlockType.WATER_WHEEL, event);
        modifyWood(TFCBlockEntities.WINDMILL.get(), CompatWood.BlockType.WINDMILL, event);
        modifyWood(TFCBlockEntities.CLUTCH.get(), CompatWood.BlockType.CLUTCH, event);
        modifyWood(TFCBlockEntities.GEAR_BOX.get(), CompatWood.BlockType.GEAR_BOX, event);
        modifyWood(TFCBlockEntities.SHELF.get(), CompatWood.BlockType.SHELF, event);
        modifyWood(TFCBlockEntities.ENCASED_AXLE.get(), CompatWood.BlockType.ENCASED_AXLE, event);

        modifyBlockEntityType(TFCBlockEntities.FARMLAND.get(), Stream.of(ModBlocks.COMPAT_FARMLAND.get()), event);
        modifyBlockEntityType(TFCBlockEntities.TICK_COUNTER.get(), Stream.of(ModBlocks.DRYING_MUD_BRICK.get()), event);
        modifyBlockEntityType(TFCBlockEntities.ANVIL.get(), Stream.of(ModBlocks.PRIMITIVE_ANVIL.get()), event);

        modifyBlockEntityType(TFCBlockEntities.CHEST.get(), Stream.of(ModBlocks.COMPAT_CHEST.get()), event);
        modifyBlockEntityType(TFCBlockEntities.TRAPPED_CHEST.get(), Stream.of(ModBlocks.COMPAT_TRAPPED_CHEST.get()), event);

        modifyBlockEntityType(TFCBlockEntities.LAMP.get(), Stream.of(ModBlocks.LANTERN.get()), event);

        for(Metal metal : Metal.values()){
            if(metal.allParts()){
                modifyBlockEntityType(TFCBlockEntities.LAMP.get(), Stream.of(ModBlocks.COMPAT_LANTERNS.get(metal).get()), event);
            }
        }

        if(ModList.get().isLoaded("firmalife")){
            modifyBlockEntityType(
                    FLBlockEntities.FOOD_SHELF.get(),
                    BuiltInRegistries.BLOCK.stream()
                            .filter(block -> {
                                ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
                                return id.getNamespace().equals(FirmaCompat.MODID)
                                        && id.getPath().endsWith("_food_shelf")
                                        && !id.getPath().endsWith("_wine_shelf");
                            }),
                    event
            );
            modifyBlockEntityType(
                    FLBlockEntities.HANGER.get(),
                    BuiltInRegistries.BLOCK.stream()
                            .filter(block -> {
                                ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
                                return id.getNamespace().equals(FirmaCompat.MODID)
                                        && id.getPath().endsWith("_hanger");
                            }),
                    event
            );
            modifyBlockEntityType(
                    FLBlockEntities.JARBNET.get(),
                    BuiltInRegistries.BLOCK.stream()
                            .filter(block -> {
                                ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
                                return id.getNamespace().equals(FirmaCompat.MODID)
                                        && id.getPath().endsWith("_jarbnet");
                            }),
                    event
            );
            modifyBlockEntityType(
                    FLBlockEntities.KEG_SUB.get(),
                    BuiltInRegistries.BLOCK.stream()
                            .filter(block -> {
                                ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
                                return id.getNamespace().equals(FirmaCompat.MODID)
                                        && id.getPath().endsWith("_keg_sub");
                            }),
                    event
            );
            modifyBlockEntityType(
                    FLBlockEntities.KEG.get(),
                    BuiltInRegistries.BLOCK.stream()
                            .filter(block -> {
                                ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
                                return id.getNamespace().equals(FirmaCompat.MODID)
                                        && id.getPath().endsWith("_keg");
                            }),
                    event
            );
            modifyBlockEntityType(
                    FLBlockEntities.WINE_SHELF.get(),
                    BuiltInRegistries.BLOCK.stream()
                            .filter(block -> {
                                ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
                                return id.getNamespace().equals(FirmaCompat.MODID)
                                        && id.getPath().endsWith("_wine_shelf")
                                        && !id.getPath().endsWith("_food_shelf");
                            }),
                    event
            );
            modifyBlockEntityType(
                    FLBlockEntities.STOMPING_BARREL.get(),
                    BuiltInRegistries.BLOCK.stream()
                            .filter(block -> {
                                ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
                                return id.getNamespace().equals(FirmaCompat.MODID)
                                        && id.getPath().endsWith("_stomping_barrel");
                            }),
                    event
            );
            modifyBlockEntityType(
                    FLBlockEntities.BARREL_PRESS.get(),
                    BuiltInRegistries.BLOCK.stream()
                            .filter(block -> {
                                ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
                                return id.getNamespace().equals(FirmaCompat.MODID)
                                        && id.getPath().endsWith("_barrel_press");
                            }),
                    event
            );
        }
    }

    private static void modifyWood(BlockEntityType<?> type, CompatWood.BlockType blockType, BlockEntityTypeAddBlocksEvent event)
    {
        modifyBlockEntityType(type, ModBlocks.WOODS.values().stream().map(map -> map.get(blockType).get()), event);
    }

    private static void modifyBlockEntityType(BlockEntityType<?> type, Stream<Block> extraBlocks, BlockEntityTypeAddBlocksEvent event)
    {
        extraBlocks.forEach(
                (block -> event.modify(type, block))
        );
    }

    private static void modifyBlockEntityType(BlockEntityType<?> type, Block extraBlock, BlockEntityTypeAddBlocksEvent event)
    {
        event.modify(type, extraBlock);
    }
}
