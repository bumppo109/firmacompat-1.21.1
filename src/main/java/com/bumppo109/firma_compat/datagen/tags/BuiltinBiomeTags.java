package com.bumppo109.firma_compat.datagen.tags;

import com.bumppo109.firma_compat.FirmaCompat;
import com.bumppo109.firma_compat.block.CompatOre;
import com.bumppo109.firma_compat.util.ModTags;
import com.bumppo109.firma_compat.worldgen.CompatVein;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static com.bumppo109.firma_compat.util.ModTags.Biomes.FORESTED_BIOMES;

public class BuiltinBiomeTags extends TagsProvider<Biome> {

    public BuiltinBiomeTags(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider,
            ExistingFileHelper existingFileHelper
    ) {
        super(output, Registries.BIOME, lookupProvider, FirmaCompat.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(FORESTED_BIOMES)
                .addTags(Tags.Biomes.IS_FOREST)
                .addTags(Tags.Biomes.IS_TAIGA)
                .addTags(Tags.Biomes.IS_JUNGLE)
        ;
    }

    @Override
    public String getName() {
        return "FirmaCompat Biome Tags";
    }
}
