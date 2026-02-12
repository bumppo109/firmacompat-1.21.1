package com.bumppo109.firma_compat.entity;

import java.util.Map;
import java.util.function.Supplier;

import com.bumppo109.firma_compat.FirmaCompat;
import com.bumppo109.firma_compat.FirmaCompatHelpers;
import com.bumppo109.firma_compat.util.ModTags;
import com.google.gson.JsonParseException;
import net.dries007.tfc.client.overworld.SolarCalculator;
import net.dries007.tfc.common.entities.Fauna;
import net.dries007.tfc.common.entities.ai.predator.PackPredator;
import net.dries007.tfc.common.entities.ai.prey.TFCOcelot;
import net.dries007.tfc.common.entities.aquatic.AquaticCritter;
import net.dries007.tfc.common.entities.aquatic.AquaticMob;
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
import net.dries007.tfc.common.entities.livestock.WoolyAnimal;
import net.dries007.tfc.common.entities.livestock.horse.TFCDonkey;
import net.dries007.tfc.common.entities.livestock.horse.TFCHorse;
import net.dries007.tfc.common.entities.livestock.horse.TFCMule;
import net.dries007.tfc.common.entities.predator.AmphibiousPredator;
import net.dries007.tfc.common.entities.predator.FelinePredator;
import net.dries007.tfc.common.entities.predator.Predator;
import net.dries007.tfc.common.entities.prey.Prey;
import net.dries007.tfc.common.entities.prey.RammingPrey;
import net.dries007.tfc.common.entities.prey.TFCFox;
import net.dries007.tfc.common.entities.prey.TFCFrog;
import net.dries007.tfc.common.entities.prey.TFCPanda;
import net.dries007.tfc.common.entities.prey.TFCRabbit;
import net.dries007.tfc.common.entities.prey.WingedPrey;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.data.DataManager;
import net.dries007.tfc.util.data.DataManagers;
import net.dries007.tfc.util.registry.IdHolder;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent.Operation;

import static vazkii.patchouli.api.PatchouliAPI.LOGGER;

public class CompatFaunas {
    public static final CompatFaunas.Id<TFCCod> COD;
    public static final CompatFaunas.Id<Jellyfish> JELLYFISH;
    public static final CompatFaunas.Id<TFCTropicalFish> TROPICAL_FISH;
    public static final CompatFaunas.Id<TFCPufferfish> PUFFERFISH;
    public static final Map<Fish, CompatFaunas.Id<FreshwaterFish>> FISH;
    public static final CompatFaunas.Id<AquaticCritter> LOBSTER;
    public static final CompatFaunas.Id<AquaticCritter> CRAYFISH;
    public static final CompatFaunas.Id<AquaticCritter> ISOPOD;
    public static final CompatFaunas.Id<AquaticCritter> HORSESHOE_CRAB;
    public static final CompatFaunas.Id<TFCDolphin> DOLPHIN;
    public static final CompatFaunas.Id<TFCDolphin> ORCA;
    public static final CompatFaunas.Id<Manatee> MANATEE;
    public static final CompatFaunas.Id<TFCTurtle> TURTLE;
    public static final CompatFaunas.Id<Penguin> PENGUIN;
    public static final CompatFaunas.Id<LeopardSeal> LEOPARD_SEAL;
    public static final CompatFaunas.Id<TFCFrog> FROG;
    public static final CompatFaunas.Id<Predator> POLAR_BEAR;
    public static final CompatFaunas.Id<Predator> GRIZZLY_BEAR;
    public static final CompatFaunas.Id<Predator> BLACK_BEAR;
    public static final CompatFaunas.Id<FelinePredator> COUGAR;
    public static final CompatFaunas.Id<FelinePredator> PANTHER;
    public static final CompatFaunas.Id<FelinePredator> LION;
    public static final CompatFaunas.Id<FelinePredator> SABERTOOTH;
    public static final CompatFaunas.Id<FelinePredator> TIGER;
    public static final CompatFaunas.Id<AmphibiousPredator> CROCODILE;
    public static final CompatFaunas.Id<PackPredator> WOLF;
    public static final CompatFaunas.Id<PackPredator> HYENA;
    public static final CompatFaunas.Id<PackPredator> DIREWOLF;
    public static final CompatFaunas.Id<TFCSquid> SQUID;
    public static final CompatFaunas.Id<Octopoteuthis> OCTOPOTEUTHIS;
    public static final CompatFaunas.Id<Mammal> PIG;
    public static final CompatFaunas.Id<DairyAnimal> COW;
    public static final CompatFaunas.Id<DairyAnimal> GOAT;
    public static final CompatFaunas.Id<DairyAnimal> YAK;
    public static final CompatFaunas.Id<WoolyAnimal> ALPACA;
    public static final CompatFaunas.Id<WoolyAnimal> SHEEP;
    public static final CompatFaunas.Id<WoolyAnimal> MUSK_OX;
    public static final CompatFaunas.Id<OviparousAnimal> CHICKEN;
    public static final CompatFaunas.Id<OviparousAnimal> DUCK;
    public static final CompatFaunas.Id<OviparousAnimal> QUAIL;
    public static final CompatFaunas.Id<TFCRabbit> RABBIT;
    public static final CompatFaunas.Id<TFCFox> FOX;
    public static final CompatFaunas.Id<TFCPanda> PANDA;
    public static final CompatFaunas.Id<TFCOcelot> OCELOT;
    public static final CompatFaunas.Id<RammingPrey> BOAR;
    public static final CompatFaunas.Id<RammingPrey> WILDEBEEST;
    public static final CompatFaunas.Id<RammingPrey> BISON;
    public static final CompatFaunas.Id<Prey> BONGO;
    public static final CompatFaunas.Id<Prey> CARIBOU;
    public static final CompatFaunas.Id<Prey> DEER;
    public static final CompatFaunas.Id<Prey> GAZELLE;
    public static final CompatFaunas.Id<RammingPrey> MOOSE;
    public static final CompatFaunas.Id<WingedPrey> GROUSE;
    public static final CompatFaunas.Id<WingedPrey> PHEASANT;
    public static final CompatFaunas.Id<WingedPrey> TURKEY;
    public static final CompatFaunas.Id<WingedPrey> PEAFOWL;
    public static final CompatFaunas.Id<TFCDonkey> DONKEY;
    public static final CompatFaunas.Id<TFCMule> MULE;
    public static final CompatFaunas.Id<TFCHorse> HORSE;

