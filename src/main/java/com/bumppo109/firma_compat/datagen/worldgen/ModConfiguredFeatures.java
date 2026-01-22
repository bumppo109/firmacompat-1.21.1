package com.bumppo109.firma_compat.datagen.worldgen;

import com.bumppo109.firma_compat.FirmaCompat;
import com.bumppo109.firma_compat.block.CompatOre;
import com.bumppo109.firma_compat.block.CompatRock;
import com.bumppo109.firma_compat.block.ModBlocks;
import com.bumppo109.firma_compat.worldgen.CompatSingleBlockVein;
import com.bumppo109.firma_compat.worldgen.CompatVein;
import com.mojang.datafixers.util.Pair;
import net.dries007.tfc.util.collections.IWeighted;
import net.dries007.tfc.util.collections.Weighted;
import net.dries007.tfc.world.feature.vein.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;

import java.util.*;

import static com.bumppo109.firma_compat.block.ModBlocks.GRADED_ORES;
import static com.bumppo109.firma_compat.block.ModBlocks.ORES;

public class ModConfiguredFeatures {

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {

        //SingleBlock Vein
        for (CompatSingleBlockVein vein : CompatSingleBlockVein.values()) {
            String veinName = vein.name().toLowerCase(Locale.ROOT);

            // 1. Build replacement map
            Map<Block, IWeighted<BlockState>> blockSwapMap = buildSingleReplacementMap(vein);

            // 2. Optional indicator – use list constructor
            Optional<Indicator> indicator = Optional.empty();
            if (vein.indicator != null) {
                List<Pair<BlockState, Double>> indicatorWeights = List.of(
                        Pair.of(vein.indicator.defaultBlockState(), 1.0)
                );
                var states = new Weighted<BlockState>(indicatorWeights);

                indicator = Optional.of(new Indicator(
                        vein.indicatorDepth != null ? vein.indicatorDepth : 3,
                        vein.indicatorRarity != null ? vein.indicatorRarity : 12,
                        vein.indicatorUnderRarity != null ? vein.indicatorUnderRarity : 3,
                        vein.indicatorCount != null ? vein.indicatorCount : 2,
                        states
                ));
            }

            // 3. Base config
            //TODO - if project & project offset needed, modify other loop
            VeinConfig base = new VeinConfig(
                    blockSwapMap,
                    indicator,
                    vein.rarity,
                    vein.density,
                    vein.minY,
                    vein.maxY,
                    vein.project,
                    vein.project_offset,
                    veinSeedFromName(veinName),
                    false
            );

            // 4. Type-specific config + feature ID
            IVeinConfig config;
            ResourceLocation featureId;

            switch (vein.veinType) {
                case DISC -> {
                    config = new DiscVeinConfig(
                            base,
                            vein.size != null ? vein.size : 20,
                            vein.height != null ? vein.height : 4
                    );
                    featureId = ResourceLocation.fromNamespaceAndPath("tfc", "disc_vein");
                }
                case CLUSTER -> {
                    config = new ClusterVeinConfig(
                            base,
                            vein.size != null ? vein.size : 20
                    );
                    featureId = ResourceLocation.fromNamespaceAndPath("tfc", "cluster_vein");
                }
                case PIPE -> {
                    config = new PipeVeinConfig(
                            base,
                            vein.pipeHeight != null ? vein.pipeHeight : 60,
                            vein.radius != null ? vein.radius : 5,
                            vein.minSkew != null ? vein.minSkew : 5,
                            vein.maxSkew != null ? vein.maxSkew : 13,
                            vein.minSlant != null ? vein.minSlant : 0,
                            vein.maxSlant != null ? vein.maxSlant : 2,
                            vein.sign != null ? vein.sign.floatValue() : 0.0f
                    );
                    featureId = ResourceLocation.fromNamespaceAndPath("tfc", "pipe_vein");
                }
                default -> throw new IllegalStateException("Unknown vein type: " + vein.veinType);
            }

            // 5. Get Feature
            @SuppressWarnings("unchecked")
            Feature<IVeinConfig> feature = (Feature<IVeinConfig>) BuiltInRegistries.FEATURE.get(featureId);
            if (feature == null) {
                System.err.println("TFC vein feature missing: " + featureId);
                continue;
            }

            // 6. Register
            ConfiguredFeature<?, ?> configured = new ConfiguredFeature<>(feature, config);

            ResourceKey<ConfiguredFeature<?, ?>> key = ResourceKey.create(
                    Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(FirmaCompat.MODID, "overworld/vein/" + veinName)
            );

            context.register(key, configured);
            System.out.println("Registered vein configured feature: " + key.location());
        }

        //Vein
        for (CompatVein vein : CompatVein.values()) {
            String veinName = vein.name().toLowerCase(Locale.ROOT);

            // 1. Build replacement map
            Map<Block, IWeighted<BlockState>> blockSwapMap = buildReplacementMap(vein);

            // 2. Optional indicator – use list constructor
            Optional<Indicator> indicator = Optional.empty();
            if (vein.indicator != null) {
                List<Pair<BlockState, Double>> indicatorWeights = List.of(
                        Pair.of(vein.indicator.defaultBlockState(), 1.0)
                );
                var states = new Weighted<BlockState>(indicatorWeights);

                indicator = Optional.of(new Indicator(
                        vein.indicatorDepth != null ? vein.indicatorDepth : 3,
                        vein.indicatorRarity != null ? vein.indicatorRarity : 12,
                        vein.indicatorUnderRarity != null ? vein.indicatorUnderRarity : 3,
                        vein.indicatorCount != null ? vein.indicatorCount : 2,
                        states
                ));
            }

            // 3. Base config
            VeinConfig base = new VeinConfig(
                    blockSwapMap,
                    indicator,
                    vein.rarity,
                    vein.density,
                    vein.minY,
                    vein.maxY,
                    false,  // aquifer
                    false,  // canReplaceFluids
                    veinSeedFromName(veinName),
                    false   // debug
            );

            // 4. Type-specific config + feature ID
            IVeinConfig config;
            ResourceLocation featureId;

            switch (vein.veinType) {
                case DISC -> {
                    config = new DiscVeinConfig(
                            base,
                            vein.size != null ? vein.size : 20,
                            vein.height != null ? vein.height : 4
                    );
                    featureId = ResourceLocation.fromNamespaceAndPath("tfc", "disc_vein");
                }
                case CLUSTER -> {
                    config = new ClusterVeinConfig(
                            base,
                            vein.size != null ? vein.size : 20
                    );
                    featureId = ResourceLocation.fromNamespaceAndPath("tfc", "cluster_vein");
                }
                case PIPE -> {
                    config = new PipeVeinConfig(
                            base,
                            vein.pipeHeight != null ? vein.pipeHeight : 60,
                            vein.radius != null ? vein.radius : 5,
                            vein.minSkew != null ? vein.minSkew : 5,
                            vein.maxSkew != null ? vein.maxSkew : 13,
                            vein.minSlant != null ? vein.minSlant : 0,
                            vein.maxSlant != null ? vein.maxSlant : 2,
                            vein.sign != null ? vein.sign.floatValue() : 0.0f
                    );
                    featureId = ResourceLocation.fromNamespaceAndPath("tfc", "pipe_vein");
                }
                default -> throw new IllegalStateException("Unknown vein type: " + vein.veinType);
            }

            // 5. Get Feature
            @SuppressWarnings("unchecked")
            Feature<IVeinConfig> feature = (Feature<IVeinConfig>) BuiltInRegistries.FEATURE.get(featureId);
            if (feature == null) {
                System.err.println("TFC vein feature missing: " + featureId);
                continue;
            }

            // 6. Register
            ConfiguredFeature<?, ?> configured = new ConfiguredFeature<>(feature, config);

            ResourceKey<ConfiguredFeature<?, ?>> key = ResourceKey.create(
                    Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(FirmaCompat.MODID, "overworld/vein/" + veinName)
            );

            context.register(key, configured);
            System.out.println("Registered vein configured feature: " + key.location());
        }
    }

