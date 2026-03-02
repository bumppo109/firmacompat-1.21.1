package com.bumppo109.firma_compat.block;

import com.bumppo109.firma_compat.FirmaCompat;
import com.bumppo109.firma_compat.fluid.ModFluids;
import com.bumppo109.firma_compat.item.ModItems;
import com.google.common.base.Suppliers;
import net.dries007.tfc.common.blockentities.FarmlandBlockEntity;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.HotWaterBlock;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.devices.DryingBricksBlock;
import net.dries007.tfc.common.blocks.rock.AqueductBlock;
import net.dries007.tfc.common.blocks.rock.RockAnvilBlock;
import net.dries007.tfc.common.blocks.soil.FarmlandBlock;
import net.dries007.tfc.common.blocks.wood.TFCChestBlock;
import net.dries007.tfc.common.blocks.wood.TFCTrappedChestBlock;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.dries007.tfc.common.items.ChestBlockItem;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.dries007.tfc.util.registry.RegistryHolder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(Registries.BLOCK, FirmaCompat.MODID);

    public static final DeferredRegister<Block> FLUID_BLOCKS =
            DeferredRegister.create(Registries.BLOCK, FirmaCompat.MODID);

    //Wood
    public static final Map<CompatWood, Map<CompatWood.BlockType, Id<Block>>> WOODS = Helpers.mapOf(CompatWood.class, wood ->
            Helpers.mapOf(CompatWood.BlockType.class, type ->
                    register((wood.name() + "_" + type.name()), createWood(wood, type), type.createBlockItem(wood, new Item.Properties()))
            )
    );

    public static final Id<Block> COMPAT_CHEST = register(
            "compat_chest",
            () -> new TFCChestBlock(
                    ExtendedProperties.of()
                            .strength(2.5F)
                            .flammableLikeLogs()
                            .blockEntity(TFCBlockEntities.CHEST)
                            .clientTicks(ChestBlockEntity::lidAnimateTick),
                    "compat_chest"  // texture identifier (folder or key for entity textures)
            ),
            block -> new ChestBlockItem(
                    block,
                    new Item.Properties(),
                    ResourceLocation.fromNamespaceAndPath(FirmaCompat.MODID, "textures/entity/chest_boat/compat.png")  // required third param; dummy/custom path
            )
    );

    public static final Id<Block> COMPAT_TRAPPED_CHEST = register(
            "compat_trapped_chest",
            () -> new TFCTrappedChestBlock(
                    ExtendedProperties.of()
                            .strength(2.5F)
                            .flammableLikeLogs()
                            .blockEntity(TFCBlockEntities.TRAPPED_CHEST)
                            .clientTicks(ChestBlockEntity::lidAnimateTick),
                    "compat_trapped_chest"  // texture identifier
            ),
            block -> new ChestBlockItem(
                    block,
                    new Item.Properties(),
                    ResourceLocation.fromNamespaceAndPath(FirmaCompat.MODID, "textures/entity/chest_boat/compat_trapped.png")  // or same as above
            )
    );

    //Rock
    public static final Map<CompatRock, Map<CompatRock.BlockType, Id<Block>>> ROCK_BLOCKS = Helpers.mapOf(CompatRock.class, rock ->
            Helpers.mapOf(CompatRock.BlockType.class, type -> switch (type){
                        case HARDENED_COBBLE ->     register(("hardened_" + rock.name() + "_cobble"), () -> type.create(rock), rock.createItemProperties());
                        case LOOSE_COBBLE ->     register(("loose_" + rock.name() + "_cobble"), () -> type.create(rock), rock.createItemProperties());
                        default ->     register((rock.name() + "_" + type.name()), () -> type.create(rock), rock.createItemProperties());
                    }
            )
    );

    //TODO - fix creating rock variants
    public static final Map<CompatRock, Map<CompatRock.BlockType, CompatDecorationBlockHolder>> ROCK_DECORATIONS = Helpers.mapOf(CompatRock.class, (rock) ->
            Helpers.mapOf(CompatRock.BlockType.class, CompatRock.BlockType::hasVariants, (type) ->
                    registerDecorations(rock.name() + "_" + type.name(), () -> type.createSlab(rock), () -> type.createStairs(rock), () -> type.createWall(rock), rock.createItemProperties())));

    //ore
    public static final Map<CompatRock, Map<CompatOre, Id<Block>>> ORES = Helpers.mapOf(CompatRock.class, rock ->
            Helpers.mapOf(CompatOre.class, ore -> (!ore.isGraded() && ore.hasBlock()), ore ->
                    register((rock.name() + "_" + ore.name() + "_ore"), () -> ore.create(rock))
            )
    );

    public static final Map<CompatRock, Map<CompatOre, Map<CompatOre.Grade, Id<Block>>>> GRADED_ORES = Helpers.mapOf(CompatRock.class, rock ->
            Helpers.mapOf(CompatOre.class, CompatOre::isGraded, ore ->
                    Helpers.mapOf(CompatOre.Grade.class, grade ->
                            register((grade.name()+ "_" + rock.name() + "_" + ore.name() + "_ore"), () -> ore.create(rock))
                    )
            )
    );

    //anvil
    public static final Id<Block> PRIMITIVE_ANVIL = register("primitive_anvil",
            () -> new RockAnvilBlock(ExtendedProperties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(2.0F, 10.0F).requiresCorrectToolForDrops().cloneItem(Blocks.STONE).blockEntity(TFCBlockEntities.ANVIL)));

    //gravel deposit
    public static final Id<Block> CASSITERITE_GRAVEL_DEPOSIT = register("cassiterite_gravel_deposit",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL)));
    public static final Id<Block> NATIVE_COPPER_GRAVEL_DEPOSIT = register("native_copper_gravel_deposit",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL)));
    public static final Id<Block> NATIVE_GOLD_GRAVEL_DEPOSIT = register("native_gold_gravel_deposit",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL)));
    public static final Id<Block> NATIVE_SILVER_GRAVEL_DEPOSIT = register("native_silver_gravel_deposit",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL)));

    //aqueduct
    public static final Map<CompatBricks, Id<AqueductBlock>> AQUEDUCTS =
            Helpers.mapOf(
                    CompatBricks.class,
                    brick -> register(
                            brick.getSingleName() + "_aqueduct",
                            () -> {
                                // Defer .get() to here – runs after registries are populated
                                Block vanilla = brick.vanillaEquivalent().get();
                                if (vanilla == null) {
                                    // Log and fallback to avoid crash during dev
                                    FirmaCompat.LOGGER.error("Vanilla block for brick {} is null – using default properties", brick);
                                    return new AqueductBlock(BlockBehaviour.Properties.of());
                                }
                                return new AqueductBlock(BlockBehaviour.Properties.ofFullCopy(vanilla));
                            }
                    )
            );

    //Natural
    public static final Id<Block> DRYING_MUD_BRICK = register("drying_mud_brick",
            () -> new DryingBricksBlock(ExtendedProperties.of(MapColor.DIRT).noCollission().noOcclusion().instabreak().sound(SoundType.STEM).randomTicks().blockEntity(TFCBlockEntities.TICK_COUNTER), ModItems.MUD_BRICK));
    public static final Id<Block> COMPAT_FARMLAND = register("compat_farmland",
            () -> new FarmlandBlock(ExtendedProperties.of(MapColor.DIRT).requiresCorrectToolForDrops().strength(1.3F).sound(SoundType.GRAVEL).randomTicks().isViewBlocking(TFCBlocks::always).isSuffocating(TFCBlocks::always).blockEntity(TFCBlockEntities.FARMLAND).serverTicks(FarmlandBlockEntity::serverTick), Suppliers.ofInstance(Blocks.DIRT)));
    public static final Id<Block> CLAY_GRASS_BLOCK = register("clay_grass_block",
            () -> new GrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK)));
    public static final Id<Block> CLAY_DIRT = register("clay_dirt",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)));
    public static final Id<Block> CLAY_PODZOL = register("clay_podzol",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.PODZOL)));
    public static final Id<Block> KAOLIN_CLAY_GRASS_BLOCK = register("kaolin_clay_grass_block",
            () -> new GrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK)));
    public static final Id<Block> KAOLIN_CLAY_DIRT = register("kaolin_clay_dirt",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)));
    public static final Id<Block> KAOLIN_CLAY_PODZOL = register("kaolin_clay_podzol",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.PODZOL)));

    //Fluid
    public static final Map<CompatMetal, Id<LiquidBlock>> METAL_FLUIDS = Helpers.mapOf(CompatMetal.class, metal ->
            registerNoItem("fluid/metal/" + metal.name(), () -> new LiquidBlock(ModFluids.METALS.get(metal).source().get(), BlockBehaviour.Properties.ofFullCopy(Blocks.LAVA).noLootTable()))
    );

    /*
    public static final Id<LiquidBlock> PURIFIED_WATER = registerNoItem("fluid/purified_water",
            () -> new LiquidBlock(ModFluids.PURIFIED_WATER.getFlowing(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
     */

    private static <T1 extends SlabBlock, T2 extends StairBlock, T3 extends WallBlock> CompatDecorationBlockHolder registerDecorations(String baseName, Supplier<T1> slab, Supplier<T2> stair, Supplier<T3> wall, Item.Properties properties) {
        return new CompatDecorationBlockHolder(register(baseName + "_slab", slab, (Function)((b) -> new BlockItem((Block) b, properties))), register(baseName + "_stairs", stair, (Function)((b) -> new BlockItem((Block) b, properties))), register(baseName + "_wall", wall, (Function)((b) -> new BlockItem((Block) b, properties))));
    }

    private static <T extends Block> Id<T> registerNoItem(String name, Supplier<T> blockSupplier) {
        return register(name, blockSupplier, (Function)null);
    }

    public static Supplier<Block> createWood(CompatWood RegionsWood, CompatWood.BlockType blockType)
    {
        return blockType.create(RegionsWood);
    }

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }

    protected static <T extends Block> Id<T> register(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, new Item.Properties()));
    }

    private static <T extends Block> Id<T> register(String name, Supplier<T> blockSupplier, Item.Properties blockItemProperties)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, blockItemProperties));
    }

    private static <T extends Block> Id<T> register(String name, Supplier<T> blockSupplier, @Nullable Function<T, ? extends BlockItem> blockItemFactory)
    {
        return new Id<>(RegistrationHelpers.registerBlock(ModBlocks.BLOCKS, ModItems.ITEMS, name, blockSupplier, blockItemFactory));
    }

    // TODO - need to properly show textures to hanging signs
    private static <B extends SignBlock> Map<CompatWood, Map<Metal, Id<B>>> registerHangingSigns(String variant, BiFunction<ExtendedProperties, WoodType, B> factory)
    {
        return Helpers.mapOf(CompatWood.class, wood ->
                Helpers.mapOf(Metal.class, Metal::allParts, metal -> register(
                        wood.getSerializedName() + "_" + metal.getSerializedName() + "_" + variant,
                        () -> factory.apply(ExtendedProperties.of(wood.woodColor()).sound(SoundType.WOOD).noCollission().strength(1F).flammableLikePlanks().blockEntity(TFCBlockEntities.HANGING_SIGN).ticks(SignBlockEntity::tick), wood.getVanillaWoodType()),
                        (Function<B, BlockItem>) null)
                )
        );
    }

    private static <T extends Block> Id<T> registerFluidNoItem(String name, Supplier<T> blockSupplier)
    {
        return registerFluid(name, blockSupplier, (Function<T, ? extends BlockItem>) null);
    }

    private static <T extends Block> Id<T> registerFluid(String name, Supplier<T> blockSupplier, @Nullable Function<T, ? extends BlockItem> blockItemFactory)
    {
        return new Id<>(RegistrationHelpers.registerBlock(ModBlocks.FLUID_BLOCKS, ModItems.ITEMS, name, blockSupplier, blockItemFactory));
    }

    public record Id<T extends Block>(DeferredHolder<Block, T> holder) implements RegistryHolder<Block, T>, ItemLike
    {
        @Override
        public Item asItem()
        {
            return get().asItem();
        }
    }
}
