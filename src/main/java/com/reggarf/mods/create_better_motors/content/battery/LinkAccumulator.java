package com.reggarf.mods.create_better_motors.content.battery;


import com.reggarf.mods.create_better_motors.Create_better_motors;
import com.reggarf.mods.create_better_motors.config.CBMConfig;
import com.reggarf.mods.create_better_motors.content.electricity.network.CBMPackets;
import com.reggarf.mods.create_better_motors.content.electricity.network.VoidBatteryUpdatePacket;
import com.reggarf.mods.create_better_motors.content.motor.LinkMotorNetworkHandler;
import com.reggarf.mods.create_better_motors.energy.InternalEnergyStorage;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.network.PacketDistributor;

public class LinkAccumulator extends InternalEnergyStorage {

	private final LinkMotorNetworkHandler.NetworkKey key;

	public LinkAccumulator(LinkMotorNetworkHandler.NetworkKey key) {
		super(
				CBMConfig.getCommon().CAPACITY.get(),
				CBMConfig.getCommon().MAX_RECEIVE.get(),
				CBMConfig.getCommon().MAX_EXTRACT.get());

		this.key = key;
	}

	public boolean isEmpty() {
		return energy == 0;
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		int inserted = super.receiveEnergy(maxReceive, simulate);
		if (inserted != 0) onContentsChanged();
		return inserted;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		int extracted = super.extractEnergy(maxExtract, simulate);
		if (extracted != 0) onContentsChanged();
		return extracted;
	}


	private void onContentsChanged() {
		if (Create_better_motors.BATTERIES_DATA != null) Create_better_motors.BATTERIES_DATA.setDirty();
		CBMPackets.channel.send(PacketDistributor.ALL.noArg(), new VoidBatteryUpdatePacket(key, this));
	}

	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		nbt.putLong("Energy", energy);
		return nbt;
	}

	public void deserializeNBT(CompoundTag nbt) {
		energy = nbt.getInt("Energy");
	}

}

