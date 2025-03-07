//package com.reggarf.mods.create_better_motors.tools;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//
//import com.simibubi.create.AllPartialModels;
//import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
//
//
//import net.createmod.catnip.render.CachedBuffers;
//import net.createmod.catnip.render.SuperByteBuffer;
//import net.minecraft.client.model.SkullModel;
//import net.minecraft.client.model.SkullModelBase;
//import net.minecraft.client.model.geom.ModelLayers;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
//import net.minecraft.core.Direction;
//import net.minecraft.world.level.block.state.BlockState;
//
//public class MotorRenderer extends KineticBlockEntityRenderer<MotorBlockEntity> implements VoidTileRenderer<MotorBlockEntity> {
//
//	private final SkullModelBase skullModelBase;
//
//	public MotorRenderer(BlockEntityRendererProvider.Context context) {
//		super(context);
//		skullModelBase = new SkullModel(context.getModelSet().bakeLayer(ModelLayers.PLAYER_HEAD));
//	}
//
//	@Override
//	protected void renderSafe(MotorBlockEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
//		super.renderSafe(te, partialTicks, ms, buffer, light, overlay);
//		renderVoid(te, partialTicks, ms, buffer, light, overlay);
//	}
//
//	@Override
//	public SkullModelBase getBase() {
//		return skullModelBase;
//	}
//
//	@Override
//	public boolean shouldRenderFrame(MotorBlockEntity te, Direction direction) {
//		return te.getBlockState().getValue(MotorBlock.FACING) == direction;
//	}
//
//	@Override
//	public float getFrameWidth() {
//		return .375F;
//	}
//
//	@Override
//	public float getFrameOffset(Direction direction) {
//		return .876F;
//	}
//
//	@Override
//	protected SuperByteBuffer getRotatedModel(MotorBlockEntity te, BlockState state) {
//		return CachedBuffers.partialFacing(AllPartialModels.SHAFT_HALF, state);
//	}
//
//}
