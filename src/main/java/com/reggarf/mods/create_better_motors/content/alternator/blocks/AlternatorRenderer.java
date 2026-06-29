package com.reggarf.mods.create_better_motors.content.alternator.blocks;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;

import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.createmod.catnip.render.CachedBuffers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class AlternatorRenderer extends KineticBlockEntityRenderer {

	public AlternatorRenderer(Context dispatcher) {
		super(dispatcher);
	}

	@Override
	protected void renderSafe(KineticBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
                              int light, int overlay) {
		if (VisualizationManager.supportsVisualization(be.getLevel())) return;
		BlockState state = be.getBlockState();
		Direction facing = state.getValue(BlockStateProperties.FACING);
		VertexConsumer vb = buffer.getBuffer(RenderType.solid());

		renderRotatingBuffer(be, CachedBuffers.partialFacing(AllPartialModels.SHAFT_HALF, state), ms, vb, light);
		renderRotatingBuffer(be, CachedBuffers.partialFacing(AllPartialModels.SHAFT_HALF,
				state.setValue(BlockStateProperties.FACING, facing.getOpposite())), ms, vb, light);
	}
}