package com.reggarf.mods.create_better_motors.ponder;

import com.reggarf.mods.create_better_motors.Create_better_motors;

import com.reggarf.mods.create_better_motors.registry.CBMBlocks;
import com.reggarf.mods.create_better_motors.registry.CBMItems;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.catnip.platform.CatnipServices;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;


import static com.simibubi.create.infrastructure.ponder.AllCreatePonderTags.DECORATION;


public class CECPonderTags {

    public static final ResourceLocation KINETIC_SOURCES = loc("kinetic_sources");

    private static ResourceLocation loc(String id) {
        return Create_better_motors.asResource(id);
    }


    public static void register(PonderTagRegistrationHelper<ResourceLocation> helper) {
        PonderTagRegistrationHelper<RegistryEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);
        PonderTagRegistrationHelper<ItemLike> itemHelper = helper.withKeyFunction(
                CatnipServices.REGISTRIES::getKeyOrThrow);

        HELPER.addToTag(KINETIC_SOURCES)
                .add(CBMBlocks.ELECTRICAL_CONNECTOR)
                .add(CBMItems.COPPER_WIRE)
                .add(CBMItems.DIAMOND_WIRE)
                .add(CBMItems.GOLDEN_WIRE)
                .add(CBMItems.IRON_WIRE)
                .add(CBMBlocks.STARTER_MOTOR)
                .add(CBMBlocks.BASIC_MOTOR)
                .add(CBMBlocks.HARDENED_MOTOR)
                .add(CBMBlocks.BLAZING_MOTOR)
                .add(CBMBlocks.NIOTIC_MOTOR)
                .add(CBMBlocks.SPIRITED_MOTOR)
                .add(CBMBlocks.NITRO_MOTOR)
                .add(CBMBlocks.ALTERNATOR)
        ;

        }

    }

