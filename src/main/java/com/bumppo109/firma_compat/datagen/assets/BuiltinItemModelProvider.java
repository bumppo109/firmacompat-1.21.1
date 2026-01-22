package com.bumppo109.firma_compat.datagen.assets;

import com.bumppo109.firma_compat.FirmaCompat;
import com.bumppo109.firma_compat.block.*;
import com.bumppo109.firma_compat.item.ModItems;
import com.bumppo109.firma_compat.tfcaddon.firmalife.CompatFLBlocks;
import com.bumppo109.firma_compat.tfcaddon.rnr.CompatRNR;
import com.bumppo109.firma_compat.tfcaddon.rnr.RNRCompatBlocks;
import com.bumppo109.firma_compat.tfcaddon.rnr.RNRCompatItems;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static com.bumppo109.firma_compat.block.ModBlocks.GRADED_ORES;
import static com.bumppo109.firma_compat.block.ModBlocks.ORES;

public class BuiltinItemModelProvider extends ItemModelProvider {
    public BuiltinItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, FirmaCompat.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (CompatWood wood : CompatWood.VALUES) {
            var woodMap = ModBlocks.WOODS.get(wood);
            var woodSupportMap = ModItems.SUPPORTS.get(wood);
            var woodLumberMap = ModItems.LUMBER.get(wood);
            String woodName = wood.getSerializedName();

            ResourceLocation planksTexture = mcLoc("block/" + woodName + "_planks");
            ResourceLocation axleCasingFrontTexture = modLoc("block/template/axle_casing_front");

            Block twigBlock = woodMap.get(CompatWood.BlockType.TWIG).get();
            Block logFenceBlock = woodMap.get(CompatWood.BlockType.LOG_FENCE).get();
            Item supportBlockItem = woodSupportMap.get();
            Block toolRackBlock = woodMap.get(CompatWood.BlockType.TOOL_RACK).get();
            Block sluiceBlock = woodMap.get(CompatWood.BlockType.SLUICE).get();
            Block barrelBlock = woodMap.get(CompatWood.BlockType.BARREL).get();
            Block loomBlock = woodMap.get(CompatWood.BlockType.LOOM).get();
            Block scribingTableBlock = woodMap.get(CompatWood.BlockType.SCRIBING_TABLE).get();
            Block sewingTableBlock = woodMap.get(CompatWood.BlockType.SEWING_TABLE).get();
            Block shelfBlock = woodMap.get(CompatWood.BlockType.SHELF).get();
            Block bladedAxleBlock = woodMap.get(CompatWood.BlockType.BLADED_AXLE).get();
            Block axleBlock = woodMap.get(CompatWood.BlockType.AXLE).get();
            Block encasedAxleBlock = woodMap.get(CompatWood.BlockType.ENCASED_AXLE).get();
            Block clutchBlock = woodMap.get(CompatWood.BlockType.CLUTCH).get();
            Block gearBoxBlock = woodMap.get(CompatWood.BlockType.GEAR_BOX).get();
            Block waterWheelBlock = woodMap.get(CompatWood.BlockType.WATER_WHEEL).get();
            //Block windmillBlock = woodMap.get(CompatWood.BlockType.WINDMILL).get();

            Item lumberItem = woodLumberMap.get();

            //lumber
            basicItem(lumberItem);
            //twig
            basicItem(twigBlock.asItem());
            //log fence
            withExistingParent(blockPathName(logFenceBlock), modLoc("block/log_fence/" + woodName + "/inventory"));
            //supports
            withExistingParent(itemPathName(supportBlockItem), modLoc("block/support/" + woodName + "/inventory"));
            //tool rack
            withExistingParent(blockPathName(toolRackBlock), modLoc("block/tool_rack/" + woodName));
            //sluice
            withExistingParent(blockPathName(sluiceBlock), modLoc("block/sluice/" + woodName + "/lower"));
            //barrel
            withExistingParent(blockPathName(barrelBlock), modLoc("block/barrel/" + woodName + "/barrel"));
            //loom
            withExistingParent(blockPathName(loomBlock), modLoc("block/loom/" + woodName));
            //scribing table
            withExistingParent(blockPathName(scribingTableBlock), modLoc("block/scribing_table/" + woodName));
            //sewing table
            withExistingParent(blockPathName(sewingTableBlock), modLoc("block/sewing_table/" + woodName));
            //shelf
            withExistingParent(blockPathName(shelfBlock), modLoc("block/shelf/" + woodName));
            //axle
            withExistingParent(blockPathName(axleBlock), modLoc("block/axle/" + woodName));
            //bladed axle
            withExistingParent(blockPathName(bladedAxleBlock), modLoc("block/bladed_axle/" + woodName));
            //encased axle
            withExistingParent(blockPathName(encasedAxleBlock), modLoc("block/encased_axle/" + woodName));
            //gear box
            withExistingParent(blockPathName(gearBoxBlock), modLoc("block/template/ore"))
                    .texture("all", planksTexture)
                    .texture("overlay", axleCasingFrontTexture);
            //clutch
            withExistingParent(blockPathName(clutchBlock), modLoc("block/clutch/" + woodName));
            //water wheel
            basicItem(waterWheelBlock.asItem());
            //windmill - N/A

            //Firmalife
                Block barrelPressBlock = CompatFLBlocks.BARREL_PRESSES.get(wood).get();
                Block foodShelfBlock = CompatFLBlocks.FOOD_SHELVES.get(wood).get();
                Block hangerBlock = CompatFLBlocks.HANGERS.get(wood).get();
                Block jarbnetBlock = CompatFLBlocks.JARBNETS.get(wood).get();
                Block kegBlock = CompatFLBlocks.KEGS.get(wood).get();
                Block stompBarrelBlock = CompatFLBlocks.STOMPING_BARRELS.get(wood).get();
                Block wineShelfBlock = CompatFLBlocks.WINE_SHELVES.get(wood).get();

                withExistingParent(blockPathName(barrelPressBlock), modLoc("block/barrel_press/" + woodName));
                withExistingParent(blockPathName(foodShelfBlock), modLoc("block/food_shelf/" + woodName));
                withExistingParent(blockPathName(hangerBlock), modLoc("block/hanger/" + woodName));
                withExistingParent(blockPathName(jarbnetBlock), modLoc("block/jarbnet/" + woodName));
                withExistingParent(blockPathName(kegBlock), modLoc("block/big_barrel/" + woodName + "_item"));
                withExistingParent(blockPathName(stompBarrelBlock), modLoc("block/stomping_barrel/" + woodName));
                withExistingParent(blockPathName(wineShelfBlock), modLoc("block/wine_shelf/" + woodName));


            //RNR
                for(CompatRock rock : CompatRock.VALUES){
                    Block flagstoneBlock = RNRCompatBlocks.ROCK_BLOCKS.get(rock).get(CompatRNR.FLAGSTONE).get();
                    Block flagstoneStair = RNRCompatBlocks.ROCK_STAIRS.get(rock).get(CompatRNR.FLAGSTONE).get();
                    Block flagstoneSlab = RNRCompatBlocks.ROCK_SLABS.get(rock).get(CompatRNR.FLAGSTONE).get();
                    Block cobbledBlock = RNRCompatBlocks.ROCK_BLOCKS.get(rock).get(CompatRNR.COBBLED_ROAD).get();
                    Block cobbledStair = RNRCompatBlocks.ROCK_STAIRS.get(rock).get(CompatRNR.COBBLED_ROAD).get();
                    Block cobbledSlab = RNRCompatBlocks.ROCK_SLABS.get(rock).get(CompatRNR.COBBLED_ROAD).get();
                    Block settBlock = RNRCompatBlocks.ROCK_BLOCKS.get(rock).get(CompatRNR.SETT_ROAD).get();
                    Block settStair = RNRCompatBlocks.ROCK_STAIRS.get(rock).get(CompatRNR.SETT_ROAD).get();
                    Block settSlab = RNRCompatBlocks.ROCK_SLABS.get(rock).get(CompatRNR.SETT_ROAD).get();

                    simpleBlockItem(flagstoneBlock);
                    simpleBlockItem(flagstoneStair);
                    simpleBlockItem(flagstoneSlab);
                    simpleBlockItem(cobbledBlock);
                    simpleBlockItem(cobbledStair);
                    simpleBlockItem(cobbledSlab);
                    simpleBlockItem(settBlock);
                    simpleBlockItem(settStair);
                    simpleBlockItem(settSlab);

                    basicItem(RNRCompatItems.FLAGSTONE.get(rock).get());
                }
                Block overheightGravelBlock = RNRCompatBlocks.OVER_HEIGHT_GRAVEL.get();
                Block tampedDirtBlock = RNRCompatBlocks.TAMPED_DIRT.get();
                Block tampedMudBlock = RNRCompatBlocks.TAMPED_MUD.get();

                Block gravelBlock = RNRCompatBlocks.GRAVEL_ROAD.get();
                Block gravelStair = RNRCompatBlocks.GRAVEL_ROAD_STAIRS.get();
                Block gravelSlab = RNRCompatBlocks.GRAVEL_ROAD_SLAB.get();

                Block macadamBlock = RNRCompatBlocks.MACADAM_ROAD.get();
                Block macadamStair = RNRCompatBlocks.MACADAM_ROAD_STAIRS.get();
                Block macadamSlab = RNRCompatBlocks.MACADAM_ROAD_SLAB.get();

                simpleBlockItem(overheightGravelBlock);
                simpleBlockItem(tampedDirtBlock);
                simpleBlockItem(tampedMudBlock);

                simpleBlockItem(gravelBlock);
                simpleBlockItem(gravelStair);
                simpleBlockItem(gravelSlab);

                simpleBlockItem(macadamBlock);
                simpleBlockItem(macadamStair);
                simpleBlockItem(macadamSlab);

                basicItem(RNRCompatItems.GRAVEL_FILL.get());

        }

