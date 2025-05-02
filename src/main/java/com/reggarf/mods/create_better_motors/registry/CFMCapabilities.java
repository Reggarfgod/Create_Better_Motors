package com.reggarf.mods.create_better_motors.registry;



import com.reggarf.mods.create_better_motors.content.alternator.blocksentity.AndesiteAlternatorBlockEntity;
import com.reggarf.mods.create_better_motors.content.alternator.blocksentity.BrassAlternatorBlockEntity;
import com.reggarf.mods.create_better_motors.content.alternator.blocksentity.CopperAlternatorBlockEntity;
import com.reggarf.mods.create_better_motors.content.heavy_connector.HeavyConnectorBlockEntity;
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
        HeavyConnectorBlockEntity.registerCapabilities(event);
        AndesiteAlternatorBlockEntity.registerCapabilities(event);
        BrassAlternatorBlockEntity.registerCapabilities(event);
        CopperAlternatorBlockEntity.registerCapabilities(event);
    }
}
