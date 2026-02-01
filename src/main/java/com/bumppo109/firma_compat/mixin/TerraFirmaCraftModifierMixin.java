package com.bumppo109.firma_compat.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import sfiomn.legendarysurvivaloverhaul.common.integration.terrafirmacraft.TerraFirmaCraftModifier;

@Mixin(TerraFirmaCraftModifier.class)
public abstract class TerraFirmaCraftModifierMixin {

    /**
     * Force this method to always return 0.0F — disables TFC temp influence completely.
     */
    @Overwrite
    public float getWorldInfluence(Player player, Level level, BlockPos pos) {
        return 0.0F;
    }

    /**
     * Force this internal helper to always return 0.0F as well (defensive).
     */
    @Overwrite
    public float getUncaughtWorldInfluence(Level level, BlockPos pos) {
        return 0.0F;
    }
}
