package com.reggarf.mods.create_better_motors.registry;

import com.reggarf.mods.create_better_motors.CBMClient;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class CBMClientIniter {
    public static void onInitializeClient(final FMLClientSetupEvent event) {
        CBMClient.onInitializeClient(event);
    }
}
