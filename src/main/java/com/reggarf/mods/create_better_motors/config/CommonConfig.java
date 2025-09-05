package com.reggarf.mods.create_better_motors.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.mrh0.createaddition.CreateAddition;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.ModConfigSpec;

import static com.reggarf.mods.create_better_motors.Create_better_motors.MOD_ID;


@EventBusSubscriber(modid = MOD_ID,bus = EventBusSubscriber.Bus.MOD)
public class CommonConfig {
    //public static final String CATAGORY_GENERAL = "general";
    public static final String CATAGORY_STARTER_MOTOR = "starter_motor";
    public static final String CATAGORY_BASIC_MOTOR = "basic_motor";
    public static final String CATAGORY_HARDENED_MOTOR = "hardened_motor";
    public static final String CATAGORY_BLAZING_MOTOR = "blazing_motor";
    public static final String CATAGORY_NIOTIC_MOTOR = "niotic_motor";
    public static final String CATAGORY_SPIRITED_MOTOR = "spirited_motor";
    public static final String CATAGORY_NITRO_MOTOR = "nitro_motor";
    public static final String CATAGORY_WIRES = "wires";
    public static final String CATAGORY_ACCUMULATOR = "accumulator";
    public static final String CATAGORY_ANDESITE_ALTERNATOR = "andesite_alternator";
    public static final String CATAGORY_BRASS_ALTERNATOR = "brass_alternator";
    public static final String CATAGORY_COPPER_ALTERNATOR = "copper_alternator";


    public static final String CATAGORY_MESSAGES = "Messages";
    public static ModConfigSpec.BooleanValue MESSAGES_ENABLED;


    private static final ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

    public static ModConfigSpec COMMON_CONFIG;

    public static ModConfigSpec.IntValue ANDESITE_ALTERNATOR_FE_RPM;
    public static ModConfigSpec.IntValue ANDESITE_ALTERNATOR_MAX_STRESS;
    public static ModConfigSpec.BooleanValue ANDESITE_ALTERNATOR_AUDIO_ENABLED;
    public static ModConfigSpec.IntValue ANDESITE_ALTERNATOR_MAX_OUTPUT;
    public static ModConfigSpec.IntValue ANDESITE_ALTERNATOR_CAPACITY;
    public static ModConfigSpec.DoubleValue ANDESITE_ALTERNATOR_EFFICIENCY;

    public static ModConfigSpec.IntValue BRASS_ALTERNATOR_FE_RPM;
    public static ModConfigSpec.IntValue BRASS_ALTERNATOR_MAX_STRESS;
    public static ModConfigSpec.BooleanValue BRASS_ALTERNATOR_AUDIO_ENABLED;
    public static ModConfigSpec.IntValue BRASS_ALTERNATOR_MAX_OUTPUT;
    public static ModConfigSpec.IntValue BRASS_ALTERNATOR_CAPACITY;
    public static ModConfigSpec.DoubleValue BRASS_ALTERNATOR_EFFICIENCY;

    public static ModConfigSpec.IntValue COPPER_ALTERNATOR_FE_RPM;
    public static ModConfigSpec.IntValue COPPER_ALTERNATOR_MAX_STRESS;
    public static ModConfigSpec.BooleanValue COPPER_ALTERNATOR_AUDIO_ENABLED;
    public static ModConfigSpec.IntValue COPPER_ALTERNATOR_MAX_OUTPUT;
    public static ModConfigSpec.IntValue COPPER_ALTERNATOR_CAPACITY;
    public static ModConfigSpec.DoubleValue COPPER_ALTERNATOR_EFFICIENCY;


    public static ModConfigSpec.IntValue HEAVY_CONNECTOR_MAX_INPUT;
    public static ModConfigSpec.IntValue HEAVY_CONNECTOR_MAX_OUTPUT;
    public static ModConfigSpec.IntValue HEAVY_CONNECTOR_MAX_LENGTH;

