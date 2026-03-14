package com.bumppo109.firma_compat.event;

import com.bumppo109.firma_compat.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingBreatheEvent;

@EventBusSubscriber(modid = "firma_compat") // keep your modid
public class HybridAquaticFishSurvival {

    @SubscribeEvent
    public static void handleHybridAquaticBreathing(LivingBreatheEvent event) {
        LivingEntity entity = event.getEntity();

        // Only affect Hybrid Aquatic mobs
        ResourceLocation entityKey = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        if (!"hybrid_aquatic".equals(entityKey.getNamespace())) {
            return;
        }

        // Quick exit if not in any water at all
        if (!entity.isInWater()) {
            return;
        }

        BlockPos eyePos = BlockPos.containing(entity.getEyePosition());
        FluidState eyeFluid = entity.level().getFluidState(eyePos);

        // Breathe perfectly in TFC fresh water, salt water, AND vanilla water
        // (your WATERLOGGING_WATER tag should already contain TFC waters)
        if (eyeFluid.is(ModTags.Fluids.WATERLOGGING_WATER) || eyeFluid.is(net.minecraft.tags.FluidTags.WATER)) {
            event.setCanBreathe(true);
            event.setRefillAirAmount(entity.getMaxAirSupply()); // instant full air
            event.setConsumeAirAmount(0);                       // never lose air
            return;
        }

        // If they're in some other fluid (lava, milk, etc.), vanilla logic applies (they drown/burn normally)
    }
}
