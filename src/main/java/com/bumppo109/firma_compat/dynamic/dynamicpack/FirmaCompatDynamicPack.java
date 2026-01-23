package com.bumppo109.firma_compat.dynamic.dynamicpack;

import com.bumppo109.firma_compat.FirmaCompat;
import com.bumppo109.firma_compat.FirmaCompatHelpers;
import com.bumppo109.firma_compat.block.CompatMetal;
import com.bumppo109.firma_compat.block.CompatWood;
import com.bumppo109.firma_compat.item.ModItems;
import com.bumppo109.firma_compat.tfcaddon.rnr.RNRCompatItems;
import it.unimi.dsi.fastutil.Pair;
import net.mehvahdjukaar.moonlight.api.events.AfterLanguageLoadEvent;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynamicClientResourceProvider;
import net.mehvahdjukaar.moonlight.api.resources.pack.PackGenerationStrategy;
import net.mehvahdjukaar.moonlight.api.resources.pack.ResourceGenTask;
import net.mehvahdjukaar.moonlight.api.resources.textures.Palette;
import net.mehvahdjukaar.moonlight.api.resources.textures.Respriter;
import net.mehvahdjukaar.moonlight.api.resources.textures.TextureImage;
import net.mehvahdjukaar.moonlight.api.resources.textures.TextureOps;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class FirmaCompatDynamicPack {

    // call during mod init
    public static void init() {
        //register the generator
        RegHelper.registerDynamicResourceProvider(new ExampleModDynamicClientAssets());
    }

    // Class responsible to generate assets into your dynamic pack
    public static class ExampleModDynamicClientAssets extends DynamicClientResourceProvider {

        public ExampleModDynamicClientAssets() {
            super(FirmaCompatHelpers.modIdentifier("firma_compat"), PackGenerationStrategy.CACHED);
            //try other strategies aswell or implement your own
        }


        @Override
        protected Collection<String> gatherSupportedNamespaces() {
            //All known namespaces that the pack will support must be known beforehand
            return List.of(
                    "minecraft",
                    "firma_compat",
                    "tfc"
            );

        }

        // generate here your assets
        @Override
        public void regenerateDynamicAssets(Consumer<ResourceGenTask> executor) {
            executor.accept((manager, sink) -> {
                for(CompatWood wood : CompatWood.VALUES){
                    FirmaCompat.LOGGER.error("Starting wood texture gen");

                    ResourceLocation shingleItemRes = ResourceLocation.fromNamespaceAndPath(FirmaCompat.MODID, "block/oak_shingles");
                    ResourceLocation woodBlockRes;

                    if(wood.equals(CompatWood.CRIMSON) || wood.equals(CompatWood.WARPED)){
                        woodBlockRes = ResourceLocation.fromNamespaceAndPath("minecraft", "block/stripped_" + wood.getSerializedName() + "_stem");
                    } else {
                        woodBlockRes = ResourceLocation.fromNamespaceAndPath("minecraft", "block/stripped_" + wood.getSerializedName() + "_log");
                    }


                    try (TextureImage woodImage = TextureImage.open(manager, woodBlockRes);
                         TextureImage woodItemImage = TextureImage.open(manager, shingleItemRes)){

                        // Step 1: Make a safe working copy (never mutate opened resources directly!)
                        TextureImage working = woodItemImage.makeCopy();
                        // Step 2: Grayscale the item texture (TFC wrought iron items are usually already near-grayscale,
                        // but this ensures consistency and better palette mapping)
                        TextureOps.grayscale(working);

                        // Step 3: Build palette from the target metal block texture
                        Palette woodPalette = Palette.fromImage(woodImage);

                        // Step 4: Recolor the grayscale version
                        Respriter respriter = Respriter.of(working);
                        TextureImage recolored = respriter.recolor(woodPalette);

                        String itemPath = Utils.getID(RNRCompatItems.SHINGLE.get(wood)).getPath();

                        ResourceLocation targetLoc = FirmaCompatHelpers.modIdentifier(
                                "textures/block/rnr/" + itemPath);

                        FirmaCompat.LOGGER.error(targetLoc.toString());

                        sink.addTextureIfNotPresent(manager, targetLoc, () -> recolored);
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                }

                /*
                for (CompatMetal metal : CompatMetal.values()) {
                    for (CompatMetal.ItemType itemType : CompatMetal.ItemType.values()) {
                        FirmaCompat.LOGGER.error("Starting metal texture gen");

                        if(metal.blockMatch().get() != null){
                            String metalBlockPath = Utils.getID(metal.blockMatch()).getPath();
                            String metalBlockNamespace = Utils.getID(metal.blockMatch()).getNamespace();
                            ResourceLocation metalBlockRes = ResourceLocation.fromNamespaceAndPath(metalBlockNamespace, "block/" + metalBlockPath);
                            ResourceLocation tfcItemRes = ResourceLocation.fromNamespaceAndPath("tfc", "item/metal/" + itemType.toString().toLowerCase() + "/wrought_iron");

                            if(itemType.toString().equalsIgnoreCase("unfinished_leggings")){
                                tfcItemRes = ResourceLocation.fromNamespaceAndPath("tfc", "item/metal/unfinished_greaves/wrought_iron");
                            }

                            try (TextureImage metalBlockImage = TextureImage.open(manager, metalBlockRes);
                                 TextureImage metalItemImage = TextureImage.open(manager, tfcItemRes)){

                                // Step 1: Make a safe working copy (never mutate opened resources directly!)
                                TextureImage working = metalItemImage.makeCopy();
                                // Step 2: Grayscale the item texture (TFC wrought iron items are usually already near-grayscale,
                                // but this ensures consistency and better palette mapping)
                                TextureOps.grayscale(working);

                                // Step 3: Build palette from the target metal block texture
                                Palette metalPalette = Palette.fromImage(metalBlockImage);

                                // Step 4: Recolor the grayscale version
                                Respriter respriter = Respriter.of(working);
                                TextureImage recolored = respriter.recolor(metalPalette);

                                String itemPath = Utils.getID(ModItems.METAL_ITEMS.get(metal).get(itemType).get()).getPath();

                                ResourceLocation targetLoc = FirmaCompatHelpers.modIdentifier(
                                        "textures/item/" + itemPath);

                                FirmaCompat.LOGGER.error(targetLoc.toString());

                                sink.addTextureIfNotPresent(manager, targetLoc, () -> recolored);
                            } catch (Exception e) {
                                throw new IllegalStateException(e);
                            }
                        }
                    }
                }

                 */
            });
        }

        public static Pair<String, String> splitOnColon(String id) {
            if (id == null || id.isEmpty()) {
                return Pair.of("", "");
            }

            int colonIndex = id.indexOf(':');
            if (colonIndex == -1) {
                // No colon → assume path only
                return Pair.of("", id);
                // or: return Pair.of("minecraft", id);
            }

            String prefix = id.substring(0, colonIndex);
            String suffix = id.substring(colonIndex + 1);

            return Pair.of(prefix, suffix);
        }

        public static String insertBlockPrefix(String id) {
            if (id == null) return null;

            String[] parts = id.split(":", 2);  // limit to 2 parts → namespace + path

            if (parts.length != 2) {
                // no colon or malformed → return original or throw, depending on your needs
                return id;
            }

            return parts[0] + ":block/" + parts[1];
        }

        @Override
        public void addDynamicTranslations(AfterLanguageLoadEvent languageEvent) {
            // Useful to add translation for dynamic blocks. See BlockSetExample
            //languageEvent.addEntry("moonlight.test.translation", "Hello World!");
        }

    }
}
