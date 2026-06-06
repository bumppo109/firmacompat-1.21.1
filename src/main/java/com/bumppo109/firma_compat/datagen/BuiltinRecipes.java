package com.bumppo109.firma_compat.datagen;

import com.bumppo109.firma_compat.FirmaCompatHelpers;
import com.bumppo109.firma_compat.block.CompatOre;
import com.bumppo109.firma_compat.block.CompatRock;
import com.bumppo109.firma_compat.block.CompatWood;
import com.bumppo109.firma_compat.block.ModBlocks;
import com.bumppo109.firma_compat.datagen.helpers.CinchMissingBlocks;
import com.bumppo109.firma_compat.datagen.recipe.*;
import com.bumppo109.firma_compat.item.ModItems;
import com.bumppo109.firma_compat.integration.firmalife.CompatFLBlocks;
import com.bumppo109.firma_compat.integration.rnr.CompatRNR;
import com.bumppo109.firma_compat.integration.rnr.RNRCompatBlocks;
import com.bumppo109.firma_compat.integration.rnr.RNRCompatItems;
import com.eerussianguy.firmalife.common.items.FLItems;
import com.mojang.serialization.Codec;
import com.therighthon.rnr.common.block.RNRBlocks;
import com.therighthon.rnr.common.item.RNRItems;
import com.therighthon.rnr.common.recipe.BlockModRecipe;
import com.therighthon.rnr.common.recipe.MattockRecipe;
import net.cinchtail.cinchsmissingblocks.CinchsMissingBlocks;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.items.Food;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.common.player.ChiselMode;
import net.dries007.tfc.common.recipes.*;
import net.dries007.tfc.common.recipes.ingredients.BlockIngredient;
import net.dries007.tfc.common.recipes.outputs.ItemStackProvider;
import net.dries007.tfc.util.DataGenerationHelpers;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class BuiltinRecipes extends RecipeProvider implements ModRecipes,
        ModCraftingRecipes,
        ModHeatRecipes,
        ModAlloyRecipes,
        ModCastingRecipe,
        ModAnvilRecipes,
        ModBarrelRecipes,
        ModChiselRecipes,
        ModQuernRecipes,
        ModWeldingRecipes,
        ModKnappingRecipes,
        ModPotRecipes

