//package com.reggarf.mods.create_better_motors.tools;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import com.reggarf.mods.create_better_motors.content.battery.Accumulator;
//import com.reggarf.mods.create_better_motors.content.battery.AccumulatorBlock;
//import com.reggarf.mods.create_better_motors.content.battery.AccumulatorBlockEntity;
//import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
//
//
//import net.createmod.catnip.render.CachedBuffers;
//import net.minecraft.client.model.SkullModel;
//import net.minecraft.client.model.SkullModelBase;
//import net.minecraft.client.model.geom.ModelLayers;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.RenderType;
//import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
//import net.minecraft.core.Direction;
//import net.minecraft.world.level.block.state.BlockState;
//import org.joml.Vector3f;
//
//public class AccumulatorRenderer extends SafeBlockEntityRenderer<AccumulatorBlockEntity> implements VoidTileRenderer<AccumulatorBlockEntity> {
//
//	private final SkullModelBase Base;
//
//	public AccumulatorRenderer(BlockEntityRendererProvider.Context context) {
//		Base = new SkullModel(context.getModelSet().bakeLayer(ModelLayers.PLAYER_HEAD));
//	}
//
//
//	protected void renderDial(AccumulatorBlockEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
//
//		BlockState state = te.getBlockState();
//		VertexConsumer vb = buffer.getBuffer(RenderType.solid());
//
//		Accumulator battery = te.getBattery();
//		float progress = (float) battery.getEnergyStored() / battery.getMaxEnergyStored();
//
//		Direction direction = state.getValue(AccumulatorBlock.FACING);
//		Vector3f vec = new Vector3f(.5f, .375f, .5f)
//				.add(direction.step().mul(.625f));
//
//		ms.pushPose();
//		CachedBuffers.partial(CBMPartialsModels.VOID_BATTERY_DIAL, state)
//				.translate(vec)
//				.rotateY(180 - direction.toYRot())
//				.rotateZ(180 * progress)
//				.light(light)
//				.renderInto(ms, vb);
//		ms.popPose();
//
//	}
//	@Override
//	protected void renderSafe(AccumulatorBlockEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
//		renderVoid(te, partialTicks, ms, buffer, light, overlay);
//		renderDial(te, partialTicks, ms, buffer, light, overlay);
//	}
//	@Override
//	public SkullModelBase getBase() {
//		return Base;
//	}
//
//	@Override
//	public boolean shouldRenderFrame(AccumulatorBlockEntity te, Direction direction) {
//		return false;
//	}
//
//	@Override
//	public float getFrameWidth() {
//		return .0F;
//	}
//
//	@Override
//	public float getFrameOffset(Direction direction) {
//		return .0F;
//	}
//}
