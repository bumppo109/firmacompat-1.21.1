package com.bumppo109.firma_compat.integration.dynamic_light;

import com.bumppo109.firma_compat.block.ModBlocks;
import dev.lambdaurora.lambdynlights.api.item.ItemLightSources;
import net.dries007.tfc.util.Metal;
import net.minecraft.world.item.Item;

public class DynamicLighHandler {

    public static void initDynamicLight() {
        registerLitItem(ModBlocks.LANTERN.get().asItem(), 15);
        for(Metal metal : Metal.values()){
            if(metal.allParts()){
                registerLitItem(ModBlocks.COMPAT_LANTERNS.get(metal).get().asItem(), 15);
            }
        }
    }

    private static void registerLitItem(Item item, int lightLevel) {
        ItemLightSources.registerItemLightSource(
                new LitLanternLightSource(item, lightLevel)
        );
    }
}