    // Helper: build the weighted replacement map depending on graded or not
    private static Map<Block, IWeighted<BlockState>> buildReplacementMap(CompatVein vein) {
        Map<Block, IWeighted<BlockState>> map = new HashMap<>();

        int added = 0;
        int skipped = 0;

        for (CompatRock rock : CompatRock.VALUES) {
            Block target = rock.rawBlock().get();  // ← fixed: use vanilla equiv (STONE, GRANITE, etc.)
            if (target == null || target == Blocks.AIR) {
                skipped++;
                continue;
            }

            if (!vein.ore.isGraded()) {
                var oreId = ModBlocks.ORES.get(rock).get(vein.ore);
                if (oreId == null) {
                    skipped++;
                    continue;
                }
                Block oreBlock = oreId.get();
                if (oreBlock == null || oreBlock == Blocks.AIR) {
                    skipped++;
                    continue;
                }

                map.put(target, new Weighted<>(
                        List.of(Pair.of(oreBlock.defaultBlockState(), 1.0))
                ));
                added++;
            } else {
                var oreMap = ModBlocks.GRADED_ORES.get(rock);
                if (oreMap == null) {
                    skipped++;
                    continue;
                }
                var gradeMap = oreMap.get(vein.ore);
                if (gradeMap == null) {
                    skipped++;
                    continue;
                }

                var poorId   = gradeMap.get(CompatOre.Grade.POOR);
                var normalId = gradeMap.get(CompatOre.Grade.NORMAL);
                var richId   = gradeMap.get(CompatOre.Grade.RICH);

                if (poorId == null || normalId == null || richId == null) {
                    skipped++;
                    continue;
                }

                Block poor   = poorId.get();
                Block normal = normalId.get();
                Block rich   = richId.get();

                if (poor == null || normal == null || rich == null) {
                    skipped++;
                    continue;
                }

                List<Pair<BlockState, Double>> weights = switch(vein.gradedVeinClass){
                    case SURFACE -> List.of(
                            Pair.of(poor.defaultBlockState(),   70.0),
                            Pair.of(normal.defaultBlockState(), 25.0),
                            Pair.of(rich.defaultBlockState(),    5.0));
                    case NORMAL -> List.of(
                            Pair.of(poor.defaultBlockState(),   35.0),
                            Pair.of(normal.defaultBlockState(), 40.0),
                            Pair.of(rich.defaultBlockState(),   25.0));
                    case RICH -> List.of(
                            Pair.of(poor.defaultBlockState(),   15.0),
                            Pair.of(normal.defaultBlockState(), 25.0),
                            Pair.of(rich.defaultBlockState(),   60.0));
                };

                map.put(target, new Weighted<>(weights));
                added++;
            }
        }

        System.out.println("Vein " + vein.name() + ": Added " + added + " replacements, skipped " + skipped);
        return map;
    }

