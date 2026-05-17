package com.bumppo109.firma_compat.entity;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

import com.bumppo109.firma_compat.item.ModItems;
import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.common.TFCTags.Items;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.entities.ai.predator.PackPredator;
import net.dries007.tfc.common.entities.ai.prey.TFCOcelot;
import net.dries007.tfc.common.entities.aquatic.AmphibiousAnimal;
import net.dries007.tfc.common.entities.aquatic.AquaticCritter;
import net.dries007.tfc.common.entities.aquatic.Fish;
import net.dries007.tfc.common.entities.aquatic.FreshwaterFish;
import net.dries007.tfc.common.entities.aquatic.Jellyfish;
import net.dries007.tfc.common.entities.aquatic.LeopardSeal;
import net.dries007.tfc.common.entities.aquatic.Manatee;
import net.dries007.tfc.common.entities.aquatic.Octopoteuthis;
import net.dries007.tfc.common.entities.aquatic.Penguin;
import net.dries007.tfc.common.entities.aquatic.TFCCod;
import net.dries007.tfc.common.entities.aquatic.TFCDolphin;
import net.dries007.tfc.common.entities.aquatic.TFCPufferfish;
import net.dries007.tfc.common.entities.aquatic.TFCSquid;
import net.dries007.tfc.common.entities.aquatic.TFCTropicalFish;
import net.dries007.tfc.common.entities.aquatic.TFCTurtle;
import net.dries007.tfc.common.entities.livestock.DairyAnimal;
import net.dries007.tfc.common.entities.livestock.Mammal;
import net.dries007.tfc.common.entities.livestock.OviparousAnimal;
import net.dries007.tfc.common.entities.livestock.TFCAnimal;
import net.dries007.tfc.common.entities.livestock.WoolyAnimal;
import net.dries007.tfc.common.entities.livestock.horse.TFCDonkey;
import net.dries007.tfc.common.entities.livestock.horse.TFCHorse;
import net.dries007.tfc.common.entities.livestock.horse.TFCMule;
import net.dries007.tfc.common.entities.livestock.pet.Dog;
import net.dries007.tfc.common.entities.livestock.pet.TFCCat;
import net.dries007.tfc.common.entities.misc.GlowArrow;
import net.dries007.tfc.common.entities.misc.HoldingMinecart;
import net.dries007.tfc.common.entities.misc.Seat;
import net.dries007.tfc.common.entities.misc.TFCBoat;
import net.dries007.tfc.common.entities.misc.TFCChestBoat;
import net.dries007.tfc.common.entities.misc.TFCFallingBlockEntity;
import net.dries007.tfc.common.entities.misc.TFCFishingHook;
import net.dries007.tfc.common.entities.misc.TFCMinecartChest;
import net.dries007.tfc.common.entities.misc.ThrownJavelin;
import net.dries007.tfc.common.entities.predator.AmphibiousPredator;
import net.dries007.tfc.common.entities.predator.FelinePredator;
import net.dries007.tfc.common.entities.predator.Predator;
import net.dries007.tfc.common.entities.prey.Pest;
import net.dries007.tfc.common.entities.prey.Prey;
import net.dries007.tfc.common.entities.prey.RammingPrey;
import net.dries007.tfc.common.entities.prey.TFCFox;
import net.dries007.tfc.common.entities.prey.TFCFrog;
import net.dries007.tfc.common.entities.prey.TFCPanda;
import net.dries007.tfc.common.entities.prey.TFCRabbit;
import net.dries007.tfc.common.entities.prey.WingedPrey;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistryHolder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.GlowSquid;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CompatTFCEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES;
    public static final Map<Fish, CompatTFCEntities.Id<FreshwaterFish>> FRESHWATER_FISH;
    public static final CompatTFCEntities.Id<TFCCod> COD;
    public static final CompatTFCEntities.Id<TFCTropicalFish> TROPICAL_FISH;
    public static final CompatTFCEntities.Id<TFCPufferfish> PUFFERFISH;
    public static final CompatTFCEntities.Id<Jellyfish> JELLYFISH;
    public static final CompatTFCEntities.Id<AquaticCritter> ISOPOD;
    public static final CompatTFCEntities.Id<AquaticCritter> LOBSTER;
    public static final CompatTFCEntities.Id<AquaticCritter> CRAYFISH;
    public static final CompatTFCEntities.Id<AquaticCritter> HORSESHOE_CRAB;
    public static final CompatTFCEntities.Id<TFCDolphin> DOLPHIN;
    public static final CompatTFCEntities.Id<TFCDolphin> ORCA;
    public static final CompatTFCEntities.Id<Manatee> MANATEE;
    public static final CompatTFCEntities.Id<TFCSquid> SQUID;
    public static final CompatTFCEntities.Id<Octopoteuthis> OCTOPOTEUTHIS;
    public static final CompatTFCEntities.Id<TFCTurtle> TURTLE;
    public static final CompatTFCEntities.Id<Penguin> PENGUIN;
    public static final CompatTFCEntities.Id<LeopardSeal> LEOPARD_SEAL;
    public static final CompatTFCEntities.Id<TFCFrog> FROG;
    public static final CompatTFCEntities.Id<Predator> POLAR_BEAR;
    public static final CompatTFCEntities.Id<Predator> GRIZZLY_BEAR;
    public static final CompatTFCEntities.Id<Predator> BLACK_BEAR;
    public static final CompatTFCEntities.Id<FelinePredator> COUGAR;
    public static final CompatTFCEntities.Id<FelinePredator> PANTHER;
    public static final CompatTFCEntities.Id<FelinePredator> LION;
    public static final CompatTFCEntities.Id<FelinePredator> SABERTOOTH;
    public static final CompatTFCEntities.Id<FelinePredator> TIGER;
    public static final CompatTFCEntities.Id<AmphibiousPredator> CROCODILE;
    public static final CompatTFCEntities.Id<PackPredator> WOLF;
    public static final CompatTFCEntities.Id<PackPredator> HYENA;
    public static final CompatTFCEntities.Id<PackPredator> DIREWOLF;
    public static final CompatTFCEntities.Id<Mammal> PIG;
    public static final CompatTFCEntities.Id<DairyAnimal> COW;
    public static final CompatTFCEntities.Id<DairyAnimal> GOAT;
    public static final CompatTFCEntities.Id<DairyAnimal> YAK;
    public static final CompatTFCEntities.Id<WoolyAnimal> ALPACA;
    public static final CompatTFCEntities.Id<WoolyAnimal> SHEEP;
    public static final CompatTFCEntities.Id<WoolyAnimal> MUSK_OX;
    public static final CompatTFCEntities.Id<OviparousAnimal> CHICKEN;
    public static final CompatTFCEntities.Id<OviparousAnimal> DUCK;
    public static final CompatTFCEntities.Id<OviparousAnimal> QUAIL;
    public static final CompatTFCEntities.Id<TFCRabbit> RABBIT;
    public static final CompatTFCEntities.Id<TFCFox> FOX;
    public static final CompatTFCEntities.Id<TFCPanda> PANDA;
    public static final CompatTFCEntities.Id<TFCOcelot> OCELOT;
    public static final CompatTFCEntities.Id<Prey> DEER;
    public static final CompatTFCEntities.Id<Prey> CARIBOU;
    public static final CompatTFCEntities.Id<Prey> BONGO;
    public static final CompatTFCEntities.Id<Prey> GAZELLE;
    public static final CompatTFCEntities.Id<WingedPrey> GROUSE;
    public static final CompatTFCEntities.Id<WingedPrey> PHEASANT;
    public static final CompatTFCEntities.Id<WingedPrey> TURKEY;
    public static final CompatTFCEntities.Id<WingedPrey> PEAFOWL;
    public static final CompatTFCEntities.Id<RammingPrey> BOAR;
    public static final CompatTFCEntities.Id<RammingPrey> MOOSE;
    public static final CompatTFCEntities.Id<RammingPrey> WILDEBEEST;
    public static final CompatTFCEntities.Id<RammingPrey> BISON;
    public static final CompatTFCEntities.Id<Pest> RAT;
    public static final CompatTFCEntities.Id<Pest> LEMMING;
    public static final CompatTFCEntities.Id<Pest> MONGOOSE;
    public static final CompatTFCEntities.Id<Pest> JERBOA;
    public static final CompatTFCEntities.Id<TFCDonkey> DONKEY;
    public static final CompatTFCEntities.Id<TFCMule> MULE;
    public static final CompatTFCEntities.Id<TFCHorse> HORSE;
    public static final CompatTFCEntities.Id<TFCCat> CAT;
    public static final CompatTFCEntities.Id<Dog> DOG;

    public static <E extends Entity> CompatTFCEntities.Id<E> register(String name, EntityType.Builder<E> builder) {
        return register(name, builder, true);
    }



    public static <E extends Entity> CompatTFCEntities.Id<E> register(String name, EntityType.Builder<E> builder, boolean serialize) {
        String id = name.toLowerCase(Locale.ROOT);
        return new CompatTFCEntities.Id<E>(ENTITIES.register(id, () -> {
            if (!serialize) {
                builder.noSave();
            }

            return builder.build("firma_compat:" + id);
        }));
    }

    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        FRESHWATER_FISH.values().forEach((reg) -> event.put((EntityType)reg.get(), AbstractFish.createAttributes().build()));
        event.put((EntityType)COD.get(), AbstractFish.createAttributes().build());
        event.put((EntityType)TROPICAL_FISH.get(), AbstractFish.createAttributes().build());
        event.put((EntityType)PUFFERFISH.get(), AbstractFish.createAttributes().build());
        event.put((EntityType)JELLYFISH.get(), AbstractFish.createAttributes().build());
        event.put((EntityType)LOBSTER.get(), AbstractFish.createAttributes().build());
        event.put((EntityType)CRAYFISH.get(), AbstractFish.createAttributes().build());
        event.put((EntityType)ISOPOD.get(), AbstractFish.createAttributes().build());
        event.put((EntityType)HORSESHOE_CRAB.get(), AbstractFish.createAttributes().build());
        event.put((EntityType)DOLPHIN.get(), Dolphin.createAttributes().build());
        event.put((EntityType)ORCA.get(), Dolphin.createAttributes().build());
        event.put((EntityType)MANATEE.get(), Manatee.createAttributes().build());
        event.put((EntityType)TURTLE.get(), AmphibiousAnimal.createAttributes().build());
        event.put((EntityType)PENGUIN.get(), Penguin.createAttributes().build());
        event.put((EntityType)LEOPARD_SEAL.get(), LeopardSeal.createAttributes().build());
        event.put((EntityType)FROG.get(), TFCFrog.createAttributes().build());
        event.put((EntityType)POLAR_BEAR.get(), Predator.createBearAttributes().build());
        event.put((EntityType)GRIZZLY_BEAR.get(), Predator.createBearAttributes().build());
        event.put((EntityType)BLACK_BEAR.get(), Predator.createBearAttributes().build());
        event.put((EntityType)COUGAR.get(), FelinePredator.createAttributes().build());
        event.put((EntityType)PANTHER.get(), FelinePredator.createAttributes().build());
        event.put((EntityType)LION.get(), FelinePredator.createAttributes().build());
        event.put((EntityType)SABERTOOTH.get(), FelinePredator.createAttributes().build());
        event.put((EntityType)TIGER.get(), FelinePredator.createAttributes().build());
        event.put((EntityType)CROCODILE.get(), AmphibiousPredator.createAttributes().build());
        event.put((EntityType)WOLF.get(), Predator.createAttributes().build());
        event.put((EntityType)HYENA.get(), Predator.createAttributes().build());
        event.put((EntityType)DIREWOLF.get(), Predator.createAttributes().build());
        event.put((EntityType)SQUID.get(), Squid.createAttributes().build());
        event.put((EntityType)OCTOPOTEUTHIS.get(), GlowSquid.createAttributes().build());
        event.put((EntityType)PIG.get(), Pig.createAttributes().add(Attributes.STEP_HEIGHT, (double)1.0F).build());
        event.put((EntityType)COW.get(), Cow.createAttributes().add(Attributes.STEP_HEIGHT, (double)1.0F).build());
        event.put((EntityType)GOAT.get(), Pig.createAttributes().add(Attributes.STEP_HEIGHT, (double)1.0F).build());
        event.put((EntityType)YAK.get(), Cow.createAttributes().add(Attributes.STEP_HEIGHT, (double)1.0F).build());
        event.put((EntityType)ALPACA.get(), Cow.createAttributes().add(Attributes.STEP_HEIGHT, (double)1.0F).build());
        event.put((EntityType)SHEEP.get(), Cow.createAttributes().add(Attributes.STEP_HEIGHT, (double)1.0F).build());
        event.put((EntityType)MUSK_OX.get(), Cow.createAttributes().add(Attributes.STEP_HEIGHT, (double)1.0F).build());
        event.put((EntityType)CHICKEN.get(), OviparousAnimal.createAttributes().build());
        event.put((EntityType)DUCK.get(), OviparousAnimal.createAttributes().build());
        event.put((EntityType)QUAIL.get(), OviparousAnimal.createAttributes().build());
        event.put((EntityType)RABBIT.get(), TFCRabbit.createAttributes().build());
        event.put((EntityType)FOX.get(), TFCFox.createAttributes().build());
        event.put((EntityType)DEER.get(), Prey.createAttributes().build());
        event.put((EntityType)BONGO.get(), Prey.createAttributes().build());
        event.put((EntityType)GAZELLE.get(), Prey.createAttributes().build());
        event.put((EntityType)CARIBOU.get(), Prey.createAttributes().build());
        event.put((EntityType)BOAR.get(), RammingPrey.createAttributes().build());
        event.put((EntityType)WILDEBEEST.get(), RammingPrey.createMediumAttributes().build());
        event.put((EntityType)MOOSE.get(), RammingPrey.createLargeAttributes().build());
        event.put((EntityType)BISON.get(), RammingPrey.createLargeAttributes().build());
        event.put((EntityType)GROUSE.get(), OviparousAnimal.createAttributes().build());
        event.put((EntityType)PHEASANT.get(), OviparousAnimal.createAttributes().build());
        event.put((EntityType)TURKEY.get(), OviparousAnimal.createAttributes().build());
        event.put((EntityType)PEAFOWL.get(), OviparousAnimal.createAttributes().build());
        event.put((EntityType)RAT.get(), Pest.createAttributes().build());
        event.put((EntityType)LEMMING.get(), Pest.createAttributes().build());
        event.put((EntityType)MONGOOSE.get(), Pest.createAttributes().build());
        event.put((EntityType)JERBOA.get(), Pest.createAttributes().build());
        event.put((EntityType)MULE.get(), AbstractChestedHorse.createBaseChestedHorseAttributes().build());
        event.put((EntityType)DONKEY.get(), AbstractChestedHorse.createBaseChestedHorseAttributes().build());
        event.put((EntityType)HORSE.get(), AbstractHorse.createBaseHorseAttributes().build());
        event.put((EntityType)CAT.get(), TFCCat.createAttributes().build());
        event.put((EntityType)DOG.get(), Dog.createAttributes().build());
        event.put((EntityType)PANDA.get(), TFCPanda.createAttributes().add(Attributes.STEP_HEIGHT, (double)1.0F).build());
        event.put((EntityType)OCELOT.get(), TFCOcelot.createAttributes().build());
    }

    public static Mammal makePig(EntityType<? extends Mammal> animal, Level level) {
        return new Mammal(animal, level, TFCSounds.PIG, TFCConfig.SERVER.pigConfig) {
            public TagKey<Item> getFoodTag() {
                return Items.PIG_FOOD;
            }
        };
    }

    public static DairyAnimal makeCow(EntityType<? extends DairyAnimal> animal, Level level) {
        return new DairyAnimal(animal, level, TFCSounds.COW, TFCConfig.SERVER.cowConfig) {
            public TagKey<Item> getFoodTag() {
                return Items.COW_FOOD;
            }
        };
    }

    public static DairyAnimal makeGoat(EntityType<? extends DairyAnimal> animal, Level level) {
        return new DairyAnimal(animal, level, TFCSounds.GOAT, TFCConfig.SERVER.goatConfig) {
            public TagKey<Item> getFoodTag() {
                return Items.GOAT_FOOD;
            }
        };
    }

    public static DairyAnimal makeYak(EntityType<? extends DairyAnimal> animal, Level level) {
        return new DairyAnimal(animal, level, TFCSounds.YAK, TFCConfig.SERVER.yakConfig) {
            public TagKey<Item> getFoodTag() {
                return Items.YAK_FOOD;
            }
        };
    }

    public static WoolyAnimal makeAlpaca(EntityType<? extends WoolyAnimal> animal, Level level) {
        return new WoolyAnimal(animal, level, TFCSounds.ALPACA, TFCConfig.SERVER.alpacaConfig) {
            public TagKey<Item> getFoodTag() {
                return Items.ALPACA_FOOD;
            }
        };
    }

    public static WoolyAnimal makeSheep(EntityType<? extends WoolyAnimal> animal, Level level) {
        return new WoolyAnimal(animal, level, TFCSounds.SHEEP, TFCConfig.SERVER.sheepConfig) {
            public TagKey<Item> getFoodTag() {
                return Items.SHEEP_FOOD;
            }
        };
    }

    public static WoolyAnimal makeMuskOx(EntityType<? extends WoolyAnimal> animal, Level level) {
        return new WoolyAnimal(animal, level, TFCSounds.MUSK_OX, TFCConfig.SERVER.muskOxConfig) {
            public TagKey<Item> getFoodTag() {
                return Items.MUSK_OX_FOOD;
            }
        };
    }

    public static OviparousAnimal makeChicken(EntityType<? extends OviparousAnimal> animal, Level level) {
        return new OviparousAnimal(animal, level, TFCSounds.CHICKEN, TFCConfig.SERVER.chickenConfig, true) {
            public TagKey<Item> getFoodTag() {
                return Items.CHICKEN_FOOD;
            }
        };
    }

    public static OviparousAnimal makeDuck(EntityType<? extends OviparousAnimal> animal, Level level) {
        return new OviparousAnimal(animal, level, TFCSounds.DUCK, TFCConfig.SERVER.duckConfig, false) {
            public TagKey<Item> getFoodTag() {
                return Items.DUCK_FOOD;
            }
        };
    }

    public static OviparousAnimal makeQuail(EntityType<? extends OviparousAnimal> animal, Level level) {
        return new OviparousAnimal(animal, level, TFCSounds.QUAIL, TFCConfig.SERVER.quailConfig, false) {
            public TagKey<Item> getFoodTag() {
                return Items.QUAIL_FOOD;
            }
        };
    }

    public static TFCRabbit makeRabbit(EntityType<? extends Rabbit> animal, Level level) {
        return new TFCRabbit(animal, level, TFCConfig.SERVER.rabbitConfig);
    }

    public static RammingPrey makeBoar(EntityType<? extends RammingPrey> animal, Level level) {
        return new RammingPrey(animal, level, TFCSounds.BOAR, 0.1);
    }

    public static RammingPrey makeWildebeest(EntityType<? extends RammingPrey> animal, Level level) {
        return new RammingPrey(animal, level, TFCSounds.WILDEBEEST, 0.1);
    }

    public static RammingPrey makeMoose(EntityType<? extends RammingPrey> animal, Level level) {
        return new RammingPrey(animal, level, TFCSounds.MOOSE, (double)0.75F);
    }

    public static RammingPrey makeBison(EntityType<? extends RammingPrey> animal, Level level) {
        return new RammingPrey(animal, level, TFCSounds.BISON, (double)0.75F);
    }

    public static Prey makeBongo(EntityType<? extends Prey> animal, Level level) {
        return new Prey(animal, level, TFCSounds.BONGO);
    }

    public static Prey makeCaribou(EntityType<? extends Prey> animal, Level level) {
        return new Prey(animal, level, TFCSounds.CARIBOU);
    }

    public static Prey makeDeer(EntityType<? extends Prey> animal, Level level) {
        return new Prey(animal, level, TFCSounds.DEER);
    }

    public static Prey makeGazelle(EntityType<? extends Prey> animal, Level level) {
        return new Prey(animal, level, TFCSounds.GAZELLE);
    }

    public static WingedPrey makePheasant(EntityType<? extends WingedPrey> animal, Level level) {
        return new WingedPrey(animal, level, TFCSounds.PHEASANT);
    }

    public static WingedPrey makeGrouse(EntityType<? extends WingedPrey> animal, Level level) {
        return new WingedPrey(animal, level, TFCSounds.GROUSE);
    }

    public static WingedPrey makeTurkey(EntityType<? extends WingedPrey> animal, Level level) {
        return new WingedPrey(animal, level, TFCSounds.TURKEY);
    }

    public static WingedPrey makePeafowl(EntityType<? extends WingedPrey> animal, Level level) {
        return new WingedPrey(animal, level, TFCSounds.PEAFOWL);
    }

    public static Pest makeRat(EntityType<? extends Pest> animal, Level level) {
        return new Pest(animal, level, TFCSounds.RAT);
    }

    static {
        ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, "firma_compat");
        //FRESHWATER_FISH = Helpers.mapOf(Fish.class, (fish) -> register(fish.getSerializedName(), Builder.of((type, level) -> new FreshwaterFish(type, level, (TFCSounds.FishId)TFCSounds.FRESHWATER_FISHES.get(fish), (Supplier)TFCItems.FRESHWATER_FISH_BUCKETS.get(fish)), MobCategory.WATER_AMBIENT).sized(fish.getWidth(), fish.getHeight()).clientTrackingRange(4)));
        FRESHWATER_FISH = Helpers.mapOf(Fish.class, (fish) -> register(
                "compat_" + fish.getSerializedName(),
                Builder.of(
                                (EntityType<FreshwaterFish> type, Level level) ->
                                        new FreshwaterFish(
                                                type,
                                                level,
                                                (TFCSounds.FishId) TFCSounds.FRESHWATER_FISHES.get(fish),
                                                (Supplier<Item>) ModItems.FRESHWATER_FISH_BUCKETS.get(fish)
                                        ),
                                MobCategory.WATER_AMBIENT
                        )
                        .sized(fish.getWidth(), fish.getHeight())
                        .clientTrackingRange(4)
        ));
        COD = register("compat_cod", Builder.of(TFCCod::new, MobCategory.WATER_AMBIENT).sized(0.5F, 0.3F).eyeHeight(0.26F).clientTrackingRange(4));
        TROPICAL_FISH = register("compat_tropical_fish", Builder.of(TFCTropicalFish::new, MobCategory.WATER_AMBIENT).sized(0.5F, 0.4F).eyeHeight(0.26F).clientTrackingRange(4));
        PUFFERFISH = register("compat_pufferfish", Builder.of(TFCPufferfish::new, MobCategory.WATER_AMBIENT).sized(0.7F, 0.7F).eyeHeight(0.455F).clientTrackingRange(4));
        JELLYFISH = register("compat_jellyfish", Builder.of(Jellyfish::new, MobCategory.WATER_AMBIENT).sized(0.5F, 0.5F).eyeHeight(0.26F).clientTrackingRange(4));
        ISOPOD = register("compat_isopod", Builder.of(AquaticCritter::salty, MobCategory.WATER_AMBIENT).sized(0.5F, 0.3F).eyeHeight(0.26F).clientTrackingRange(4));
        LOBSTER = register("compat_lobster", Builder.of(AquaticCritter::salty, MobCategory.WATER_AMBIENT).sized(0.5F, 0.3F).eyeHeight(0.26F).clientTrackingRange(4));
        CRAYFISH = register("compat_crayfish", Builder.of(AquaticCritter::fresh, MobCategory.WATER_AMBIENT).sized(0.5F, 0.3F).eyeHeight(0.26F).clientTrackingRange(4));
        HORSESHOE_CRAB = register("compat_horseshoe_crab", Builder.of(AquaticCritter::salty, MobCategory.WATER_AMBIENT).sized(0.5F, 0.3F).eyeHeight(0.26F).clientTrackingRange(4));
        DOLPHIN = register("compat_dolphin", Builder.of(TFCDolphin::new, MobCategory.WATER_CREATURE).sized(0.9F, 0.6F).eyeHeight(0.3F));
        ORCA = register("compat_orca", Builder.of(TFCDolphin::new, MobCategory.WATER_CREATURE).sized(1.1F, 1.0F).eyeHeight(0.3F));
        MANATEE = register("compat_manatee", Builder.of(Manatee::new, MobCategory.WATER_CREATURE).sized(1.5F, 1.2F).eyeHeight(0.3F));
        SQUID = register("compat_squid", Builder.of(TFCSquid::new, MobCategory.WATER_CREATURE).sized(0.8F, 0.8F).eyeHeight(0.4F).clientTrackingRange(8));
        OCTOPOTEUTHIS = register("compat_octopoteuthis", Builder.of(Octopoteuthis::new, MobCategory.UNDERGROUND_WATER_CREATURE).sized(0.8F, 0.8F).eyeHeight(0.4F).clientTrackingRange(8));
        TURTLE = register("compat_turtle", Builder.of(TFCTurtle::new, MobCategory.CREATURE).sized(0.8F, 0.3F).clientTrackingRange(10));
        PENGUIN = register("compat_penguin", Builder.of(Penguin::new, MobCategory.CREATURE).sized(0.3F, 0.6F).clientTrackingRange(10));
        LEOPARD_SEAL = register("compat_leopard_seal", Builder.of(LeopardSeal::new, MobCategory.CREATURE).sized(1.2F, 0.7F).clientTrackingRange(10));
        FROG = register("compat_frog", Builder.of(TFCFrog::new, MobCategory.CREATURE).sized(0.5F, 0.5F).clientTrackingRange(10));
        POLAR_BEAR = register("compat_polar_bear", Builder.of(Predator::createBear, MobCategory.CREATURE).immuneTo(new Block[]{Blocks.POWDER_SNOW}).sized(1.4F, 1.4F).clientTrackingRange(10));
        GRIZZLY_BEAR = register("compat_grizzly_bear", Builder.of(Predator::createBear, MobCategory.CREATURE).immuneTo(new Block[]{Blocks.POWDER_SNOW}).sized(1.4F, 1.6F).clientTrackingRange(10));
        BLACK_BEAR = register("compat_black_bear", Builder.of(Predator::createBear, MobCategory.CREATURE).immuneTo(new Block[]{Blocks.POWDER_SNOW}).sized(1.2F, 1.3F).clientTrackingRange(10));
        COUGAR = register("compat_cougar", Builder.of(FelinePredator::createCougar, MobCategory.CREATURE).sized(1.0F, 1.1F).clientTrackingRange(10));
        PANTHER = register("compat_panther", Builder.of(FelinePredator::createCougar, MobCategory.CREATURE).sized(1.0F, 1.1F).clientTrackingRange(10));
        LION = register("compat_lion", Builder.of(FelinePredator::createLion, MobCategory.CREATURE).sized(1.0F, 1.2F).clientTrackingRange(10));
        SABERTOOTH = register("compat_sabertooth", Builder.of(FelinePredator::createSabertooth, MobCategory.CREATURE).sized(1.1F, 1.3F).clientTrackingRange(10));
        TIGER = register("compat_tiger", Builder.of(FelinePredator::createTiger, MobCategory.CREATURE).sized(1.1F, 1.3F).clientTrackingRange(10));
        CROCODILE = register("compat_crocodile", Builder.of(AmphibiousPredator::createCrocodile, MobCategory.CREATURE).sized(1.8F, 0.8F).clientTrackingRange(10));
        WOLF = register("compat_wolf", Builder.of(PackPredator::createWolf, MobCategory.CREATURE).sized(0.65F, 0.9F).eyeHeight(0.68F).clientTrackingRange(10));
        HYENA = register("compat_hyena", Builder.of(PackPredator::createHyena, MobCategory.CREATURE).sized(0.65F, 0.9F).clientTrackingRange(10));
        DIREWOLF = register("compat_direwolf", Builder.of(PackPredator::createDirewolf, MobCategory.CREATURE).sized(1.0F, 1.2F).clientTrackingRange(10));
        PIG = register("compat_pig", Builder.of(CompatTFCEntities::makePig, MobCategory.CREATURE).sized(0.9F, 0.9F).clientTrackingRange(10));
        COW = register("compat_cow", Builder.of(CompatTFCEntities::makeCow, MobCategory.CREATURE).sized(0.9F, 1.4F).eyeHeight(1.3F).clientTrackingRange(10));
        GOAT = register("compat_goat", Builder.of(CompatTFCEntities::makeGoat, MobCategory.CREATURE).sized(0.9F, 1.3F).clientTrackingRange(10));
        YAK = register("compat_yak", Builder.of(CompatTFCEntities::makeYak, MobCategory.CREATURE).sized(1.3F, 1.7F).clientTrackingRange(10));
        ALPACA = register("compat_alpaca", Builder.of(CompatTFCEntities::makeAlpaca, MobCategory.CREATURE).sized(0.9F, 1.9F).clientTrackingRange(10));
        SHEEP = register("compat_sheep", Builder.of(CompatTFCEntities::makeSheep, MobCategory.CREATURE).sized(0.9F, 1.2F).clientTrackingRange(10));
        MUSK_OX = register("compat_musk_ox", Builder.of(CompatTFCEntities::makeMuskOx, MobCategory.CREATURE).sized(1.3F, 1.5F).clientTrackingRange(10));
        CHICKEN = register("compat_chicken", Builder.of(CompatTFCEntities::makeChicken, MobCategory.CREATURE).sized(0.4F, 0.7F).eyeHeight(0.644F).clientTrackingRange(10));
        DUCK = register("compat_duck", Builder.of(CompatTFCEntities::makeDuck, MobCategory.CREATURE).sized(0.4F, 0.7F).eyeHeight(0.644F).clientTrackingRange(10));
        QUAIL = register("compat_quail", Builder.of(CompatTFCEntities::makeQuail, MobCategory.CREATURE).sized(0.4F, 0.7F).eyeHeight(0.644F).clientTrackingRange(10));
        RABBIT = register("compat_rabbit", Builder.of(CompatTFCEntities::makeRabbit, MobCategory.CREATURE).sized(0.4F, 0.5F).clientTrackingRange(8));
        FOX = register("compat_fox", Builder.of(TFCFox::new, MobCategory.CREATURE).sized(0.6F, 0.7F).eyeHeight(0.4F).clientTrackingRange(8));
        PANDA = register("compat_panda", Builder.of(TFCPanda::new, MobCategory.CREATURE).sized(1.3F, 1.25F).clientTrackingRange(10));
        OCELOT = register("compat_ocelot", Builder.of(TFCOcelot::new, MobCategory.CREATURE).sized(0.6F, 0.7F).clientTrackingRange(10));
        DEER = register("compat_deer", Builder.of(CompatTFCEntities::makeDeer, MobCategory.CREATURE).sized(1.0F, 1.3F).clientTrackingRange(10));
        CARIBOU = register("compat_caribou", Builder.of(CompatTFCEntities::makeCaribou, MobCategory.CREATURE).sized(1.0F, 1.3F).clientTrackingRange(10));
        BONGO = register("compat_bongo", Builder.of(CompatTFCEntities::makeBongo, MobCategory.CREATURE).sized(1.0F, 1.3F).clientTrackingRange(10));
        GAZELLE = register("compat_gazelle", Builder.of(CompatTFCEntities::makeGazelle, MobCategory.CREATURE).sized(1.0F, 1.3F).clientTrackingRange(10));
        GROUSE = register("compat_grouse", Builder.of(CompatTFCEntities::makeGrouse, MobCategory.CREATURE).sized(0.4F, 0.7F).clientTrackingRange(10));
        PHEASANT = register("compat_pheasant", Builder.of(CompatTFCEntities::makePheasant, MobCategory.CREATURE).sized(0.4F, 0.7F).eyeHeight(0.644F).clientTrackingRange(10));
        TURKEY = register("compat_turkey", Builder.of(CompatTFCEntities::makeTurkey, MobCategory.CREATURE).sized(0.5F, 0.8F).eyeHeight(0.644F).clientTrackingRange(10));
        PEAFOWL = register("compat_peafowl", Builder.of(CompatTFCEntities::makePeafowl, MobCategory.CREATURE).sized(0.5F, 0.8F).eyeHeight(0.644F).clientTrackingRange(10));
        BOAR = register("compat_boar", Builder.of(CompatTFCEntities::makeBoar, MobCategory.CREATURE).sized(0.9F, 0.9F).clientTrackingRange(10));
        MOOSE = register("compat_moose", Builder.of(CompatTFCEntities::makeMoose, MobCategory.CREATURE).sized(1.8F, 2.2F).clientTrackingRange(10));
        WILDEBEEST = register("compat_wildebeest", Builder.of(CompatTFCEntities::makeWildebeest, MobCategory.CREATURE).sized(1.0F, 1.4F).clientTrackingRange(10));
        BISON = register("compat_bison", Builder.of(CompatTFCEntities::makeBison, MobCategory.CREATURE).sized(1.5F, 2.0F).clientTrackingRange(10));
        RAT = register("compat_rat", Builder.of(CompatTFCEntities::makeRat, MobCategory.CREATURE).sized(0.4F, 0.3F).eyeHeight(0.13F).clientTrackingRange(8));
        LEMMING = register("compat_lemming", Builder.of(CompatTFCEntities::makeRat, MobCategory.CREATURE).sized(0.4F, 0.3F).eyeHeight(0.13F).clientTrackingRange(8));
        MONGOOSE = register("compat_mongoose", Builder.of(CompatTFCEntities::makeRat, MobCategory.CREATURE).sized(0.6F, 0.4F).eyeHeight(0.23F).clientTrackingRange(8));
        JERBOA = register("compat_jerboa", Builder.of(CompatTFCEntities::makeRat, MobCategory.CREATURE).sized(0.4F, 0.4F).eyeHeight(0.23F).clientTrackingRange(8));
        DONKEY = register("compat_donkey", Builder.of(TFCDonkey::new, MobCategory.CREATURE).sized(1.3964844F, 1.5F).eyeHeight(1.425F).passengerAttachments(new float[]{1.1125F}).clientTrackingRange(10));
        MULE = register("compat_mule", Builder.of(TFCMule::new, MobCategory.CREATURE).sized(1.3964844F, 1.6F).eyeHeight(1.52F).passengerAttachments(new float[]{1.2125F}).clientTrackingRange(8));
        //HORSE = register("compat_horse", Builder.of(TFCHorse::new, MobCategory.CREATURE).sized(1.3964844F, 1.6F).eyeHeight(1.52F).passengerAttachments(new float[]{1.44375F}).clientTrackingRange(10));
        CAT = register("compat_cat", Builder.of(TFCCat::new, MobCategory.CREATURE).sized(0.6F, 0.7F).eyeHeight(0.35F).clientTrackingRange(8));
        DOG = register("compat_dog", Builder.of(Dog::new, MobCategory.CREATURE).sized(0.6F, 0.85F).eyeHeight(0.68F).clientTrackingRange(10));

        HORSE = register("compat_horse",
                Builder.of(
                                (EntityType<TFCHorse> type, Level level) ->
                                        new TFCHorse(type, level, TFCSounds.HORSE,
                                                () -> SoundEvents.HORSE_EAT,
                                                () -> SoundEvents.HORSE_ANGRY,
                                                TFCConfig.SERVER.horseConfig),
                                MobCategory.CREATURE
                        )
                        .sized(1.3964844F, 1.6F)
                        .eyeHeight(1.52F)
                        .passengerAttachments(new float[]{1.44375F})
                        .clientTrackingRange(10)
        );
    }


    public static record Id<T extends Entity>(DeferredHolder<EntityType<?>, EntityType<T>> holder) implements RegistryHolder<EntityType<?>, EntityType<T>> {
    }
}

