package com.bumppo109.firma_compat.dynamic.everycompat;

import com.bumppo109.firma_compat.FirmaCompat;
import com.bumppo109.firma_compat.block.CompatRock;
import com.bumppo109.firma_compat.integration.rnr.CompatRNR;
import com.bumppo109.firma_compat.integration.rnr.RNRCompatBlocks;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.therighthon.rnr.common.RNRTags;
import com.therighthon.rnr.common.block.PathHeightBlock;
import com.therighthon.rnr.common.block.PathSlabBlock;
import com.therighthon.rnr.common.block.PathStairBlock;
import com.therighthon.rnr.common.block.RNRBlocks;
import net.dries007.tfc.common.TFCTags;
import net.mehvahdjukaar.every_compat.EveryCompat;
import net.mehvahdjukaar.every_compat.api.ItemOnlyEntrySet;
import net.mehvahdjukaar.every_compat.api.PaletteStrategies;
import net.mehvahdjukaar.every_compat.api.SimpleEntrySet;
import net.mehvahdjukaar.moonlight.api.resources.ResType;
import net.mehvahdjukaar.moonlight.api.resources.pack.ResourceGenTask;
import net.mehvahdjukaar.moonlight.api.resources.pack.ResourceSink;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.mehvahdjukaar.stone_zone.StoneZone;
import net.mehvahdjukaar.stone_zone.api.StoneZoneModule;
import net.mehvahdjukaar.stone_zone.api.set.stone.StoneType;
import net.mehvahdjukaar.stone_zone.api.set.stone.StoneTypeRegistry;
import net.mehvahdjukaar.stone_zone.api.set.stone.VanillaStoneChildKeys;
import net.mehvahdjukaar.stone_zone.api.set.stone.VanillaStoneTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;

public class RNRStoneZoneModule extends StoneZoneModule {

    public final ItemOnlyEntrySet<StoneType, Item> FLAGSTONE;
    public final SimpleEntrySet<StoneType, Block> FLAGSTONES;
    public final SimpleEntrySet<StoneType, Block> FLAGSTONE_STAIRS;
    public final SimpleEntrySet<StoneType, Block> FLAGSTONE_SLAB;
    public final SimpleEntrySet<StoneType, Block> COBBLED_ROAD;
    public final SimpleEntrySet<StoneType, Block> COBBLED_ROAD_STAIRS;
    public final SimpleEntrySet<StoneType, Block> COBBLED_ROAD_SLAB;
    public final SimpleEntrySet<StoneType, Block> SETT_ROAD;
    public final SimpleEntrySet<StoneType, Block> SETT_ROAD_STAIRS;
    public final SimpleEntrySet<StoneType, Block> SETT_ROAD_SLAB;

