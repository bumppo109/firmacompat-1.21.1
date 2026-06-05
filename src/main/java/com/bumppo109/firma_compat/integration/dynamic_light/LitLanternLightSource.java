package com.bumppo109.firma_compat.integration.dynamic_light;

import com.bumppo109.firma_compat.data.ModDataComponents;
import dev.lambdaurora.lambdynlights.api.item.ItemLightSource;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class LitLanternLightSource extends ItemLightSource {

    private final Item targetItem;
    private final int lightLevel;

    public LitLanternLightSource(Item item, int lightLevel) {
        super(
                ResourceLocation.fromNamespaceAndPath(BuiltInRegistries.ITEM.getKey(item).getNamespace(),
                        BuiltInRegistries.ITEM.getKey(item).getPath()),
                item
        );
        this.targetItem = item;
        this.lightLevel = lightLevel;
    }

    @Override
    public int getLuminance(ItemStack stack) {
        if (!stack.is(targetItem)) {
            return 0;
        }
        boolean lit = stack.getOrDefault(ModDataComponents.LIT, false);
        return lit ? lightLevel : 0;
    }
}