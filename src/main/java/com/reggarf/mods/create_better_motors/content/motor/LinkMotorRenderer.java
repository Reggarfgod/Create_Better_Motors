package com.reggarf.mods.create_better_motors.content.motor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.reggarf.mods.create_better_motors.tools.VoidTileRenderer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;

import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class LinkMotorRenderer extends KineticBlockEntityRenderer<LinkMotorTileEntity> implements VoidTileRenderer<LinkMotorTileEntity> {

	private final SkullModelBase skullModelBase;

	public LinkMotorRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
		skullModelBase = new SkullModel(context.getModelSet().bakeLayer(ModelLayers.PLAYER_HEAD));
	}

	@Override
	protected void renderSafe(LinkMotorTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		super.renderSafe(te, partialTicks, ms, buffer, light, overlay);
		renderVoid(te, partialTicks, ms, buffer, light, overlay);
	}

	@Override
	public SkullModelBase getSkullModelBase() {
		return skullModelBase;
	}

	@Override
	public boolean shouldRenderFrame(LinkMotorTileEntity te, Direction direction) {
		return te.getBlockState().getValue(LinkMotorBlock.FACING) == direction;
	}

	@Override
	public float getFrameWidth() {
		return .375F;
	}

	@Override
	public float getFrameOffset(Direction direction) {
		return .876F;
	}

	@Override
	protected SuperByteBuffer getRotatedModel(LinkMotorTileEntity te, BlockState state) {
		return CachedBufferer.partialFacing(AllPartialModels.SHAFT_HALF, state);
	}

}
