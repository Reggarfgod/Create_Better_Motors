package com.reggarf.mods.create_better_motors.content.electricity.network;

import com.reggarf.mods.create_better_motors.CBMClient;
import com.reggarf.mods.create_better_motors.content.battery.LinkAccumulator;
import com.reggarf.mods.create_better_motors.content.motor.LinkMotorNetworkHandler;
import com.simibubi.create.foundation.networking.SimplePacketBase;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public class VoidBatteryUpdatePacket extends SimplePacketBase {

	private final LinkMotorNetworkHandler.NetworkKey key;
	private final LinkAccumulator battery;

	public VoidBatteryUpdatePacket(LinkMotorNetworkHandler.NetworkKey key, LinkAccumulator battery) {
		this.key = key;
		this.battery = battery;
	}

	public VoidBatteryUpdatePacket(FriendlyByteBuf buffer) {
		key = LinkMotorNetworkHandler.NetworkKey.fromBuffer(buffer);
		battery = new LinkAccumulator(key);
		battery.deserializeNBT(buffer.readNbt());
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		key.writeToBuffer(buffer);
		buffer.writeNbt(battery.serializeNBT());
	}

	@Override
	public boolean handle(NetworkEvent.Context context) {
		context.enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
			CBMClient.VOID_BATTERIES.storages.put(key, battery)
		));
		return true;
	}

}
