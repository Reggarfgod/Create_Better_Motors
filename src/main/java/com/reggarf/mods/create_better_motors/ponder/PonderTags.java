package com.reggarf.mods.create_better_motors.ponder;

import com.mrh0.createaddition.CreateAddition;

import com.mrh0.createaddition.index.CABlocks;
import com.reggarf.mods.create_better_motors.Create_better_motors;

import com.reggarf.mods.create_better_motors.registry.CBMBlocks;

import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.catnip.platform.CatnipServices;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;



public class PonderTags {

    public static final ResourceLocation KINETIC_SOURCES = loc("kinetic_sources");
    public static final ResourceLocation ELECTRIC = CreateAddition.asResource("electric");

    private static ResourceLocation loc(String id) {
        return Create_better_motors.asResource(id);
    }


    public static void register(PonderTagRegistrationHelper<ResourceLocation> helper) {
        PonderTagRegistrationHelper<RegistryEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);
        PonderTagRegistrationHelper<ItemLike> itemHelper = helper.withKeyFunction(
                CatnipServices.REGISTRIES::getKeyOrThrow);


        HELPER.registerTag(ELECTRIC)
                .addToIndex()
                .item(CBMBlocks.STARTER_MOTOR.get(), true, false)
                .title("Electric Blocks")
                .description("Components which use electricity")
                .register();

        HELPER.addToTag(AllCreatePonderTags.KINETIC_SOURCES)
                .add(CABlocks.ELECTRIC_MOTOR)
                .add(CBMBlocks.STARTER_MOTOR)
                .add(CBMBlocks.BASIC_MOTOR)
                .add(CBMBlocks.HARDENED_MOTOR)
                .add(CBMBlocks.BLAZING_MOTOR)
                .add(CBMBlocks.NIOTIC_MOTOR)
                .add(CBMBlocks.SPIRITED_MOTOR)
                .add(CBMBlocks.NITRO_MOTOR);

        HELPER.addToTag(AllCreatePonderTags.KINETIC_APPLIANCES)
                .add(CBMBlocks.ANDESITE_ALTERNATOR)
                .add(CBMBlocks.COPPER_ALTERNATOR)
                .add(CBMBlocks.BRASS_ALTERNATOR);

        HELPER.addToTag(ELECTRIC)
                .add(CBMBlocks.HEAVY_CONNECTOR)
                .add(CBMBlocks.STARTER_MOTOR)
                .add(CBMBlocks.BASIC_MOTOR)
                .add(CBMBlocks.HARDENED_MOTOR)
                .add(CBMBlocks.BLAZING_MOTOR)
                .add(CBMBlocks.NIOTIC_MOTOR)
                .add(CBMBlocks.SPIRITED_MOTOR)
                .add(CBMBlocks.NITRO_MOTOR)
                .add(CBMBlocks.ANDESITE_ALTERNATOR)
                .add(CBMBlocks.COPPER_ALTERNATOR)
                .add(CBMBlocks.BRASS_ALTERNATOR);

        }

    }

