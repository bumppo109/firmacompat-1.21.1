package com.bumppo109.firma_compat.datagen;

import com.bumppo109.firma_compat.FirmaCompat;
import com.bumppo109.firma_compat.block.CompatBricks;
import com.bumppo109.firma_compat.block.CompatRock;
import com.bumppo109.firma_compat.block.CompatWood;
import com.bumppo109.firma_compat.block.ModBlocks;
import com.bumppo109.firma_compat.item.ModItems;
import com.bumppo109.firma_compat.integration.firmalife.CompatFLBlocks;
import com.bumppo109.firma_compat.integration.rnr.CompatRNR;
import com.bumppo109.firma_compat.integration.rnr.RNRCompatBlocks;
import com.eerussianguy.firmalife.common.items.FLItems;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.devices.DryingBricksBlock;
import net.dries007.tfc.common.blocks.rock.LooseRockBlock;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.component.TFCComponents;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.loot.ApplyStackSizeFunction;
import net.dries007.tfc.util.loot.IsIsolatedCondition;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static net.minecraft.world.level.storage.loot.LootPool.lootPool;
import static net.minecraft.world.level.storage.loot.entries.LootItem.lootTableItem;
import static net.minecraft.world.level.storage.loot.predicates.ExplosionCondition.survivesExplosion;

public class BuiltinLootTableProvider extends LootTableProvider {
    public BuiltinLootTableProvider(PackOutput output, CompletableFuture<net.minecraft.core.HolderLookup.Provider> registries) {
        super(output, Set.of(), List.of(
                new SubProviderEntry(ModBlockLoot::new, LootContextParamSets.BLOCK)
        ), registries);
    }

    private static class ModBlockLoot extends BlockLootSubProvider {
        protected ModBlockLoot(net.minecraft.core.HolderLookup.Provider registries) {
            super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
        }

