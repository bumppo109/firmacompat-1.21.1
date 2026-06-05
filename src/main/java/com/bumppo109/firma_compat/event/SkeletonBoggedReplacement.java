package com.bumppo109.firma_compat.event;

import com.bumppo109.firma_compat.FirmaCompat;
import net.dries007.tfc.util.EnvironmentHelpers;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Bogged;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Stray;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

@EventBusSubscriber(modid = "firma_compat")
public class SkeletonBoggedReplacement {

    @SubscribeEvent
    public static void replaceSkeletonWithBogged(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) return;
        if (!(event.getEntity() instanceof Skeleton skeleton)) return;
        if (event.loadedFromDisk()) return;

        ServerLevel level = (ServerLevel) event.getLevel();
        BlockPos pos = skeleton.blockPosition();

        ChunkData data = ChunkData.get(level, pos);
        if (data == null) return;

        if (isBoggedClimateValid(level, pos)) {
            event.setCanceled(true);

            Bogged bogged = EntityType.BOGGED.create(level);
            if (bogged != null) {
                createEntity(bogged, skeleton, level, pos);
            }

        } else if (isStrayClimateValid(level, data, pos)) {
            event.setCanceled(true);

            Stray stray = EntityType.STRAY.create(level);
            if (stray != null) {
                createEntity(stray, skeleton, level, pos);
            }
        }
    }

    private static boolean isBoggedClimateValid(ServerLevel level, BlockPos pos) {
        var biome = level.getBiome(pos);

        return biome.is(ResourceLocation.fromNamespaceAndPath("tfc", "lowlands"))
                || biome.is(ResourceLocation.fromNamespaceAndPath("tfc", "salt_marsh"));
    }

    private static boolean isStrayClimateValid(ServerLevel level, ChunkData data, BlockPos pos) {
        float temp = EnvironmentHelpers.adjustAvgTempForElev(
                pos.getY(),
                data.getAverageSeaLevelTemp(pos)
        );

        return temp < 0.0f;
    }

    private static void createEntity(AbstractSkeleton newEntity,
                                     Skeleton oldEntity,
                                     ServerLevel level,
                                     BlockPos pos) {

        newEntity.moveTo(oldEntity.getX(), oldEntity.getY(), oldEntity.getZ(),
                oldEntity.getYRot(), oldEntity.getXRot());

        newEntity.setDeltaMovement(oldEntity.getDeltaMovement());

        newEntity.setBaby(oldEntity.isBaby());
        newEntity.setNoAi(oldEntity.isNoAi());
        newEntity.setSilent(oldEntity.isSilent());
        newEntity.setCustomName(oldEntity.getCustomName());
        newEntity.setCustomNameVisible(oldEntity.isCustomNameVisible());
        newEntity.setPersistenceRequired();

        for (var slot : net.minecraft.world.entity.EquipmentSlot.values()) {
            newEntity.setItemSlot(slot, oldEntity.getItemBySlot(slot).copy());
        }

        oldEntity.getActiveEffects().forEach(newEntity::addEffect);

        newEntity.finalizeSpawn(level, level.getCurrentDifficultyAt(pos),
                MobSpawnType.CONVERSION, null);

        level.addFreshEntity(newEntity);

        level.sendParticles(net.minecraft.core.particles.ParticleTypes.SMOKE,
                newEntity.getX(),
                newEntity.getY() + newEntity.getBbHeight() / 2.0,
                newEntity.getZ(),
                8, 0.3, 0.3, 0.3, 0.02);
    }
}