    public RNRStoneZoneModule() {
        super(FirmaCompat.MODID, FirmaCompat.MODID);

        ResourceKey<CreativeModeTab> tab = CreativeModeTabs.BUILDING_BLOCKS;

        FLAGSTONE = ItemOnlyEntrySet.builder(StoneType.class, "flagstone",
                        getModItem("stone_flagstone"), () -> VanillaStoneTypes.STONE,
                        w -> new Item(new Item.Properties())
                )
                .addTexture(modRes("item/stone_flagstone"), PaletteStrategies.MAIN_CHILD)
                .addTag(RNRTags.Items.FLAGSTONE_ROAD_ITEMS, Registries.ITEM)
                .excludeBlockTypes("tfc:.*")
                .setTabKey(tab)
                .build();
        this.addEntry(FLAGSTONE);

        FLAGSTONES = SimpleEntrySet.builder(StoneType.class, "flagstones",
                        getModBlock("stone_flagstones"), () -> VanillaStoneTypes.STONE,
                        stoneType -> new PathHeightBlock(Utils.copyPropertySafe(RNRCompatBlocks.ROCK_BLOCKS.get(CompatRock.STONE).get(CompatRNR.FLAGSTONE).get()))
                )
                .addTag(BlockTags.MINEABLE_WITH_PICKAXE, Registries.BLOCK)
                .addTag(TFCTags.Blocks.CAN_LANDSLIDE, Registries.BLOCK)
                .addTag(TFCTags.Blocks.SUPPORTS_LANDSLIDE, Registries.BLOCK)
                .addTag(RNRTags.Blocks.FLAGSTONE_ROAD_BLOCKS, Registries.BLOCK)
                .addTexture(modRes("block/stone_flagstones"), PaletteStrategies.MAIN_CHILD)
                .dropSelf()
                .setTabKey(tab)
                .excludeBlockTypes("tfc:.*")
                .build();
        this.addEntry(FLAGSTONES);

        FLAGSTONE_STAIRS = SimpleEntrySet.builder(StoneType.class, "flagstone_stairs",
                        getModBlock("stone_flagstone_stairs"), () -> VanillaStoneTypes.STONE,
                        stoneType -> new PathStairBlock(() -> FLAGSTONE.blocks.get(stoneType).defaultBlockState(),
                                Utils.copyPropertySafe(RNRCompatBlocks.ROCK_BLOCKS.get(CompatRock.STONE).get(CompatRNR.FLAGSTONE).get()))
                )
                .addTag(BlockTags.MINEABLE_WITH_PICKAXE, Registries.BLOCK)
                .addTag(TFCTags.Blocks.CAN_LANDSLIDE, Registries.BLOCK)
                .addTag(TFCTags.Blocks.SUPPORTS_LANDSLIDE, Registries.BLOCK)
                .addTag(RNRTags.Blocks.FLAGSTONE_ROAD_STAIRS, Registries.BLOCK)
                .dropSelf()
                .setTabKey(tab)
                .excludeBlockTypes("tfc:.*")
                .build();
        this.addEntry(FLAGSTONE_STAIRS);

        FLAGSTONE_SLAB = SimpleEntrySet.builder(StoneType.class, "flagstone_slab",
                        getModBlock("stone_flagstone_slab"), () -> VanillaStoneTypes.STONE,
                        stoneType -> new PathSlabBlock(Utils.copyPropertySafe(RNRCompatBlocks.ROCK_BLOCKS.get(CompatRock.STONE).get(CompatRNR.FLAGSTONE).get()))
                )
                .addTag(BlockTags.MINEABLE_WITH_PICKAXE, Registries.BLOCK)
                .addTag(TFCTags.Blocks.CAN_LANDSLIDE, Registries.BLOCK)
                .addTag(TFCTags.Blocks.SUPPORTS_LANDSLIDE, Registries.BLOCK)
                .addTag(RNRTags.Blocks.FLAGSTONE_ROAD_SLABS, Registries.BLOCK)
                .dropSelf()
                .setTabKey(tab)
                .excludeBlockTypes("tfc:.*")
                .build();
        this.addEntry(FLAGSTONE_SLAB);

        COBBLED_ROAD = SimpleEntrySet.builder(StoneType.class, "cobbled_road",
                        getModBlock("stone_cobbled_road"), () -> VanillaStoneTypes.STONE,
                        stoneType -> new PathHeightBlock(Utils.copyPropertySafe(RNRCompatBlocks.ROCK_BLOCKS.get(CompatRock.STONE).get(CompatRNR.COBBLED_ROAD).get()))
                )
                .addTag(BlockTags.MINEABLE_WITH_PICKAXE, Registries.BLOCK)
                .addTag(TFCTags.Blocks.CAN_LANDSLIDE, Registries.BLOCK)
                .addTag(TFCTags.Blocks.SUPPORTS_LANDSLIDE, Registries.BLOCK)
                .addTag(RNRTags.Blocks.COBBLED_ROAD_BLOCKS, Registries.BLOCK)
                .addTexture(modRes("block/loose_stone_cobble"), PaletteStrategies.MAIN_CHILD)
                .dropSelf()
                .setTabKey(tab)
                .excludeBlockTypes("tfc:.*")
                .build();
        this.addEntry(COBBLED_ROAD);

        COBBLED_ROAD_STAIRS = SimpleEntrySet.builder(StoneType.class, "cobbled_road_stairs",
                        getModBlock("stone_cobbled_road_stairs"), () -> VanillaStoneTypes.STONE,
                        stoneType -> new PathStairBlock(() -> FLAGSTONE.blocks.get(stoneType).defaultBlockState(),
                                Utils.copyPropertySafe(RNRCompatBlocks.ROCK_BLOCKS.get(CompatRock.STONE).get(CompatRNR.COBBLED_ROAD).get()))
                )
                .addTag(BlockTags.MINEABLE_WITH_PICKAXE, Registries.BLOCK)
                .addTag(TFCTags.Blocks.CAN_LANDSLIDE, Registries.BLOCK)
                .addTag(TFCTags.Blocks.SUPPORTS_LANDSLIDE, Registries.BLOCK)
                .addTag(RNRTags.Blocks.COBBLED_ROAD_STAIRS, Registries.BLOCK)
                .dropSelf()
                .setTabKey(tab)
                .excludeBlockTypes("tfc:.*")
                .build();
        this.addEntry(COBBLED_ROAD_STAIRS);

        COBBLED_ROAD_SLAB = SimpleEntrySet.builder(StoneType.class, "cobbled_road_slab",
                        getModBlock("stone_cobbled_road_slab"), () -> VanillaStoneTypes.STONE,
                        stoneType -> new PathSlabBlock(Utils.copyPropertySafe(RNRCompatBlocks.ROCK_BLOCKS.get(CompatRock.STONE).get(CompatRNR.COBBLED_ROAD).get()))
                )
                .addTag(BlockTags.MINEABLE_WITH_PICKAXE, Registries.BLOCK)
                .addTag(TFCTags.Blocks.CAN_LANDSLIDE, Registries.BLOCK)
                .addTag(TFCTags.Blocks.SUPPORTS_LANDSLIDE, Registries.BLOCK)
                .addTag(RNRTags.Blocks.COBBLED_ROAD_SLABS, Registries.BLOCK)
                .dropSelf()
                .setTabKey(tab)
                .excludeBlockTypes("tfc:.*")
                .build();
        this.addEntry(COBBLED_ROAD_SLAB);

        SETT_ROAD = SimpleEntrySet.builder(StoneType.class, "sett_road",
                        getModBlock("stone_sett_road"), () -> VanillaStoneTypes.STONE,
                        stoneType -> new PathHeightBlock(Utils.copyPropertySafe(RNRCompatBlocks.ROCK_BLOCKS.get(CompatRock.STONE).get(CompatRNR.SETT_ROAD).get()))
                )
                .requiresChildren(VanillaStoneChildKeys.BRICKS)
                .addTag(BlockTags.MINEABLE_WITH_PICKAXE, Registries.BLOCK)
                .addTag(TFCTags.Blocks.CAN_LANDSLIDE, Registries.BLOCK)
                .addTag(TFCTags.Blocks.SUPPORTS_LANDSLIDE, Registries.BLOCK)
                .addTag(RNRTags.Blocks.SETT_ROAD_BLOCKS, Registries.BLOCK)
                .addTexture(modRes("block/stone_sett_road"), PaletteStrategies.MAIN_CHILD)
                .dropSelf()
                .setTabKey(tab)
                .excludeBlockTypes("tfc:.*")
                .build();
        this.addEntry(SETT_ROAD);

        SETT_ROAD_STAIRS = SimpleEntrySet.builder(StoneType.class, "sett_road_stairs",
                        getModBlock("stone_sett_road_stairs"), () -> VanillaStoneTypes.STONE,
                        stoneType -> new PathStairBlock(() -> FLAGSTONE.blocks.get(stoneType).defaultBlockState(),
                                Utils.copyPropertySafe(RNRCompatBlocks.ROCK_BLOCKS.get(CompatRock.STONE).get(CompatRNR.SETT_ROAD).get()))
                )
                .requiresChildren(VanillaStoneChildKeys.BRICKS)
                .addTag(BlockTags.MINEABLE_WITH_PICKAXE, Registries.BLOCK)
                .addTag(TFCTags.Blocks.CAN_LANDSLIDE, Registries.BLOCK)
                .addTag(TFCTags.Blocks.SUPPORTS_LANDSLIDE, Registries.BLOCK)
                .addTag(RNRTags.Blocks.SETT_ROAD_STAIRS, Registries.BLOCK)
                .dropSelf()
                .setTabKey(tab)
                .excludeBlockTypes("tfc:.*")
                .build();
        this.addEntry(SETT_ROAD_STAIRS);

        SETT_ROAD_SLAB = SimpleEntrySet.builder(StoneType.class, "sett_road_slab",
                        getModBlock("stone_sett_road_slab"), () -> VanillaStoneTypes.STONE,
                        stoneType -> new PathSlabBlock(Utils.copyPropertySafe(RNRCompatBlocks.ROCK_BLOCKS.get(CompatRock.STONE).get(CompatRNR.SETT_ROAD).get()))
                )
                .requiresChildren(VanillaStoneChildKeys.BRICKS)
                .addTag(BlockTags.MINEABLE_WITH_PICKAXE, Registries.BLOCK)
                .addTag(TFCTags.Blocks.CAN_LANDSLIDE, Registries.BLOCK)
                .addTag(TFCTags.Blocks.SUPPORTS_LANDSLIDE, Registries.BLOCK)
                .addTag(RNRTags.Blocks.SETT_ROAD_SLABS, Registries.BLOCK)
                .dropSelf()
                .setTabKey(tab)
                .excludeBlockTypes("tfc:.*")
                .build();
        this.addEntry(SETT_ROAD_SLAB);
    }

