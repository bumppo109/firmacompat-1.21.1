package com.bumppo109.firma_compat.datagen.tags;

import com.bumppo109.firma_compat.FirmaCompat;
import com.bumppo109.firma_compat.block.CompatBricks;
import com.bumppo109.firma_compat.block.CompatRock;
import com.bumppo109.firma_compat.block.CompatWood;
import com.bumppo109.firma_compat.block.ModBlocks;
import com.bumppo109.firma_compat.datagen.ModAccessors;
import com.bumppo109.firma_compat.integration.rnr.CompatRNR;
import com.bumppo109.firma_compat.integration.rnr.RNRCompatBlocks;
import com.bumppo109.firma_compat.util.ModTags;
import com.eerussianguy.firmalife.common.FLTags;
import com.google.common.base.Preconditions;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.DecorationBlockHolder;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.registry.IdHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.bumppo109.firma_compat.util.ModTags.Blocks.MAKES_PRIMITIVE_ANVIL;
import static com.bumppo109.firma_compat.util.ModTags.Blocks.PREVENT_INTERACTION;
import static com.eerussianguy.firmalife.common.FLTags.Blocks.*;
import static com.therighthon.rnr.common.RNRTags.Blocks.*;
import static net.dries007.tfc.common.TFCTags.Blocks.*;
import static net.dries007.tfc.common.TFCTags.Blocks.DIRT;
import static net.minecraft.tags.BlockTags.*;

public class BuiltinBlockTags extends TagsProvider<Block> implements ModAccessors
{
    private final ExistingFileHelper.IResourceType resourceType;