    private static Map<Block, IWeighted<BlockState>> buildSingleReplacementMap(CompatSingleBlockVein vein) {
        Map<Block, IWeighted<BlockState>> map = new HashMap<>();

        int added = 0;
        int skipped = 0;

        for (CompatRock rock : CompatRock.VALUES) {
            Block target = rock.rawBlock().get();  // ← fixed: use vanilla equiv (STONE, GRANITE, etc.)
            if (target == null || target == Blocks.AIR) {
                skipped++;
                continue;
            }
            if (vein.oreBlock == null || vein.oreBlock == Blocks.AIR) {
                skipped++;
                continue;
            }

            map.put(target, new Weighted<>(
                    List.of(Pair.of(vein.oreBlock.defaultBlockState(), 1.0))
            ));
            added++;

        }

        System.out.println("Vein " + vein.name() + ": Added " + added + " replacements, skipped " + skipped);
        return map;
    }

    // Helper: get non-graded ore block for rock (from your ORES map)
    private static Block getOreBlock(CompatOre ore, CompatRock rock) {
        // Assuming your registration provides access; adjust if needed
        return ORES.get(rock).get(ore).get();
    }

    // Helper: get graded ore block for rock + grade (from your GRADED_ORES map)
    private static Block getGradedOreBlock(CompatOre ore, CompatRock rock, CompatOre.Grade grade) {
        // Assuming your registration provides access; adjust if needed
        return GRADED_ORES.get(rock).get(ore).get(grade).get();
    }

