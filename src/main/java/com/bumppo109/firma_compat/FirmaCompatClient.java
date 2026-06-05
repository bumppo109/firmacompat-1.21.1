package com.bumppo109.firma_compat;

import com.bumppo109.firma_compat.block.CompatRock;
import com.bumppo109.firma_compat.block.CompatWood;
import com.bumppo109.firma_compat.block.ModBlocks;
import com.bumppo109.firma_compat.entity.CompatTFCEntities;
import com.bumppo109.firma_compat.fluid.ModFluids;
import com.bumppo109.firma_compat.integration.dynamic_light.DynamicLighHandler;
import com.bumppo109.firma_compat.item.ModItems;
import com.bumppo109.firma_compat.integration.firmalife.CompatFLBlocks;
import com.bumppo109.firma_compat.data.ModDataComponents;
import net.dries007.tfc.client.ClientEventHandler;
import net.dries007.tfc.client.RenderHelpers;
import net.dries007.tfc.client.extensions.FluidRendererExtension;
import net.dries007.tfc.client.model.entity.*;
import net.dries007.tfc.client.render.blockentity.*;
import net.dries007.tfc.client.render.entity.*;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.component.TFCComponents;
import net.dries007.tfc.common.entities.aquatic.Fish;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.dries007.tfc.common.items.ChestBlockItem;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.minecraft.client.model.CodModel;
import net.minecraft.client.model.SquidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.bumppo109.firma_compat.FirmaCompat.isDynamicLightLoaded;
import static com.bumppo109.firma_compat.block.CompatWood.BlockType.*;
import static com.bumppo109.firma_compat.block.CompatWood.BlockType.BARREL;
import static com.bumppo109.firma_compat.block.CompatWood.BlockType.CLUTCH;
import static com.bumppo109.firma_compat.block.CompatWood.BlockType.ENCASED_AXLE;
import static com.bumppo109.firma_compat.block.CompatWood.BlockType.GEAR_BOX;
import static com.bumppo109.firma_compat.block.CompatWood.BlockType.SEWING_TABLE;
import static com.bumppo109.firma_compat.block.CompatWood.BlockType.SHELF;
import static com.bumppo109.firma_compat.block.ModBlocks.GRADED_ORES;
import static com.bumppo109.firma_compat.block.ModBlocks.ORES;

