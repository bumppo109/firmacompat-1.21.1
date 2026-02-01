package com.bumppo109.firma_compat.event;

import net.dries007.tfc.util.events.StartFireEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber (modid = "firma_compat")
public class CustomBlockLightingHandler {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        Direction face = event.getFace();  // Targeted face
        InteractionHand hand = event.getHand();
        ItemStack held = player.getItemInHand(hand);

        // Client-side skip
        if (level.isClientSide) return;

        // Only proceed if holding a valid TFC igniter
        if (!held.is(Tags.Items.TOOLS_IGNITER)) {
            return;
        }

        BlockState state = level.getBlockState(pos);

        // Check if block can be lit (has 'lit' property and is off)
        if (state.hasProperty(BlockStateProperties.LIT) && !state.getValue(BlockStateProperties.LIT)) {
            // Use TFC's helper method — this posts StartFireEvent internally
            // Use STRONG for normal igniters like flint & steel / firestarter
            boolean litSuccessfully = StartFireEvent.startFireWithSound(
                    level,
                    pos,
                    state,
                    face,
                    player,
                    held
            );

            if (litSuccessfully) {
                // Optional: extra custom logic after successful lighting
                // (e.g., play extra particles, trigger your own advancement)

                // Cancel vanilla block interaction (prevents GUI open, etc.)
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.CONSUME);
            }
            // If not lit (canceled by event or no firepit creation), let other handlers run
        }
    }
}