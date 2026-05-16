package com.bumppo109.firma_compat.event;

import com.bumppo109.firma_compat.item.FirmaLampItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber
public class FirmaLampEvents
{
    /*
     * =========================================================
     * LIGHT LAMP IN HAND
     * =========================================================
     */

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event)
    {
        ItemStack lampStack = event.getItemStack();

        if (!(lampStack.getItem() instanceof FirmaLampItem lamp))
        {
            return;
        }

        InteractionHand otherHand =
                event.getHand() == InteractionHand.MAIN_HAND
                        ? InteractionHand.OFF_HAND
                        : InteractionHand.MAIN_HAND;

        ItemStack igniterStack =
                event.getEntity().getItemInHand(otherHand);

        if (!igniterStack.is(Tags.Items.TOOLS_IGNITER))
        {
            return;
        }

        /*
         * Extinguish if sneaking
         */
        if (event.getEntity().isShiftKeyDown())
        {
            if (!lamp.isInfiniteFuel(lampStack))
            {
                if(lamp.isLit(lampStack)) {
                    lamp.extinguish(lampStack);

                    event.getLevel().playSound(
                            null,
                            event.getPos(),
                            SoundEvents.FIRE_EXTINGUISH,
                            event.getEntity().getSoundSource(),
                            1f,
                            1f
                    );
                }
            }

            return;
        }

        /*
         * Light lamp
         */
        if (!lamp.isLit(lampStack) && lamp.canLight(lampStack))
        {
            lamp.light(lampStack);

            event.getLevel().playSound(
                    null,
                    event.getPos(),
                    SoundEvents.FLINTANDSTEEL_USE,
                    event.getEntity().getSoundSource(),
                    1f,
                    1f
            );

            /*
             * Damage flint and steel
             */
            if (igniterStack.isDamageableItem())
            {
                igniterStack.hurtAndBreak(
                        1,
                        (ServerPlayer) event.getEntity(),
                        event.getEntity().getEquipmentSlotForItem(igniterStack)
                );
            }
        }
    }
}