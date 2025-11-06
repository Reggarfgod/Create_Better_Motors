package com.reggarf.mods.create_better_motors;

import com.mojang.logging.LogUtils;

import com.reggarf.mods.create_better_motors.api.MessagePlugin;
import com.reggarf.mods.create_better_motors.config.CommonConfig;
import com.reggarf.mods.create_better_motors.registry.*;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Create_better_motors.MOD_ID)
public class Create_better_motors {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "create_better_motors";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final CreateRegistrate BASE_REGISTRATE = CreateRegistrate.create(MOD_ID);

    private static DeferredRegister<CreativeModeTab> TAB_REGISTRAR = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> tab = TAB_REGISTRAR.register("create_better_motors_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("item_group." + MOD_ID + ".tab"))
                    .icon(CBMBlocks.STARTER_MOTOR::asStack)
                    .build()
    );

    public static final CreateRegistrate REGISTRATE = BASE_REGISTRATE.setCreativeTab(tab);


    public static final ResourceKey<CreativeModeTab> CREATIVE_TAB_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB,
           ResourceLocation.fromNamespaceAndPath(MOD_ID, "create_better_motors_tab"));



    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public Create_better_motors(IEventBus modEventBus, ModContainer modContainer) {
        ModLoadingContext modLoadingContext = ModLoadingContext.get();

        LOGGER.info("Hello 1.21.1 C:BM!");
        BASE_REGISTRATE.registerEventListeners(modEventBus);
        TAB_REGISTRAR.register(modEventBus);
        NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(CBMClientIniter::onInitializeClient);
        CBMBlocks.load();
        CBMBlockEntityTypes.load();
        CBMItems.load();
        modContainer.registerConfig(ModConfig.Type.COMMON, CommonConfig.COMMON_CONFIG);
        modEventBus.addListener(RegisterCapabilitiesEvent.class, CFMCapabilities::register);
        MessagePlugin.register();

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
}
