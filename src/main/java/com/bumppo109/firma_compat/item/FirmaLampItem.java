package com.bumppo109.firma_compat.item;

import com.bumppo109.firma_compat.data.ModDataComponents;
import net.dries007.tfc.common.blocks.devices.LampBlock;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.common.items.LampBlockItem;
import net.dries007.tfc.util.data.LampFuel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public class FirmaLampItem extends LampBlockItem {

    public FirmaLampItem(Block block, Properties properties) {
        super(block, properties);
    }

    public boolean isLit(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.LIT.get(), false);
    }

    public void setLit(ItemStack stack, boolean lit) {
        stack.set(ModDataComponents.LIT.get(), lit);
    }

    public boolean hasFuel(ItemStack stack) {
        FluidStack fluid = FluidHelpers.getContainedFluid(stack);
        if (fluid.isEmpty()) {
            return false;
        }

        LampFuel fuel = LampFuel.get(
                fluid.getFluid(),
                ((LampBlock) getBlock()).defaultBlockState()
        );

        return fuel != null && fluid.getAmount() > 0;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (level.isClientSide) return;

        if (isLit(stack) && !hasFuel(stack)) {
            setLit(stack, false);           // Auto extinguish
            return;
        }

        if(isLit(stack)) {
            IFluidHandler handler = (IFluidHandler)stack.getCapability(Capabilities.FluidHandler.ITEM);
            if (handler == null) return;

            FluidStack fluid = FluidHelpers.getContainedFluid(stack);
            if (fluid.isEmpty()) return;

            //Determine fuel rules from TFC
            LampFuel fuel = LampFuel.get(
                    fluid.getFluid(),
                    ((LampBlock) getBlock()).defaultBlockState()
            );

            if (fuel == null) return;

            int burnRate = fuel.burnRate();
            if (burnRate <= 0) return;

            long time = level.getGameTime();

            if (time % burnRate == 0) {
                handler.drain(1, IFluidHandler.FluidAction.EXECUTE);
            }
        }
    }
}