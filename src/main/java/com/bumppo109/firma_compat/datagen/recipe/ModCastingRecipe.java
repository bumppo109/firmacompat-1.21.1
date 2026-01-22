package com.bumppo109.firma_compat.datagen.recipe;

import com.bumppo109.firma_compat.block.CompatMetal;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.common.recipes.CastingRecipe;
import net.dries007.tfc.common.recipes.outputs.ItemStackProvider;
import net.dries007.tfc.util.Metal;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

import java.util.Map;

import static com.bumppo109.firma_compat.block.CompatMetal.METAL_SETS;

public interface ModCastingRecipe extends ModRecipes {
    default void castingRecipes()
    {
        for (Map.Entry<CompatMetal, CompatMetal.MetalSet> entry : METAL_SETS.entrySet()) {
            CompatMetal metal = entry.getKey();
            CompatMetal.MetalSet set = entry.getValue();

            add(metal.name() + "_ingot", new CastingRecipe(
                    Ingredient.of(TFCItems.MOLDS.get(Metal.ItemType.INGOT)),
                    SizedFluidIngredient.of(fluidOf(metal), 100),
                    ItemStackProvider.of(set.ingot().get()),
                    0.1f
            ));
            add(metal.name() + "_fire_ingot", new CastingRecipe(
                    Ingredient.of(TFCItems.FIRE_INGOT_MOLD),
                    SizedFluidIngredient.of(fluidOf(metal), 100),
                    ItemStackProvider.of(set.ingot().get()),
                    0.01f
            ));
        }

//        casting("gold_bell", TFCItems.BELL_MOLD, Metal.GOLD, Items.BELL, 100, 1f);
    }

    private void casting(String name, ItemLike item, CompatMetal metal, ItemLike result, int units, float chance)
    {
        add(name, new CastingRecipe(
                Ingredient.of(item),
                SizedFluidIngredient.of(fluidOf(metal), units),
                ItemStackProvider.of(result),
                chance
        ));
    }
}