    /*
    @Override
    public boolean isEntryAlreadyRegistered(String entrySetId, String blockId, BlockType blockType, Registry<?> registry) {
        return false;
    }
     */

    @Override
    public void addDynamicClientResources(Consumer<ResourceGenTask> executor) {
        super.addDynamicClientResources(executor);

        executor.accept((manager, sink) -> {

        });
    }


    @Override
    // RECIPES, TAGS
    //everycomp log tags formatted -> everycomp:[modid]/[woodType]_logs
    public void addDynamicServerResources(Consumer<ResourceGenTask> executor) {
        super.addDynamicServerResources(executor);

        executor.accept((manager, sink) -> {
            for(StoneType stone : StoneTypeRegistry.INSTANCE){
                if(stone.stone != null){
                    generateBlockModRecipe(sink, stone, COBBLED_ROAD);
                    generateMattockStairRecipes(sink, stone, COBBLED_ROAD, COBBLED_ROAD_STAIRS,"tfc:stair");
                    generateMattockStairRecipes(sink, stone, COBBLED_ROAD, COBBLED_ROAD_SLAB,"tfc:slab");

                    if(FLAGSTONE.items.get(stone) != null){
                        generateToolItemRecipe(sink, stone.stone.asItem(), "c:tools/chisel", FLAGSTONE.items.get(stone), 12, null);
                        generateBlockModRecipe(sink, stone, FLAGSTONES);
                        generateMattockStairRecipes(sink, stone, FLAGSTONES, FLAGSTONE_STAIRS,"tfc:stair");
                        generateMattockStairRecipes(sink, stone, FLAGSTONES, FLAGSTONE_SLAB,"tfc:slab");
                    }
                }
                if(stone.hasChildren(VanillaStoneChildKeys.BRICKS)){
                    generateBlockModRecipe(sink, stone, SETT_ROAD);
                    generateMattockStairRecipes(sink, stone, SETT_ROAD, SETT_ROAD_STAIRS,"tfc:stair");
                    generateMattockStairRecipes(sink, stone, SETT_ROAD, SETT_ROAD_SLAB,"tfc:slab");
                }
            }
        });
    }

