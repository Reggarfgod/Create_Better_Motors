package com.reggarf.mods.create_better_motors.tools;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;

import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class CBMHalfShaftRenderer extends KineticBlockEntityRenderer {
    public CBMHalfShaftRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected SuperByteBuffer getRotatedModel(KineticBlockEntity blockEntity, BlockState blockState) {
        return CachedBuffers.partialFacing(AllPartialModels.SHAFT_HALF, blockState);
    }

}
