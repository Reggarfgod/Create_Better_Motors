package com.reggarf.mods.create_better_motors.registry;


import com.reggarf.mods.create_better_motors.CBMClient;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;


public class CBMClientIniter {
    public static void onInitializeClient(final FMLClientSetupEvent event) {
        CBMClient.onInitializeClient(event);
    }
}
