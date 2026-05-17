package com.bumppo109.firma_compat.integration.dynamic_light;

import com.bumppo109.firma_compat.FirmaCompat;
import com.bumppo109.firma_compat.block.ModBlocks;
import dev.lambdaurora.lambdynlights.api.item.ItemLightSources;
import net.dries007.tfc.util.Metal;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;

@EventBusSubscriber(modid = FirmaCompat.MODID, value = Dist.CLIENT)
public class DynamicLightRegister {

    private static boolean REGISTERED = false;

    @SubscribeEvent
    public static void onClientLogin(ClientPlayerNetworkEvent.LoggingIn event) {

        if (REGISTERED) return;
        REGISTERED = true;

        // Ensure we run AFTER client is fully ready
        Minecraft.getInstance().execute(DynamicLightRegister::registerLights);
    }

    private static void registerLights() {

        // --- base lantern ---
        registerLantern("lantern", ModBlocks.LANTERN.get().asItem());

        // --- TFC metals ---
        for (Metal metal : Metal.values()) {
            if (!metal.allParts()) continue;

            var block = ModBlocks.COMPAT_LANTERNS.get(metal);
            if (block == null || block.get() == null) continue;

            registerLantern(metal.getSerializedName() + "_lantern", block.get().asItem());
        }
    }

    private static void registerLantern(String name, net.minecraft.world.item.Item item) {

        if (item == null) return;

        ItemLightSources.registerItemLightSource(
                new ModLitItemLightSource(
                        ResourceLocation.fromNamespaceAndPath(FirmaCompat.MODID, name),
                        item
                )
        );
    }
}