@Mod(value = FirmaCompat.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = FirmaCompat.MODID, value = Dist.CLIENT)
public class FirmaCompatClient {
    public FirmaCompatClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {

        if(isDynamicLightLoaded){
            DynamicLighHandler.initDynamicLight();
        }

        // Render Types
        final RenderType solid = RenderType.solid();
        final RenderType cutout = RenderType.cutout();
        final RenderType cutoutMipped = RenderType.cutoutMipped();
        final RenderType translucent = RenderType.translucent();
        final Predicate<RenderType> ghostBlock = rt -> rt == cutoutMipped || rt == Sheets.translucentCullBlockSheet();

        //final Predicate<RenderType> leafPredicate = layer -> Minecraft.useFancyGraphics() ? layer == cutoutMipped : layer == solid;
        ModBlocks.WOODS.values().forEach(map -> {
            Stream.of(TWIG, BARREL, SCRIBING_TABLE, SEWING_TABLE, SHELF, ENCASED_AXLE, CLUTCH, GEAR_BOX).forEach(type -> ItemBlockRenderTypes.setRenderLayer(map.get(type).get(), cutout));
        });

        registerLampLitProperty(ModBlocks.LANTERN.get());

        for(Metal metal : Metal.values()){
            if(metal.allParts()){
                registerLampLitProperty(ModBlocks.COMPAT_LANTERNS.get(metal).get());
            }
        }

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.LANTERN.get(), cutout);

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CASSITERITE_GRAVEL_DEPOSIT.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.NATIVE_COPPER_GRAVEL_DEPOSIT.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.NATIVE_SILVER_GRAVEL_DEPOSIT.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.NATIVE_GOLD_GRAVEL_DEPOSIT.get(), cutout);

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CLAY_GRASS_BLOCK.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CLAY_PODZOL.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.KAOLIN_CLAY_GRASS_BLOCK.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.KAOLIN_CLAY_PODZOL.get(), cutout);

        //TODO - add barrel horse texture
        event.enqueueWork(() -> {
            ModBlocks.WOODS.forEach((wood, map) -> {
                HorseChestLayer.registerChest(map.get(BARREL).get().asItem(), FirmaCompatHelpers.modIdentifier("textures/entity/chest/horse/" + wood.getSerializedName() + "_barrel.png"));
            });
            HorseChestLayer.registerChest(ModBlocks.COMPAT_CHEST.get().asItem(), FirmaCompatHelpers.modIdentifier("textures/entity/chest/horse/compat_chest"));
            HorseChestLayer.registerChest(ModBlocks.COMPAT_TRAPPED_CHEST.get().asItem(), FirmaCompatHelpers.modIdentifier("textures/entity/chest/horse/compat_chest"));
        });

        //TODO - not sure what this does
        for (CompatWood wood : CompatWood.VALUES)
        {
            Sheets.addWoodType(wood.getVanillaWoodType());
        }

        ModBlocks.WOODS.values().forEach(map -> registerSealedProperty(map.get(BARREL), TFCComponents.BARREL));

        //Ore
        // Non-graded ores (simple ore blocks)
        ORES.forEach((rock, oreMap) ->
                oreMap.forEach((ore, blockId) ->
                        ItemBlockRenderTypes.setRenderLayer(blockId.get(), RenderType.cutout())
                )
        );

        // Graded ores (rich/poor/normal variants)
        GRADED_ORES.forEach((rock, oreMap) ->
                oreMap.forEach((ore, gradeMap) ->
                        gradeMap.forEach((grade, blockId) ->
                                ItemBlockRenderTypes.setRenderLayer(blockId.get(), RenderType.cutout())
                        )
                )
        );

        event.enqueueWork(() -> {
            PlacedItemBlockEntityRenderer.MODELS.putAll(Map.of(
                    ModItems.SWEET_BERRIES_JAR.get(), translucent("block/sweet_berries_jar"),
                    ModItems.SWEET_BERRIES_JAR_UNSEALED.get(), translucent("block/sweet_berries_jar_unsealed"),
                    ModItems.GLOW_BERRIES_JAR.get(), translucent("block/glow_berries_jar"),
                    ModItems.GLOW_BERRIES_JAR_UNSEALED.get(), translucent("block/glow_berries_jar_unsealed")));
        });

        if(ModList.get().isLoaded("firmalife")){
            for(CompatWood wood : CompatWood.VALUES){
                ItemBlockRenderTypes.setRenderLayer(CompatFLBlocks.FOOD_SHELVES.get(wood).get(), cutout);
                ItemBlockRenderTypes.setRenderLayer(CompatFLBlocks.HANGERS.get(wood).get(), cutout);
                ItemBlockRenderTypes.setRenderLayer(CompatFLBlocks.JARBNETS.get(wood).get(), cutout);
                ItemBlockRenderTypes.setRenderLayer(CompatFLBlocks.STOMPING_BARRELS.get(wood).get(), cutout);
                ItemBlockRenderTypes.setRenderLayer(CompatFLBlocks.BARREL_PRESSES.get(wood).get(), cutout);
                ItemBlockRenderTypes.setRenderLayer(CompatFLBlocks.WINE_SHELVES.get(wood).get(), cutout);
            }
            for(CompatRock rock : CompatRock.VALUES){
                ItemBlockRenderTypes.setRenderLayer(CompatFLBlocks.CHROMITE_ORES.get(rock).get(Ore.Grade.POOR).get(), cutout);
                ItemBlockRenderTypes.setRenderLayer(CompatFLBlocks.CHROMITE_ORES.get(rock).get(Ore.Grade.NORMAL).get(), cutout);
                ItemBlockRenderTypes.setRenderLayer(CompatFLBlocks.CHROMITE_ORES.get(rock).get(Ore.Grade.RICH).get(), cutout);
            }
        }
    }

    private static PlacedItemBlockEntityRenderer.Provider solid(String model) {
        return new PlacedItemBlockEntityRenderer.Provider(ModelResourceLocation.standalone(FirmaCompatHelpers.modIdentifier(model)), RenderType.solid());
    }

    private static PlacedItemBlockEntityRenderer.Provider translucent(String model) {
        return new PlacedItemBlockEntityRenderer.Provider(ModelResourceLocation.standalone(FirmaCompatHelpers.modIdentifier(model)), RenderType.translucent());
    }

    private static PlacedItemBlockEntityRenderer.Provider translucentTFC(String model) {
        return new PlacedItemBlockEntityRenderer.Provider(ModelResourceLocation.standalone(Helpers.identifier(model)), RenderType.translucent());
    }

    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // Direct renderers (no builder) - these stay as ::new
        event.registerEntityRenderer(CompatTFCEntities.FRESHWATER_FISH.get(Fish.LARGEMOUTH_BASS).get(),
                ctx -> new SimpleMobRenderer.Builder<>(
                        ctx,
                        part -> new CodModel(part),
                        "largemouth_bass"
                ).flops().build());

        event.registerEntityRenderer(CompatTFCEntities.FRESHWATER_FISH.get(Fish.SMALLMOUTH_BASS).get(),
                ctx -> new SimpleMobRenderer.Builder<>(
                        ctx,
                        part -> new CodModel(part),
                        "smallmouth_bass"
                ).flops().build());

        event.registerEntityRenderer(CompatTFCEntities.FRESHWATER_FISH.get(Fish.LAKE_TROUT).get(),
                ctx -> new SimpleMobRenderer.Builder<>(
                        ctx,
                        part -> new CodModel(part),
                        "lake_trout"
                ).flops().build());

        event.registerEntityRenderer(CompatTFCEntities.FRESHWATER_FISH.get(Fish.RAINBOW_TROUT).get(),
                ctx -> new SimpleMobRenderer.Builder<>(
                        ctx,
                        part -> new CodModel(part),
                        "rainbow_trout"
                ).flops().build());

        event.registerEntityRenderer(CompatTFCEntities.FRESHWATER_FISH.get(Fish.CRAPPIE).get(),
                ctx -> new SimpleMobRenderer.Builder<>(
                        ctx,
                        part -> new CodModel(part),
                        "crappie"
                ).flops().build());

        event.registerEntityRenderer(CompatTFCEntities.FRESHWATER_FISH.get(Fish.BLUEGILL).get(),
                ctx -> new SimpleMobRenderer.Builder<>(
                        ctx,
                        part -> new BluegillModel(part),
                        "bluegill"
                ).flops().build());

        event.registerEntityRenderer(CompatTFCEntities.TROPICAL_FISH.get(), TropicalFishRenderer::new);
        event.registerEntityRenderer(CompatTFCEntities.PUFFERFISH.get(), PufferfishRenderer::new);
        event.registerEntityRenderer(CompatTFCEntities.COD.get(), CodRenderer::new);
        event.registerEntityRenderer((CompatTFCEntities.FRESHWATER_FISH.get(Fish.SALMON)).get(), SalmonRenderer::new);

        event.registerEntityRenderer(CompatTFCEntities.JELLYFISH.get(), JellyfishRenderer::new);
        event.registerEntityRenderer(CompatTFCEntities.DOLPHIN.get(), DolphinRenderer::new);
        event.registerEntityRenderer(CompatTFCEntities.PENGUIN.get(), PenguinRenderer::new);
        event.registerEntityRenderer(CompatTFCEntities.FROG.get(), FrogRenderer::new);
        event.registerEntityRenderer(CompatTFCEntities.RABBIT.get(), RabbitRenderer::new);
        event.registerEntityRenderer(CompatTFCEntities.FOX.get(), FoxRenderer::new);
        event.registerEntityRenderer(CompatTFCEntities.PANDA.get(), PandaRenderer::new);
        event.registerEntityRenderer(CompatTFCEntities.OCELOT.get(), OcelotRenderer::new);
        event.registerEntityRenderer(CompatTFCEntities.SQUID.get(),
                ctx -> new TFCSquidRenderer(ctx, new SquidModel(RenderHelpers.bakeSimple(ctx, "squid"))));
        event.registerEntityRenderer(CompatTFCEntities.OCTOPOTEUTHIS.get(),
                ctx -> new OctopoteuthisRenderer(ctx, new SquidModel(RenderHelpers.bakeSimple(ctx, "glow_squid"))));
        event.registerEntityRenderer(CompatTFCEntities.PIG.get(),
                ctx -> new AnimalRenderer(ctx, new TFCPigModel(RenderHelpers.bakeSimple(ctx, "pig")), "pig"));
        event.registerEntityRenderer(CompatTFCEntities.COW.get(),
                ctx -> new AnimalRenderer(ctx, new TFCCowModel(RenderHelpers.bakeSimple(ctx, "cow")), "cow"));
        event.registerEntityRenderer(CompatTFCEntities.GOAT.get(),
                ctx -> new AnimalRenderer(ctx, new TFCGoatModel(RenderHelpers.bakeSimple(ctx, "goat")), "goat"));
        event.registerEntityRenderer(CompatTFCEntities.YAK.get(),
                ctx -> new AnimalRenderer(ctx, new YakModel(RenderHelpers.bakeSimple(ctx, "yak")), "yak"));
        event.registerEntityRenderer(CompatTFCEntities.ALPACA.get(),
                ctx -> new AnimalRenderer(ctx, new AlpacaModel(RenderHelpers.bakeSimple(ctx, "alpaca")), "alpaca"));
        event.registerEntityRenderer(CompatTFCEntities.SHEEP.get(),
                ctx -> new AnimalRenderer(ctx, new TFCSheepModel(RenderHelpers.bakeSimple(ctx, "sheep")), "sheep"));
        event.registerEntityRenderer(CompatTFCEntities.MUSK_OX.get(),
                ctx -> new AnimalRenderer(ctx, new MuskOxModel(RenderHelpers.bakeSimple(ctx, "musk_ox")), "musk_ox"));
        event.registerEntityRenderer(CompatTFCEntities.CHICKEN.get(),
                ctx -> new OviparousRenderer(ctx, new TFCChickenModel(RenderHelpers.bakeSimple(ctx, "chicken")), "chicken", "rooster", "chick"));
        event.registerEntityRenderer(CompatTFCEntities.DUCK.get(),
                ctx -> new OviparousRenderer(ctx, new DuckModel(RenderHelpers.bakeSimple(ctx, "duck")), "duck", "drake", "duckling"));
        event.registerEntityRenderer(CompatTFCEntities.QUAIL.get(),
                ctx -> new OviparousRenderer(ctx, new QuailModel(RenderHelpers.bakeSimple(ctx, "quail")), "quail", "quail_male", "quail_chick"));
        event.registerEntityRenderer(CompatTFCEntities.MULE.get(),
                ctx -> new TFCChestedHorseRenderer(ctx, 0.92F, RenderHelpers.layerId("mule"), "mule"));
        event.registerEntityRenderer(CompatTFCEntities.DONKEY.get(),
                ctx -> new TFCChestedHorseRenderer(ctx, 0.87F, RenderHelpers.layerId("donkey"), "donkey"));

        // All builder-based registrations - now using explicit lambdas
        event.registerEntityRenderer(CompatTFCEntities.LOBSTER.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new LobsterModel(part), "lobster").build());

        event.registerEntityRenderer(CompatTFCEntities.CRAYFISH.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new LobsterModel(part), "crayfish").build());

        event.registerEntityRenderer(CompatTFCEntities.ISOPOD.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new IsopodModel(part), "isopod").build());

        event.registerEntityRenderer(CompatTFCEntities.HORSESHOE_CRAB.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new HorseshoeCrabModel(part), "horseshoe_crab").build());

        event.registerEntityRenderer(CompatTFCEntities.ORCA.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new OrcaModel(part), "orca").build());

        event.registerEntityRenderer(CompatTFCEntities.MANATEE.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new ManateeModel(part), "manatee").build());

        event.registerEntityRenderer(CompatTFCEntities.TURTLE.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new TFCTurtleModel(part), "turtle").build());

        event.registerEntityRenderer(CompatTFCEntities.LEOPARD_SEAL.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new LeopardSealModel(part), "seal").build());

        event.registerEntityRenderer(CompatTFCEntities.POLAR_BEAR.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new PolarBearModel(part), "polar_bear")
                        .shadow(0.9F).build());

        event.registerEntityRenderer(CompatTFCEntities.GRIZZLY_BEAR.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new GrizzlyBearModel(part), "grizzly_bear")
                        .shadow(0.9F).scale(1.1F).build());

        event.registerEntityRenderer(CompatTFCEntities.BLACK_BEAR.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new BlackBearModel(part), "black_bear")
                        .shadow(0.9F).scale(0.9F).build());

        event.registerEntityRenderer(CompatTFCEntities.COUGAR.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new CougarModel(part), "cougar")
                        .shadow(0.8F).build());

        event.registerEntityRenderer(CompatTFCEntities.PANTHER.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new CougarModel(part), "panther")
                        .shadow(0.8F).build());

        event.registerEntityRenderer(CompatTFCEntities.LION.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new LionModel(part), "lion")
                        .shadow(0.8F).build());

        event.registerEntityRenderer(CompatTFCEntities.SABERTOOTH.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new SabertoothModel(part), "sabertooth")
                        .shadow(0.8F).build());

        event.registerEntityRenderer(CompatTFCEntities.TIGER.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new TigerModel(part), "tiger")
                        .shadow(0.8F).build());

        event.registerEntityRenderer(CompatTFCEntities.CROCODILE.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new CrocodileModel(part), "crocodile")
                        .shadow(0.8F).build());

        event.registerEntityRenderer(CompatTFCEntities.WOLF.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new TFCWolfModel(part), "wolf")
                        .shadow(0.5F).scale(1.1F).build());

        event.registerEntityRenderer(CompatTFCEntities.HYENA.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new HyenaModel(part), "hyena")
                        .shadow(0.5F).scale(1.1F).build());

        event.registerEntityRenderer(CompatTFCEntities.DIREWOLF.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new DirewolfModel(part), "direwolf")
                        .shadow(0.9F).build());

        event.registerEntityRenderer(CompatTFCEntities.BONGO.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new BongoModel(part), "bongo")
                        .shadow(0.6F).build());

        event.registerEntityRenderer(CompatTFCEntities.CARIBOU.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new CaribouModel(part), "caribou")
                        .shadow(0.6F).build());

        event.registerEntityRenderer(CompatTFCEntities.DEER.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new DeerModel(part), "deer")
                        .shadow(0.6F).hasBabyTexture().build());

        event.registerEntityRenderer(CompatTFCEntities.GAZELLE.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new GazelleModel(part), "gazelle")
                        .shadow(0.6F).build());

        event.registerEntityRenderer(CompatTFCEntities.GROUSE.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new GrouseModel(part), "grouse")
                        .shadow(0.5F)
                        .texture(e -> RenderHelpers.getGenderedTexture(e, "grouse"))
                        .build());

        event.registerEntityRenderer(CompatTFCEntities.PHEASANT.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new PheasantModel(part), "pheasant")
                        .shadow(0.5F)
                        .texture(e -> RenderHelpers.getGenderedTexture(e, "pheasant"))
                        .build());

        event.registerEntityRenderer(CompatTFCEntities.TURKEY.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new TurkeyModel(part), "turkey")
                        .shadow(0.5F)
                        .texture(e -> RenderHelpers.getGenderedTexture(e, "turkey"))
                        .build());

        event.registerEntityRenderer(CompatTFCEntities.PEAFOWL.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new PeafowlModel(part), "peafowl")
                        .shadow(0.5F)
                        .texture(e -> RenderHelpers.getGenderedTexture(e, "peafowl"))
                        .build());

        event.registerEntityRenderer(CompatTFCEntities.BOAR.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new BoarModel(part), "boar").build());

        event.registerEntityRenderer(CompatTFCEntities.WILDEBEEST.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new WildebeestModel(part), "wildebeest").build());

        event.registerEntityRenderer(CompatTFCEntities.MOOSE.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new MooseModel(part), "moose")
                        .shadow(1.0F).scale(0.8F).build());

        event.registerEntityRenderer(CompatTFCEntities.BISON.get(),
                ctx -> new SimpleMobRenderer.Builder<>(ctx, part -> new BisonModel(part), "bison").build());

        // Add any others you might have missed (rat, jerboa, lemming, mongoose, cat, dog)
        // Example:
        // event.registerEntityRenderer(CompatTFCEntities.RAT.get(), RatRenderer::new);
        // event.registerEntityRenderer(CompatTFCEntities.JERBOA.get(), JerboaRenderer::new);
        // etc.
    }

    public static void registerExtensions(RegisterClientExtensionsEvent event)
    {
        ModFluids.METALS.forEach((metal, holder) -> event.registerFluidType(
                new FluidRendererExtension(TFCFluids.ALPHA_MASK | metal.getColor(), ClientEventHandler.MOLTEN_STILL, ClientEventHandler.MOLTEN_FLOW, null, null),
                holder.getType()
        ));
        // Chest item renderers
        registerChestItemRenderer(event, ModBlocks.COMPAT_CHEST);
        registerChestItemRenderer(event, ModBlocks.COMPAT_TRAPPED_CHEST);

    }

    private static void registerLampLitProperty(ItemLike item) {
        ItemProperties.register(item.asItem(), ResourceLocation.fromNamespaceAndPath(FirmaCompat.MODID, "lit"),
                (stack, level, entity, seed) ->
                        stack.getOrDefault(ModDataComponents.LIT, false) ? 1.0F : 0.0F
        );
    }

    private static final ResourceLocation SEALED = Helpers.identifier("sealed");

    private static void registerSealedProperty(ItemLike item, Supplier<? extends DataComponentType<?>> type)
    {
        ItemProperties.register(item.asItem(), SEALED, (stack, level, entity, unused) -> stack.has(type) ? 1.0f : 0f);
    }

    private static void registerChestItemRenderer(
            RegisterClientExtensionsEvent event,
            Supplier<? extends Block> blockSupplier
    ) {
        if (blockSupplier == null) return;

        Block block = blockSupplier.get();
        Item item = block.asItem();

        // TFC expects the item to be ChestBlockItem for type safety, but we can cast or check
        if (!(item instanceof ChestBlockItem chestItem)) {
            FirmaCompat.LOGGER.warn("Attempted to register chest item renderer for non-ChestBlockItem: {}", item);
            return;
        }

        event.registerItem(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                // Use TFC's exact renderer – it creates a dummy BE from your block and dispatches render
                return new ChestItemRenderer(chestItem);
            }
        }, item);
    }

    // Static flag to ensure reload happens only once per game session
    private static boolean hasTriggeredInitialReload = false;

    /*
    @SubscribeEvent
    public static void onClientTickPost(ClientTickEvent.Post event) {
        if (!hasTriggeredInitialReload) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.level != null) {  // Only trigger when in a world
                hasTriggeredInitialReload = true;
                mc.execute(() -> {
                    mc.reloadResourcePacks();
                    // Optional: log to confirm it fired
                    FirmaCompat.LOGGER.info("FirmaCompat: Initial resource reload triggered after world load");
                });
            }
        }
    }

     */
}
