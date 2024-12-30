package com.reggarf.mods.create_better_motors.ponder;

import com.reggarf.mods.create_better_motors.content.battery.LinkAccumulatorBlockEntity;
import com.reggarf.mods.create_better_motors.content.motor.LinkMotorTileEntity;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.Selection;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.utility.Pointing;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

import java.util.function.Consumer;

public class VoidScenes {

	public static void voidMotor(SceneBuilder scene, SceneBuildingUtil util) {

		scene.title("motor_link", "Motor Link");
		scene.configureBasePlate(0, 0, 5);
		scene.showBasePlate();
		scene.world.showSection(util.select.position(5, 0, 2), Direction.UP);

		Selection source = util.select.fromTo(5, 1, 1, 4, 1, 1);
		Selection receiver = util.select.fromTo(1, 1, 2, 2, 1, 2);

		scene.world.showSection(source, Direction.DOWN);
		scene.idle(10);
		scene.world.showSection(receiver, Direction.DOWN);
		scene.idle(10);

		BlockPos sourcePos = util.grid.at(4, 1, 1);
		BlockPos receiverPos = util.grid.at(1, 1, 2);

		playVoidSequence(
				scene, util, LinkMotorTileEntity.class,
				.015f, 0,
				"Motor Link", "Rotational Force",
				sourcePos, receiverPos,
				Direction.WEST, Direction.WEST,
				(pos) -> scene.world.setKineticSpeed(receiver, 0),
				(pos) -> scene.world.setKineticSpeed(receiver, -32),
				false, false
		);

	}



	public static void voidBattery(SceneBuilder scene, SceneBuildingUtil util) {

		scene.title("accumulator_link", "Accumulator Link");
		scene.showBasePlate();

		BlockPos sourcePos = util.grid.at(3, 1, 2);
		BlockPos receiverPos = util.grid.at(1, 1, 2);

		scene.world.showSection(util.select.position(sourcePos), Direction.DOWN);
		scene.idle(10);
		scene.world.showSection(util.select.position(receiverPos), Direction.DOWN);
		scene.idle(10);

		playVoidSequence(
				scene, util, LinkAccumulatorBlockEntity.class,
				-.0475f, -.1875f,
				"Accumulator Link", "Energy",
				sourcePos, receiverPos,
				Direction.SOUTH, Direction.SOUTH,
				(pos) -> {},
				(pos) -> {},
				true, false
		);

	}

	private static void playVoidSequence(SceneBuilder scene,
										 SceneBuildingUtil util,
										 Class<? extends BlockEntity> beType,
										 float shift, float yOffset,
										 String blockName,
										 String transmittedName,
										 BlockPos firstPos,
										 BlockPos secondPos,
										 Direction firstDirection,
										 Direction secondDirection,
										 Consumer<BlockPos> onDisconnect,
										 Consumer<BlockPos> onConnect,
										 boolean rotate,
										 boolean isTank) {

		Selection firstBlock = util.select.position(firstPos);
		Vec3 firstVec = util.vector.blockSurface(firstPos, firstDirection);

		Selection secondBlock = util.select.position(secondPos);
		Vec3 secondVec = util.vector.blockSurface(secondPos, secondDirection);

		scene.overlay.showText(50)
				.text(blockName + " can transmit " + transmittedName + " across distances")
				.pointAt(firstVec);
		scene.idle(50);

		if (rotate) scene.rotateCameraY(-90);
		scene.addKeyframe();

		Vec3 firstBackFreq = getFirstFrequency(firstVec, firstDirection, shift, yOffset);
		Vec3 firstFrontFreq = getLastFrequency(firstVec, firstDirection, shift, yOffset);
		Vec3 firstOwner = getOwner(firstVec, firstDirection, shift, yOffset);

		Vec3 secondBackFreq = getFirstFrequency(secondVec, secondDirection, shift, yOffset);
		Vec3 secondFrontFreq = getLastFrequency(secondVec, secondDirection, shift, yOffset);

		scene.idle(10);
		scene.overlay.showFilterSlotInput(firstBackFreq, firstDirection, 100);
		scene.overlay.showFilterSlotInput(firstFrontFreq, firstDirection, 100);
		scene.idle(10);

		scene.overlay.showText(50)
				.text("Placing items in the two upper slots can specify a Frequency")
				.placeNearTarget()
				.pointAt(firstFrontFreq);
		scene.idle(60);

		ItemStack iron = new ItemStack(Items.IRON_INGOT);
		ItemStack gold = new ItemStack(Items.GOLD_INGOT);

		showFrequency(scene, firstBlock, beType, firstFrontFreq, "FrequencyLast", Pointing.LEFT, iron);
		onDisconnect.accept(secondPos);
		showFrequency(scene, firstBlock, beType, firstBackFreq, "FrequencyFirst", Pointing.RIGHT, gold);

		if (isTank) onConnect.accept(firstPos);

		scene.idle(30);

		scene.addKeyframe();
		scene.idle(10);
		scene.overlay.showFilterSlotInput(firstOwner, firstDirection, 100);
		scene.idle(10);

		scene.overlay.showControls(new InputWindowElement(firstOwner, Pointing.UP).rightClick(), 40);
		scene.idle(7);
		scene.world.modifyBlockEntityNBT(firstBlock, beType, nbt -> nbt.remove("Owner"));

		scene.overlay.showText(50)
				.text("Right-click the bottom slot to unclaim the " + blockName)
				.placeNearTarget()
				.pointAt(firstOwner);
		scene.idle(60);

		scene.overlay.showControls(new InputWindowElement(firstOwner, Pointing.UP).rightClick(), 40);
		scene.idle(7);
		scene.world.restoreBlocks(firstBlock);
		scene.world.modifyBlockEntityNBT(firstBlock, beType, nbt -> {
			nbt.put("FrequencyFirst", gold.save(new CompoundTag()));
			nbt.put("FrequencyLast", iron.save(new CompoundTag()));
		});

		if (isTank) onConnect.accept(firstPos);

		scene.overlay.showText(50)
				.text("Right-click it again to re-claim it")
				.placeNearTarget()
				.pointAt(firstOwner);
		scene.idle(60);

		scene.overlay.showText(50)
				.text("If a " + blockName + " is claimed, only it's owner is able to edit it's Frequency")
				.placeNearTarget()
				.pointAt(firstVec);
		scene.idle(60);

		scene.addKeyframe();
		scene.idle(10);

		scene.overlay.showText(60)
				.text("A " + blockName + " will only receive " + transmittedName + " from " + blockName + "s with the same Frequency and Owner")
				.placeNearTarget()
				.pointAt(secondVec);
		scene.idle(70);

		showFrequency(scene, secondBlock, beType, secondFrontFreq, "FrequencyLast", Pointing.LEFT, iron);
		showFrequency(scene, secondBlock, beType, secondBackFreq, "FrequencyFirst", Pointing.RIGHT, gold);
		onConnect.accept(secondPos);

		if (rotate) {
			scene.idle(20);
			scene.rotateCameraY(90);
			scene.idle(30);
		} else scene.idle(50);

	}

