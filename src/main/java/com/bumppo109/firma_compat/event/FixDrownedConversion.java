package com.bumppo109.firma_compat.event;

import com.bumppo109.firma_compat.util.ModTags;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingBreatheEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDrownEvent;

import java.util.Objects;

@EventBusSubscriber(modid = "firma_compat") // Replace with your mod ID
public class FixDrownedConversion {

    @SubscribeEvent
    public static void handleSaltWaterBreathing(LivingBreatheEvent event) {
        LivingEntity entity = event.getEntity();

        // Early exit: only care about zombies and drowned
        if (!(entity instanceof Zombie || entity instanceof Drowned)) return;

        // Quick check: skip if not in fluid at all
        if (!entity.isInWater() && !entity.isInFluidType(TFCFluids.SALT_WATER.getSource().getFluidType())) {
            return;
        }

        BlockPos eyePos = BlockPos.containing(entity.getEyePosition());
        FluidState eyeFluid = entity.level().getFluidState(eyePos);

        // Only act if eye is in TFC salt water (use your tag for consistency)
        if (!eyeFluid.is(ModTags.Fluids.WATERLOGGING_WATER)) {
            return;
        }

        // Drowned: full immunity in salt water
        if (entity instanceof Drowned) {
            event.setCanBreathe(true);
            event.setRefillAirAmount(entity.getMaxAirSupply()); // instant full refill
            event.setConsumeAirAmount(0); // no depletion even if canBreathe=false
            return;
        }

        // Zombie: simulate drowning
        Zombie zombie = (Zombie) entity;

        // Skip if zombie has water breathing or can breathe underwater
        if (zombie.canBreatheUnderwater() || zombie.hasEffect(MobEffects.WATER_BREATHING)) {
            event.setCanBreathe(true);
            event.setRefillAirAmount(4); // fast refill like vanilla out-of-water
            event.setConsumeAirAmount(0);
            return;
        }

        // Normal drowning: consume air, apply damage later via vanilla
        event.setCanBreathe(false);
        event.setConsumeAirAmount(1);      // 1 per tick = 20 per second (vanilla rate)
        event.setRefillAirAmount(0);
    }
    /**
     * Fallback / reinforcement: zero damage in Pre too.
     * This catches any damage that slips through HurtEvent.
     */
    /*
    @SubscribeEvent
    public static void drownedImmune(LivingDamageEvent.Pre event) {
        LivingEntity entity = event.getEntity();

        if (!(entity instanceof Drowned)) return;
        if (!event.getSource().is(net.minecraft.world.damagesource.DamageTypes.DROWN)) return;

        BlockPos eye = BlockPos.containing(entity.getEyePosition());
        FluidState fluid = entity.level().getFluidState(eye);

        if (fluid.is(ModTags.Fluids.WATERLOGGING_WATER)) {
            event.setNewDamage(0.0F);
        }
    }

     */
}
