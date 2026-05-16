package com.bumppo109.firma_compat.loot;

import com.bumppo109.firma_compat.util.ModDataComponents;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dries007.tfc.common.blocks.devices.LampBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;

public class CopyLampStateFunction extends LootItemConditionalFunction
{
    public static final MapCodec<CopyLampStateFunction> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    commonFields(instance).apply(instance, CopyLampStateFunction::new)
            );

    public CopyLampStateFunction(List<LootItemCondition> predicates)
    {
        super(predicates);
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext context)
    {
        BlockState state =
                context.getParamOrNull(LootContextParams.BLOCK_STATE);

        if (state != null && state.hasProperty(LampBlock.LIT))
        {
            stack.set(
                    ModDataComponents.LIT.get(),
                    state.getValue(LampBlock.LIT)
            );
        }

        return stack;
    }

    @Override
    public LootItemFunctionType<CopyLampStateFunction> getType()
    {
        return ModLootFunctions.COPY_LAMP_STATE.get();
    }
}