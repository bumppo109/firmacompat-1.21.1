package com.bumppo109.firma_compat.integration.dynamic_light;

import com.bumppo109.firma_compat.util.ModDataComponents;
import dev.lambdaurora.lambdynlights.api.item.ItemLightSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ModLitItemLightSource extends ItemLightSource {

    public ModLitItemLightSource(ResourceLocation id, Item item) {
        super(id, item, false);
    }

    @Override
    public int getLuminance(ItemStack stack) {

        Boolean charge = stack.get(ModDataComponents.LIT);

        if (charge == null)
            return 0;

        if (charge){
            return 15;
        } else {
            return 0;
        }
    }
}
