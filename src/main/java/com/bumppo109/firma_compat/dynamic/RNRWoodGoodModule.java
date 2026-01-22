package com.bumppo109.firma_compat.dynamic;

import com.bumppo109.firma_compat.FirmaCompat;
import com.eerussianguy.firmalife.common.FLTags;
import com.eerussianguy.firmalife.common.blockentities.BarrelPressBlockEntity;
import com.eerussianguy.firmalife.common.blockentities.FLBlockEntities;
import com.eerussianguy.firmalife.common.blockentities.KegBlockEntity;
import com.eerussianguy.firmalife.common.blocks.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Metal;
import net.mehvahdjukaar.every_compat.api.*;
import net.mehvahdjukaar.moonlight.api.resources.ResType;
import net.mehvahdjukaar.moonlight.api.resources.pack.ResourceGenTask;
import net.mehvahdjukaar.moonlight.api.resources.pack.ResourceSink;
import net.mehvahdjukaar.moonlight.api.set.BlockType;
import net.mehvahdjukaar.moonlight.api.set.wood.VanillaWoodTypes;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodTypeRegistry;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.material.PushReaction;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.function.Consumer;

public final class RNRWoodGoodModule extends SimpleModule {

    public final ItemOnlyEntrySet<WoodType, Item> SHINGLE;
    public final SimpleEntrySet<WoodType, Block> WOOD_SHINGLES;
    public final SimpleEntrySet<WoodType, Block> WOOD_SHINGLE_STAIRS;
    public final SimpleEntrySet<WoodType, Block> WOOD_SHINGLE_SLAB;


    public RNRWoodGoodModule() {
        super(FirmaCompat.MODID, FirmaCompat.MODID, FirmaCompat.MODID);

        ResourceKey<CreativeModeTab> tab = CreativeModeTabs.BUILDING_BLOCKS;

        SHINGLE = ItemOnlyEntrySet.builder(WoodType.class, "shingle",
                        getModItem("oak_shingle"), () -> VanillaWoodTypes.OAK,
                        w -> new Item(new Item.Properties())
                )
                .requiresChildren("log")
                .addTexture(modRes("item/oak_shingle"), PaletteStrategies.MAIN_CHILD)
                .setTabKey(tab)
                .excludeBlockTypes("tfc:.*")
                .excludeBlockTypes("afc:.*")
                .build();
        this.addEntry(SHINGLE);

        WOOD_SHINGLES = SimpleEntrySet.builder(WoodType.class, "shingles",
                        getModBlock("oak_shingles"), () -> VanillaWoodTypes.OAK,
                        w -> new Block(Utils.copyPropertySafe(w.planks))
                )
                .requiresChildren("log")
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .addTexture(modRes("block/oak_shingles"), PaletteStrategies.MAIN_CHILD)
                .dropSelf()
                .setTabKey(tab)
                .excludeBlockTypes("tfc:.*").excludeBlockTypes("afc:.*")
                .build();
        this.addEntry(WOOD_SHINGLES);

        WOOD_SHINGLE_STAIRS = SimpleEntrySet.builder(WoodType.class, "shingle_stairs",
                        getModBlock("oak_shingle_stairs"), () -> VanillaWoodTypes.OAK,
                        w -> new StairBlock(WOOD_SHINGLES.blocks.get(w).defaultBlockState(), Utils.copyPropertySafe(w.planks))
                )
                .requiresFromMap(WOOD_SHINGLES.blocks)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .dropSelf()
                .setTabKey(tab)
                .excludeBlockTypes("tfc:.*").excludeBlockTypes("afc:.*")
                .build();
        this.addEntry(WOOD_SHINGLE_STAIRS);

        WOOD_SHINGLE_SLAB = SimpleEntrySet.builder(WoodType.class, "shingle_slab",
                        getModBlock("oak_shingle_slab"), () -> VanillaWoodTypes.OAK,
                        w -> new SlabBlock(Utils.copyPropertySafe(w.planks))
                )
                .requiresFromMap(WOOD_SHINGLES.blocks)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .dropSelf()
                .setTabKey(tab)
                .excludeBlockTypes("tfc:.*").excludeBlockTypes("afc:.*")
                .build();
        this.addEntry(WOOD_SHINGLE_SLAB);
    }

    @Override
    public void addDynamicClientResources(Consumer<ResourceGenTask> executor) {
        super.addDynamicClientResources(executor);

        executor.accept((manager, sink) -> {});
    }

    @Override
    // RECIPES, TAGS
    //everycomp log tags formatted -> everycomp:[modid]/[woodType]_logs
    public void addDynamicServerResources(Consumer<ResourceGenTask> executor) {
        super.addDynamicServerResources(executor);

        executor.accept((manager, sink) -> {
            for(WoodType wood : WoodTypeRegistry.INSTANCE){
                //TODO - recipes
            }
        });
    }

    public void generateFoodShelfRecipe(
            ResourceSink sink,
            WoodType wood,
            @Nullable String suffix
    ) {
        String lumberItem = wood.getTypeName().toLowerCase(Locale.ROOT) + "_lumber";
        String lumberNamespace = wood.getNamespace();
        Item outputItem = Items.ACACIA_BUTTON;
        Item planksItem = wood.getItemOfThis("planks");

        String lumberItemPath = FirmaCompat.MODID + "/" + lumberNamespace + "/" + lumberItem;


        assert outputItem != null;
        String outputItemPath = Utils.getID(outputItem).getPath();
        String outputItemNamespace = Utils.getID(outputItem).getNamespace();

        JsonObject recipe = new JsonObject();
        recipe.addProperty("type", "minecraft:crafting_shaped");
        recipe.addProperty("category", "misc");

        // Key definitions
        JsonObject key = new JsonObject();

        JsonObject lumberKey = new JsonObject();
        lumberKey.addProperty("item", FirmaCompat.MODID + ":" + lumberItemPath);
        key.add("L", lumberKey);

        JsonObject planksKey = new JsonObject();
        assert planksItem != null;
        planksKey.addProperty("item", planksItem.toString());
        key.add("P", planksKey);

        recipe.add("key", key);

        JsonArray pattern = new JsonArray();
        pattern.add("PPP");
        pattern.add("LLL");
        pattern.add("PPP");
        recipe.add("pattern", pattern);

        // Result: 1 door (vanilla wood type)
        JsonObject result = new JsonObject();
        result.addProperty("count", 1);
        result.addProperty("id",  outputItemNamespace + ":" + outputItemPath);
        recipe.add("result", result);

        // Recipe path
        String recipePath = "crafting/" + FirmaCompat.MODID + "/" + outputItemNamespace + "/" + outputItemPath;

        if (suffix != null && !suffix.isEmpty()) {
            recipePath += ("_" + suffix);
        }

        ResourceLocation recipeId = ResourceLocation.fromNamespaceAndPath(FirmaCompat.MODID, recipePath);

        sink.addJson(recipeId, recipe, ResType.RECIPES);
    }
}
