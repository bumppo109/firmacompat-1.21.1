package com.bumppo109.firma_compat.dynamic.dynamicpack;

import com.bumppo109.firma_compat.FirmaCompat;
import com.bumppo109.firma_compat.FirmaCompatHelpers;
import com.bumppo109.firma_compat.block.CompatMetal;
import com.bumppo109.firma_compat.item.ModItems;
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

            });
        }

        @Override
        public void addDynamicTranslations(AfterLanguageLoadEvent languageEvent) {

        }

    }
}
