package com.bumppo109.firma_compat.datagen.recipe;

import com.bumppo109.firma_compat.block.*;
import com.bumppo109.firma_compat.item.ModItems;
import com.bumppo109.firma_compat.util.ModTags;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.DecorationBlockHolder;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.rock.RockCategory;
import net.dries007.tfc.common.component.food.FoodData;
import net.dries007.tfc.common.items.Food;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.common.recipes.ingredients.AndIngredient;
import net.dries007.tfc.common.recipes.ingredients.FluidContentIngredient;
import net.dries007.tfc.common.recipes.ingredients.NotRottenIngredient;
import net.dries007.tfc.common.recipes.outputs.MealModifier;
import net.dries007.tfc.util.Metal;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.Tags;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static net.dries007.tfc.util.DataGenerationHelpers.Builder;

public interface ModCraftingRecipes extends ModRecipes
{

    default void craftingRecipes()
    {
        remove(
                "bread",
                "crafter",
                "sugar_from_sugar_cane",
                "sugar_from_honey_bottle",
                "raw_iron_block",
                "raw_iron",
                "raw_copper",
                "raw_copper_block",
                "raw_gold",
                "raw_gold_block",
                "iron_ingot_from_nuggets",
                "gold_ingot_from_nuggets",
                "gold_ingot_from_gold_block",
                "pumpkin_pie",
                "cake",
                "cookie",
                "copper_block",
                "waxed_copper_block_from_honeycomb",
                "netherite_sword_smithing",
                "netherite_ingot",
                "netherite_axe",
                "netherite_pickaxe",
                "netherite_shovel",
                "netherite_hoe",
                "netherite_axe_smithing",
                "netherite_pickaxe_smithing",
                "netherite_shovel_smithing",
                "netherite_hoe_smithing",
                "netherite_helmet",
                "netherite_chestplate",
                "netherite_leggings",
                "netherite_boots",
                "netherite_helmet_smithing",
                "netherite_chestplate_smithing",
                "netherite_leggings_smithing",
                "netherite_boots_smithing",
                "netherite_upgrade_smithing_template",
                "iron_block",
                "iron_ingot_from_iron_block",
                "iron_bars",
                "iron_nugget",
                "gold_nugget",
                "chain",
                "gold_block",
                "emerald_block",
                "emerald",
                "copper_ingot",
                "copper_ingot_from_waxed_copper_block",
                "lapis_block",
                "diamond_block",
                "netherite_block",
                "netherite_ingot_from_netherite_block",
                "diamond",
                "quartz_pillar",
                "quartz_bricks",
                "nether_bricks",
                "red_nether_bricks",
                "stone_bricks",
                "cobblestone",
                "smooth_stone",
                "cracked_stone_bricks",
                "chiseled_stone_bricks",
                "granite",
                "polished_granite",
                "andesite",
                "polished_andesite",
                "diorite",
                "polished_diorite",
                "stone_brick_slab_from_stone_stonecutting",
                "stone_brick_stairs_from_stone_stonecutting",
                "stone_brick_walls_from_stone_stonecutting",
                "chiseled_stone_bricks_from_stone_stonecutting",
                "stone_bricks_from_stone_stonecutting",
                "stone_pressure_plate",
                "stone_button",
                "tuff_bricks",
                "tuff_bricks_from_tuff_stonecutting",
                "tuff_bricks_from_polished_tuff_stonecutting",
                "tuff_brick_stairs_from_tuff_stonecutting",
                "tuff_brick_slab_from_tuff_stonecutting",
                "tuff_brick_wall_from_tuff_stonecutting",
                "chiseled_tuff",
                "chiseled_tuff_from_tuff_stonecutting",
                "chiseled_tuff_bricks",
                "chiseled_tuff_bricks_from_tuff_stonecutting",
                "polished_tuff",
                "deepslate_bricks",
                "cobbled_deepslate",
                "chiseled_deepslate",
                "polished_deepslate",
                "cracked_deepslate_bricks",
                "cracked_deepslate_tiles",
                "deepslate_brick_slab_from_cobbled_deepslate_stonecutting",
                "deepslate_brick_stairs_from_cobbled_deepslate_stonecutting",
                "deepslate_brick_walls_from_cobbled_deepslate_stonecutting",
                "deepslate_bricks_from_cobbled_deepslate_stonecutting",
                "deepslate_tiles_from_cobbled_deepslate_stonecutting",
                "sandstone",
                "smooth_sandstone",
                "cut_sandstone",
                "chiseled_sandstone",
                "red_sandstone",
                "smooth_red_sandstone",
                "cut_red_sandstone",
                "chiseled_red_sandstone",
                "prismarine",
                "prismarine_bricks",
                "dark_prismarine",
                "smooth_basalt",
                "polished_basalt",
                "chiseled_polished_blackstone",
                "polished_blackstone",
                "polished_blackstone_bricks",
                "cracked_polished_blackstone_bricks",
                "polished_blackstone_pressure_plate",
                "polished_blackstone_button",
                "polished_blackstone_bricks_from_blackstone_stonecutting",
                "polished_blackstone_brick_slab_from_blackstone_stonecutting",
                "polished_blackstone_brick_stairs_from_blackstone_stonecutting",
                "polished_blackstone_brick_wall_from_blackstone_stonecutting",
                "chiseled_polished_blackstone_from_blackstone_stonecutting",
                "end_stone_bricks",
                "stone_button",
                "stone_pressure_plate",
                "polished_blackstone_button",
                "polished_blackstone_pressure_plate",
                "stone_button",
                "stone_pressure_plate",
                "polished_blackstone_button",
                "polished_blackstone_pressure_plate",
                "packed_mud",
                "mud_bricks",
                "bone_block",
                "bone_meal_from_bone_block",
                "decorated_pot_simple",
                "stonecutter",
                "smithing_table",
                "blast_furnace",
                "cauldron",
                "chiseled_bookshelf",
                "tripwire_hook",
                "piston",
                "daylight_detector",
                "hopper",
                "observer",
                "detector_rail",
                "activator_rail",
                "minecart",
                "tnt",
                "shears",
                "carrot_on_a_stick",
                "warped_fungus_on_a_stick",
                "crossbow",
                "honey_bottle",
                "yellow_dye_from_dandelion",
                "red_dye_from_poppy",
                "light_blue_dye_from_blue_orchid",
                "magenta_dye_from_allium",
                "light_gray_dye_from_azure_bluet",
                "red_dye_from_tulip",
                "orange_dye_from_orange_tulip",
                "light_gray_dye_from_white_tulip",
                "pink_dye_from_pink_tulip",
                "light_gray_dye_from_oxeye_daisy",
                "blue_dye_from_cornflower",
                "white_dye_from_lily_of_the_valley",
                "orange_dye_from_torchflower",
                "black_dye_from_wither_rose",
                "pink_dye_from_pink_petals",
                "yellow_dye_from_sunflower",
                "magenta_dye_from_lilac",
                "red_dye_from_rose_bush",
                "pink_dye_from_peony",
                "cyan_dye_from_pitcher_plant",

                "acacia_planks",
                "acacia_door",
                "acacia_trapdoor",
                "acacia_fence",
                "acacia_fence_gate",
                "acacia_pressure_plate",
                "acacia_sign",
                "acacia_hanging_sign",

                "birch_planks",
                "birch_door",
                "birch_trapdoor",
                "birch_fence",
                "birch_fence_gate",
                "birch_pressure_plate",
                "birch_sign",
                "birch_hanging_sign",

                "cherry_planks",
                "cherry_door",
                "cherry_trapdoor",
                "cherry_fence",
                "cherry_fence_gate",
                "cherry_pressure_plate",
                "cherry_sign",
                "cherry_hanging_sign",

                "crimson_planks",
                "crimson_door",
                "crimson_trapdoor",
                "crimson_fence",
                "crimson_fence_gate",
                "crimson_pressure_plate",
                "crimson_sign",
                "crimson_hanging_sign",

                "warped_planks",
                "warped_door",
                "warped_trapdoor",
                "warped_fence",
                "warped_fence_gate",
                "warped_pressure_plate",
                "warped_sign",
                "warped_hanging_sign",

                "dark_oak_planks",
                "dark_oak_door",
                "dark_oak_trapdoor",
                "dark_oak_fence",
                "dark_oak_fence_gate",
                "dark_oak_pressure_plate",
                "dark_oak_sign",
                "dark_oak_hanging_sign",

                "jungle_planks",
                "jungle_door",
                "jungle_trapdoor",
                "jungle_fence",
                "jungle_fence_gate",
                "jungle_pressure_plate",
                "jungle_sign",
                "jungle_hanging_sign",

                "mangrove_planks",
                "mangrove_door",
                "mangrove_trapdoor",
                "mangrove_fence",
                "mangrove_fence_gate",
                "mangrove_pressure_plate",
                "mangrove_sign",
                "mangrove_hanging_sign",

                "oak_planks",
                "oak_door",
                "oak_trapdoor",
                "oak_fence",
                "oak_fence_gate",
                "oak_pressure_plate",
                "oak_sign",
                "oak_hanging_sign",

                "spruce_planks",
                "spruce_door",
                "spruce_trapdoor",
                "spruce_fence",
                "spruce_fence_gate",
                "spruce_pressure_plate",
                "spruce_sign",
                "spruce_hanging_sign",
                "chiseled_stone_bricks_stone_from_stonecutting",
                "deepslate_bricks_from_polished_deepslate_stonecutting",
                "deepslate_brick_wall_from_cobbled_deepslate_stonecutting",
                "deepslate_tile_slab_from_cobbled_deepslate_stonecutting",
                "deepslate_tile_stairs_from_cobbled_deepslate_stonecutting",
                "deepslate_tile_wall_from_cobbled_deepslate_stonecutting",
                "chiseled_deepslate_from_cobbled_deepslate_stonecutting",
                "end_stone_bricks_from_end_stone_stonecutting",
                "polished_blackstone_bricks_from_polished_blackstone_stonecutting"
        );

        for(String suffix : List.of("copper","chiseled_copper", "cut_copper", "copper_bulb", "copper_door", "copper_trapdoor", "copper_grate")){
            if(suffix.equals("cut_copper")){
                remove(
                        "waxed_" + suffix,
                        "waxed_" + suffix + "_from_waxed_copper_block_stonecutting",
                        "waxed_" + suffix + "_from_honeycomb",
                        "waxed_" + suffix + "_stairs",
                        "waxed_" + suffix + "_stairs_from_waxed_" + suffix + "_stonecutting",
                        "waxed_" + suffix + "_slab",
                        "waxed_" + suffix + "_slab_from_waxed_" + suffix + "_stonecutting",
                        "waxed_" + suffix + "_stairs_from_honeycomb",
                        "waxed_" + suffix + "_slab_from_honeycomb",
                        "waxed_" + suffix + "_stairs_from_waxed_copper_block_stonecutting",
                        "waxed_" + suffix + "_slab_from_waxed_copper_block_stonecutting"
                );
            } else {
                remove(
                        suffix,
                        suffix + "_from_copper_block_stonecutting",
                        "waxed_" + suffix,
                        "waxed_" + suffix + "_from_honeycomb",
                        "waxed_" + suffix + "_from_waxed_copper_block_stonecutting",
                        "waxed_" + suffix + "_from_waxed_cut_copper_stonecutting"
                );
            }
            for(String weather : List.of("exposed", "weathered", "oxidized")){
                if(suffix.equals("cut_copper")){
                    remove(
                            "waxed_" + weather + "_" + suffix,
                            "waxed_" + weather + "_" + suffix + "_from_honeycomb",
                            "waxed_" + weather + "_" + suffix + "_from_waxed_" + weather + "_copper_stonecutting",
                            "waxed_" + weather + "_" + suffix + "_from_waxed_" + weather + "_cut_copper_stonecutting",
                            "waxed_" + weather + "_" + suffix + "_stairs",
                            "waxed_" + weather + "_" + suffix + "_slab",
                            "waxed_" + weather + "_" + suffix + "_stairs_from_honeycomb",
                            "waxed_" + weather + "_" + suffix + "_slab_from_honeycomb",
                            "waxed_" + weather + "_" + suffix + "_stairs_from_waxed_" + weather + "_copper_block_stonecutting",
                            "waxed_" + weather + "_" + suffix + "_stairs_from_waxed_" + weather + "_cut_copper_stonecutting",
                            "waxed_" + weather + "_" + suffix + "_slab_from_waxed_" + weather + "_copper_stairs_stonecutting",
                            "waxed_" + weather + "_" + suffix + "_slab_from_waxed_" + weather + "_cut_copper_stonecutting"
                    );
                } else {
                    remove(
                            weather + "_" + suffix,
                            "waxed_" + weather + "_" + suffix,
                            "waxed_" + weather + "_" + suffix + "_from_honeycomb",
                            "waxed_" + weather + "_" + suffix + "_from_waxed_" + weather + "_copper_stonecutting"
                    );
                }
                if(suffix.equals("chiseled_copper")){
                    remove(
                            "waxed_" + weather + "_" + suffix + "_from_waxed_" + weather + "_copper_block_stonecutting",
                            "waxed_" + weather + "_" + suffix + "_from_waxed_" + weather + "_cut_copper_stonecutting"
                    );
                }
            }
        }

        //Food
        recipe()
                .input('W', TFCItems.FOOD.get(Food.BEET).get())
                .input('G', Items.BOWL)
                .pattern("WWW", "G  ")
                .shaped(Items.BEETROOT_SOUP);
        recipe()
                .input('R', TFCItems.FOOD.get(Food.COOKED_RABBIT).get())
                .input('C', TFCItems.FOOD.get(Food.CARROT).get())
                .input('B', TFCItems.FOOD.get(Food.BAKED_POTATO).get())
                .input('M', Tags.Items.MUSHROOMS)
                .input('G', Items.BOWL)
                .pattern(" R ", "CBM", " G ")
                .shaped(Items.RABBIT_STEW);
        recipe()
                .input('M', Tags.Items.MUSHROOMS)
                .input('G', Items.BOWL)
                .pattern("MM ", "G  ")
                .shaped(Items.MUSHROOM_STEW);

        recipe()
                .input(ModItems.SWEET_BERRIES_JAR.get())
                .shapeless(ModItems.SWEET_BERRIES_JAR_UNSEALED);
        recipe()
                .input(ModItems.GLOW_BERRIES_JAR.get())
                .shapeless(ModItems.GLOW_BERRIES_JAR_UNSEALED);
        recipe()
                .input(ModItems.SWEET_BERRIES_JAR_UNSEALED.get())
                .shapeless(ModItems.SWEET_BERRIES_JAM);
        recipe()
                .input(ModItems.GLOW_BERRIES_JAR_UNSEALED.get())
                .shapeless(ModItems.GLOW_BERRIES_JAM);

        //Wood
        for (CompatWood wood : CompatWood.values())
        {
            final var blocks = ModBlocks.WOODS.get(wood);
            final var lumber = ModItems.LUMBER.get(wood);

            recipe()
                    .input('W', wood.strippedLog())
                    .input('G', TFCItems.GLUE)
                    .pattern("WGW")
                    .shaped(blocks.get(CompatWood.BlockType.AXLE), 4);
            recipe()
                    .input('L', lumber)
                    .pattern("L L", "L L", "LLL")
                    .shaped(blocks.get(CompatWood.BlockType.BARREL));
            recipe()
                    .input(blocks.get(CompatWood.BlockType.AXLE))
                    .input(TFCItems.METAL_ITEMS.get(Metal.STEEL).get(Metal.ItemType.INGOT).get().asItem())
                    .shapeless(blocks.get(CompatWood.BlockType.BLADED_AXLE));
            recipe()
                    .input('L', lumber)
                    .input('S', wood.strippedLog())
                    .input('M', TFCItems.BRASS_MECHANISMS)
                    .input('A', blocks.get(CompatWood.BlockType.AXLE))
                    .input('R', Tags.Items.DUSTS_REDSTONE)
                    .pattern("LSL", "MAR", "LSL")
                    .shaped(blocks.get(CompatWood.BlockType.CLUTCH), 2);
            recipe()
                    .input('L', lumber)
                    .input('S', wood.strippedLog())
                    .input('A', blocks.get(CompatWood.BlockType.AXLE))
                    .pattern(" S ", "LAL", " S ")
                    .shaped(blocks.get(CompatWood.BlockType.ENCASED_AXLE), 4);
            recipe()
                    .input('L', lumber)
                    .input('M', TFCItems.BRASS_MECHANISMS)
                    .pattern(" L ", "LML", " L ")
                    .shaped(blocks.get(CompatWood.BlockType.GEAR_BOX), 2);
            recipe()
                    .input('P', wood.log())
                    .input('L', lumber)
                    .pattern("PLP", "PLP")
                    .shaped(blocks.get(CompatWood.BlockType.LOG_FENCE), 8);
            recipe()
                    .input('L', lumber)
                    .input('S', Tags.Items.RODS_WOODEN)
                    .pattern("LLL", "LSL", "L L")
                    .shaped(blocks.get(CompatWood.BlockType.LOOM));
            recipe("from_logs")
                    .inputIsPrimary(TFCTags.Items.TOOLS_SAW)
                    .input(woodLogsTagOf(Registries.ITEM, wood))
                    .damageInputs()
                    .shapeless(lumber, 8);
            recipe("from_planks")
                    .inputIsPrimary(TFCTags.Items.TOOLS_SAW)
                    .input(wood.planks())
                    .damageInputs()
                    .shapeless(lumber, 4);
            recipe("from_stairs")
                    .inputIsPrimary(TFCTags.Items.TOOLS_SAW)
                    .input(wood.stair())
                    .damageInputs()
                    .shapeless(lumber, 3);
            recipe("from_slabs")
                    .inputIsPrimary(TFCTags.Items.TOOLS_SAW)
                    .input(wood.slab())
                    .damageInputs()
                    .shapeless(lumber, 2);
            recipe()
                    .input('F', Tags.Items.FEATHERS)
                    .input('D', Tags.Items.DYES_BLACK)
                    .input('S', wood.slab())
                    .input('W', wood.planks())
                    .pattern("F D", "SSS", "W W")
                    .shaped(blocks.get(CompatWood.BlockType.SCRIBING_TABLE));
            recipe()
                    .input('S', Tags.Items.TOOLS_SHEAR)
                    .input('L', Tags.Items.LEATHERS)
                    .input('P', wood.planks())
                    .input('G', wood.log())
                    .pattern(" LS", "PPP", "G G")
                    .shaped(blocks.get(CompatWood.BlockType.SEWING_TABLE));
            recipe()
                    .input('L', lumber)
                    .input('P', wood.planks())
                    .input('S', Tags.Items.RODS_WOODEN)
                    .pattern("PPP", "L L", "S S")
                    .shaped(blocks.get(CompatWood.BlockType.SHELF), 2);
            recipe()
                    .input('L', lumber)
                    .input('S', Tags.Items.RODS_WOODEN)
                    .pattern("  S", " SL", "SLL")
                    .shaped(blocks.get(CompatWood.BlockType.SLUICE));
            recipe()
                    .input('L', vanillaLogsTag(Registries.ITEM, wood))
                    .input('S', TFCTags.Items.TOOLS_SAW)
                    .pattern("LS", "L ")
                    .damageInputs()
                    .source(0, 1)
                    .shaped(ModItems.SUPPORTS.get(wood), 8);
            recipe()
                    .input('L', lumber)
                    .pattern("LLL", "   ", "LLL")
                    .shaped(blocks.get(CompatWood.BlockType.TOOL_RACK));
            recipe()
                    .input('L', lumber)
                    .input('P', wood.planks())
                    .input('A', blocks.get(CompatWood.BlockType.AXLE))
                    .pattern("LPL", "PAP", "LPL")
                    .shaped(blocks.get(CompatWood.BlockType.WATER_WHEEL));

        //Replace Vanilla
            recipe().to2x2(lumber, wood.planks(), 1);

            recipe()
                    .input('L', lumber)
                    .pattern("LL", "LL", "LL")
                    .shaped(wood.door());
            recipe()
                    .input('P', wood.planks())
                    .input('L', lumber)
                    .pattern("PLP", "PLP")
                    .shaped(wood.fence(), 8);
            recipe()
                    .input('P', wood.planks())
                    .input('L', lumber)
                    .pattern("LPL", "LPL")
                    .shaped(wood.fenceGate(), 2);
            recipe()
                    .input('L', lumber)
                    .pattern("LLL", "LLL")
                    .shaped(wood.trapdoor(), 2);
            recipe()
                    .input('L', lumber)
                    .input('S', Tags.Items.RODS_WOODEN)
                    .pattern("LLL", "LLL", " S ")
                    .shaped(wood.sign(), 3);
            recipe()
                    .input('L', lumber)
                    .input('S', Tags.Items.CHAINS)
                    .pattern("S S", "LLL", "LLL")
                    .shaped(wood.hangingSign(), 4);
            recipe()
                    .input('L', lumber)
                    .pattern("LL")
                    .shaped(wood.pressurePlate());
        }
        //Bamboo
        recipe().to2x2(ModItems.BAMBOO_LUMBER, Items.BAMBOO_PLANKS, 1);

        recipe()
                .input('L', ModItems.BAMBOO_LUMBER)
                .pattern("LL", "LL", "LL")
                .shaped(Items.BAMBOO_DOOR);
        recipe()
                .input('P', Items.BAMBOO_PLANKS)
                .input('L', ModItems.BAMBOO_LUMBER)
                .pattern("PLP", "PLP")
                .shaped(Items.BAMBOO_FENCE, 8);
        recipe()
                .input('P', Items.BAMBOO_PLANKS)
                .input('L', ModItems.BAMBOO_LUMBER)
                .pattern("LPL", "LPL")
                .shaped(Items.BAMBOO_FENCE_GATE, 2);
        recipe()
                .input('L', ModItems.BAMBOO_LUMBER)
                .pattern("LLL", "LLL")
                .shaped(Items.BAMBOO_TRAPDOOR, 2);
        recipe()
                .input('L', ModItems.BAMBOO_LUMBER)
                .input('S', Tags.Items.RODS_WOODEN)
                .pattern("LLL", "LLL", " S ")
                .shaped(Items.BAMBOO_SIGN, 3);
        recipe()
                .input('L', ModItems.BAMBOO_LUMBER)
                .pattern("LL")
                .shaped(Items.BAMBOO_PRESSURE_PLATE);
        recipe("from_logs")
                .inputIsPrimary(TFCTags.Items.TOOLS_SAW)
                .input(Items.BAMBOO_BLOCK)
                .damageInputs()
                .shapeless(ModItems.BAMBOO_LUMBER, 8);
        recipe("from_planks")
                .inputIsPrimary(TFCTags.Items.TOOLS_SAW)
                .input(Items.BAMBOO_PLANKS)
                .damageInputs()
                .shapeless(ModItems.BAMBOO_LUMBER, 4);
        recipe("from_stairs")
                .inputIsPrimary(TFCTags.Items.TOOLS_SAW)
                .input(Items.BAMBOO_STAIRS)
                .damageInputs()
                .shapeless(ModItems.BAMBOO_LUMBER, 3);
        recipe("from_slabs")
                .inputIsPrimary(TFCTags.Items.TOOLS_SAW)
                .input(Items.BAMBOO_STAIRS)
                .damageInputs()
                .shapeless(ModItems.BAMBOO_LUMBER, 2);

        //Rock
        recipe().useTool(TFCTags.Items.TOOLS_CHISEL, ModItems.ANDESITE_BRICK, ModBlocks.ROCK_BLOCKS.get(CompatRock.ANDESITE).get(CompatRock.BlockType.LOOSE).get().asItem());
        recipe().useTool(TFCTags.Items.TOOLS_CHISEL, ModItems.GRANITE_BRICK, ModBlocks.ROCK_BLOCKS.get(CompatRock.GRANITE).get(CompatRock.BlockType.LOOSE).get().asItem());
        recipe().useTool(TFCTags.Items.TOOLS_CHISEL, ModItems.DIORITE_BRICK, ModBlocks.ROCK_BLOCKS.get(CompatRock.DIORITE).get(CompatRock.BlockType.LOOSE).get().asItem());
        recipe().useTool(TFCTags.Items.TOOLS_CHISEL, ModItems.DRIPSTONE_BRICK, ModBlocks.ROCK_BLOCKS.get(CompatRock.DRIPSTONE).get(CompatRock.BlockType.LOOSE).get().asItem());
        recipe().useTool(TFCTags.Items.TOOLS_CHISEL, ModItems.CALCITE_BRICK, ModBlocks.ROCK_BLOCKS.get(CompatRock.CALCITE).get(CompatRock.BlockType.LOOSE).get().asItem());

        for(Rock rock : Rock.values()){
            Block hardenedCobbleBlock = ModBlocks.COMPAT_HARDENED_COBBLE.get(rock).get();

            recipe()
                    .input('L', TFCBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE).get().asItem())
                    .input('X', TFCItems.MORTAR)
                    .pattern("LXL", "XLX", "LXL")
                    .shaped(hardenedCobbleBlock, 4);
        }