        basicItem(ModItems.BAMBOO_LUMBER.get());
        withExistingParent(blockPathName(ModBlocks.PRIMITIVE_ANVIL.get()), modLoc("block/primitive_anvil"));

        //Rock
        for (CompatRock rock : CompatRock.VALUES) {
            var rockMap = ModBlocks.ROCK_BLOCKS.get(rock);
            String rockName = rock.getSerializedName();

            //ResourceLocation planksTexture = mcLoc("block/" + woodName + "_planks");

            Block looseBlock = rockMap.get(CompatRock.BlockType.LOOSE).get();
            Block hardenedBlock = rockMap.get(CompatRock.BlockType.HARDENED).get();
            Block looseCobbleBlock = rockMap.get(CompatRock.BlockType.LOOSE_COBBLE).get();
            Block hardenedCobbleBlock = rockMap.get(CompatRock.BlockType.HARDENED_COBBLE).get();

            //loose
            basicItem(looseBlock.asItem());
            //hardened
            //withExistingParent(blockPathName(hardenedBlock), modLoc("block/hardened_" + rockName));
            simpleBlockItem(hardenedBlock);
            simpleBlockItem(looseCobbleBlock);
            simpleBlockItem(hardenedCobbleBlock);

            // Rock ores (non-graded)
            var oreMap = ORES.get(rock);
            if (oreMap != null) {
                oreMap.forEach((ore, blockId) -> {
                    Block oreBlock = blockId.get();

                    // Force lowercase for safety
                    String rockLower = rock.getSerializedName().toLowerCase();
                    String oreLower = ore.name().toLowerCase();  // or ore.getSerializedName().toLowerCase() if you have it

                    String modelName = rockLower + "_" + oreLower + "_ore";

                    withExistingParent(blockPathName(oreBlock), modLoc("block/" + modelName));
                });
            }

            // Graded ores (poor/normal/rich)
            var gradedOreMap = GRADED_ORES.get(rock);
            if (gradedOreMap != null) {
                gradedOreMap.forEach((ore, gradeMap) -> {
                    gradeMap.forEach((grade, blockId) -> {
                        Block oreBlock = blockId.get();

                        // Force lowercase for rock, ore, and grade
                        String rockLower = rock.getSerializedName().toLowerCase();
                        String oreLower  = ore.name().toLowerCase();           // safe fallback
                        String gradeLower = grade.name().toLowerCase();        // "poor", "normal", "rich"

                        String modelName = gradeLower + "_" + rockLower + "_" + oreLower + "_ore";

                        withExistingParent(blockPathName(oreBlock), modLoc("block/" + modelName));
                    });
                });
            }

            if(ModList.get().isLoaded("firmalife")){
                for(Ore.Grade grade : Ore.Grade.values()){
                    Block chromiteOreBlock = CompatFLBlocks.CHROMITE_ORES.get(rock).get(grade).get();

                    simpleBlockItem(chromiteOreBlock);
                }
            }
        }
        basicItem(ModItems.ANDESITE_BRICK.get());
        basicItem(ModItems.GRANITE_BRICK.get());
        basicItem(ModItems.DIORITE_BRICK.get());
        basicItem(ModItems.CALCITE_BRICK.get());
        basicItem(ModItems.DRIPSTONE_BRICK.get());
        basicItem(ModItems.BASALT_BRICK.get());