    public BuiltinBlockTags(GatherDataEvent event, CompletableFuture<HolderLookup.Provider> lookup)
    {
        super(event.getGenerator().getPackOutput(), Registries.BLOCK, lookup, FirmaCompat.MODID, event.getExistingFileHelper());
        this.resourceType = new ExistingFileHelper.ResourceType(PackType.SERVER_DATA, ".json", Registries.tagsDirPath(registryKey));
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        //Util
        tag(PREVENT_INTERACTION)
                .add(Blocks.BLAST_FURNACE)
                .add(Blocks.SMOKER)
                .add(Blocks.COMPOSTER)
                .add(Blocks.CAMPFIRE)
                .add(Blocks.SOUL_CAMPFIRE)
                .add(Blocks.FURNACE);

        //consume tool durability
        tag(CONSUMES_TOOL_DURABILITY)
                .add(Blocks.TALL_GRASS)
                .add(Blocks.SHORT_GRASS)
                .add(Blocks.FERN)
                .add(Blocks.LARGE_FERN)
        ;

        tag(LAMPS).add(ModBlocks.LANTERN.get());

        for(Metal metal : Metal.values()) {
            if(metal.allParts()){
                tag(LAMPS).add(ModBlocks.COMPAT_LANTERNS.get(metal).get());
                tag(MINEABLE_WITH_PICKAXE).add(ModBlocks.COMPAT_LANTERNS.get(metal).get());
            }
        }

        tag(HEAT_PASSABLE)
                .add(Blocks.COPPER_GRATE)
                .add(Blocks.EXPOSED_COPPER_GRATE)
                .add(Blocks.WEATHERED_COPPER_GRATE)
                .add(Blocks.OXIDIZED_COPPER_GRATE)
                .add(Blocks.WAXED_COPPER_GRATE)
                .add(Blocks.WAXED_EXPOSED_COPPER_GRATE)
                .add(Blocks.WAXED_WEATHERED_COPPER_GRATE)
                .add(Blocks.WAXED_OXIDIZED_COPPER_GRATE)
        ;

        tag(Tags.Blocks.CHESTS_WOODEN)
                .add(ModBlocks.COMPAT_CHEST.get())
                .add(ModBlocks.COMPAT_TRAPPED_CHEST.get());

        tag(PET_SITS_ON)
                .add(ModBlocks.COMPAT_CHEST.get())
                .add(ModBlocks.COMPAT_TRAPPED_CHEST.get());

        for(Rock rock : Rock.values()){
            Block hardCobbleBlock = ModBlocks.COMPAT_HARDENED_COBBLE.get(rock).get();

            tag(Tags.Blocks.COBBLESTONES_NORMAL).add(hardCobbleBlock);
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(hardCobbleBlock);
        }

        //Wood
        addAllTFCWoods(Wood.BlockType.TWIG, ModTags.Blocks.TWIGS);
        //Mineable
        addAllCompatWoods(CompatWood.BlockType.TOOL_RACK, MINEABLE_WITH_AXE);
        addAllCompatWoods(CompatWood.BlockType.TWIG, MINEABLE_WITH_AXE);
        addAllCompatWoods(CompatWood.BlockType.TWIG, TFCTags.Blocks.CAN_BE_SNOW_PILED);
        addAllCompatWoods(CompatWood.BlockType.LOOM, MINEABLE_WITH_AXE);
        addAllCompatWoods(CompatWood.BlockType.SLUICE, MINEABLE_WITH_AXE);
        addAllCompatWoods(CompatWood.BlockType.BARREL, MINEABLE_WITH_AXE);
        addAllCompatWoods(CompatWood.BlockType.SCRIBING_TABLE, MINEABLE_WITH_AXE);
        addAllCompatWoods(CompatWood.BlockType.SEWING_TABLE, MINEABLE_WITH_AXE);
        addAllCompatWoods(CompatWood.BlockType.SHELF, MINEABLE_WITH_AXE);
        addAllCompatWoods(CompatWood.BlockType.AXLE, MINEABLE_WITH_AXE);
        addAllCompatWoods(CompatWood.BlockType.BLADED_AXLE, MINEABLE_WITH_AXE);
        addAllCompatWoods(CompatWood.BlockType.ENCASED_AXLE, MINEABLE_WITH_AXE);
        addAllCompatWoods(CompatWood.BlockType.CLUTCH, MINEABLE_WITH_AXE);
        addAllCompatWoods(CompatWood.BlockType.GEAR_BOX, MINEABLE_WITH_AXE);
        addAllCompatWoods(CompatWood.BlockType.HORIZONTAL_SUPPORT, MINEABLE_WITH_AXE);
        addAllCompatWoods(CompatWood.BlockType.VERTICAL_SUPPORT, MINEABLE_WITH_AXE);
        addAllCompatWoods(CompatWood.BlockType.WATER_WHEEL, MINEABLE_WITH_AXE);
        //Other
        addAllCompatWoods(CompatWood.BlockType.BARREL, TFCTags.Blocks.CLOCK_READABLE);
        addAllCompatWoods(CompatWood.BlockType.LOG_FENCE, Tags.Blocks.FENCES_WOODEN);
        addAllCompatWoods(CompatWood.BlockType.LOG_FENCE, BlockTags.WOODEN_FENCES);
        addAllCompatWoods(CompatWood.BlockType.HORIZONTAL_SUPPORT, TFCTags.Blocks.SUPPORT_BEAMS);
        addAllCompatWoods(CompatWood.BlockType.VERTICAL_SUPPORT, TFCTags.Blocks.SUPPORT_BEAMS);
        addAllCompatWoods(CompatWood.BlockType.TWIG, ModTags.Blocks.TWIGS);

        //Rock
        addAllCompatRocks(CompatRock.BlockType.HARDENED_COBBLE, ModTags.Blocks.HARDENED_COBBLESTONE);
        addAllCompatRocks(CompatRock.BlockType.HARDENED, STONES_HARDENED);
        addAllCompatRocks(CompatRock.BlockType.HARDENED, BlockTags.MINEABLE_WITH_PICKAXE);
        addAllCompatRocks(CompatRock.BlockType.LOOSE, STONES_LOOSE);
        addAllCompatRocks(CompatRock.BlockType.LOOSE, CAN_BE_SNOW_PILED);
        addAllCompatRocks(CompatRock.BlockType.SPIKE, STONES_SPIKE);
        addAllCompatRocks(CompatRock.BlockType.SPIKE, BlockTags.MINEABLE_WITH_PICKAXE);

        tag(ANVILS).add(ModBlocks.PRIMITIVE_ANVIL.get());

        tag(MAKES_PRIMITIVE_ANVIL)
                .add(Blocks.STONE)
                .add(Blocks.DEEPSLATE)
                .add(Blocks.ANDESITE)
                .add(Blocks.DIORITE)
                .add(Blocks.GRANITE)
        ;

        tag(CAN_LANDSLIDE)
                .add(Blocks.RED_SAND)
                .add(Blocks.GRASS_BLOCK)
                .add(Blocks.DIRT)
                .add(Blocks.DIRT_PATH)
                .add(Blocks.COARSE_DIRT)
                .add(Blocks.ROOTED_DIRT)
                .add(ModBlocks.CASSITERITE_GRAVEL_DEPOSIT.get())
                .add(ModBlocks.NATIVE_GOLD_GRAVEL_DEPOSIT.get())
                .add(ModBlocks.NATIVE_SILVER_GRAVEL_DEPOSIT.get())
                .add(ModBlocks.NATIVE_COPPER_GRAVEL_DEPOSIT.get())
                .add(ModBlocks.CLAY_DIRT.get())
                .add(ModBlocks.CLAY_PODZOL.get())
                .add(ModBlocks.CLAY_GRASS_BLOCK.get())
                .add(ModBlocks.KAOLIN_CLAY_DIRT.get())
                .add(ModBlocks.KAOLIN_CLAY_PODZOL.get())
                .add(ModBlocks.KAOLIN_CLAY_GRASS_BLOCK.get())
                .add(ModBlocks.COMPAT_FARMLAND.get())
                .add(Blocks.FARMLAND)
        ;

        tag(DIRT)
                .add(ModBlocks.CLAY_DIRT.get())
                .add(ModBlocks.CLAY_PODZOL.get())
                .add(ModBlocks.CLAY_GRASS_BLOCK.get())
                .add(ModBlocks.KAOLIN_CLAY_DIRT.get())
                .add(ModBlocks.KAOLIN_CLAY_PODZOL.get())
                .add(ModBlocks.KAOLIN_CLAY_GRASS_BLOCK.get())
                .add(ModBlocks.COMPAT_FARMLAND.get())
                .add(TFCBlocks.PEAT.get())
        ;

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.LANTERN.get())
                .add(ModBlocks.PRIMITIVE_ANVIL.get());

        tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(ModBlocks.CASSITERITE_GRAVEL_DEPOSIT.get())
                .add(ModBlocks.NATIVE_GOLD_GRAVEL_DEPOSIT.get())
                .add(ModBlocks.NATIVE_SILVER_GRAVEL_DEPOSIT.get())
                .add(ModBlocks.NATIVE_COPPER_GRAVEL_DEPOSIT.get())
                .add(ModBlocks.CLAY_DIRT.get())
                .add(ModBlocks.CLAY_GRASS_BLOCK.get())
                .add(ModBlocks.KAOLIN_CLAY_DIRT.get())
                .add(ModBlocks.KAOLIN_CLAY_PODZOL.get())
                .add(ModBlocks.KAOLIN_CLAY_GRASS_BLOCK.get())
                .add(ModBlocks.COMPAT_FARMLAND.get())
        ;

        for (CompatBricks brick : CompatBricks.VALUES)
        {
            Block aqueductBlock = ModBlocks.AQUEDUCTS.get(brick).get();

            tag(AQUEDUCTS).add(aqueductBlock);
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(aqueductBlock);
        }

        for (CompatRock rock : CompatRock.VALUES)
        {
            Block looseCobbleBlock = ModBlocks.ROCK_BLOCKS.get(rock).get(CompatRock.BlockType.LOOSE_COBBLE).get();
            Block hardenedCobbleBlock = ModBlocks.ROCK_BLOCKS.get(rock).get(CompatRock.BlockType.HARDENED_COBBLE).get();

            tag(Tags.Blocks.COBBLESTONES_NORMAL).add(looseCobbleBlock);
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(looseCobbleBlock);
            tag(CAN_LANDSLIDE).add(looseCobbleBlock);

            tag(Tags.Blocks.COBBLESTONES_NORMAL).add(hardenedCobbleBlock);
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(hardenedCobbleBlock);
            tag(STONES_RAW).add(rock.rawBlock().get());
        }

        addAllCompatRocks(CompatRock.BlockType.HARDENED, BREAKS_WHEN_ISOLATED);

