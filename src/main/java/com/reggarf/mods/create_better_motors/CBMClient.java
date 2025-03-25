package com.reggarf.mods.create_better_motors;



import com.reggarf.mods.create_better_motors.ponder.PonderPlugin;
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

    public static void onInitializeClient(final FMLClientSetupEvent event) {
        PonderIndex.addPlugin(new PonderPlugin());

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
