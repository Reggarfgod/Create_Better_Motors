package com.reggarf.mods.create_better_motors.events;

import com.reggarf.mods.create_better_motors.Create_better_motors;
import com.reggarf.mods.create_better_motors.content.battery.LinkAccumulatorData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CBMCommonEvents {

	@SubscribeEvent
	public static void onLoad(LevelEvent.Load event) {

		MinecraftServer server = event.getLevel().getServer();
		if (server == null) return;

		LevelAccessor level = event.getLevel();
		DimensionDataStorage dataStorage = server.overworld().getDataStorage();

		Create_better_motors.MOTOR_LINK_NETWORK_HANDLER.onLoadWorld(level);

		Create_better_motors.BATTERIES_DATA = dataStorage
				.computeIfAbsent(LinkAccumulatorData::load, LinkAccumulatorData::new, "Batteries");
	}

	@SubscribeEvent
	public static void onUnload(LevelEvent.Unload event) {
		Create_better_motors.MOTOR_LINK_NETWORK_HANDLER.onUnloadWorld(event.getLevel());
	}

}
