package com.reggarf.mods.create_better_motors.ponder;


import com.mrh0.createaddition.index.CABlocks;
import com.mrh0.createaddition.ponder.PonderScenes;
import com.reggarf.mods.create_better_motors.registry.CBMBlocks;

import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

import static com.reggarf.mods.create_better_motors.ponder.PonderTags.ELECTRIC;


public class PonderIndex {

    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemProviderEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

        HELPER.forComponents(CBMBlocks.HEAVY_CONNECTOR)
       .addStoryBoard("wires", ElectricityPonder::Electricity, AllCreatePonderTags.KINETIC_SOURCES, ELECTRIC);

        HELPER.forComponents(CBMBlocks.STARTER_MOTOR,
                        CBMBlocks.BASIC_MOTOR,
                        CBMBlocks.HARDENED_MOTOR,
                        CBMBlocks.BLAZING_MOTOR,
                        CBMBlocks.NIOTIC_MOTOR,
                        CBMBlocks.SPIRITED_MOTOR,
                        CBMBlocks.NITRO_MOTOR)
                .addStoryBoard("motor", PonderScenes::electricMotor, AllCreatePonderTags.KINETIC_SOURCES, ELECTRIC);

        HELPER.forComponents(CBMBlocks.ANDESITE_ALTERNATOR,
                        CBMBlocks.COPPER_ALTERNATOR,
                        CBMBlocks.BRASS_ALTERNATOR)
                .addStoryBoard("alternator", PonderScenes::alternator, AllCreatePonderTags.KINETIC_APPLIANCES, ELECTRIC);

    }
}