        //Deposits
        Block cassiteriteDepositBlock = ModBlocks.CASSITERITE_GRAVEL_DEPOSIT.get();
        Block copperDepositBlock = ModBlocks.NATIVE_COPPER_GRAVEL_DEPOSIT.get();
        Block silverDepositBlock = ModBlocks.NATIVE_SILVER_GRAVEL_DEPOSIT.get();
        Block goldDepositBlock = ModBlocks.NATIVE_GOLD_GRAVEL_DEPOSIT.get();

        withExistingParent(blockPathName(cassiteriteDepositBlock), modLoc("block/cassiterite_gravel_deposit"));
        withExistingParent(blockPathName(copperDepositBlock), modLoc("block/native_copper_gravel_deposit"));
        withExistingParent(blockPathName(silverDepositBlock), modLoc("block/native_silver_gravel_deposit"));
        withExistingParent(blockPathName(goldDepositBlock), modLoc("block/native_gold_gravel_deposit"));

        //aqueduct
        for (CompatBricks brick : CompatBricks.VALUES) {
            String brickName = brick.getSingleName();

            Block aqueductBlock = ModBlocks.AQUEDUCTS.get(brick).get();

            withExistingParent(blockPathName(aqueductBlock), modLoc("block/aqueduct/" + brickName + "/base"));
        }

