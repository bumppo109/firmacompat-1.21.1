package com.bumppo109.firma_compat.datagen;

import com.bumppo109.firma_compat.FirmaCompatHelpers;
import com.bumppo109.firma_compat.block.CompatOre;
import com.bumppo109.firma_compat.block.CompatRock;
import com.bumppo109.firma_compat.block.CompatWood;
import com.bumppo109.firma_compat.block.ModBlocks;
import com.bumppo109.firma_compat.datagen.recipe.*;
import com.bumppo109.firma_compat.item.ModItems;
import com.bumppo109.firma_compat.tfcaddon.firmalife.CompatFLBlocks;
import com.eerussianguy.firmalife.common.items.FLItems;
import com.mojang.serialization.Codec;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.common.recipes.CollapseRecipe;
import net.dries007.tfc.common.recipes.HeatingRecipe;
import net.dries007.tfc.common.recipes.LandslideRecipe;
import net.dries007.tfc.common.recipes.ingredients.BlockIngredient;
import net.dries007.tfc.common.recipes.outputs.ItemStackProvider;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
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
        ModKnappingRecipes

{
    final Set<ResourceLocation> removedRecipes = new HashSet<>();
    ModLoadedCondition flLoaded = new ModLoadedCondition("firmalife");
    ModLoadedCondition rnrLoaded = new ModLoadedCondition("rnr");

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
    }

    @Override
    public HolderLookup.Provider lookup()
    {
        return lookup;
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