    // Simple deterministic seed – same as TFC mostly uses
    private static long veinSeedFromName(String name) {
        long hash = 0;
        for (char c : name.toCharArray()) {
            hash = 31 * hash + c;
        }
        return hash & 0x7FFFFFFFFFFFFFFFL; // positive
    }

    /*
    List<Pair<BlockState, Double>> weights = "normal".equals(vein.gradedVein)
                            ? List.of(
                            Pair.of(poor.defaultBlockState(),   15.0),
                            Pair.of(normal.defaultBlockState(), 25.0),
                            Pair.of(rich.defaultBlockState(),   60.0))
                            : "surface".equals(vein.gradedVein)
                            ? List.of(
                            Pair.of(poor.defaultBlockState(),   70.0),
                            Pair.of(normal.defaultBlockState(), 25.0),
                            Pair.of(rich.defaultBlockState(),   5.0))
                            : null;

     */

    /*
    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        // ────────────────────────────────────────────────────────────────
        // Loop 1: CompatVein – graded/per-rock variants
        // ────────────────────────────────────────────────────────────────
        for (CompatVein vein : CompatVein.values()) {
            System.out.println("Processing CompatVein: " + vein.veinName + " (" + vein.veinType + ")");

            Map<Block, IWeighted<BlockState>> states = new HashMap<>();
            int added = 0;
            int skipped = 0;

            for (CompatRock rock : CompatRock.values()) {
                Block rawBlock = rock.vanillaEquivalent().get();
                if (rawBlock == null || rawBlock == Blocks.AIR) {
                    skipped++;
                    continue;
                }

                if (!vein.ore.isGraded()) {
                    var oreId = ModBlocks.ORES.get(rock).get(vein.ore);
                    if (oreId == null) {
                        skipped++;
                        continue;
                    }

                    Block oreBlock = oreId.get();
                    if (oreBlock == null || oreBlock == Blocks.AIR) {
                        skipped++;
                        continue;
                    }

                    states.put(rawBlock, new Weighted<>(List.of(Pair.of(oreBlock.defaultBlockState(), 1.0))));
                    added++;
                } else {
                    // Graded: safe lookup
                    var oreMap = ModBlocks.GRADED_ORES.get(rock);
                    if (oreMap == null) {
                        skipped++;
                        continue;
                    }

                    var gradeMap = oreMap.get(vein.ore);
                    if (gradeMap == null) {
                        skipped++;
                        continue;
                    }

                    var poorId = gradeMap.get(CompatOre.Grade.POOR);
                    var normalId = gradeMap.get(CompatOre.Grade.NORMAL);
                    var richId = gradeMap.get(CompatOre.Grade.RICH);

                    if (poorId == null || normalId == null || richId == null) {
                        skipped++;
                        continue;
                    }

                    Block poor   = poorId.get();
                    Block normal = normalId.get();
                    Block rich   = richId.get();

                    if (poor == null || normal == null || rich == null) {
                        skipped++;
                        continue;
                    }

                    List<Pair<BlockState, Double>> weights = "normal".equals(vein.gradedVein)
                            ? List.of(
                            Pair.of(poor.defaultBlockState(),   15.0),
                            Pair.of(normal.defaultBlockState(), 25.0),
                            Pair.of(rich.defaultBlockState(),   60.0))
                            : "surface".equals(vein.gradedVein)
                            ? List.of(
                            Pair.of(poor.defaultBlockState(),   70.0),
                            Pair.of(normal.defaultBlockState(), 25.0),
                            Pair.of(rich.defaultBlockState(),   5.0))
                            : null;

                    if (weights == null) {
                        skipped++;
                        continue;
                    }

                    states.put(rawBlock, new Weighted<>(weights));
                    added++;
                }
            }

            System.out.println("  → Added " + added + " valid replacements (skipped " + skipped + " missing/invalid)");

            // Do NOT skip if empty – register anyway (TFC handles empty states safely)
            if (states.isEmpty()) {
                System.out.println("  → Warning: No valid ore states found for " + vein.veinName + " – registering empty vein");
            }

            // Register the vein (even if partial or empty)
            registerVein(context, vein.veinName, vein.veinType, vein.rarity, vein.density,
                    vein.minY, vein.maxY, vein.size, vein.height,
                    vein.minSkew, vein.maxSkew, vein.minSlant, vein.maxSlant,
                    vein.sign, vein.pipeHeight, vein.radius, states);
        }

        // ────────────────────────────────────────────────────────────────
        // Loop 2: BlockVein – single unified ore block for all rocks
        // ────────────────────────────────────────────────────────────────
        for (BlockVein bVein : BlockVein.values()) {
            System.out.println("Processing BlockVein: " + bVein.veinName + " (" + bVein.veinType + ")");

            Map<Block, IWeighted<BlockState>> states = new HashMap<>();
            int added = 0;

            Block oreBlock = bVein.oreBlock;
            if (oreBlock == null || oreBlock == Blocks.AIR) {
                System.out.println("  → Skipping: missing ore block for " + bVein.veinName);
                continue;
            }

            BlockState oreState = oreBlock.defaultBlockState();

            for (CompatRock rock : CompatRock.values()) {
                Block rawBlock = rock.vanillaEquivalent().get();
                if (rawBlock == null || rawBlock == Blocks.AIR) continue;

                states.put(rawBlock, new Weighted<>(List.of(Pair.of(oreState, 1.0))));
                added++;
            }

            System.out.println("  → Generated " + added + " replacements (using " + BuiltInRegistries.BLOCK.getKey(oreBlock) + ")");

            if (states.isEmpty()) {
                System.out.println("  → Warning: No valid raw rocks for " + bVein.veinName);
                continue;
            }

            registerVein(context, bVein.veinName, bVein.veinType, bVein.rarity, bVein.density,
                    bVein.minY, bVein.maxY, bVein.size, bVein.height,
                    bVein.minSkew, bVein.maxSkew, bVein.minSlant, bVein.maxSlant,
                    bVein.sign, bVein.pipeHeight, bVein.radius, states);
        }
    }

     */

