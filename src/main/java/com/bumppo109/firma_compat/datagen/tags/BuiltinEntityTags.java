package com.bumppo109.firma_compat.datagen.tags;

import com.bumppo109.firma_compat.FirmaCompat;
import com.bumppo109.firma_compat.block.CompatMetal;
import com.bumppo109.firma_compat.block.CompatRock;
import com.bumppo109.firma_compat.block.CompatWood;
import com.bumppo109.firma_compat.block.ModBlocks;
import com.bumppo109.firma_compat.datagen.ModAccessors;
import com.bumppo109.firma_compat.entity.CompatTFCEntities;
import com.bumppo109.firma_compat.item.ModItems;
import com.bumppo109.firma_compat.util.ModTags;
import com.google.common.base.Preconditions;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.rock.RockCategory;
import net.dries007.tfc.common.entities.aquatic.Fish;
import net.dries007.tfc.util.registry.IdHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.bumppo109.firma_compat.util.ModTags.Entities.COMPAT_TFC_ENTITIES;
import static com.bumppo109.firma_compat.util.ModTags.Items.*;
import static net.dries007.tfc.common.TFCTags.Items.*;

public class BuiltinEntityTags extends EntityTypeTagsProvider
{
    public BuiltinEntityTags(GatherDataEvent event, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(event.getGenerator().getPackOutput(), provider, FirmaCompat.MODID, event.getExistingFileHelper());
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        tag(COMPAT_TFC_ENTITIES)
                .add(CompatTFCEntities.HORSE.get())
                .add(CompatTFCEntities.FRESHWATER_FISH.get(Fish.BLUEGILL).get())
                .add(CompatTFCEntities.FRESHWATER_FISH.get(Fish.CRAPPIE).get())
                .add(CompatTFCEntities.FRESHWATER_FISH.get(Fish.LAKE_TROUT).get())
                .add(CompatTFCEntities.FRESHWATER_FISH.get(Fish.RAINBOW_TROUT).get())
                .add(CompatTFCEntities.FRESHWATER_FISH.get(Fish.LARGEMOUTH_BASS).get())
                .add(CompatTFCEntities.FRESHWATER_FISH.get(Fish.SMALLMOUTH_BASS).get())
                .add(CompatTFCEntities.FRESHWATER_FISH.get(Fish.SALMON).get())
                .add(CompatTFCEntities.COD.get())
                .add(CompatTFCEntities.TROPICAL_FISH.get())
                .add(CompatTFCEntities.PUFFERFISH.get())
                .add(CompatTFCEntities.ISOPOD.get())
                .add(CompatTFCEntities.CRAYFISH.get())
                .add(CompatTFCEntities.LOBSTER.get())
                .add(CompatTFCEntities.HORSESHOE_CRAB.get())
                .add(CompatTFCEntities.JELLYFISH.get())
                .add(CompatTFCEntities.ORCA.get())
                .add(CompatTFCEntities.DOLPHIN.get())
                .add(CompatTFCEntities.MANATEE.get())
                .add(CompatTFCEntities.CROCODILE.get())
                .add(CompatTFCEntities.PENGUIN.get())
                .add(CompatTFCEntities.LEOPARD_SEAL.get())
                .add(CompatTFCEntities.FROG.get())
                .add(CompatTFCEntities.TURTLE.get())
                .add(CompatTFCEntities.POLAR_BEAR.get())
                .add(CompatTFCEntities.GRIZZLY_BEAR.get())
                .add(CompatTFCEntities.BLACK_BEAR.get())
                .add(CompatTFCEntities.COUGAR.get())
                .add(CompatTFCEntities.PANTHER.get())
                .add(CompatTFCEntities.LION.get())
                .add(CompatTFCEntities.SABERTOOTH.get())
                .add(CompatTFCEntities.TIGER.get())
                .add(CompatTFCEntities.SQUID.get())
                .add(CompatTFCEntities.OCTOPOTEUTHIS.get())
                .add(CompatTFCEntities.PIG.get())
                .add(CompatTFCEntities.COW.get())
                .add(CompatTFCEntities.GOAT.get())
                .add(CompatTFCEntities.YAK.get())
                .add(CompatTFCEntities.ALPACA.get())
                .add(CompatTFCEntities.SHEEP.get())
                .add(CompatTFCEntities.MUSK_OX.get())
                .add(CompatTFCEntities.CHICKEN.get())
                .add(CompatTFCEntities.DUCK.get())
                .add(CompatTFCEntities.QUAIL.get())
                .add(CompatTFCEntities.RABBIT.get())
                .add(CompatTFCEntities.FOX.get())
                .add(CompatTFCEntities.PANDA.get())
                .add(CompatTFCEntities.OCELOT.get())
                .add(CompatTFCEntities.BOAR.get())
                .add(CompatTFCEntities.WILDEBEEST.get())
                .add(CompatTFCEntities.BISON.get())
                .add(CompatTFCEntities.BONGO.get())
                .add(CompatTFCEntities.CARIBOU.get())
                .add(CompatTFCEntities.DEER.get())
                .add(CompatTFCEntities.GAZELLE.get())
                .add(CompatTFCEntities.MOOSE.get())
                .add(CompatTFCEntities.GROUSE.get())
                .add(CompatTFCEntities.PHEASANT.get())
                .add(CompatTFCEntities.TURKEY.get())
                .add(CompatTFCEntities.PEAFOWL.get())
                .add(CompatTFCEntities.WOLF.get())
                .add(CompatTFCEntities.HYENA.get())
                .add(CompatTFCEntities.DIREWOLF.get())
                .add(CompatTFCEntities.DONKEY.get())
                .add(CompatTFCEntities.MULE.get());
    }
}