        @Override
        protected void generate() {
            //vanilla
            add(Blocks.BARREL, LootTable.lootTable()
                    .withPool(lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(lootTableItem(ModItems.LUMBER.get(CompatWood.OAK))
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 4.0f)))
                                    .apply(ApplyExplosionDecay.explosionDecay())     // optional: less items if exploded
                            )
                            .when(survivesExplosion())
                    )
            );
            dropSelf(ModBlocks.COMPAT_CHEST.get());
            dropSelf(ModBlocks.COMPAT_TRAPPED_CHEST.get());

            //wood
            for (CompatWood wood : CompatWood.VALUES) {
                var woodMap = ModBlocks.WOODS.get(wood);

                for (CompatWood.BlockType type : CompatWood.BlockType.values()) {
                    if (woodMap.containsKey(type)) {
                        Block block = woodMap.get(type).get();

                        if (type == CompatWood.BlockType.WINDMILL) {
                            // Custom windmill loot: drops the axle (exact JSON you showed)
                            Block axleBlock = woodMap.get(CompatWood.BlockType.AXLE).get();
                            add(block, LootTable.lootTable()
                                    .withPool(lootPool()
                                            .name("loot_pool")
                                            .setRolls(ConstantValue.exactly(1))
                                            .add(lootTableItem(axleBlock))
                                            .when(survivesExplosion())
                                    )
                            );
                        } else if (type == CompatWood.BlockType.BARREL) {
                            createBarrelLoot(block);
                        } else if (type == CompatWood.BlockType.VERTICAL_SUPPORT || type == CompatWood.BlockType.HORIZONTAL_SUPPORT) {
                            // If supports drop nothing or a special item
                            // dropNothing();  // or dropOther(block, yourSupportItem);
                        } else if (type.needsItem()) {
                            dropSelf(block);
                        }
                        // No action for pure structural/no-drop blocks
                    }
                }
            }

            //Rock
            for (CompatRock rock : CompatRock.VALUES) {
                var rockMap = ModBlocks.ROCK_BLOCKS.get(rock);

                for (CompatRock.BlockType type : CompatRock.BlockType.values()) {
                    if (rockMap.containsKey(type)) {
                        Block block = rockMap.get(type).get();

                        if(type == CompatRock.BlockType.LOOSE){
                            addLooseRockLoot(block);
                        }
                        if(type == CompatRock.BlockType.HARDENED){
                            addHardenedRockLoot(block, rock);
                        }
                        if(type == CompatRock.BlockType.LOOSE_COBBLE || type == CompatRock.BlockType.HARDENED_COBBLE){
                            dropSelf(block);
                        }
                        if(type.equals(CompatRock.BlockType.SPIKE)){
                            addSpikeLoot(block, rock);
                        }
                    }
                }
            }

            add(ModBlocks.PRIMITIVE_ANVIL.get(), LootTable.lootTable()
                    .withPool(lootPool()
                            .name("loot_pool")
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModBlocks.ROCK_BLOCKS.get(CompatRock.STONE).get(CompatRock.BlockType.LOOSE).get().asItem())
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
                            .when(survivesExplosion())
                    )
            );

            //deposit
            dropSelf(ModBlocks.CASSITERITE_GRAVEL_DEPOSIT.get());
            dropSelf(ModBlocks.NATIVE_COPPER_GRAVEL_DEPOSIT.get());
            dropSelf(ModBlocks.NATIVE_SILVER_GRAVEL_DEPOSIT.get());
            dropSelf(ModBlocks.NATIVE_GOLD_GRAVEL_DEPOSIT.get());

            //aqueduct
            for(CompatBricks brick : CompatBricks.VALUES){
                dropSelf(ModBlocks.AQUEDUCTS.get(brick).get());
            }

            //Natural
            add(ModBlocks.CLAY_GRASS_BLOCK.get(), LootTable.lootTable()
                    .withPool(lootPool()
                            .name("loot_pool")
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(Items.CLAY_BALL)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                            .when(survivesExplosion())
                    )
            );
            add(ModBlocks.CLAY_DIRT.get(), LootTable.lootTable()
                    .withPool(lootPool()
                            .name("loot_pool")
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(Items.CLAY_BALL)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                            .when(survivesExplosion())
                    )
            );
            add(ModBlocks.CLAY_PODZOL.get(), LootTable.lootTable()
                    .withPool(lootPool()
                            .name("loot_pool")
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(Items.CLAY_BALL)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                            .when(survivesExplosion())
                    )
            );
            add(ModBlocks.KAOLIN_CLAY_GRASS_BLOCK.get(), LootTable.lootTable()
                    .withPool(lootPool()
                            .name("loot_pool")
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(TFCItems.KAOLIN_CLAY)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                            .when(survivesExplosion())
                    )
            );
            add(ModBlocks.KAOLIN_CLAY_DIRT.get(), LootTable.lootTable()
                    .withPool(lootPool()
                            .name("loot_pool")
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(TFCItems.KAOLIN_CLAY)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                            .when(survivesExplosion())
                    )
            );
            add(ModBlocks.KAOLIN_CLAY_PODZOL.get(), LootTable.lootTable()
                    .withPool(lootPool()
                            .name("loot_pool")
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(TFCItems.KAOLIN_CLAY)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                            .when(survivesExplosion())
                    )
            );

            dropOther(ModBlocks.COMPAT_FARMLAND.get(), Blocks.DIRT);

            addDryingBricksLoot(ModBlocks.DRYING_MUD_BRICK.get(), ModItems.MUD_BRICK.get());

            // Non-graded ores (normal only)
            ModBlocks.ORES.forEach((rock, oreMap) -> {
                oreMap.forEach((ore, id) -> {
                    if (id != null) {
                        Block oreBlock = id.get();
                        String oreName = ore.name().toLowerCase(Locale.ROOT);
                        ResourceLocation itemId = ResourceLocation.fromNamespaceAndPath("tfc", "ore/" + oreName);
                        addSimpleOreDrop(oreBlock, itemId);
                    }
                });
            });

            // Graded ores (poor/normal/rich) – use the correct map (gradeMap)
            ModBlocks.GRADED_ORES.forEach((rock, oreMap) -> {
                oreMap.forEach((ore, gradeMap) -> {
                    gradeMap.forEach((grade, id) -> {
                        if (id != null) {
                            Block oreBlock = id.get();  // ← this is the graded block
                            String oreName = ore.name().toLowerCase(Locale.ROOT);
                            String gradePrefix = grade.name().toLowerCase(Locale.ROOT);
                            ResourceLocation itemId = ResourceLocation.fromNamespaceAndPath("tfc", "ore/" + gradePrefix + "_" + oreName);
                            addSimpleOreDrop(oreBlock, itemId);
                        }
                    });
                });
            });

            //Firmalife
                for(CompatWood wood : CompatWood.VALUES){
                    dropSelf(CompatFLBlocks.FOOD_SHELVES.get(wood).get());
                    dropSelf(CompatFLBlocks.HANGERS.get(wood).get());
                    dropSelf(CompatFLBlocks.JARBNETS.get(wood).get());
                    dropSelf(CompatFLBlocks.WINE_SHELVES.get(wood).get());
                    dropSelf(CompatFLBlocks.KEGS.get(wood).get());
                    dropSelf(CompatFLBlocks.STOMPING_BARRELS.get(wood).get());
                    dropSelf(CompatFLBlocks.BARREL_PRESSES.get(wood).get());
                }
                for(CompatRock rock : CompatRock.VALUES){
                    for(Ore.Grade grade : Ore.Grade.values()){
                        Item chromiteItem = FLItems.CHROMIUM_ORES.get(grade).get();
                        Block chromiteOreBlock = CompatFLBlocks.CHROMITE_ORES.get(rock).get(grade).get();

                        addSimpleOreDrop(chromiteOreBlock, chromiteItem);
                    }
                }

            //RNR
            // Tamped blocks
            dropSelf(RNRCompatBlocks.TAMPED_DIRT.get());
            dropSelf(RNRCompatBlocks.TAMPED_MUD.get());