    /*
    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        // ────────────────────────────────────────────────────────────────
        // First loop: CompatVein (graded / per-rock variants)
        // ────────────────────────────────────────────────────────────────
        for (CompatVein vein : CompatVein.values()) {
            System.out.println("Processing CompatVein: " + vein.veinName + " (" + vein.veinType + ")");

            Map<Block, IWeighted<BlockState>> states = new HashMap<>();
            int added = 0;

            for (CompatRock rock : CompatRock.values()) {
                Block rawBlock = rock.vanillaEquivalent().get();
                if (rawBlock == null || rawBlock == Blocks.AIR) continue;

                if (!vein.ore.isGraded()) {
                    var oreId = ModBlocks.ORES.get(rock).get(vein.ore);
                    if (oreId == null) continue;

                    Block oreBlock = oreId.get();
                    if (oreBlock == null || oreBlock == Blocks.AIR) continue;

                    states.put(rawBlock, new Weighted<>(List.of(Pair.of(oreBlock.defaultBlockState(), 1.0))));
                    added++;
                } else {
                    var oreMap = ModBlocks.GRADED_ORES.get(rock);
                    if (oreMap == null) continue;

                    var gradeMap = oreMap.get(vein.ore);
                    if (gradeMap == null) continue;

                    var poorId = gradeMap.get(CompatOre.Grade.POOR);
                    var normalId = gradeMap.get(CompatOre.Grade.NORMAL);
                    var richId = gradeMap.get(CompatOre.Grade.RICH);

                    if (poorId == null || normalId == null || richId == null) continue;

                    Block poor   = poorId.get();
                    Block normal = normalId.get();
                    Block rich   = richId.get();

                    if (poor == null || normal == null || rich == null) continue;

                    List<Pair<BlockState, Double>> weights = "normal".equals(vein.gradedVein)
                            ? List.of(
                            Pair.of(poor.defaultBlockState(),   15.0),
                            Pair.of(normal.defaultBlockState(), 25.0),
                            Pair.of(rich.defaultBlockState(),   60.0))
                            : "surface".equals(vein.gradedVein)
                            ? List.of(
                            Pair.of(poor.defaultBlockState(),   70.0),
                            Pair.of(normal.defaultBlockState(), 25.0),
                            Pair.of(rich.defaultBlockState(),   5.0))
                            : null;

                    if (weights == null) continue;

                    states.put(rawBlock, new Weighted<>(weights));
                    added++;
                }
            }

            System.out.println("  → Generated " + added + " replacements");

            if (states.isEmpty()) {
                System.out.println("  → Skipping (no valid states): " + vein.veinName);
                continue;
            }

            registerCompatVein(context, vein.veinName, vein.veinType, vein.rarity, vein.density,
                    vein.minY, vein.maxY, vein.size, vein.height,
                    vein.minSkew, vein.maxSkew, vein.minSlant, vein.maxSlant,
                    vein.sign, vein.pipeHeight, vein.radius, states);
        }

        // ────────────────────────────────────────────────────────────────
        // Second loop: BlockVein (single unified ore block for all rocks)
        // ────────────────────────────────────────────────────────────────
        for (BlockVein bVein : BlockVein.values()) {
            System.out.println("Processing BlockVein: " + bVein.veinName + " (" + bVein.veinType + ")");

            Map<Block, IWeighted<BlockState>> states = new HashMap<>();
            int added = 0;

            Block oreBlock = bVein.oreBlock;
            if (oreBlock == null || oreBlock == Blocks.AIR) {
                System.out.println("  → Skipping: missing ore block for " + bVein.veinName);
                continue;
            }

            BlockState oreState = oreBlock.defaultBlockState();

            for (CompatRock rock : CompatRock.values()) {
                Block rawBlock = rock.vanillaEquivalent().get();
                if (rawBlock == null || rawBlock == Blocks.AIR) continue;

                states.put(rawBlock, new Weighted<>(List.of(Pair.of(oreState, 1.0))));
                added++;
            }

            System.out.println("  → Generated " + added + " replacements (using " + BuiltInRegistries.BLOCK.getKey(oreBlock) + ")");

            if (states.isEmpty()) {
                System.out.println("  → Skipping (no valid raw rocks): " + bVein.veinName);
                continue;
            }

            // Register with distinct name to avoid key collision with CompatVein
            registerVein(context, bVein.veinName + "_block", bVein.veinType, bVein.rarity, bVein.density,
                    bVein.minY, bVein.maxY, bVein.size, bVein.height,
                    bVein.minSkew, bVein.maxSkew, bVein.minSlant, bVein.maxSlant,
                    bVein.sign, bVein.pipeHeight, bVein.radius, states);
        }
    }

     */


