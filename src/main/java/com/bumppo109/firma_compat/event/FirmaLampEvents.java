package com.bumppo109.firma_compat.event;

import com.bumppo109.firma_compat.item.FirmaLampItem;
import com.bumppo109.firma_compat.data.ModDataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber
public class FirmaLampEvents {

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {

        ItemStack stack = event.getItemStack();

        if (!(stack.getItem() instanceof FirmaLampItem)) {
            return;
        }

        var player = event.getEntity();
        var level = event.getLevel();

        InteractionHand eventHand = event.getHand();

        InteractionHand otherHand =
                eventHand == InteractionHand.MAIN_HAND
                        ? InteractionHand.OFF_HAND
                        : InteractionHand.MAIN_HAND;

        ItemStack eventHandStack = stack;
        ItemStack otherHandStack = player.getItemInHand(otherHand);

        boolean eventHandIgniter = eventHandStack.is(Tags.Items.TOOLS_IGNITER);
        boolean otherHandIgniter = otherHandStack.is(Tags.Items.TOOLS_IGNITER);

        boolean hasIgniter = eventHandIgniter || otherHandIgniter;

        boolean lit = stack.getOrDefault(ModDataComponents.LIT.get(), false);

        // =====================================================
        // EXTINGUISH
        // =====================================================
        if (player.isShiftKeyDown()) {

            if (lit && otherHandStack.isEmpty()) {

                stack.set(ModDataComponents.LIT.get(), false);

                level.playSound(
                        null,
                        player.blockPosition(),
                        SoundEvents.FIRE_EXTINGUISH,
                        player.getSoundSource(),
                        1f,
                        1f
                );

                event.setCancellationResult(InteractionResult.SUCCESS);
                event.setCanceled(true);
            }

            return;
        }

        // =====================================================
        // LIGHT
        // =====================================================
        if (!lit && hasIgniter && ((FirmaLampItem) stack.getItem()).hasFuel(stack)) {

            stack.set(ModDataComponents.LIT.get(), true);

            level.playSound(
                    null,
                    player.blockPosition(),
                    SoundEvents.FLINTANDSTEEL_USE,
                    player.getSoundSource(),
                    1f,
                    1f
            );

            if (player instanceof ServerPlayer serverPlayer) {

                ItemStack igniter =
                        eventHandIgniter ? eventHandStack : otherHandStack;

                if (igniter.isDamageableItem()) {
                    igniter.hurtAndBreak(
                            1,
                            serverPlayer,
                            player.getEquipmentSlotForItem(igniter)
                    );
                }
            }

            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
        }
    }
}