    public static ModConfigSpec.IntValue STARTER_ELECTRIC_MOTOR_RPM_RANGE;
    public static ModConfigSpec.IntValue STARTER_ELECTRIC_MOTOR_MAX_INPUT;
    public static ModConfigSpec.IntValue STARTER_ELECTRIC_MOTOR_MINIMUM_CONSUMPTION;
    public static ModConfigSpec.IntValue STARTER_ELECTRIC_MOTOR_CAPACITY;
    public static ModConfigSpec.IntValue STARTER_FE_RPM;
    public static ModConfigSpec.IntValue STARTER_MAX_STRESS;
    public static ModConfigSpec.BooleanValue STARTER_AUDIO_ENABLED;

    public static ModConfigSpec.IntValue BASIC_ELECTRIC_MOTOR_RPM_RANGE;
    public static ModConfigSpec.IntValue BASIC_ELECTRIC_MOTOR_MAX_INPUT;
    public static ModConfigSpec.IntValue BASIC_ELECTRIC_MOTOR_MINIMUM_CONSUMPTION;
    public static ModConfigSpec.IntValue BASIC_ELECTRIC_MOTOR_CAPACITY;
    public static ModConfigSpec.IntValue BASIC_FE_RPM;
    public static ModConfigSpec.IntValue BASIC_MAX_STRESS;
    public static ModConfigSpec.BooleanValue BASIC_AUDIO_ENABLED;

    public static ModConfigSpec.IntValue HARDENED_ELECTRIC_MOTOR_RPM_RANGE;
    public static ModConfigSpec.IntValue HARDENED_ELECTRIC_MOTOR_MAX_INPUT;
    public static ModConfigSpec.IntValue HARDENED_ELECTRIC_MOTOR_MINIMUM_CONSUMPTION;
    public static ModConfigSpec.IntValue HARDENED_ELECTRIC_MOTOR_CAPACITY;
    public static ModConfigSpec.IntValue HARDENED_FE_RPM;
    public static ModConfigSpec.IntValue HARDENED_MAX_STRESS;
    public static ModConfigSpec.BooleanValue HARDENED_AUDIO_ENABLED;

    public static ModConfigSpec.IntValue BLAZING_ELECTRIC_MOTOR_RPM_RANGE;
    public static ModConfigSpec.IntValue BLAZING_ELECTRIC_MOTOR_MAX_INPUT;
    public static ModConfigSpec.IntValue BLAZING_ELECTRIC_MOTOR_MINIMUM_CONSUMPTION;
    public static ModConfigSpec.IntValue BLAZING_ELECTRIC_MOTOR_CAPACITY;
    public static ModConfigSpec.IntValue BLAZING_FE_RPM;
    public static ModConfigSpec.IntValue BLAZING_MAX_STRESS;
    public static ModConfigSpec.BooleanValue BLAZING_AUDIO_ENABLED;

    public static ModConfigSpec.IntValue NIOTIC_ELECTRIC_MOTOR_RPM_RANGE;
    public static ModConfigSpec.IntValue NIOTIC_ELECTRIC_MOTOR_MAX_INPUT;
    public static ModConfigSpec.IntValue NIOTIC_ELECTRIC_MOTOR_MINIMUM_CONSUMPTION;
    public static ModConfigSpec.IntValue NIOTIC_ELECTRIC_MOTOR_CAPACITY;
    public static ModConfigSpec.IntValue NIOTIC_FE_RPM;
    public static ModConfigSpec.IntValue NIOTIC_MAX_STRESS;
    public static ModConfigSpec.BooleanValue NIOTIC_AUDIO_ENABLED;