        for(CompatRock rock : CompatRock.VALUES){
                Item looseItem = ModBlocks.ROCK_BLOCKS.get(rock).get(CompatRock.BlockType.LOOSE).get().asItem();
                Item looseCobbleItem = ModBlocks.ROCK_BLOCKS.get(rock).get(CompatRock.BlockType.LOOSE_COBBLE).get().asItem();
                Item hardenedCobbleItem = ModBlocks.ROCK_BLOCKS.get(rock).get(CompatRock.BlockType.HARDENED_COBBLE).get().asItem();

                recipe()
                        .input('L', looseItem)
                        .pattern("LL ", "LL ")
                        .shaped(looseCobbleItem);

                if(!rock.equals(CompatRock.STONE) && !rock.equals(CompatRock.DEEPSLATE)){
                    recipe()
                            .input('L', looseItem)
                            .input('X', TFCItems.MORTAR)
                            .pattern("LXL", "XLX", "LXL")
                            .shaped(hardenedCobbleItem, 4);
                }

            if(rock.equals(CompatRock.STONE)){
                Item brickItem = ModItems.STONE_BRICK.get();
                Item brickBlockItem = Blocks.STONE_BRICKS.asItem();
                Item rawBlockItem = Blocks.STONE.asItem();

                recipe()
                        .input('L', brickItem)
                        .input('X', TFCItems.MORTAR)
                        .pattern("LXL", "XLX", "LXL")
                        .shaped(brickBlockItem, 4);
                recipe().useTool(TFCTags.Items.TOOLS_HAMMER, brickBlockItem, Items.CRACKED_STONE_BRICKS);
                recipe()
                        .input('L', looseItem)
                        .input('X', TFCItems.MORTAR)
                        .pattern("LXL", "XLX", "LXL")
                        .shaped(Blocks.COBBLESTONE);
                recipe()
                        .input('L', brickItem)
                        .pattern("LL ")
                        .shaped(Blocks.STONE_PRESSURE_PLATE);
                recipe().useTool(TFCTags.Items.TOOLS_CHISEL, brickItem, Items.STONE_BUTTON);
                recipe().useTool(TFCTags.Items.TOOLS_CHISEL, looseItem, brickItem);
                recipe().useTool(TFCTags.Items.TOOLS_CHISEL, rawBlockItem, Items.SMOOTH_STONE);
                recipe().useTool(TFCTags.Items.TOOLS_CHISEL, brickBlockItem, Items.CHISELED_STONE_BRICKS);
            }

            if(rock.equals(CompatRock.ANDESITE) || rock.equals(CompatRock.DIORITE) || rock.equals(CompatRock.GRANITE)){
                Item rawBlockItem = switch(rock){
                    case CompatRock.ANDESITE -> Blocks.ANDESITE.asItem();
                    case CompatRock.DIORITE -> Blocks.DIORITE.asItem();
                    case CompatRock.GRANITE -> Blocks.GRANITE.asItem();
                    default -> Blocks.AIR.asItem();
                };

                Item polishedBlock = switch(rock){
                    case CompatRock.ANDESITE -> Blocks.POLISHED_ANDESITE.asItem();
                    case CompatRock.DIORITE -> Blocks.POLISHED_DIORITE.asItem();
                    case CompatRock.GRANITE -> Blocks.POLISHED_GRANITE.asItem();
                    default -> Blocks.AIR.asItem();
                };

                recipe().useTool(TFCTags.Items.TOOLS_CHISEL, rawBlockItem, polishedBlock);
            }

            if(rock.equals(CompatRock.DEEPSLATE)){
                Item brickItem = ModItems.DEEPSLATE_BRICK.get();
                Item brickBlockItem = Blocks.DEEPSLATE_BRICKS.asItem();
                Item rawBlockItem = Blocks.DEEPSLATE.asItem();

                recipe()
                        .input('L', brickItem)
                        .input('X', TFCItems.MORTAR)
                        .pattern("LXL", "XLX", "LXL")
                        .shaped(brickBlockItem, 4);
                recipe().useTool(TFCTags.Items.TOOLS_HAMMER, brickBlockItem, Items.CRACKED_DEEPSLATE_BRICKS);
                recipe()
                        .input('L', looseItem)
                        .input('X', TFCItems.MORTAR)
                        .pattern("LXL", "XLX", "LXL")
                        .shaped(Blocks.COBBLED_DEEPSLATE);
                recipe().useTool(TFCTags.Items.TOOLS_CHISEL, looseItem, brickItem);
                recipe().useTool(TFCTags.Items.TOOLS_CHISEL, rawBlockItem, Items.POLISHED_DEEPSLATE);
                recipe().useTool(TFCTags.Items.TOOLS_CHISEL, brickBlockItem, Items.CHISELED_DEEPSLATE);
            }

            if(rock.equals(CompatRock.TUFF)){
                Item brickItem = ModItems.TUFF_BRICK.get();
                Item brickBlockItem = Blocks.TUFF_BRICKS.asItem();
                Item rawBlockItem = Blocks.TUFF.asItem();

                recipe()
                        .input('L', brickItem)
                        .input('X', TFCItems.MORTAR)
                        .pattern("LXL", "XLX", "LXL")
                        .shaped(brickBlockItem, 4);
                recipe().useTool(TFCTags.Items.TOOLS_CHISEL, looseItem, brickItem);
                recipe().useTool(TFCTags.Items.TOOLS_CHISEL, rawBlockItem, Items.POLISHED_TUFF);
                recipe().useTool(TFCTags.Items.TOOLS_CHISEL, Items.POLISHED_TUFF, Items.CHISELED_TUFF);
                recipe().useTool(TFCTags.Items.TOOLS_CHISEL, brickBlockItem, Items.CHISELED_TUFF_BRICKS);
            }

            if(rock.equals(CompatRock.BASALT)){
                Item rawBlockItem = Blocks.BASALT.asItem();

                recipe().useTool(TFCTags.Items.TOOLS_CHISEL, rawBlockItem, Items.SMOOTH_BASALT);
                recipe().useTool(TFCTags.Items.TOOLS_CHISEL, Items.SMOOTH_BASALT, Items.POLISHED_BASALT);
            }

            if(rock.equals(CompatRock.BLACKSTONE)){
                Item brickItem = ModItems.POLISHED_BLACKSTONE_BRICK.get();
                Item brickBlockItem = Blocks.POLISHED_BLACKSTONE_BRICKS.asItem();
                Item rawBlockItem = Blocks.BLACKSTONE.asItem();

                recipe()
                        .input('L', brickItem)
                        .input('X', TFCItems.MORTAR)
                        .pattern("LXL", "XLX", "LXL")
                        .shaped(brickBlockItem, 4);
                recipe()
                        .input('L', brickItem)
                        .pattern("LL ")
                        .shaped(Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE);
                recipe().useTool(TFCTags.Items.TOOLS_CHISEL, brickItem, Items.POLISHED_BLACKSTONE_BUTTON);
                recipe().useTool(TFCTags.Items.TOOLS_CHISEL, looseItem, brickItem);
                recipe().useTool(TFCTags.Items.TOOLS_CHISEL, rawBlockItem, Items.POLISHED_BLACKSTONE);
                recipe().useTool(TFCTags.Items.TOOLS_CHISEL, Items.POLISHED_BLACKSTONE, Items.CHISELED_POLISHED_BLACKSTONE);
                recipe().useTool(TFCTags.Items.TOOLS_HAMMER, brickBlockItem, Items.CRACKED_POLISHED_BLACKSTONE_BRICKS);
            }

            if(rock.equals(CompatRock.END_STONE)){
                Item brickItem = ModItems.END_STONE_BRICK.get();
                Item brickBlockItem = Blocks.END_STONE_BRICKS.asItem();
                Item rawBlockItem = Blocks.END_STONE.asItem();

                recipe()
                        .input('L', brickItem)
                        .input('X', TFCItems.MORTAR)
                        .pattern("LXL", "XLX", "LXL")
                        .shaped(brickBlockItem, 4);
                recipe().useTool(TFCTags.Items.TOOLS_CHISEL, looseItem, brickItem);
            }
        }

