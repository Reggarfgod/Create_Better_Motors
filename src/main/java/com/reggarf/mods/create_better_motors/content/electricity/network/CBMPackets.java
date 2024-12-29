package com.reggarf.mods.create_better_motors.content.electricity.network;

import com.reggarf.mods.create_better_motors.Create_better_motors;
import com.simibubi.create.foundation.networking.SimplePacketBase;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public enum CBMPackets {


	VOID_BATTERY_UPDATE(VoidBatteryUpdatePacket.class, VoidBatteryUpdatePacket::new, NetworkDirection.PLAY_TO_CLIENT);

	public static final ResourceLocation CHANNEL_NAME = Create_better_motors.asResource("main");
	public static final int NETWORK_VERSION = 2;
	public static final String NETWORK_VERSION_STR = String.valueOf(NETWORK_VERSION);
	public static SimpleChannel channel;

	private final LoadedPacket<?> packet;

	<T extends SimplePacketBase> CBMPackets(Class<T> type, Function<FriendlyByteBuf, T> factory,
											NetworkDirection direction) {
		packet = new LoadedPacket<>(type, factory, direction);
	}

	public static void registerPackets() {
		channel = NetworkRegistry.ChannelBuilder.named(CHANNEL_NAME)
				.serverAcceptedVersions(NETWORK_VERSION_STR::equals)
				.clientAcceptedVersions(NETWORK_VERSION_STR::equals)
				.networkProtocolVersion(() -> NETWORK_VERSION_STR)
				.simpleChannel();
		for (CBMPackets packet : values())
			packet.packet.register();
	}

	private static class LoadedPacket<T extends SimplePacketBase> {
		private static int index = 0;

		private BiConsumer<T, FriendlyByteBuf> encoder;
		private Function<FriendlyByteBuf, T> decoder;
		private BiConsumer<T, Supplier<NetworkEvent.Context>> handler;
		private Class<T> type;
		private NetworkDirection direction;

		private LoadedPacket(Class<T> type, Function<FriendlyByteBuf, T> factory, NetworkDirection direction) {
			encoder = T::write;
			decoder = factory;
			handler = (packet, contextSupplier) -> {
				NetworkEvent.Context context = contextSupplier.get();
				if (packet.handle(context)) {
					context.setPacketHandled(true);
				}
			};
			this.type = type;
			this.direction = direction;
		}

		private void register() {
			channel.messageBuilder(type, index++, direction)
					.encoder(encoder)
					.decoder(decoder)
					.consumerNetworkThread(handler)
					.add();
		}

	}

}