    static {
        // Register your manager into TFC's existing DeferredRegister
        // This runs exactly once when the class is first referenced
        DataManagers.MANAGERS.register(
                CompatFauna.MANAGER.getName(),          // usually returns "fauna"
                () -> CompatFauna.MANAGER
        );
    }

    public static void init() {}

    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        FISH.values().forEach((fish) -> registerSpawnPlacement(event, fish));
        registerSpawnPlacement(event, COD);
        registerSpawnPlacement(event, JELLYFISH);
        registerSpawnPlacement(event, TROPICAL_FISH);
        registerSpawnPlacement(event, PUFFERFISH);
        registerSpawnPlacement(event, LOBSTER);
        registerSpawnPlacement(event, CRAYFISH);
        registerSpawnPlacement(event, ISOPOD);
        registerSpawnPlacement(event, HORSESHOE_CRAB);
        registerSpawnPlacement(event, DOLPHIN);
        registerSpawnPlacement(event, ORCA);
        registerSpawnPlacement(event, MANATEE);
        registerSpawnPlacement(event, TURTLE);
        registerSpawnPlacement(event, PENGUIN);
        registerSpawnPlacement(event, LEOPARD_SEAL);
        registerSpawnPlacement(event, FROG);
        registerSpawnPlacement(event, POLAR_BEAR);
        registerSpawnPlacement(event, GRIZZLY_BEAR);
        registerSpawnPlacement(event, BLACK_BEAR);
        registerSpawnPlacement(event, COUGAR);
        registerSpawnPlacement(event, PANTHER);
        registerSpawnPlacement(event, LION);
        registerSpawnPlacement(event, SABERTOOTH);
        registerSpawnPlacement(event, TIGER);
        registerSpawnPlacement(event, CROCODILE);
        registerSpawnPlacement(event, SQUID);
        registerSpawnPlacement(event, OCTOPOTEUTHIS);
        registerSpawnPlacement(event, PIG);
        registerSpawnPlacement(event, COW);
        registerSpawnPlacement(event, GOAT);
        registerSpawnPlacement(event, YAK);
        registerSpawnPlacement(event, ALPACA);
        registerSpawnPlacement(event, SHEEP);
        registerSpawnPlacement(event, MUSK_OX);
        registerSpawnPlacement(event, CHICKEN);
        registerSpawnPlacement(event, DUCK);
        registerSpawnPlacement(event, QUAIL);
        registerSpawnPlacement(event, RABBIT);
        registerSpawnPlacement(event, FOX);
        registerSpawnPlacement(event, PANDA);
        registerSpawnPlacement(event, OCELOT);
        registerSpawnPlacement(event, BOAR);
        registerSpawnPlacement(event, WILDEBEEST);
        registerSpawnPlacement(event, BISON);
        registerSpawnPlacement(event, MOOSE);
        registerSpawnPlacement(event, BONGO);
        registerSpawnPlacement(event, CARIBOU);
        registerSpawnPlacement(event, DEER);
        registerSpawnPlacement(event, GAZELLE);
        registerSpawnPlacement(event, GROUSE);
        registerSpawnPlacement(event, PHEASANT);
        registerSpawnPlacement(event, TURKEY);
        registerSpawnPlacement(event, PEAFOWL);
        registerSpawnPlacement(event, DONKEY);
        registerSpawnPlacement(event, MULE);
        registerSpawnPlacement(event, HORSE);
        registerSpawnPlacement(event, WOLF);
        registerSpawnPlacement(event, HYENA);
        registerSpawnPlacement(event, DIREWOLF);
    }

    private static <E extends Mob> CompatFaunas.Id<E> registerAnimal(IdHolder<EntityType<E>> entity) {
        return register(entity, SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES);
    }

    private static <E extends Mob> CompatFaunas.Id<E> registerFish(IdHolder<EntityType<E>> entity) {
        return register(entity, SpawnPlacementTypes.IN_WATER, Types.MOTION_BLOCKING_NO_LEAVES);
    }

    private static <E extends Mob> CompatFaunas.Id<E> registerAmphibiousPredator(IdHolder<EntityType<E>> entity) {
        return register(entity, SpawnPlacementTypes.NO_RESTRICTIONS, Types.MOTION_BLOCKING_NO_LEAVES);
    }

    private static <E extends Mob> CompatFaunas.Id<E> register(IdHolder<EntityType<E>> entity, SpawnPlacementType spawnPlacement, Heightmap.Types heightmapType) {
        return new CompatFaunas.Id<E>(entity, CompatFauna.MANAGER.getReference(entity.getId()), spawnPlacement, heightmapType);
    }

    private static <E extends Mob> void registerSpawnPlacement(RegisterSpawnPlacementsEvent event, Id<E> type) {
        event.register((EntityType)type.entity().get(), type.spawnPlacementType(), type.heightmapType(), (mob, level, heightmap, pos, rand) -> {
            CompatFauna fauna;
            try {
                fauna = (CompatFauna) type.fauna().get();
            } catch (IllegalStateException e) {
                FirmaCompat.LOGGER.debug("did not load fauna data for {}", type.entity().get());
                return false;
            }
            ChunkGenerator generator = level.getLevel().getChunkSource().getGenerator();
            if (rand.nextInt(fauna.chance()) != 0) {
                return false;
            } else {
                if (mob instanceof AquaticMob) {
                    AquaticMob aquaticMob = (AquaticMob)mob;
                    if (!aquaticMob.canSpawnIn(level.getFluidState(pos).getType())) {
                        return false;
                    }
                }

                boolean isWater = Helpers.isFluid(level.getFluidState(pos), ModTags.Fluids.WATERLOGGING_WATER);
                boolean isWaterBelow = Helpers.isFluid(level.getFluidState(pos), ModTags.Fluids.WATERLOGGING_WATER);
                if(isWater || isWaterBelow){
                    return false;
                }

                int seaLevel = generator.getSeaLevel();
                if (fauna.distanceBelowSeaLevel() != -1 && pos.getY() > seaLevel - fauna.distanceBelowSeaLevel()) {
                    return false;
                } else {
                    boolean hemisphere = SolarCalculator.getInNorthernHemisphere(pos, level.getLevel());
                    if (!fauna.climate().isValid((WorldGenLevel) level, pos, rand)) {
                        return false;
                    } else {
                        BlockPos below = pos.below();
                        if (fauna.solidGround() && !Helpers.isBlock(level.getBlockState(below), BlockTags.VALID_SPAWN)) {
                            return false;
                        } else if (!(mob.equals(CompatTFCEntities.POLAR_BEAR.get())) && Helpers.isBlock(level.getBlockState(below), BlockTags.ICE)) {
                            return false;
                        } else if (!fauna.months().isEmpty() && !fauna.months().contains(Calendars.SERVER.getHemispheralCalendarMonthOfYear(hemisphere))) {
                            return false;
                        } else {
                            return fauna.maxBrightness() == -1 || level.getRawBrightness(pos, 0) <= fauna.maxBrightness();
                        }
                    }
                }
            }
        }, Operation.REPLACE);
    }

    static {
        COD = registerFish(CompatTFCEntities.COD);
        JELLYFISH = registerFish(CompatTFCEntities.JELLYFISH);
        TROPICAL_FISH = registerFish(CompatTFCEntities.TROPICAL_FISH);
        PUFFERFISH = registerFish(CompatTFCEntities.PUFFERFISH);
        FISH = Helpers.mapOf(Fish.class, (fish) -> registerFish((IdHolder)CompatTFCEntities.FRESHWATER_FISH.get(fish)));
        LOBSTER = registerFish(CompatTFCEntities.LOBSTER);
        CRAYFISH = registerFish(CompatTFCEntities.CRAYFISH);
        ISOPOD = registerFish(CompatTFCEntities.ISOPOD);
        HORSESHOE_CRAB = registerFish(CompatTFCEntities.HORSESHOE_CRAB);
        DOLPHIN = registerFish(CompatTFCEntities.DOLPHIN);
        ORCA = registerFish(CompatTFCEntities.ORCA);
        MANATEE = registerFish(CompatTFCEntities.MANATEE);
        TURTLE = registerAnimal(CompatTFCEntities.TURTLE);
        PENGUIN = registerAnimal(CompatTFCEntities.PENGUIN);
        LEOPARD_SEAL = registerAnimal(CompatTFCEntities.LEOPARD_SEAL);
        FROG = registerAnimal(CompatTFCEntities.FROG);
        POLAR_BEAR = registerAnimal(CompatTFCEntities.POLAR_BEAR);
        GRIZZLY_BEAR = registerAnimal(CompatTFCEntities.GRIZZLY_BEAR);
        BLACK_BEAR = registerAnimal(CompatTFCEntities.BLACK_BEAR);
        COUGAR = registerAnimal(CompatTFCEntities.COUGAR);
        PANTHER = registerAnimal(CompatTFCEntities.PANTHER);
        LION = registerAnimal(CompatTFCEntities.LION);
        SABERTOOTH = registerAnimal(CompatTFCEntities.SABERTOOTH);
        TIGER = registerAnimal(CompatTFCEntities.TIGER);
        CROCODILE = registerAmphibiousPredator(CompatTFCEntities.CROCODILE);
        WOLF = registerAnimal(CompatTFCEntities.WOLF);
        HYENA = registerAnimal(CompatTFCEntities.HYENA);
        DIREWOLF = registerAnimal(CompatTFCEntities.DIREWOLF);
        SQUID = registerFish(CompatTFCEntities.SQUID);
        OCTOPOTEUTHIS = registerFish(CompatTFCEntities.OCTOPOTEUTHIS);
        PIG = registerAnimal(CompatTFCEntities.PIG);
        COW = registerAnimal(CompatTFCEntities.COW);
        GOAT = registerAnimal(CompatTFCEntities.GOAT);
        YAK = registerAnimal(CompatTFCEntities.YAK);
        ALPACA = registerAnimal(CompatTFCEntities.ALPACA);
        SHEEP = registerAnimal(CompatTFCEntities.SHEEP);
        MUSK_OX = registerAnimal(CompatTFCEntities.MUSK_OX);
        CHICKEN = registerAnimal(CompatTFCEntities.CHICKEN);
        DUCK = registerAnimal(CompatTFCEntities.DUCK);
        QUAIL = registerAnimal(CompatTFCEntities.QUAIL);
        RABBIT = registerAnimal(CompatTFCEntities.RABBIT);
        FOX = registerAnimal(CompatTFCEntities.FOX);
        PANDA = registerAnimal(CompatTFCEntities.PANDA);
        OCELOT = registerAnimal(CompatTFCEntities.OCELOT);
        BOAR = registerAnimal(CompatTFCEntities.BOAR);
        WILDEBEEST = registerAnimal(CompatTFCEntities.WILDEBEEST);
        BISON = registerAnimal(CompatTFCEntities.BISON);
        BONGO = registerAnimal(CompatTFCEntities.BONGO);
        CARIBOU = registerAnimal(CompatTFCEntities.CARIBOU);
        DEER = registerAnimal(CompatTFCEntities.DEER);
        GAZELLE = registerAnimal(CompatTFCEntities.GAZELLE);
        MOOSE = registerAnimal(CompatTFCEntities.MOOSE);
        GROUSE = registerAnimal(CompatTFCEntities.GROUSE);
        PHEASANT = registerAnimal(CompatTFCEntities.PHEASANT);
        TURKEY = registerAnimal(CompatTFCEntities.TURKEY);
        PEAFOWL = registerAnimal(CompatTFCEntities.PEAFOWL);
        DONKEY = registerAnimal(CompatTFCEntities.DONKEY);
        MULE = registerAnimal(CompatTFCEntities.MULE);
        HORSE = registerAnimal(CompatTFCEntities.HORSE);
    }

    public static record Id<E extends Mob>(Supplier<EntityType<E>> entity, DataManager.Reference<CompatFauna> fauna, SpawnPlacementType spawnPlacementType, Heightmap.Types heightmapType) {}
}

