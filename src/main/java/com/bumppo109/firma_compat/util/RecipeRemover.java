package com.bumppo109.firma_compat.util;

import com.bumppo109.firma_compat.FirmaCompat;
import com.bumppo109.firma_compat.FirmaCompatConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Removes recipes whose output matches a per-type tag,
 * but preserves any recipe from a namespace listed in the config.
 */
public class RecipeRemover {

    // ─── Define which tags to check for each recipe type ───
    // Add more entries as needed
    private static final List<RemovalConfig> REMOVAL_CONFIGS = List.of(
            new RemovalConfig(RecipeType.CRAFTING, ModTags.Items.REMOVE_FROM_CRAFTING),
            new RemovalConfig(RecipeType.SMELTING, ModTags.Items.REMOVE_FROM_SMELTING),
            new RemovalConfig(RecipeType.SMITHING, ModTags.Items.REMOVE_FROM_SMITHING),
            new RemovalConfig(RecipeType.STONECUTTING, ModTags.Items.REMOVE_FROM_STONECUTTING)
            // new RemovalConfig(RecipeType.CAMPFIRE_COOKING, FirmaCompatTags.Items.REMOVE_FROM_CAMPFIRE), etc.
    );

    private record RemovalConfig(RecipeType<?> type, TagKey<Item> tag) {}

    public static void init() {
        NeoForge.EVENT_BUS.addListener(RecipeRemover::onServerStarting);
    }

    private static void onServerStarting(ServerStartingEvent event) {
        RecipeManager rm = event.getServer().getRecipeManager();
        var registries = event.getServer().registryAccess();

        // Load preserved namespaces from config
        Set<String> preservedNamespaces = new HashSet<>(FirmaCompatConfig.getAllowedNamespaces());
        FirmaCompat.LOGGER.debug("Preserving recipes from namespaces: {}", preservedNamespaces);

        // Collect IDs to remove
        Set<ResourceLocation> toRemove = new HashSet<>();
        int totalRemoved = 0;

        for (RemovalConfig cfg : REMOVAL_CONFIGS) {
            @SuppressWarnings("unchecked")
            List<RecipeHolder<?>> recipesOfType = rm.getAllRecipesFor((RecipeType) cfg.type());

            for (RecipeHolder<?> holder : recipesOfType) {
                ResourceLocation recipeId = holder.id();

                // 1. Preserve if namespace is allowed in config
                if (preservedNamespaces.contains(recipeId.getNamespace())) {
                    continue;
                }

                // 2. Check if output matches the removal tag for this type
                ItemStack result = holder.value().getResultItem(registries);
                if (result.is(cfg.tag())) {
                    toRemove.add(recipeId);
                    totalRemoved++;
                    FirmaCompat.LOGGER.info("Removing recipe {} (type: {}, produces: {})",
                            recipeId, cfg.type(), result.getHoverName().getString());
                }
            }
        }

        if (!toRemove.isEmpty()) {
            // Build new list of keepers
            List<RecipeHolder<?>> keepers = new ArrayList<>();
            for (RecipeHolder<?> holder : rm.getRecipes()) {
                if (!toRemove.contains(holder.id())) {
                    keepers.add(holder);
                }
            }

            // Apply replacement
            rm.replaceRecipes(keepers);
            FirmaCompat.LOGGER.info("Removed {} recipes across {} configured types", totalRemoved, REMOVAL_CONFIGS.size());
        } else {
            FirmaCompat.LOGGER.debug("No recipes matched removal criteria");
        }
    }
}