package com.bumppo109.firma_compat.datagen.recipe;

import com.bumppo109.firma_compat.FirmaCompatHelpers;
import com.bumppo109.firma_compat.item.ModItems;
import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.common.recipes.KnappingRecipe;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.data.KnappingPattern;
import net.dries007.tfc.util.data.KnappingType;
import net.dries007.tfc.util.registry.HolderHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

import static net.dries007.tfc.util.DataGenerationHelpers.Builder;

public interface ModKnappingRecipes extends ModRecipes
{
    ResourceLocation CLAY = Helpers.identifier("clay");

    default void knappingRecipes()
    {
        //Need fake manager for each knapping type
        FirmaCompatHelpers.fakeDataManager(KnappingType.MANAGER, Map.of(
                "clay", fake(TFCTags.Items.CLAY_KNAPPING, 5, 5,
                        TFCSounds.KNAP_CLAY,
                        true, true, false,
                        Items.CLAY_BALL))
        );

        clayKnapping(ModItems.UNFIRED_POT, " XXX ", "X   X", "X X X", "X   X", " XXX ");
    }


    private void clayKnapping(Metal.ItemType output, String... pattern)
    {
        clayKnapping("", TFCItems.UNFIRED_MOLDS.get(output), output == Metal.ItemType.INGOT ? 2 : 1, true, pattern);
    }

    private void clayKnapping(ItemLike output, String... pattern)
    {
        clayKnapping(output, 1, pattern);
    }

    private void clayKnapping(ItemLike output, int count, String... pattern)
    {
        clayKnapping("", output, count, false, pattern);
    }

    private void clayKnapping(String suffix, ItemLike output, int count, boolean defaultOn, String... pattern)
    {
        add(nameOf(output) + (suffix.isEmpty() ? "" : "_" + suffix), new KnappingRecipe(
                KnappingType.MANAGER.getCheckedReference(CLAY),
                KnappingPattern.from(defaultOn, pattern),
                Optional.empty(),
                new ItemStack(output, count)
        ));
        // Un-crafting, only for non-suffixed recipes
        if (suffix.isEmpty()) new Builder((name, r) -> add(nameOf(output) + "_to_clay", r))
                .input(output)
                .shapeless(Items.CLAY_BALL, 5 / count);
    }

    /*
    private void rockKnapping(RockCategory.ItemType output, String... pattern)
    {
        rockKnapping("", output, 1, pattern);
    }

    private void rockKnapping(String suffix, RockCategory.ItemType output, int count, String... pattern)
    {
        for (RockCategory type : RockCategory.values())
            add(nameOf(TFCItems.ROCK_TOOLS.get(type).get(output)) + (suffix.isEmpty() ? "" : "_" + suffix), new KnappingRecipe(
                    KnappingType.MANAGER.getCheckedReference(BuiltinKnappingTypes.ROCK),
                    KnappingPattern.from(false, pattern),
                    Optional.of(Ingredient.of(TFCTags.Items.STONES_LOOSE_CATEGORY.get(type))),
                    new ItemStack(TFCItems.ROCK_TOOLS.get(type).get(output), count)
            ));
    }

    private void fireClayKnapping(ItemLike output, int count, String... pattern)
    {
        fireClayKnapping("", output, count, pattern);
    }


    private void fireClayKnapping(String suffix, ItemLike output, int count, String... pattern)
    {
        add(nameOf(output) + (suffix.isEmpty() ? "" : "_" + suffix), new KnappingRecipe(
                KnappingType.MANAGER.getCheckedReference(BuiltinKnappingTypes.FIRE_CLAY),
                KnappingPattern.from(true, pattern),
                Optional.empty(),
                new ItemStack(output, count)
        ));
        // Un-crafting, only for non-suffixed recipes
        if (suffix.isEmpty()) new Builder((name, r) -> add(nameOf(output) + "_to_fire_clay", r))
                .input(output)
                .shapeless(TFCItems.FIRE_CLAY, 5 / count);
    }
    private void leatherKnapping(ItemLike output, String... pattern)
    {
        knapping(BuiltinKnappingTypes.LEATHER, pattern, output, 1);
    }

    private void goatKnapping(ResourceKey<Instrument> instrument, String... pattern)
    {
        final ItemStack output = Items.GOAT_HORN.getDefaultInstance();
        output.set(DataComponents.INSTRUMENT, BuiltInRegistries.INSTRUMENT.getHolderOrThrow(instrument));
        knapping(BuiltinKnappingTypes.GOAT_HORN, pattern, output, instrument.location().getPath() + "_goat_horn");
    }

     */

    private void knapping(ResourceLocation knappingType, String[] pattern, ItemLike output, int count)
    {
        knapping(knappingType, pattern, new ItemStack(output, count), null);
    }

    private void knapping(ResourceLocation knappingType, String[] pattern, ItemStack output, @Nullable String name)
    {
        final KnappingRecipe recipe = new KnappingRecipe(KnappingType.MANAGER.getCheckedReference(knappingType), KnappingPattern.from(true, pattern), Optional.empty(), output);
        if (name == null)
        {
            add(recipe);
        }
        else
        {
            add(name, recipe);
        }
    }

    private KnappingType fake(TagKey<Item> item, int amount, int consumeAmount, HolderHolder<SoundEvent> sound, boolean consumeAfterComplete, boolean useDisabledTexture, boolean spawnsParticles, ItemLike jeiIcon)
    {
        return new KnappingType(new SizedIngredient(Ingredient.of(item), amount), amount == consumeAmount ? Optional.empty() : Optional.of(consumeAmount), sound.holder(), consumeAfterComplete, useDisabledTexture, spawnsParticles, new ItemStack(jeiIcon));
    }
}
