package com.reggarf.mods.create_better_motors.ponder;

import com.reggarf.mods.create_better_motors.registry.CBMBlocks;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.kinetics.crank.ValveHandleBlock;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlock;
import com.simibubi.create.content.kinetics.gauge.GaugeBlock;
import com.simibubi.create.content.kinetics.gauge.StressGaugeBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.CogWheelBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogwheelBlock;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedShaftBlock;
import com.simibubi.create.content.kinetics.transmission.sequencer.SequencedGearshiftBlock;
import com.simibubi.create.content.kinetics.waterwheel.WaterWheelBlockEntity;
import com.simibubi.create.content.redstone.nixieTube.NixieTubeBlock;
import com.simibubi.create.content.redstone.nixieTube.NixieTubeBlockEntity;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;

import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.WorldSectionElement;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Vec3i;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class KineticsScenes {


	public static void multimeter(SceneBuilder scene, SceneBuildingUtil util) {
		gauge(scene, util, true);
	}
	private static void gauge(SceneBuilder builder, SceneBuildingUtil util, boolean speed) {
		CreateSceneBuilder scene = new CreateSceneBuilder(builder);
		String component = speed ? "Speedometer" : "Stressometer";
		String title = "Monitoring Kinetic information using the " + component;
		scene.title(speed ? "speedometer" : "stressometer", title);
		scene.configureBasePlate(1, 0, 5);

		BlockPos gaugePos = util.grid().at(2, 1, 3);

		scene.world().showSection(util.select().layer(0), Direction.UP);
		scene.idle(5);

		for (int x = 6; x >= 0; x--) {
			scene.idle(2);
			scene.world().showSection(util.select().position(x, 1, 3), Direction.DOWN);
		}
		scene.idle(10);

		scene.world().setBlock(gaugePos, (speed ? CBMBlocks.MULTIMETER : CBMBlocks.MULTIMETER ).getDefaultState()
			.setValue(GaugeBlock.FACING, Direction.UP), true);
		scene.world().setKineticSpeed(util.select().position(gaugePos), 32);
		scene.idle(10);

		scene.overlay().showText(80)
			.text("The " + component + " displays the current " + (speed ? "Speed" : "Stress Capacity")
				+ (speed ? " of attached components" : " of the attached kinetic network"))
			.attachKeyFrame()
			.pointAt(util.vector().topOf(gaugePos))
			.placeNearTarget();
		scene.idle(90);

		if (speed) {
			scene.world().multiplyKineticSpeed(util.select().everywhere(), 4);
			scene.effects().rotationSpeedIndicator(util.grid().at(6, 1, 3));
			scene.idle(5);
			scene.effects().indicateSuccess(gaugePos);

		} else {
			BlockState state = AllBlocks.CRUSHING_WHEEL.getDefaultState()
				.setValue(CrushingWheelBlock.AXIS, Axis.X);
			scene.world().setBlock(util.grid().at(5, 1, 3), state, true);
			scene.world().setKineticSpeed(util.select().position(5, 1, 3), 32);
			scene.world().modifyBlockEntityNBT(util.select().position(gaugePos), StressGaugeBlockEntity.class,
				nbt -> nbt.putFloat("Value", .5f));
			scene.effects().indicateRedstone(gaugePos);
			scene.idle(20);
			scene.world().setBlock(util.grid().at(4, 1, 3), state, true);
			scene.world().setKineticSpeed(util.select().position(4, 1, 3), 32);
			scene.world().modifyBlockEntityNBT(util.select().position(gaugePos), StressGaugeBlockEntity.class,
				nbt -> nbt.putFloat("Value", .9f));
			scene.effects().indicateRedstone(gaugePos);
			scene.idle(10);
		}

		scene.idle(30);

		Vec3 blockSurface = util.vector().blockSurface(gaugePos, Direction.NORTH);
		scene.overlay().showControls(blockSurface, Pointing.RIGHT, 80).withItem(AllItems.GOGGLES.asStack());
		scene.idle(7);
		scene.overlay().showText(80)
			.text("When wearing Engineers' Goggles, the player can get more detailed information from the Gauge")
			.attachKeyFrame()
			.colored(PonderPalette.MEDIUM)
			.pointAt(blockSurface)
			.placeNearTarget();
		scene.idle(100);

		Selection comparator = util.select().fromTo(2, 1, 1, 2, 1, 2);
		scene.world().showSection(comparator, Direction.SOUTH);
		scene.idle(10);
		scene.world().toggleRedstonePower(comparator);
		scene.effects().indicateRedstone(util.grid().at(2, 1, 2));
		scene.idle(20);

		scene.overlay().showText(120)
			.text("Comparators can emit analog Restone Signals relative to the " + component + "'s measurements")
			.attachKeyFrame()
			.colored(PonderPalette.RED)
			.pointAt(util.vector().centerOf(2, 1, 2)
				.add(0, -0.35, 0))
			.placeNearTarget();
		scene.idle(130);
		scene.markAsFinished();
	}

}
