package com.bumppo109.firma_compat.datagen.assets;

import com.bumppo109.firma_compat.FirmaCompat;
import com.bumppo109.firma_compat.block.*;
import com.bumppo109.firma_compat.item.ModItems;
import com.bumppo109.firma_compat.tfcaddon.firmalife.CompatFLBlocks;
import com.bumppo109.firma_compat.tfcaddon.rnr.CompatRNR;
import com.bumppo109.firma_compat.tfcaddon.rnr.RNRCompatBlocks;
import com.bumppo109.firma_compat.tfcaddon.rnr.RNRCompatItems;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class BuiltinLang extends LanguageProvider {
    public BuiltinLang(PackOutput output) {
        super(output, FirmaCompat.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("firma_compat.creative_tab.firma_compat", "Firma Compat");

        add("fluid.firma_compat.metal.netherite", "Molten Netherite");
        add("fluid.firma_compat.metal.poor_netherite", "Molten Poor Netherite");

        //Wood Good Compat
        add("item_type.firma_compat.lumber", "%s Lumber");
        add("block_type.firma_compat.twig", "%s Twig");
        add("block_type.firma_compat.support", "%s Support");
        add("block_type.firma_compat.vertical_support", "%s Support");
        add("block_type.firma_compat.horizontal_support", "%s Support");
        add("block_type.firma_compat.log_fence", "%s Log Fence");
        add("block_type.firma_compat.tool_rack", "%s Tool Rack");
        add("block_type.firma_compat.loom", "%s Loom");
        add("block_type.firma_compat.sluice", "%s Sluice");
        add("block_type.firma_compat.barrel", "%s Barrel");
        add("block_type.firma_compat.scribing_table", "%s Scribing Table");
        add("block_type.firma_compat.sewing_table", "%s Sewing Table");
        add("block_type.firma_compat.shelf", "%s Shelf");
        add("block_type.firma_compat.axle", "%s Axle");
        add("block_type.firma_compat.bladed_axle", "%s Bladed Axle");
        add("block_type.firma_compat.encased_axle", "%s Encased Axle");
        add("block_type.firma_compat.clutch", "%s Clutch");
        add("block_type.firma_compat.gear_box", "%s Gear Box");
        add("block_type.firma_compat.windmill", "%s Windmill");
        add("block_type.firma_compat.water_wheel", "%s Water Wheel");

        add("block_type.firma_compat.keg", "%s Keg");
        add("block_type.firma_compat.food_shelf", "%s Food Shelf");
        add("block_type.firma_compat.wine_shelf", "%s Wine Shelf");
        add("block_type.firma_compat.hanger", "%s Hanger");
        add("block_type.firma_compat.jarbnet", "%s Jarbnet");
        add("block_type.firma_compat.stomping_barrel", "%s Stomping Barrel");
        add("block_type.firma_compat.barrel_press", "%s Barrel Press");

        // Wood Related
        for (CompatWood wood : CompatWood.VALUES) {
            //TODO special case - not sure how to reference the item. but does it matter?
            Item supportItem = ModItems.SUPPORTS.get(wood).get().asItem();
            String supportName = getItemDisplayName(supportItem);
            //add(supportItem.getDescriptionId(), supportName);

            //WOODS
            for (CompatWood.BlockType blockType : CompatWood.BlockType.values()) {
                Block block = ModBlocks.WOODS.get(wood).get(blockType).get();
                String blockName;

                if(blockType.equals(CompatWood.BlockType.VERTICAL_SUPPORT) || blockType.equals(CompatWood.BlockType.HORIZONTAL_SUPPORT)){
                    blockName = supportName;
                } else {
                    blockName = getBlockDisplayName(block);
                }
                add(block.getDescriptionId(), blockName);
            }
            //Hanging Signs
            /*
            for(Metal metal : Metal.values()){
                //TODO - null values from Block variables
                //TODO - add item names
                Block ceilingBlock = ModBlocks.CEILING_HANGING_SIGNS.get(wood).get(metal).get();
                Block wallBlock = ModBlocks.WALL_HANGING_SIGNS.get(wood).get(metal).get();
                String MetalName = metal.getSerializedName();

                add(ceilingBlock.getDescriptionId(), wood.getSerializedName().toUpperCase(Locale.ROOT) + " Hanging Sign");
                add(wallBlock.getDescriptionId(), wood.getSerializedName().toUpperCase(Locale.ROOT) + " Hanging Sign");
            }
             */

            //Lumber
            Item lumberItem = ModItems.LUMBER.get(wood).get();
            String lumberName = getItemDisplayName(lumberItem);

            add(lumberItem.getDescriptionId(), lumberName);
        }
        //Rock
        for (CompatRock rock : CompatRock.VALUES) {
            //ROCK_BLOCKS
            for (CompatRock.BlockType blockType : CompatRock.BlockType.values()) {
                Block block = ModBlocks.ROCK_BLOCKS.get(rock).get(blockType).get();
                String blockName = getBlockDisplayName(block);

                add(block.getDescriptionId(), blockName);
            }
            //Ore
            for(CompatOre ore : CompatOre.values()){
                if(!ore.isGraded()){
                    Block block = ModBlocks.ORES.get(rock).get(ore).get();
                    String blockName = getBlockDisplayName(block);

                    add(block.getDescriptionId(), blockName);
                } else {
                    for(CompatOre.Grade grade : CompatOre.Grade.values()){
                        Block block = ModBlocks.GRADED_ORES.get(rock).get(ore).get(grade).get();
                        String blockName = getBlockDisplayName(block);

                        add(block.getDescriptionId(), blockName);
                    }
                }
            }
        }

        for(CompatBricks brick : CompatBricks.VALUES){
            Block block = ModBlocks.AQUEDUCTS.get(brick).get();
            String blockName = getBlockDisplayName(block);

            add(block.getDescriptionId(), blockName);
        }

        //Other Blocks
        add(ModBlocks.PRIMITIVE_ANVIL.get().getDescriptionId(), "Primitive Anvil");
        add(ModBlocks.NATIVE_GOLD_GRAVEL_DEPOSIT.get().getDescriptionId(), "Native Gold Gravel Deposit");
        add(ModBlocks.NATIVE_SILVER_GRAVEL_DEPOSIT.get().getDescriptionId(), "Native Silver Gravel Deposit");
        add(ModBlocks.NATIVE_COPPER_GRAVEL_DEPOSIT.get().getDescriptionId(), "Native Copper Gravel Deposit");
        add(ModBlocks.CASSITERITE_GRAVEL_DEPOSIT.get().getDescriptionId(), "Cassiterite Gravel Deposit");
        add(ModBlocks.DRYING_MUD_BRICK.get().getDescriptionId(), "Wet Mud Brick");
        add(ModBlocks.COMPAT_FARMLAND.get().getDescriptionId(), "Farmland");
        add(ModBlocks.CLAY_PODZOL.get().getDescriptionId(), "Clay Podzol");
        add(ModBlocks.CLAY_GRASS_BLOCK.get().getDescriptionId(), "Clay Grass Block");
        add(ModBlocks.CLAY_DIRT.get().getDescriptionId(), "Clay Dirt");
        add(ModBlocks.KAOLIN_CLAY_PODZOL.get().getDescriptionId(), "Kaolin Clay Podzol");
        add(ModBlocks.KAOLIN_CLAY_GRASS_BLOCK.get().getDescriptionId(), "Kaolin Clay Grass Block");
        add(ModBlocks.KAOLIN_CLAY_DIRT.get().getDescriptionId(), "Kaolin Clay Dirt");

        add(ModItems.ANDESITE_BRICK.get().getDescriptionId(), "Andesite Brick");
        add(ModItems.DIORITE_BRICK.get().getDescriptionId(), "Diorite Brick");
        add(ModItems.GRANITE_BRICK.get().getDescriptionId(), "Granite Brick");
        add(ModItems.CALCITE_BRICK.get().getDescriptionId(), "Calcite Brick");
        add(ModItems.DRIPSTONE_BRICK.get().getDescriptionId(), "Dripstone Brick");
        add(ModItems.BASALT_BRICK.get().getDescriptionId(), "Basalt Brick");

        //Metal Items
        for(CompatMetal metal : CompatMetal.values()){
            for(CompatMetal.ItemType itemType : CompatMetal.ItemType.values()){
                if(itemType.has(metal)){
                    Item metalItem = ModItems.METAL_ITEMS.get(metal).get(itemType).get();
                    String itemName = getItemDisplayName(metalItem);

                    add(metalItem.getDescriptionId(), itemName);
                }
            }
            //Metal Bucket items
            Item bucketItem = ModItems.METAL_FLUID_BUCKETS.get(metal).get();
            String bucketName = cleanUpString(metal.getSerializedName()) + " Bucket";

            add(bucketItem.getDescriptionId(), bucketName);
            //Metal Fluid Block
            Block fluidBlock = ModBlocks.METAL_FLUIDS.get(metal).get();
            String fluidName = getBlockDisplayName(fluidBlock);

            add(fluidBlock.getDescriptionId(), fluidName);
            //TODO - fluid?
        }



        //Other Items
        add(ModItems.BAMBOO_LUMBER.get().getDescriptionId(), "Bamboo Lumber");
        add(ModItems.MUD_BRICK.get().getDescriptionId(), "Mud Brick");
        add(ModItems.STONE_BRICK.get().getDescriptionId(), "Stone Brick");
        add(ModItems.DEEPSLATE_BRICK.get().getDescriptionId(), "Deepslate Brick");
        add(ModItems.DEEPSLATE_TILE.get().getDescriptionId(), "Deepslate Tile");
        add(ModItems.TUFF_BRICK.get().getDescriptionId(), "Tuff Brick");
        add(ModItems.POLISHED_BLACKSTONE_BRICK.get().getDescriptionId(), "Polished Blackstone Brick");
        add(ModItems.END_STONE_BRICK.get().getDescriptionId(), "End Stone Brick");
        add(ModItems.QUARTZ_BRICK.get().getDescriptionId(), "Quartz Brick");
        add(ModItems.PRISMARINE_BRICK.get().getDescriptionId(), "Prismarine Brick");
        add(ModItems.UNFIRED_POT.get().getDescriptionId(), "Unfired Simple Pot");
        add(ModItems.NETHERITE_SCRAP_INGOT.get().getDescriptionId(), "Netherite Scrap Ingot");

        //Firmalife
            for(CompatWood wood : CompatWood.VALUES){
                Block foodShelfBlock = CompatFLBlocks.FOOD_SHELVES.get(wood).get();
                Block hangerBlock = CompatFLBlocks.HANGERS.get(wood).get();
                Block jarbnetBlock = CompatFLBlocks.JARBNETS.get(wood).get();
                Block wineShelfBlock = CompatFLBlocks.WINE_SHELVES.get(wood).get();
                Block kegBlock = CompatFLBlocks.KEGS.get(wood).get();
                Block stompBarrelBlock = CompatFLBlocks.STOMPING_BARRELS.get(wood).get();
                Block barrelPressBlock = CompatFLBlocks.BARREL_PRESSES.get(wood).get();

                add(foodShelfBlock.getDescriptionId(), getBlockDisplayName(foodShelfBlock));
                add(hangerBlock.getDescriptionId(), getBlockDisplayName(hangerBlock));
                add(jarbnetBlock.getDescriptionId(), getBlockDisplayName(jarbnetBlock));
                add(wineShelfBlock.getDescriptionId(), getBlockDisplayName(wineShelfBlock));
                add(kegBlock.getDescriptionId(), getBlockDisplayName(kegBlock));
                add(stompBarrelBlock.getDescriptionId(), getBlockDisplayName(stompBarrelBlock));
                add(barrelPressBlock.getDescriptionId(), getBlockDisplayName(barrelPressBlock));
            }

            for(CompatRock rock : CompatRock.VALUES){
                for(Ore.Grade grade : Ore.Grade.values()){
                    Block chromiteOreBlock = CompatFLBlocks.CHROMITE_ORES.get(rock).get(grade).get();

                    add(chromiteOreBlock.getDescriptionId(), getBlockDisplayName(chromiteOreBlock));
                }
            }


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
                Item flagstoneItem = RNRCompatItems.FLAGSTONE.get(rock).get();

                add(cobbledBlock.getDescriptionId(), getBlockDisplayName(cobbledBlock));
                add(cobbledStair.getDescriptionId(), getBlockDisplayName(cobbledStair));
                add(cobbledSlab.getDescriptionId(), getBlockDisplayName(cobbledSlab));
                if(rock.equals(CompatRock.NETHERRACK)) continue;

                add(flagstoneItem.getDescriptionId(), getItemDisplayName(flagstoneItem));
                add(flagstoneBlock.getDescriptionId(), getBlockDisplayName(flagstoneBlock));
                add(flagstoneStair.getDescriptionId(), getBlockDisplayName(flagstoneStair));
                add(flagstoneSlab.getDescriptionId(), getBlockDisplayName(flagstoneSlab));
                add(settBlock.getDescriptionId(), getBlockDisplayName(settBlock));
                add(settStair.getDescriptionId(), getBlockDisplayName(settStair));
                add(settSlab.getDescriptionId(), getBlockDisplayName(settSlab));
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

            add(overheightGravelBlock.getDescriptionId(), getBlockDisplayName(overheightGravelBlock));
            add(tampedDirtBlock.getDescriptionId(), getBlockDisplayName(tampedDirtBlock));
            add(tampedMudBlock.getDescriptionId(), getBlockDisplayName(tampedMudBlock));

            add(gravelBlock.getDescriptionId(), getBlockDisplayName(gravelBlock));
            add(gravelStairsBlock.getDescriptionId(), getBlockDisplayName(gravelStairsBlock));
            add(gravelSlabBlock.getDescriptionId(), getBlockDisplayName(gravelSlabBlock));
            add(macadamBlock.getDescriptionId(), getBlockDisplayName(macadamBlock));
            add(macadamStairsBlock.getDescriptionId(), getBlockDisplayName(macadamStairsBlock));
            add(macadamSlabBlock.getDescriptionId(), getBlockDisplayName(macadamSlabBlock));

            for(CompatWood wood : CompatWood.VALUES){
                Block shingleBlock = RNRCompatBlocks.WOOD_SHINGLE_ROOFS.get(wood).get();
                Block shingleStair = RNRCompatBlocks.WOOD_SHINGLE_ROOF_STAIRS.get(wood).get();
                Block shingleSlab = RNRCompatBlocks.WOOD_SHINGLE_ROOF_SLABS.get(wood).get();
                Item shingleItem = RNRCompatItems.SHINGLE.get(wood).get();

                add(shingleBlock.getDescriptionId(), getBlockDisplayName(shingleBlock));
                add(shingleStair.getDescriptionId(), getBlockDisplayName(shingleStair));
                add(shingleSlab.getDescriptionId(), getBlockDisplayName(shingleSlab));
                add(shingleItem.getDescriptionId(), getItemDisplayName(shingleItem));
            }

    }

    private String cleanUpString(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "";
        }

        // Replace underscores with spaces
        String normalized = input.replace('_', ' ');

        // Split into words, capitalize each, and join back
        String[] words = normalized.split("\\s+");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        // Remove trailing space
        return result.toString().trim();
    }

    private String getBlockDisplayName(Block block) {
        if (block == null) {
            return "Unknown Block";
        }

        ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
        if (id == null || id.equals(BuiltInRegistries.BLOCK.getDefaultKey())) {
            return "Air";
        }

        // Get the path part, e.g. "wood/planks/oak" or "metal/block/copper"
        String path = id.getPath();

        // Split on both "_" and "/" to handle folder-like paths nicely
        String[] parts = path.split("[_/]");

        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (String part : parts) {
            if (part.isEmpty()) {
                continue;
            }

            // Capitalize first letter, lowercase the rest
            String word = Character.toUpperCase(part.charAt(0)) + part.substring(1).toLowerCase();
            sb.append(word);

            sb.append(" ");
            first = false;
        }

        // Trim trailing space
        String result = sb.toString().trim();
        return result;
    }

    private String getItemDisplayName(Item item) {
        if (item == null) {
            return "Unknown Item";
        }

        ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
        if (id == null || id.equals(BuiltInRegistries.ITEM.getDefaultKey())) {
            return "Air";
        }

        // Get the path part, e.g. "metal/ingot/copper" or "lumber/oak"
        String path = id.getPath();

        // Split on both "_" and "/" to handle folder-like paths nicely
        String[] parts = path.split("[_/]");

        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (String part : parts) {
            if (part.isEmpty()) {
                continue;
            }

            // Capitalize first letter, lowercase the rest
            String word = Character.toUpperCase(part.charAt(0)) + part.substring(1).toLowerCase();
            sb.append(word).append(" ");
            first = false;
        }

        // Trim trailing space
        String result = sb.toString().trim();
        return result;
    }
}