    /*
    private static void registerVein(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                     String nameSuffix, BlockVein.BlockVeinType type,
                                     int rarity, float density, int minY, int maxY,
                                     Integer size, Integer height,
                                     Integer minSkew, Integer maxSkew,
                                     Integer minSlant, Integer maxSlant,
                                     Integer sign, Integer pipeHeight, Integer radius,
                                     Map<Block, IWeighted<BlockState>> states) {

        VeinConfig baseConfig = new VeinConfig(
                states,
                Optional.empty(),  // indicator – add later if needed
                rarity,
                density,
                minY,
                maxY,
                false, false,
                veinSeedFromName(nameSuffix),
                false  // nearLava – add field if needed
        );

        IVeinConfig veinConfig;
        ResourceLocation featureId;

        switch (type) {
            case DISC -> {
                veinConfig = new DiscVeinConfig(baseConfig, size != null ? size : 20, height != null ? height : 4);
                featureId = ResourceLocation.fromNamespaceAndPath("tfc", "disc_vein");
            }
            case CLUSTER -> {
                veinConfig = new ClusterVeinConfig(baseConfig, size != null ? size : 20);
                featureId = ResourceLocation.fromNamespaceAndPath("tfc", "cluster_vein");
            }
            case PIPE -> {
                veinConfig = new PipeVeinConfig(
                        baseConfig,
                        pipeHeight != null ? pipeHeight : 60,
                        radius != null ? radius : 5,
                        minSkew != null ? minSkew : 5,
                        maxSkew != null ? maxSkew : 13,
                        minSlant != null ? minSlant : 0,
                        maxSlant != null ? maxSlant : 2,
                        sign != null ? sign.floatValue() : 0.0f
                );
                featureId = ResourceLocation.fromNamespaceAndPath("tfc", "pipe_vein");
            }
            default -> throw new IllegalArgumentException("Unknown vein type: " + type);
        }

        Feature<IVeinConfig> feature = (Feature<IVeinConfig>) BuiltInRegistries.FEATURE.get(featureId);
        if (feature == null) {
            System.err.println("Feature not found: " + featureId);
            return;
        }

        ConfiguredFeature<?, ?> configured = new ConfiguredFeature<>(feature, veinConfig);

        ResourceKey<ConfiguredFeature<?, ?>> key = ResourceKey.create(
                Registries.CONFIGURED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(FirmaCompat.MODID, nameSuffix + "_vein_configured")
        );

        context.register(key, configured);
        System.out.println("  → Registered: " + key.location());
    }

     */

