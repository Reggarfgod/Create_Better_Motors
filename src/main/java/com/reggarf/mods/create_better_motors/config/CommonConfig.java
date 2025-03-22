package com.reggarf.mods.create_better_motors.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
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

//    public static ForgeConfigSpec.IntValue FE_RPM;
//    public static ForgeConfigSpec.IntValue MAX_STRESS;
//
//    public static ForgeConfigSpec.BooleanValue AUDIO_ENABLED;
//
//    public static ForgeConfigSpec.IntValue ALTERNATOR_MAX_OUTPUT;
//    public static ForgeConfigSpec.IntValue ALTERNATOR_CAPACITY;
//    public static ForgeConfigSpec.DoubleValue ALTERNATOR_EFFICIENCY;
//
    public static ForgeConfigSpec.IntValue HEAVY_CONNECTOR_MAX_INPUT;
    public static ForgeConfigSpec.IntValue HEAVY_CONNECTOR_MAX_OUTPUT;
    public static ForgeConfigSpec.IntValue HEAVY_CONNECTOR_MAX_LENGTH;

    public static ForgeConfigSpec.IntValue STARTER_ELECTRIC_MOTOR_RPM_RANGE;
    public static ForgeConfigSpec.IntValue STARTER_ELECTRIC_MOTOR_MAX_INPUT;
    public static ForgeConfigSpec.IntValue STARTER_ELECTRIC_MOTOR_MINIMUM_CONSUMPTION;
    public static ForgeConfigSpec.IntValue STARTER_ELECTRIC_MOTOR_CAPACITY;
    public static ForgeConfigSpec.IntValue STARTER_FE_RPM;
    public static ForgeConfigSpec.IntValue STARTER_MAX_STRESS;
    public static ForgeConfigSpec.BooleanValue STARTER_AUDIO_ENABLED;

    public static ForgeConfigSpec.IntValue BASIC_ELECTRIC_MOTOR_RPM_RANGE;
    public static ForgeConfigSpec.IntValue BASIC_ELECTRIC_MOTOR_MAX_INPUT;
    public static ForgeConfigSpec.IntValue BASIC_ELECTRIC_MOTOR_MINIMUM_CONSUMPTION;
    public static ForgeConfigSpec.IntValue BASIC_ELECTRIC_MOTOR_CAPACITY;
    public static ForgeConfigSpec.IntValue BASIC_FE_RPM;
    public static ForgeConfigSpec.IntValue BASIC_MAX_STRESS;
    public static ForgeConfigSpec.BooleanValue BASIC_AUDIO_ENABLED;

    public static ForgeConfigSpec.IntValue HARDENED_ELECTRIC_MOTOR_RPM_RANGE;
    public static ForgeConfigSpec.IntValue HARDENED_ELECTRIC_MOTOR_MAX_INPUT;
    public static ForgeConfigSpec.IntValue HARDENED_ELECTRIC_MOTOR_MINIMUM_CONSUMPTION;
    public static ForgeConfigSpec.IntValue HARDENED_ELECTRIC_MOTOR_CAPACITY;
    public static ForgeConfigSpec.IntValue HARDENED_FE_RPM;
    public static ForgeConfigSpec.IntValue HARDENED_MAX_STRESS;
    public static ForgeConfigSpec.BooleanValue HARDENED_AUDIO_ENABLED;

    public static ForgeConfigSpec.IntValue BLAZING_ELECTRIC_MOTOR_RPM_RANGE;
    public static ForgeConfigSpec.IntValue BLAZING_ELECTRIC_MOTOR_MAX_INPUT;
    public static ForgeConfigSpec.IntValue BLAZING_ELECTRIC_MOTOR_MINIMUM_CONSUMPTION;
    public static ForgeConfigSpec.IntValue BLAZING_ELECTRIC_MOTOR_CAPACITY;
    public static ForgeConfigSpec.IntValue BLAZING_FE_RPM;
    public static ForgeConfigSpec.IntValue BLAZING_MAX_STRESS;
    public static ForgeConfigSpec.BooleanValue BLAZING_AUDIO_ENABLED;

    public static ForgeConfigSpec.IntValue NIOTIC_ELECTRIC_MOTOR_RPM_RANGE;
    public static ForgeConfigSpec.IntValue NIOTIC_ELECTRIC_MOTOR_MAX_INPUT;
    public static ForgeConfigSpec.IntValue NIOTIC_ELECTRIC_MOTOR_MINIMUM_CONSUMPTION;
    public static ForgeConfigSpec.IntValue NIOTIC_ELECTRIC_MOTOR_CAPACITY;
    public static ForgeConfigSpec.IntValue NIOTIC_FE_RPM;
    public static ForgeConfigSpec.IntValue NIOTIC_MAX_STRESS;
    public static ForgeConfigSpec.BooleanValue NIOTIC_AUDIO_ENABLED;


    public static ForgeConfigSpec.IntValue SPIRITED_ELECTRIC_MOTOR_RPM_RANGE;
    public static ForgeConfigSpec.IntValue SPIRITED_ELECTRIC_MOTOR_MAX_INPUT;
    public static ForgeConfigSpec.IntValue SPIRITED_ELECTRIC_MOTOR_MINIMUM_CONSUMPTION;
    public static ForgeConfigSpec.IntValue SPIRITED_ELECTRIC_MOTOR_CAPACITY;
    public static ForgeConfigSpec.IntValue SPIRITED_FE_RPM;
    public static ForgeConfigSpec.IntValue SPIRITED_MAX_STRESS;
    public static ForgeConfigSpec.BooleanValue SPIRITED_AUDIO_ENABLED;

    public static ForgeConfigSpec.IntValue NITRO_ELECTRIC_MOTOR_RPM_RANGE;
    public static ForgeConfigSpec.IntValue NITRO_ELECTRIC_MOTOR_MAX_INPUT;
    public static ForgeConfigSpec.IntValue NITRO_ELECTRIC_MOTOR_MINIMUM_CONSUMPTION;
    public static ForgeConfigSpec.IntValue NITRO_ELECTRIC_MOTOR_CAPACITY;
    public static ForgeConfigSpec.IntValue NITRO_FE_RPM;
    public static ForgeConfigSpec.IntValue NITRO_MAX_STRESS;
    public static ForgeConfigSpec.BooleanValue NITRO_AUDIO_ENABLED;

    public CommonConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Wires").push(CATAGORY_WIRES);

        HEAVY_CONNECTOR_MAX_INPUT = builder.comment("Large Connector max input in FE/t (Energy transfer).")
                .defineInRange("large_connector_max_input", 90000, 0, Integer.MAX_VALUE);

        HEAVY_CONNECTOR_MAX_OUTPUT = builder.comment("Large Connector max output in FE/t (Energy transfer).")
                .defineInRange("large_connector_max_output", 90000, 0, Integer.MAX_VALUE);

        HEAVY_CONNECTOR_MAX_LENGTH = builder.comment("Large Connector max wire length in blocks.")
                .defineInRange("large_connector_wire_length", 48, 0, 256);
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
                .defineInRange("max_stress", 353072, 0, Integer.MAX_VALUE);

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
                .defineInRange("fe_at_max_rpm", 15360, 0, Integer.MAX_VALUE);

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
                .defineInRange("fe_at_max_rpm", 30720, 0, Integer.MAX_VALUE);

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
                .defineInRange("fe_at_max_rpm", 62440, 0, Integer.MAX_VALUE);

        NITRO_MAX_STRESS = builder.comment("Max stress for the Alternator and Electric Motor (in SU at 256 RPM).")
                .defineInRange("max_stress", 2499576, 0, Integer.MAX_VALUE);

        NITRO_AUDIO_ENABLED = builder.comment("If audio should be enabled or not.")
                .define("audio_enabled", true);
        builder.pop();

    }
    public static void loadConfig(ForgeConfigSpec spec, java.nio.file.Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();
        configData.load();
        spec.setConfig(configData);
    }
}