        tag(BREAKS_WHEN_ISOLATED)
                .add(Blocks.STONE)
                .add(Blocks.DEEPSLATE)
                .add(Blocks.ANDESITE)
                .add(Blocks.DIORITE)
                .add(Blocks.DRIPSTONE_BLOCK)
                .add(Blocks.GRANITE)
                .add(Blocks.TUFF)
                .add(Blocks.CALCITE)
                .add(Blocks.BLACKSTONE)
                .add(Blocks.END_STONE)
                .add(Blocks.NETHERRACK);

        //Natural
        tag(FARMLANDS).add(ModBlocks.COMPAT_FARMLAND.get());
        tag(NORMAL_FARMLAND).add(ModBlocks.COMPAT_FARMLAND.get());

        // Ore (non-graded)
        ModBlocks.ORES.forEach((rock, oreMap) -> {
            oreMap.forEach((ore, blockId) -> {
                tag(BlockTags.MINEABLE_WITH_PICKAXE).add(blockId.get());
            });
        });

        // Graded ores
        ModBlocks.GRADED_ORES.forEach((rock, oreMap) -> {
            oreMap.forEach((ore, gradeMap) -> {
                gradeMap.forEach((grade, blockId) -> {
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(blockId.get());
                });
            });
        });

        //Firmalife
        for(CompatWood wood : CompatWood.VALUES){
            ResourceLocation foodShelf = ResourceLocation.fromNamespaceAndPath("firma_compat", wood.getSerializedName() + "_food_shelf");
            ResourceLocation hanger = ResourceLocation.fromNamespaceAndPath("firma_compat", wood.getSerializedName() + "_hanger");
            ResourceLocation jarbnet = ResourceLocation.fromNamespaceAndPath("firma_compat", wood.getSerializedName() + "_jarbnet");
            ResourceLocation stompBarrel = ResourceLocation.fromNamespaceAndPath("firma_compat", wood.getSerializedName() + "_stomping_barrel");
            ResourceLocation barrelPress = ResourceLocation.fromNamespaceAndPath("firma_compat", wood.getSerializedName() + "_barrel_press");
            ResourceLocation keg = ResourceLocation.fromNamespaceAndPath("firma_compat", wood.getSerializedName() + "_keg");

            ResourceLocation cfoodShelf = ResourceLocation.fromNamespaceAndPath("firma_compat", "compat_food_shelves");
            ResourceLocation cwineShelf = ResourceLocation.fromNamespaceAndPath("firma_compat", "compat_wine_shelves");
            ResourceLocation changer = ResourceLocation.fromNamespaceAndPath("firma_compat", "compat_hangers");
            ResourceLocation cjarbnet = ResourceLocation.fromNamespaceAndPath("firma_compat", "compat_jarbnets");
            ResourceLocation cstompBarrel = ResourceLocation.fromNamespaceAndPath("firma_compat", "compat_stomping_barrels");
            ResourceLocation cbarrelPress = ResourceLocation.fromNamespaceAndPath("firma_compat", "compat_barrel_presses");
            ResourceLocation ckeg = ResourceLocation.fromNamespaceAndPath("firma_compat", "compat_kegs");

            tag(FOOD_SHELVES).addOptional(foodShelf).addOptionalTag(cfoodShelf);
            tag(FLTags.Blocks.WINE_SHELVES).addOptional(foodShelf).addOptionalTag(cwineShelf);
            tag(HANGERS).addOptional(hanger).addOptionalTag(changer);
            tag(JARBNETS).addOptional(jarbnet).addOptionalTag(cjarbnet);
            tag(STOMPING_BARRELS).addOptional(stompBarrel).addOptionalTag(cstompBarrel);
            tag(BARREL_PRESSES).addOptional(barrelPress).addOptionalTag(cbarrelPress);
            tag(KEGS).addOptional(keg).addOptionalTag(ckeg);

            tag(MINEABLE_WITH_AXE)
                    .addOptional(foodShelf)
                    .addOptional(hanger)
                    .addOptional(jarbnet)
                    .addOptional(stompBarrel)
                    .addOptional(barrelPress)
                    .addOptional(keg);
        }

