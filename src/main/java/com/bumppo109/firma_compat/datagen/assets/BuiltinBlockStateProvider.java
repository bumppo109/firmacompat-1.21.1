package com.bumppo109.firma_compat.datagen.assets;

import com.bumppo109.firma_compat.FirmaCompat;
import com.bumppo109.firma_compat.block.CompatBricks;
import com.bumppo109.firma_compat.block.CompatRock;
import com.bumppo109.firma_compat.block.CompatWood;
import com.bumppo109.firma_compat.block.ModBlocks;
import com.bumppo109.firma_compat.tfcaddon.firmalife.CompatFLBlocks;
import com.bumppo109.firma_compat.tfcaddon.rnr.CompatRNR;
import com.bumppo109.firma_compat.tfcaddon.rnr.RNRCompatBlocks;
import com.eerussianguy.firmalife.common.blocks.*;
import net.dries007.tfc.common.blocks.devices.BarrelBlock;
import net.dries007.tfc.common.blocks.devices.DryingBricksBlock;
import net.dries007.tfc.common.blocks.devices.SluiceBlock;
import net.dries007.tfc.common.blocks.rock.LooseRockBlock;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BuiltinBlockStateProvider extends BlockStateProvider {
    public BuiltinBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, "firma_compat", existingFileHelper);
    }


    @Override
    protected void registerStatesAndModels() {

        for (CompatWood wood : CompatWood.VALUES) {
            String woodName = wood.getSerializedName();

            Block twigBlock = ModBlocks.WOODS.get(wood).get(CompatWood.BlockType.TWIG).get();
            Block logFenceBlock = ModBlocks.WOODS.get(wood).get(CompatWood.BlockType.LOG_FENCE).get();
            Block verticalSupportBlock = ModBlocks.WOODS.get(wood).get(CompatWood.BlockType.VERTICAL_SUPPORT).get();
            Block horizontalSupportBlock = ModBlocks.WOODS.get(wood).get(CompatWood.BlockType.HORIZONTAL_SUPPORT).get();
            Block toolRackBlock = ModBlocks.WOODS.get(wood).get(CompatWood.BlockType.TOOL_RACK).get();
            Block sluiceBlock = ModBlocks.WOODS.get(wood).get(CompatWood.BlockType.SLUICE).get();
            Block barrelBlock = ModBlocks.WOODS.get(wood).get(CompatWood.BlockType.BARREL).get();
            Block loomBlock = ModBlocks.WOODS.get(wood).get(CompatWood.BlockType.LOOM).get();
            Block scribingTableBlock = ModBlocks.WOODS.get(wood).get(CompatWood.BlockType.SCRIBING_TABLE).get();
            Block sewingTableBlock = ModBlocks.WOODS.get(wood).get(CompatWood.BlockType.SEWING_TABLE).get();
            Block shelfBlock = ModBlocks.WOODS.get(wood).get(CompatWood.BlockType.SHELF).get();
            Block axleBlock = ModBlocks.WOODS.get(wood).get(CompatWood.BlockType.AXLE).get();
            Block bladedAxleBlock = ModBlocks.WOODS.get(wood).get(CompatWood.BlockType.BLADED_AXLE).get();
            Block encasedAxleBlock = ModBlocks.WOODS.get(wood).get(CompatWood.BlockType.ENCASED_AXLE).get();
            Block clutchBlock = ModBlocks.WOODS.get(wood).get(CompatWood.BlockType.CLUTCH).get();
            Block gearBoxBlock = ModBlocks.WOODS.get(wood).get(CompatWood.BlockType.GEAR_BOX).get();
            Block waterWheelBlock = ModBlocks.WOODS.get(wood).get(CompatWood.BlockType.WATER_WHEEL).get();
            Block windmillBlock = ModBlocks.WOODS.get(wood).get(CompatWood.BlockType.WINDMILL).get();

            //Empty Model
            ModelFile emptyModel = new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath("tfc", "block/empty"));

            // Construct vanilla texture locations dynamically
            String logSuffix = switch(woodName){
                case "crimson", "warped" -> "stem";
                default -> "log";
            };
            ResourceLocation logSideTexture = mcLoc("block/" + woodName + "_" + logSuffix);
            ResourceLocation logTopTexture = mcLoc("block/" + woodName + "_" + logSuffix + "_top");
            ResourceLocation strippedLogSideTexture = mcLoc("block/stripped_" + woodName + "_" + logSuffix);
            ResourceLocation strippedLogTopTexture = mcLoc("block/stripped_" + woodName + "_" + logSuffix + "_top");
            ResourceLocation planksTexture = wood.planksTexture();

            ResourceLocation scribingParaphernaliaTexture = modLoc("block/template/scribing_paraphernalia");
            ResourceLocation axleCasingTexture = modLoc("block/template/axle_casing");
            ResourceLocation axleCasingFrontTexture = modLoc("block/template/axle_casing_front");
            ResourceLocation axleCasingRoundTexture = modLoc("block/template/axle_casing_round");
            ResourceLocation axleCasingUnpoweredTexture = modLoc("block/template/axle_casing_unpowered");
            ResourceLocation axleCasingPoweredTexture = modLoc("block/template/axle_casing_powered");

            //Twig
            ModelFile twigModel = models()
                    .withExistingParent(("block/twig/" + woodName), modLoc("block/template/twig"))
                    .texture("side", logSideTexture)
                    .texture("top", logTopTexture);
            simpleBlock(twigBlock,
                    ConfiguredModel.builder()
                            .modelFile(twigModel).rotationY(90).buildLast(),
                    ConfiguredModel.builder()
                            .modelFile(twigModel).buildLast(),
                    ConfiguredModel.builder()
                            .modelFile(twigModel).rotationY(180).buildLast(),
                    ConfiguredModel.builder()
                            .modelFile(twigModel).rotationY(270).buildLast()
            );

            //Log Fence
            ModelFile logFenceInventoryModel = models()
                    .withExistingParent(("block/log_fence/" + woodName + "/inventory"), modLoc("block/template/log_fence_inventory"))
                    .texture("log", logSideTexture)
                    .texture("planks", planksTexture);
            ModelFile logFencePostModel = models()
                    .withExistingParent(("block/log_fence/" + woodName + "/post"), mcLoc("block/fence_post"))
                    .texture("texture", logSideTexture);
            ModelFile logFenceSideModel = models()
                    .withExistingParent(("block/log_fence/" + woodName + "/side"), mcLoc("block/fence_side"))
                    .texture("texture", planksTexture);

            getMultipartBuilder(logFenceBlock)
                    // Always show the post
                    .part()
                    .modelFile(logFencePostModel)
                    .addModel()
                    .end()

                    // North connection
                    .part()
                    .modelFile(logFenceSideModel)
                    .uvLock(true)
                    .addModel()
                    .condition(BlockStateProperties.NORTH, true)
                    .end()

                    // East connection
                    .part()
                    .modelFile(logFenceSideModel)
                    .rotationY(90)
                    .uvLock(true)
                    .addModel()
                    .condition(BlockStateProperties.EAST, true)
                    .end()

                    // South connection
                    .part()
                    .modelFile(logFenceSideModel)
                    .rotationY(180)
                    .uvLock(true)
                    .addModel()
                    .condition(BlockStateProperties.SOUTH, true)
                    .end()

                    // West connection
                    .part()
                    .modelFile(logFenceSideModel)
                    .rotationY(270)
                    .uvLock(true)
                    .addModel()
                    .condition(BlockStateProperties.WEST, true)
                    .end();

            //TODO - Vertical Support
            ModelFile supportConnectionModel = models()
                    .withExistingParent(("block/support/" + woodName + "/connection"), modLoc("block/template/support/connection"))
                    .texture("texture", strippedLogSideTexture)
                    .texture("particle", strippedLogSideTexture);
            ModelFile supportHorizontalModel = models()
                    .withExistingParent(("block/support/" + woodName + "/horizontal"), modLoc("block/template/support/horizontal"))
                    .texture("texture", strippedLogSideTexture)
                    .texture("particle", strippedLogSideTexture);
            ModelFile supportVerticalModel = models()
                    .withExistingParent(("block/support/" + woodName + "/vertical"), modLoc("block/template/support/vertical"))
                    .texture("texture", strippedLogSideTexture)
                    .texture("particle", strippedLogSideTexture);
            ModelFile supportInventoryModel = models()
                    .withExistingParent(("block/support/" + woodName + "/inventory"), modLoc("block/template/support/inventory"))
                    .texture("texture", strippedLogSideTexture);

            getMultipartBuilder(verticalSupportBlock)
                    // Always show the vertical beam
                    .part()
                    .modelFile(supportVerticalModel)
                    .addModel()
                    .end()

                    // Connections
                    .part()
                    .modelFile(supportConnectionModel)
                    .rotationY(270)
                    .addModel()
                    .condition(BlockStateProperties.NORTH, true)
                    .end()
                    .part()
                    .modelFile(supportConnectionModel)
                    .addModel()  // east: no rotation
                    .condition(BlockStateProperties.EAST, true)
                    .end()
                    .part()
                    .modelFile(supportConnectionModel)
                    .rotationY(90)
                    .addModel()
                    .condition(BlockStateProperties.SOUTH, true)
                    .end()
                    .part()
                    .modelFile(supportConnectionModel)
                    .rotationY(180)
                    .addModel()
                    .condition(BlockStateProperties.WEST, true)
                    .end();

            //TODO - Horizontal Support
            getMultipartBuilder(horizontalSupportBlock)
                    // Always show the horizontal beam
                    .part()
                    .modelFile(supportHorizontalModel)
                    .addModel()
                    .end()

                    // Connections — same rotations as vertical
                    .part()
                    .modelFile(supportConnectionModel)
                    .rotationY(270)
                    .addModel()
                    .condition(BlockStateProperties.NORTH, true)
                    .end()
                    .part()
                    .modelFile(supportConnectionModel)
                    .addModel()
                    .condition(BlockStateProperties.EAST, true)
                    .end()
                    .part()
                    .modelFile(supportConnectionModel)
                    .rotationY(90)
                    .addModel()
                    .condition(BlockStateProperties.SOUTH, true)
                    .end()
                    .part()
                    .modelFile(supportConnectionModel)
                    .rotationY(180)
                    .addModel()
                    .condition(BlockStateProperties.WEST, true)
                    .end();

            //TODO - Tool Rack
            ModelFile toolRackModel = models()
                    .withExistingParent(("block/tool_rack/" + woodName), modLoc("block/template/tool_rack"))
                    .texture("texture", planksTexture)
                    .texture("particle", planksTexture);

            horizontalBlock(toolRackBlock, toolRackModel, 0);

            //TODO - Sluice
            ModelFile sluiceLowerModel = models()
                    .withExistingParent(("block/sluice/" + woodName + "/lower"), modLoc("block/template/sluice/lower"))
                    .texture("texture", strippedLogSideTexture);
            ModelFile sluiceUpperModel = models()
                    .withExistingParent(("block/sluice/" + woodName + "/upper"), modLoc("block/template/sluice/upper"))
                    .texture("texture", strippedLogSideTexture);

            getVariantBuilder(sluiceBlock)
                    // Upper part (upper=true)
                    .partialState().with(SluiceBlock.UPPER, true).with(HorizontalDirectionalBlock.FACING, Direction.EAST)
                    .modelForState().modelFile(sluiceUpperModel).rotationY(90).addModel()
                    .partialState().with(SluiceBlock.UPPER, true).with(HorizontalDirectionalBlock.FACING, Direction.NORTH)
                    .modelForState().modelFile(sluiceUpperModel).rotationY(0).addModel()
                    .partialState().with(SluiceBlock.UPPER, true).with(HorizontalDirectionalBlock.FACING, Direction.SOUTH)
                    .modelForState().modelFile(sluiceUpperModel).rotationY(180).addModel()
                    .partialState().with(SluiceBlock.UPPER, true).with(HorizontalDirectionalBlock.FACING, Direction.WEST)
                    .modelForState().modelFile(sluiceUpperModel).rotationY(270).addModel()

                    // Lower part (upper=false)
                    .partialState().with(SluiceBlock.UPPER, false).with(HorizontalDirectionalBlock.FACING, Direction.EAST)
                    .modelForState().modelFile(sluiceLowerModel).rotationY(90).addModel()
                    .partialState().with(SluiceBlock.UPPER, false).with(HorizontalDirectionalBlock.FACING, Direction.NORTH)
                    .modelForState().modelFile(sluiceLowerModel).rotationY(0).addModel()
                    .partialState().with(SluiceBlock.UPPER, false).with(HorizontalDirectionalBlock.FACING, Direction.SOUTH)
                    .modelForState().modelFile(sluiceLowerModel).rotationY(180).addModel()
                    .partialState().with(SluiceBlock.UPPER, false).with(HorizontalDirectionalBlock.FACING, Direction.WEST)
                    .modelForState().modelFile(sluiceLowerModel).rotationY(270).addModel();
            //TODO - Barrel
            barrelBlock(barrelBlock, wood);

            //TODO - Loom - check offset
            ModelFile loomModel = models()
                    .withExistingParent(("block/loom/" + woodName), modLoc("block/template/loom"))
                    .texture("texture", planksTexture)
                    .texture("particle", planksTexture);

            getVariantBuilder(loomBlock)
                    .partialState().with(HorizontalDirectionalBlock.FACING, Direction.EAST)
                    .modelForState().modelFile(loomModel).rotationY(270).addModel()
                    .partialState().with(HorizontalDirectionalBlock.FACING, Direction.NORTH)
                    .modelForState().modelFile(loomModel).rotationY(180).addModel()
                    .partialState().with(HorizontalDirectionalBlock.FACING, Direction.SOUTH)
                    .modelForState().modelFile(loomModel).rotationY(0).addModel()  // default, no rotation
                    .partialState().with(HorizontalDirectionalBlock.FACING, Direction.WEST)
                    .modelForState().modelFile(loomModel).rotationY(90).addModel();

            //TODO - Scribing Table
            ModelFile scribingTableModel = models()
                    .withExistingParent(("block/scribing_table/" + woodName), modLoc("block/template/scribing_table"))
                    .texture("top", strippedLogSideTexture)
                    .texture("leg", logSideTexture)
                    .texture("side", planksTexture)
                    .texture("misc", scribingParaphernaliaTexture)
                    .texture("particle", planksTexture);

            getVariantBuilder(scribingTableBlock)
                    .partialState().with(HorizontalDirectionalBlock.FACING, Direction.EAST)
                    .modelForState().modelFile(scribingTableModel).rotationY(90).addModel()
                    .partialState().with(HorizontalDirectionalBlock.FACING, Direction.NORTH)
                    .modelForState().modelFile(scribingTableModel).rotationY(0).addModel()
                    .partialState().with(HorizontalDirectionalBlock.FACING, Direction.SOUTH)
                    .modelForState().modelFile(scribingTableModel).rotationY(180).addModel()  // default, no rotation
                    .partialState().with(HorizontalDirectionalBlock.FACING, Direction.WEST)
                    .modelForState().modelFile(scribingTableModel).rotationY(270).addModel();

            //TODO - Sewing Table
            ModelFile sewingTableModel = models()
                    .withExistingParent(("block/sewing_table/" + woodName), modLoc("block/template/sewing_table"))
                    .texture("0", logSideTexture)
                    .texture("1", planksTexture);

            getVariantBuilder(sewingTableBlock)
                    .partialState().with(HorizontalDirectionalBlock.FACING, Direction.EAST)
                    .modelForState().modelFile(sewingTableModel).rotationY(90).addModel()
                    .partialState().with(HorizontalDirectionalBlock.FACING, Direction.NORTH)
                    .modelForState().modelFile(sewingTableModel).rotationY(0).addModel()
                    .partialState().with(HorizontalDirectionalBlock.FACING, Direction.SOUTH)
                    .modelForState().modelFile(sewingTableModel).rotationY(180).addModel()  // default, no rotation
                    .partialState().with(HorizontalDirectionalBlock.FACING, Direction.WEST)
                    .modelForState().modelFile(sewingTableModel).rotationY(270).addModel();

            //TODO - Shelf
            ModelFile shelfModel = models()
                    .withExistingParent(("block/shelf/" + woodName), modLoc("block/template/shelf"))
                    .texture("0", planksTexture);

            getVariantBuilder(shelfBlock)
                    .partialState().with(HorizontalDirectionalBlock.FACING, Direction.EAST)
                    .modelForState().modelFile(shelfModel).rotationY(90).addModel()
                    .partialState().with(HorizontalDirectionalBlock.FACING, Direction.NORTH)
                    .modelForState().modelFile(shelfModel).rotationY(0).addModel()
                    .partialState().with(HorizontalDirectionalBlock.FACING, Direction.SOUTH)
                    .modelForState().modelFile(shelfModel).rotationY(180).addModel()  // default, no rotation
                    .partialState().with(HorizontalDirectionalBlock.FACING, Direction.WEST)
                    .modelForState().modelFile(shelfModel).rotationY(270).addModel();

            //TODO - Bladed Axle
            ModelFile bladedAxleModel = models()
                    .withExistingParent(("block/bladed_axle/" + woodName), modLoc("block/template/bladed_axle"))
                    .texture("wood", strippedLogSideTexture);

            simpleBlock(bladedAxleBlock, emptyModel);

            //TODO - Axle
            ModelFile axleModel = models()
                    .withExistingParent(("block/axle/" + woodName), modLoc("block/template/axle"))
                    .texture("wood", strippedLogSideTexture);

            simpleBlock(axleBlock, emptyModel);

            //TODO - Encased Axle
            ModelFile encasedAxleModel = models()
                    .withExistingParent(("block/encased_axle/" + woodName), modLoc("block/template/ore_column"))
                    .texture("side", strippedLogSideTexture)
                    .texture("end", planksTexture)
                    .texture("overlay", axleCasingTexture)
                    .texture("overlay_end", axleCasingFrontTexture)
                    .texture("particle", strippedLogSideTexture);

            getVariantBuilder(encasedAxleBlock)
                    .partialState().with(BlockStateProperties.AXIS, Direction.Axis.X)
                    .modelForState().modelFile(encasedAxleModel).rotationX(90).rotationY(90).addModel()
                    .partialState().with(BlockStateProperties.AXIS, Direction.Axis.Y)
                    .modelForState().modelFile(encasedAxleModel).addModel()
                    .partialState().with(BlockStateProperties.AXIS, Direction.Axis.Z)
                    .modelForState().modelFile(encasedAxleModel).rotationX(90).addModel();

            //TODO - Gear Box
            ModelFile gearBoxPortModel = models()
                    .withExistingParent(("block/gear_box/" + woodName + "/port"), modLoc("block/template/gear_box/port"))
                    .texture("all", planksTexture)
                    .texture("overlay", axleCasingFrontTexture);
            ModelFile gearBoxFaceModel = models()
                    .withExistingParent(("block/gear_box/" + woodName + "/face"), modLoc("block/template/gear_box/face"))
                    .texture("all", planksTexture)
                    .texture("overlay", axleCasingRoundTexture);

            getMultipartBuilder(gearBoxBlock)
                    // North
                    .part().modelFile(gearBoxPortModel).addModel().condition(BlockStateProperties.NORTH, true).end()
                    .part().modelFile(gearBoxFaceModel).addModel().condition(BlockStateProperties.NORTH, false).end()

                    // South
                    .part().modelFile(gearBoxPortModel).rotationY(180).addModel().condition(BlockStateProperties.SOUTH, true).end()
                    .part().modelFile(gearBoxFaceModel).rotationY(180).addModel().condition(BlockStateProperties.SOUTH, false).end()

                    // East
                    .part().modelFile(gearBoxPortModel).rotationY(90).addModel().condition(BlockStateProperties.EAST, true).end()
                    .part().modelFile(gearBoxFaceModel).rotationY(90).addModel().condition(BlockStateProperties.EAST, false).end()

                    // West
                    .part().modelFile(gearBoxPortModel).rotationY(270).addModel().condition(BlockStateProperties.WEST, true).end()
                    .part().modelFile(gearBoxFaceModel).rotationY(270).addModel().condition(BlockStateProperties.WEST, false).end()

                    // Down
                    .part().modelFile(gearBoxPortModel).rotationX(90).addModel().condition(BlockStateProperties.DOWN, true).end()
                    .part().modelFile(gearBoxFaceModel).rotationX(90).addModel().condition(BlockStateProperties.DOWN, false).end()

                    // Up
                    .part().modelFile(gearBoxPortModel).rotationX(270).addModel().condition(BlockStateProperties.UP, true).end()
                    .part().modelFile(gearBoxFaceModel).rotationX(270).addModel().condition(BlockStateProperties.UP, false).end();

            //TODO - Clutch
            ModelFile clutchModel = models()
                    .withExistingParent(("block/clutch/" + woodName), modLoc("block/template/ore_column"))
                    .texture("side", strippedLogSideTexture)
                    .texture("end", planksTexture)
                    .texture("overlay", axleCasingUnpoweredTexture)
                    .texture("overlay_end", axleCasingFrontTexture)
                    .texture("particle", strippedLogSideTexture);
            ModelFile clutchPoweredModel = models()
                    .withExistingParent(("block/clutch/" + woodName + "_powered"), modLoc("block/template/ore_column"))
                    .texture("side", strippedLogSideTexture)
                    .texture("end", planksTexture)
                    .texture("overlay", axleCasingPoweredTexture)
                    .texture("overlay_end", axleCasingFrontTexture)
                    .texture("particle", strippedLogSideTexture);

            getVariantBuilder(clutchBlock)
                    // Axis X
                    .partialState().with(BlockStateProperties.AXIS, Direction.Axis.X).with(BlockStateProperties.POWERED, false)
                    .modelForState().modelFile(clutchModel).rotationX(90).rotationY(90).addModel()
                    .partialState().with(BlockStateProperties.AXIS, Direction.Axis.X).with(BlockStateProperties.POWERED, true)
                    .modelForState().modelFile(clutchPoweredModel).rotationX(90).rotationY(90).addModel()

                    // Axis Y
                    .partialState().with(BlockStateProperties.AXIS, Direction.Axis.Y).with(BlockStateProperties.POWERED, false)
                    .modelForState().modelFile(clutchModel).addModel()
                    .partialState().with(BlockStateProperties.AXIS, Direction.Axis.Y).with(BlockStateProperties.POWERED, true)
                    .modelForState().modelFile(clutchPoweredModel).addModel()

                    // Axis Z
                    .partialState().with(BlockStateProperties.AXIS, Direction.Axis.Z).with(BlockStateProperties.POWERED, false)
                    .modelForState().modelFile(clutchModel).rotationX(90).addModel()
                    .partialState().with(BlockStateProperties.AXIS, Direction.Axis.Z).with(BlockStateProperties.POWERED, true)
                    .modelForState().modelFile(clutchPoweredModel).rotationX(90).addModel();
            //TODO - Waterwheel
            ModelFile waterWheelModel = models()
                    .getBuilder("block/water_wheel/" + woodName)
                    .texture("particle", planksTexture);

            simpleBlock(waterWheelBlock, waterWheelModel);

            simpleBlock(windmillBlock, emptyModel);

            if(ModList.get().isLoaded("firmalife")){
                Block foodShelfBlock = CompatFLBlocks.FOOD_SHELVES.get(wood).get();
                Block hangerBlock = CompatFLBlocks.HANGERS.get(wood).get();
                Block jarbnetBlock = CompatFLBlocks.JARBNETS.get(wood).get();
                Block kegBlock = CompatFLBlocks.KEGS.get(wood).get();
                Block kegSubBlock = CompatFLBlocks.KEG_SUBS.get(wood).get();
                Block stompBarrelBlock = CompatFLBlocks.STOMPING_BARRELS.get(wood).get();
                Block barrelPressBlock = CompatFLBlocks.BARREL_PRESSES.get(wood).get();
                Block wineShelfBlock = CompatFLBlocks.WINE_SHELVES.get(wood).get();

                //food shelf
                ModelFile foodShelfModel = models()
                        .withExistingParent(("block/food_shelf/" + woodName), modLoc("block/template/firmalife/food_shelf_base"))
                        .texture("wood", planksTexture);
                ModelFile foodShelfDynamicModel = models().getBuilder("block/food_shelf/" + woodName + "_dynamic")
                        .customLoader((parent, helper) -> new FirmalifeCustomLoaderBuilder<>("food_shelf", parent, helper)
                                .base(ResourceLocation.fromNamespaceAndPath(FirmaCompat.MODID, "block/food_shelf/" + woodName))).end();

                getVariantBuilder(foodShelfBlock)
                        .partialState().with(HorizontalDirectionalBlock.FACING, Direction.EAST)
                        .modelForState().modelFile(foodShelfDynamicModel).rotationY(270).addModel()
                        .partialState().with(HorizontalDirectionalBlock.FACING, Direction.NORTH)
                        .modelForState().modelFile(foodShelfDynamicModel).rotationY(180).addModel()
                        .partialState().with(HorizontalDirectionalBlock.FACING, Direction.SOUTH)
                        .modelForState().modelFile(foodShelfDynamicModel).rotationY(0).addModel()
                        .partialState().with(HorizontalDirectionalBlock.FACING, Direction.WEST)
                        .modelForState().modelFile(foodShelfDynamicModel).rotationY(90).addModel();

                //hanger
                ModelFile hangerModel = models()
                        .withExistingParent(("block/hanger/" + woodName), modLoc("block/template/firmalife/hanger_base"))
                        .texture("wood", planksTexture)
                        .texture("string", "minecraft:block/white_wool");
                ModelFile hangerDynamicModel = models().getBuilder("block/hanger/" + woodName + "_dynamic")
                        .customLoader((parent, helper) -> new FirmalifeCustomLoaderBuilder<>("hanger", parent, helper)
                                .base(ResourceLocation.fromNamespaceAndPath(FirmaCompat.MODID, "block/hanger/" + woodName))).end();

                simpleBlock(hangerBlock, hangerDynamicModel);

                //jarbnet
                ModelFile jarbnetModel = models()
                        .withExistingParent(("block/jarbnet/" + woodName), modLoc("block/template/firmalife/jarbnet"))
                        .texture("planks", planksTexture)
                        .texture("sheet", strippedLogSideTexture)
                        .texture("log", logSideTexture);
                ModelFile jarbnetDynamicModel = models().getBuilder("block/jarbnet/" + woodName + "_dynamic")
                        .customLoader((parent, helper) -> new FirmalifeCustomLoaderBuilder<>("jarbnet", parent, helper)
                                .base(ResourceLocation.fromNamespaceAndPath(FirmaCompat.MODID, "block/jarbnet/" + woodName))).end();

                getVariantBuilder(jarbnetBlock)
                        //Open true
                        .partialState().with(FourWayDeviceBlock.FACING, Direction.EAST).with(JarbnetBlock.OPEN, true)
                        .modelForState().modelFile(jarbnetDynamicModel).rotationY(90).addModel()
                        .partialState().with(FourWayDeviceBlock.FACING, Direction.NORTH).with(JarbnetBlock.OPEN, true)
                        .modelForState().modelFile(jarbnetDynamicModel).addModel()
                        .partialState().with(FourWayDeviceBlock.FACING, Direction.SOUTH).with(JarbnetBlock.OPEN, true)
                        .modelForState().modelFile(jarbnetDynamicModel).rotationY(180).addModel()
                        .partialState().with(FourWayDeviceBlock.FACING, Direction.WEST).with(JarbnetBlock.OPEN, true)
                        .modelForState().modelFile(jarbnetDynamicModel).rotationY(270).addModel()
                        //Open false
                        .partialState().with(FourWayDeviceBlock.FACING, Direction.EAST).with(JarbnetBlock.OPEN, false)
                        .modelForState().modelFile(jarbnetDynamicModel).rotationY(90).addModel()
                        .partialState().with(FourWayDeviceBlock.FACING, Direction.NORTH).with(JarbnetBlock.OPEN, false)
                        .modelForState().modelFile(jarbnetDynamicModel).addModel()
                        .partialState().with(FourWayDeviceBlock.FACING, Direction.SOUTH).with(JarbnetBlock.OPEN, false)
                        .modelForState().modelFile(jarbnetDynamicModel).rotationY(180).addModel()
                        .partialState().with(FourWayDeviceBlock.FACING, Direction.WEST).with(JarbnetBlock.OPEN, false)
                        .modelForState().modelFile(jarbnetDynamicModel).rotationY(270).addModel();

                //wine shelf
                ModelFile wineShelfModel = models()
                        .withExistingParent(("block/wine_shelf/" + woodName), modLoc("block/template/firmalife/wine_shelf"))
                        .texture("0", planksTexture)
                        .texture("2", strippedLogSideTexture)
                        .texture("3", strippedLogSideTexture);
                ModelFile wineShelfDynamicModel = models().getBuilder("block/wine_shelf/" + woodName + "_dynamic")
                        .customLoader((parent, helper) -> new FirmalifeCustomLoaderBuilder<>("wine_shelf", parent, helper)
                                .base(ResourceLocation.fromNamespaceAndPath(FirmaCompat.MODID, "block/wine_shelf/" + woodName))).end();

                getVariantBuilder(wineShelfBlock)
                        .partialState().with(FourWayDeviceBlock.FACING, Direction.EAST)
                        .modelForState().modelFile(wineShelfDynamicModel).rotationY(90).addModel()
                        .partialState().with(FourWayDeviceBlock.FACING, Direction.NORTH)
                        .modelForState().modelFile(wineShelfDynamicModel).addModel()
                        .partialState().with(FourWayDeviceBlock.FACING, Direction.SOUTH)
                        .modelForState().modelFile(wineShelfDynamicModel).rotationY(180).addModel()
                        .partialState().with(FourWayDeviceBlock.FACING, Direction.WEST)
                        .modelForState().modelFile(wineShelfDynamicModel).rotationY(270).addModel();

                //stomping barrel
                ModelFile stompBarrelModel = models()
                        .withExistingParent(("block/stomping_barrel/" + woodName), modLoc("block/template/firmalife/stomping_barrel"))
                        .texture("0", planksTexture);

                simpleBlock(stompBarrelBlock, stompBarrelModel);

                //barrel press
                ModelFile barrelPressModel = models()
                        .withExistingParent(("block/barrel_press/" + woodName), modLoc("block/template/firmalife/barrel_press"))
                        .texture("0", strippedLogSideTexture);

                simpleBlock(barrelPressBlock, barrelPressModel);

                //keg
                // Common base path prefix for big barrel textures
                String bbPrefix = FirmaCompat.MODID + ":block/big_barrel/" + woodName;

                // 1. big_barrel_0_sealed
                ModelFile bigBarrel0Sealed = models()
                        .withExistingParent("block/big_barrel/" + woodName + "_0_sealed", modLoc("block/template/firmalife/big_barrel_0_sealed"))
                        .texture("0", bbPrefix + "_3_side")
                        .texture("1", bbPrefix + "_0")
                        .texture("2", bbPrefix + "_0_side")
                        .texture("3", bbPrefix + "_1")
                        .texture("4", bbPrefix + "_1_side")
                        .texture("5", bbPrefix + "_2")
                        .texture("6", bbPrefix + "_2_side")
                        .texture("7", bbPrefix + "_3")
                        .texture("8", bbPrefix + "_3_top")
                        .texture("9", bbPrefix + "_0_top")
                        .texture("10", bbPrefix + "_1_top")
                        .texture("11", bbPrefix + "_2_top")
                        .texture("12", logSideTexture);  // dynamic TFC log texture

                // 2. big_barrel_0_unsealed
                ModelFile bigBarrel0Unsealed = models()
                        .withExistingParent("block/big_barrel/" + woodName + "_0_unsealed", modLoc("block/template/firmalife/big_barrel_0_unsealed"))
                        .texture("0", bbPrefix + "_3_side")
                        .texture("1", bbPrefix + "_0")
                        .texture("2", bbPrefix + "_0_side")
                        .texture("3", bbPrefix + "_1")
                        .texture("4", bbPrefix + "_1_side")
                        .texture("5", bbPrefix + "_2")
                        .texture("6", bbPrefix + "_2_side")
                        .texture("7", bbPrefix + "_3")
                        .texture("8", bbPrefix + "_3_top")
                        .texture("9", bbPrefix + "_0_top")
                        .texture("10", bbPrefix + "_1_top")
                        .texture("11", bbPrefix + "_2_top")
                        .texture("12", logSideTexture);

                // 3. big_barrel_1
                ModelFile bigBarrel1 = models()
                        .withExistingParent("block/big_barrel/" + woodName + "_1", modLoc("block/template/firmalife/big_barrel_1"))
                        .texture("0", bbPrefix + "_3_side")
                        .texture("1", bbPrefix + "_0")
                        .texture("2", bbPrefix + "_0_side")
                        .texture("3", bbPrefix + "_1")
                        .texture("4", bbPrefix + "_1_side")
                        .texture("5", bbPrefix + "_2")
                        .texture("6", bbPrefix + "_2_side")
                        .texture("7", bbPrefix + "_3")
                        .texture("8", bbPrefix + "_3_top")
                        .texture("9", bbPrefix + "_0_top")
                        .texture("10", bbPrefix + "_1_top")
                        .texture("11", bbPrefix + "_2_top")
                        .texture("12", logSideTexture);

                // 4. big_barrel_2
                ModelFile bigBarrel2 = models()
                        .withExistingParent("block/big_barrel/" + woodName + "_2", modLoc("block/template/firmalife/big_barrel_2"))
                        .texture("0", bbPrefix + "_3_side")
                        .texture("1", bbPrefix + "_0")
                        .texture("2", bbPrefix + "_0_side")
                        .texture("3", bbPrefix + "_1")
                        .texture("4", bbPrefix + "_1_side")
                        .texture("5", bbPrefix + "_2")
                        .texture("6", bbPrefix + "_2_side")
                        .texture("7", bbPrefix + "_3")
                        .texture("8", bbPrefix + "_3_top")
                        .texture("9", bbPrefix + "_0_top")
                        .texture("10", bbPrefix + "_1_top")
                        .texture("11", bbPrefix + "_2_top")
                        .texture("12", logSideTexture);

                // 5. big_barrel_3
                ModelFile bigBarrel3 = models()
                        .withExistingParent("block/big_barrel/" + woodName + "_3", modLoc("block/template/firmalife/big_barrel_3"))
                        .texture("0", bbPrefix + "_3_side")
                        .texture("1", bbPrefix + "_0")
                        .texture("2", bbPrefix + "_0_side")
                        .texture("3", bbPrefix + "_1")
                        .texture("4", bbPrefix + "_1_side")
                        .texture("5", bbPrefix + "_2")
                        .texture("6", bbPrefix + "_2_side")
                        .texture("7", bbPrefix + "_3")
                        .texture("8", bbPrefix + "_3_top")
                        .texture("9", bbPrefix + "_0_top")
                        .texture("10", bbPrefix + "_1_top")
                        .texture("11", bbPrefix + "_2_top")
                        .texture("12", logSideTexture);

                // 6. big_barrel_4
                ModelFile bigBarrel4 = models()
                        .withExistingParent("block/big_barrel/" + woodName + "_4", modLoc("block/template/firmalife/big_barrel_4"))
                        .texture("0", bbPrefix + "_3_side")
                        .texture("1", bbPrefix + "_0")
                        .texture("2", bbPrefix + "_0_side")
                        .texture("3", bbPrefix + "_1")
                        .texture("4", bbPrefix + "_1_side")
                        .texture("5", bbPrefix + "_2")
                        .texture("6", bbPrefix + "_2_side")
                        .texture("7", bbPrefix + "_3")
                        .texture("8", bbPrefix + "_3_top")
                        .texture("9", bbPrefix + "_0_top")
                        .texture("10", bbPrefix + "_1_top")
                        .texture("11", bbPrefix + "_2_top")
                        .texture("12", logSideTexture);

                // 7. big_barrel_5
                ModelFile bigBarrel5 = models()
                        .withExistingParent("block/big_barrel/" + woodName + "_5", modLoc("block/template/firmalife/big_barrel_5"))
                        .texture("0", bbPrefix + "_3_side")
                        .texture("1", bbPrefix + "_0")
                        .texture("2", bbPrefix + "_0_side")
                        .texture("3", bbPrefix + "_1")
                        .texture("4", bbPrefix + "_1_side")
                        .texture("5", bbPrefix + "_2")
                        .texture("6", bbPrefix + "_2_side")
                        .texture("7", bbPrefix + "_3")
                        .texture("8", bbPrefix + "_3_top")
                        .texture("9", bbPrefix + "_0_top")
                        .texture("10", bbPrefix + "_1_top")
                        .texture("11", bbPrefix + "_2_top")
                        .texture("12", logSideTexture);

                // 8. big_barrel_6
                ModelFile bigBarrel6 = models()
                        .withExistingParent("block/big_barrel/" + woodName + "_6", modLoc("block/template/firmalife/big_barrel_6"))
                        .texture("0", bbPrefix + "_3_side")
                        .texture("1", bbPrefix + "_0")
                        .texture("2", bbPrefix + "_0_side")
                        .texture("3", bbPrefix + "_1")
                        .texture("4", bbPrefix + "_1_side")
                        .texture("5", bbPrefix + "_2")
                        .texture("6", bbPrefix + "_2_side")
                        .texture("7", bbPrefix + "_3")
                        .texture("8", bbPrefix + "_3_top")
                        .texture("9", bbPrefix + "_0_top")
                        .texture("10", bbPrefix + "_1_top")
                        .texture("11", bbPrefix + "_2_top")
                        .texture("12", logSideTexture);

                // 9. big_barrel_7
                ModelFile bigBarrel7 = models()
                        .withExistingParent("block/big_barrel/" + woodName + "_7", modLoc("block/template/firmalife/big_barrel_7"))
                        .texture("0", bbPrefix + "_3_side")
                        .texture("1", bbPrefix + "_0")
                        .texture("2", bbPrefix + "_0_side")
                        .texture("3", bbPrefix + "_1")
                        .texture("4", bbPrefix + "_1_side")
                        .texture("5", bbPrefix + "_2")
                        .texture("6", bbPrefix + "_2_side")
                        .texture("7", bbPrefix + "_3")
                        .texture("8", bbPrefix + "_3_top")
                        .texture("9", bbPrefix + "_0_top")
                        .texture("10", bbPrefix + "_1_top")
                        .texture("11", bbPrefix + "_2_top")
                        .texture("12", logSideTexture);

                // 10. big_barrel_item (inventory model)
                ModelFile bigBarrelItem = models()
                        .withExistingParent("block/big_barrel/" + woodName + "_item", modLoc("block/template/firmalife/big_barrel_item"))
                        .texture("0", bbPrefix + "_3_side")
                        .texture("1", bbPrefix + "_0")
                        .texture("2", bbPrefix + "_0_side")
                        .texture("3", bbPrefix + "_1")
                        .texture("4", bbPrefix + "_1_side")
                        .texture("5", bbPrefix + "_2")
                        .texture("6", bbPrefix + "_2_side")
                        .texture("7", bbPrefix + "_3")
                        .texture("8", bbPrefix + "_3_top")
                        .texture("9", bbPrefix + "_0_top")
                        .texture("10", bbPrefix + "_1_top")
                        .texture("11", bbPrefix + "_2_top")
                        .texture("12", logSideTexture);

                getVariantBuilder(kegBlock)
                        // Unsealed (sealed=false)
                        .partialState().with(TwoByTwoCoreBlock.FACING, Direction.EAST).with(KegCoreBlock.SEALED, false)
                        .modelForState().modelFile(bigBarrel0Unsealed).rotationY(90).addModel()
                        .partialState().with(TwoByTwoCoreBlock.FACING, Direction.NORTH).with(KegCoreBlock.SEALED, false)
                        .modelForState().modelFile(bigBarrel0Unsealed).rotationY(0).addModel()
                        .partialState().with(TwoByTwoCoreBlock.FACING, Direction.SOUTH).with(KegCoreBlock.SEALED, false)
                        .modelForState().modelFile(bigBarrel0Unsealed).rotationY(180).addModel()
                        .partialState().with(TwoByTwoCoreBlock.FACING, Direction.WEST).with(KegCoreBlock.SEALED, false)
                        .modelForState().modelFile(bigBarrel0Unsealed).rotationY(270).addModel()

                        // Sealed (sealed=true)
                        .partialState().with(TwoByTwoCoreBlock.FACING, Direction.EAST).with(KegCoreBlock.SEALED, true)
                        .modelForState().modelFile(bigBarrel0Sealed).rotationY(90).addModel()
                        .partialState().with(TwoByTwoCoreBlock.FACING, Direction.NORTH).with(KegCoreBlock.SEALED, true)
                        .modelForState().modelFile(bigBarrel0Sealed).rotationY(0).addModel()
                        .partialState().with(TwoByTwoCoreBlock.FACING, Direction.SOUTH).with(KegCoreBlock.SEALED, true)
                        .modelForState().modelFile(bigBarrel0Sealed).rotationY(180).addModel()
                        .partialState().with(TwoByTwoCoreBlock.FACING, Direction.WEST).with(KegCoreBlock.SEALED, true)
                        .modelForState().modelFile(bigBarrel0Sealed).rotationY(270).addModel();


                var subBuilder = getVariantBuilder(kegSubBlock);

                ModelFile[] partModels = {
                        null,                    // index 0 unused
                        bigBarrel1,
                        bigBarrel2,
                        bigBarrel3,
                        bigBarrel4,
                        bigBarrel5,
                        bigBarrel6,
                        bigBarrel7
                };

                for (int part = 1; part <= 7; part++) {
                    ModelFile model = partModels[part];

                    // East
                    subBuilder.partialState()
                            .with(KegSubBlock.BARREL_PART, part)
                            .with(TwoByTwoSubBlock.FACING, Direction.EAST)
                            .modelForState().modelFile(model).rotationY(90).addModel();

                    // North
                    subBuilder.partialState()
                            .with(KegSubBlock.BARREL_PART, part)
                            .with(TwoByTwoSubBlock.FACING, Direction.NORTH)
                            .modelForState().modelFile(model).rotationY(0).addModel();

                    // South
                    subBuilder.partialState()
                            .with(KegSubBlock.BARREL_PART, part)
                            .with(TwoByTwoSubBlock.FACING, Direction.SOUTH)
                            .modelForState().modelFile(model).rotationY(180).addModel();

                    // West
                    subBuilder.partialState()
                            .with(KegSubBlock.BARREL_PART, part)
                            .with(TwoByTwoSubBlock.FACING, Direction.WEST)
                            .modelForState().modelFile(model).rotationY(270).addModel();
                }
            }
        }
        //Rock
        for (CompatRock rock : CompatRock.VALUES) {
            String rockName = rock.getSerializedName();

            Block looseBlock = ModBlocks.ROCK_BLOCKS.get(rock).get(CompatRock.BlockType.LOOSE).get();
            Block hardenedBlock = ModBlocks.ROCK_BLOCKS.get(rock).get(CompatRock.BlockType.HARDENED).get();
            Block looseCobbleBlock = ModBlocks.ROCK_BLOCKS.get(rock).get(CompatRock.BlockType.LOOSE_COBBLE).get();
            Block hardenedCobbleBlock = ModBlocks.ROCK_BLOCKS.get(rock).get(CompatRock.BlockType.HARDENED_COBBLE).get();

            Block rawBlock = switch (rockName) {
                case "dripstone" -> Blocks.DRIPSTONE_BLOCK;
                default -> BuiltInRegistries.BLOCK.get(ResourceLocation.parse(rockName));
            };

            ResourceLocation rockTexture = switch (rockName) {
                case "basalt" -> mcLoc("block/" + rockName + "_side");
                case "dripstone" -> mcLoc("block/" + rockName + "_block");
                default -> mcLoc("block/" + rockName);
            };

            //TODO - Loose
            ModelFile looseOneModel = switch (rockName) {
                case "andesite", "basalt" -> models()
                        .withExistingParent(("block/loose/" + rockName + "_1"), modLoc("block/template/loose/loose_igneous_extrusive_1"))
                        .texture("texture", rockTexture);
                case "granite", "diorite", "deepslate" -> models()
                        .withExistingParent(("block/loose/" + rockName + "_1"), modLoc("block/template/loose/loose_igneous_intrusive_1"))
                        .texture("texture", rockTexture);
                case "chalk", "tuff", "dripstone" -> models()
                        .withExistingParent(("block/loose/" + rockName + "_1"), modLoc("block/template/loose/loose_sedimentary_1"))
                        .texture("texture", rockTexture);
                default -> models()
                        .withExistingParent(("block/loose/" + rockName + "_1"), modLoc("block/template/loose/loose_metamorphic_1"))
                        .texture("texture", rockTexture);
            };

            ModelFile looseTwoModel = switch (rockName) {
                case "andesite", "basalt" -> models()
                        .withExistingParent(("block/loose/" + rockName + "_2"), modLoc("block/template/loose/loose_igneous_extrusive_2"))
                        .texture("texture", rockTexture);
                case "granite", "diorite", "deepslate" -> models()
                        .withExistingParent(("block/loose/" + rockName + "_2"), modLoc("block/template/loose/loose_igneous_intrusive_2"))
                        .texture("texture", rockTexture);
                case "chalk", "tuff", "dripstone" -> models()
                        .withExistingParent(("block/loose/" + rockName + "_2"), modLoc("block/template/loose/loose_sedimentary_2"))
                        .texture("texture", rockTexture);
                default -> models()
                        .withExistingParent(("block/loose/" + rockName + "_2"), modLoc("block/template/loose/loose_metamorphic_2"))
                        .texture("texture", rockTexture);
            };

            ModelFile looseThreeModel = switch (rockName) {
                case "andesite", "basalt" -> models()
                        .withExistingParent(("block/loose/" + rockName + "_3"), modLoc("block/template/loose/loose_igneous_extrusive_3"))
                        .texture("texture", rockTexture);
                case "granite", "diorite", "deepslate" -> models()
                        .withExistingParent(("block/loose/" + rockName + "_3"), modLoc("block/template/loose/loose_igneous_intrusive_3"))
                        .texture("texture", rockTexture);
                case "chalk", "tuff", "dripstone" -> models()
                        .withExistingParent(("block/loose/" + rockName + "_3"), modLoc("block/template/loose/loose_sedimentary_3"))
                        .texture("texture", rockTexture);
                default -> models()
                        .withExistingParent(("block/loose/" + rockName + "_3"), modLoc("block/template/loose/loose_metamorphic_3"))
                        .texture("texture", rockTexture);
            };

            getVariantBuilder(looseBlock)
                    .forAllStatesExcept(
                            state -> {
                                int count = state.getValue(LooseRockBlock.COUNT);
                                ModelFile model = switch (count) {
                                    case 1 -> looseOneModel;
                                    case 2 -> looseTwoModel;
                                    case 3 -> looseThreeModel;
                                    default -> looseOneModel; // fallback
                                };

                                return new ConfiguredModel[] {
                                        new ConfiguredModel(model, 0, 0,   false),
                                        new ConfiguredModel(model, 0, 90,  false),
                                        new ConfiguredModel(model, 0, 180, false),
                                        new ConfiguredModel(model, 0, 270, false)
                                };
                            },
                            LooseRockBlock.FLUID // ← ignore this property (replace with your actual fluid Property)
                    );

            //TODO - Mossy Loose

            //TODO - Hardened
            ModelFile hardenedModel = switch(rockName) {
                case "dripstone" -> models().withExistingParent(rockName + "_hardened", mcLoc("block/cube_all"))
                        .texture("all", mcLoc("block/" + rockName + "_block"));
                case "basalt" -> models().withExistingParent(rockName + "_hardened", mcLoc("block/cube_column"))
                        .texture("side", mcLoc("block/" + rockName + "_side"))
                        .texture("end", mcLoc("block/" + rockName + "_top"));
                default -> models().withExistingParent(rockName + "_hardened", mcLoc("block/cube_all"))
                        .texture("all", mcLoc("block/" + rockName));
            };

            simpleBlock(hardenedBlock, hardenedModel);

            ModelFile looseCobbledModel = models().withExistingParent("loose_" + rockName + "_cobble", mcLoc("block/cube_all"))
                    .texture("all", modLoc("block/loose_" + rockName + "_cobble"));
            ModelFile hardenedCobbledModel = models().withExistingParent("hardened_" + rockName + "_cobble", mcLoc("block/cube_all"))
                    .texture("all", modLoc("block/loose_" + rockName + "_cobble"));

            simpleBlock(looseCobbleBlock, looseCobbledModel);
            simpleBlock(hardenedCobbleBlock, hardenedCobbledModel);

            if(ModList.get().isLoaded("firmalife")){
                for(Ore.Grade grade : Ore.Grade.values()){
                    Block oreBlock = CompatFLBlocks.CHROMITE_ORES.get(rock).get(grade).get();
                    String gradeName = grade.name().toLowerCase();

                    ResourceLocation overlayTexture = switch(gradeName) {
                        case "poor" -> modLoc("block/template/ore_overlay/poor_chromite");
                        case "normal" -> modLoc("block/template/ore_overlay/normal_chromite");
                        case "rich" -> modLoc("block/template/ore_overlay/rich_chromite");
                        default -> mcLoc("block/acacia_planks");
                    };

                    // Model name: e.g. poor_granite_hematite_ore
                    ModelFile oreModel = models()
                            .withExistingParent(gradeName + "_" + rockName + "_" + "chromite_ore", modLoc("block/template/ore"))
                            .texture("all", rockTexture)
                            .texture("overlay", overlayTexture);

                    FirmaCompat.LOGGER.error("Creating {} ore with {} model", oreBlock, oreModel);
                    simpleBlock(oreBlock, oreModel);
                }
            }
        }

        simpleBlock(ModBlocks.PRIMITIVE_ANVIL.get(),
                models().withExistingParent("primitive_anvil", modLoc("block/template/rock_anvil"))
                        .texture("texture", mcLoc("block/smooth_stone")));

        //TODO - Ore
        // 1. Non-graded ores (normal ores)
        ModBlocks.ORES.forEach((rock, oreMap) -> {
            oreMap.forEach((ore, blockSupplier) -> {
                Block oreBlock = blockSupplier.get(); // or blockSupplier.value() if DeferredBlock
                String rockName = rock.name().toLowerCase();
                String oreName = ore.name().toLowerCase();

                ResourceLocation rockTexture = switch (rockName) {
                    case "basalt" -> mcLoc("block/" + rockName + "_side");
                    case "dripstone" -> mcLoc("block/" + rockName + "_block");
                    default -> mcLoc("block/" + rockName);
                };

                ResourceLocation overlayTexture = modLoc("block/template/ore_overlay/" + oreName);

                // Parent template
                ModelFile oreModel = models()
                        .withExistingParent(rockName + "_" + oreName + "_ore", modLoc("block/template/ore"))
                        .texture("all",   rockTexture)
                        .texture("overlay",   overlayTexture);

                // Generate blockstate + model + item model
                simpleBlock(oreBlock, oreModel);

                // Optional: if you want explicit item model (overrides default simpleBlock behavior)
                // simpleBlockItem(oreBlock, oreModel);
            });
        });

        // ────────────────────────────────────────────────
        // 2. Graded ores (poor/normal/rich)
        ModBlocks.GRADED_ORES.forEach((rock, oreMap) -> {
            oreMap.forEach((ore, gradeMap) -> {
                gradeMap.forEach((grade, blockSupplier) -> {
                    Block oreBlock = blockSupplier.get();
                    String rockName  = rock.name().toLowerCase();
                    String oreName   = ore.name().toLowerCase();
                    String gradeName = grade.name().toLowerCase(); // poor, normal, rich

                    ResourceLocation rockTexture = switch (rockName) {
                        case "basalt" -> mcLoc("block/" + rockName + "_side");
                        case "dripstone" -> mcLoc("block/" + rockName + "_block");
                        default -> mcLoc("block/" + rockName);
                    };

                    ResourceLocation overlayTexture = switch(gradeName) {
                        case "poor" -> modLoc("block/template/ore_overlay/poor_" + oreName);
                        case "normal" -> modLoc("block/template/ore_overlay/normal_" + oreName);
                        case "rich" -> modLoc("block/template/ore_overlay/rich_" + oreName);
                        default -> mcLoc("block/acacia_planks");
                    };

                    // Model name: e.g. poor_granite_hematite_ore
                    ModelFile oreModel = models()
                            .withExistingParent(gradeName + "_" + rockName + "_" + oreName + "_ore", modLoc("block/template/ore"))
                            .texture("all", rockTexture)
                            .texture("overlay", overlayTexture);

                    simpleBlock(oreBlock, oreModel);
                });
            });
        });

        //Gravel Deposits
        Block cassiteriteDepositBlock = ModBlocks.CASSITERITE_GRAVEL_DEPOSIT.get();
        Block copperDepositBlock = ModBlocks.NATIVE_COPPER_GRAVEL_DEPOSIT.get();
        Block silverDepositBlock = ModBlocks.NATIVE_SILVER_GRAVEL_DEPOSIT.get();
        Block goldDepositBlock = ModBlocks.NATIVE_GOLD_GRAVEL_DEPOSIT.get();

        ResourceLocation cassiteriteOverlay = modLoc("block/template/deposit_overlay/cassiterite");
        ResourceLocation copperOverlay = modLoc("block/template/deposit_overlay/native_copper");
        ResourceLocation silverOverlay = modLoc("block/template/deposit_overlay/native_silver");
        ResourceLocation goldOverlay = modLoc("block/template/deposit_overlay/native_gold");

        ModelFile cassiteriteDepositModel = models()
                .withExistingParent("cassiterite_gravel_deposit", modLoc("block/template/ore"))
                .texture("all",   ResourceLocation.withDefaultNamespace("block/gravel"))
                .texture("overlay",   cassiteriteOverlay);
        ModelFile copperDepositModel = models()
                .withExistingParent("native_copper_gravel_deposit", modLoc("block/template/ore"))
                .texture("all",   ResourceLocation.withDefaultNamespace("block/gravel"))
                .texture("overlay",   copperOverlay);
        ModelFile silverDepositModel = models()
                .withExistingParent("native_silver_gravel_deposit", modLoc("block/template/ore"))
                .texture("all",   ResourceLocation.withDefaultNamespace("block/gravel"))
                .texture("overlay",   silverOverlay);
        ModelFile goldDepositModel = models()
                .withExistingParent("native_gold_gravel_deposit", modLoc("block/template/ore"))
                .texture("all",   ResourceLocation.withDefaultNamespace("block/gravel"))
                .texture("overlay",   goldOverlay);

        // Generate blockstate + model + item model
        simpleBlock(cassiteriteDepositBlock, cassiteriteDepositModel);
        simpleBlock(copperDepositBlock, copperDepositModel);
        simpleBlock(silverDepositBlock, silverDepositModel);
        simpleBlock(goldDepositBlock, goldDepositModel);

        //TODO - Aqueduct
        for (CompatBricks brick : CompatBricks.VALUES) {
            String brickIdName = brick.getSingleName();

            Block aqueductBlock = ModBlocks.AQUEDUCTS.get(brick).get();

            ModelFile aqueductBaseModel = models().withExistingParent("block/aqueduct/" + brickIdName + "/base", modLoc("block/template/aqueduct/base"))
                    .texture("texture", ResourceLocation.withDefaultNamespace("block/" + brick.vanillaTexture()))
                    .texture("particle", ResourceLocation.withDefaultNamespace("block/" + brick.vanillaTexture()));
            ModelFile aqueductNorthModel = models().withExistingParent("block/aqueduct/" + brickIdName + "/north", modLoc("block/template/aqueduct/north"))
                    .texture("texture", ResourceLocation.withDefaultNamespace("block/" + brick.vanillaTexture()))
                    .texture("particle", ResourceLocation.withDefaultNamespace("block/" + brick.vanillaTexture()));
            ModelFile aqueductSouthModel = models().withExistingParent("block/aqueduct/" + brickIdName + "/south", modLoc("block/template/aqueduct/south"))
                    .texture("texture", ResourceLocation.withDefaultNamespace("block/" + brick.vanillaTexture()))
                    .texture("particle", ResourceLocation.withDefaultNamespace("block/" + brick.vanillaTexture()));
            ModelFile aqueductEastModel = models().withExistingParent("block/aqueduct/" + brickIdName + "/east", modLoc("block/template/aqueduct/east"))
                    .texture("texture", ResourceLocation.withDefaultNamespace("block/" + brick.vanillaTexture()))
                    .texture("particle", ResourceLocation.withDefaultNamespace("block/" + brick.vanillaTexture()));
            ModelFile aqueductWestModel = models().withExistingParent("block/aqueduct/" + brickIdName + "/west", modLoc("block/template/aqueduct/west"))
                    .texture("texture", ResourceLocation.withDefaultNamespace("block/" + brick.vanillaTexture()))
                    .texture("particle", ResourceLocation.withDefaultNamespace("block/" + brick.vanillaTexture()));

            getMultipartBuilder(aqueductBlock)
                    .part().modelFile(aqueductBaseModel).addModel().end()
                    .part().modelFile(aqueductNorthModel).addModel().condition(BlockStateProperties.NORTH, false).end()
                    .part().modelFile(aqueductSouthModel).addModel().condition(BlockStateProperties.SOUTH, false).end()
                    .part().modelFile(aqueductEastModel).addModel().condition(BlockStateProperties.EAST, false).end()
                    .part().modelFile(aqueductWestModel).addModel().condition(BlockStateProperties.WEST, false).end();
        }

        //Natural Blocks
        Block clayGrassBlock = ModBlocks.CLAY_GRASS_BLOCK.get();
        Block clayDirtBlock = ModBlocks.CLAY_DIRT.get();
        Block clayPodzolBlock = ModBlocks.CLAY_PODZOL.get();
        Block farmlandBlock = ModBlocks.COMPAT_FARMLAND.get();
        Block dryingBrickBlock = ModBlocks.DRYING_MUD_BRICK.get();
        Block kaolinGrassBlock = ModBlocks.KAOLIN_CLAY_GRASS_BLOCK.get();
        Block kaolinDirtBlock = ModBlocks.KAOLIN_CLAY_DIRT.get();
        Block kaolinPodzolBlock = ModBlocks.KAOLIN_CLAY_PODZOL.get();

        ResourceLocation dirtTexture = ResourceLocation.withDefaultNamespace("block/dirt");
        ResourceLocation farmlandTexture = ResourceLocation.withDefaultNamespace("block/farmland");
        ResourceLocation mudTexture = ResourceLocation.withDefaultNamespace("block/mud");
        ResourceLocation mudBricksTexture = ResourceLocation.withDefaultNamespace("block/mud_bricks");
        ResourceLocation grassTopTexture = ResourceLocation.withDefaultNamespace("block/grass_block_top");
        ResourceLocation grassSideTexture = ResourceLocation.withDefaultNamespace("block/grass_block_side");
        ResourceLocation grassBlockOverlayTexture = ResourceLocation.withDefaultNamespace("block/grass_block_side_overlay");
        ResourceLocation podzolTopTexture = ResourceLocation.withDefaultNamespace("block/podzol_top");
        ResourceLocation podzolSideTexture = ResourceLocation.withDefaultNamespace("block/podzol_side");
        ResourceLocation podzolBlockOverlayTexture = ResourceLocation.fromNamespaceAndPath("firma_compat","block/podzol_overlay");
        ResourceLocation clayDirtTexture = ResourceLocation.fromNamespaceAndPath("firma_compat","block/clay_dirt");
        ResourceLocation kaolinDirtTexture = ResourceLocation.fromNamespaceAndPath("firma_compat","block/kaolin_clay");

        ModelFile clayGrassModel = models().withExistingParent("block/clay_grass_block", mcLoc("grass_block"))
                .texture("bottom", clayDirtTexture)
                .texture("top", grassTopTexture)
                .texture("side", clayDirtTexture)
                .texture("overlay", grassBlockOverlayTexture)
                .texture("particle", clayDirtTexture);

        ModelFile clayPodzolModel = models().withExistingParent("block/clay_podzol", mcLoc("grass_block"))
                .texture("bottom", clayDirtTexture)
                .texture("top", podzolTopTexture)
                .texture("side", clayDirtTexture)
                .texture("overlay", podzolBlockOverlayTexture)
                .texture("particle", clayDirtTexture);

        ModelFile kaolinGrassModel = models().withExistingParent("block/kaolin_clay_grass_block", mcLoc("grass_block"))
                .texture("bottom", kaolinDirtTexture)
                .texture("top", grassTopTexture)
                .texture("side", kaolinDirtTexture)
                .texture("overlay", grassBlockOverlayTexture)
                .texture("particle", kaolinDirtTexture);

        ModelFile kaolinPodzolModel = models().withExistingParent("block/kaolin_clay_podzol", mcLoc("grass_block"))
                .texture("bottom", kaolinDirtTexture)
                .texture("top", podzolTopTexture)
                .texture("side", kaolinDirtTexture)
                .texture("overlay", podzolBlockOverlayTexture)
                .texture("particle", kaolinDirtTexture);

        ModelFile clayDirtModel = models().cubeAll("clay_dirt", clayDirtTexture);
        ModelFile kaolinDirtModel = models().cubeAll("kaolin_clay_dirt", kaolinDirtTexture);

        ModelFile farmlandModel = models().withExistingParent("block/farmland", mcLoc("block/farmland"));

        ModelFile brickWet1Model = models().withExistingParent("block/drying_bricks/mud_wet_1", modLoc("block/template/drying_bricks/1"))
                .texture("mud", mudTexture);
        ModelFile brickDry1Model = models().withExistingParent("block/drying_bricks/mud_dry_1", modLoc("block/template/drying_bricks/1"))
                .texture("mud", mudBricksTexture);
        ModelFile brickWet2Model = models().withExistingParent("block/drying_bricks/mud_wet_2", modLoc("block/template/drying_bricks/2"))
                .texture("mud", mudTexture);
        ModelFile brickDry2Model = models().withExistingParent("block/drying_bricks/mud_dry_2", modLoc("block/template/drying_bricks/2"))
                .texture("mud", mudBricksTexture);
        ModelFile brickWet3Model = models().withExistingParent("block/drying_bricks/mud_wet_3", modLoc("block/template/drying_bricks/3"))
                .texture("mud", mudTexture);
        ModelFile brickDry3Model = models().withExistingParent("block/drying_bricks/mud_dry_3", modLoc("block/template/drying_bricks/3"))
                .texture("mud", mudBricksTexture);
        ModelFile brickWet4Model = models().withExistingParent("block/drying_bricks/mud_wet_4", modLoc("block/template/drying_bricks/4"))
                .texture("mud", mudTexture);
        ModelFile brickDry4Model = models().withExistingParent("block/drying_bricks/mud_dry_4", modLoc("block/template/drying_bricks/4"))
                .texture("mud", mudBricksTexture);

        simpleBlock(clayDirtBlock, clayDirtModel);
        simpleBlock(clayGrassBlock, clayGrassModel);
        simpleBlock(clayPodzolBlock, clayPodzolModel);
        simpleBlock(farmlandBlock, farmlandModel);
        simpleBlock(kaolinDirtBlock, kaolinDirtModel);
        simpleBlock(kaolinGrassBlock, kaolinGrassModel);
        simpleBlock(kaolinPodzolBlock, kaolinPodzolModel);

        getVariantBuilder(dryingBrickBlock)
                .partialState().with(DryingBricksBlock.COUNT, 1).with(DryingBricksBlock.DRIED, false)
                .modelForState().modelFile(brickWet1Model).addModel()
                .partialState().with(DryingBricksBlock.COUNT, 1).with(DryingBricksBlock.DRIED, true)
                .modelForState().modelFile(brickDry1Model).addModel()

                .partialState().with(DryingBricksBlock.COUNT, 2).with(DryingBricksBlock.DRIED, false)
                .modelForState().modelFile(brickWet2Model).addModel()
                .partialState().with(DryingBricksBlock.COUNT, 2).with(DryingBricksBlock.DRIED, true)
                .modelForState().modelFile(brickDry2Model).addModel()

                .partialState().with(DryingBricksBlock.COUNT, 3).with(DryingBricksBlock.DRIED, false)
                .modelForState().modelFile(brickWet3Model).addModel()
                .partialState().with(DryingBricksBlock.COUNT, 3).with(DryingBricksBlock.DRIED, true)
                .modelForState().modelFile(brickDry3Model).addModel()

                .partialState().with(DryingBricksBlock.COUNT, 4).with(DryingBricksBlock.DRIED, false)
                .modelForState().modelFile(brickWet4Model).addModel()
                .partialState().with(DryingBricksBlock.COUNT, 4).with(DryingBricksBlock.DRIED, true)
                .modelForState().modelFile(brickDry4Model).addModel();


        //RNR
            for(CompatRock rock : CompatRock.VALUES){
                String rockName = rock.getSerializedName();

                Block flagstoneBlock = RNRCompatBlocks.ROCK_BLOCKS.get(rock).get(CompatRNR.FLAGSTONE).get();
                Block flagstoneStair = RNRCompatBlocks.ROCK_STAIRS.get(rock).get(CompatRNR.FLAGSTONE).get();
                Block flagstoneSlab = RNRCompatBlocks.ROCK_SLABS.get(rock).get(CompatRNR.FLAGSTONE).get();
                Block cobbledBlock = RNRCompatBlocks.ROCK_BLOCKS.get(rock).get(CompatRNR.COBBLED_ROAD).get();
                Block cobbledStair = RNRCompatBlocks.ROCK_STAIRS.get(rock).get(CompatRNR.COBBLED_ROAD).get();
                Block cobbledSlab = RNRCompatBlocks.ROCK_SLABS.get(rock).get(CompatRNR.COBBLED_ROAD).get();
                Block settBlock = RNRCompatBlocks.ROCK_BLOCKS.get(rock).get(CompatRNR.SETT_ROAD).get();
                Block settStair = RNRCompatBlocks.ROCK_STAIRS.get(rock).get(CompatRNR.SETT_ROAD).get();
                Block settSlab = RNRCompatBlocks.ROCK_SLABS.get(rock).get(CompatRNR.SETT_ROAD).get();

                ModelFile flagstoneBlockModel = models().withExistingParent(rockName + "_flagstones", modLoc("block/template/rnr/path_block"))
                        .texture("top", modLoc("block/" + rockName + "_flagstones"))
                        .texture("gravel", mcLoc("block/gravel"));
                ModelFile cobbledBlockModel = models().withExistingParent(rockName + "_cobbled_road", modLoc("block/template/rnr/path_block"))
                        .texture("top", modLoc("block/loose_" + rockName + "_cobble"))
                        .texture("gravel", mcLoc("block/gravel"));
                ModelFile settBlockModel = models().withExistingParent(rockName + "_sett_road", modLoc("block/template/rnr/path_block"))
                        .texture("top", modLoc("block/" + rockName + "_sett_road"))
                        .texture("gravel", mcLoc("block/gravel"));

                ModelFile flagstoneSlabModel = models().withExistingParent(rockName + "_flagstone_slab", modLoc("block/template/rnr/path_slab"))
                        .texture("top", modLoc("block/" + rockName + "_flagstones"))
                        .texture("gravel", mcLoc("block/gravel"));
                ModelFile cobbledSlabModel = models().withExistingParent(rockName + "_cobbled_road_slab", modLoc("block/template/rnr/path_slab"))
                        .texture("top", modLoc("block/loose_" + rockName + "_cobble"))
                        .texture("gravel", mcLoc("block/gravel"));
                ModelFile settSlabModel = models().withExistingParent(rockName + "_sett_road_slab", modLoc("block/template/rnr/path_slab"))
                        .texture("top", modLoc("block/" + rockName + "_sett_road"))
                        .texture("gravel", mcLoc("block/gravel"));

                ModelFile flagstoneStairsModel = models().withExistingParent(rockName + "_flagstone_stairs", modLoc("block/template/rnr/path_stairs"))
                        .texture("top", modLoc("block/" + rockName + "_flagstones"))
                        .texture("side", modLoc("block/" + rockName + "_flagstones"))
                        .texture("bottom", modLoc("block/" + rockName + "_flagstones"));
                ModelFile cobbledStairsModel = models().withExistingParent(rockName + "_cobbled_road_stairs", modLoc("block/template/rnr/path_stairs"))
                        .texture("top", modLoc("block/loose_" + rockName + "_cobble"))
                        .texture("side", modLoc("block/loose_" + rockName + "_cobble"))
                        .texture("bottom", modLoc("block/loose_" + rockName + "_cobble"));
                ModelFile settStairsModel = models().withExistingParent(rockName + "_sett_road_stairs", modLoc("block/template/rnr/path_stairs"))
                        .texture("top", modLoc("block/" + rockName + "_sett_road"))
                        .texture("side", modLoc("block/" + rockName + "_sett_road"))
                        .texture("bottom", modLoc("block/" + rockName + "_sett_road"));

                ModelFile flagstoneStairsInnerModel = models().withExistingParent(rockName + "_flagstone_inner_stairs", modLoc("block/template/rnr/path_inner_stairs"))
                        .texture("top", modLoc("block/" + rockName + "_flagstones"))
                        .texture("side", modLoc("block/" + rockName + "_flagstones"))
                        .texture("bottom", modLoc("block/" + rockName + "_flagstones"));
                ModelFile cobbledStairsInnerModel = models().withExistingParent(rockName + "_cobbled_road_inner_stairs", modLoc("block/template/rnr/path_inner_stairs"))
                        .texture("top", modLoc("block/loose_" + rockName + "_cobble"))
                        .texture("side", modLoc("block/loose_" + rockName + "_cobble"))
                        .texture("bottom", modLoc("block/loose_" + rockName + "_cobble"));
                ModelFile settStairsInnerModel = models().withExistingParent(rockName + "_sett_road_inner_stairs", modLoc("block/template/rnr/path_inner_stairs"))
                        .texture("top", modLoc("block/" + rockName + "_sett_road"))
                        .texture("side", modLoc("block/" + rockName + "_sett_road"))
                        .texture("bottom", modLoc("block/" + rockName + "_sett_road"));

                ModelFile flagstoneStairsOuterModel = models().withExistingParent(rockName + "_flagstone_outer_stairs", modLoc("block/template/rnr/path_outer_stairs"))
                        .texture("top", modLoc("block/" + rockName + "_flagstones"))
                        .texture("side", modLoc("block/" + rockName + "_flagstones"))
                        .texture("bottom", modLoc("block/" + rockName + "_flagstones"));
                ModelFile cobbledStairsOuterModel = models().withExistingParent(rockName + "_cobbled_road_outer_stairs", modLoc("block/template/rnr/path_outer_stairs"))
                        .texture("top", modLoc("block/loose_" + rockName + "_cobble"))
                        .texture("side", modLoc("block/loose_" + rockName + "_cobble"))
                        .texture("bottom", modLoc("block/loose_" + rockName + "_cobble"));
                ModelFile settStairsOuterModel = models().withExistingParent(rockName + "_sett_road_outer_stairs", modLoc("block/template/rnr/path_outer_stairs"))
                        .texture("top", modLoc("block/" + rockName + "_sett_road"))
                        .texture("side", modLoc("block/" + rockName + "_sett_road"))
                        .texture("bottom", modLoc("block/" + rockName + "_sett_road"));

                simpleBlock(flagstoneBlock, flagstoneBlockModel);
                simpleBlock(flagstoneSlab, flagstoneSlabModel);
                simpleBlock(cobbledBlock, cobbledBlockModel);
                simpleBlock(cobbledSlab, cobbledSlabModel);
                simpleBlock(settBlock, settBlockModel);
                simpleBlock(settSlab, settSlabModel);

                VariantBlockStateBuilder flagstoneBuilder = getVariantBuilder(flagstoneStair);
                flagstoneBuilder.partialState().with(StairBlock.FACING, Direction.EAST).with(StairBlock.SHAPE, StairsShape.STRAIGHT)
                        .modelForState().modelFile(flagstoneStairsModel).rotationY(0).uvLock(true).addModel();
                flagstoneBuilder.partialState().with(StairBlock.FACING, Direction.WEST).with(StairBlock.SHAPE, StairsShape.STRAIGHT)
                        .modelForState().modelFile(flagstoneStairsModel).rotationY(180).uvLock(true).addModel();
                flagstoneBuilder.partialState().with(StairBlock.FACING, Direction.SOUTH).with(StairBlock.SHAPE, StairsShape.STRAIGHT)
                        .modelForState().modelFile(flagstoneStairsModel).rotationY(90).uvLock(true).addModel();
                flagstoneBuilder.partialState().with(StairBlock.FACING, Direction.NORTH).with(StairBlock.SHAPE, StairsShape.STRAIGHT)
                        .modelForState().modelFile(flagstoneStairsModel).rotationY(270).uvLock(true).addModel();

                flagstoneBuilder.partialState().with(StairBlock.FACING, Direction.EAST).with(StairBlock.SHAPE, StairsShape.OUTER_RIGHT)
                        .modelForState().modelFile(flagstoneStairsOuterModel).rotationY(0).uvLock(true).addModel();
                flagstoneBuilder.partialState().with(StairBlock.FACING, Direction.WEST).with(StairBlock.SHAPE, StairsShape.OUTER_RIGHT)
                        .modelForState().modelFile(flagstoneStairsOuterModel).rotationY(180).uvLock(true).addModel();
                flagstoneBuilder.partialState().with(StairBlock.FACING, Direction.SOUTH).with(StairBlock.SHAPE, StairsShape.OUTER_RIGHT)
                        .modelForState().modelFile(flagstoneStairsOuterModel).rotationY(90).uvLock(true).addModel();
                flagstoneBuilder.partialState().with(StairBlock.FACING, Direction.NORTH).with(StairBlock.SHAPE, StairsShape.OUTER_RIGHT)
                        .modelForState().modelFile(flagstoneStairsOuterModel).rotationY(270).uvLock(true).addModel();
                flagstoneBuilder.partialState().with(StairBlock.FACING, Direction.EAST).with(StairBlock.SHAPE, StairsShape.OUTER_LEFT)
                        .modelForState().modelFile(flagstoneStairsOuterModel).rotationY(270).uvLock(true).addModel();
                flagstoneBuilder.partialState().with(StairBlock.FACING, Direction.WEST).with(StairBlock.SHAPE, StairsShape.OUTER_LEFT)
                        .modelForState().modelFile(flagstoneStairsOuterModel).rotationY(90).uvLock(true).addModel();
                flagstoneBuilder.partialState().with(StairBlock.FACING, Direction.SOUTH).with(StairBlock.SHAPE, StairsShape.OUTER_LEFT)
                        .modelForState().modelFile(flagstoneStairsOuterModel).rotationY(0).uvLock(true).addModel();
                flagstoneBuilder.partialState().with(StairBlock.FACING, Direction.NORTH).with(StairBlock.SHAPE, StairsShape.OUTER_LEFT)
                        .modelForState().modelFile(flagstoneStairsOuterModel).rotationY(180).uvLock(true).addModel();

                flagstoneBuilder.partialState().with(StairBlock.FACING, Direction.EAST).with(StairBlock.SHAPE, StairsShape.INNER_RIGHT)
                        .modelForState().modelFile(flagstoneStairsInnerModel).rotationY(0).uvLock(true).addModel();
                flagstoneBuilder.partialState().with(StairBlock.FACING, Direction.WEST).with(StairBlock.SHAPE, StairsShape.INNER_RIGHT)
                        .modelForState().modelFile(flagstoneStairsInnerModel).rotationY(180).uvLock(true).addModel();
                flagstoneBuilder.partialState().with(StairBlock.FACING, Direction.SOUTH).with(StairBlock.SHAPE, StairsShape.INNER_RIGHT)
                        .modelForState().modelFile(flagstoneStairsInnerModel).rotationY(90).uvLock(true).addModel();
                flagstoneBuilder.partialState().with(StairBlock.FACING, Direction.NORTH).with(StairBlock.SHAPE, StairsShape.INNER_RIGHT)
                        .modelForState().modelFile(flagstoneStairsInnerModel).rotationY(270).uvLock(true).addModel();
                flagstoneBuilder.partialState().with(StairBlock.FACING, Direction.EAST).with(StairBlock.SHAPE, StairsShape.INNER_LEFT)
                        .modelForState().modelFile(flagstoneStairsInnerModel).rotationY(270).uvLock(true).addModel();
                flagstoneBuilder.partialState().with(StairBlock.FACING, Direction.WEST).with(StairBlock.SHAPE, StairsShape.INNER_LEFT)
                        .modelForState().modelFile(flagstoneStairsInnerModel).rotationY(90).uvLock(true).addModel();
                flagstoneBuilder.partialState().with(StairBlock.FACING, Direction.SOUTH).with(StairBlock.SHAPE, StairsShape.INNER_LEFT)
                        .modelForState().modelFile(flagstoneStairsInnerModel).rotationY(0).uvLock(true).addModel();
                flagstoneBuilder.partialState().with(StairBlock.FACING, Direction.NORTH).with(StairBlock.SHAPE, StairsShape.INNER_LEFT)
                        .modelForState().modelFile(flagstoneStairsInnerModel).rotationY(180).uvLock(true).addModel();

                VariantBlockStateBuilder cobbledBuilder = getVariantBuilder(cobbledStair);
                cobbledBuilder.partialState().with(StairBlock.FACING, Direction.EAST).with(StairBlock.SHAPE, StairsShape.STRAIGHT)
                        .modelForState().modelFile(cobbledStairsModel).rotationY(0).uvLock(true).addModel();
                cobbledBuilder.partialState().with(StairBlock.FACING, Direction.WEST).with(StairBlock.SHAPE, StairsShape.STRAIGHT)
                        .modelForState().modelFile(cobbledStairsModel).rotationY(180).uvLock(true).addModel();
                cobbledBuilder.partialState().with(StairBlock.FACING, Direction.SOUTH).with(StairBlock.SHAPE, StairsShape.STRAIGHT)
                        .modelForState().modelFile(cobbledStairsModel).rotationY(90).uvLock(true).addModel();
                cobbledBuilder.partialState().with(StairBlock.FACING, Direction.NORTH).with(StairBlock.SHAPE, StairsShape.STRAIGHT)
                        .modelForState().modelFile(cobbledStairsModel).rotationY(270).uvLock(true).addModel();

                cobbledBuilder.partialState().with(StairBlock.FACING, Direction.EAST).with(StairBlock.SHAPE, StairsShape.OUTER_RIGHT)
                        .modelForState().modelFile(cobbledStairsOuterModel).rotationY(0).uvLock(true).addModel();
                cobbledBuilder.partialState().with(StairBlock.FACING, Direction.WEST).with(StairBlock.SHAPE, StairsShape.OUTER_RIGHT)
                        .modelForState().modelFile(cobbledStairsOuterModel).rotationY(180).uvLock(true).addModel();
                cobbledBuilder.partialState().with(StairBlock.FACING, Direction.SOUTH).with(StairBlock.SHAPE, StairsShape.OUTER_RIGHT)
                        .modelForState().modelFile(cobbledStairsOuterModel).rotationY(90).uvLock(true).addModel();
                cobbledBuilder.partialState().with(StairBlock.FACING, Direction.NORTH).with(StairBlock.SHAPE, StairsShape.OUTER_RIGHT)
                        .modelForState().modelFile(cobbledStairsOuterModel).rotationY(270).uvLock(true).addModel();
                cobbledBuilder.partialState().with(StairBlock.FACING, Direction.EAST).with(StairBlock.SHAPE, StairsShape.OUTER_LEFT)
                        .modelForState().modelFile(cobbledStairsOuterModel).rotationY(270).uvLock(true).addModel();
                cobbledBuilder.partialState().with(StairBlock.FACING, Direction.WEST).with(StairBlock.SHAPE, StairsShape.OUTER_LEFT)
                        .modelForState().modelFile(cobbledStairsOuterModel).rotationY(90).uvLock(true).addModel();
                cobbledBuilder.partialState().with(StairBlock.FACING, Direction.SOUTH).with(StairBlock.SHAPE, StairsShape.OUTER_LEFT)
                        .modelForState().modelFile(cobbledStairsOuterModel).rotationY(0).uvLock(true).addModel();
                cobbledBuilder.partialState().with(StairBlock.FACING, Direction.NORTH).with(StairBlock.SHAPE, StairsShape.OUTER_LEFT)
                        .modelForState().modelFile(cobbledStairsOuterModel).rotationY(180).uvLock(true).addModel();

                cobbledBuilder.partialState().with(StairBlock.FACING, Direction.EAST).with(StairBlock.SHAPE, StairsShape.INNER_RIGHT)
                        .modelForState().modelFile(cobbledStairsInnerModel).rotationY(0).uvLock(true).addModel();
                cobbledBuilder.partialState().with(StairBlock.FACING, Direction.WEST).with(StairBlock.SHAPE, StairsShape.INNER_RIGHT)
                        .modelForState().modelFile(cobbledStairsInnerModel).rotationY(180).uvLock(true).addModel();
                cobbledBuilder.partialState().with(StairBlock.FACING, Direction.SOUTH).with(StairBlock.SHAPE, StairsShape.INNER_RIGHT)
                        .modelForState().modelFile(cobbledStairsInnerModel).rotationY(90).uvLock(true).addModel();
                cobbledBuilder.partialState().with(StairBlock.FACING, Direction.NORTH).with(StairBlock.SHAPE, StairsShape.INNER_RIGHT)
                        .modelForState().modelFile(cobbledStairsInnerModel).rotationY(270).uvLock(true).addModel();
                cobbledBuilder.partialState().with(StairBlock.FACING, Direction.EAST).with(StairBlock.SHAPE, StairsShape.INNER_LEFT)
                        .modelForState().modelFile(cobbledStairsInnerModel).rotationY(270).uvLock(true).addModel();
                cobbledBuilder.partialState().with(StairBlock.FACING, Direction.WEST).with(StairBlock.SHAPE, StairsShape.INNER_LEFT)
                        .modelForState().modelFile(cobbledStairsInnerModel).rotationY(90).uvLock(true).addModel();
                cobbledBuilder.partialState().with(StairBlock.FACING, Direction.SOUTH).with(StairBlock.SHAPE, StairsShape.INNER_LEFT)
                        .modelForState().modelFile(cobbledStairsInnerModel).rotationY(0).uvLock(true).addModel();
                cobbledBuilder.partialState().with(StairBlock.FACING, Direction.NORTH).with(StairBlock.SHAPE, StairsShape.INNER_LEFT)
                        .modelForState().modelFile(cobbledStairsInnerModel).rotationY(180).uvLock(true).addModel();

                VariantBlockStateBuilder settBuilder = getVariantBuilder(settStair);
                settBuilder.partialState().with(StairBlock.FACING, Direction.EAST).with(StairBlock.SHAPE, StairsShape.STRAIGHT)
                        .modelForState().modelFile(settStairsModel).rotationY(0).uvLock(true).addModel();
                settBuilder.partialState().with(StairBlock.FACING, Direction.WEST).with(StairBlock.SHAPE, StairsShape.STRAIGHT)
                        .modelForState().modelFile(settStairsModel).rotationY(180).uvLock(true).addModel();
                settBuilder.partialState().with(StairBlock.FACING, Direction.SOUTH).with(StairBlock.SHAPE, StairsShape.STRAIGHT)
                        .modelForState().modelFile(settStairsModel).rotationY(90).uvLock(true).addModel();
                settBuilder.partialState().with(StairBlock.FACING, Direction.NORTH).with(StairBlock.SHAPE, StairsShape.STRAIGHT)
                        .modelForState().modelFile(settStairsModel).rotationY(270).uvLock(true).addModel();

                settBuilder.partialState().with(StairBlock.FACING, Direction.EAST).with(StairBlock.SHAPE, StairsShape.OUTER_RIGHT)
                        .modelForState().modelFile(settStairsOuterModel).rotationY(0).uvLock(true).addModel();
                settBuilder.partialState().with(StairBlock.FACING, Direction.WEST).with(StairBlock.SHAPE, StairsShape.OUTER_RIGHT)
                        .modelForState().modelFile(settStairsOuterModel).rotationY(180).uvLock(true).addModel();
                settBuilder.partialState().with(StairBlock.FACING, Direction.SOUTH).with(StairBlock.SHAPE, StairsShape.OUTER_RIGHT)
                        .modelForState().modelFile(settStairsOuterModel).rotationY(90).uvLock(true).addModel();
                settBuilder.partialState().with(StairBlock.FACING, Direction.NORTH).with(StairBlock.SHAPE, StairsShape.OUTER_RIGHT)
                        .modelForState().modelFile(settStairsOuterModel).rotationY(270).uvLock(true).addModel();
                settBuilder.partialState().with(StairBlock.FACING, Direction.EAST).with(StairBlock.SHAPE, StairsShape.OUTER_LEFT)
                        .modelForState().modelFile(settStairsOuterModel).rotationY(270).uvLock(true).addModel();
                settBuilder.partialState().with(StairBlock.FACING, Direction.WEST).with(StairBlock.SHAPE, StairsShape.OUTER_LEFT)
                        .modelForState().modelFile(settStairsOuterModel).rotationY(90).uvLock(true).addModel();
                settBuilder.partialState().with(StairBlock.FACING, Direction.SOUTH).with(StairBlock.SHAPE, StairsShape.OUTER_LEFT)
                        .modelForState().modelFile(settStairsOuterModel).rotationY(0).uvLock(true).addModel();
                settBuilder.partialState().with(StairBlock.FACING, Direction.NORTH).with(StairBlock.SHAPE, StairsShape.OUTER_LEFT)
                        .modelForState().modelFile(settStairsOuterModel).rotationY(180).uvLock(true).addModel();

                settBuilder.partialState().with(StairBlock.FACING, Direction.EAST).with(StairBlock.SHAPE, StairsShape.INNER_RIGHT)
                        .modelForState().modelFile(settStairsInnerModel).rotationY(0).uvLock(true).addModel();
                settBuilder.partialState().with(StairBlock.FACING, Direction.WEST).with(StairBlock.SHAPE, StairsShape.INNER_RIGHT)
                        .modelForState().modelFile(settStairsInnerModel).rotationY(180).uvLock(true).addModel();
                settBuilder.partialState().with(StairBlock.FACING, Direction.SOUTH).with(StairBlock.SHAPE, StairsShape.INNER_RIGHT)
                        .modelForState().modelFile(settStairsInnerModel).rotationY(90).uvLock(true).addModel();
                settBuilder.partialState().with(StairBlock.FACING, Direction.NORTH).with(StairBlock.SHAPE, StairsShape.INNER_RIGHT)
                        .modelForState().modelFile(settStairsInnerModel).rotationY(270).uvLock(true).addModel();
                settBuilder.partialState().with(StairBlock.FACING, Direction.EAST).with(StairBlock.SHAPE, StairsShape.INNER_LEFT)
                        .modelForState().modelFile(settStairsInnerModel).rotationY(270).uvLock(true).addModel();
                settBuilder.partialState().with(StairBlock.FACING, Direction.WEST).with(StairBlock.SHAPE, StairsShape.INNER_LEFT)
                        .modelForState().modelFile(settStairsInnerModel).rotationY(90).uvLock(true).addModel();
                settBuilder.partialState().with(StairBlock.FACING, Direction.SOUTH).with(StairBlock.SHAPE, StairsShape.INNER_LEFT)
                        .modelForState().modelFile(settStairsInnerModel).rotationY(0).uvLock(true).addModel();
                settBuilder.partialState().with(StairBlock.FACING, Direction.NORTH).with(StairBlock.SHAPE, StairsShape.INNER_LEFT)
                        .modelForState().modelFile(settStairsInnerModel).rotationY(180).uvLock(true).addModel();

            }
            Block overheightGravelBlock = RNRCompatBlocks.OVER_HEIGHT_GRAVEL.get();
            Block tampedDirtBlock = RNRCompatBlocks.TAMPED_DIRT.get();
            Block tampedMudBlock = RNRCompatBlocks.TAMPED_MUD.get();

            Block gravelBlock = RNRCompatBlocks.GRAVEL_ROAD.get();
            Block gravelStairsBlock = RNRCompatBlocks.GRAVEL_ROAD_STAIRS.get();
            Block gravelSlabBlock = RNRCompatBlocks.GRAVEL_ROAD_SLAB.get();
            Block macadamBlock = RNRCompatBlocks.MACADAM_ROAD.get();
            Block macadamStairsBlock = RNRCompatBlocks.MACADAM_ROAD_STAIRS.get();
            Block macadamSlabBlock = RNRCompatBlocks.MACADAM_ROAD_SLAB.get();

            ModelFile overheightBlockModel = models().withExistingParent("overheight_gravel", modLoc("block/template/rnr/overfilled_block"))
                    .texture("top", mcLoc("block/gravel"))
                    .texture("gravel", mcLoc("block/gravel"));
            ModelFile tampedDirtBlockModel = models().withExistingParent("tamped_dirt", modLoc("block/template/rnr/tamped_block"))
                    .texture("dirt", mcLoc("block/dirt"));
            ModelFile tampedMudBlockModel = models().withExistingParent("tamped_mud", modLoc("block/template/rnr/tamped_block"))
                    .texture("dirt", mcLoc("block/mud"));

            ModelFile gravelBlockModel = models().withExistingParent("gravel_road", modLoc("block/template/rnr/path_block"))
                    .texture("top", mcLoc("block/gravel"))
                    .texture("gravel", mcLoc("block/gravel"));
            ModelFile gravelSlabModel = models().withExistingParent("gravel_road_slab", modLoc("block/template/rnr/path_slab"))
                    .texture("top", mcLoc("block/gravel"))
                    .texture("gravel", mcLoc("block/gravel"));
            ModelFile gravelStairsModel = models().withExistingParent("gravel_road_stairs", modLoc("block/template/rnr/path_stairs"))
                    .texture("top", mcLoc("block/gravel"))
                    .texture("side", mcLoc("block/gravel"))
                    .texture("bottom", mcLoc("block/gravel"));
            ModelFile gravelStairsInnerModel = models().withExistingParent("gravel_road_inner_stairs", modLoc("block/template/rnr/path_inner_stairs"))
                    .texture("top", mcLoc("block/gravel"))
                    .texture("side", mcLoc("block/gravel"))
                    .texture("bottom", mcLoc("block/gravel"));
            ModelFile gravelStairsOuterModel = models().withExistingParent("gravel_road_outer_stairs", modLoc("block/template/rnr/path_outer_stairs"))
                    .texture("top", mcLoc("block/gravel"))
                    .texture("side", mcLoc("block/gravel"))
                    .texture("bottom", mcLoc("block/gravel"));

            ModelFile macadamBlockModel = models().withExistingParent("macadam_road", modLoc("block/template/rnr/path_block"))
                    .texture("top", mcLoc("block/gravel"))
                    .texture("gravel", mcLoc("block/gravel"));
            ModelFile macadamSlabModel = models().withExistingParent("macadam_road_slab", modLoc("block/template/rnr/path_slab"))
                    .texture("top", mcLoc("block/gravel"))
                    .texture("gravel", mcLoc("block/gravel"));
            ModelFile macadamStairsModel = models().withExistingParent("macadam_road_stairs", modLoc("block/template/rnr/path_stairs"))
                    .texture("top", mcLoc("block/gravel"))
                    .texture("side", mcLoc("block/gravel"))
                    .texture("bottom", mcLoc("block/gravel"));
            ModelFile macadamStairsInnerModel = models().withExistingParent("macadam_road_inner_stairs", modLoc("block/template/rnr/path_inner_stairs"))
                    .texture("top", mcLoc("block/gravel"))
                    .texture("side", mcLoc("block/gravel"))
                    .texture("bottom", mcLoc("block/gravel"));
            ModelFile macadamStairsOuterModel = models().withExistingParent("macadam_road_outer_stairs", modLoc("block/template/rnr/path_outer_stairs"))
                    .texture("top", mcLoc("block/gravel"))
                    .texture("side", mcLoc("block/gravel"))
                    .texture("bottom", mcLoc("block/gravel"));

            simpleBlock(overheightGravelBlock, overheightBlockModel);
            simpleBlock(tampedDirtBlock, tampedDirtBlockModel);
            simpleBlock(tampedMudBlock, tampedMudBlockModel);

            simpleBlock(gravelBlock, gravelBlockModel);
            simpleBlock(gravelSlabBlock, gravelSlabModel);

            simpleBlock(macadamBlock, macadamBlockModel);
            simpleBlock(macadamSlabBlock, macadamSlabModel);

            VariantBlockStateBuilder gravelBuilder = getVariantBuilder(gravelStairsBlock);
            gravelBuilder.partialState().with(StairBlock.FACING, Direction.EAST).with(StairBlock.SHAPE, StairsShape.STRAIGHT)
                    .modelForState().modelFile(gravelStairsModel).rotationY(0).uvLock(true).addModel();
            gravelBuilder.partialState().with(StairBlock.FACING, Direction.WEST).with(StairBlock.SHAPE, StairsShape.STRAIGHT)
                    .modelForState().modelFile(gravelStairsModel).rotationY(180).uvLock(true).addModel();
            gravelBuilder.partialState().with(StairBlock.FACING, Direction.SOUTH).with(StairBlock.SHAPE, StairsShape.STRAIGHT)
                    .modelForState().modelFile(gravelStairsModel).rotationY(90).uvLock(true).addModel();
            gravelBuilder.partialState().with(StairBlock.FACING, Direction.NORTH).with(StairBlock.SHAPE, StairsShape.STRAIGHT)
                    .modelForState().modelFile(gravelStairsModel).rotationY(270).uvLock(true).addModel();

            gravelBuilder.partialState().with(StairBlock.FACING, Direction.EAST).with(StairBlock.SHAPE, StairsShape.OUTER_RIGHT)
                    .modelForState().modelFile(gravelStairsOuterModel).rotationY(0).uvLock(true).addModel();
            gravelBuilder.partialState().with(StairBlock.FACING, Direction.WEST).with(StairBlock.SHAPE, StairsShape.OUTER_RIGHT)
                    .modelForState().modelFile(gravelStairsOuterModel).rotationY(180).uvLock(true).addModel();
            gravelBuilder.partialState().with(StairBlock.FACING, Direction.SOUTH).with(StairBlock.SHAPE, StairsShape.OUTER_RIGHT)
                    .modelForState().modelFile(gravelStairsOuterModel).rotationY(90).uvLock(true).addModel();
            gravelBuilder.partialState().with(StairBlock.FACING, Direction.NORTH).with(StairBlock.SHAPE, StairsShape.OUTER_RIGHT)
                    .modelForState().modelFile(gravelStairsOuterModel).rotationY(270).uvLock(true).addModel();
            gravelBuilder.partialState().with(StairBlock.FACING, Direction.EAST).with(StairBlock.SHAPE, StairsShape.OUTER_LEFT)
                    .modelForState().modelFile(gravelStairsOuterModel).rotationY(270).uvLock(true).addModel();
            gravelBuilder.partialState().with(StairBlock.FACING, Direction.WEST).with(StairBlock.SHAPE, StairsShape.OUTER_LEFT)
                    .modelForState().modelFile(gravelStairsOuterModel).rotationY(90).uvLock(true).addModel();
            gravelBuilder.partialState().with(StairBlock.FACING, Direction.SOUTH).with(StairBlock.SHAPE, StairsShape.OUTER_LEFT)
                    .modelForState().modelFile(gravelStairsOuterModel).rotationY(0).uvLock(true).addModel();
            gravelBuilder.partialState().with(StairBlock.FACING, Direction.NORTH).with(StairBlock.SHAPE, StairsShape.OUTER_LEFT)
                    .modelForState().modelFile(gravelStairsOuterModel).rotationY(180).uvLock(true).addModel();

            gravelBuilder.partialState().with(StairBlock.FACING, Direction.EAST).with(StairBlock.SHAPE, StairsShape.INNER_RIGHT)
                    .modelForState().modelFile(gravelStairsInnerModel).rotationY(0).uvLock(true).addModel();
            gravelBuilder.partialState().with(StairBlock.FACING, Direction.WEST).with(StairBlock.SHAPE, StairsShape.INNER_RIGHT)
                    .modelForState().modelFile(gravelStairsInnerModel).rotationY(180).uvLock(true).addModel();
            gravelBuilder.partialState().with(StairBlock.FACING, Direction.SOUTH).with(StairBlock.SHAPE, StairsShape.INNER_RIGHT)
                    .modelForState().modelFile(gravelStairsInnerModel).rotationY(90).uvLock(true).addModel();
            gravelBuilder.partialState().with(StairBlock.FACING, Direction.NORTH).with(StairBlock.SHAPE, StairsShape.INNER_RIGHT)
                    .modelForState().modelFile(gravelStairsInnerModel).rotationY(270).uvLock(true).addModel();
            gravelBuilder.partialState().with(StairBlock.FACING, Direction.EAST).with(StairBlock.SHAPE, StairsShape.INNER_LEFT)
                    .modelForState().modelFile(gravelStairsInnerModel).rotationY(270).uvLock(true).addModel();
            gravelBuilder.partialState().with(StairBlock.FACING, Direction.WEST).with(StairBlock.SHAPE, StairsShape.INNER_LEFT)
                    .modelForState().modelFile(gravelStairsInnerModel).rotationY(90).uvLock(true).addModel();
            gravelBuilder.partialState().with(StairBlock.FACING, Direction.SOUTH).with(StairBlock.SHAPE, StairsShape.INNER_LEFT)
                    .modelForState().modelFile(gravelStairsInnerModel).rotationY(0).uvLock(true).addModel();
            gravelBuilder.partialState().with(StairBlock.FACING, Direction.NORTH).with(StairBlock.SHAPE, StairsShape.INNER_LEFT)
                    .modelForState().modelFile(gravelStairsInnerModel).rotationY(180).uvLock(true).addModel();

            VariantBlockStateBuilder macadamBuilder = getVariantBuilder(macadamStairsBlock);
            macadamBuilder.partialState().with(StairBlock.FACING, Direction.EAST).with(StairBlock.SHAPE, StairsShape.STRAIGHT)
                    .modelForState().modelFile(macadamStairsModel).rotationY(0).uvLock(true).addModel();
            macadamBuilder.partialState().with(StairBlock.FACING, Direction.WEST).with(StairBlock.SHAPE, StairsShape.STRAIGHT)
                    .modelForState().modelFile(macadamStairsModel).rotationY(180).uvLock(true).addModel();
            macadamBuilder.partialState().with(StairBlock.FACING, Direction.SOUTH).with(StairBlock.SHAPE, StairsShape.STRAIGHT)
                    .modelForState().modelFile(macadamStairsModel).rotationY(90).uvLock(true).addModel();
            macadamBuilder.partialState().with(StairBlock.FACING, Direction.NORTH).with(StairBlock.SHAPE, StairsShape.STRAIGHT)
                    .modelForState().modelFile(macadamStairsModel).rotationY(270).uvLock(true).addModel();

            macadamBuilder.partialState().with(StairBlock.FACING, Direction.EAST).with(StairBlock.SHAPE, StairsShape.OUTER_RIGHT)
                    .modelForState().modelFile(macadamStairsOuterModel).rotationY(0).uvLock(true).addModel();
            macadamBuilder.partialState().with(StairBlock.FACING, Direction.WEST).with(StairBlock.SHAPE, StairsShape.OUTER_RIGHT)
                    .modelForState().modelFile(macadamStairsOuterModel).rotationY(180).uvLock(true).addModel();
            macadamBuilder.partialState().with(StairBlock.FACING, Direction.SOUTH).with(StairBlock.SHAPE, StairsShape.OUTER_RIGHT)
                    .modelForState().modelFile(macadamStairsOuterModel).rotationY(90).uvLock(true).addModel();
            macadamBuilder.partialState().with(StairBlock.FACING, Direction.NORTH).with(StairBlock.SHAPE, StairsShape.OUTER_RIGHT)
                    .modelForState().modelFile(macadamStairsOuterModel).rotationY(270).uvLock(true).addModel();
            macadamBuilder.partialState().with(StairBlock.FACING, Direction.EAST).with(StairBlock.SHAPE, StairsShape.OUTER_LEFT)
                    .modelForState().modelFile(macadamStairsOuterModel).rotationY(270).uvLock(true).addModel();
            macadamBuilder.partialState().with(StairBlock.FACING, Direction.WEST).with(StairBlock.SHAPE, StairsShape.OUTER_LEFT)
                    .modelForState().modelFile(macadamStairsOuterModel).rotationY(90).uvLock(true).addModel();
            macadamBuilder.partialState().with(StairBlock.FACING, Direction.SOUTH).with(StairBlock.SHAPE, StairsShape.OUTER_LEFT)
                    .modelForState().modelFile(macadamStairsOuterModel).rotationY(0).uvLock(true).addModel();
            macadamBuilder.partialState().with(StairBlock.FACING, Direction.NORTH).with(StairBlock.SHAPE, StairsShape.OUTER_LEFT)
                    .modelForState().modelFile(macadamStairsOuterModel).rotationY(180).uvLock(true).addModel();

            macadamBuilder.partialState().with(StairBlock.FACING, Direction.EAST).with(StairBlock.SHAPE, StairsShape.INNER_RIGHT)
                    .modelForState().modelFile(macadamStairsInnerModel).rotationY(0).uvLock(true).addModel();
            macadamBuilder.partialState().with(StairBlock.FACING, Direction.WEST).with(StairBlock.SHAPE, StairsShape.INNER_RIGHT)
                    .modelForState().modelFile(macadamStairsInnerModel).rotationY(180).uvLock(true).addModel();
            macadamBuilder.partialState().with(StairBlock.FACING, Direction.SOUTH).with(StairBlock.SHAPE, StairsShape.INNER_RIGHT)
                    .modelForState().modelFile(macadamStairsInnerModel).rotationY(90).uvLock(true).addModel();
            macadamBuilder.partialState().with(StairBlock.FACING, Direction.NORTH).with(StairBlock.SHAPE, StairsShape.INNER_RIGHT)
                    .modelForState().modelFile(macadamStairsInnerModel).rotationY(270).uvLock(true).addModel();
            macadamBuilder.partialState().with(StairBlock.FACING, Direction.EAST).with(StairBlock.SHAPE, StairsShape.INNER_LEFT)
                    .modelForState().modelFile(macadamStairsInnerModel).rotationY(270).uvLock(true).addModel();
            macadamBuilder.partialState().with(StairBlock.FACING, Direction.WEST).with(StairBlock.SHAPE, StairsShape.INNER_LEFT)
                    .modelForState().modelFile(macadamStairsInnerModel).rotationY(90).uvLock(true).addModel();
            macadamBuilder.partialState().with(StairBlock.FACING, Direction.SOUTH).with(StairBlock.SHAPE, StairsShape.INNER_LEFT)
                    .modelForState().modelFile(macadamStairsInnerModel).rotationY(0).uvLock(true).addModel();
            macadamBuilder.partialState().with(StairBlock.FACING, Direction.NORTH).with(StairBlock.SHAPE, StairsShape.INNER_LEFT)
                    .modelForState().modelFile(macadamStairsInnerModel).rotationY(180).uvLock(true).addModel();

        //Wood
        for(CompatWood wood : CompatWood.VALUES){
            Block shingleBlock = RNRCompatBlocks.WOOD_SHINGLE_ROOFS.get(wood).get();
            StairBlock shingleStair = (StairBlock) RNRCompatBlocks.WOOD_SHINGLE_ROOF_STAIRS.get(wood).get();
            SlabBlock shingleSlab = (SlabBlock) RNRCompatBlocks.WOOD_SHINGLE_ROOF_SLABS.get(wood).get();

            ResourceLocation woodShingleTexture = ResourceLocation.fromNamespaceAndPath(FirmaCompat.MODID, "block/" + wood.getSerializedName() + "_shingles");

            simpleBlock(shingleBlock);
            stairsBlock(shingleStair, woodShingleTexture);
            slabBlock(shingleSlab, woodShingleTexture, woodShingleTexture);
        }

    }

    private void pillarBlock(Block block, ResourceLocation modelPath) {
        ModelFile model = new ModelFile.UncheckedModelFile(modelPath);

        getVariantBuilder(block)
                // Axis X: rotate 90° on X and 90° on Y
                .partialState().with(BlockStateProperties.AXIS, Direction.Axis.X)
                .modelForState()
                .modelFile(model)
                .rotationX(90)
                .rotationY(90)
                .addModel()

                // Axis Y: no rotation needed (upright)
                .partialState().with(BlockStateProperties.AXIS, Direction.Axis.Y)
                .modelForState()
                .modelFile(model)
                .addModel()

                // Axis Z: rotate 90° on X
                .partialState().with(BlockStateProperties.AXIS, Direction.Axis.Z)
                .modelForState()
                .modelFile(model)
                .rotationX(90)
                .addModel();
    }

    private void barrelBlock(Block barrelBlock, CompatWood wood) {
        // Model files (adjust paths if your templates are in a different folder)
        ModelFile barrelModel = models()
                .withExistingParent(("block/barrel/" + wood.getSerializedName() + "/barrel"), modLoc("block/template/barrel/barrel"))
                .texture("particle", wood.planksTexture())
                .texture("planks", wood.planksTexture())
                .texture("sheet", wood.strippedLogTexture());
        ModelFile barrelSealedModel = models()
                .withExistingParent(("block/barrel/" + wood.getSerializedName() + "/barrel_sealed"), modLoc("block/template/barrel/barrel_sealed"))
                .texture("particle", wood.planksTexture())
                .texture("planks", wood.planksTexture())
                .texture("sheet", wood.strippedLogTexture());
        ModelFile barrelSideModel = models()
                .withExistingParent(("block/barrel/" + wood.getSerializedName() + "/barrel_side"), modLoc("block/template/barrel/barrel_side"))
                .texture("particle", wood.planksTexture())
                .texture("planks", wood.planksTexture())
                .texture("sheet", wood.strippedLogTexture());
        ModelFile barrelSideRackModel = models()
                .withExistingParent(("block/barrel/" + wood.getSerializedName() + "/barrel_side_rack"), modLoc("block/template/barrel/barrel_side_rack"))
                .texture("particle", wood.planksTexture())
                .texture("planks", wood.planksTexture())
                .texture("sheet", wood.strippedLogTexture());
        ModelFile barrelSideSealedModel = models()
                .withExistingParent(("block/barrel/" + wood.getSerializedName() + "/barrel_side_sealed"), modLoc("block/template/barrel/barrel_side_sealed"))
                .texture("particle", wood.planksTexture())
                .texture("planks", wood.planksTexture())
                .texture("sheet", wood.strippedLogTexture());
        ModelFile barrelSideSealedRackModel = models()
                .withExistingParent(("block/barrel/" + wood.getSerializedName() + "/barrel_side_sealed_rack"), modLoc("block/template/barrel/barrel_side_sealed_rack"))
                .texture("particle", wood.planksTexture())
                .texture("planks", wood.planksTexture())
                .texture("sheet", wood.strippedLogTexture());

        var builder = getVariantBuilder(barrelBlock);

        // Facing UP — no rotation needed
        builder
                .partialState().with(BarrelBlock.FACING, Direction.UP).with(BarrelBlock.RACK, true).with(BarrelBlock.SEALED, true)
                .modelForState().modelFile(barrelSealedModel).addModel()
                .partialState().with(BarrelBlock.FACING, Direction.UP).with(BarrelBlock.RACK, true).with(BarrelBlock.SEALED, false)
                .modelForState().modelFile(barrelModel).addModel()
                .partialState().with(BarrelBlock.FACING, Direction.UP).with(BarrelBlock.RACK, false).with(BarrelBlock.SEALED, true)
                .modelForState().modelFile(barrelSealedModel).addModel()
                .partialState().with(BarrelBlock.FACING, Direction.UP).with(BarrelBlock.RACK, false).with(BarrelBlock.SEALED, false)
                .modelForState().modelFile(barrelModel).addModel();

        // Horizontal facings — adjust rotation so barrel lies along the facing axis
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            // Base rotation from direction
            int baseYRot = (int) dir.toYRot(); // N=0, E=90, S=180, W=270

            // Add 90° so that when facing east/west, the model aligns east-west
            // This compensates for side models being authored facing north
            int fixedYRot = (baseYRot + 90) % 360;

            ModelFile rackModel = BarrelBlock.SEALED.getPossibleValues().contains(true) ? barrelSideSealedRackModel : barrelSideRackModel;
            ModelFile noRackModel = BarrelBlock.SEALED.getPossibleValues().contains(true) ? barrelSideSealedModel : barrelSideModel;

            // Rack + Sealed
            builder.partialState().with(BarrelBlock.FACING, dir).with(BarrelBlock.RACK, true).with(BarrelBlock.SEALED, true)
                    .modelForState().modelFile(barrelSideSealedRackModel).rotationY(fixedYRot).addModel();

            // Rack + Unsealed
            builder.partialState().with(BarrelBlock.FACING, dir).with(BarrelBlock.RACK, true).with(BarrelBlock.SEALED, false)
                    .modelForState().modelFile(barrelSideRackModel).rotationY(fixedYRot).addModel();

            // No Rack + Sealed
            builder.partialState().with(BarrelBlock.FACING, dir).with(BarrelBlock.RACK, false).with(BarrelBlock.SEALED, true)
                    .modelForState().modelFile(barrelSideSealedModel).rotationY(fixedYRot).addModel();

            // No Rack + Unsealed
            builder.partialState().with(BarrelBlock.FACING, dir).with(BarrelBlock.RACK, false).with(BarrelBlock.SEALED, false)
                    .modelForState().modelFile(barrelSideModel).rotationY(fixedYRot).addModel();
        }
    }

}
