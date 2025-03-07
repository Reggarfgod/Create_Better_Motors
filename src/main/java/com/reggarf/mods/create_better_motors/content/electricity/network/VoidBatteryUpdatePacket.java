//package com.reggarf.mods.create_better_motors.content.electricity.network;
//
//import com.reggarf.mods.create_better_motors.CBMClient;
//import com.reggarf.mods.create_better_motors.content.battery.Accumulator;
//import com.reggarf.mods.create_better_motors.util.MotorNetworkHandler;
//import com.simibubi.create.foundation.networking.SimplePacketBase;
//
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.fml.DistExecutor;
//import net.minecraftforge.network.NetworkEvent;
//
//public class VoidBatteryUpdatePacket extends SimplePacketBase {
//
//	private final MotorNetworkHandler.NetworkKey key;
//	private final Accumulator battery;
//
//	public VoidBatteryUpdatePacket(MotorNetworkHandler.NetworkKey key, Accumulator battery) {
//		this.key = key;
//		this.battery = battery;
//	}
//
//	public VoidBatteryUpdatePacket(FriendlyByteBuf buffer) {
//		key = MotorNetworkHandler.NetworkKey.fromBuffer(buffer);
//		battery = new Accumulator(key);
//		battery.deserializeNBT(buffer.readNbt());
//	}
//
//	@Override
//	public void write(FriendlyByteBuf buffer) {
//		key.writeToBuffer(buffer);
//		buffer.writeNbt(battery.serializeNBT());
//	}
//
//	@Override
//	public boolean handle(NetworkEvent.Context context) {
//		context.enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
//			CBMClient.BATTERIES.storages.put(key, battery)
//		));
//		return true;
//	}
//
//}
