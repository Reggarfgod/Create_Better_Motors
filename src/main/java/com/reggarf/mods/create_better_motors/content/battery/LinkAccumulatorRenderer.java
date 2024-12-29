package com.reggarf.mods.create_better_motors.content.battery;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.reggarf.mods.create_better_motors.tools.CBMPartialsModels;
import com.reggarf.mods.create_better_motors.tools.VoidTileRenderer;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;

import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3f;

public class LinkAccumulatorRenderer extends SafeBlockEntityRenderer<LinkAccumulatorBlockEntity> implements VoidTileRenderer<LinkAccumulatorBlockEntity> {

	private final SkullModelBase skullModelBase;

	public LinkAccumulatorRenderer(BlockEntityRendererProvider.Context context) {
		skullModelBase = new SkullModel(context.getModelSet().bakeLayer(ModelLayers.PLAYER_HEAD));
	}

	@Override
	protected void renderSafe(LinkAccumulatorBlockEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
		renderVoid(te, partialTicks, ms, buffer, light, overlay);
		renderDial(te, partialTicks, ms, buffer, light, overlay);
	}

	protected void renderDial(LinkAccumulatorBlockEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {

		BlockState state = te.getBlockState();
		VertexConsumer vb = buffer.getBuffer(RenderType.solid());

		LinkAccumulator battery = te.getBattery();
		float progress = (float) battery.getEnergyStored() / battery.getMaxEnergyStored();

		Direction direction = state.getValue(LinkAccumulatorBlock.FACING);
		Vector3f vec = new Vector3f(.5f, .375f, .5f)
				.add(direction.step().mul(.625f));

		ms.pushPose();
		CachedBufferer.partial(CBMPartialsModels.VOID_BATTERY_DIAL, state)
				.translate(vec)
				.rotateY(180 - direction.toYRot())
				.rotateZ(180 * progress)
				.light(light)
				.renderInto(ms, vb);
		ms.popPose();

	}

	@Override
	public SkullModelBase getSkullModelBase() {
		return skullModelBase;
	}

	@Override
	public boolean shouldRenderFrame(LinkAccumulatorBlockEntity te, Direction direction) {
		return false;
	}

	@Override
	public float getFrameWidth() {
		return .0F;
	}

	@Override
	public float getFrameOffset(Direction direction) {
		return .0F;
	}
}
