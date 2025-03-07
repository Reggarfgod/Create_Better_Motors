package com.reggarf.mods.create_better_motors;



import com.reggarf.mods.create_better_motors.ponder.CECPonderPlugin;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;


import com.tterrag.registrate.util.entry.BlockEntry;
import net.createmod.catnip.config.ui.BaseConfigScreen;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class CBMClient {

//    public static final StorageClient<Accumulator> BATTERIES = new StorageClient<>(
//            Accumulator::new);
//    public static void onCtorClient(IEventBus modEventBus, IEventBus forgeEventBus) {
//        CBMPartialsModels.init();
//    }

    public static void onInitializeClient(final FMLClientSetupEvent event) {
        PonderIndex.addPlugin(new CECPonderPlugin());


//        PonderSceneRegistrationHelper<ItemProviderEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);
//        // ponders
//
//
//        HELPER.addStoryBoard(CBMBlocks.ELECTRICAL_CONNECTOR, "wires", ElectricityPonder::ponder);
//        HELPER.addStoryBoard(CBMItems.COPPER_WIRE, "wires", ElectricityPonder::ponder);
//        HELPER.addStoryBoard(CBMItems.DIAMOND_WIRE, "wires", ElectricityPonder::ponder);
//        HELPER.addStoryBoard(CBMItems.GOLDEN_WIRE, "wires", ElectricityPonder::ponder);
//        HELPER.addStoryBoard(CBMItems.IRON_WIRE, "wires", ElectricityPonder::ponder);
//
//
//        HELPER.forComponents(CBMBlocks.STARTER_MOTOR,
//                        CBMBlocks.BASIC_MOTOR,
//                        CBMBlocks.HARDENED_MOTOR,
//                        CBMBlocks.BLAZING_MOTOR,
//                        CBMBlocks.NIOTIC_MOTOR,
//                        CBMBlocks.SPIRITED_MOTOR,
//                        CBMBlocks.NITRO_MOTOR)
//                .addStoryBoard("motor", MotorPonder::motor);
//        HELPER.forComponents(CBMBlocks.ALTERNATOR)
//                .addStoryBoard("alternator", AlternatorPonder::alternator);

        // ToolTip

        ModContainer modContainer = ModList.get()
                .getModContainerById(Create_better_motors.MOD_ID)
                .orElseThrow(() -> new IllegalStateException("What the..."));

        modContainer.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                        (mc, previousScreen) -> new BaseConfigScreen(previousScreen, Create_better_motors.MOD_ID)));
    }

    public static void addToolTipModifier(BlockEntry<?> entry) {
        TooltipModifier.REGISTRY.register(entry.asItem(), KineticStats.create(entry.asItem()));
    }


}
