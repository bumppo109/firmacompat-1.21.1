package com.bumppo109.firma_compat.item;

import com.bumppo109.firma_compat.util.ModDataComponents;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.devices.LampBlock;
import net.dries007.tfc.common.component.fluid.FluidContainerInfo;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.common.items.LampBlockItem;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.data.LampFuel;
import net.dries007.tfc.util.loot.CopyFluidFunction;
import net.dries007.tfc.util.tooltip.Tooltips;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (level.isClientSide) return;

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