package com.bumppo109.firma_compat.datagen.assets;

import com.bumppo109.firma_compat.FirmaCompat;
import com.bumppo109.firma_compat.block.*;
import com.bumppo109.firma_compat.item.ModItems;
import com.bumppo109.firma_compat.integration.firmalife.CompatFLBlocks;
import com.bumppo109.firma_compat.integration.rnr.CompatRNR;
import com.bumppo109.firma_compat.integration.rnr.RNRCompatBlocks;
import com.bumppo109.firma_compat.integration.rnr.RNRCompatItems;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.util.Metal;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
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

        add("entity.minecraft.villager.firma_compat.compat_farmer", "Farmer");

        //Gems Realm Compat
        add("item_type.firma_compat.rod", "%s Rod");
        add("item_type.firma_compat.double_ingot", "%s Double Ingot");
        add("item_type.firma_compat.sheet", "%s Sheet");
        add("item_type.firma_compat.double_sheet", "%s Double Sheet");
        add("item_type.firma_compat.pickaxe_head", "%s Pickaxe Head");
        add("item_type.firma_compat.axe_head", "%s Axe Head");
        add("item_type.firma_compat.shovel_head", "%s Shovel Head");
        add("item_type.firma_compat.hoe_head", "%s Hoe Head");
        add("item_type.firma_compat.sword_blade", "%s Sword Blade");
        add("item_type.firma_compat.unfinished_helmet", "%s Unfinished Helmet");
        add("item_type.firma_compat.unfinished_chestplate", "%s Unfinished Chestplate");
        add("item_type.firma_compat.unfinished_leggings", "%s Unfinished Leggings");
        add("item_type.firma_compat.unfinished_boots", "%s Unfinished Boots");

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

        //Mobs
        add("entity.firma_compat.compat_bluegill", "Bluegill");
        add("entity.firma_compat.compat_crappie", "Crappie");
        add("entity.firma_compat.compat_lake_trout", "Lake Trout");
        add("entity.firma_compat.compat_largemouth_bass", "Largemouth Bass");
        add("entity.firma_compat.compat_rainbow_trout", "Rainbow Trout");
        add("entity.firma_compat.compat_salmon", "Salmon");
        add("entity.firma_compat.compat_smallmouth_bass", "Smallmouth Bass");
        add("entity.firma_compat.compat_cod", "Cod");
        add("entity.firma_compat.compat_pufferfish", "Pufferfish");
        add("entity.firma_compat.compat_tropical_fish", "Tropical Fish");
        add("entity.firma_compat.compat_jellyfish", "Jellyfish");
        add("entity.firma_compat.compat_manatee", "Manatee");
        add("entity.firma_compat.compat_orca", "Orca");
        add("entity.firma_compat.compat_dolphin", "Dolphin");
        add("entity.firma_compat.compat_isopod", "Isopod");
        add("entity.firma_compat.compat_lobster", "Lobster");
        add("entity.firma_compat.compat_crayfish", "Crayfish");
        add("entity.firma_compat.compat_horseshoe_crab", "Horseshoe Crab");
        add("entity.firma_compat.compat_penguin", "Penguin");
        add("entity.firma_compat.compat_leopard_seal", "Leopard Seal");
        add("entity.firma_compat.compat_frog", "Frog");
        add("entity.firma_compat.compat_turtle", "Turtle");
        add("entity.firma_compat.compat_pig", "Pig");
        add("entity.firma_compat.compat_pig.male", "Pig");
        add("entity.firma_compat.compat_pig.female", "Sow");
        add("entity.firma_compat.compat_cow", "Cow");
        add("entity.firma_compat.compat_cow.female", "Cow");
        add("entity.firma_compat.compat_cow.male", "Bull");
        add("entity.firma_compat.compat_goat", "Goat");
        add("entity.firma_compat.compat_goat.female", "Nanny Goat");
        add("entity.firma_compat.compat_goat.male", "Billy Goat");
        add("entity.firma_compat.compat_alpaca", "Alpaca");
        add("entity.firma_compat.compat_alpaca.female", "Female Alpaca");
        add("entity.firma_compat.compat_alpaca.male", "Male Alpaca");
        add("entity.firma_compat.compat_sheep", "Sheep");
        add("entity.firma_compat.compat_sheep.female", "Ewe");
        add("entity.firma_compat.compat_sheep.male", "Ram");
        add("entity.firma_compat.compat_musk_ox", "Musk Ox");
        add("entity.firma_compat.compat_musk_ox.female", "Musk Ox Cow");
        add("entity.firma_compat.compat_musk_ox.male", "Musk Ox Bull");
        add("entity.firma_compat.compat_yak", "Yak");
        add("entity.firma_compat.compat_yak.female", "Female Yak");
        add("entity.firma_compat.compat_yak.male", "Male Yak");
        add("entity.firma_compat.compat_rabbit.female", "Doe Rabbit");
        add("entity.firma_compat.compat_rabbit.male", "Buck Rabbit");
        add("entity.firma_compat.compat_polar_bear", "Polar Bear");
        add("entity.firma_compat.compat_grizzly_bear", "Grizzly Bear");
        add("entity.firma_compat.compat_black_bear", "Black Bear");
        add("entity.firma_compat.compat_cougar", "Cougar");
        add("entity.firma_compat.compat_panther", "Panther");
        add("entity.firma_compat.compat_lion", "Lion");
        add("entity.firma_compat.compat_sabertooth", "Sabertooth");
        add("entity.firma_compat.compat_tiger", "Tiger");
        add("entity.firma_compat.compat_crocodile", "Crocodile");
        add("entity.firma_compat.compat_squid", "Squid");
        add("entity.firma_compat.compat_octopoteuthis", "Octopoteuthis");
        add("entity.firma_compat.compat_chicken", "Chicken");
        add("entity.firma_compat.compat_chicken.male", "Rooster");
        add("entity.firma_compat.compat_chicken.female", "Chicken");
        add("entity.firma_compat.compat_duck", "Duck");
        add("entity.firma_compat.compat_duck.male", "Drake");
        add("entity.firma_compat.compat_duck.female", "Duck");
        add("entity.firma_compat.compat_quail", "Quail");
        add("entity.firma_compat.compat_quail.male", "Male Quail");
        add("entity.firma_compat.compat_quail.female", "Female Quail");
        add("entity.firma_compat.compat_rabbit", "Rabbit");
        add("entity.firma_compat.compat_fox", "Fox");
        add("entity.firma_compat.compat_panda", "Panda");
        add("entity.firma_compat.compat_boar", "Boar");
        add("entity.firma_compat.compat_wildebeest", "Wildebeest");
        add("entity.firma_compat.compat_bison", "Bison");
        add("entity.firma_compat.compat_ocelot", "Ocelot");
        add("entity.firma_compat.compat_bongo", "Bongo");
        add("entity.firma_compat.compat_caribou", "Caribou");
        add("entity.firma_compat.compat_deer", "Deer");
        add("entity.firma_compat.compat_gazelle", "Gazelle");
        add("entity.firma_compat.compat_moose", "Moose");
        add("entity.firma_compat.compat_grouse", "Grouse");
        add("entity.firma_compat.compat_pheasant", "Pheasant");
        add("entity.firma_compat.compat_turkey", "Turkey");
        add("entity.firma_compat.compat_peafowl", "Peafowl");
        add("entity.firma_compat.compat_peafowl.male", "Peacock");
        add("entity.firma_compat.compat_peafowl.female", "Peahen");
        add("entity.firma_compat.compat_rat", "Rat");
        add("entity.firma_compat.compat_jerboa", "Jerboa");
        add("entity.firma_compat.compat_lemming", "Lemming");
        add("entity.firma_compat.compat_mongoose", "Mongoose");
        add("entity.firma_compat.compat_cat", "Cat");
        add("entity.firma_compat.compat_cat.female", "Female Cat");
        add("entity.firma_compat.compat_cat.male", "Male Cat");
        add("entity.firma_compat.compat_dog", "Dog");
        add("entity.firma_compat.compat_dog.male", "Male Dog");
        add("entity.firma_compat.compat_dog.female", "Female Dog");
        add("entity.firma_compat.compat_wolf", "Wolf");
        add("entity.firma_compat.compat_hyena", "Hyena");
        add("entity.firma_compat.compat_direwolf", "Direwolf");
        add("entity.firma_compat.compat_mule", "Mule");
        add("entity.firma_compat.compat_mule.male", "Mule");
        add("entity.firma_compat.compat_mule.female", "Mule");
        add("entity.firma_compat.compat_donkey", "Donkey");
        add("entity.firma_compat.compat_donkey.male", "Jack Donkey");
        add("entity.firma_compat.compat_donkey.female", "Jenny Donkey");
        add("entity.firma_compat.compat_horse", "Horse");
        add("entity.firma_compat.compat_horse.male", "Stallion");
        add("entity.firma_compat.compat_horse.female", "Mare");

        //Food
        Item sweetBerryJar = ModItems.SWEET_BERRIES_JAR.get();
        Item sweetBerryJarUnsealed = ModItems.SWEET_BERRIES_JAR_UNSEALED.get();
        Item sweetBerryJam = ModItems.SWEET_BERRIES_JAM.get();

        Item glowBerryJar = ModItems.GLOW_BERRIES_JAR.get();
        Item glowBerryJarUnsealed = ModItems.GLOW_BERRIES_JAR_UNSEALED.get();
        Item glowBerryJam = ModItems.GLOW_BERRIES_JAM.get();

        add(sweetBerryJam, getItemDisplayName(sweetBerryJam));
        add(sweetBerryJar, getItemDisplayName(sweetBerryJar));
        add(sweetBerryJarUnsealed, getItemDisplayName(sweetBerryJarUnsealed));
        add(glowBerryJar, getItemDisplayName(glowBerryJar));
        add(glowBerryJarUnsealed, getItemDisplayName(glowBerryJarUnsealed));
        add(glowBerryJam, getItemDisplayName(glowBerryJam));

        // Wood Related
        Block chestBlock = ModBlocks.COMPAT_CHEST.get();
        Block trappedChestBlock = ModBlocks.COMPAT_TRAPPED_CHEST.get();
        Item minecartChest = ModItems.COMPAT_CHEST_MINECART.get();

        add(chestBlock, "Chest");
        add(trappedChestBlock, "Trapped Chest");
        add(minecartChest, "Minecart with Chest");

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

        for(Metal metal : Metal.values()){
            if(metal.allParts()){
                Block block = ModBlocks.COMPAT_LANTERNS.get(metal).get();
                add(block.getDescriptionId(), getBlockDisplayName(block));
                add(block.getDescriptionId() + ".filled", getBlockDisplayName(block));
            }
        }
        add(ModItems.UNFINISHED_LANTERN.asItem(), "Unfinished Lantern");
        add(ModBlocks.LANTERN.get(), "Lantern");
        add(ModBlocks.LANTERN.get().getDescriptionId() + ".filled", "Lantern");


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
        add(ModItems.POOR_NETHERITE_INGOT.get().getDescriptionId(), "Poor Netherite Ingot");

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
                add(flagstoneItem.getDescriptionId(), getItemDisplayName(flagstoneItem));
                add(flagstoneBlock.getDescriptionId(), getBlockDisplayName(flagstoneBlock));
                add(flagstoneStair.getDescriptionId(), getBlockDisplayName(flagstoneStair));
                add(flagstoneSlab.getDescriptionId(), getBlockDisplayName(flagstoneSlab));

                if(rock.equals(CompatRock.NETHERRACK)) continue;
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

            Item gravelFill = RNRCompatItems.GRAVEL_FILL.get();

            add(gravelFill, getItemDisplayName(gravelFill));
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