        for(CompatRock rock : CompatRock.VALUES){
            for(Ore.Grade grade : Ore.Grade.values()){
                ResourceLocation oreRes = ResourceLocation.fromNamespaceAndPath("firma_compat", grade.toString().toLowerCase(Locale.ROOT) + "_" + rock.getSerializedName() + "_chromite_ore");

                tag(MINEABLE_WITH_PICKAXE).addOptional(oreRes);
                tag(CAN_COLLAPSE).addOptional(oreRes);
                tag(CAN_START_COLLAPSE).addOptional(oreRes);
                tag(CAN_TRIGGER_COLLAPSE).addOptional(oreRes);
            }
        }

        //RNR
        for (CompatRock rock : CompatRock.VALUES) {
            // Helper to get the block's ResourceLocation safely (adjust if your map returns RegistryObject)
            Function<CompatRNR, ResourceLocation> getId = variant ->
                    BuiltInRegistries.BLOCK.getKey(RNRCompatBlocks.ROCK_BLOCKS.get(rock).get(variant).get());

            Function<CompatRNR, ResourceLocation> getStairId = variant ->
                    BuiltInRegistries.BLOCK.getKey(RNRCompatBlocks.ROCK_STAIRS.get(rock).get(variant).get());

            Function<CompatRNR, ResourceLocation> getSlabId = variant ->
                    BuiltInRegistries.BLOCK.getKey(RNRCompatBlocks.ROCK_SLABS.get(rock).get(variant).get());

            // ───────────────────────────────────────────────────────
            // Tags that should include the blocks only if they exist
            // ───────────────────────────────────────────────────────

            tag(MINEABLE_WITH_PICKAXE)
                    .addOptional(getId.apply(CompatRNR.FLAGSTONE))
                    .addOptional(getStairId.apply(CompatRNR.FLAGSTONE))
                    .addOptional(getSlabId.apply(CompatRNR.FLAGSTONE))
                    .addOptional(getId.apply(CompatRNR.COBBLED_ROAD))
                    .addOptional(getStairId.apply(CompatRNR.COBBLED_ROAD))
                    .addOptional(getSlabId.apply(CompatRNR.COBBLED_ROAD))
                    .addOptional(getId.apply(CompatRNR.SETT_ROAD))
                    .addOptional(getStairId.apply(CompatRNR.SETT_ROAD))
                    .addOptional(getSlabId.apply(CompatRNR.SETT_ROAD));

            tag(CAN_LANDSLIDE)
                    .addOptional(getId.apply(CompatRNR.FLAGSTONE))
                    .addOptional(getStairId.apply(CompatRNR.FLAGSTONE))
                    .addOptional(getSlabId.apply(CompatRNR.FLAGSTONE))
                    .addOptional(getId.apply(CompatRNR.COBBLED_ROAD))
                    .addOptional(getStairId.apply(CompatRNR.COBBLED_ROAD))
                    .addOptional(getSlabId.apply(CompatRNR.COBBLED_ROAD))
                    .addOptional(getId.apply(CompatRNR.SETT_ROAD))
                    .addOptional(getStairId.apply(CompatRNR.SETT_ROAD))
                    .addOptional(getSlabId.apply(CompatRNR.SETT_ROAD));

            tag(SUPPORTS_LANDSLIDE)
                    .addOptional(getId.apply(CompatRNR.FLAGSTONE))
                    .addOptional(getStairId.apply(CompatRNR.FLAGSTONE))
                    .addOptional(getSlabId.apply(CompatRNR.FLAGSTONE))
                    .addOptional(getId.apply(CompatRNR.COBBLED_ROAD))
                    .addOptional(getStairId.apply(CompatRNR.COBBLED_ROAD))
                    .addOptional(getSlabId.apply(CompatRNR.COBBLED_ROAD))
                    .addOptional(getId.apply(CompatRNR.SETT_ROAD))
                    .addOptional(getStairId.apply(CompatRNR.SETT_ROAD))
                    .addOptional(getSlabId.apply(CompatRNR.SETT_ROAD));

            // ───────────────────────────────────────────────────────
            // Specialized road tags — only the base block matters here
            // ───────────────────────────────────────────────────────

            tag(FLAGSTONE_ROAD_BLOCKS)
                    .addOptional(getId.apply(CompatRNR.FLAGSTONE));

            tag(FLAGSTONE_ROAD_STAIRS)
                    .addOptional(getStairId.apply(CompatRNR.FLAGSTONE));

            tag(FLAGSTONE_ROAD_SLABS)
                    .addOptional(getSlabId.apply(CompatRNR.FLAGSTONE));

            tag(COBBLED_ROAD_BLOCKS)
                    .addOptional(getId.apply(CompatRNR.COBBLED_ROAD));

            tag(COBBLED_ROAD_STAIRS)
                    .addOptional(getStairId.apply(CompatRNR.COBBLED_ROAD));

            tag(COBBLED_ROAD_SLABS)
                    .addOptional(getSlabId.apply(CompatRNR.COBBLED_ROAD));

            tag(SETT_ROAD_BLOCKS)
                    .addOptional(getId.apply(CompatRNR.SETT_ROAD));

            tag(SETT_ROAD_STAIRS)
                    .addOptional(getStairId.apply(CompatRNR.SETT_ROAD));

            tag(SETT_ROAD_SLABS)
                    .addOptional(getSlabId.apply(CompatRNR.SETT_ROAD));
        }