    private void generateMattockStairRecipes(
        ResourceSink sink,
        StoneType stone,
        SimpleEntrySet<StoneType, Block> baseEntrySet,
        SimpleEntrySet<StoneType, Block> stairsEntrySet,
        String chiselMode) {

        Block stairs = stairsEntrySet.blocks.get(stone);
        Block base = baseEntrySet.blocks.get(stone);

        if (stairs != null && base != null) {

            ResourceLocation stairsId = Utils.getID(stairs);
            ResourceLocation baseId = Utils.getID(base);

            // Skip if something is wrong / not registered
            if (stairsId != null && baseId != null) {

                JsonObject recipe = new JsonObject();
                recipe.addProperty("type", "rnr:mattock");

                JsonArray ingredient = new JsonArray();
                ingredient.add(baseId.toString());
                recipe.add("ingredient", ingredient);

                recipe.addProperty("mode", chiselMode);

                recipe.addProperty("result", stairsId.toString());

                // Final path example: firma_compat:recipes/mattock/andesite_cobbled_road_stairs.json
                ResourceLocation recipePath = ResourceLocation.fromNamespaceAndPath(FirmaCompat.MODID,"mattock/" + stairsId.getPath());

                sink.addJson(recipePath, recipe, ResType.RECIPES);
            }
        }
    }