        //Aqueduct
        for (CompatBricks brickBlock : CompatBricks.values()){
            if(brickBlock.brickItem() != null){
                recipe()
                        .input('L', Objects.requireNonNull(brickBlock.brickItem()))
                        .input('X', TFCItems.MORTAR)
                        .pattern("L L", "XLX")
                        .shaped(ModBlocks.AQUEDUCTS.get(brickBlock).asItem());

            }
        }
        recipe("_from_vines")
                .input(ModBlocks.AQUEDUCTS.get(CompatBricks.STONE_BRICKS))
                .input(Items.VINE)
                .shapeless(ModBlocks.AQUEDUCTS.get(CompatBricks.MOSSY_STONE_BRICKS));
        recipe("_from_moss")
                .input(ModBlocks.AQUEDUCTS.get(CompatBricks.STONE_BRICKS))
                .input(Items.MOSS_BLOCK)
                .shapeless(ModBlocks.AQUEDUCTS.get(CompatBricks.MOSSY_STONE_BRICKS));

        //Nether
        recipe().useTool(TFCTags.Items.TOOLS_CHISEL,
                ModBlocks.ROCK_BLOCKS.get(CompatRock.NETHERRACK).get(CompatRock.BlockType.LOOSE).get().asItem(),
                Items.NETHER_BRICK);
        recipe()
                .input('L', Items.NETHER_BRICK)
                .input('X', TFCItems.MORTAR)
                .pattern("LXL", "XLX", "LXL")
                .shaped(Items.NETHER_BRICKS, 4);
        recipe().useTool(TFCTags.Items.TOOLS_HAMMER, Items.NETHER_BRICKS, Items.CRACKED_NETHER_BRICKS);
        recipe().useTool(TFCTags.Items.TOOLS_CHISEL, Items.NETHER_BRICKS, Items.CHISELED_NETHER_BRICKS);