        for(CompatWood wood : CompatWood.VALUES){
            Function<Block, ResourceLocation> getBlockId = block ->
                    BuiltInRegistries.BLOCK.getKey(block);

            tag(MINEABLE_WITH_AXE)
                    .addOptional(getBlockId.apply(RNRCompatBlocks.WOOD_SHINGLE_ROOFS.get(wood).get()))
                    .addOptional(getBlockId.apply(RNRCompatBlocks.WOOD_SHINGLE_ROOF_STAIRS.get(wood).get()))
                    .addOptional(getBlockId.apply(RNRCompatBlocks.WOOD_SHINGLE_ROOF_SLABS.get(wood).get()));
        }

        Function<Block, ResourceLocation> getBlockId = block ->
                BuiltInRegistries.BLOCK.getKey(block);

// ───────────────────────────────────────────────────────
// Tags that should include these blocks only if they exist
// ───────────────────────────────────────────────────────

        tag(MINEABLE_WITH_SHOVEL)
                .addOptional(getBlockId.apply(RNRCompatBlocks.TAMPED_DIRT.get()))
                .addOptional(getBlockId.apply(RNRCompatBlocks.TAMPED_MUD.get()))
                .addOptional(getBlockId.apply(RNRCompatBlocks.OVER_HEIGHT_GRAVEL.get()))
                .addOptional(getBlockId.apply(RNRCompatBlocks.GRAVEL_ROAD.get()))
                .addOptional(getBlockId.apply(RNRCompatBlocks.GRAVEL_ROAD_STAIRS.get()))
                .addOptional(getBlockId.apply(RNRCompatBlocks.GRAVEL_ROAD_SLAB.get()))
                .addOptional(getBlockId.apply(RNRCompatBlocks.MACADAM_ROAD.get()))
                .addOptional(getBlockId.apply(RNRCompatBlocks.MACADAM_ROAD_STAIRS.get()))
                .addOptional(getBlockId.apply(RNRCompatBlocks.MACADAM_ROAD_SLAB.get()));

        tag(CAN_LANDSLIDE)
                .addOptional(getBlockId.apply(RNRCompatBlocks.TAMPED_DIRT.get()))
                .addOptional(getBlockId.apply(RNRCompatBlocks.TAMPED_MUD.get()))
                .addOptional(getBlockId.apply(RNRCompatBlocks.OVER_HEIGHT_GRAVEL.get()))
                .addOptional(getBlockId.apply(RNRCompatBlocks.GRAVEL_ROAD.get()))
                .addOptional(getBlockId.apply(RNRCompatBlocks.GRAVEL_ROAD_STAIRS.get()))
                .addOptional(getBlockId.apply(RNRCompatBlocks.GRAVEL_ROAD_SLAB.get()))
                .addOptional(getBlockId.apply(RNRCompatBlocks.MACADAM_ROAD.get()))
                .addOptional(getBlockId.apply(RNRCompatBlocks.MACADAM_ROAD_STAIRS.get()))
                .addOptional(getBlockId.apply(RNRCompatBlocks.MACADAM_ROAD_SLAB.get()));