    public static ModConfigSpec.IntValue SPIRITED_ELECTRIC_MOTOR_RPM_RANGE;
    public static ModConfigSpec.IntValue SPIRITED_ELECTRIC_MOTOR_MAX_INPUT;
    public static ModConfigSpec.IntValue SPIRITED_ELECTRIC_MOTOR_MINIMUM_CONSUMPTION;
    public static ModConfigSpec.IntValue SPIRITED_ELECTRIC_MOTOR_CAPACITY;
    public static ModConfigSpec.IntValue SPIRITED_FE_RPM;
    public static ModConfigSpec.IntValue SPIRITED_MAX_STRESS;
    public static ModConfigSpec.BooleanValue SPIRITED_AUDIO_ENABLED;

    public static ModConfigSpec.IntValue NITRO_ELECTRIC_MOTOR_RPM_RANGE;
    public static ModConfigSpec.IntValue NITRO_ELECTRIC_MOTOR_MAX_INPUT;
    public static ModConfigSpec.IntValue NITRO_ELECTRIC_MOTOR_MINIMUM_CONSUMPTION;
    public static ModConfigSpec.IntValue NITRO_ELECTRIC_MOTOR_CAPACITY;
    public static ModConfigSpec.IntValue NITRO_FE_RPM;
    public static ModConfigSpec.IntValue NITRO_MAX_STRESS;
    public static ModConfigSpec.BooleanValue NITRO_AUDIO_ENABLED;

    public static ModConfigSpec.IntValue ACCUMULATOR_MAX_INPUT;
    public static ModConfigSpec.IntValue ACCUMULATOR_MAX_OUTPUT;

