package com.bumppo109.firma_compat.datagen.tags;

import com.bumppo109.firma_compat.FirmaCompat;
import com.bumppo109.firma_compat.block.CompatOre;
import com.bumppo109.firma_compat.util.ModTags;
import com.bumppo109.firma_compat.worldgen.CompatVein;
import net.dries007.tfc.util.Metal;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static com.bumppo109.firma_compat.util.ModTags.Biomes.FORESTED_BIOMES;

public class BuiltinPlacedFeatureTags extends TagsProvider<PlacedFeature> {

    public BuiltinPlacedFeatureTags(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider,
            ExistingFileHelper existingFileHelper
    ) {
        super(output, Registries.PLACED_FEATURE, lookupProvider, FirmaCompat.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        var compatVeinsTag = ModTags.PlacedFeatures.COMPAT_VEINS;

        for (CompatVein vein : CompatVein.values()) {
            String veinName = vein.name().toLowerCase(java.util.Locale.ROOT);

            if(!vein.ore.equals(CompatOre.MALACHITE) && !vein.ore.equals(CompatOre.NATIVE_COPPER) && !vein.ore.equals(CompatOre.TETRAHEDRITE) &&
                    !vein.ore.equals(CompatOre.HEMATITE) && !vein.ore.equals(CompatOre.LIMONITE) && !vein.ore.equals(CompatOre.MAGNETITE)){
                ResourceLocation placedLoc = ResourceLocation.fromNamespaceAndPath(FirmaCompat.MODID,"overworld/vein/" + veinName);

                tag(compatVeinsTag).addOptional(placedLoc);
            }
        }
        ResourceLocation copperPlacerLoc = ResourceLocation.fromNamespaceAndPath(FirmaCompat.MODID,"vein/copper_vein_placer");
        ResourceLocation ironPlacerLoc = ResourceLocation.fromNamespaceAndPath(FirmaCompat.MODID,"vein/iron_vein_placer");

        tag(compatVeinsTag).addOptional(copperPlacerLoc);
        tag(compatVeinsTag).addOptional(ironPlacerLoc);
    }

    @Override
    public String getName() {
        return "FirmaCompat Placed Feature Tags";
    }
}