        basicItem(ModItems.STONE_BRICK.asItem());
        basicItem(ModItems.DEEPSLATE_BRICK.asItem());
        basicItem(ModItems.DEEPSLATE_TILE.asItem());
        basicItem(ModItems.POLISHED_BLACKSTONE_BRICK.asItem());
        basicItem(ModItems.END_STONE_BRICK.asItem());
        basicItem(ModItems.QUARTZ_BRICK.asItem());
        basicItem(ModItems.PRISMARINE_BRICK.asItem());
        basicItem(ModItems.TUFF_BRICK.asItem());

        //Natural
        Block clayGrassBlock = ModBlocks.CLAY_GRASS_BLOCK.get();
        Block clayDirtBlock = ModBlocks.CLAY_DIRT.get();
        Block clayPodzolBlock = ModBlocks.CLAY_PODZOL.get();
        Block farmlandBlock = ModBlocks.COMPAT_FARMLAND.get();
        Item dryingMudBrickItem = ModBlocks.DRYING_MUD_BRICK.get().asItem();
        Block kaolinGrassBlock = ModBlocks.KAOLIN_CLAY_GRASS_BLOCK.get();
        Block kaolinDirtBlock = ModBlocks.KAOLIN_CLAY_DIRT.get();
        Block kaolinPodzolBlock = ModBlocks.KAOLIN_CLAY_PODZOL.get();

        withExistingParent(blockPathName(clayGrassBlock), modLoc("block/clay_grass_block"));
        withExistingParent(blockPathName(clayDirtBlock), modLoc("block/clay_dirt"));
        withExistingParent(blockPathName(clayPodzolBlock), modLoc("block/clay_podzol"));
        withExistingParent(blockPathName(farmlandBlock), modLoc("block/farmland"));
        basicItem(dryingMudBrickItem);
        basicItem(ModItems.MUD_BRICK.get());
        basicItem(ModItems.UNFIRED_POT.asItem());
        withExistingParent(blockPathName(kaolinGrassBlock), modLoc("block/kaolin_clay_grass_block"));
        withExistingParent(blockPathName(kaolinDirtBlock), modLoc("block/kaolin_clay_dirt"));
        withExistingParent(blockPathName(kaolinPodzolBlock), modLoc("block/kaolin_clay_podzol"));

        //Metal
        for(CompatMetal metal : CompatMetal.values()){
            for(CompatMetal.ItemType itemType : CompatMetal.ItemType.values()){
                if(itemType.has(metal)){
                    Item metalItem = ModItems.METAL_ITEMS.get(metal).get(itemType).get();

                    basicItem(metalItem);
                }
            }
        }
    }

    private String blockPathName(Block block) {
        return block.builtInRegistryHolder().key().location().getPath();
    }

    private String itemPathName(Item item) {
        return item.builtInRegistryHolder().key().location().getPath();
    }
}
