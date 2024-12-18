package com.reggarf.mods.create_better_motors;

import com.reggarf.mods.create_better_motors.registry.CBMClientIniter;
import com.reggarf.mods.create_better_motors.registry.CBMBlockEntityTypes;
import com.reggarf.mods.create_better_motors.registry.CBMBlocks;
import com.reggarf.mods.create_better_motors.registry.CBMItems;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import com.reggarf.mods.create_better_motors.config.CBMConfig;

import com.reggarf.mods.create_better_motors.tools.RecipeTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Mod("create_better_motors")
public class Create_better_motors {
    public static final Logger LOGGER = LoggerFactory.getLogger("create_better_motors");

    public static final String MOD_ID = "create_better_motors";

    public static final CreateRegistrate BASE_REGISTRATE = CreateRegistrate.create(MOD_ID);

    private static DeferredRegister<CreativeModeTab> TAB_REGISTRAR = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
    public static final RegistryObject<CreativeModeTab> tab = TAB_REGISTRAR.register("create_better_motors_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("item_group." + MOD_ID + ".tab"))
                    .icon(CBMBlocks.BASIC_MOTOR::asStack)
                    .build()
    );

    public static final CreateRegistrate REGISTRATE = BASE_REGISTRATE.setCreativeTab(tab);


    public static final ResourceKey<CreativeModeTab> CREATIVE_TAB_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB,
            new ResourceLocation(MOD_ID, "create_better_motors_tab"));

    public static IRecipeTypeInfo ENERGISING_RECIPE_TYPE;

    private static int magnetPlacementHelperId;

    public Create_better_motors() {
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        LOGGER.info("Hello 1.20.1 Create!");

        BASE_REGISTRATE.registerEventListeners(modBus);
        TAB_REGISTRAR.register(modBus);
        MinecraftForge.EVENT_BUS.register(this);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        CBMBlocks.load();
        CBMBlockEntityTypes.load();
        CBMItems.load();
        CBMConfig.getCommon();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(CBMClientIniter::onInitializeClient);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::generalSetup);
        RecipeTool.register_type.register(modBus);
        RecipeTool.register.register(modBus);

    }

    public static int getMagnetPlacementHelperId() {
        return magnetPlacementHelperId;
    }



    private void generalSetup(final FMLCommonSetupEvent event) {

    }
}