        //Prismarine
        recipe().useTool(TFCTags.Items.TOOLS_CHISEL, Items.PRISMARINE_SHARD, ModItems.PRISMARINE_BRICK);
        recipe()
                .input('L', Items.PRISMARINE_SHARD)
                .input('X', TFCItems.MORTAR)
                .pattern("LXL", "XLX", "LXL")
                .shaped(Items.PRISMARINE);
        recipe()
                .input('L', ModItems.PRISMARINE_BRICK)
                .input('X', TFCItems.MORTAR)
                .pattern("LXL", "XLX", "LXL")
                .shaped(Items.PRISMARINE_BRICKS);

        //Quartz
        recipe().useTool(TFCTags.Items.TOOLS_CHISEL, Items.QUARTZ, ModItems.QUARTZ_BRICK);
        recipe()
                .input('L', Items.QUARTZ)
                .input('X', TFCItems.MORTAR)
                .pattern("LXL", "XLX", "LXL")
                .shaped(Items.QUARTZ_BLOCK);
        recipe()
                .input('L', ModItems.QUARTZ_BRICK)
                .input('X', TFCItems.MORTAR)
                .pattern("LXL", "XLX", "LXL")
                .shaped(Items.QUARTZ_BRICKS);
        recipe().useTool(TFCTags.Items.TOOLS_CHISEL, Items.QUARTZ_BRICKS, Items.CHISELED_QUARTZ_BLOCK);
        recipe().useTool(TFCTags.Items.TOOLS_CHISEL, Items.QUARTZ_BLOCK, Items.SMOOTH_QUARTZ);