    /*
    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        //Compat Vein Feature
        for (CompatVein vein : CompatVein.values()) {
            System.out.println("Processing vein: " + vein.veinName + " (" + vein.veinType + ")");
            //ResourceLocation oreBaseRl = ResourceLocation.fromNamespaceAndPath(FirmaCompat.MODID, "ore/" + vein.veinName);

            // Build the states map: raw rock Block → weighted ore states
            Map<Block, IWeighted<BlockState>> states = new HashMap<>();
            int addedCount = 0;

            if(vein.ore.isGraded()){
                for (CompatRock rock : CompatRock.values()) {
                    Block poorOreBlock = ModBlocks.GRADED_ORES.get(rock).get(vein.ore).get(CompatOre.Grade.POOR).get();
                    Block normalOreBlock = ModBlocks.GRADED_ORES.get(rock).get(vein.ore).get(CompatOre.Grade.NORMAL).get();
                    Block richOreBlock = ModBlocks.GRADED_ORES.get(rock).get(vein.ore).get(CompatOre.Grade.RICH).get();

                    Block rawBlock = rock.vanillaEquivalent().get();

                    if (poorOreBlock == null || normalOreBlock == null || richOreBlock == null) {
                        System.out.println("  - Skipping graded vein (missing variant): " + vein.veinName + " / " + rock.getSerializedName());
                        continue;
                    }

                    if (rawBlock == null || rawBlock == Blocks.AIR) {
                        System.out.println("  - Skipping raw rock (not found): " + rock.getSerializedName());
                        continue;
                    }

                    BlockState poorOreState = poorOreBlock.defaultBlockState();
                    BlockState normalOreState = normalOreBlock.defaultBlockState();
                    BlockState richOreState = richOreBlock.defaultBlockState();

                    // TFC-style weights: poor ~15%, normal ~25%, rich ~60%
                    // Total = 100 → weights are percentages
                    if(vein.gradedVein.equals("normal")){
                        List<Pair<BlockState, Double>> weightedList = List.of(
                                Pair.of(poorOreState,   15.0),
                                Pair.of(normalOreState, 25.0),
                                Pair.of(richOreState,   60.0)
                        );

                        IWeighted<BlockState> weighted = new Weighted<>(weightedList);

                        states.put(rawBlock, weighted);
                        addedCount++;
                    } else if(vein.gradedVein.equals("surface")){
                        List<Pair<BlockState, Double>> weightedList = List.of(
                                Pair.of(poorOreState,   70.0),
                                Pair.of(normalOreState, 25.0),
                                Pair.of(richOreState,   5.0)
                        );

                        IWeighted<BlockState> weighted = new Weighted<>(weightedList);

                        states.put(rawBlock, weighted);
                        addedCount++;
                    } else {
                        System.out.println("  - Invalid Graded Vein Type: " + vein.gradedVein);
                    }
                }

                System.out.println("  → Added " + addedCount + " rock/ore pairs to states map");
            } else {
                for (CompatRock rock : CompatRock.values()) {
                    Block oreBlock = ModBlocks.ORES.get(rock).get(vein.ore).get();

                    Block rawBlock = rock.vanillaEquivalent().get();

                    BlockState oreState = oreBlock.defaultBlockState();

                    // Simple: single state with weight 1.0
                    List<Pair<BlockState, Double>> weightedList = List.of(Pair.of(oreState, 1.0));

                    IWeighted<BlockState> weighted = new Weighted<>(weightedList);

                    states.put(rawBlock, weighted);
                    addedCount++;
                }

                System.out.println("  → Added " + addedCount + " rock/ore pairs to states map");
            }

            // Then create VeinConfig
            VeinConfig baseConfig = new VeinConfig(
                    states,
                    Optional.empty(),  // indicator
                    vein.rarity,
                    vein.density,
                    vein.minY,
                    vein.maxY,
                    false,             // projectToSurface
                    false,             // projectOffset
                    veinSeedFromName(vein.veinName),  // ← use our helper
                    false              // nearLava
            );

            // Now wrap in the specific config type
            IVeinConfig veinConfig;
            ResourceLocation featureId;

            switch (vein.veinType) {
                case DISC -> {
                    veinConfig = new DiscVeinConfig(baseConfig, vein.size, vein.height);
                    featureId = ResourceLocation.fromNamespaceAndPath("tfc", "disc_vein");
                }
                case CLUSTER -> {
                    veinConfig = new ClusterVeinConfig(baseConfig, vein.size);
                    featureId = ResourceLocation.fromNamespaceAndPath("tfc", "cluster_vein");
                }
                case PIPE -> {
                    veinConfig = new PipeVeinConfig(
                            baseConfig,
                            vein.pipeHeight != null ? vein.pipeHeight : 60,  // fallback
                            vein.radius != null ? vein.radius : 5,
                            vein.minSkew != null ? vein.minSkew : 5,
                            vein.maxSkew != null ? vein.maxSkew : 13,
                            vein.minSlant != null ? vein.minSlant : 0,
                            vein.maxSlant != null ? vein.maxSlant : 2,
                            vein.sign != null ? vein.sign.floatValue() : 0.0f
                    );
                    featureId = ResourceLocation.fromNamespaceAndPath("tfc", "pipe_vein");
                }
                default -> throw new IllegalArgumentException("Unknown vein type: " + vein.veinType);
            }

            // Get the Feature instance
            @SuppressWarnings("unchecked")
            Feature<IVeinConfig> feature = (Feature<IVeinConfig>) BuiltInRegistries.FEATURE.get(featureId);
            if (feature == null) {
                // Error handling – TFC not loaded properly?
                continue;
            }

            ConfiguredFeature<?, ?> configured = new ConfiguredFeature<>(feature, veinConfig);

            // Register
            ResourceKey<ConfiguredFeature<?, ?>> key = ResourceKey.create(
                    Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(FirmaCompat.MODID, vein.veinName + "_vein_configured")
            );

            context.register(key, configured);
        }
    }

    private static long veinSeedFromName(String name) {
        RandomSupport.Seed128bit seed128 = RandomSupport.seedFromHashOf(name);
        return seed128.seedLo() ^ seed128.seedHi();
    }

     */
}
