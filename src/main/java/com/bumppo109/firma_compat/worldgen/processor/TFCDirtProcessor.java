package com.bumppo109.firma_compat.worldgen.processor;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nullable;

//TODO - this correctly turns any blocks tagged DIRT to the correct TFC dirt block
public class TFCDirtProcessor extends StructureProcessor
{
    public static final MapCodec<TFCDirtProcessor> CODEC = MapCodec.unit(TFCDirtProcessor::new);

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo processBlock(
            LevelReader level,
            BlockPos structureOffset,
            BlockPos piecePos,
            StructureTemplate.StructureBlockInfo original,
            StructureTemplate.StructureBlockInfo transformed,
            StructurePlaceSettings settings
    )
    {
        BlockState state = transformed.state();

        if (!state.is(BlockTags.DIRT))
        {
            return transformed;
        }

        BlockState replacement =
                TFCSoilResolver.getNaturalDirt(
                        level,
                        transformed.pos()
                );

        return new StructureTemplate.StructureBlockInfo(
                transformed.pos(),
                replacement,
                transformed.nbt()
        );
    }

    @Override
    protected StructureProcessorType<?> getType()
    {
        return ModStructureProcessors.TFC_DIRT.get();
    }
}