        //Natural
        recipe()
                .input('L', ModTags.Items.COMPAT_MUD)
                .input('X', TFCItems.STRAW)
                .pattern("LX ")
                .shaped(ModBlocks.DRYING_MUD_BRICK, 4);
        recipe()
                .input('L', ModItems.MUD_BRICK)
                .pattern("LL ", "LL ")
                .shaped(Items.MUD_BRICKS);
        recipe()
                .input('L', Items.MUD)
                .pattern("LL ", "LL ")
                .shaped(Items.PACKED_MUD);

        recipe().useTool(TFCTags.Items.TOOLS_CHISEL, Items.SANDSTONE, Items.SMOOTH_SANDSTONE);
        recipe().useTool(TFCTags.Items.TOOLS_CHISEL, Items.SMOOTH_SANDSTONE, Items.CUT_SANDSTONE);
        recipe().useTool(TFCTags.Items.TOOLS_CHISEL, Items.CUT_SANDSTONE, Items.CHISELED_SANDSTONE);

        recipe().useTool(TFCTags.Items.TOOLS_CHISEL, Items.RED_SANDSTONE, Items.SMOOTH_RED_SANDSTONE);
        recipe().useTool(TFCTags.Items.TOOLS_CHISEL, Items.SMOOTH_RED_SANDSTONE, Items.CUT_RED_SANDSTONE);
        recipe().useTool(TFCTags.Items.TOOLS_CHISEL, Items.CUT_RED_SANDSTONE, Items.CHISELED_RED_SANDSTONE);

