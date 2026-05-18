package com.bumppo109.firma_compat.item;

import com.bumppo109.firma_compat.FirmaCompat;
import com.bumppo109.firma_compat.block.CompatMetal;
import com.bumppo109.firma_compat.block.CompatWood;
import com.bumppo109.firma_compat.block.ModBlocks;
import com.bumppo109.firma_compat.entity.CompatTFCEntities;
import com.bumppo109.firma_compat.fluid.ModFluids;
import net.dries007.tfc.common.Lore;
import net.dries007.tfc.common.entities.TFCEntities;
import net.dries007.tfc.common.entities.aquatic.Fish;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.dries007.tfc.common.items.*;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.registry.RegistryHolder;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, FirmaCompat.MODID);

    //Food
    public static final ItemId SWEET_BERRIES_JAR = register("sweet_berries_jar",
            () -> new Item((new Item.Properties()).component(Lore.TYPE, Lore.SEALED)));
    public static final ItemId SWEET_BERRIES_JAR_UNSEALED = register("sweet_berries_jar_unsealed",
            () -> new Item((new Item.Properties()).component(Lore.TYPE, Lore.UNSEALED).craftRemainder(TFCItems.EMPTY_JAR.get())));
    public static final ItemId SWEET_BERRIES_JAM = register("sweet_berries_jam",
            () -> new Item((new Item.Properties())));
    public static final ItemId GLOW_BERRIES_JAR = register("glow_berries_jar",
            () -> new Item((new Item.Properties()).component(Lore.TYPE, Lore.SEALED)));
    public static final ItemId GLOW_BERRIES_JAR_UNSEALED = register("glow_berries_jar_unsealed",
            () -> new Item((new Item.Properties()).component(Lore.TYPE, Lore.UNSEALED).craftRemainder(TFCItems.EMPTY_JAR.get())));
    public static final ItemId GLOW_BERRIES_JAM = register("glow_berries_jam",
            () -> new Item((new Item.Properties())));

    //Wood
    public static final ItemId COMPAT_CHEST_MINECART = register("compat_chest_minecart",
            (() -> new TFCMinecartItem(new Item.Properties(), TFCEntities.CHEST_MINECART,
                    () -> ModBlocks.COMPAT_CHEST.get().asItem())));
    public static final Map<CompatWood, ItemId> LUMBER = Helpers.mapOf(CompatWood.class, wood -> register(wood.name() + "_lumber"));

    public static final ItemId BAMBOO_LUMBER = register("bamboo_lumber");

    public static final Map<CompatWood, ItemId> SUPPORTS = Helpers.mapOf(CompatWood.class, wood ->
            register(wood.name() + "_support", () -> new StandingAndWallBlockItem(ModBlocks.WOODS.get(wood).get(CompatWood.BlockType.VERTICAL_SUPPORT).get(), ModBlocks.WOODS.get(wood).get(CompatWood.BlockType.HORIZONTAL_SUPPORT).get(), new Item.Properties(), Direction.DOWN))
    );

    //Rock
    public static final ItemId STONE_BRICK = register("stone_brick");
    public static final ItemId DEEPSLATE_BRICK = register("deepslate_brick");
    public static final ItemId DEEPSLATE_TILE = register("deepslate_tile");
    public static final ItemId POLISHED_BLACKSTONE_BRICK = register("polished_blackstone_brick");
    public static final ItemId END_STONE_BRICK = register("end_stone_brick");
    public static final ItemId TUFF_BRICK = register("tuff_brick");
    public static final ItemId ANDESITE_BRICK = register("andesite_brick");
    public static final ItemId DIORITE_BRICK = register("diorite_brick");
    public static final ItemId GRANITE_BRICK = register("granite_brick");
    public static final ItemId CALCITE_BRICK = register("calcite_brick");
    public static final ItemId DRIPSTONE_BRICK = register("dripstone_brick");
    public static final ItemId BASALT_BRICK = register("basalt_brick");

    public static final ItemId QUARTZ_BRICK = register("quartz_brick");
    public static final ItemId PRISMARINE_BRICK = register("prismarine_brick");

    //Misc
    public static final ItemId MUD_BRICK = register("mud_brick");
    public static final ItemId UNFIRED_POT = register("unfired_pot");

    //Metal
    public static final Map<CompatMetal, Map<CompatMetal.ItemType, ItemId>> METAL_ITEMS = Helpers.mapOf(CompatMetal.class, metal ->
            Helpers.mapOf(CompatMetal.ItemType.class, type -> type.has(metal), type ->
                    register(metal.name() + "_" + type.name(), () -> type.create(metal))
            )
    );

    public static final ItemId UNFINISHED_LANTERN = register("unfinished_lantern");
    public static final ItemId POOR_NETHERITE_INGOT = register("poor_netherite_ingot");

    //IRON used for GemsRealm Module
    public static final Map<CompatMetal.ItemType, ItemId> IRON_STUFF = Helpers.mapOf(CompatMetal.ItemType.class, type -> register("iron_" + type.name()));

    //Fluid Buckets
    public static final Map<CompatMetal, ItemId> METAL_FLUID_BUCKETS = Helpers.mapOf(CompatMetal.class, metal ->
            register("bucket/metal/" + metal.name(), () -> new BucketItem(ModFluids.METALS.get(metal).getSource(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)))
    );

    /*
    public static final ItemId PURIFIED_WATER_BUCKET = register("bucket/purified_water",
            () -> new BucketItem(ModFluids.PURIFIED_WATER.getSource(), (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1)));
     */

    //Fish buckets
    public static final Map<Fish, ModItems.ItemId> FRESHWATER_FISH_BUCKETS = Helpers.mapOf(Fish.class, (fish) -> register("bucket/" + fish.getSerializedName(), (Supplier)(() -> new MobBucketItem((EntityType)((CompatTFCEntities.Id)CompatTFCEntities.FRESHWATER_FISH.get(fish)).get(), net.minecraft.world.level.material.Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1)))));

    public static final ModItems.ItemId COD_BUCKET = register("bucket/cod", (Supplier)(() -> new MobBucketItem((EntityType)CompatTFCEntities.COD.get(), TFCFluids.SALT_WATER.getSource(), SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1))));
    public static final ModItems.ItemId PUFFERFISH_BUCKET = register("bucket/pufferfish", (Supplier)(() -> new MobBucketItem((EntityType)CompatTFCEntities.PUFFERFISH.get(), TFCFluids.SALT_WATER.getSource(), SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1))));
    public static final ModItems.ItemId JELLYFISH_BUCKET = register("bucket/jellyfish", (Supplier)(() -> new MobBucketItem((EntityType)CompatTFCEntities.JELLYFISH.get(), TFCFluids.SALT_WATER.getSource(), SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1))));
    public static final ModItems.ItemId TROPICAL_FISH_BUCKET = register("bucket/tropical_fish", (Supplier)(() -> new MobBucketItem((EntityType)CompatTFCEntities.TROPICAL_FISH.get(), TFCFluids.SALT_WATER.getSource(), SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1))));


    private static ItemId register(String name)
    {
        return register(name, () -> new Item(new Item.Properties()));
    }

    private static <T extends Item> ItemId register(String name, Supplier<T> item)
    {
        return new ItemId(ITEMS.register(name.toLowerCase(Locale.ROOT), item));
    }

    public record ItemId(DeferredHolder<Item, Item> holder) implements RegistryHolder<Item, Item>, ItemLike
    {
        @Override
        public Item asItem()
        {
            return get();
        }
    }
}
