package com.reggarf.mods.create_better_motors.tools;

import com.reggarf.mods.create_better_motors.Create_better_motors;
import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;
import com.simibubi.create.foundation.block.connected.CTType;

import static com.simibubi.create.foundation.block.connected.AllCTTypes.*;


public class CBMSpriteShifts {

	private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName) {
		return getCT(type, blockTextureName, blockTextureName);
	}
	private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName, String connectedTextureName) {
		return CTSpriteShifter.getCT(type,
				Create_better_motors.asResource("block/" + blockTextureName),
				Create_better_motors.asResource("block/" + connectedTextureName + "_connected"));
	}

}