        //Metal
        recipe()
                .input(ModItems.UNFINISHED_LANTERN.get())
                .input(TFCItems.LAMP_GLASS.get())
                .shapeless(ModBlocks.LANTERN.get().asItem());

        for(Metal metal : Metal.values()){
            if(metal.allParts()){
                Item unfinishedItem = TFCItems.METAL_ITEMS.get(metal).get(Metal.ItemType.UNFINISHED_LAMP).get();
                Item finishedLamp = ModBlocks.COMPAT_LANTERNS.get(metal).get().asItem();

                recipe()
                        .input(unfinishedItem)
                        .input(TFCItems.LAMP_GLASS.get())
                        .shapeless(finishedLamp);
            }
        }

        recipe().useTool(TFCTags.Items.TOOLS_CHISEL, Items.COPPER_BLOCK, Items.CHISELED_COPPER);
        recipe().useTool(TFCTags.Items.TOOLS_CHISEL, Items.EXPOSED_COPPER, Items.EXPOSED_CHISELED_COPPER);
        recipe().useTool(TFCTags.Items.TOOLS_CHISEL, Items.WEATHERED_COPPER, Items.WEATHERED_CHISELED_COPPER);
        recipe().useTool(TFCTags.Items.TOOLS_CHISEL, Items.OXIDIZED_COPPER, Items.OXIDIZED_CHISELED_COPPER);
        recipe()
                .input('S', ingredientOf(Metal.COPPER, Metal.ItemType.SHEET))
                .input('W', ItemTags.PLANKS)
                .input('H', TFCTags.Items.TOOLS_HAMMER)
                .pattern(" SH", "SWS", " S ")
                .damageInputs()
                .source(0, 2)
                .shaped(Blocks.COPPER_BLOCK, 8);
        recipe()
                .input('S', ingredientOf(Metal.GOLD, Metal.ItemType.SHEET))
                .input('W', ItemTags.PLANKS)
                .input('H', TFCTags.Items.TOOLS_HAMMER)
                .pattern(" SH", "SWS", " S ")
                .damageInputs()
                .source(0, 2)
                .shaped(Blocks.GOLD_BLOCK, 8);
        recipe()
                .input('B', Blocks.COPPER_BLOCK)
                .pattern("BBB")
                .shaped(TFCBlocks.METALS.get(Metal.COPPER).get(Metal.BlockType.BLOCK_SLAB), 6);
        recipe()
                .input('B', Blocks.COPPER_BLOCK)
                .pattern("B  ", "BB ", "BBB")
                .shaped(TFCBlocks.METALS.get(Metal.COPPER).get(Metal.BlockType.BLOCK_STAIRS), 8);
        recipe()
                .input('L', TFCBlocks.METALS.get(Metal.COPPER).get(Metal.BlockType.BARS))
                .pattern(" L ", "L L", " L ")
                .shaped(Items.COPPER_GRATE);
        recipe()
                .input('L', Blocks.COPPER_BLOCK)
                .input('X', TFCItems.BRASS_MECHANISMS)
                .input('A', Tags.Items.DUSTS_REDSTONE)
                .pattern(" L ", "LXL", " A ")
                .shaped(Items.COPPER_BULB);
        recipe()
                .input('L', Blocks.EXPOSED_COPPER)
                .input('X', TFCItems.BRASS_MECHANISMS)
                .input('A', Tags.Items.DUSTS_REDSTONE)
                .pattern(" L ", "LXL", " A ")
                .shaped(Items.EXPOSED_COPPER_BULB);
        recipe()
                .input('L', Blocks.WEATHERED_COPPER)
                .input('X', TFCItems.BRASS_MECHANISMS)
                .input('A', Tags.Items.DUSTS_REDSTONE)
                .pattern(" L ", "LXL", " A ")
                .shaped(Items.WEATHERED_COPPER_BULB);
        recipe()
                .input('L', Blocks.OXIDIZED_COPPER)
                .input('X', TFCItems.BRASS_MECHANISMS)
                .input('A', Tags.Items.DUSTS_REDSTONE)
                .pattern(" L ", "LXL", " A ")
                .shaped(Items.OXIDIZED_COPPER_BULB);