        tag(SUPPORTS_LANDSLIDE)
                .addOptional(getBlockId.apply(RNRCompatBlocks.TAMPED_DIRT.get()))
                .addOptional(getBlockId.apply(RNRCompatBlocks.TAMPED_MUD.get()))
                .addOptional(getBlockId.apply(RNRCompatBlocks.OVER_HEIGHT_GRAVEL.get()))
                .addOptional(getBlockId.apply(RNRCompatBlocks.GRAVEL_ROAD.get()))
                .addOptional(getBlockId.apply(RNRCompatBlocks.GRAVEL_ROAD_STAIRS.get()))
                .addOptional(getBlockId.apply(RNRCompatBlocks.GRAVEL_ROAD_SLAB.get()))
                .addOptional(getBlockId.apply(RNRCompatBlocks.MACADAM_ROAD.get()))
                .addOptional(getBlockId.apply(RNRCompatBlocks.MACADAM_ROAD_STAIRS.get()))
                .addOptional(getBlockId.apply(RNRCompatBlocks.MACADAM_ROAD_SLAB.get()));

// ───────────────────────────────────────────────────────
// Specialized tags — only the base block or variant
// ───────────────────────────────────────────────────────

        tag(TAMPED_BLOCKS)
                .addOptional(getBlockId.apply(RNRCompatBlocks.TAMPED_DIRT.get()))
                .addOptional(getBlockId.apply(RNRCompatBlocks.TAMPED_MUD.get()));

        tag(GRAVEL_ROAD_BLOCKS)
                .addOptional(getBlockId.apply(RNRCompatBlocks.GRAVEL_ROAD.get()));

        tag(GRAVEL_ROAD_STAIRS)
                .addOptional(getBlockId.apply(RNRCompatBlocks.GRAVEL_ROAD_STAIRS.get()));

        tag(GRAVEL_ROAD_SLABS)
                .addOptional(getBlockId.apply(RNRCompatBlocks.GRAVEL_ROAD_SLAB.get()));

        tag(MACADAM_ROAD_BLOCKS)
                .addOptional(getBlockId.apply(RNRCompatBlocks.MACADAM_ROAD.get()));

        tag(MACADAM_ROAD_STAIRS)
                .addOptional(getBlockId.apply(RNRCompatBlocks.MACADAM_ROAD_STAIRS.get()));

