package com.reggarf.mods.create_better_motors.ponder;

import com.simibubi.create.foundation.ponder.PonderScene;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.instruction.PonderInstruction;

public class ParallelSceneBuilder extends SceneBuilder {

	private final ParallelInstruction instruction;

	public ParallelSceneBuilder(PonderScene ponderScene, ParallelInstruction instruction) {
		super(ponderScene);
		this.instruction = instruction;
	}

	@Override
	public void addInstruction(PonderInstruction instruction) {
		this.instruction.addInstruction(instruction);
	}
}