{
    final Set<ResourceLocation> removedRecipes = new HashSet<>();
    ModLoadedCondition flLoaded = new ModLoadedCondition("firmalife");
    ModLoadedCondition rnrLoaded = new ModLoadedCondition("rnr");
    ModLoadedCondition cinchLoaded = new ModLoadedCondition("cinchsmissingblocks");
    ModLoadedCondition beneathLoaded = new ModLoadedCondition("beneath");

    final Codec<Unit> emptyRecipeCodec = Codec.STRING.fieldOf("type")
            .codec()
            .listOf()
            .fieldOf("neoforge:conditions")
            .xmap(l -> Unit.INSTANCE, r -> List.of("neoforge:false"))
            .codec();

    private RecipeOutput output;
    private HolderLookup.Provider lookup;
    private final List<BuiltinItemHeat.MeltingRecipe> meltingRecipes;
    final CompletableFuture<?> before;

    public BuiltinRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, CompletableFuture<?> before, BuiltinItemHeat itemHeat)
    {
        super(output, registries);
        this.before = CompletableFuture.allOf(before, itemHeat.output());
        this.meltingRecipes = itemHeat.meltingRecipes;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output, HolderLookup.Provider lookup)
    {

        this.lookup = lookup;
        return before.thenCompose(v -> CompletableFuture.allOf(
                super.run(output, lookup),
                CompletableFuture.allOf(removedRecipes
                        .stream()
                        .map(id -> DataProvider.saveStable(output, lookup, emptyRecipeCodec, Unit.INSTANCE, recipePathProvider.json(id)))
                        .toArray(CompletableFuture[]::new))
        ));
    }

    @Override
    public void buildRecipes(RecipeOutput output)
    {
        this.output = output;
        craftingRecipes();
        heatRecipes();
        alloyRecipes();
        castingRecipes();
        anvilRecipes();
        barrelRecipes();
        chiselRecipes();
        quernRecipes();
        weldingRecipes();
        knappingRecipes();
        potRecipes();

        // Heat Recipes from Melting
        for (BuiltinItemHeat.MeltingRecipe melt : meltingRecipes)
        {
            add(nameOf(melt.item()), new HeatingRecipe(
                    Ingredient.of(melt.item()),
                    ItemStackProvider.empty(),
                    new FluidStack(fluidOf(melt.metal()), melt.units()),
                    temperatureOf(melt.metal()),
                    false
            ));
        }

        //Landslide Recipes
        add(new LandslideRecipe(BlockIngredient.of(Blocks.RED_SAND), Blocks.RED_SAND.defaultBlockState()));
        add("grass_block", new LandslideRecipe(BlockIngredient.of(Blocks.GRASS_BLOCK), Blocks.DIRT.defaultBlockState()));
        add("podzol", new LandslideRecipe(BlockIngredient.of(Blocks.PODZOL), Blocks.DIRT.defaultBlockState()));
        add("mycelium", new LandslideRecipe(BlockIngredient.of(Blocks.MYCELIUM), Blocks.DIRT.defaultBlockState()));
        add("dirt_path", new LandslideRecipe(BlockIngredient.of(Blocks.DIRT_PATH), Blocks.DIRT.defaultBlockState()));
        add("coarse_dirt", new LandslideRecipe(BlockIngredient.of(Blocks.COARSE_DIRT), Blocks.DIRT.defaultBlockState()));
        add("farmland", new LandslideRecipe(BlockIngredient.of(Blocks.FARMLAND), Blocks.DIRT.defaultBlockState()));
        add("rooted_dirt", new LandslideRecipe(BlockIngredient.of(Blocks.ROOTED_DIRT), Blocks.DIRT.defaultBlockState()));
        add(new LandslideRecipe(BlockIngredient.of(ModBlocks.CLAY_DIRT.get()), ModBlocks.CLAY_DIRT.get().defaultBlockState()));
        add("clay_grass_block", new LandslideRecipe(BlockIngredient.of(ModBlocks.CLAY_GRASS_BLOCK.get()), ModBlocks.CLAY_DIRT.get().defaultBlockState()));
        add("clay_podzol", new LandslideRecipe(BlockIngredient.of(ModBlocks.CLAY_PODZOL.get()), ModBlocks.CLAY_DIRT.get().defaultBlockState()));

        for(CompatRock rock : CompatRock.VALUES){
            Block looseCobbleBlock = ModBlocks.ROCK_BLOCKS.get(rock).get(CompatRock.BlockType.LOOSE_COBBLE).get();

            add(new LandslideRecipe(BlockIngredient.of(looseCobbleBlock), looseCobbleBlock.defaultBlockState()));
        }

        //Collapse Recipes
        ModBlocks.ROCK_BLOCKS.forEach((rock, blocks) -> {
            add(new CollapseRecipe(BlockIngredient.of(Stream.of(
                    List.of(
                            rock.rawBlock(),
                            blocks.get(CompatRock.BlockType.HARDENED)
                    ),
                    pivot(ModBlocks.GRADED_ORES.get(rock), CompatOre.Grade.POOR).values(), ModBlocks.ORES.get(rock).values()
            ).flatMap(Collection::stream).map(Supplier::get)), blocks.get(CompatRock.BlockType.LOOSE_COBBLE).get().defaultBlockState()));
            ModBlocks.GRADED_ORES.get(rock).forEach((ore, oreBlocks) -> {
                add(new CollapseRecipe(
                        BlockIngredient.of(oreBlocks.get(CompatOre.Grade.RICH).get()),
                        oreBlocks.get(CompatOre.Grade.NORMAL).get().defaultBlockState()));
                add(new CollapseRecipe(
                        BlockIngredient.of(oreBlocks.get(CompatOre.Grade.NORMAL).get()),
                        oreBlocks.get(CompatOre.Grade.POOR).get().defaultBlockState()));
            });
        });

        //Firmalife Recipes
        //ore collapse
        ModBlocks.ROCK_BLOCKS.forEach((rock, blocks) -> {
            add("fl_loose_" + rock.getSerializedName() + "_cobble", new CollapseRecipe(
                    BlockIngredient.of(CompatFLBlocks.CHROMITE_ORES.get(rock).get(Ore.Grade.POOR).get()),
                    ModBlocks.ROCK_BLOCKS.get(rock).get(CompatRock.BlockType.LOOSE_COBBLE).get().defaultBlockState()), new ModLoadedCondition("firmalife"));
            add(new CollapseRecipe(
                    BlockIngredient.of(CompatFLBlocks.CHROMITE_ORES.get(rock).get(Ore.Grade.RICH).get()),
                    CompatFLBlocks.CHROMITE_ORES.get(rock).get(Ore.Grade.NORMAL).get().defaultBlockState()), new ModLoadedCondition("firmalife"));
            add(new CollapseRecipe(
                    BlockIngredient.of(CompatFLBlocks.CHROMITE_ORES.get(rock).get(Ore.Grade.NORMAL).get()),
                    CompatFLBlocks.CHROMITE_ORES.get(rock).get(Ore.Grade.POOR).get().defaultBlockState()), new ModLoadedCondition("firmalife"));
        });

        //crafting
        for (CompatWood wood : CompatWood.VALUES) {
            String woodName = wood.name().toLowerCase(Locale.ROOT);

            // ───────────────────────────────────────────────
            // 1. Food Shelf
            // ───────────────────────────────────────────────
            {
                Map<Character, Ingredient> key = new LinkedHashMap<>();
                key.put('A', Ingredient.of(wood.planks().asItem()));
                key.put('B', Ingredient.of(ModItems.LUMBER.get(wood)));

                List<String> patternRows = List.of("AAA", "BBB", "AAA");

                ShapedRecipePattern pattern = ShapedRecipePattern.of(key, patternRows);

                ItemStack resultStack = new ItemStack(CompatFLBlocks.FOOD_SHELVES.get(wood).get());

                ShapedRecipe recipe = new ShapedRecipe(
                        wood.getSerializedName(),                             // group (empty = none)
                        CraftingBookCategory.MISC,      // or CraftingBookCategory.BUILDING
                        pattern,
                        resultStack,
                        true                            // showNotification
                );

                add("firmalife/food_shelf_" + woodName, recipe, flLoaded);
            }

            // ───────────────────────────────────────────────
            // 2. Hanger
            // ───────────────────────────────────────────────
            {
                Map<Character, Ingredient> key = new LinkedHashMap<>();
                key.put('A', Ingredient.of(wood.planks().asItem()));
                key.put('B', Ingredient.of(Tags.Items.STRINGS));

                List<String> patternRows = List.of("AAA", " B ", " B ");

                ShapedRecipePattern pattern = ShapedRecipePattern.of(key, patternRows);

                ItemStack resultStack = new ItemStack(CompatFLBlocks.HANGERS.get(wood).get());

                ShapedRecipe recipe = new ShapedRecipe(
                        wood.getSerializedName(),
                        CraftingBookCategory.MISC,
                        pattern,
                        resultStack,
                        true
                );

                add("firmalife/hanger_" + woodName, recipe, flLoaded);
            }

            // ───────────────────────────────────────────────
            // 3. Jarbnet
            // ───────────────────────────────────────────────
            {
                Map<Character, Ingredient> key = new LinkedHashMap<>();
                key.put('A', Ingredient.of(wood.log().asItem()));
                key.put('B', Ingredient.of(TFCItems.METAL_ITEMS.get(Metal.BRASS).get(Metal.ItemType.ROD).get()));
                key.put('C', Ingredient.of(ModItems.LUMBER.get(wood)));

                List<String> patternRows = List.of("A  ", "BCC", "A  ");

                ShapedRecipePattern pattern = ShapedRecipePattern.of(key, patternRows);

                ItemStack resultStack = new ItemStack(CompatFLBlocks.JARBNETS.get(wood).get());

                ShapedRecipe recipe = new ShapedRecipe(wood.getSerializedName(), CraftingBookCategory.MISC, pattern, resultStack, true);

                add("firmalife/jarbnet_" + woodName, recipe, flLoaded);
            }

            // ───────────────────────────────────────────────
            // 4. Keg
            // ───────────────────────────────────────────────
            {
                Map<Character, Ingredient> key = new LinkedHashMap<>();
                key.put('A', Ingredient.of(wood.log().asItem()));
                key.put('B', Ingredient.of(FLItems.BARREL_STAVE));
                key.put('C', Ingredient.of(TFCItems.GLUE));

                List<String> patternRows = List.of("ABA", "BCB", "ABA");

                ShapedRecipePattern pattern = ShapedRecipePattern.of(key, patternRows);

                ItemStack resultStack = new ItemStack(CompatFLBlocks.KEGS.get(wood).get());

                ShapedRecipe recipe = new ShapedRecipe(wood.getSerializedName(), CraftingBookCategory.MISC, pattern, resultStack, true);

                add("firmalife/keg_" + woodName, recipe, flLoaded);
            }

            // ───────────────────────────────────────────────
            // 5. Stomping Barrel
            // ───────────────────────────────────────────────
            {
                Map<Character, Ingredient> key = new LinkedHashMap<>();
                key.put('A', Ingredient.of(ModItems.LUMBER.get(wood)));
                key.put('B', Ingredient.of(TFCItems.GLUE));

                List<String> patternRows = List.of("ABA", "AAA", "BBB");

                ShapedRecipePattern pattern = ShapedRecipePattern.of(key, patternRows);

                ItemStack resultStack = new ItemStack(CompatFLBlocks.STOMPING_BARRELS.get(wood).get());

                ShapedRecipe recipe = new ShapedRecipe(wood.getSerializedName(), CraftingBookCategory.MISC, pattern, resultStack, true);

                add("firmalife/stomping_barrel_" + woodName, recipe, flLoaded);
            }

            // ───────────────────────────────────────────────
            // 6. Barrel Press
            // ───────────────────────────────────────────────
            {
                Map<Character, Ingredient> key = new LinkedHashMap<>();
                key.put('A', Ingredient.of(CompatFLBlocks.STOMPING_BARRELS.get(wood).get().asItem()));
                key.put('B', Ingredient.of(TFCItems.METAL_ITEMS.get(Metal.WROUGHT_IRON).get(Metal.ItemType.ROD).get()));
                key.put('C', Ingredient.of(TFCItems.METAL_ITEMS.get(Metal.WROUGHT_IRON).get(Metal.ItemType.SHEET).get()));
                key.put('D', Ingredient.of(TFCItems.BRASS_MECHANISMS));

                List<String> patternRows = List.of("AB ", "CD ");

                ShapedRecipePattern pattern = ShapedRecipePattern.of(key, patternRows);

                ItemStack resultStack = new ItemStack(CompatFLBlocks.BARREL_PRESSES.get(wood).get());

                ShapedRecipe recipe = new ShapedRecipe(wood.getSerializedName(), CraftingBookCategory.MISC, pattern, resultStack, true);

                add("firmalife/barrel_press_" + woodName, recipe, flLoaded);
            }

            // ───────────────────────────────────────────────
            // 7. Wine Shelf
            // ───────────────────────────────────────────────
            {
                Map<Character, Ingredient> key = new LinkedHashMap<>();
                key.put('A', Ingredient.of(wood.log().asItem()));
                key.put('B', Ingredient.of(FLItems.TREATED_LUMBER));

                List<String> patternRows = List.of("ABA", "ABA", "ABA");

                ShapedRecipePattern pattern = ShapedRecipePattern.of(key, patternRows);

                ItemStack resultStack = new ItemStack(CompatFLBlocks.WINE_SHELVES.get(wood).get());

                ShapedRecipe recipe = new ShapedRecipe("", CraftingBookCategory.MISC, pattern, resultStack, true);

                add("firmalife/wine_shelf_" + woodName, recipe, flLoaded);
            }
        }

        //RNR
        for(CompatRock rock : CompatRock.VALUES){
            Block baseBlock = RNRBlocks.BASE_COURSE.get();
            Block flagstoneBlock = RNRCompatBlocks.ROCK_BLOCKS.get(rock).get(CompatRNR.FLAGSTONE).get();
            Block settBlock = RNRCompatBlocks.ROCK_BLOCKS.get(rock).get(CompatRNR.SETT_ROAD).get();
            Block cobbleBlock = RNRCompatBlocks.ROCK_BLOCKS.get(rock).get(CompatRNR.COBBLED_ROAD).get();

            Item flagstoneItem = RNRCompatItems.FLAGSTONE.get(rock).get();
            Item brickItem = rock.brickItem().get();
            Item cobbleItem = ModBlocks.ROCK_BLOCKS.get(rock).get(CompatRock.BlockType.LOOSE).get().asItem();

            add(new BlockModRecipe(Ingredient.of(cobbleItem), BlockIngredient.of(baseBlock), cobbleBlock.defaultBlockState(), true), rnrLoaded);

            mattock(BlockIngredient.of(new Block[]{(Block)((ModBlocks.Id)((Map)RNRCompatBlocks.ROCK_BLOCKS.get(rock)).get(CompatRNR.COBBLED_ROAD)).get()}),
                    ((Block)((ModBlocks.Id)((Map)RNRCompatBlocks.ROCK_STAIRS.get(rock)).get(CompatRNR.COBBLED_ROAD)).get()).defaultBlockState(),
                    ChiselMode.STAIR, "stair", rnrLoaded);
            mattock(BlockIngredient.of(new Block[]{(Block)((ModBlocks.Id)((Map)RNRCompatBlocks.ROCK_BLOCKS.get(rock)).get(CompatRNR.COBBLED_ROAD)).get()}),
                    ((Block)((ModBlocks.Id)((Map)RNRCompatBlocks.ROCK_SLABS.get(rock)).get(CompatRNR.COBBLED_ROAD)).get()).defaultBlockState(),
                    ChiselMode.SLAB, "slab", rnrLoaded);

            //Flagstone Items
            Ingredient primaryInput = Ingredient.of(rock.rawBlock().get());
            Ingredient tool = Ingredient.of(TFCTags.Items.TOOLS_CHISEL);
            ItemStack resultStack = new ItemStack(flagstoneItem).copyWithCount(12);

            NonNullList<Ingredient> ingredients = NonNullList.create();
            ingredients.add(primaryInput);   // the raw block
            ingredients.add(tool);           // the chisel

            AdvancedShapelessRecipe recipe = new AdvancedShapelessRecipe(
                    ingredients,
                    ItemStackProvider.of(resultStack),// result as ItemStackProvider
                    Optional.empty(),                            // no special remainder (or add if needed)
                    Optional.of(primaryInput)                 // primary ingredient = the block being chiseled
            );
            add("rnr/" + rock.getSerializedName() + "_flagstone", recipe, rnrLoaded);

            add(new BlockModRecipe(Ingredient.of(flagstoneItem), BlockIngredient.of(baseBlock), flagstoneBlock.defaultBlockState(), true), rnrLoaded);

            mattock(BlockIngredient.of(new Block[]{(Block)((ModBlocks.Id)((Map)RNRCompatBlocks.ROCK_BLOCKS.get(rock)).get(CompatRNR.FLAGSTONE)).get()}),
                    ((Block)((ModBlocks.Id)((Map)RNRCompatBlocks.ROCK_STAIRS.get(rock)).get(CompatRNR.FLAGSTONE)).get()).defaultBlockState(),
                    ChiselMode.STAIR, "stair", rnrLoaded);
            mattock(BlockIngredient.of(new Block[]{(Block)((ModBlocks.Id)((Map)RNRCompatBlocks.ROCK_BLOCKS.get(rock)).get(CompatRNR.FLAGSTONE)).get()}),
                    ((Block)((ModBlocks.Id)((Map)RNRCompatBlocks.ROCK_SLABS.get(rock)).get(CompatRNR.FLAGSTONE)).get()).defaultBlockState(),
                    ChiselMode.SLAB, "slab", rnrLoaded);

            if(rock.equals(CompatRock.NETHERRACK)) continue;

            add(new BlockModRecipe(Ingredient.of(brickItem), BlockIngredient.of(baseBlock), settBlock.defaultBlockState(), true), rnrLoaded);

            //Mattock Recipe
            mattock(BlockIngredient.of(new Block[]{(Block)((ModBlocks.Id)((Map)RNRCompatBlocks.ROCK_BLOCKS.get(rock)).get(CompatRNR.SETT_ROAD)).get()}),
                    ((Block)((ModBlocks.Id)((Map)RNRCompatBlocks.ROCK_STAIRS.get(rock)).get(CompatRNR.SETT_ROAD)).get()).defaultBlockState(),
                    ChiselMode.STAIR, "stair", rnrLoaded);
            mattock(BlockIngredient.of(new Block[]{(Block)((ModBlocks.Id)((Map)RNRCompatBlocks.ROCK_BLOCKS.get(rock)).get(CompatRNR.SETT_ROAD)).get()}),
                    ((Block)((ModBlocks.Id)((Map)RNRCompatBlocks.ROCK_SLABS.get(rock)).get(CompatRNR.SETT_ROAD)).get()).defaultBlockState(),
                    ChiselMode.SLAB, "slab", rnrLoaded);
        }
        Block baseBlock = RNRBlocks.BASE_COURSE.get();
        Block overheightGravel = RNRCompatBlocks.OVER_HEIGHT_GRAVEL.get();
        Block tampedDirt = RNRCompatBlocks.TAMPED_DIRT.get();
        Block tampedMud = RNRCompatBlocks.TAMPED_MUD.get();
        Block gravelBlock = RNRCompatBlocks.GRAVEL_ROAD.get();

        mattock(BlockIngredient.of(RNRCompatBlocks.GRAVEL_ROAD.get()), RNRCompatBlocks.GRAVEL_ROAD_SLAB.get().defaultBlockState(),
                ChiselMode.SLAB, "slab", rnrLoaded);
        mattock(BlockIngredient.of(RNRCompatBlocks.GRAVEL_ROAD.get()), RNRCompatBlocks.GRAVEL_ROAD_STAIRS.get().defaultBlockState(),
                ChiselMode.STAIR, "stair", rnrLoaded);
        mattock(BlockIngredient.of(RNRCompatBlocks.OVER_HEIGHT_GRAVEL.get()), RNRCompatBlocks.MACADAM_ROAD.get().defaultBlockState(),
                ChiselMode.SMOOTH, "smooth", rnrLoaded);
        mattock(BlockIngredient.of(RNRCompatBlocks.MACADAM_ROAD.get()), RNRCompatBlocks.MACADAM_ROAD_STAIRS.get().defaultBlockState(),
                ChiselMode.STAIR, "stair", rnrLoaded);
        mattock(BlockIngredient.of(RNRCompatBlocks.MACADAM_ROAD.get()), RNRCompatBlocks.MACADAM_ROAD_SLAB.get().defaultBlockState(),
                ChiselMode.SLAB, "slab", rnrLoaded);

        add(new BlockModRecipe(Ingredient.of(RNRCompatItems.GRAVEL_FILL.get()), BlockIngredient.of(baseBlock), gravelBlock.defaultBlockState(), true), rnrLoaded);
        add(new BlockModRecipe(Ingredient.of(RNRCompatItems.GRAVEL_FILL.get()), BlockIngredient.of(gravelBlock), overheightGravel.defaultBlockState(), true), rnrLoaded);

        add(new BlockModRecipe(Ingredient.of(RNRItems.CRUSHED_BASE_COURSE.get()), BlockIngredient.of(tampedDirt), baseBlock.defaultBlockState(), true), rnrLoaded);
        add(new BlockModRecipe(Ingredient.of(RNRItems.CRUSHED_BASE_COURSE.get()), BlockIngredient.of(tampedMud), tampedDirt.defaultBlockState(), true), rnrLoaded);

        mattock(BlockIngredient.of(Blocks.GRASS_BLOCK), RNRCompatBlocks.TAMPED_DIRT.get().defaultBlockState(),
                ChiselMode.SMOOTH, "from_grass_block", rnrLoaded);
        mattock(BlockIngredient.of(Blocks.PODZOL), RNRCompatBlocks.TAMPED_DIRT.get().defaultBlockState(),
                ChiselMode.SMOOTH, "from_podzol", rnrLoaded);
        mattock(BlockIngredient.of(Blocks.MYCELIUM), RNRCompatBlocks.TAMPED_DIRT.get().defaultBlockState(),
                ChiselMode.SMOOTH, "from_mycelium", rnrLoaded);
        mattock(BlockIngredient.of(Blocks.DIRT), RNRCompatBlocks.TAMPED_DIRT.get().defaultBlockState(),
                ChiselMode.SMOOTH, "from_dirt", rnrLoaded);
        mattock(BlockIngredient.of(Blocks.COARSE_DIRT), RNRCompatBlocks.TAMPED_DIRT.get().defaultBlockState(),
                ChiselMode.SMOOTH, "from_coarse", rnrLoaded);
        mattock(BlockIngredient.of(Blocks.FARMLAND), RNRCompatBlocks.TAMPED_DIRT.get().defaultBlockState(),
                ChiselMode.SMOOTH, "from_farmland", rnrLoaded);
        mattock(BlockIngredient.of(ModBlocks.COMPAT_FARMLAND.get()), RNRCompatBlocks.TAMPED_DIRT.get().defaultBlockState(),
                ChiselMode.SMOOTH, "from_compat_farmland", rnrLoaded);

        mattock(BlockIngredient.of(Blocks.MUD), RNRCompatBlocks.TAMPED_MUD.get().defaultBlockState(),
                ChiselMode.SMOOTH, "smooth", rnrLoaded);

        for(CompatWood wood : CompatWood.VALUES){
            Block shingleBlock = RNRCompatBlocks.WOOD_SHINGLE_ROOFS.get(wood).get();
            Block shingleStair = RNRCompatBlocks.WOOD_SHINGLE_ROOF_STAIRS.get(wood).get();
            Block shingleSlab = RNRCompatBlocks.WOOD_SHINGLE_ROOF_SLABS.get(wood).get();
            Item shingleItem = RNRCompatItems.SHINGLE.get(wood).get();

            add(new BlockModRecipe(Ingredient.of(shingleItem), BlockIngredient.of(RNRBlocks.ROOF_FRAME.get()), shingleBlock.defaultBlockState(), true), rnrLoaded);
            add(new BlockModRecipe(Ingredient.of(shingleItem), BlockIngredient.of(RNRBlocks.ROOF_FRAME_STAIRS.get()), shingleStair.defaultBlockState(), true), rnrLoaded);
            add(new BlockModRecipe(Ingredient.of(shingleItem), BlockIngredient.of(RNRBlocks.ROOF_FRAME_SLAB.get()), shingleSlab.defaultBlockState(), true), rnrLoaded);

            chisel(BlockIngredient.of(shingleBlock), shingleStair.defaultBlockState(),
                    ChiselMode.STAIR, "stair", rnrLoaded);
            chisel(BlockIngredient.of(shingleBlock), shingleSlab.defaultBlockState(),
                    ChiselMode.SLAB, "slab", rnrLoaded);

            //shingle Items
            Ingredient primaryInput = Ingredient.of(wood.log());
            Ingredient tool = Ingredient.of(TFCTags.Items.TOOLS_CHISEL);
            ItemStack resultStack = new ItemStack(shingleItem).copyWithCount(12);

            NonNullList<Ingredient> ingredients = NonNullList.create();
            ingredients.add(primaryInput);   // the raw block
            ingredients.add(tool);           // the chisel

            AdvancedShapelessRecipe recipe = new AdvancedShapelessRecipe(
                    ingredients,
                    ItemStackProvider.of(resultStack),// result as ItemStackProvider
                    Optional.empty(),                            // no special remainder (or add if needed)
                    Optional.of(primaryInput)                 // primary ingredient = the block being chiseled
            );
            add("rnr/" + wood.getSerializedName() + "_shingle", recipe, rnrLoaded);
        }

        //Cincs Missing Blocks
        recipe()
                .input('B', ModItems.ANDESITE_BRICK.get())
                .input('M', TFCItems.MORTAR.get())
                .pattern("BMB", "MBM", "BMB")
                .shaped(net.cinchtail.cinchsmissingblocks.block.ModBlocks.ANDESITE_BRICKS.get().asItem());
        recipe()
                .input('B', ModItems.GRANITE_BRICK.get())
                .input('M', TFCItems.MORTAR.get())
                .pattern("BMB", "MBM", "BMB")
                .shaped(net.cinchtail.cinchsmissingblocks.block.ModBlocks.GRANITE_BRICKS.get().asItem());
        recipe()
                .input('B', ModItems.DIORITE_BRICK.get())
                .input('M', TFCItems.MORTAR.get())
                .pattern("BMB", "MBM", "BMB")
                .shaped(net.cinchtail.cinchsmissingblocks.block.ModBlocks.DIORITE_BRICKS.get().asItem());
        recipe()
                .input('B', ModItems.DRIPSTONE_BRICK.get())
                .input('M', TFCItems.MORTAR.get())
                .pattern("BMB", "MBM", "BMB")
                .shaped(net.cinchtail.cinchsmissingblocks.block.ModBlocks.DRIPSTONE_BRICKS.get().asItem());
        recipe()
                .input('B', ModItems.CALCITE_BRICK.get())
                .input('M', TFCItems.MORTAR.get())
                .pattern("BMB", "MBM", "BMB")
                .shaped(net.cinchtail.cinchsmissingblocks.block.ModBlocks.CALCITE_BRICKS.get().asItem());

        //tool variants
        for (var set : CinchMissingBlocks.BRICK_SETS) {
            Block bricks   = set.bricks();
            Block cracked  = set.cracked();
            Block chiseled = set.chiseled();

            String crackedId = BuiltInRegistries.BLOCK.getKey(cracked).getPath();
            String chiseledId = BuiltInRegistries.BLOCK.getKey(chiseled).getPath();

            Ingredient primaryInput = Ingredient.of(bricks);
            Ingredient hammer = Ingredient.of(TFCTags.Items.TOOLS_HAMMER);
            Ingredient chisel = Ingredient.of(TFCTags.Items.TOOLS_CHISEL);
            ItemStack crackedStack = new ItemStack(cracked.asItem());
            ItemStack chiseledStack = new ItemStack(chiseled.asItem());

            NonNullList<Ingredient> crackedIngredients = NonNullList.create();
            crackedIngredients.add(primaryInput);   // the raw block
            crackedIngredients.add(hammer);           // the hammer

            AdvancedShapelessRecipe crackedRecipe = new AdvancedShapelessRecipe(
                    crackedIngredients,
                    ItemStackProvider.of(crackedStack),// result as ItemStackProvider
                    Optional.empty(),                            // no special remainder (or add if needed)
                    Optional.of(primaryInput)                 // primary ingredient = the block being chiseled
            );

            add("cinchsmissingblocks/" + crackedId, crackedRecipe, cinchLoaded);

            NonNullList<Ingredient> chiseledIngredients = NonNullList.create();
            chiseledIngredients.add(primaryInput);   // the raw block
            chiseledIngredients.add(chisel);           // the chisel

            AdvancedShapelessRecipe chiseledRecipe = new AdvancedShapelessRecipe(
                    chiseledIngredients,
                    ItemStackProvider.of(chiseledStack),// result as ItemStackProvider
                    Optional.empty(),                            // no special remainder (or add if needed)
                    Optional.of(primaryInput)                 // primary ingredient = the block being chiseled
            );

            add("cinchsmissingblocks/" + chiseledId, chiseledRecipe, cinchLoaded);

        }
        for (var set : CinchMissingBlocks.BRICK_VARIANTS) {
            Block bricks   = set.bricks();
            Block stair  = set.stair();
            Block slab = set.slab();

            chisel(BlockIngredient.of(bricks), stair.defaultBlockState(), ChiselMode.STAIR, "stair", cinchLoaded);
            chisel(BlockIngredient.of(bricks), slab.defaultBlockState(), ChiselMode.SLAB, "slab", cinchLoaded);
        }
        for (var set : CinchMissingBlocks.RAW_SET) {
            Block raw   = set.raw();
            Block stair  = set.rawStair();
            Block slab = set.rawSlab();

            collapse("from_stair", BlockIngredient.of(stair), slab.defaultBlockState(), cinchLoaded);
            collapse("", BlockIngredient.of(slab), slab.defaultBlockState(), cinchLoaded);
        }

        chisel(BlockIngredient.of(Blocks.CUT_SANDSTONE), net.cinchtail.cinchsmissingblocks.block.ModBlocks.CUT_SANDSTONE_STAIRS.get().defaultBlockState(), ChiselMode.STAIR, "stair", cinchLoaded);
        chisel(BlockIngredient.of(Blocks.CUT_RED_SANDSTONE), net.cinchtail.cinchsmissingblocks.block.ModBlocks.CUT_RED_SANDSTONE_STAIRS.get().defaultBlockState(), ChiselMode.STAIR, "stair", cinchLoaded);

        Ingredient calciteInput = Ingredient.of(Blocks.CALCITE);
        Ingredient dripstoneInput = Ingredient.of(Blocks.DRIPSTONE_BLOCK);
        Ingredient chisel = Ingredient.of(TFCTags.Items.TOOLS_CHISEL);
        ItemStack polishedCalciteStack = new ItemStack(net.cinchtail.cinchsmissingblocks.block.ModBlocks.POLISHED_CALCITE.get().asItem());
        ItemStack polishedDripstoneStack = new ItemStack(net.cinchtail.cinchsmissingblocks.block.ModBlocks.POLISHED_DRIPSTONE.get().asItem());

        NonNullList<Ingredient> calciteIngredients = NonNullList.create();
        calciteIngredients.add(calciteInput);   // the raw block
        calciteIngredients.add(chisel);           // the chisel

        NonNullList<Ingredient> dripstoneIngredients = NonNullList.create();
        dripstoneIngredients.add(dripstoneInput);   // the raw block
        dripstoneIngredients.add(chisel);           // the chisel

        AdvancedShapelessRecipe polishedCalciteRecipe = new AdvancedShapelessRecipe(
                calciteIngredients,
                ItemStackProvider.of(polishedCalciteStack),// result as ItemStackProvider
                Optional.empty(),                            // no special remainder (or add if needed)
                Optional.of(calciteInput)                 // primary ingredient = the block being chiseled
        );
        AdvancedShapelessRecipe polishedDripstoneRecipe = new AdvancedShapelessRecipe(
                dripstoneIngredients,
                ItemStackProvider.of(polishedDripstoneStack),// result as ItemStackProvider
                Optional.empty(),                            // no special remainder (or add if needed)
                Optional.of(dripstoneInput)                 // primary ingredient = the block being chiseled
        );

        add("cinchsmissingblocks/polished_calcite", polishedCalciteRecipe, cinchLoaded);
        add("cinchsmissingblocks/polished_dripstone", polishedDripstoneRecipe, cinchLoaded);
    }

    @Override
    public HolderLookup.Provider lookup()
    {
        return lookup;
    }

    private void mattock(BlockIngredient in, BlockState out, Holder<ChiselMode> mode, String suffix) {
        this.add("mattock", this.nameOf(out.getBlock().asItem()) + (Objects.equals(suffix, "") ? "" : "_") + suffix, new MattockRecipe(in, out, (ChiselMode)mode.value(), ItemStackProvider.empty()));
    }

    private void mattock(BlockIngredient in, BlockState out, Holder<ChiselMode> mode, String suffix, ICondition... conditions) {
        this.add("mattock",
                this.nameOf(out.getBlock().asItem()) + (Objects.equals(suffix, "") ? "" : "_") + suffix,
                new MattockRecipe(in, out, mode.value(), ItemStackProvider.empty()),
                conditions);
    }

    private void chisel(BlockIngredient in, BlockState out, Holder<ChiselMode> mode, String suffix, ICondition... conditions) {
        this.add("chisel",
                this.nameOf(out.getBlock().asItem()) + (Objects.equals(suffix, "") ? "" : "_") + suffix,
                new ChiselRecipe(in, out, mode.value(), ItemStackProvider.empty()),
                conditions);
    }

    private void collapse(String suffix, BlockIngredient in, BlockState out, ICondition... conditions) {
        this.add(this.nameOf(out.getBlock().asItem()) + (Objects.equals(suffix, "") ? "" : "_") + suffix,
                new CollapseRecipe(in, out), conditions);
    }

    /**
     * @return A builder for a new recipe with a name inferred from the output.
     */
    private DataGenerationHelpers.Builder recipe()
    {
        return new DataGenerationHelpers.Builder((name, r) -> {
            if (name != null) add(name, r);
            else add(r);
        });
    }

    @Override
    public void add(String prefix, String name, Recipe<?> recipe)
    {
        output.accept(FirmaCompatHelpers.modIdentifier((prefix + "/" + name).toLowerCase(Locale.ROOT)), recipe, null);
    }

    public void add(Recipe<?> recipe, ICondition... conditions) {
        // Uses the default chain: add(nameOf(recipe)) → add(prefix, name, recipe)
        add(nameOf(recipe), recipe, conditions);
    }

    public void add(String name, Recipe<?> recipe, ICondition... conditions) {
        // Uses: add(prefix from type, name, recipe, conditions)
        add(Objects.requireNonNull(BuiltInRegistries.RECIPE_TYPE.getKey(recipe.getType()), "No recipe type").getPath(),
                name,
                recipe,
                conditions);
    }

    public void add(String prefix, String name, Recipe<?> recipe, ICondition... conditions) {
        ResourceLocation id = FirmaCompatHelpers.modIdentifier((prefix + "/" + name).toLowerCase(Locale.ROOT));
        this.output.accept(id, recipe, null, conditions);  // ← passes varargs conditions
    }

    @Override
    public void remove(String... names)
    {
        for (String name : names)
        {
            final ResourceLocation id = Helpers.identifierMC(name);
            removedRecipes.add(id);
        }
    }

    //TODO - ?
    @Override
    public void replace(String name, Recipe<?> recipe) {}

}