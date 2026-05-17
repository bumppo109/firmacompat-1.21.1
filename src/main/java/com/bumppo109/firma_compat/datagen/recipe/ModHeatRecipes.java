package com.bumppo109.firma_compat.datagen.recipe;

import com.bumppo109.firma_compat.block.CompatMetal;
import com.bumppo109.firma_compat.block.ModBlocks;
import com.bumppo109.firma_compat.item.ModItems;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.dries007.tfc.common.items.Food;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.common.recipes.HeatingRecipe;
import net.dries007.tfc.common.recipes.ingredients.AndIngredient;
import net.dries007.tfc.common.recipes.ingredients.NotRottenIngredient;
import net.dries007.tfc.common.recipes.outputs.CopyFoodModifier;
import net.dries007.tfc.common.recipes.outputs.ItemStackProvider;
import net.dries007.tfc.util.Metal;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.Objects;

import static com.bumppo109.firma_compat.datagen.BuiltinItemHeat.copperType;

public interface ModHeatRecipes extends ModRecipes
{
    default void heatRecipes()
    {
        addFood(Items.KELP, Items.DRIED_KELP);
        burnFood("kelp", Ingredient.of(Items.DRIED_KELP), 700);
        add(Ingredient.of(Items.CHAIN), new FluidStack(meltFluidFor(Metal.CAST_IRON), 6), 1535);
        add(Ingredient.of(Items.IRON_NUGGET), new FluidStack(meltFluidFor(Metal.CAST_IRON), 10), 1535);
        add(Ingredient.of(Items.GOLD_NUGGET), new FluidStack(meltFluidFor(Metal.GOLD), 10), 1060);

        add(ModItems.UNFIRED_POT, Items.DECORATED_POT, 1399);

        ModItems.METAL_ITEMS.forEach((metal, items) -> items.forEach((type, item) -> add(nameOf(item), new HeatingRecipe(
                ingredientOf(metal, type),
                ItemStackProvider.empty(),
                new FluidStack(meltFluidFor(metal), units(type)),
                temperatureOf(metal), new ItemStack(item).isDamageableItem()))));

        copperType.forEach(type -> {
            for(WeatheringCopper.WeatherState weatherState : WeatheringCopper.WeatherState.values()){
                String idStr;
                String waxIdStr;
                if(weatherState.equals(WeatheringCopper.WeatherState.UNAFFECTED)){
                    if(type.equals("copper")){
                        idStr = "copper_block";
                        waxIdStr = "waxed_copper_block";
                    } else {
                        idStr = type;
                        waxIdStr = "waxed_" + type;
                    }
                } else {
                    idStr = weatherState.getSerializedName() + "_" + type;
                    waxIdStr = "waxed_" + weatherState.getSerializedName() + "_" + type;
                }

                Item item = Objects.requireNonNull(BuiltInRegistries.ITEM.get(ResourceLocation.withDefaultNamespace(idStr)));
                Item waxedItem = Objects.requireNonNull(BuiltInRegistries.ITEM.get(ResourceLocation.withDefaultNamespace(waxIdStr)));
                switch (type) {
                    case "copper_door", "copper_trapdoor" -> add(Ingredient.of(item), new FluidStack(meltFluidFor(Metal.COPPER), 200), 1060);
                    case "cut_copper_stairs" -> add(Ingredient.of(item), new FluidStack(meltFluidFor(Metal.COPPER), 75), 1060);
                    case "cut_copper_slab" -> add(Ingredient.of(item), new FluidStack(meltFluidFor(Metal.COPPER), 50), 1060);
                    default -> add(Ingredient.of(item), new FluidStack(meltFluidFor(Metal.COPPER), 100), 1060);
                }
                switch (type) {
                    case "copper_door", "copper_trapdoor" -> add(Ingredient.of(waxedItem), new FluidStack(meltFluidFor(Metal.COPPER), 200), 1060);
                    case "cut_copper_stairs" -> add(Ingredient.of(waxedItem), new FluidStack(meltFluidFor(Metal.COPPER), 75), 1060);
                    case "cut_copper_slab" -> add(Ingredient.of(waxedItem), new FluidStack(meltFluidFor(Metal.COPPER), 50), 1060);
                    default -> add(Ingredient.of(waxedItem), new FluidStack(meltFluidFor(Metal.COPPER), 100), 1060);
                }
            }
        });
    }

    private Fluid meltFluidFor(CompatMetal metal)
    {
        return fluidOf(switch (metal)
        {
            default -> metal;
        });
    }

    private Fluid meltFluidFor(Metal metal)
    {
        return fluidOf(switch (metal)
        {
            default -> metal;
        });
    }

    private Ingredient notRotten(ItemLike input)
    {
        return AndIngredient.of(Ingredient.of(input), NotRottenIngredient.INSTANCE);
    }

    private void addFood(Food input, Food output)
    {
        add(notRotten(TFCItems.FOOD.get(input)), ItemStackProvider.of(new ItemStack(TFCItems.FOOD.get(output)), CopyFoodModifier.INSTANCE), 200);
    }

    private void addFood(Item input, Item output)
    {
        add(notRotten(input), ItemStackProvider.of(new ItemStack(output), CopyFoodModifier.INSTANCE), 200);
    }

    private void burnFood(String name, Ingredient input, float temperature)
    {
        add("burn_" + name, new HeatingRecipe(input, ItemStackProvider.empty(), FluidStack.EMPTY, temperature, false));
    }

    private void add(ItemLike input, ItemLike output, float temperature)
    {
        add(Ingredient.of(input), ItemStackProvider.of(output), temperature);
    }

    private void add(Ingredient input, FluidStack output, float temperature)
    {
        add(nameOf(input), new HeatingRecipe(input, ItemStackProvider.empty(), output, temperature, false));
    }

    private void add(String suffix, Ingredient input, ItemStackProvider output, float temperature)
    {
        add(nameOf(output.getEmptyStack().getItem()) + "_" + suffix, new HeatingRecipe(input, output, FluidStack.EMPTY, temperature, false));
    }

    private void add(Ingredient input, ItemStackProvider output, float temperature)
    {
        add(new HeatingRecipe(input, output, FluidStack.EMPTY, temperature, false));
    }
}
