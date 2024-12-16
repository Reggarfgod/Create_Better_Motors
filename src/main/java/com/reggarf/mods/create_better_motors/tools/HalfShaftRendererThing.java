package com.reggarf.mods.create_better_motors.tools;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class HalfShaftRendererThing extends KineticBlockEntityRenderer {
    public HalfShaftRendererThing(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected SuperByteBuffer getRotatedModel(KineticBlockEntity blockEntity, BlockState blockState) {
        return CachedBufferer.partialFacing(AllPartialModels.SHAFT_HALF, blockState);
    }

}
