package com.reggarf.mods.create_better_motors.registry;



import com.reggarf.mods.create_better_motors.content.motors.blocksentity.*;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class CFMCapabilities {
    public static void register(RegisterCapabilitiesEvent event) {

        BasicMotorBlockEntity.registerCapabilitiesbasic(event);
        StarterMotorBlockEntity.registerCapabilitiesstarter(event);
        HardenedMotorBlockEntity.registerCapabilitieshardened(event);
        BlazingMotorBlockEntity.registerCapabilitiesblazing(event);
        NioticMotorBlockEntity.registerCapabilitiesniotic(event);
        SpritedMotorBlockEntity.registerCapabilitiesspirited(event);
        NitroMotorBlockEntity.registerCapabilitiesnitro(event);
    }
}
