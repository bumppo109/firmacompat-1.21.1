package com.bumppo109.firma_compat.data;

import com.bumppo109.firma_compat.entity.CompatFauna;
import net.dries007.tfc.common.component.food.FoodCapability;
import net.dries007.tfc.common.component.heat.HeatCapability;
import net.dries007.tfc.common.component.size.ItemSizeManager;
import net.dries007.tfc.common.entities.Fauna;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.climate.ClimateRange;
import net.dries007.tfc.util.data.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

public final class CompatDataManagers {

    static {
        DataManagers.MANAGERS.register(CompatFauna.MANAGER.getName(), () -> CompatFauna.MANAGER);
    }
}
