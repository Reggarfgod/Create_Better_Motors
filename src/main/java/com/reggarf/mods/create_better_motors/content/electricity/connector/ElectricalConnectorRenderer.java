package com.reggarf.mods.create_better_motors.content.electricity.connector;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.reggarf.mods.create_better_motors.config.CBMConfig;
import com.reggarf.mods.create_better_motors.registry.CBMRenderTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import com.reggarf.mods.create_better_motors.content.electricity.wire.ElectricWireItem;
import com.reggarf.mods.create_better_motors.content.electricity.wire.WireType;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.Map;

import static com.reggarf.mods.create_better_motors.tools.RaycastUtil.pickBlockFromPos;


public class ElectricalConnectorRenderer implements BlockEntityRenderer<ElectricalConnectorBlockEntity> {
    public static final float SAG_FACTOR = 0.92f;

    public static final int[] TOO_LONG1 = { 150, 0, 0, 255 };
    public static final int[] TOO_LONG2 = { 204, 0, 0, 255 };

    public ElectricalConnectorRenderer(BlockEntityRendererProvider.Context context) {
        super();
    }

    @Override
    public void render(ElectricalConnectorBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        VertexConsumer consumer = buffer.getBuffer(CBMRenderTypes.WIRE);
        BlockPos pos = blockEntity.getBlockPos();
        Vector3f from = new Vector3f(0.0f);

        for (Map.Entry<BlockPos, WireType> e : blockEntity.getConnectorPositions().entrySet()) {
            if (e.getKey().hashCode() > blockEntity.getBlockPos().hashCode())
                continue;

            poseStack.pushPose();

            poseStack.translate(0.5f, 0.5f, 0.5f);
            Matrix4f pose = poseStack.last().pose();

            Vector3f to = new Vector3f(
                    e.getKey().getX() - pos.getX(),
                    e.getKey().getY() - pos.getY(),
                    e.getKey().getZ() - pos.getZ()
            );

            renderWire(consumer, pose, from, to, blockEntity, e.getValue().getColor1(), e.getValue().getColor2());

            poseStack.popPose();
        }

        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null && Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
            ItemStack itemInHand = player.getMainHandItem();

            if (!(itemInHand.getItem() instanceof ElectricWireItem))
                itemInHand = player.getOffhandItem();

            if (itemInHand.getItem() instanceof ElectricWireItem wire) {
                BlockPos bound = wire.getBoundConnector(itemInHand);

                if (bound != null && bound.equals(blockEntity.getBlockPos())) {
                    Vec3 eyePos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
                    Vec3 endPos = eyePos.add(player.getViewVector(partialTick).normalize().scale(2.0f));

                    HitResult hit = pickBlockFromPos(blockEntity.getLevel(), eyePos, player.getViewVector(partialTick), Minecraft.getInstance().gameMode.getPickRange());

                    if (hit instanceof BlockHitResult blockHit) {
                        Vec3 vec = eyePos.add(blockHit.getLocation().subtract(eyePos).scale(0.9f));

                        if (eyePos.distanceTo(endPos) > eyePos.distanceTo(vec))
                            endPos = vec;
                    }

                    Vector3f to = new Vector3f(
                            (float) (endPos.x - pos.getX() - 0.5),
                            (float) (endPos.y - pos.getY() - 0.5),
                            (float) (endPos.z - pos.getZ() - 0.5)
                    );

                    double distance = endPos.distanceToSqr(bound.getX() + 0.5, bound.getY() + 0.5, bound.getZ() + 0.5);
                    int maxDistance = CBMConfig.getCommon().maxWireLength.get();

                    if (distance > Mth.square(maxDistance * 2))
                        return;

                    int[] color1 = wire.getWireType().getColor1();
                    int[] color2 = wire.getWireType().getColor2();

                    if (Minecraft.getInstance().gameMode != null && hit instanceof BlockHitResult blockHit) {
                        if (blockEntity.getLevel().getBlockEntity(blockHit.getBlockPos()) instanceof ElectricalConnectorBlockEntity connector) {
                            if (connector.isConnected(blockEntity.getBlockPos()))
                                return;

                            to = new Vector3f(
                                    blockHit.getBlockPos().getX() - pos.getX(),
                                    blockHit.getBlockPos().getY() - pos.getY(),
                                    blockHit.getBlockPos().getZ() - pos.getZ()
                            );

                            distance = connector.getBlockPos().distSqr(blockEntity.getBlockPos());
                        }
                    }

                    if (distance >= Mth.square(maxDistance)) {
                        color1 = TOO_LONG1;
                        color2 = TOO_LONG2;
                    }

                    poseStack.pushPose();

                    poseStack.translate(0.5f, 0.5f, 0.5f);
                    Matrix4f pose = poseStack.last().pose();

                    renderWire(consumer, pose, to, from, blockEntity, color1, color2);

                    poseStack.popPose();
                }
            }
        }
    }

    private void renderWire(VertexConsumer consumer, Matrix4f pose, Vector3f from, Vector3f to, ElectricalConnectorBlockEntity blockEntity, int[] color1, int[] color2) {
        Vector3f lastSection = from;
        Vector3f direction = new Vector3f(to).sub(from).normalize();
        float distance = to.distance(from);
        int sections = (int) Math.ceil(distance * CBMConfig.getClient().wireSectionsPerMeter.get());
        float perSection = distance / sections;

        for (int i = 0; i <= sections; i++) {
            int[] color = (i % 2 == 0) ? color1 : color2;
            Vector3f sectionTo = new Vector3f(direction).mul(perSection * i).add(0.0f, catenary(i, distance, sections), 0.0f);
            wireSection(consumer, pose, lastSection, sectionTo.add(from), color, calculateLighting(blockEntity, lastSection, sectionTo));
            lastSection = sectionTo;
        }
    }

    @Override
    public boolean shouldRenderOffScreen(ElectricalConnectorBlockEntity blockEntity) {
        return true;
    }

    private int calculateLighting(BlockEntity entity, Vector3f pos, Vector3f pos1) {
        BlockPos blockPos = new BlockPos(entity.getBlockPos()).offset(Math.round(pos.x()), Math.round(pos.y()), Math.round(pos.z()));
        BlockPos blockPos1 = new BlockPos(entity.getBlockPos()).offset(Math.round(pos1.x()), Math.round(pos1.y()), Math.round(pos1.z()));

        int sky = entity.getLevel().getBrightness(LightLayer.SKY, blockPos);
        int block = entity.getLevel().getBrightness(LightLayer.BLOCK, blockPos);
        int sky1 = entity.getLevel().getBrightness(LightLayer.SKY, blockPos1);
        int block1 = entity.getLevel().getBrightness(LightLayer.BLOCK, blockPos1);

        return LightTexture.pack(Math.max(block, block1), Math.max(sky, sky1));
    }

    private float catenary(double x, double length, int sections) {
        double a = length / CBMConfig.getCommon().maxWireLength.get() * SAG_FACTOR;
        x = (x / sections * 2 - 1);
        return (float) ((Math.cosh(x) - Math.cosh(1.0f)) * a);
    }

    private void wireSection(VertexConsumer consumer, Matrix4f pose, Vector3f from, Vector3f to, int[] color, int light) {
        Vector3f direction = new Vector3f(to).sub(from).normalize();
        Vector3f up = new Vector3f(0.0f, 1.0f, 0.0f);

        if (isVertical(direction, up))
            up = new Vector3f(1.0f, 0.0f, 0.0f);

        pose = new Matrix4f(pose)
                .translate(from)
                .rotateTowards(direction, up);

        int r = color[0];
        int g = color[1];
        int b = color[2];
        int z = color[3];

        float f = CBMConfig.getClient().wireThickness.get().floatValue() / 2;
        float distance = from.distance(to);

        consumer.vertex(pose, -f, -f, 0.0f).color(r, g, b, z).uv2(light).endVertex();
        consumer.vertex(pose, -f, -f, distance).color(r, g, b, z).uv2(light).endVertex();
        consumer.vertex(pose, f, f, distance).color(r, g, b, z).uv2(light).endVertex();
        consumer.vertex(pose, f, f, 0.0f).color(r, g, b, z).uv2(light).endVertex();

        consumer.vertex(pose, f, -f, 0.0f).color(r, g, b, z).uv2(light).endVertex();
        consumer.vertex(pose, f, -f, distance).color(r, g, b, z).uv2(light).endVertex();
        consumer.vertex(pose, -f, f, distance).color(r, g, b, z).uv2(light).endVertex();
        consumer.vertex(pose, -f, f, 0.0f).color(r, g, b, z).uv2(light).endVertex();
    }

    private boolean isVertical(Vector3f vec, Vector3f up) {
        return vec.equals(up, 0.001f) || vec.equals(up.mul(-1.0f), 0.001f);
    }
}