// Over-height gravel (special gravel variant)
            dropSelf(RNRCompatBlocks.OVER_HEIGHT_GRAVEL.get());

// Gravel road set
            dropSelf(RNRCompatBlocks.GRAVEL_ROAD.get());
            dropSelf(RNRCompatBlocks.GRAVEL_ROAD_STAIRS.get());
            dropSelf(RNRCompatBlocks.GRAVEL_ROAD_SLAB.get());

// Macadam road set
            dropSelf(RNRCompatBlocks.MACADAM_ROAD.get());
            dropSelf(RNRCompatBlocks.MACADAM_ROAD_STAIRS.get());
            dropSelf(RNRCompatBlocks.MACADAM_ROAD_SLAB.get());

// Flagstone, Cobbled Road, Sett Road — per rock variant
            for (CompatRock rock : CompatRock.VALUES) {
                // Flagstone
                dropSelf(RNRCompatBlocks.ROCK_BLOCKS.get(rock).get(CompatRNR.FLAGSTONE).get());
                dropSelf(RNRCompatBlocks.ROCK_STAIRS.get(rock).get(CompatRNR.FLAGSTONE).get());
                dropSelf(RNRCompatBlocks.ROCK_SLABS.get(rock).get(CompatRNR.FLAGSTONE).get());

                // Cobbled Road
                dropSelf(RNRCompatBlocks.ROCK_BLOCKS.get(rock).get(CompatRNR.COBBLED_ROAD).get());
                dropSelf(RNRCompatBlocks.ROCK_STAIRS.get(rock).get(CompatRNR.COBBLED_ROAD).get());
                dropSelf(RNRCompatBlocks.ROCK_SLABS.get(rock).get(CompatRNR.COBBLED_ROAD).get());

                // Sett Road
                dropSelf(RNRCompatBlocks.ROCK_BLOCKS.get(rock).get(CompatRNR.SETT_ROAD).get());
                dropSelf(RNRCompatBlocks.ROCK_STAIRS.get(rock).get(CompatRNR.SETT_ROAD).get());
                dropSelf(RNRCompatBlocks.ROCK_SLABS.get(rock).get(CompatRNR.SETT_ROAD).get());
            }

            for(CompatWood wood : CompatWood.VALUES){
                dropSelf(RNRCompatBlocks.WOOD_SHINGLE_ROOFS.get(wood).get());
                dropSelf(RNRCompatBlocks.WOOD_SHINGLE_ROOF_STAIRS.get(wood).get());
                dropSelf(RNRCompatBlocks.WOOD_SHINGLE_ROOF_SLABS.get(wood).get());
            }

        }

        private void addSimpleOreDrop(Block oreBlock, ResourceLocation oreItemId) {
            Item oreItem = BuiltInRegistries.ITEM.get(oreItemId);
            if (oreItem == null || oreItem == Items.AIR) {
                FirmaCompat.LOGGER.error("Missing ore item: {}", oreItemId);
                return;
            }

            add(oreBlock, LootTable.lootTable()
                    .withPool(lootPool()
                            .name("loot_pool")
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(oreItem))
                            .when(survivesExplosion())
                    )
            );
        }

        private void addSimpleOreDrop(Block oreBlock, Item oreItem) {
            if (oreItem == null || oreItem == Items.AIR) {
                FirmaCompat.LOGGER.error("Missing ore item: {}", oreItem);
                return;
            }

            add(oreBlock, LootTable.lootTable()
                    .withPool(lootPool()
                            .name("loot_pool")
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(oreItem))
                            .when(survivesExplosion())
                    )
            );
        }

        private void addLooseRockLoot(Block looseBlock) {
            // looseBlock is already the Block instance → it implements ItemLike and drops its own item

            LootPoolEntryContainer.Builder<?> entry = LootItem.lootTableItem(looseBlock)  // ← pass the Block directly
                    // Base count = 1 is default (no function needed)
                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))
                            .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(looseBlock)
                                    .setProperties(StatePropertiesPredicate.Builder.properties()
                                            .hasProperty(LooseRockBlock.COUNT, 2))))
                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(3))
                            .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(looseBlock)
                                    .setProperties(StatePropertiesPredicate.Builder.properties()
                                            .hasProperty(LooseRockBlock.COUNT, 3))))
                    .apply(ApplyExplosionDecay.explosionDecay());

            add(looseBlock, LootTable.lootTable()
                    .withPool(lootPool()
                            .name("loot_pool")
                            .setRolls(ConstantValue.exactly(1))
                            .add(entry)
                            .when(survivesExplosion())
                    )
            );
        }

        private void addHardenedRockLoot(Block hardenedBlock, CompatRock rock) {
            Item raw = rock.rawBlock().get().asItem();

            Item loose = ModBlocks.ROCK_BLOCKS.get(rock).get(CompatRock.BlockType.LOOSE).get().asItem();

            var alternatives = AlternativesEntry.alternatives(
                    LootItem.lootTableItem(raw)
                            .when(() -> IsIsolatedCondition.INSTANCE),
                    LootItem.lootTableItem(loose)
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4)))
            );

            add(hardenedBlock, LootTable.lootTable()
                    .withPool(lootPool()
                            .name("loot_pool")
                            .setRolls(ConstantValue.exactly(1))
                            .add(alternatives)
                            .when(survivesExplosion())
                    )
            );
        }

        private void addSpikeLoot(Block spikeBlock, CompatRock rock) {
            Item loose = ModBlocks.ROCK_BLOCKS.get(rock).get(CompatRock.BlockType.LOOSE).get().asItem();

            add(spikeBlock, LootTable.lootTable()
                    .withPool(lootPool()
                            .name("loot_pool")
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(loose)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
                            .when(survivesExplosion())
                    )
            );
        }

        /**
         * Adds a loot table for a drying bricks block that drops:
         * - drying_bricks/<soil> when not dried (based on count)
         * - mud_brick/<soil> when dried (based on count)
         * Matches your exact multi-pool JSON structure.
         */
        private void addDryingBricksLoot(Block dryingBlock, Item driedBrick) {
            LootTable.Builder builder = LootTable.lootTable();

            // Add one pool per possible count value (1 to 4)
            for (int count = 1; count <= 4; count++) {
                // Pool for NOT dried → drying bricks
                LootPoolEntryContainer.Builder<?> notDriedEntry = LootItem.lootTableItem(dryingBlock)
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(dryingBlock)
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(DryingBricksBlock.COUNT, count)
                                        .hasProperty(DryingBricksBlock.DRIED, false)))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(count)));

                builder.withPool(lootPool()
                        .name("loot_pool")  // all pools can share the name, or make unique if preferred
                        .setRolls(ConstantValue.exactly(1))
                        .add(notDriedEntry)
                        .when(survivesExplosion()));

                // Pool for DRIED → mud brick
                LootPoolEntryContainer.Builder<?> driedEntry = LootItem.lootTableItem(driedBrick)
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(dryingBlock)
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(DryingBricksBlock.COUNT, count)
                                        .hasProperty(DryingBricksBlock.DRIED, true)))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(count)));

                builder.withPool(lootPool()
                        .name("loot_pool")
                        .setRolls(ConstantValue.exactly(1))
                        .add(driedEntry)
                        .when(survivesExplosion()));
            }

            add(dryingBlock, builder);
        }

        protected void createBarrelLoot(Block barrelBlock) {
            add(barrelBlock, LootTable.lootTable()
                    .withPool(lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(AlternativesEntry.alternatives(
                                    lootTableItem(barrelBlock)
                                            .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(barrelBlock)
                                                    .setProperties(StatePropertiesPredicate.Builder.properties()
                                                            .hasProperty(TFCBlockStateProperties.SEALED, true)))
                                            .apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                                                    .include(DataComponents.CUSTOM_NAME)
                                                    .include(TFCComponents.BARREL.get()))
                                            .apply(ApplyStackSizeFunction.simpleBuilder(ApplyStackSizeFunction::new)),
                                    lootTableItem(barrelBlock.asItem())
                            ))
                            .when(survivesExplosion())
                    )
            );
        }


        private boolean needsCustomLoot(CompatRock.BlockType type) {
            return type == CompatRock.BlockType.LOOSE;  // add other types here later, e.g. || type == SLAB || type == STAIRS
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            List<Block> knownBlocks = new ArrayList<>();

            // 1. Wood blocks - only those with defined loot behavior
            Stream<Block> woodStream = Stream.of(CompatWood.VALUES)
                    .flatMap(wood -> ModBlocks.WOODS.get(wood).entrySet().stream())
                    .filter(entry -> {
                        CompatWood.BlockType type = entry.getKey();
                        return type.needsItem()
                                || type == CompatWood.BlockType.WINDMILL
                                || type == CompatWood.BlockType.BARREL;
                    })
                    .map(entry -> entry.getValue().get());

            knownBlocks.addAll(woodStream.toList());

            // 2. Rock blocks (your existing filter)
            Stream<Block> rockStream = Stream.of(CompatRock.VALUES)
                    .flatMap(rock -> ModBlocks.ROCK_BLOCKS.get(rock).entrySet().stream())
                    .filter(entry -> {
                        CompatRock.BlockType type = entry.getKey();
                        return type == CompatRock.BlockType.LOOSE
                                || type == CompatRock.BlockType.HARDENED
                                || type == CompatRock.BlockType.LOOSE_COBBLE
                                || type == CompatRock.BlockType.HARDENED_COBBLE
                                || type == CompatRock.BlockType.SPIKE;
                    })
                    .map(entry -> entry.getValue().get());

            knownBlocks.addAll(rockStream.toList());

            // 3. Non-graded ores (rock → ore → block)
            ModBlocks.ORES.forEach((rock, oreMap) -> {
                oreMap.forEach((ore, id) -> {
                    if (id != null) {
                        knownBlocks.add(id.get());
                    }
                });
            });

            // 4. Graded ores (rock → ore → grade → block)
            ModBlocks.GRADED_ORES.forEach((rock, oreMap) -> {
                oreMap.forEach((ore, gradeMap) -> {
                    gradeMap.forEach((grade, id) -> {
                        if (id != null) {
                            knownBlocks.add(id.get());
                        }
                    });
                });
            });

            // 4. Aqueduct blocks (this was missing!)
            ModBlocks.AQUEDUCTS.forEach((brick, id) -> {
                if (id != null) {
                    knownBlocks.add(id.get());
                }
            });

            // 5. Other special blocks that use dropSelf / add (add these as needed)
            knownBlocks.add(ModBlocks.PRIMITIVE_ANVIL.get());
            knownBlocks.add(ModBlocks.DRYING_MUD_BRICK.get());
            knownBlocks.add(ModBlocks.COMPAT_FARMLAND.get());
            knownBlocks.add(ModBlocks.CLAY_GRASS_BLOCK.get());
            knownBlocks.add(ModBlocks.CLAY_DIRT.get());
            knownBlocks.add(ModBlocks.CLAY_PODZOL.get());
            knownBlocks.add(ModBlocks.KAOLIN_CLAY_GRASS_BLOCK.get());
            knownBlocks.add(ModBlocks.KAOLIN_CLAY_DIRT.get());
            knownBlocks.add(ModBlocks.KAOLIN_CLAY_PODZOL.get());

            // Gravel deposits
            knownBlocks.add(ModBlocks.CASSITERITE_GRAVEL_DEPOSIT.get());
            knownBlocks.add(ModBlocks.NATIVE_COPPER_GRAVEL_DEPOSIT.get());
            knownBlocks.add(ModBlocks.NATIVE_GOLD_GRAVEL_DEPOSIT.get());
            knownBlocks.add(ModBlocks.NATIVE_SILVER_GRAVEL_DEPOSIT.get());

            knownBlocks.add(ModBlocks.COMPAT_CHEST.get());
            knownBlocks.add(ModBlocks.COMPAT_TRAPPED_CHEST.get());

            //Firmalife
            for(CompatWood wood : CompatWood.VALUES){
                knownBlocks.add(CompatFLBlocks.FOOD_SHELVES.get(wood).get());
                knownBlocks.add(CompatFLBlocks.HANGERS.get(wood).get());
                knownBlocks.add(CompatFLBlocks.JARBNETS.get(wood).get());
                knownBlocks.add(CompatFLBlocks.WINE_SHELVES.get(wood).get());
                knownBlocks.add(CompatFLBlocks.KEGS.get(wood).get());
                knownBlocks.add(CompatFLBlocks.STOMPING_BARRELS.get(wood).get());
                knownBlocks.add(CompatFLBlocks.BARREL_PRESSES.get(wood).get());
            }
            for(CompatRock rock : CompatRock.VALUES){
                for(Ore.Grade grade : Ore.Grade.values()){
                    Block chromiteOreBlock = CompatFLBlocks.CHROMITE_ORES.get(rock).get(grade).get();

                    knownBlocks.add(chromiteOreBlock);
                }
            }

            // Tamped blocks
            knownBlocks.add(RNRCompatBlocks.TAMPED_DIRT.get());
            knownBlocks.add(RNRCompatBlocks.TAMPED_MUD.get());

            // Over-height gravel
            knownBlocks.add(RNRCompatBlocks.OVER_HEIGHT_GRAVEL.get());

            // Gravel road set
            knownBlocks.add(RNRCompatBlocks.GRAVEL_ROAD.get());
            knownBlocks.add(RNRCompatBlocks.GRAVEL_ROAD_STAIRS.get());
            knownBlocks.add(RNRCompatBlocks.GRAVEL_ROAD_SLAB.get());

            // Macadam road set
            knownBlocks.add(RNRCompatBlocks.MACADAM_ROAD.get());
            knownBlocks.add(RNRCompatBlocks.MACADAM_ROAD_STAIRS.get());
            knownBlocks.add(RNRCompatBlocks.MACADAM_ROAD_SLAB.get());

            // Per-rock variants: Flagstone, Cobbled Road, Sett Road
            for (CompatRock rock : CompatRock.VALUES) {
                // Flagstone
                knownBlocks.add(RNRCompatBlocks.ROCK_BLOCKS.get(rock).get(CompatRNR.FLAGSTONE).get());
                knownBlocks.add(RNRCompatBlocks.ROCK_STAIRS.get(rock).get(CompatRNR.FLAGSTONE).get());
                knownBlocks.add(RNRCompatBlocks.ROCK_SLABS.get(rock).get(CompatRNR.FLAGSTONE).get());

                // Cobbled Road
                knownBlocks.add(RNRCompatBlocks.ROCK_BLOCKS.get(rock).get(CompatRNR.COBBLED_ROAD).get());
                knownBlocks.add(RNRCompatBlocks.ROCK_STAIRS.get(rock).get(CompatRNR.COBBLED_ROAD).get());
                knownBlocks.add(RNRCompatBlocks.ROCK_SLABS.get(rock).get(CompatRNR.COBBLED_ROAD).get());

                // Sett Road
                knownBlocks.add(RNRCompatBlocks.ROCK_BLOCKS.get(rock).get(CompatRNR.SETT_ROAD).get());
                knownBlocks.add(RNRCompatBlocks.ROCK_STAIRS.get(rock).get(CompatRNR.SETT_ROAD).get());
                knownBlocks.add(RNRCompatBlocks.ROCK_SLABS.get(rock).get(CompatRNR.SETT_ROAD).get());
            }

            for (CompatWood wood : CompatWood.VALUES) {
                knownBlocks.add(RNRCompatBlocks.WOOD_SHINGLE_ROOFS.get(wood).get());
                knownBlocks.add(RNRCompatBlocks.WOOD_SHINGLE_ROOF_STAIRS.get(wood).get());
                knownBlocks.add(RNRCompatBlocks.WOOD_SHINGLE_ROOF_SLABS.get(wood).get());
            }

            knownBlocks.add(Blocks.BARREL);
            
            // Return the combined list
            return knownBlocks;
        }
    }
}
