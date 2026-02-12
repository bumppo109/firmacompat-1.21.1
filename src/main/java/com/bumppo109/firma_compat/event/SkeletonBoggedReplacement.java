package com.bumppo109.firma_compat.event;

import com.bumppo109.firma_compat.FirmaCompat;
import com.bumppo109.firma_compat.util.ModTags;
import net.dries007.tfc.util.climate.OverworldClimateModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Bogged;
import net.minecraft.world.entity.monster.Skeleton;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

@EventBusSubscriber (modid = "firma_compat")
public class SkeletonBoggedReplacement {

    @SubscribeEvent
    public static void replaceSkeletonWithBogged(EntityJoinLevelEvent event) {
        // Early exits – very important for performance
        if (event.getLevel().isClientSide()) return;
        if (!(event.getEntity() instanceof Skeleton skeleton)) return;

        // Skip loaded-from-disk entities (chunk reload, dimension travel, etc.)
        if (event.loadedFromDisk()) return;

        // Only replace natural / structure / spawner spawns
        ServerLevel level = (ServerLevel) event.getLevel();
        BlockPos pos = skeleton.blockPosition();

            // Use your existing climate check (from CompatClimatePlacement or similar)
            // Assuming you have a helper method or can reuse logic from there
            if (isBoggedClimateValid(level, pos)) {
                // Cancel skeleton spawn
                event.setCanceled(true);

                // Spawn bogged instead
                Bogged bogged = EntityType.BOGGED.create(level);
                if (bogged != null) {
                    // Copy position, rotation, motion
                    bogged.moveTo(skeleton.getX(), skeleton.getY(), skeleton.getZ(),
                            skeleton.getYRot(), skeleton.getXRot());
                    bogged.setDeltaMovement(skeleton.getDeltaMovement());

                    // Copy relevant state (baby, custom name, persistence, etc.)
                    bogged.setBaby(skeleton.isBaby());
                    bogged.setNoAi(skeleton.isNoAi());
                    bogged.setSilent(skeleton.isSilent());
                    bogged.setCustomName(skeleton.getCustomName());
                    bogged.setCustomNameVisible(skeleton.isCustomNameVisible());
                    bogged.setPersistenceRequired();

                    // Copy equipment (very important – skeletons often spawn with bows/arrows)
                    for (var slot : net.minecraft.world.entity.EquipmentSlot.values()) {
                        bogged.setItemSlot(slot, skeleton.getItemBySlot(slot).copy());
                    }

                    // Copy active effects (e.g. if it had any from spawner)
                    skeleton.getActiveEffects().forEach(bogged::addEffect);

                    // Finalize spawn (applies difficulty scaling, etc.)
                    bogged.finalizeSpawn(level, level.getCurrentDifficultyAt(pos),
                            MobSpawnType.CONVERSION, null);

                    // Add to world
                    level.addFreshEntity(bogged);

                    // Optional: subtle visual feedback (smoke particles like despawn)
                    level.sendParticles(net.minecraft.core.particles.ParticleTypes.SMOKE,
                            bogged.getX(), bogged.getY() + bogged.getBbHeight() / 2.0,
                            bogged.getZ(), 8, 0.3, 0.3, 0.3, 0.02);
                }
            }

    }

    private static boolean isBoggedClimateValid(ServerLevel level, BlockPos pos) {
        var biome = level.getBiome(pos);

        return biome.is(ResourceLocation.fromNamespaceAndPath("tfc", "lowlands"))
                || biome.is(ResourceLocation.fromNamespaceAndPath("tfc", "salt_marsh"));
    }
}
