package com.reggarf.mods.create_better_motors.tools;

import com.jozufozu.flywheel.core.PartialModel;
import com.reggarf.mods.create_better_motors.Create_better_motors;


public class CBMPartialsModels {

	public static final PartialModel VOID_BATTERY_DIAL = block("void_battery/dial");

	private static PartialModel block(String path) {
		return new PartialModel(Create_better_motors.asResource("block/" + path));
	}

	public static void init() {}

}