        tag(MACADAM_ROAD_SLABS)
                .addOptional(getBlockId.apply(RNRCompatBlocks.MACADAM_ROAD_SLAB.get()));

    }

    private void addAllCompatWoods(CompatWood.BlockType type, TagKey<Block> tagKey) {
        if (tagKey == null) return;  // Skip if no tag for this type

        ModBlocks.WOODS.forEach((wood, map) -> {
            if (map.containsKey(type)) {
                tag(tagKey).add(map.get(type).get());
            }
        });
    }

    private void addAllTFCWoods(Wood.BlockType type, TagKey<Block> tagKey) {
        if (tagKey == null) return;  // Skip if no tag for this type

        TFCBlocks.WOODS.forEach((wood, map) -> {
            if (map.containsKey(type)) {
                tag(tagKey).add(map.get(type).get());
            }
        });
    }

    private void addAllCompatRocks(CompatRock.BlockType type, TagKey<Block> tagKey) {
        if (tagKey == null) return;  // Skip if no tag for this type

        ModBlocks.ROCK_BLOCKS.forEach((wood, map) -> {
            if (map.containsKey(type)) {
                tag(tagKey).add(map.get(type).get());
            }
        });
    }

    @Override
    protected BlockTagAppender tag(TagKey<Block> tag)
    {
        return new BlockTagAppender(getOrCreateRawBuilder(tag));
    }

    @Override
    protected TagBuilder getOrCreateRawBuilder(TagKey<Block> tag)
    {
        if (existingFileHelper != null) existingFileHelper.trackGenerated(tag.location(), resourceType);
        return this.builders.computeIfAbsent(tag.location(), key -> new TagBuilder()
        {
            @Override
            public TagBuilder add(TagEntry entry)
            {
                Preconditions.checkArgument(!entry.getId().equals(BuiltInRegistries.BLOCK.getDefaultKey()), "Adding air to block tag");
                return super.add(entry);
            }
        });
    }

    @SuppressWarnings("UnusedReturnValue")
    static class BlockTagAppender extends TagAppender<Block> implements ModAccessors
    {
        BlockTagAppender(TagBuilder builder)
        {
            super(builder);
        }

        BlockTagAppender add(Block... blocks)
        {
            for (Block block : blocks) add(key(block));
            return this;
        }

        BlockTagAppender add(Stream<? extends Supplier<? extends Block>> blocks)
        {
            blocks.forEach(b -> add(key(b.get())));
            return this;
        }

        @SafeVarargs
        final <T extends IdHolder<? extends Block>> BlockTagAppender add(T... blocks)
        {
            return add(Arrays.stream(blocks));
        }

        /*
        BlockTagAppender addEveryFL(Predicate<Block> predicate)
        {
            return add(ModBlocks.BLOCK.getEntries().stream().filter(e -> predicate.test(e.get())));
        }
        */

        BlockTagAppender add(Map<?, ? extends IdHolder<? extends Block>> blocks)
        {
            blocks.values().forEach(this::add);
            return this;
        }

        BlockTagAppender add2(Map<?, ? extends Map<?, ? extends IdHolder<? extends Block>>> blocks)
        {
            blocks.values().forEach(m -> m.values().forEach(this::add));
            return this;
        }

        <V> BlockTagAppender add2(Map<?, ? extends Map<?, V>> blocks, Function<V, ? extends IdHolder<? extends Block>> ap)
        {
            blocks.values().forEach(m -> m.values().forEach(v -> add(ap.apply(v))));
            return this;
        }

        BlockTagAppender add3(Map<?, ? extends Map<?, ? extends Map<?, ? extends IdHolder<? extends Block>>>> blocks)
        {
            blocks.values().forEach(m1 -> m1.values().forEach(m2 -> m2.values().forEach(this::add)));
            return this;
        }

        BlockTagAppender addAll(Map<?, DecorationBlockHolder> blocks)
        {
            blocks.values().forEach(h -> add(h.slab(), h.stair(), h.wall()));
            return this;
        }

        BlockTagAppender addAll(DecorationBlockHolder blocks)
        {
            add(blocks.slab(), blocks.stair(), blocks.wall());
            return this;
        }

        BlockTagAppender addAll2(Map<?, ? extends Map<?, DecorationBlockHolder>> blocks)
        {
            blocks.values().forEach(m -> m.values().forEach(h -> add(h.slab(), h.stair(), h.wall())));
            return this;
        }

        <T1, T2, V extends IdHolder<? extends Block>> BlockTagAppender add(Map<T1, Map<T2, V>> blocks, T2 key)
        {
            return add(pivot(blocks, key));
        }

        <T, V extends IdHolder<? extends Block>> BlockTagAppender addOnly(Map<T, V> blocks, Predicate<T> key)
        {
            blocks.forEach((k, v) -> {if (key.test(k)) add(v);});
            return this;
        }

        <T1, T2, V extends IdHolder<? extends Block>> BlockTagAppender addOnly2(Map<T1, Map<T2, V>> blocks, Predicate<T2> key)
        {
            blocks.values().forEach(m -> addOnly(m, key));
            return this;
        }

        @SafeVarargs
        @SuppressWarnings("unchecked")
        final <K> BlockTagAppender addTags(Function<K, TagKey<Block>> apply, K... values)
        {
            return addTags(Arrays.stream(values).map(apply).toArray(TagKey[]::new));
        }

        @Override
        public BlockTagAppender addTag(TagKey<Block> tag)
        {
            return (BlockTagAppender) super.addTag(tag);
        }

        @Override
        @SafeVarargs
        public final BlockTagAppender addTags(TagKey<Block>... values)
        {
            return (BlockTagAppender) super.addTags(values);
        }

        BlockTagAppender remove(Block... blocks)
        {
            for (Block block : blocks) remove(key(block));
            return this;
        }

        private ResourceKey<Block> key(Block block)
        {
            return BuiltInRegistries.BLOCK.getResourceKey(block).orElseThrow();
        }
    }
}
