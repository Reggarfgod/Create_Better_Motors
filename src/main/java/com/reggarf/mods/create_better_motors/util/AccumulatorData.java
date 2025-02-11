package com.reggarf.mods.create_better_motors.util;


import com.reggarf.mods.create_better_motors.content.battery.Accumulator;
import com.reggarf.mods.create_better_motors.tools.VoidStorageData;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

public class AccumulatorData extends VoidStorageData<Accumulator> {

	public Accumulator computeStorageIfAbsent(MotorNetworkHandler.NetworkKey key) {
		return super.computeStorageIfAbsent(key, Accumulator::new);
	}

	@Override
	public @NotNull CompoundTag save(@NotNull CompoundTag tag) {
		return super.save(tag, Accumulator::isEmpty, Accumulator::serializeNBT);
	}

	public static AccumulatorData load(CompoundTag tag) {
		return load(tag, AccumulatorData::new, Accumulator::new, Accumulator::deserializeNBT);
	}

}
