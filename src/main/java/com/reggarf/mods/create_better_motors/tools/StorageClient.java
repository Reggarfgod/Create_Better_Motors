//package com.reggarf.mods.create_better_motors.tools;
//
//
//
//import com.reggarf.mods.create_better_motors.util=.MotorNetworkHandler;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//@OnlyIn(Dist.CLIENT)
//public class StorageClient<T> {
//
//	public final Map<MotorNetworkHandler.NetworkKey, T> storages = new HashMap<>();
//	private final Function<MotorNetworkHandler.NetworkKey, T> factory;
//	public StorageClient(Function<MotorNetworkHandler.NetworkKey, T> factory) {
//		this.factory = factory;
//	}
//	public final T computeStorageIfAbsent(MotorNetworkHandler.NetworkKey key) {
//		return storages.computeIfAbsent(key, factory);
//	}
//
//}
