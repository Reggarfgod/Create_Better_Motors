package com.reggarf.mods.create_better_motors.content.battery;


import com.reggarf.mods.create_better_motors.content.motor.LinkMotorNetworkHandler;
import com.reggarf.mods.create_better_motors.tools.VoidStorageData;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

public class LinkAccumulatorData extends VoidStorageData<LinkAccumulator> {

	public LinkAccumulator computeStorageIfAbsent(LinkMotorNetworkHandler.NetworkKey key) {
		return super.computeStorageIfAbsent(key, LinkAccumulator::new);
	}

	@Override
	public @NotNull CompoundTag save(@NotNull CompoundTag tag) {
		return super.save(tag, LinkAccumulator::isEmpty, LinkAccumulator::serializeNBT);
	}

	public static LinkAccumulatorData load(CompoundTag tag) {
		return load(tag, LinkAccumulatorData::new, LinkAccumulator::new, LinkAccumulator::deserializeNBT);
	}

}
