package com.reggarf.mods.create_better_motors.tools;



import com.reggarf.mods.create_better_motors.content.motor.LinkMotorNetworkHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class VoidStorageClient<T> {

	public final Map<LinkMotorNetworkHandler.NetworkKey, T> storages = new HashMap<>();
	private final Function<LinkMotorNetworkHandler.NetworkKey, T> factory;
	public VoidStorageClient(Function<LinkMotorNetworkHandler.NetworkKey, T> factory) {
		this.factory = factory;
	}
	public final T computeStorageIfAbsent(LinkMotorNetworkHandler.NetworkKey key) {
		return storages.computeIfAbsent(key, factory);
	}

}
