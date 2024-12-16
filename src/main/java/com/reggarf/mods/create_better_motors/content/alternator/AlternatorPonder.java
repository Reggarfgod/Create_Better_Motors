package com.reggarf.mods.create_better_motors.content.alternator;

import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;



public class AlternatorPonder {

	public static void alternator(SceneBuilder scene, SceneBuildingUtil util) {
		scene.title("alternator", "Generating Electric energy using a Alternator");
		scene.configureBasePlate(1, 0, 4);
		scene.world.showSection(util.select.layer(0), Direction.UP);

		BlockPos generator = util.grid.at(3, 1, 2);

		for (int i = 0; i < 6; i++) {
			scene.idle(5);
			scene.world.showSection(util.select.position(i, 1, 2), Direction.DOWN);
			//scene.world.showSection(util.select.position(i, 2, 2), Direction.DOWN);
		}

		scene.idle(10);
		scene.overlay.showText(50)
			.text("The Alternator generates electric energy (fe) from rotational force")
			.placeNearTarget()
			.pointAt(util.vector.topOf(generator));
		scene.idle(60);

		scene.overlay.showText(50)
			.text("It requires atleast 32 RPM to operate")
			.placeNearTarget()
			.pointAt(util.vector.topOf(generator));
		scene.idle(60);


		scene.overlay.showText(50)
		.text("The Alternators energy production is determined by the input RPM")
		.placeNearTarget()
		.pointAt(util.vector.topOf(generator));
		scene.idle(60);
		scene.markAsFinished();
	}
}