        recipe()
                .input('H', ModItems.METAL_ITEMS.get(CompatMetal.NETHERITE).get(CompatMetal.ItemType.PICKAXE_HEAD).get())
                .input('R', Tags.Items.RODS_WOODEN)
                .pattern("H  ", "R  ")
                .shaped(Items.NETHERITE_PICKAXE);
        recipe()
                .input('H', ModItems.METAL_ITEMS.get(CompatMetal.NETHERITE).get(CompatMetal.ItemType.SHOVEL_HEAD).get())
                .input('R', Tags.Items.RODS_WOODEN)
                .pattern("H  ", "R  ")
                .shaped(Items.NETHERITE_SHOVEL);
        recipe()
                .input('H', ModItems.METAL_ITEMS.get(CompatMetal.NETHERITE).get(CompatMetal.ItemType.AXE_HEAD).get())
                .input('R', Tags.Items.RODS_WOODEN)
                .pattern("H  ", "R  ")
                .shaped(Items.NETHERITE_AXE);
        recipe()
                .input('H', ModItems.METAL_ITEMS.get(CompatMetal.NETHERITE).get(CompatMetal.ItemType.HOE_HEAD).get())
                .input('R', Tags.Items.RODS_WOODEN)
                .pattern("H  ", "R  ")
                .shaped(Items.NETHERITE_HOE);
        recipe()
                .input('H', ModItems.METAL_ITEMS.get(CompatMetal.NETHERITE).get(CompatMetal.ItemType.SWORD_BLADE).get())
                .input('R', Tags.Items.RODS_WOODEN)
                .pattern("H  ", "R  ")
                .shaped(Items.NETHERITE_SWORD);
        //Dye
        for(DyeColor color : DyeColor.values()){
            String woolRecipe = "dye_" + color.getSerializedName() + "_wool";
            String carpetRecipe = "dye_" + color.getSerializedName() + "_carpet";
            String glazedTerracottaRecipe = color.getSerializedName() + "_glazed_terracotta";
            String candleRecipe = color.getSerializedName() + "_candle";

            remove(
                    woolRecipe,
                    carpetRecipe,
                    glazedTerracottaRecipe,
                    candleRecipe
            );


        }

        //Devices
        recipe()
                .input('L', ModTags.Items.COMPAT_LUMBER)
                .pattern("LLL", "L L", "LLL")
                .shaped(ModBlocks.COMPAT_CHEST.get().asItem());
        recipe()
                .input(ModBlocks.COMPAT_CHEST.get().asItem())
                .input(Items.TRIPWIRE_HOOK)
                .shapeless(ModBlocks.COMPAT_TRAPPED_CHEST.get().asItem());
        recipe()
                .input(ModBlocks.COMPAT_CHEST.get().asItem())
                .input(Items.MINECART)
                .shapeless(ModItems.COMPAT_CHEST_MINECART.get());

