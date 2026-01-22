package com.bumppo109.firma_compat.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber (modid = "firma_compat") // Replace with your mod ID
public class FarmlandEntityPlaceEventHandler {
    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
    /* TODO - might be needed for AstikorCarts
        if (event.getPlacedBlock().is(Blocks.FARMLAND) && !(event.getEntity() instanceof Player)) {
            event.setCanceled(true); // Prevent farmland placement

            BlockPos pos = event.getPos();
            Level level = (Level) event.getLevel();
            // Replace with your custom block (e.g., a modded "enchanted farmland")
            BlockState replacement = OLDModBlocks.FARMLAND.get().defaultBlockState();
            level.setBlock(pos, replacement, 3); // Flags: 3 = update neighbors, no player notify

            // Optional: Play tilling sound/particle effects
            level.levelEvent(2001, pos, Block.getId(replacement)); // Block break sound ID for tilling effect
        }

     */
    }
}
