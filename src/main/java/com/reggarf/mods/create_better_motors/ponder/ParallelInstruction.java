package com.reggarf.mods.create_better_motors.ponder;

import com.reggarf.mods.create_better_motors.mixin.SceneBuilderAccessor;
import com.simibubi.create.foundation.ponder.PonderScene;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.instruction.PonderInstruction;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParallelInstruction extends PonderInstruction {

	private final List<PonderInstruction> schedule = new ArrayList<>();
	private final List<PonderInstruction> activeSchedule = new ArrayList<>();

	public final ParallelSceneBuilder scene;

	public ParallelInstruction(SceneBuilder scene) {
		PonderScene ponderScene = ((SceneBuilderAccessor) scene).getScene();
		this.scene = new ParallelSceneBuilder(ponderScene, this);
	}

	@Override
	public void reset(PonderScene scene) {
		super.reset(scene);
		activeSchedule.clear();
		schedule.forEach(mdi -> mdi.reset(scene));
	}

	@Override
	public boolean isComplete() {
		return activeSchedule.isEmpty();
	}

	@Override
	public void onScheduled(PonderScene scene) {
		activeSchedule.addAll(schedule);
	}

	@Override
	public void tick(PonderScene scene) {
		for (Iterator<PonderInstruction> iterator = activeSchedule.iterator(); iterator.hasNext();) {
			PonderInstruction instruction = iterator.next();
			instruction.tick(scene);
			if (instruction.isComplete()) {
				iterator.remove();
				if (instruction.isBlocking())
					break;
				continue;
			}
			if (instruction.isBlocking())
				break;
		}
	}

	public void addInstruction(PonderInstruction instruction) {
		schedule.add(instruction);
	}

}
