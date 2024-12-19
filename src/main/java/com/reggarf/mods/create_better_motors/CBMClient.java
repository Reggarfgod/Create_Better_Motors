package com.reggarf.mods.create_better_motors;

import com.reggarf.mods.create_better_motors.content.alternator.AlternatorPonder;
import com.reggarf.mods.create_better_motors.content.electricity.ElectricityPonder;
import com.reggarf.mods.create_better_motors.content.motors.MotorPonder;
import com.reggarf.mods.create_better_motors.registry.CBMBlocks;
import com.reggarf.mods.create_better_motors.registry.CBMItems;
import com.simibubi.create.foundation.config.ui.BaseConfigScreen;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;

import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;




public class CBMClient {


    public static void onInitializeClient(final FMLClientSetupEvent event) {

        // ponders
        PonderRegistrationHelper helper = new PonderRegistrationHelper(Create_better_motors.MOD_ID);

        var wires = helper.createTag("wiring")
                .item(CBMItems.COPPER_WIRE)
                .addToIndex();

        helper.addStoryBoard(CBMBlocks.ELECTRICAL_CONNECTOR, "wires", ElectricityPonder::ponder);
        helper.addStoryBoard(CBMItems.COPPER_WIRE, "wires", ElectricityPonder::ponder);
        helper.addStoryBoard(CBMItems.DIAMOND_WIRE, "wires", ElectricityPonder::ponder);
        helper.addStoryBoard(CBMItems.GOLDEN_WIRE, "wires", ElectricityPonder::ponder);
        helper.addStoryBoard(CBMItems.IRON_WIRE, "wires", ElectricityPonder::ponder);

        helper.forComponents(CBMBlocks.STARTER_MOTOR,
                        CBMBlocks.BASIC_MOTOR,
                        CBMBlocks.HARDENED_MOTOR,
                        CBMBlocks.BLAZING_MOTOR,
                        CBMBlocks.NIOTIC_MOTOR,
                        CBMBlocks.SPIRITED_MOTOR,
                        CBMBlocks.NITRO_MOTOR)
                .addStoryBoard("motor", MotorPonder::motor);
                  helper.forComponents(CBMBlocks.ALTERNATOR)
                .addStoryBoard("alternator", AlternatorPonder::alternator);

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
