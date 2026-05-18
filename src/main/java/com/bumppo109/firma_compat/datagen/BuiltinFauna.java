package com.bumppo109.firma_compat.datagen;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.UnaryOperator;

import com.bumppo109.firma_compat.FirmaCompat;
import com.bumppo109.firma_compat.entity.CompatFauna;
import com.bumppo109.firma_compat.entity.CompatFaunas;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import net.dries007.tfc.common.entities.aquatic.Fish;
import net.dries007.tfc.util.calendar.Month;

public class BuiltinFauna extends DataManagerProvider<CompatFauna> {
    private static final List<Month> FRESHWATER_FISH_MONTHS = List.of(Month.MAY, Month.JUNE, Month.JULY, Month.AUGUST, Month.SEPTEMBER, Month.OCTOBER);

    public BuiltinFauna(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(CompatFauna.MANAGER, output, lookup, FirmaCompat.MODID);
    }

    @Override
    protected void addData(HolderLookup.Provider provider) {
        add(CompatFaunas.ISOPOD, b -> b.distanceBelowSeaLevel(20).maxTemperature(16));
        add(CompatFaunas.CRAYFISH, b -> b.distanceBelowSeaLevel(2).minTemperature(10).minGroundwater(250));
        add(CompatFaunas.LOBSTER, b -> b.distanceBelowSeaLevel(1).maxTemperature(25));
        add(CompatFaunas.HORSESHOE_CRAB, b -> b.distanceBelowSeaLevel(1).temperature(10, 30).minGroundwater(250));
        add(CompatFaunas.COD, b -> b.distanceBelowSeaLevel(5).maxTemperature(25));
        add(CompatFaunas.PUFFERFISH, b -> b.distanceBelowSeaLevel(3).minTemperature(10));
        add(CompatFaunas.TROPICAL_FISH, b -> b.distanceBelowSeaLevel(3).temperature(10, 20));
        add(CompatFaunas.JELLYFISH, b -> b.distanceBelowSeaLevel(3).minTemperature(10));
        add(CompatFaunas.ORCA, b -> b.distanceBelowSeaLevel(6).maxTemperature(20).minGroundwater(250).chance(10));
        add(CompatFaunas.DOLPHIN, b -> b.distanceBelowSeaLevel(6).minTemperature(10).minGroundwater(250).chance(10));
        add(CompatFaunas.MANATEE, b -> b.distanceBelowSeaLevel(3).minTemperature(30).minGroundwater(250).chance(10));
        add(CompatFaunas.CROCODILE, b -> b.distanceBelowSeaLevel(0).minTemperature(20));

        add(CompatFaunas.FISH.get(Fish.BLUEGILL), b -> b.temperature(0, 30).minGroundwater(250).months(FRESHWATER_FISH_MONTHS));
        add(CompatFaunas.FISH.get(Fish.CRAPPIE), b -> b.temperature(0, 30).minGroundwater(250).months(FRESHWATER_FISH_MONTHS));
        add(CompatFaunas.FISH.get(Fish.LAKE_TROUT), b -> b.temperature(0, 30).minGroundwater(250).months(FRESHWATER_FISH_MONTHS));
        add(CompatFaunas.FISH.get(Fish.RAINBOW_TROUT), b -> b.temperature(0, 30).minGroundwater(250).months(FRESHWATER_FISH_MONTHS));
        add(CompatFaunas.FISH.get(Fish.LARGEMOUTH_BASS), b -> b.temperature(0, 30).minGroundwater(250).months(FRESHWATER_FISH_MONTHS));
        add(CompatFaunas.FISH.get(Fish.SMALLMOUTH_BASS), b -> b.temperature(0, 30).minGroundwater(250).months(FRESHWATER_FISH_MONTHS));
        add(CompatFaunas.FISH.get(Fish.SALMON), b -> b.temperature(0, 30).minGroundwater(250).months(FRESHWATER_FISH_MONTHS));

        add(CompatFaunas.PENGUIN, b -> b.maxTemperature(-5).minGroundwater(250));
        add(CompatFaunas.LEOPARD_SEAL, b -> b.maxTemperature(5).minGroundwater(250));
        add(CompatFaunas.FROG, b -> b.minGroundwater(250).minTemperature(25));
        add(CompatFaunas.TURTLE, b -> b.minTemperature(16).minGroundwater(250));
        add(CompatFaunas.POLAR_BEAR, b -> b.maxTemperature(-5).minGroundwater(250));
        add(CompatFaunas.GRIZZLY_BEAR, b -> b.temperature(-15, 30).minGroundwater(250));
        add(CompatFaunas.BLACK_BEAR, b -> b.temperature(0, 20).minGroundwater(250));
        add(CompatFaunas.COUGAR, b -> b.temperature(-10, 21).minGroundwater(250));
        add(CompatFaunas.PANTHER, b -> b.temperature(-10, 21).minGroundwater(250));
        add(CompatFaunas.LION, b -> b.minTemperature(60).minGroundwater(250));
        add(CompatFaunas.SABERTOOTH, b -> b.maxTemperature(0).minGroundwater(250));
        add(CompatFaunas.TIGER, b -> b.temperature(35, 60).minGroundwater(250));

        add(CompatFaunas.SQUID, b -> b.distanceBelowSeaLevel(15));
        add(CompatFaunas.OCTOPOTEUTHIS, b -> b.maxBrightness(0).distanceBelowSeaLevel(33));
        add(CompatFaunas.PIG, b -> b.temperature(5, 30).minGroundwater(250));
        add(CompatFaunas.COW, b -> b.temperature(5, 30).minGroundwater(250));
        add(CompatFaunas.GOAT, b -> b.temperature(-40, 25).minElevation(100));
        add(CompatFaunas.YAK, b -> b.maxTemperature(-5).minGroundwater(250).minElevation(80));
        add(CompatFaunas.ALPACA, b -> b.temperature(-8, 12).minGroundwater(250).minElevation(80));
        add(CompatFaunas.SHEEP, b -> b.temperature(0, 35).minGroundwater(250));
        add(CompatFaunas.MUSK_OX, b -> b.temperature(-25, 0).minGroundwater(250));
        add(CompatFaunas.CHICKEN, b -> b.minTemperature(14).minGroundwater(250));
        add(CompatFaunas.DUCK, b -> b.temperature(-25, 30).minGroundwater(250));
        add(CompatFaunas.QUAIL, b -> b.temperature(-15, 10).minGroundwater(250));
        add(CompatFaunas.RABBIT, b -> b.minTemperature(-16).minGroundwater(250));
        add(CompatFaunas.FOX, b -> b.maxTemperature(25).minGroundwater(250));
        add(CompatFaunas.PANDA, b -> b.temperature(35, 60).minGroundwater(250));
        add(CompatFaunas.BOAR, b -> b.temperature(-5, 25).minGroundwater(250));
        add(CompatFaunas.WILDEBEEST, b -> b.minTemperature(60).minGroundwater(250));
        add(CompatFaunas.BISON, b -> b.temperature(-10, 30).minGroundwater(250));
        add(CompatFaunas.OCELOT, b -> b.temperature(35, 60).minGroundwater(250));
        add(CompatFaunas.CARIBOU, b -> b.maxTemperature(0).minGroundwater(250));
        add(CompatFaunas.DEER, b -> b.temperature(0, 30).minGroundwater(250));
        add(CompatFaunas.GAZELLE, b -> b.minTemperature(60).minGroundwater(250));
        add(CompatFaunas.BONGO, b -> b.minTemperature(60).minGroundwater(250));
        add(CompatFaunas.MOOSE, b -> b.temperature(-15, 16).minGroundwater(250));
        add(CompatFaunas.GROUSE, b -> b.temperature(-15, 16).minGroundwater(250));
        add(CompatFaunas.PHEASANT, b -> b.temperature(-5, 20).minGroundwater(250));
        add(CompatFaunas.TURKEY, b -> b.temperature(0, 25).minGroundwater(250));
        add(CompatFaunas.PEAFOWL, b -> b.minTemperature(16).minGroundwater(250));
        add(CompatFaunas.WOLF, b -> b.temperature(-15, 30).minGroundwater(250));
        add(CompatFaunas.HYENA, b -> b.minTemperature(60).minGroundwater(250));
        add(CompatFaunas.DIREWOLF, b -> b.maxTemperature(-5).minGroundwater(250));
        add(CompatFaunas.DONKEY, b -> b.minTemperature(0).minGroundwater(250));
        add(CompatFaunas.MULE, b -> b.minTemperature(0).minGroundwater(250));
        add(CompatFaunas.HORSE, b -> b.minTemperature(0).minGroundwater(250));
    }

    private void add(CompatFaunas.Id<?> fauna, UnaryOperator<CompatFauna.Builder> builder) {
        add(fauna.fauna(), builder.apply(new CompatFauna.Builder()).build());
    }
}