    private void generateBlockModRecipe(ResourceSink sink, StoneType stone, SimpleEntrySet<StoneType, Block> baseEntrySet) {
        Block base = baseEntrySet.blocks.get(stone);
        Block baseCourse = RNRBlocks.BASE_COURSE.get();
        Item roadItem = null;

        if(baseEntrySet == FLAGSTONES){
            roadItem = FLAGSTONE.items.get(stone);
        } else if(baseEntrySet == COBBLED_ROAD){
            roadItem = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(StoneZone.MOD_ID, FirmaCompat.MODID + "/" + stone.getNamespace() + "/" + stone.getTypeName() +"_loose"));
        } else if(baseEntrySet == SETT_ROAD){
            roadItem = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(StoneZone.MOD_ID, FirmaCompat.MODID + "/" + stone.getNamespace() + "/" + stone.getTypeName() +"_brick"));
        }

        if (base != null && roadItem != null){
            ResourceLocation baseCourseID = Utils.getID(baseCourse);
            ResourceLocation baseId = Utils.getID(base);

            if (baseCourseID != null && baseId != null){
                JsonObject recipe = new JsonObject();
                recipe.addProperty("type", "rnr:block_mod");

                JsonArray ingredient = new JsonArray();
                ingredient.add(baseCourseID.toString());
                recipe.add("input_block", ingredient);

                JsonObject input_item = new JsonObject();
                input_item.addProperty("item", Utils.getID(roadItem).toString());
                recipe.add("input_item", input_item);

                recipe.addProperty("output_block", baseId.toString());


                // Final path example: firma_compat:recipes/mattock/andesite_cobbled_road_stairs.json
                ResourceLocation recipePath = ResourceLocation.fromNamespaceAndPath(FirmaCompat.MODID,"block_mod/" + baseId.getPath());

                sink.addJson(recipePath, recipe, ResType.RECIPES);
            }
        }
    }

    public void generateToolItemRecipe(
            ResourceSink sink,
            Item inputitem,
            String toolTag,               // e.g. "c:tools/saw"
            Item ouptutItem,
            int count,
            @Nullable String recipeSuffix
    ) {
        String outputItemPath = Utils.getID(Objects.requireNonNull(ouptutItem)).getPath();
        String outputItemNamespace = Utils.getID(Objects.requireNonNull(ouptutItem)).getNamespace();

        if (count < 1) {
            count = 1;
            EveryCompat.LOGGER.warn("Invalid result count {} → clamped to 1 for {}",
                    count, outputItemPath);
        }

        JsonObject recipe = new JsonObject();
        recipe.addProperty("type", "tfc:advanced_shapeless_crafting");

        // Ingredients array (required for shapeless)
        JsonArray ingredients = new JsonArray();

        // The tool is marked as primary_ingredient
        JsonObject toolIngredient = new JsonObject();
        toolIngredient.addProperty("tag", toolTag);
        ingredients.add(toolIngredient);

        // The actual material being processed
        JsonObject materialIngredient = new JsonObject();

        materialIngredient.addProperty("item", Utils.getID(inputitem).toString());

        ingredients.add(materialIngredient);

        recipe.add("ingredients", ingredients);

        // Mark which one is the primary (tool)
        JsonObject primary = new JsonObject();
        primary.addProperty("tag", toolTag);
        recipe.add("primary_ingredient", primary);

        // Remainder → damage the tool
        JsonObject remainder = new JsonObject();
        JsonArray modifiers = new JsonArray();
        JsonObject damageModifier = new JsonObject();
        damageModifier.addProperty("type", "tfc:damage_crafting_remainder");
        modifiers.add(damageModifier);
        remainder.add("modifiers", modifiers);
        recipe.add("remainder", remainder);

        // Result
        JsonObject result = new JsonObject();
        result.addProperty("count", count);

        result.addProperty("id", outputItemNamespace + ":" + outputItemPath);
        recipe.add("result", result);

        // Recipe ID
        String path = "crafting/" + outputItemPath;
        if (recipeSuffix != null && !recipeSuffix.isEmpty()) {
            path += recipeSuffix;
        }

        ResourceLocation recipeId = ResourceLocation.fromNamespaceAndPath(FirmaCompat.MODID, path);

        sink.addJson(recipeId, recipe, ResType.RECIPES);
    }
}
