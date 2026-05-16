package com.bumppo109.firma_compat.item;

import com.bumppo109.firma_compat.util.ModDataComponents;
import net.dries007.tfc.common.items.LampBlockItem;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.util.data.LampFuel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;

public class FirmaLampItem extends LampBlockItem {

    public FirmaLampItem(Properties props, net.dries007.tfc.common.blocks.devices.LampBlock block) {
        super(block, props);
    }

    // =========================================================
    // LIT STATE (ITEM STORAGE ONLY)
    // =========================================================

    public boolean isLit(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.LIT.get(), false);
    }

    public void setLit(ItemStack stack, boolean lit) {
        stack.set(ModDataComponents.LIT.get(), lit);
    }

    // =========================================================
    // FUEL HELPERS (READ ONLY)
    // =========================================================

    public LampFuel getFuel(ItemStack stack) {
        FluidStack fluid = FluidHelpers.getContainedFluid(stack);
        return fluid.isEmpty() ? null : LampFuel.get(fluid.getFluid(), getBlock().defaultBlockState());
    }

    public boolean canLight(ItemStack stack) {
        return !FluidHelpers.getContainedFluid(stack).isEmpty();
    }

    public boolean isInfiniteFuel(ItemStack stack) {
        LampFuel fuel = getFuel(stack);
        return fuel != null && fuel.burnRate() == -1;
    }

    public void light(ItemStack stack) {
        if (canLight(stack)) {
            setLit(stack, true);
        }
    }

    public void extinguish(ItemStack stack) {
        if (!isInfiniteFuel(stack)) {
            setLit(stack, false);
        }
    }

    // =========================================================
    // INVENTORY TICK (CORE NEW LOGIC)
    // =========================================================

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);

        if (level.isClientSide) return;
        if (!(entity instanceof Player)) return;
        if (!isLit(stack)) return;

        LampFuel fuel = getFuel(stack);
        if (fuel == null) {
            setLit(stack, false);
            return;
        }

        // infinite fuel (lava etc)
        if (fuel.burnRate() == -1) return;

        if (fuel.burnRate() <= 0) {
            setLit(stack, false);
            return;
        }

        // tick-based consumption
        if (level.getGameTime() % fuel.burnRate() != 0) return;

        IFluidHandlerItem handler = stack.getCapability(Capabilities.FluidHandler.ITEM);
        if (handler == null) {
            setLit(stack, false);
            return;
        }

        FluidStack drained = handler.drain(1, IFluidHandlerItem.FluidAction.EXECUTE);

        if (drained.isEmpty()) {
            setLit(stack, false);
        }
    }

    // =========================================================
    // PLACEMENT (KEEP TFC LOGIC, ONLY DO STATE SYNC)
    // =========================================================

    @Override
    protected boolean updateCustomBlockEntityTag(
            net.minecraft.core.BlockPos pos,
            Level level,
            Player player,
            ItemStack stack,
            net.minecraft.world.level.block.state.BlockState state
    ) {
        boolean result = super.updateCustomBlockEntityTag(pos, level, player, stack, state);

        // IMPORTANT: only sync BLOCKSTATE, NOT fluid (TFC already handles it)
        level.setBlock(
                pos,
                state.setValue(net.dries007.tfc.common.blocks.devices.LampBlock.LIT, isLit(stack)),
                3
        );

        return result;
    }
}