//package com.reggarf.mods.create_better_motors.content.motor;
//
//import com.reggarf.mods.create_better_motors.tools.voidlink.LinkSlot;
//import com.simibubi.create.content.kinetics.base.IRotate;
//import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
//import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
//
//
//import net.createmod.catnip.math.VecHelper;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.level.block.entity.BlockEntityType;
//import net.minecraft.world.level.block.state.BlockState;
//import org.apache.commons.lang3.tuple.Triple;
//
//import java.util.List;
//
//public class MotorBlockEntity extends KineticBlockEntity {
//
//	MotorBehaviour link;
//
//	public MotorBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
//		super(typeIn, pos, state);
//	}
//
//	@Override
//	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
//		createLink();
//		behaviours.add(link);
//	}
//
//	public void createLink() {
//
//		Triple<LinkSlot, LinkSlot, LinkSlot> slots = LinkSlot.makeSlots(
//				index -> new LinkSlot(index,
//						state -> state.getValue(MotorBlock.FACING),
//						VecHelper.voxelSpace(5.5F, 10.5F, -.001F)));
//
//		link = new MotorBehaviour(this, slots);
//
//	}
//
//	public void onConnectToVoidNetwork() {
//		attachKinetics();
//	}
//
//	public void onDisconnectFromVoidNetwork() {
//		detachKinetics();
//		removeSource();
//	}
//
//
//	@Override
//	public List<BlockPos> addPropagationLocations(IRotate block, BlockState state, List<BlockPos> neighbours) {
//		neighbours.addAll(link.getNetwork());
//		return neighbours;
//	}
//
//	@Override
//	public float propagateRotationTo(KineticBlockEntity target, BlockState stateFrom, BlockState stateTo, BlockPos diff, boolean connectedViaAxes, boolean connectedViaCogs) {
//		MotorBehaviour targetLink = (MotorBehaviour) BlockEntityBehaviour.get(target, com.reggarf.mods.create_better_motors.content.motor.MotorBehaviour.TYPE);
//		if (targetLink != null) return targetLink.getNetworkKey().equals(link.getNetworkKey()) ? 1 : 0;
//		return 0;
//	}
//
//	@Override
//	protected boolean isNoisy() {
//		return false;
//	}
//
//}