        recipe()
                .input('X', ModTags.Items.COMPAT_LUMBER)
                .input('L', ItemTags.PLANKS)
                .pattern("XX ", "LL ")
                .shaped(Items.CRAFTING_TABLE);
        recipe()
                .input('X', TFCItems.METAL_ITEMS.get(Metal.WROUGHT_IRON).get(Metal.ItemType.SAW_BLADE).get().asItem())
                .input('L', ModTags.Items.COMPAT_LUMBER)
                .input('B', ItemTags.STONE_BRICKS)
                .pattern(" X ", "LBL")
                .shaped(Items.STONECUTTER);
        recipe()
                .input('A', ModTags.Items.COMPAT_LUMBER)
                .input('B', Items.SMOOTH_STONE)
                .input('C', ItemTags.PLANKS)
                .pattern("ABA", "C C")
                .shaped(Items.GRINDSTONE);
        recipe()
                .input('B', ModTags.Items.COMPAT_LUMBER)
                .input('C', Items.CHISELED_BOOKSHELF)
                .pattern("BBB", " C ", " B ")
                .shaped(Items.LECTERN);
        recipe()
                .input('B', Items.BOOK)
                .input('C', Items.CHISELED_BOOKSHELF)
                .pattern("BBB", "BBB", "C  ")
                .shaped(Items.BOOKSHELF);
        recipe()
                .input('B', ModTags.Items.COMPAT_LUMBER)
                .input('C', Tags.Items.RODS_WOODEN)
                .pattern("BBB", "CCC", "BBB")
                .shaped(Items.CHISELED_BOOKSHELF);
        recipe()
                .input('A', ItemTags.PLANKS)
                .input('B', TFCItems.BRASS_MECHANISMS)
                .pattern("AAA", "ABA", "AAA")
                .shaped(Items.JUKEBOX);
        recipe()
                .input('A', Items.BOOK)
                .input('B', TFCItems.GEMS.get(Ore.DIAMOND))
                .input('C', Items.OBSIDIAN)
                .pattern(" A ", "BCB", "CCC")
                .shaped(Items.ENCHANTING_TABLE);
        recipe()
                .input('A', TFCItems.METAL_ITEMS.get(Metal.CAST_IRON).get(Metal.ItemType.SHEET).get().asItem())
                .pattern("A A", "AAA")
                .shaped(Items.CAULDRON);
        recipe()
                .input('L', Items.CHEST)
                .input('X', Items.TRIPWIRE_HOOK)
                .pattern("LX ")
                .shaped(Items.TRAPPED_CHEST);
        recipe()
                .input('L', TFCItems.ORES.get(Ore.AMETHYST).get())
                .pattern("LL ", "LL ")
                .shaped(Items.AMETHYST_BLOCK);

    }

    /**
     * @return A builder for a new recipe with a name inferred from the output.
     */
    private Builder recipe()
    {
        return new Builder((name, r) -> {
            if (name != null) add(name, r);
            else add(r);
        });
    }

    /**
     * @return A builder for a new recipe with a name inferred from the output, plus a suffix. The suffix should not start with an underscore.
     */
    private Builder recipe(String suffix)
    {
        return new Builder((name, r) -> {
            assert !suffix.startsWith("_") : "recipe(String suffix) shouldn't start with an '_', it is added for you!";
            assert name == null : "Cannot use a named recipe and recipe(String suffix) at the same time!";
            add(nameOf(r.getResultItem(lookup()).getItem()) + "_" + suffix, r);
        });
    }

    /**
     * @return A builder for a recipe that will replace a vanilla recipe at {@code name}. Checks for conflicts with removals or other replacements.
     */
    private Builder replace(String name)
    {
        return new Builder((name1, r) -> {
            assert name1 == null : "Cannot used replace() with a named recipe!";
            replace(name, r);
        });
    }

    private <T> TagKey<T> woodLogsTagOf(ResourceKey<Registry<T>> registry, CompatWood wood)
    {
        if(wood == CompatWood.CRIMSON || wood == CompatWood.WARPED){
            return TagKey.create(registry, ResourceLocation.withDefaultNamespace(wood.getSerializedName() + "_stems"));
        } else {
            return TagKey.create(registry, ResourceLocation.withDefaultNamespace(wood.getSerializedName() + "_logs"));
        }
    }

    private void addDecorations(ItemLike input, DecorationBlockHolder output)
    {
        recipe()
                .input('#', input)
                .pattern("###")
                .shaped(output.slab(), 6);
        recipe()
                .input('#', input)
                .pattern("#  ", "## ", "###")
                .shaped(output.stair(), 8);
        recipe()
                .input('#', input)
                .pattern("###", "###")
                .shaped(output.wall(), 6);
    }

    private void addGrains(Food crop, Food grain, Food flour, Food dough, Food bread, Food sandwich, Food jamSandwich)
    {
        final var meal = new MealModifier(
                FoodData.ofFood(1f, 0.5f, 4.5f),
                List.of(
                        // For a 3-ingredient sandwich, average nutritional value is 0.75, matching salads
                        new MealModifier.MealPortion(Optional.of(Ingredient.of(TFCItems.FOOD.get(bread))), 0.675f, 0.5f, 0.5f),
                        new MealModifier.MealPortion(Optional.empty(), 0.8f, 0.8f, 0.8f)
                ));

        recipe()
                .input(notRotten(crop))
                .inputIsPrimary(TFCTags.Items.TOOLS_KNIFE)
                .damageInputs()
                .copyFood()
                .extraProduct(TFCItems.STRAW)
                .shapeless(TFCItems.FOOD.get(grain));

        // Non-jam sandwiches
        for (String pattern : List.of("SSS", "SS ", " SS", "S S", "S  ", " S ", "  S"))
        {
            recipe(pattern.replace(" ", "x").toLowerCase())
                    .input('K', TFCTags.Items.TOOLS_KNIFE)
                    .input('B', notRotten(bread))
                    .input('S', notRotten(Ingredient.of(TFCTags.Items.USABLE_IN_SANDWICH)))
                    .pattern("KB ", pattern, " B ")
                    .damageInputs()
                    .addOutputModifier(meal)
                    .shaped(TFCItems.FOOD.get(sandwich), 1);
        }

        // Two and three ingredient jam sandwiches
        for (String pattern : List.of("JSS", "SJS", "SSJ", "JS ", "SJ ", " JS", " SJ", "S J", "J S"))
        {
            String variant = "_jar";
            for (TagKey<Item> tag : List.of(TFCTags.Items.PRESERVES, TFCTags.Items.JAM))
            {
                recipe("" + pattern.replace(" ", "x").toLowerCase() + variant)
                        .input('K', TFCTags.Items.TOOLS_KNIFE)
                        .input('B', notRotten(bread))
                        .input('S', notRotten(Ingredient.of(TFCTags.Items.USABLE_IN_JAM_SANDWICH)))
                        .input('J', notRotten(Ingredient.of(tag)))
                        .pattern("KB ", pattern, " B ")
                        .damageInputs()
                        .addOutputModifier(meal)
                        .shaped(TFCItems.FOOD.get(jamSandwich));
                variant = "_jam";
            }
        }

        // One item jam sandwiches
        for (String pattern : List.of(" J ", "J  ", "  J"))
        {
            String variant = "_jar";
            for (TagKey<Item> tag : List.of(TFCTags.Items.PRESERVES, TFCTags.Items.JAM))
            {
                recipe("" + pattern.replace(" ", "x").toLowerCase() + variant)
                        .input('K', TFCTags.Items.TOOLS_KNIFE)
                        .input('B', notRotten(bread))
                        .input('J', notRotten(Ingredient.of(tag)))
                        .pattern("KB ", pattern, " B ")
                        .damageInputs()
                        .addOutputModifier(meal)
                        .shaped(TFCItems.FOOD.get(jamSandwich));
                variant = "_jam";
            }
        }

        for (int n = 1; n <= 8; n++)
            recipe("" + n)
                    .inputIsPrimary(FluidContentIngredient.of(Fluids.WATER, 100))
                    .input(notRotten(flour), n)
                    .copyOldestFood()
                    .shapeless(TFCItems.FOOD.get(dough), n);
    }

    private void addTools(Metal.ItemType input, Metal.ItemType output)
    {
        for (Metal metal : Metal.values())
            if (metal.allParts())
                recipe()
                        .input('S', Tags.Items.RODS_WOODEN)
                        .input('X', TFCItems.METAL_ITEMS.get(metal).get(input))
                        .pattern("X", "S")
                        .copyForging()
                        .source(0, 0)
                        .shaped(TFCItems.METAL_ITEMS.get(metal).get(output));
    }

    private void addTools(RockCategory.ItemType input, RockCategory.ItemType output)
    {
        for (RockCategory type : RockCategory.values())
            recipe()
                    .input('S', Tags.Items.RODS_WOODEN)
                    .input('X', TFCItems.ROCK_TOOLS.get(type).get(input))
                    .pattern("X", "S")
                    .shaped(TFCItems.ROCK_TOOLS.get(type).get(output));
    }

    private Ingredient notRotten(Food food)
    {
        return notRotten(Ingredient.of(TFCItems.FOOD.get(food)));
    }

    private Ingredient notRotten(Ingredient food)
    {
        return AndIngredient.of(food, NotRottenIngredient.INSTANCE);
    }

    //Add copper crafting recipes
    public record WeatheringCopperSet(
            Block regular,
            Block exposed,
            Block weathered,
            Block oxidized
    ) {
        // Optional helper: get variant by index (0=regular, 1=exposed, ...)
        public Block get(int stage) {
            return switch (stage) {
                case 0 -> regular;
                case 1 -> exposed;
                case 2 -> weathered;
                case 3 -> oxidized;
                default -> regular;
            };
        }
    }
}