    static {
        builder.comment("Make sure config changes are duplicated on both Clients and the Server when running a dedicated Server,")
                .comment(" as the config isnt synced between Clients and Server.");

        builder.comment("Messages").push(CATAGORY_MESSAGES);
        MESSAGES_ENABLED = builder.comment("If Update Messages should be enabled or not.")
                .define("Messages_enabled", true);
        builder.pop();

        builder.comment("Accumulator").push(CATAGORY_ACCUMULATOR);
        ACCUMULATOR_MAX_INPUT =
                builder.comment("Accumulator max input in FE/t (Energy transfer).")
                .defineInRange("accumulator_max_input", 800000, 0, Integer.MAX_VALUE);

        ACCUMULATOR_MAX_OUTPUT = builder.comment("Accumulator max output in FE/t (Energy transfer).")
                .defineInRange("accumulator_max_output", 800000, 0, Integer.MAX_VALUE);

        builder.pop();

        builder.comment("Wires").push(CATAGORY_WIRES);

        HEAVY_CONNECTOR_MAX_INPUT = builder.comment("Heavy Connector max input in FE/t (Energy transfer).")
                .defineInRange("Heavy_connector_max_input", 90000, 0, Integer.MAX_VALUE);

        HEAVY_CONNECTOR_MAX_OUTPUT = builder.comment("Heavy Connector max output in FE/t (Energy transfer).")
                .defineInRange("Heavy_connector_max_output", 90000, 0, Integer.MAX_VALUE);

        HEAVY_CONNECTOR_MAX_LENGTH = builder.comment("Heavy Connector max wire length in blocks.")
                .defineInRange("Heavy_connector_wire_length", 48, 0, 256);
        builder.pop();

        builder.comment("Starter Motor").push(CATAGORY_STARTER_MOTOR);
        STARTER_ELECTRIC_MOTOR_RPM_RANGE = builder.comment("Electric Motor min/max RPM.")
                .defineInRange("motor_rpm_range", 256, 1, Integer.MAX_VALUE);

        STARTER_ELECTRIC_MOTOR_MINIMUM_CONSUMPTION = builder.comment("Electric Motor minimum required energy consumption in FE/t.")
                .defineInRange("motor_min_consumption", 8, 0, Integer.MAX_VALUE);

        STARTER_ELECTRIC_MOTOR_MAX_INPUT = builder.comment("Electric Motor max input in FE (Energy transfer not consumption).")
                .defineInRange("motor_max_input", 80000, 0, Integer.MAX_VALUE);

        STARTER_ELECTRIC_MOTOR_CAPACITY = builder.comment("Electric Motor internal capacity in FE.")
                .defineInRange("motor_capacity", 100000, 0, Integer.MAX_VALUE);
        STARTER_FE_RPM = builder.comment("Forge Energy conversion rate (in FE/t at 256 RPM, value is the FE/t generated and consumed is at 256rpm).")
                .defineInRange("fe_at_max_rpm", 960, 0, Integer.MAX_VALUE);

        STARTER_MAX_STRESS = builder.comment("Max stress for the Alternator and Electric Motor (in SU at 256 RPM).")
                .defineInRange("max_stress", 35768, 0, Integer.MAX_VALUE);

        STARTER_AUDIO_ENABLED = builder.comment("If audio should be enabled or not.")
                .define("audio_enabled", true);
        builder.pop();

        builder.comment("Basic Motor").push(CATAGORY_BASIC_MOTOR);
        BASIC_ELECTRIC_MOTOR_RPM_RANGE = builder.comment("Electric Motor min/max RPM.")
                .defineInRange("motor_rpm_range", 256, 1, Integer.MAX_VALUE);

        BASIC_ELECTRIC_MOTOR_MINIMUM_CONSUMPTION = builder.comment("Electric Motor minimum required energy consumption in FE/t.")
                .defineInRange("motor_min_consumption", 8, 0, Integer.MAX_VALUE);

        BASIC_ELECTRIC_MOTOR_MAX_INPUT = builder.comment("Electric Motor max input in FE (Energy transfer not consumption).")
                .defineInRange("motor_max_input", 80000, 0, Integer.MAX_VALUE);

        BASIC_ELECTRIC_MOTOR_CAPACITY = builder.comment("Electric Motor internal capacity in FE.")
                .defineInRange("motor_capacity", 100000, 0, Integer.MAX_VALUE);
        BASIC_FE_RPM = builder.comment("Forge Energy conversion rate (in FE/t at 256 RPM, value is the FE/t generated and consumed is at 256rpm).")
                .defineInRange("fe_at_max_rpm", 1920, 0, Integer.MAX_VALUE);

        BASIC_MAX_STRESS = builder.comment("Max stress for the Alternator and Electric Motor (in SU at 256 RPM).")
                .defineInRange("max_stress", 75768, 0, Integer.MAX_VALUE);

        BASIC_AUDIO_ENABLED = builder.comment("If audio should be enabled or not.")
                .define("audio_enabled", true);
        builder.pop();

        builder.comment("Hardened Motor").push(CATAGORY_HARDENED_MOTOR);
        HARDENED_ELECTRIC_MOTOR_RPM_RANGE = builder.comment("Electric Motor min/max RPM.")
                .defineInRange("motor_rpm_range", 256, 1, Integer.MAX_VALUE);

        HARDENED_ELECTRIC_MOTOR_MINIMUM_CONSUMPTION = builder.comment("Electric Motor minimum required energy consumption in FE/t.")
                .defineInRange("motor_min_consumption", 8, 0, Integer.MAX_VALUE);

        HARDENED_ELECTRIC_MOTOR_MAX_INPUT = builder.comment("Electric Motor max input in FE (Energy transfer not consumption).")
                .defineInRange("motor_max_input", 80000, 0, Integer.MAX_VALUE);

        HARDENED_ELECTRIC_MOTOR_CAPACITY = builder.comment("Electric Motor internal capacity in FE.")
                .defineInRange("motor_capacity", 100000, 0, Integer.MAX_VALUE);
        HARDENED_FE_RPM = builder.comment("Forge Energy conversion rate (in FE/t at 256 RPM, value is the FE/t generated and consumed is at 256rpm).")
                .defineInRange("fe_at_max_rpm", 3840, 0, Integer.MAX_VALUE);

        HARDENED_MAX_STRESS = builder.comment("Max stress for the Alternator and Electric Motor (in SU at 256 RPM).")
                .defineInRange("max_stress", 161536, 0, Integer.MAX_VALUE);

        HARDENED_AUDIO_ENABLED = builder.comment("If audio should be enabled or not.")
                .define("audio_enabled", true);
        builder.pop();

        builder.comment("Blazing Motor").push(CATAGORY_BLAZING_MOTOR);
        BLAZING_ELECTRIC_MOTOR_RPM_RANGE = builder.comment("Electric Motor min/max RPM.")
                .defineInRange("motor_rpm_range", 256, 1, Integer.MAX_VALUE);

        BLAZING_ELECTRIC_MOTOR_MINIMUM_CONSUMPTION = builder.comment("Electric Motor minimum required energy consumption in FE/t.")
                .defineInRange("motor_min_consumption", 8, 0, Integer.MAX_VALUE);

        BLAZING_ELECTRIC_MOTOR_MAX_INPUT = builder.comment("Electric Motor max input in FE (Energy transfer not consumption).")
                .defineInRange("motor_max_input", 80000, 0, Integer.MAX_VALUE);

        BLAZING_ELECTRIC_MOTOR_CAPACITY = builder.comment("Electric Motor internal capacity in FE.")
                .defineInRange("motor_capacity", 500000, 0, Integer.MAX_VALUE);
        BLAZING_FE_RPM = builder.comment("Forge Energy conversion rate (in FE/t at 256 RPM, value is the FE/t generated and consumed is at 256rpm).")
                .defineInRange("fe_at_max_rpm", 7680, 0, Integer.MAX_VALUE);

        BLAZING_MAX_STRESS = builder.comment("Max stress for the Alternator and Electric Motor (in SU at 256 RPM).")
                .defineInRange("max_stress", 253072, 0, Integer.MAX_VALUE);

        BLAZING_AUDIO_ENABLED = builder.comment("If audio should be enabled or not.")
                .define("audio_enabled", true);
        builder.pop();
        builder.comment("Niotic Motor").push(CATAGORY_NIOTIC_MOTOR);
        NIOTIC_ELECTRIC_MOTOR_RPM_RANGE = builder.comment("Electric Motor min/max RPM.")
                .defineInRange("motor_rpm_range", 256, 1, Integer.MAX_VALUE);

        NIOTIC_ELECTRIC_MOTOR_MINIMUM_CONSUMPTION = builder.comment("Electric Motor minimum required energy consumption in FE/t.")
                .defineInRange("motor_min_consumption", 8, 0, Integer.MAX_VALUE);

        NIOTIC_ELECTRIC_MOTOR_MAX_INPUT = builder.comment("Electric Motor max input in FE (Energy transfer not consumption).")
                .defineInRange("motor_max_input", 80000, 0, Integer.MAX_VALUE);

        NIOTIC_ELECTRIC_MOTOR_CAPACITY = builder.comment("Electric Motor internal capacity in FE.")
                .defineInRange("motor_capacity", 600000, 0, Integer.MAX_VALUE);
        NIOTIC_FE_RPM = builder.comment("Forge Energy conversion rate (in FE/t at 256 RPM, value is the FE/t generated and consumed is at 256rpm).")
                .defineInRange("fe_at_max_rpm", 19360, 0, Integer.MAX_VALUE);

        NIOTIC_MAX_STRESS = builder.comment("Max stress for the Alternator and Electric Motor (in SU at 256 RPM).")
                .defineInRange("max_stress", 710144, 0, Integer.MAX_VALUE);

        NIOTIC_AUDIO_ENABLED = builder.comment("If audio should be enabled or not.")
                .define("audio_enabled", true);
        builder.pop();

        builder.comment("Sprited Motor").push(CATAGORY_SPIRITED_MOTOR);
        SPIRITED_ELECTRIC_MOTOR_RPM_RANGE = builder.comment("Electric Motor min/max RPM.")
                .defineInRange("motor_rpm_range", 256, 1, Integer.MAX_VALUE);

        SPIRITED_ELECTRIC_MOTOR_MINIMUM_CONSUMPTION = builder.comment("Electric Motor minimum required energy consumption in FE/t.")
                .defineInRange("motor_min_consumption", 8, 0, Integer.MAX_VALUE);

        SPIRITED_ELECTRIC_MOTOR_MAX_INPUT = builder.comment("Electric Motor max input in FE (Energy transfer not consumption).")
                .defineInRange("motor_max_input", 80000, 0, Integer.MAX_VALUE);

        SPIRITED_ELECTRIC_MOTOR_CAPACITY = builder.comment("Electric Motor internal capacity in FE.")
                .defineInRange("motor_capacity", 700000, 0, Integer.MAX_VALUE);
        SPIRITED_FE_RPM = builder.comment("Forge Energy conversion rate (in FE/t at 256 RPM, value is the FE/t generated and consumed is at 256rpm).")
                .defineInRange("fe_at_max_rpm", 41720, 0, Integer.MAX_VALUE);

        SPIRITED_MAX_STRESS = builder.comment("Max stress for the Alternator and Electric Motor (in SU at 256 RPM).")
                .defineInRange("max_stress", 1298288, 0, Integer.MAX_VALUE);

        SPIRITED_AUDIO_ENABLED = builder.comment("If audio should be enabled or not.")
                .define("audio_enabled", true);
        builder.pop();


        builder.comment("Nitro Motor").push(CATAGORY_NITRO_MOTOR);
        NITRO_ELECTRIC_MOTOR_RPM_RANGE = builder.comment("Electric Motor min/max RPM.")
                .defineInRange("motor_rpm_range", 256, 1, Integer.MAX_VALUE);

        NITRO_ELECTRIC_MOTOR_MINIMUM_CONSUMPTION = builder.comment("Electric Motor minimum required energy consumption in FE/t.")
                .defineInRange("motor_min_consumption", 8, 0, Integer.MAX_VALUE);

        NITRO_ELECTRIC_MOTOR_MAX_INPUT = builder.comment("Electric Motor max input in FE (Energy transfer not consumption).")
                .defineInRange("motor_max_input", 85000, 0, Integer.MAX_VALUE);

        NITRO_ELECTRIC_MOTOR_CAPACITY = builder.comment("Electric Motor internal capacity in FE.")
                .defineInRange("motor_capacity", 800000, 0, Integer.MAX_VALUE);
        NITRO_FE_RPM = builder.comment("Forge Energy conversion rate (in FE/t at 256 RPM, value is the FE/t generated and consumed is at 256rpm).")
                .defineInRange("fe_at_max_rpm", 72440, 0, Integer.MAX_VALUE);

        NITRO_MAX_STRESS = builder.comment("Max stress for the Alternator and Electric Motor (in SU at 256 RPM).")
                .defineInRange("max_stress", 2499576, 0, Integer.MAX_VALUE);

        NITRO_AUDIO_ENABLED = builder.comment("If audio should be enabled or not.")
                .define("audio_enabled", true);
        builder.pop();

        builder.comment("Andesite Alternator").push(CATAGORY_ANDESITE_ALTERNATOR);
        ANDESITE_ALTERNATOR_MAX_OUTPUT = builder.comment("Alternator max input in FE (Energy transfer, not generation).")
                .defineInRange("generator_max_output", 5000, 0, Integer.MAX_VALUE);

        ANDESITE_ALTERNATOR_CAPACITY = builder.comment("Alternator internal capacity in FE.")
                .defineInRange("generator_capacity", 5000, 0, Integer.MAX_VALUE);

        ANDESITE_ALTERNATOR_EFFICIENCY = builder.comment("Alternator efficiency relative to base conversion rate.")
                .defineInRange("generator_efficiency", 0.85d, 0.01d, 1.0d);

        ANDESITE_ALTERNATOR_FE_RPM = builder.comment("Forge Energy conversion rate (in FE/t at 256 RPM, value is the FE/t generated and consumed is at 256rpm).")
                .defineInRange("fe_at_max_rpm", 1260, 0, Integer.MAX_VALUE);

        ANDESITE_ALTERNATOR_MAX_STRESS = builder.comment("Max stress for the Alternator and Electric Motor (in SU at 256 RPM).")
                .defineInRange("max_stress", 35768, 0, Integer.MAX_VALUE);

        ANDESITE_ALTERNATOR_AUDIO_ENABLED = builder.comment("If audio should be enabled or not.")
                .define("audio_enabled", true);
        builder.pop();

        builder.comment("Copper Alternator").push(CATAGORY_COPPER_ALTERNATOR);
        COPPER_ALTERNATOR_MAX_OUTPUT = builder.comment("Alternator max input in FE (Energy transfer, not generation).")
                .defineInRange("generator_max_output", 5000, 0, Integer.MAX_VALUE);

        COPPER_ALTERNATOR_CAPACITY = builder.comment("Alternator internal capacity in FE.")
                .defineInRange("generator_capacity", 5000, 0, Integer.MAX_VALUE);

        COPPER_ALTERNATOR_EFFICIENCY = builder.comment("Alternator efficiency relative to base conversion rate.")
                .defineInRange("generator_efficiency", 0.90d, 0.01d, 1.0d);

        COPPER_ALTERNATOR_FE_RPM = builder.comment("Forge Energy conversion rate (in FE/t at 256 RPM, value is the FE/t generated and consumed is at 256rpm).")
                .defineInRange("fe_at_max_rpm", 2400, 0, Integer.MAX_VALUE);

        COPPER_ALTERNATOR_MAX_STRESS = builder.comment("Max stress for the Alternator and Electric Motor (in SU at 256 RPM).")
                .defineInRange("max_stress", 75384, 0, Integer.MAX_VALUE);

        COPPER_ALTERNATOR_AUDIO_ENABLED = builder.comment("If audio should be enabled or not.")
                .define("audio_enabled", true);
        builder.pop();

        builder.comment("Brass Alternator").push(CATAGORY_BRASS_ALTERNATOR);
        BRASS_ALTERNATOR_MAX_OUTPUT = builder.comment("Alternator max input in FE (Energy transfer, not generation).")
                .defineInRange("generator_max_output", 10000, 0, Integer.MAX_VALUE);

        BRASS_ALTERNATOR_CAPACITY = builder.comment("Alternator internal capacity in FE.")
                .defineInRange("generator_capacity", 10000, 0, Integer.MAX_VALUE);

        BRASS_ALTERNATOR_EFFICIENCY = builder.comment("Alternator efficiency relative to base conversion rate.")
                .defineInRange("generator_efficiency", 0.90d, 0.01d, 1.0d);

        BRASS_ALTERNATOR_FE_RPM = builder.comment("Forge Energy conversion rate (in FE/t at 256 RPM, value is the FE/t generated and consumed is at 256rpm).")
                .defineInRange("fe_at_max_rpm", 4240, 0, Integer.MAX_VALUE);

        BRASS_ALTERNATOR_MAX_STRESS = builder.comment("Max stress for the Alternator and Electric Motor (in SU at 256 RPM).")
                .defineInRange("max_stress", 159708, 0, Integer.MAX_VALUE);

        BRASS_ALTERNATOR_AUDIO_ENABLED = builder.comment("If audio should be enabled or not.")
                .define("audio_enabled", true);
        builder.pop();

        COMMON_CONFIG = builder.build();

    }
    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Loading event) {
       loadConfig(CommonConfig.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("create_better_motors-common.toml"));
    }
    public static void loadConfig(ModConfigSpec spec, java.nio.file.Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();
        configData.load();
        spec.correct(configData);
    }
}