	private static void showFrequency(SceneBuilder scene,
									  Selection block,
									  Class<? extends BlockEntity> beType,
									  Vec3 slotPos,
									  String slotId,
									  Pointing pointing,
									  ItemStack item) {
		scene.overlay.showControls(new InputWindowElement(slotPos, pointing).withItem(item), 30);
		scene.idle(7);
		scene.world.modifyBlockEntityNBT(block, beType, nbt -> nbt.put(slotId, item.save(new CompoundTag())));
	}

	private static Vec3 getFirstFrequency(Vec3 faceVec, Direction face, float shift, float yOffset) {
		return switch (face) {
			case NORTH -> faceVec.add(.15625f, .15625f + yOffset, -shift);
			case EAST -> faceVec.add(shift, .15625f + yOffset, .15625f);
			case SOUTH -> faceVec.add(-.15625f, .15625f + yOffset, shift);
			case WEST -> faceVec.add(-shift, .15625f + yOffset, -.15625f);
			case UP -> faceVec.add(.15625f + yOffset, shift, -.15625f);
			case DOWN -> faceVec.add(.15625f + yOffset, -shift, .15625f);
		};
	}

	private static Vec3 getLastFrequency(Vec3 faceVec, Direction face, float shift, float yOffset) {
		return switch (face) {
			case NORTH -> faceVec.add(-.15625f, .15625f + yOffset, -shift);
			case EAST -> faceVec.add(shift, .15625f + yOffset, -.15625f);
			case SOUTH -> faceVec.add(.15625f, .15625f + yOffset, shift);
			case WEST -> faceVec.add(-shift, .15625f + yOffset, .15625f);
			case UP -> faceVec.add(.15625f + yOffset, shift, .15625f);
			case DOWN -> faceVec.add(.15625f + yOffset, -shift, -.15625f);
		};
	}

	private static Vec3 getOwner(Vec3 faceVec, Direction face, float shift, float yOffset) {
		return switch (face) {
			case NORTH -> faceVec.add(0, -.15625f + yOffset, -shift);
			case EAST -> faceVec.add(shift, -.15625f + yOffset, 0);
			case SOUTH -> faceVec.add(0, -.15625f + yOffset, shift);
			case WEST -> faceVec.add(-shift, -.15625f + yOffset, 0);
			case UP -> faceVec.add(-.15625f + yOffset, shift, 0);
			case DOWN -> faceVec.add(-.15625f + yOffset, -shift, 0);
		};
	}

}
