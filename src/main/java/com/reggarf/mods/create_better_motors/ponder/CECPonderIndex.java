package com.reggarf.mods.create_better_motors.ponder;


import com.reggarf.mods.create_better_motors.registry.CBMBlocks;
import com.reggarf.mods.create_better_motors.registry.CBMItems;

import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;


public class CECPonderIndex {

    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemProviderEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);
        // ponders
//        HELPER.addStoryBoard(CBMBlocks.MOTOR_CASE, "motor_case", MotorPonder::motorCase);
//        HELPER.addStoryBoard(CBMBlocks.MOTOR_CORE, "motor_core", MotorPonder::motorCore);
//        HELPER.addStoryBoard(CBMBlocks.MOTOR_FRAME, "motor_frame", MotorPonder::motorFrame);
//        HELPER.addStoryBoard(CBMBlocks.MOTOR_MOTOR, "motor_motor", MotorPonder::motorMotor);
//        HELPER.addStoryBoard(CBMBlocks.MOTOR_PLATE, "motor_plate", MotorPonder::motorPlate);
//        HELPER.addStoryBoard(CBMBlocks.MOTOR_PLUG, "motor_plug", MotorPonder::motorPlug);

        HELPER.forComponents(CBMBlocks.ELECTRICAL_CONNECTOR,
                        CBMItems.COPPER_WIRE,
                        CBMItems.DIAMOND_WIRE,
                        CBMItems.GOLDEN_WIRE,
                        CBMItems.IRON_WIRE)
       .addStoryBoard("wires", ElectricityPonder::Electricity);

        HELPER.forComponents(CBMBlocks.STARTER_MOTOR,
                        CBMBlocks.BASIC_MOTOR,
                        CBMBlocks.HARDENED_MOTOR,
                        CBMBlocks.BLAZING_MOTOR,
                        CBMBlocks.NIOTIC_MOTOR,
                        CBMBlocks.SPIRITED_MOTOR,
                        CBMBlocks.NITRO_MOTOR)
                .addStoryBoard("motor", MotorPonder::motor);
        HELPER.forComponents(CBMBlocks.ALTERNATOR)
                .addStoryBoard("alternator", AlternatorPonder::alternator);

    }
}
