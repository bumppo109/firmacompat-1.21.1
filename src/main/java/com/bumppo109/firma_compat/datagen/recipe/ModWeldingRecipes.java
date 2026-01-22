package com.bumppo109.firma_compat.datagen.recipe;

import com.bumppo109.firma_compat.block.CompatMetal;
import com.bumppo109.firma_compat.item.ModItems;
import net.dries007.tfc.common.recipes.WeldingRecipe;
import net.dries007.tfc.common.recipes.outputs.ItemStackProvider;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.Map;

import static com.bumppo109.firma_compat.block.CompatMetal.METAL_SETS;
import static net.dries007.tfc.common.recipes.WeldingRecipe.Behavior.COPY_BEST;
import static net.dries007.tfc.common.recipes.WeldingRecipe.Behavior.IGNORE;

public interface ModWeldingRecipes extends ModRecipes {

    default void weldingRecipes(){
        for (Map.Entry<CompatMetal, CompatMetal.MetalSet> entry : METAL_SETS.entrySet()) {
            CompatMetal metal = entry.getKey();
            CompatMetal.MetalSet set = entry.getValue();

            if(metal.defaultOnlyParts()){
                add(new WeldingRecipe(
                        Ingredient.of(set.ingot().get()),
                        Ingredient.of(set.ingot().get()),
                        metal.tier() - 1,
                        ItemStackProvider.of(ModItems.METAL_ITEMS.get(metal).get(CompatMetal.ItemType.DOUBLE_INGOT)),
                        IGNORE
                ));
                weld(metal, CompatMetal.ItemType.SHEET, CompatMetal.ItemType.SHEET, ModItems.METAL_ITEMS.get(metal).get(CompatMetal.ItemType.DOUBLE_SHEET), IGNORE);

            }
            if (metal.armorParts())
            {
                weld(metal, CompatMetal.ItemType.UNFINISHED_HELMET, CompatMetal.ItemType.SHEET, set.helmet().get(), COPY_BEST);
                weld(metal, CompatMetal.ItemType.UNFINISHED_CHESTPLATE, CompatMetal.ItemType.DOUBLE_SHEET, set.chestplate().get(), COPY_BEST);
                weld(metal, CompatMetal.ItemType.UNFINISHED_LEGGINGS, CompatMetal.ItemType.SHEET, set.leggings().get(), COPY_BEST);
                weld(metal, CompatMetal.ItemType.UNFINISHED_BOOTS, CompatMetal.ItemType.SHEET, set.boots().get(), COPY_BEST);
                //weld(metal, CompatMetal.ItemType.KNIFE_BLADE, CompatMetal.ItemType.KNIFE_BLADE, ModItems.METAL_ITEMS.get(metal).get(CompatMetal.ItemType.SHEARS), COPY_WORST);
            }
        }
    }

    private void weld(CompatMetal metal, CompatMetal.ItemType input1, CompatMetal.ItemType input2, ItemLike output, WeldingRecipe.Behavior behavior)
    {
        add(new WeldingRecipe(
                ingredientOf(metal, input1),
                ingredientOf(metal, input2),
                metal.tier() - 1,
                ItemStackProvider.of(output),
                behavior
        ));
    }
}
